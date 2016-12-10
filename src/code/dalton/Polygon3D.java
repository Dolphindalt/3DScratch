package code.dalton;

import java.awt.Color;

public class Polygon3D 
{

	private Point3D[] points;
	private double[] pos, newX, newY;
	private Color c;
	private PolygonObj drawablePolygon;
	private double averageDistance = 0, xt, yt, zt;
	private boolean draw = true;
	
	public Polygon3D(Color c)
	{
		points = new Point3D[0];
		this.c = c;
		createPolygon();
	}
	
	public Polygon3D(Point3D[] points, Color c)
	{
		this.points = points;
		this.c = c;
		createPolygon();
	}
	
	public Polygon3D(Color c, Point3D... points)
	{
		this.points = points;
		this.c = c;
		createPolygon();
	}
	
	public void createPolygon()
	{
		drawablePolygon = new PolygonObj(new double[points.length], new double[points.length], c);
	}
	
	public void updatePolygon()
	{
		newX = new double[points.length];
		newY = new double[points.length];
		draw = true;
		applyTranslation();
		for (int i = 0; i < points.length; i++)
		{
			pos = Calc.calculatePositionP(Camera.viewFrom, Camera.viewTo, points[i].getX(), points[i].getY(), points[i].getZ());
			newX[i] = (Scratch.doom.getWidth()/2 - Calc.focusPos[0]) + pos[0] * Camera.zoom;
			newY[i] = (Scratch.doom.getHeight()/2 - Calc.focusPos[1]) + pos[1] * Camera.zoom;
			// t determines if the object is in front or behind us
			if (Calc.t < 0)
				draw = false;
		}
		lighting();
		drawablePolygon.setDraw(draw);
		drawablePolygon.updatePolygon(newX, newY);
		averageDistance = getDistance();
	}
	
	public void translatePolygon(double xm, double ym, double zm)
	{
		xt = xm;
		yt = ym;
		zt = zm;
	}
	
	public boolean isPointInside(Point3D point)
	{
		double det = this.getPointFaceDeterminant(point);
		if (det<=0)
		{
			return true;
		} else return false;
	}
	
	// Convex shapes only!!!
	public Point3D getIntersectionPoint(Point3D ray1, Point3D ray2)
	{
		double det1 = this.getPointFaceDeterminant(ray1);
		double det2 = this.getPointFaceDeterminant(ray2);
		
		if (det1 == det2)
		{
			//paralell line, so use middle point
			Point3D av = new Point3D();
			av = av.add(ray1, ray2);
			av = av.scale(0.5);
			return av;
		}
		else
		{
			Point3D intersect = new Point3D();
			intersect = intersect.substract(ray2, ray1);
			intersect = intersect.scale((0 - det1)/(det2-det1));
			intersect = intersect.add(ray1);
			return intersect;
		}
	}
	
	//https://en.wikipedia.org/wiki/Weiler%E2%80%93Atherton_clipping_algorithm
	//www.jhave.org/learner/misc/sutherlandhodgman/sutherlandhogdmanclipping.shtml
	// Strange that I am using a class called Polygon3D as a flat dimensional face
	public Polygon3D clipFace(Polygon3D clippingFace)
	{
		Polygon3D workingFace = new Polygon3D(c);
		
		for(int i = 0; i < this.getNumberOfEdges(); i++)
		{
			Point3D p1 = this.getStartingEdge(i);
			Point3D p2 = this.getEndEdge(i);
			
			if (clippingFace.isPointInside(p1) && clippingFace.isPointInside(p2))
			{
				// add end point
				workingFace.addVertex(p2);
			}
			else if (clippingFace.isPointInside(p1) && clippingFace.isPointInside(p2)==false)
			{
				// only intersection is added
				Point3D intersection = clippingFace.getIntersectionPoint(p1, p2);
				workingFace.addVertex(intersection);
			}
			else if (clippingFace.isPointInside(p1)==false && clippingFace.isPointInside(p2)==false)
			{
				// outside line, nothing
			}
			else
			{
				// end vertex inside and starting outside so intercept and end point are added
				Point3D intersection = clippingFace.getIntersectionPoint(p1, p2);
				workingFace.addVertex(intersection);
				workingFace.addVertex(p2);
			}
		}
		
		if (workingFace.getNumberOfEdges()>=3)
		{
			return workingFace;
		}
		else
		{
			return null;
		}
	}
	
	public void rewind(Point3D inp)
	{
		if (!this.isPointInside(inp))
		{
			Point3D[] verts = new Point3D[this.points.length];
			for (int i = this.points.length; i > 0; i--)
			{
				verts[i] = this.points[i];
			}
			this.points = verts;
		}
	}
	
	private double getPointFaceDeterminant(Point3D point)
	{
		if (this.points.length<3)
			throw new RuntimeException("A face has less then three vertices");
		Point3D a = points[0];
		Point3D b = points[1];
		Point3D c = points[2];
		Point3D x = point;
		
		Point3D bDash = new Point3D();
		bDash = bDash.substract(b, x);
		Point3D cDash = new Point3D();
		cDash = cDash.substract(c, x);
		Point3D xDash = new Point3D();
		xDash = xDash.substract(x, a);
		
		//http://www.mathsisfun.com/algebra/matrix-determinant.html
		double det = bDash.getX()*(cDash.getY()*xDash.getZ()-cDash.getZ()*xDash.getY())-bDash.getY()*
				(cDash.getX()*xDash.getZ()-cDash.getZ()*xDash.getX())+bDash.getZ()*(cDash.getX()*xDash.getY()-cDash.getY()*xDash.getX());
		return det;
	}
	
	private void applyTranslation()
	{
		for (int i = 0; i < points.length; i++)
		{
			points[i].setX(points[i].getX() + xt);
			points[i].setY(points[i].getY() + yt);
			points[i].setZ(points[i].getZ() + zt);
		}
		xt = 0;
		yt = 0;
		zt = 0;
	}
	
	private double getDistance()
	{
		double total = 0;
		for (int i = 0; i < points.length; i++)
		{
			total += getDistanceToPoint(i);
		}
		return total/points.length;
	}
	
	private double getDistanceToPoint(int i)
	{
		return Math.sqrt((Camera.viewFrom[0] - points[i].getX())*(Camera.viewFrom[0] - points[i].getX()) + 
				(Camera.viewFrom[1] - points[i].getY())*(Camera.viewFrom[1] - points[i].getY()) + 
				(Camera.viewFrom[2] - points[i].getZ())*(Camera.viewFrom[2] - points[i].getZ()));
	}

	private void lighting()
	{
		Plane lightingPlane = new Plane(this);
		double angle = Math.acos(((lightingPlane.getPlaneVector().getX() * Screen.lightDirection[0]) + 
				  (lightingPlane.getPlaneVector().getY() * Screen.lightDirection[1]) + (lightingPlane.getPlaneVector().getZ() * Screen.lightDirection[2]))
				  /(Math.sqrt(Screen.lightDirection[0] * Screen.lightDirection[0] + Screen.lightDirection[1] * Screen.lightDirection[1] + Screen.lightDirection[2] * Screen.lightDirection[2])));
		drawablePolygon.setLighting(0.2 + 1 - Math.sqrt(Math.toDegrees(angle)/180));
		if (drawablePolygon.getLighting() > 1)
			drawablePolygon.setLighting(1);
		if (drawablePolygon.getLighting() < 0)
			drawablePolygon.setLighting(0);
	}

	public void addVertex(Point3D point)
	{	
		Point3D[] re = new Point3D[this.getPoints().length+1]; 
		for (int i = 0; i < this.points.length; i++)
		{
			re[i] = this.points[i];
		}
		re[this.getPoints().length] = point;
		this.points = re;
		createPolygon();
	}
	
	public Point3D getStartingEdge(int n)
	{
		return this.points[n];
	}
	
	public Point3D getEndEdge(int n)
	{
		return this.points[((n + 1)%this.points.length)]; //loop for last edge
	}
	
	public Point3D getVertex(int index)
	{
		return this.points[index];
	}
	
	public int getNumberOfEdges()
	{
		return this.points.length;
	}
	
	public int getNumberOfSegments()
	{
		return this.points.length-2;
	}
	
	public Point3D[] getPoints() {
		return points;
	}

	public void setPoints(Point3D[] points) {
		this.points = points;
	}

	public double getAverageDistance() {
		return averageDistance;
	}

	public void setAverageDistance(double averageDistance) {
		this.averageDistance = averageDistance;
	}

	public double[] getPos() {
		return pos;
	}

	public void setPos(double[] pos) {
		this.pos = pos;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public PolygonObj getDrawablePolygon() {
		return drawablePolygon;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
	
}

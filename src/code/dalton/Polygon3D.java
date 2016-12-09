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
	
	//https://en.wikipedia.org/wiki/Weiler%E2%80%93Atherton_clipping_algorithm

	
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
	
}

package code.dalton;

import java.awt.Color;

public class Polygon3D 
{

	private double[] x, y, z, pos, newX, newY;
	private Color c;
	private PolygonObj drawablePolygon;
	private double averageDistance = 0, xt, yt, zt;
	private boolean draw = true;
	
	public Polygon3D(double[] x, double[] y, double[] z, Color c)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		createPolygon();
	}
	
	// The average distance is the average t value
	private void createPolygon()
	{
		drawablePolygon = new PolygonObj(new double[x.length], new double[x.length], c);
	}
	
	public void updatePolygon()
	{
		newX = new double[x.length];
		newY = new double[x.length];
		draw = true;
		applyTranslation();
		for (int i = 0; i < x.length; i++)
		{
			pos = Calc.calculatePositionP(Camera.viewFrom, Camera.viewTo, x[i], y[i], z[i]);
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
	
	private void applyTranslation()
	{
		for (int i = 0; i < x.length; i++)
		{
			x[i] += xt;
			y[i] += yt;
			z[i] += zt;
		}
		xt = 0;
		yt = 0;
		zt = 0;
	}
	
	private double getDistance()
	{
		double total = 0;
		for (int i = 0; i < x.length; i++)
		{
			total += getDistanceToPoint(i);
		}
		return total/x.length;
	}
	
	private double getDistanceToPoint(int i)
	{
		return Math.sqrt((Camera.viewFrom[0] - x[i])*(Camera.viewFrom[0] - x[i]) + 
				(Camera.viewFrom[1] - y[i])*(Camera.viewFrom[1] - y[i]) + 
				(Camera.viewFrom[2] - z[i])*(Camera.viewFrom[2] - z[i]));
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
	
	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double[] y) {
		this.y = y;
	}

	public double[] getZ() {
		return z;
	}

	public void setZ(double[] z) {
		this.z = z;
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

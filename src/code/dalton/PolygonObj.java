package code.dalton;

import java.awt.Color;
import java.awt.Polygon;

import java.awt.Graphics;

public class PolygonObj 
{

	private Polygon p;
	private Color c;
	private boolean draw = true;
	private double lighting = 1;
	
	public PolygonObj(double[] x, double[] y, Color c)
	{
		p = new Polygon();
		for (int i = 0; i < x.length; i++)
			p.addPoint((int) x[i], (int) y[i]);
		this.c = c;
	}
	
	public void updatePolygon(double[] x, double[] y)
	{
		p.reset();
		for (int i = 0; i < x.length; i++)
		{
			p.xpoints[i] = (int) x[i];
			p.ypoints[i] = (int) y[i];
			p.npoints = x.length;
		}
	}
	
	public void drawPolygon(Graphics g)
	{
		if (draw)
		{
			g.setColor(new Color((int) (c.getRed() * lighting), (int) (c.getGreen() * lighting), (int) (c.getBlue() * lighting)));
			g.fillPolygon(p);
		}
	}

	public boolean isDraw() 
	{
		return draw;
	}

	public void setDraw(boolean draw) 
	{
		this.draw = draw;
	}

	public double getLighting() {
		return lighting;
	}

	public void setLighting(double lighting) {
		this.lighting = lighting;
	}
	
}

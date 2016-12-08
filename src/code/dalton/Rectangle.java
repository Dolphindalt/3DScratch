package code.dalton;

import java.awt.Color;

public class Rectangle
{

	private Vector location;
	private double width, length, height, rotation, x1, x2, x3, x4, y1, y2, y3, y4, xt, yt, zt;
	private double[] rotationValues, angle;
	private Polygon3D[] polygons;
	private Color c;
	
	public Rectangle(double x, double y, double z, double width, double length, double height, Color c)
	{
		rotation = Math.PI*0.75;
		rotationValues = new double[4];
		polygons = new Polygon3D[6];
		
		polygons[0] = new Polygon3D(new double[]{x, x+width, x+width, x}, new double[]{y, y, y+length, y+length},  new double[]{z, z, z, z}, c);
		Screen.polygon3ds.add(polygons[0]);
		polygons[1] = new Polygon3D(new double[]{x, x+width, x+width, x}, new double[]{y, y, y+length, y+length},  new double[]{z+height, z+height, z+height, z+height}, c);
		Screen.polygon3ds.add(polygons[1]);
		polygons[2] = new Polygon3D(new double[]{x, x, x+width, x+width}, new double[]{y, y, y, y},  new double[]{z, z+height, z+height, z}, c);
		Screen.polygon3ds.add(polygons[2]);
		polygons[3] = new Polygon3D(new double[]{x+width, x+width, x+width, x+width}, new double[]{y, y, y+length, y+length},  new double[]{z, z+height, z+height, z}, c);
		Screen.polygon3ds.add(polygons[3]);
		polygons[4] = new Polygon3D(new double[]{x, x, x+width, x+width}, new double[]{y+length, y+length, y+length, y+length},  new double[]{z, z+height, z+height, z}, c);
		Screen.polygon3ds.add(polygons[4]);
		polygons[5] = new Polygon3D(new double[]{x, x, x, x}, new double[]{y, y, y+length, y+length},  new double[]{z, z+height, z+height, z}, c);
		Screen.polygon3ds.add(polygons[5]);
		
		this.c = c;
		this.location = new Vector(x, y, z);
		this.width = width;
		this.length = length;
		this.height = height;
		
		setRotationValues();
		updatePolygons();
		Screen.rectangles.add(this);
	}
	
	public void setRotationValues() // Everything is rotated in radians
	{
		angle = new double[4];
		double xDif = -width/2 + 0.00001;
		double yDif = -length/2 + 0.00001;
		angle[0] = Math.atan(yDif/xDif); // inverse tan in radians
		if (xDif < 0)
			angle[0] += Math.PI;
		xDif = width/2 + 0.00001;
		yDif = -length/2 + 0.00001;
		angle[1] = Math.atan(yDif/xDif);
		if (xDif < 0)
			angle[1] += Math.PI;
		xDif = width/2 + 0.00001;
		yDif = length/2 + 0.00001;
		angle[2] = Math.atan(yDif/xDif);
		if (xDif < 0)
			angle[2] += Math.PI;
		xDif = -width/2 + 0.00001;
		yDif = length/2 + 0.00001;
		angle[3] = Math.atan(yDif/xDif);
		if (xDif < 0)
			angle[3] += Math.PI;
		
		rotationValues[0] = angle[0] + 0.25 * Math.PI;
		rotationValues[1] = angle[1] + 0.25 * Math.PI;
		rotationValues[2] = angle[2] + 0.25 * Math.PI;
		rotationValues[3] = angle[3] + 0.25 * Math.PI;
	}
	
	public void updateDirection(double toX, double toY)
	{
		double xDif = toX - (location.getX() + width/2) + 0.00001;
		double yDif = toY - (location.getY() + length/2) + 0.00001;
		double aglet = Math.atan(yDif/xDif) + 0.75 * Math.PI;
		if (xDif < 0)
			aglet += Math.PI;
		rotation = aglet;
		updatePolygons();
	}
	
	public void updatePolygons()
	{
		for (int i = 0; i < 6; i++) // replace old polygons with new updated versions
		{
			Screen.polygon3ds.add(polygons[i]);
			Screen.polygon3ds.remove(polygons[i]);
		}
		applyTranslation();
		
		double radius = Math.sqrt(width*width + length*length);
		
		x1 = location.getX()+width*0.5+radius*0.5*Math.cos(rotation + rotationValues[0]);
		x2 = location.getX()+width*0.5+radius*0.5*Math.cos(rotation + rotationValues[1]);
		x3 = location.getX()+width*0.5+radius*0.5*Math.cos(rotation + rotationValues[2]);
		x4 = location.getX()+width*0.5+radius*0.5*Math.cos(rotation + rotationValues[3]);
		   
		y1 = location.getY()+length*0.5+radius*0.5*Math.sin(rotation + rotationValues[0]);
		y2 = location.getY()+length*0.5+radius*0.5*Math.sin(rotation + rotationValues[1]);
		y3 = location.getY()+length*0.5+radius*0.5*Math.sin(rotation + rotationValues[2]);
		y4 = location.getY()+length*0.5+radius*0.5*Math.sin(rotation + rotationValues[3]);
		
		polygons[0].setX(new double[]{x1, x2, x3, x4});
		polygons[0].setY(new double[]{y1, y2, y3, y4});
		polygons[0].setZ(new double[]{location.getZ(), location.getZ(), location.getZ(), location.getZ()});

		polygons[1].setX(new double[]{x4, x3, x2, x1});
		polygons[1].setY(new double[]{y4, y3, y2, y1});
		polygons[1].setZ(new double[]{location.getZ()+height, location.getZ()+height, location.getZ()+height, location.getZ()+height});
			   
		polygons[2].setX(new double[]{x1, x1, x2, x2});
		polygons[2].setY(new double[]{y1, y1, y2, y2});
		polygons[2].setZ(new double[]{location.getZ(), location.getZ()+height, location.getZ()+height, location.getZ()});

		polygons[3].setX(new double[]{x2, x2, x3, x3});
		polygons[3].setY(new double[]{y2, y2, y3, y3});
		polygons[3].setZ(new double[]{location.getZ(), location.getZ()+height, location.getZ()+height, location.getZ()});

		polygons[4].setX(new double[]{x3, x3, x4, x4});
		polygons[4].setY(new double[]{y3, y3, y4, y4});
		polygons[4].setZ(new double[]{location.getZ(), location.getZ()+height, location.getZ()+height, location.getZ()});

		polygons[5].setX(new double[]{x4, x4, x1, x1});
		polygons[5].setY(new double[]{y4, y4, y1, y1});
		polygons[5].setZ(new double[]{location.getZ(), location.getZ()+height, location.getZ()+height, location.getZ()});
	}
	
	public boolean intersectsRectangle(Rectangle r2)
	{
		Polygon3D p1 = polygons[0], p2 = polygons[1], pp1 = r2.getPolygons()[0], pp2 = r2.getPolygons()[1];
		if (p1.getX() == pp1.getX() && p1.getY() == pp1.getY() && p1.getZ() == pp1.getZ() && p2.getX() == pp2.getX() && p2.getY() == pp2.getY() && p2.getZ() == pp2.getZ())
			return true;
		return false;
	}
	
	public void translateRectangle(double xm, double ym, double zm)
	{
		xt = xm;
		yt = ym;
		zt = zm;
	}
	
	private void applyTranslation()
	{
		for (int i = 0; i < polygons.length; i++)
		{
			polygons[i].translatePolygon(xt, yt, zt);
		}
		location.setX(location.getX() + xt);
		location.setX(location.getY() + yt);
		location.setX(location.getZ() + zt);
		xt = 0;
		yt = 0;
		zt = 0;
	}
	
	public void intersects()
	{
		
	}
	
	public void removeCube()
	{
		for (int i = 0; i < 6; i++)
		{
			Screen.polygon3ds.remove(polygons[i]);
		}
		Screen.rectangles.remove(this);
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public Polygon3D[] getPolygons() {
		return polygons;
	}
	
}

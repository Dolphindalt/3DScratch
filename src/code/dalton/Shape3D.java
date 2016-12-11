package code.dalton;

import java.awt.Color;

public class Shape3D 
{

	private Polygon3D[] faces;
	private double xm = 0, ym = 0, zm = 0;
	private double width, height, length;
	private Color c;
	
	public Shape3D() 
	{
		faces = new Polygon3D[0];
	}
	
	public Shape3D(Polygon3D... faces)
	{
		this.faces = faces;
	}
	
	public Shape3D(Color c, double x, double y, double z, double width, double length, double height)
	{
        faces = new Polygon3D[6];
        this.width = width;
        this.length = length;
        this.height = height;
        this.c = c;
        
        faces[0] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x+width,y,z), new Point3D(x+width,y+length,z), new Point3D(x,y+length,z));
        Screen.polygon3ds.add(faces[0]);
        faces[1] = new Polygon3D(c, new Point3D(x,y,z+height), new Point3D(x+width,y,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x,y+length,z+height));
        Screen.polygon3ds.add(faces[1]);
        faces[2] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x,y,z+height), new Point3D(x+width,y,z+height), new Point3D(x+width,y,z));
        Screen.polygon3ds.add(faces[2]);
        faces[3] = new Polygon3D(c, new Point3D(x+width,y,z), new Point3D(x+width,y,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x+width,y+length,z));
        Screen.polygon3ds.add(faces[3]);
        faces[4] = new Polygon3D(c, new Point3D(x,y+length,z), new Point3D(x,y+length,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x+width,y+length,z));
        Screen.polygon3ds.add(faces[4]);
        faces[5] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x,y,z+height), new Point3D(x,y+length,z+height), new Point3D(x,y+length,z));
        Screen.polygon3ds.add(faces[5]);
        
        Screen.shape3ds.add(this);
	}
	
	public void updateFaces()
	{
		for (int i = 0; i < faces.length; i++)
		{
			Screen.polygon3ds.remove(faces[i]);
			Screen.polygon3ds.add(faces[i]);
		}
		applyTranslations();
	}
	
	public void translateShape3D(double x, double y, double z)
	{
		xm = x;
		ym = y;
		zm = z;
	}
	
	private void applyTranslations()
	{
		Polygon3D[] temp =  faces; 
        for (int i = 0; i < faces.length; i++)
        {
            temp[i].translatePolygon(xm, ym, zm);
        }
        faces = temp;
        xm = 0;
        ym = 0;
        zm = 0;
	}
	
	public int getNumberOfFaces()
	{
		return faces.length;
	}
	
	public void addFace(Polygon3D face)
	{
		Polygon3D[] re = new Polygon3D[this.faces.length+1];
		for (int i = 0; i < this.faces.length; i++)
		{
			re[i] = this.faces[i];
		}
		re[this.faces.length] = face;
		this.faces = re;
	}
	
	public Polygon3D getFace(int fn)
	{
		return faces[fn];
	}
	
	public Shape3D clip(Shape3D cp)
	{
		Shape3D wp = (Shape3D) clone();
		for (int i = 0; i < cp.getNumberOfFaces(); i++)
		{
			wp = clip(wp, cp.getFace(i));
		}
		return wp;
	}
	
	private Shape3D clip(Shape3D in, Polygon3D cf)
	{
		Shape3D out = new Shape3D();
		// Each edges of each face of in is clipped by cf
		for (int i = 0; i < in.getNumberOfFaces(); i++)
		{
			Polygon3D clippedFace = in.getFace(i).clipFace(cf);
			if (clippedFace != null)
				out.addFace(clippedFace);
		}
		// Convex shapes only!!!! cf clipped by in
		Polygon3D wf = cf;
		for (int i = 0; i < in.getNumberOfFaces(); i++)
		{
			if (wf == null) break;
			wf = wf.clipFace(in.getFace(i));
		}
		if (wf != null)
			out.addFace(wf);
		// return the clippings
		return out;
	}
	
	// MUST USE A RECTANGLE CONSTRUCTOR
	public void setLocationRelativeToFaceZero(double x, double y, double z)
	{
		removeAllFaces();
        faces[0] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x+width,y,z), new Point3D(x+width,y+length,z), new Point3D(x,y+length,z));
        Screen.polygon3ds.add(faces[0]);
        faces[1] = new Polygon3D(c, new Point3D(x,y,z+height), new Point3D(x+width,y,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x,y+length,z+height));
        Screen.polygon3ds.add(faces[1]);
        faces[2] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x,y,z+height), new Point3D(x+width,y,z+height), new Point3D(x+width,y,z));
        Screen.polygon3ds.add(faces[2]);
        faces[3] = new Polygon3D(c, new Point3D(x+width,y,z), new Point3D(x+width,y,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x+width,y+length,z));
        Screen.polygon3ds.add(faces[3]);
        faces[4] = new Polygon3D(c, new Point3D(x,y+length,z), new Point3D(x,y+length,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x+width,y+length,z));
        Screen.polygon3ds.add(faces[4]);
        faces[5] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x,y,z+height), new Point3D(x,y+length,z+height), new Point3D(x,y+length,z));
        Screen.polygon3ds.add(faces[5]);
	}
	
	public void addShapeToSimulation(Color c)
	{
		for (int i = 0; i < faces.length; i++)
		{
			faces[i].setC(c);
			Screen.polygon3ds.add(faces[i]);
		}
		Screen.shape3ds.add(this);
	}
	
	public void removeShape3D()
	{
		for (int i = 0; i < faces.length; i++)
		{
			Screen.polygon3ds.remove(faces[i]);
		}
		Screen.shape3ds.remove(this);
	}
	
	public void removeAllFaces()
	{
		for (int i = 0; i < faces.length; i++)
		{
			Screen.polygon3ds.remove(faces[i]);
		}
	}
	
	public void setColor(Color c)
	{
		for (Polygon3D p : faces)
		{
			p.setC(c);
		}
	}
	
	@Override
	public Object clone()
	{
		return new Shape3D(this.faces);
	}
	
}

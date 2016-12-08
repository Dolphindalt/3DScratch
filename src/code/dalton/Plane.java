package code.dalton;

public class Plane 
{

	private Vector v1, v2, planeVector;
	
	private double[] p = new double[3];
	
	public Plane(Polygon3D p3d)
	{
		p[0] = p3d.getX()[0];
		p[1] = p3d.getY()[0];
		p[2] = p3d.getZ()[0];
		
		v1 = new Vector(
				p3d.getX()[1] - p3d.getX()[0],
				p3d.getY()[1] - p3d.getY()[0],
				p3d.getZ()[1] - p3d.getZ()[0]);
		v2 = new Vector(
				p3d.getX()[2] - p3d.getX()[0],
				p3d.getY()[2] - p3d.getY()[0],
				p3d.getZ()[2] - p3d.getZ()[0]);
		planeVector = v1.crossProduct(v2);
	}
	
	public Plane(Vector v1, Vector v2, double[] z)
	{
		this.p = z;
		this.v1 = v1;
		this.v2 = v2;
		this.planeVector = v1.crossProduct(v2);
	}

	public Vector getV1() 
	{
		return v1;
	}

	public void setV1(Vector v1) 
	{
		this.v1 = v1;
	}

	public Vector getV2() 
	{
		return v2;
	}

	public void setV2(Vector v2) 
	{
		this.v2 = v2;
	}

	public Vector getPlaneVector() 
	{
		return planeVector;
	}

	public void setPlaneVector(Vector planeVector) 
	{
		this.planeVector = planeVector;
	}

	public double[] getP() 
	{
		return p;
	}

	public void setP(double[] p) 
	{
		this.p = p;
	}
	
}

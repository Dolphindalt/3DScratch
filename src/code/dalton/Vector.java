package code.dalton;

public class Vector {

	private double x = 0, y = 0, z = 0;
	
	public Vector(double x, double y, double z)
	{
		double length = Math.sqrt(x*x + y*y + z*z);
		if (length > 0)
		{
			this.x = x/length;
			this.y = y/length;
			this.z = z/length;
		}
	}
	
	// https://www.youtube.com/watch?v=h0NJK4mEIJU
	public Vector crossProduct(Vector v)
	{
		Vector crossVector = new Vector(
				y * v.z - z * v.y,
				z * v.x - x * v.z,
				x * v.y - y * v.x);
		return crossVector;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
}

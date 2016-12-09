package code.dalton;


public class Point3D
{

    private double x, y, z;
    
    public Point3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point3D negated()
    {
    	return new Point3D(-this.x, -this.y, -this.z);
    }
    
    public double getX()
    {
        return this.x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public double getZ()
    {
        return this.z;
    }
    
    public void setZ(double z)
    {
        this.z = z;
    }
    
}

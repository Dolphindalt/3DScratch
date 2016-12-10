package code.dalton;


public class Point3D
{

    private double x, y, z;
    
    public Point3D()
    {
    	
    }
    
    /*public Point3D(double x, double y, double z)
    {
        double length = Math.sqrt(x*x + y*y + z*z);
        if (length > 0)
        {
            this.x = x/length;
            this.y = y/length;
            this.z = z/length;
        }
    }*/
    
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
    
    public Point3D crossProduct(Point3D v)
    {
        return new Point3D(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
    }
    
    public Point3D add(Point3D v)
    {
        return new Point3D(x + v.getX(), y + v.getY(), z + v.getZ());
    }
    
    public Point3D add(Point3D p1, Point3D p2)
    {
    	return new Point3D(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }
    
    public Point3D subtract(Point3D v)
    {
        return new Point3D(x - v.getX(), y - v.getY(), z - v.getZ());
    }
    
    public Point3D substract(Point3D f, Point3D s)
    {
    	return new Point3D(f.x - s.x, f.y - s.y, f.z - s.z);
    }
    
    public Point3D scale(double scaleFactor)
    {
        return new Point3D(x * scaleFactor, y * scaleFactor, z * scaleFactor);
    }
    
    public double dot(Point3D v)
    {
        return x * v.getX() + y * v.getY() + z * v.getZ();
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

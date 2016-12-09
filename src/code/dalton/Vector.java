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
        return new Vector(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
    }
    
    public Vector addVector(Vector v)
    {
        return new Vector(x + v.getX(), y + v.getY(), z + v.getZ());
    }
    
    public Vector subtractVector(Vector v)
    {
        return new Vector(x - v.getX(), y - v.getY(), z - v.getZ());
    }
    
    public Vector scaleVector(double scaleFactor)
    {
        return new Vector(x * scaleFactor, y * scaleFactor, z * scaleFactor);
    }
    
    public double dot(Vector v)
    {
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }
    
    public Vector negated()
    {
    	return new Vector(-this.x, -this.y, -this.z);
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

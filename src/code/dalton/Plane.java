package code.dalton;

import java.lang.NoSuchMethodError;

public class Plane 
{

    private Vector v1, v2, planeVector;
    
    private double[] p = new double[3];
    
    public Plane(Polygon3D p3d)
    {
        try
        {
            p[0] = p3d.getPoints()[0].getX();
            p[1] = p3d.getPoints()[0].getY();
            p[2] = p3d.getPoints()[0].getZ();
        
                v1 = new Vector(
                    p3d.getPoints()[1].getX() - p3d.getPoints()[0].getX(),
                    p3d.getPoints()[1].getY() - p3d.getPoints()[0].getY(),
                    p3d.getPoints()[1].getZ() - p3d.getPoints()[0].getZ());
                v2 = new Vector(
                    p3d.getPoints()[2].getX() - p3d.getPoints()[0].getX(),
                    p3d.getPoints()[2].getY() - p3d.getPoints()[0].getY(),
                    p3d.getPoints()[2].getZ() - p3d.getPoints()[0].getZ());
                planeVector = v1.crossProduct(v2);
        }
        catch (NoSuchMethodError e) {}
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

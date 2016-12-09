package code.dalton;

import java.awt.Color;

public class Rectangle
{

    private Point3D location;
    private double width, length, height, rotation, x1, x2, x3, x4, y1, y2, y3, y4, xt, yt, zt;
    private double[] rotationValues, angle;
    private Polygon3D[] polygons;
    private Polygon3D collisionBox;
    private Color c;
    
    public Rectangle(double x, double y, double z, double width, double length, double height, Color c)
    {
        rotation = Math.PI*0.75;
        rotationValues = new double[4];
        polygons = new Polygon3D[6];
        
        polygons[0] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x+width,y,z), new Point3D(x+width,y+length,z), new Point3D(x,y+length,z));
        Screen.polygon3ds.add(polygons[0]);
        polygons[1] = new Polygon3D(c, new Point3D(x,y,z+height), new Point3D(x+width,y,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x,y+length,z+height));
        Screen.polygon3ds.add(polygons[1]);
        polygons[2] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x,y,z+height), new Point3D(x+width,y,z+height), new Point3D(x+width,y,z));
        Screen.polygon3ds.add(polygons[2]);
        polygons[3] = new Polygon3D(c, new Point3D(x+width,y,z), new Point3D(x+width,y,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x+width,y+length,z));
        Screen.polygon3ds.add(polygons[3]);
        polygons[4] = new Polygon3D(c, new Point3D(x,y+length,z), new Point3D(x,y+length,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x+width,y+length,z));
        Screen.polygon3ds.add(polygons[4]);
        polygons[5] = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x,y,z+height), new Point3D(x,y+length,z+height), new Point3D(x,y+length,z));
        Screen.polygon3ds.add(polygons[5]);
        collisionBox = new Polygon3D(c, new Point3D(x,y,z), new Point3D(x+width,y,z), new Point3D(x+width,y+length,z), new Point3D(x,y+length,z),
        new Point3D(x,y,z+height), new Point3D(x+width,y,z+height), new Point3D(x+width,y+length,z+height), new Point3D(x,y+length,z+height));
        
        this.c = c;
        this.location = new Point3D(x, y, z);
        this.width = width;
        this.length = length;
        this.height = height;
        
        Screen.rectangles.add(this);
        
        setRotationValues();
        updatePolygons();
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
        
        polygons[0].getPoints()[0].setX(x1);
        polygons[0].getPoints()[1].setX(x2);
        polygons[0].getPoints()[2].setX(x3);
        polygons[0].getPoints()[3].setX(x4);
        polygons[0].getPoints()[0].setY(y1);
        polygons[0].getPoints()[1].setY(y2);
        polygons[0].getPoints()[2].setY(y3);
        polygons[0].getPoints()[3].setY(y4);
        polygons[0].getPoints()[0].setZ(location.getZ());
        polygons[0].getPoints()[1].setZ(location.getZ());
        polygons[0].getPoints()[2].setZ(location.getZ());
        polygons[0].getPoints()[3].setZ(location.getZ());
        
        polygons[1].getPoints()[0].setX(x4);
        polygons[1].getPoints()[1].setX(x3);
        polygons[1].getPoints()[2].setX(x2);
        polygons[1].getPoints()[3].setX(x1);
        polygons[1].getPoints()[0].setY(y4);
        polygons[1].getPoints()[1].setY(y3);
        polygons[1].getPoints()[2].setY(y2);
        polygons[1].getPoints()[3].setY(y1);
        polygons[1].getPoints()[0].setZ(location.getZ()+height);
        polygons[1].getPoints()[1].setZ(location.getZ()+height);
        polygons[1].getPoints()[2].setZ(location.getZ()+height);
        polygons[1].getPoints()[3].setZ(location.getZ()+height);
        
        polygons[2].getPoints()[0].setX(x1);
        polygons[2].getPoints()[1].setX(x1);
        polygons[2].getPoints()[2].setX(x2);
        polygons[2].getPoints()[3].setX(x2);
        polygons[2].getPoints()[0].setY(y1);
        polygons[2].getPoints()[1].setY(y1);
        polygons[2].getPoints()[2].setY(y2);
        polygons[2].getPoints()[3].setY(y2);
        polygons[2].getPoints()[0].setZ(location.getZ());
        polygons[2].getPoints()[1].setZ(location.getZ()+height);
        polygons[2].getPoints()[2].setZ(location.getZ()+height);
        polygons[2].getPoints()[3].setZ(location.getZ());
        
        polygons[3].getPoints()[0].setX(x2);
        polygons[3].getPoints()[1].setX(x2);
        polygons[3].getPoints()[2].setX(x3);
        polygons[3].getPoints()[3].setX(x3);
        polygons[3].getPoints()[0].setY(y2);
        polygons[3].getPoints()[1].setY(y2);
        polygons[3].getPoints()[2].setY(y3);
        polygons[3].getPoints()[3].setY(y3);
        polygons[3].getPoints()[0].setZ(location.getZ());
        polygons[3].getPoints()[1].setZ(location.getZ()+height);
        polygons[3].getPoints()[2].setZ(location.getZ()+height);
        polygons[3].getPoints()[3].setZ(location.getZ());
        
        polygons[4].getPoints()[0].setX(x3);
        polygons[4].getPoints()[1].setX(x3);
        polygons[4].getPoints()[2].setX(x4);
        polygons[4].getPoints()[3].setX(x4);
        polygons[4].getPoints()[0].setY(y3);
        polygons[4].getPoints()[1].setY(y3);
        polygons[4].getPoints()[2].setY(y4);
        polygons[4].getPoints()[3].setY(y4);
        polygons[4].getPoints()[0].setZ(location.getZ());
        polygons[4].getPoints()[1].setZ(location.getZ()+height);
        polygons[4].getPoints()[2].setZ(location.getZ()+height);
        polygons[4].getPoints()[3].setZ(location.getZ());
        
        polygons[5].getPoints()[0].setX(x4);
        polygons[5].getPoints()[1].setX(x4);
        polygons[5].getPoints()[2].setX(x1);
        polygons[5].getPoints()[3].setX(x1);
        polygons[5].getPoints()[0].setY(y4);
        polygons[5].getPoints()[1].setY(y4);
        polygons[5].getPoints()[2].setY(y1);
        polygons[5].getPoints()[3].setY(y1);
        polygons[5].getPoints()[0].setZ(location.getZ());
        polygons[5].getPoints()[1].setZ(location.getZ()+height);
        polygons[5].getPoints()[2].setZ(location.getZ()+height);
        polygons[5].getPoints()[3].setZ(location.getZ());
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
        location.setY(location.getY() + yt);
        location.setZ(location.getZ() + zt);
        xt = 0;
        yt = 0;
        zt = 0;
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
    
    public Polygon3D getCollisionBox()
    {
        return this.collisionBox;
    }
    
}

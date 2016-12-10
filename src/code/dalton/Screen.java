package code.dalton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Screen extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    public static String ctext = "No data";
    
    public static List<Polygon3D> polygon3ds = new ArrayList<Polygon3D>();
    public static List<Shape3D> shape3ds = new ArrayList<Shape3D>();
    //public static List<Rectangle> rectangles = new ArrayList<Rectangle>();
    
    public static double[] lightDirection = new double[] {1, 1, 1}; 
    
    public double sleepTime = 1000/30, lastRefresh = 0;
    
    private Camera camera;
    private double aimSight = 4, sunPos = 0;
    private KeyManager km;
    private int[] newOrder;
    
    public Screen()
    {
        hideMouse();
        camera = new Camera();
        addMouseMotionListener(camera);
        km = new KeyManager();
        addKeyListener(km);
        setFocusable(true);
        
        //new Rectangle(0, 0, 0, 5, 5, 5, Color.GREEN);
        shape3ds.add(new Shape3D(Color.RED, 0, 0, 0, 2, 2, 2));
        shape3ds.add(new Shape3D(Color.ORANGE, 5, 5, 0, 2, 2, 2));
    }
    
    public void paintComponent(Graphics g)  
    {
        g.clearRect(0, 0, getWidth(), getHeight());
        
        km.tick();
        g.drawString("ViewFrom: " + Camera.viewFrom[0] + ", " + Camera.viewFrom[1] + ", " + Camera.viewFrom[2], 20, 20);
        g.drawString("ViewTo: " + Camera.viewTo[0] + ", " + Camera.viewTo[1] + ",   " + Camera.viewTo[2], 20, 40);
        g.drawString(ctext, 20, 60);
        camera.checkInput();
        Calc.setCalculations();
        
        light();
        
        for (int i = 0; i < shape3ds.size(); i++)
        	shape3ds.get(i).updateFaces();
        
        //for (int i = 0; i < rectangles.size(); i++)
            //rectangles.get(i).updatePolygons();
        
        for (int i = 0; i < polygon3ds.size(); i++)
            polygon3ds.get(i).updatePolygon();
        
        setOrder();
        
        for (int i = 0; i < newOrder.length; i++)
            polygon3ds.get(newOrder[i]).getDrawablePolygon().drawPolygon(g);


        drawCursor(g);
        
        sleepAndRefresh();
    }
    
    private void setOrder() // [⪧෴⪦]
    {
        // create and fill array of all average distances
        double[] k = new double[polygon3ds.size()];
        newOrder = new int[polygon3ds.size()];
        for (int i = 0; i < polygon3ds.size(); i++)
        {
            k[i] = polygon3ds.get(i).getAverageDistance();
            newOrder[i] = i; // new order is the position of each average
        }
        
        // loop and sort the array into newOrder
        double t;
        int tr;
        for (int h = 0; h < k.length-1; h++) // Perform this action for the amount of avs
        {
            for (int j = 0; j < k.length-1; j++) // Again
            {
                if (k[j] < k[j + 1]) // if av1 is less than av2 + 1
                {
                    t = k[j]; 
                    tr = newOrder[j]; 
                    newOrder[j] = newOrder[j + 1];
                    k[j] = k[j + 1]; 
                    
                    newOrder[j + 1] = tr;
                    k[j + 1] = t; 
                }
            }
        }
    }
    
    public void sleepAndRefresh()
    {
        while (true)
        {
            if ((System.currentTimeMillis() - lastRefresh) > sleepTime)
            {
                lastRefresh = System.currentTimeMillis();
                repaint();
                break;
            }
            else
            {
                try
                {
                    Thread.sleep((long)(sleepTime - (System.currentTimeMillis() - lastRefresh)));
                }
                catch (Exception e) {}
            }
        }
    }
    
    private void light()
    {
        sunPos += 0.005;
        double size = 1000;
        lightDirection[0] = size/2 - (size/2 + Math.cos(sunPos) * size * 10);
        lightDirection[1] = size/2 - (size/2 + Math.sin(sunPos) * size * 10);
        lightDirection[2] = -200;
    }
    
    private void drawCursor(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawLine((int)(Scratch.doom.getWidth()/2 - aimSight), (int)(Scratch.doom.getHeight()/2), (int)(Scratch.doom.getWidth()/2 + aimSight), (int)(Scratch.doom.getHeight()/2));
        g.drawLine((int)(Scratch.doom.getWidth()/2), (int)(Scratch.doom.getHeight()/2 - aimSight), (int)(Scratch.doom.getWidth()/2), (int)(Scratch.doom.getHeight()/2 + aimSight));
    }
    
    private void hideMouse()
    {
        BufferedImage cursor = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
        Cursor invis = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "Invis");
        setCursor(invis);
    }
}

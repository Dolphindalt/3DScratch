package code.dalton;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Camera implements MouseMotionListener
{

	public static double[] viewFrom = new double[]{ 15, 5, 10 };
	public static double[] viewTo = new double[]{ 0, 0, 0 };
	public static double mouseX = 0, mouseY = 0, movementSpeed = 1, zoom = 1000;
	
	private double vertLook = -0.9, horLook = 0, horRotSpeed = 900, vertRotSpeed = 2200;
	private Robot robot;
	
	public Camera()
	{
		
	}
	
	public void checkInput()
	{
		Vector viewVector = new Vector(viewTo[0] - viewFrom[0], viewTo[1] - viewFrom[1],
				viewTo[2] - viewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;
		Vector verticleVector = new Vector(0, 0, 1);
		Vector sideVector = viewVector.crossProduct(verticleVector);
		
		if (KeyManager.left)
		{
			xMove += sideVector.getX();
			yMove += sideVector.getY();
			zMove += sideVector.getZ();
		}
		if (KeyManager.right)
		{
			xMove -= sideVector.getX();
			yMove -= sideVector.getY();
			zMove -= sideVector.getZ();	
		}
		if (KeyManager.up)
		{
			xMove += viewVector.getX();
			yMove += viewVector.getY();
			zMove += viewVector.getZ();
		}
		if (KeyManager.down)
		{
			xMove -= viewVector.getX();
			yMove -= viewVector.getY();
			zMove -= viewVector.getZ();
		}
		if (KeyManager.esc)
		{
			System.exit(0);
		}
		if (KeyManager.u)
		{
		    Screen.rectangles.get(0).translateRectangle(-1, 0, 0);
		}
		if (KeyManager.h)
		{
		    Screen.rectangles.get(0).translateRectangle(0, -1, 0);
		}
		if (KeyManager.j)
		{
		    Screen.rectangles.get(0).translateRectangle(1, 0, 0);
		}
		if (KeyManager.k)
		{
		    Screen.rectangles.get(0).translateRectangle(0, 1, 0);
		}
		Vector moveVector = new Vector(xMove, yMove, zMove);
		moveTo(viewFrom[0] + moveVector.getX() * movementSpeed, viewFrom[1] + moveVector.getY() * movementSpeed, viewFrom[2] + moveVector.getZ() * movementSpeed);
	}
	
	private void moveTo(double x, double y, double z)
	{
		viewFrom[0] = x;
		viewFrom[1] = y;
		viewFrom[2] = z;
		updateView();
	}
	
	public void mouseDragged(MouseEvent m) 
	{
		mouseMovement(m.getX(), m.getY());
		mouseX = m.getX();
		mouseY = m.getY();
		centerMouse();
	}

	public void mouseMoved(MouseEvent m) 
	{
		mouseMovement(m.getX(), m.getY());
		mouseX = m.getX();
		mouseY = m.getY();
		centerMouse();
	}

	private void centerMouse()
	{
		try 
		{
			robot = new Robot();
			robot.mouseMove((int)(Scratch.doom.getWidth()/2), (int)(Scratch.doom.getHeight()/2));
		} 
		catch (AWTException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void mouseMovement(double newMouseX, double newMouseY)
	{
		double difX = (newMouseX - Scratch.doom.getWidth()/2);
		double difY = (newMouseY - Scratch.doom.getHeight()/2);
		difY *= 6 - Math.abs(vertLook) * 5;
		vertLook -= difY / vertRotSpeed;
		horLook += difX / horRotSpeed;
		
		if (vertLook > 0.999)
			vertLook = 0.999;
		if (vertLook < -0.999)
			vertLook = -0.999;
		updateView();
	}
	
	private void updateView()
	{
		double r = Math.sqrt(1 - (vertLook * vertLook));
		Camera.viewTo[0] = Camera.viewFrom[0] + r * Math.cos(horLook);
		Camera.viewTo[1] = Camera.viewFrom[1] + r * Math.sin(horLook);
		Camera.viewTo[2] = Camera.viewFrom[2] + vertLook;
	}
	
}

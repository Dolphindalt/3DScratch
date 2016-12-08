package code.dalton;

public class Calc // (∩ȌĹ̯Ȍ)⊃━☆ﾟ.*-_. 
{

	public static double t = 0;
	public static double[] focusPos = new double[2];
	public static Vector w1, w2, viewVector, rotationVector, directionVector, planeVector1, planeVector2;
	public static Plane p;
	
	public static double[] calculatePositionP(double[] viewFrom, double[] viewTo,
			double x, double y, double z)
	{
		double[] projP = getProj(viewFrom, viewTo, x, y, z, p);
		double[] drawP = getDrawP(projP[0], projP[1], projP[2]);
		return drawP;
	}
	
	public static void setCalculations()
	{
		viewVector = new Vector(Camera.viewTo[0] - Camera.viewFrom[0], Camera.viewTo[1] - Camera.viewFrom[1], Camera.viewTo[2] - Camera.viewFrom[2]);
		directionVector = new Vector(1, 1, 1);
		planeVector1 = viewVector.crossProduct(directionVector);
		planeVector2 = viewVector.crossProduct(planeVector1);
		p = new Plane(planeVector1, planeVector2, Camera.viewTo);
		
		rotationVector = Calc.getRotationVector(Camera.viewFrom, Camera.viewTo);
		w1 = viewVector.crossProduct(rotationVector);
		w2 = viewVector.crossProduct(w1);
		
		focusPos = Calc.calculatePositionP(Camera.viewFrom, Camera.viewTo, Camera.viewTo[0], Camera.viewTo[1], Camera.viewTo[2]);
		focusPos[0] = Camera.zoom * focusPos[0];
		focusPos[1] = Camera.zoom * focusPos[1];
	}
	
	private static double[] getProj(double[] viewFrom, double[] viewTo, double x, double y, double z, Plane p)
	{
		Vector viewToPoint = new Vector(x - viewFrom[0], y - viewFrom[1], z - viewFrom[2]);
		
		// Where is what is in front of us or how far away is it?
		t = (p.getPlaneVector().getX()*p.getP()[0] + p.getPlaneVector().getY()*p.getP()[1] + p.getPlaneVector().getZ()*p.getP()[2]
		- (p.getPlaneVector().getX()*viewFrom[0] + p.getPlaneVector().getY()*viewFrom[1] + p.getPlaneVector().getZ()*viewFrom[2]))
		/ (p.getPlaneVector().getX()*viewToPoint.getX() + p.getPlaneVector().getY()*viewToPoint.getY() + p.getPlaneVector().getZ()*viewToPoint.getZ());
		
		x = viewFrom[0] + viewToPoint.getX() * t;
		y = viewFrom[1] + viewToPoint.getY() * t;
		z = viewFrom[2] + viewToPoint.getZ() * t;
		return new double[] { x, y, z };
	}
	
	private static double[] getDrawP(double x, double y, double z)
	{
		double drawX = w2.getX() * x + w2.getY() * y + w2.getZ() * z;
		double drawY = w1.getX() * x + w1.getY() * y + w1.getZ() * z;
		return new double[]{drawX, drawY};
	}
	
	private static Vector getRotationVector(double[] viewFrom, double[] viewTo)
	{
		double dx = Math.abs(viewFrom[0]-viewTo[0]); // How far from to
		double dy = Math.abs(viewFrom[1]-viewTo[1]);
		double rotX, rotY;
		rotX = dy/(dx+dy);
		rotY = dx/(dx+dy);
		if (viewFrom[1]>viewTo[1])
			rotX = -rotX;
		if (viewFrom[0]<viewTo[0])
			rotY = -rotY;
		return new Vector(rotX, rotY, 0);
	}
	
}

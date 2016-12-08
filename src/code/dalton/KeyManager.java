package code.dalton;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener
{

	private boolean[] keys;
	
	public static boolean up, down, left, right, e, enter, esc, z, x, u, h, j, k;
	
	public KeyManager()
	{
		keys = new boolean[700];
	}
	
	public void tick()
	{
			up = keys[KeyEvent.VK_W];
			down = keys[KeyEvent.VK_S];
			left = keys[KeyEvent.VK_A];
			right = keys[KeyEvent.VK_D];
			e = keys[KeyEvent.VK_E];
			enter = keys[KeyEvent.VK_ENTER];
			esc = keys[KeyEvent.VK_ESCAPE];
			z = keys[KeyEvent.VK_Z];
			x = keys[KeyEvent.VK_X];
			u = keys[KeyEvent.VK_U];
			h = keys[KeyEvent.VK_H];
			j = keys[KeyEvent.VK_J];
			k = keys[KeyEvent.VK_K];
	}

	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) 
	{
		
	}
	
}

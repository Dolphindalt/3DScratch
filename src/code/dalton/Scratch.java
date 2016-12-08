package code.dalton;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Scratch extends JFrame
{
	
	public static Scratch SCRATCH; // ツ
	public static Dimension doom;
	
	public static void main(String[] args) {
		doom = Toolkit.getDefaultToolkit().getScreenSize();
		SCRATCH = new Scratch();
	}
	
	private static final long serialVersionUID = 1L;
	
	private Screen so;
	
	public Scratch()
	{
		so = new Screen();
		add(so);
		setUndecorated(true);
		setLocation(0, 0);
		setSize(doom);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}

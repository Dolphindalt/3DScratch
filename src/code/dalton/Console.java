package code.dalton;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Console 
{

	private List<String> consoleStrings;
	
	public Console()
	{
		consoleStrings = new ArrayList<String>();
	}
	
	public void addConsoleMessage(String message)
	{
		consoleStrings.add(message);
	}
	
	public void updateConsoleMessage(int index, String message)
	{
		try 
		{
			consoleStrings.set(index, message);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void removeConsoleMessage(int index)
	{
		consoleStrings.remove(index);
	}
	
	public void clear()
	{
		consoleStrings.clear();
	}
	
	public void renderConsole(Graphics g)
	{
        for (int i = 0; i < consoleStrings.size(); i++)
        {
        	g.drawString(consoleStrings.get(i), 20, i*20 + 20);
        }
	}
	
}

package code.dalton;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake 
{

	public static int width, height, speed, points;
	public static boolean gameOver = false;
	public static Direction currentDirection;
	
	private enum Direction
	{
		UP, DOWN, LEFT, RIGHT;
	}
	
	private long lastRefresh = 0;
	private Console console;
	private Random r;
	
	private List<Shape3D> snake;
	private Shape3D food;
	
	public Snake(Console console)
	{
		Screen.shape3ds.add(new Shape3D(Color.DARK_GRAY, 0, 0, -10, 100, 100, 0));
		this.console = console;
		console.addConsoleMessage("{ Name:3DScratch, Branch:LogicTest }");
		console.addConsoleMessage("");
		console.addConsoleMessage("");
		r = new Random();
		snake = new ArrayList<Shape3D>();
		
		gameOver = true;
	}
	
	public void tick()
	{
		updateConsole();
		if (System.currentTimeMillis() - lastRefresh > speed)
		{
			if (gameOver)
			{
				if (KeyManager.enter)
				{
					startGame();
				}
			}
			else
			{
				if (KeyManager.h && currentDirection != Direction.LEFT)
                    currentDirection = Direction.RIGHT;
                else if (KeyManager.k && currentDirection != Direction.RIGHT)
                    currentDirection = Direction.LEFT;
                else if (KeyManager.j && currentDirection != Direction.DOWN)
                    currentDirection = Direction.UP;
                else if (KeyManager.u && currentDirection != Direction.UP)
                    currentDirection = Direction.DOWN;

                moveSnake();
			}
			lastRefresh = System.currentTimeMillis();
		}
	}
	
	private void startGame()
	{
		setDefaultValues();
		
		for (int i = 0; i < snake.size(); i++)
		{
			snake.get(i).removeShape3D();
		}
		snake.clear();
		
		if (food != null)
		{
			food.removeShape3D();
			food = null;
		}
		
		snake.add(new Shape3D(Color.GREEN, 5, 5, 0, 5, 5, 5));
		
		generateFood();
	}
	
	private void moveSnake()
	{
		for (int i = snake.size() - 1; i >= 0; i--)
		{
			if (i == 0)
			{
				translateHead(i);
				
				if (snake.get(i).getFace(0).getPoints()[0].getX() < 0 || snake.get(i).getFace(0).getPoints()[0].getY() < 0
						|| snake.get(i).getFace(0).getPoints()[0].getX() >= width || snake.get(i).getFace(0).getPoints()[0].getY() >= height)
				{
					die();
				}
				
				for (int j = 2; j < snake.size(); j++)
				{
					if (snake.get(i).getFace(0).getPoints()[0].getX() == snake.get(j).getFace(0).getPoints()[0].getX() &&
							snake.get(i).getFace(0).getPoints()[0].getY() == snake.get(j).getFace(0).getPoints()[0].getY())
					{
						die();
					}
				}
				
				if (snake.size() > 2 && snake.get(i).getFace(0).getPoints()[0].getX() == snake.get(snake.size()-1).getFace(0).getPoints()[0].getX() &&
						snake.get(i).getFace(0).getPoints()[0].getY() == snake.get(snake.size()-1).getFace(0).getPoints()[0].getY())
				{
					die();
				}
				
				if (snake.get(0).getFace(0).getPoints()[0].getX() == food.getFace(0).getPoints()[0].getX() &&
						snake.get(0).getFace(0).getPoints()[0].getY() == food.getFace(0).getPoints()[0].getY())
				{
					eat();
				}
			}
			else
			{
				snake.get(i).setLocationRelativeToFaceZero(snake.get(i - 1).getFace(0).getPoints()[0].getX(), snake.get(i - 1).getFace(0).getPoints()[0].getY(), 0);
			}
		}
	}
	
	private void eat()
	{
		Shape3D tail = new Shape3D(Color.GREEN, snake.get(snake.size() - 1).getFace(0).getPoints()[0].getX(), snake.get(snake.size() - 1).getFace(0).getPoints()[0].getY(), 0, 5, 5, 5);
		
		snake.add(snake.size(), tail);
		food.removeShape3D();
		
		points++;
		updateConsole();
		generateFood();
	}
	
	private void die()
	{
		gameOver = true;
	}
	
	private void generateFood()
	{
		int x = (r.nextInt(width/10)+1)*5;
		int y = (r.nextInt(height/10)+1)*5;
		food = new Shape3D(Color.RED, (double) x, (double) y, 0, 5, 5, 5);
	}
	
	private void translateHead(int i)
	{
		switch (currentDirection)
		{
			case RIGHT:
				snake.get(i).translateShape3D(5, 0, 0);
				break;
			case LEFT:
				snake.get(i).translateShape3D(-5, 0, 0);
				break;
			case UP:
				snake.get(i).translateShape3D(0, -5, 0);
				break;
			case DOWN:
				snake.get(i).translateShape3D(0, 5, 0);
				break;
		}
	}
	
	private void setDefaultValues()
	{
		width = 100;
		height = 100;
		speed = 150;
		points = 0;
		gameOver = false;
		currentDirection = Direction.DOWN;
		console.updateConsoleMessage(2, "");
		updateConsole();
	}
	
	private void updateConsole()
	{
		console.updateConsoleMessage(1, "Points: " + points);
		console.updateConsoleMessage(2, "" + gameOver);
	}
	
}

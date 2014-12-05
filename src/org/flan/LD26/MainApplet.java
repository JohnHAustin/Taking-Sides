package org.flan.LD26;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MainApplet extends Applet
{
	private static final long serialVersionUID = 1L;
	private static Main main;
	private static Canvas display_parent;
	
	@SuppressWarnings("serial")
	public void init() 
	{
		setLayout(new BorderLayout());
		try 
		{
			display_parent = new Canvas() 
			{
				public final void addNotify() 
				{
					super.addNotify();
					startLWJGL();
				}
				public final void removeNotify() 
				{
					Main.stopGame();
					super.removeNotify();
				}
			};
			display_parent.setSize(getWidth(),getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		} catch (Exception e) 
		{
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
		
		main = new Main(true);
	}
	
	public static void startLWJGL() 
	{
		try 
		{
			Display.setParent(display_parent);
			Display.create();
			Display.makeCurrent();
			Keyboard.create();
			Mouse.create();
		} 
		catch (LWJGLException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void start()
	{
		main.paused = false;
	}
	
	@Override
	public void stop()
	{
		main.paused = true;
	}
	
	@Override
	public void destroy()
	{
		Main.stopGame();
	}
	
	@Override
	public void paint (Graphics g)
	{
		//g.drawString ("Hello World", 25, 50);
	}
}

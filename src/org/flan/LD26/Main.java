package org.flan.LD26;


import org.flan.LD26.entity.CreaturePlayer;
import org.flan.LD26.render.Renderer;
import org.flan.LD26.world.Level;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Main 
{
	public static Main instance;
	public static final String title = "LD26";
	public int screenWidth = 800;
	public int screenHeight = 600;
	public final int tickRate = 20;
	public long lastTick;
	public Renderer renderer;
	public Controller controller;
	public Level currentLevel;
	public CreaturePlayer thePlayer;
	private boolean isRunning = true;
	public int whiteness;
	public boolean paused = false;
	public boolean applet;
	public int pauseDelay = 0;
	
	public static void main(String[] args) 
	{
		new Main(false);
	}
	
	public Main(boolean app)
	{
		applet = app;
		instance = this;
		lastTick = getTime(tickRate);
		controller = new Controller(this);
		thePlayer = new CreaturePlayer();
		currentLevel = new Level(this, 1, false);

		
		try
        {
        	renderer = new Renderer(this);
        }
        catch(Exception lwjglexception)
        {
            lwjglexception.printStackTrace();
            try
            {
                Thread.sleep(1000L);
            }
            catch(InterruptedException interruptedexception) { }
            System.exit(0);
        }
		
		while(isRunning)
        {
	        Display.update();
	   
	        if(Display.isCloseRequested()) 
	        {
	        	isRunning = false;
	        	stopGame();
	        } 
	        
	        else 
	        {
	        	renderer.renderScreen();
	        	Display.sync(60);
	        } 
	   			        
	        //Tick every 20th of a second
	        long i = getTime(tickRate);
	        if(i > lastTick)
	        {
	        	tick(i);
	        	lastTick = i;
	        }
        }
	}
	
	private void tick(long time)
	{
		pauseDelay--;
		controller.tick();
		if(paused)
			return;
		if(currentLevel != null)
			currentLevel.update();
		if(whiteness > 0)
			whiteness--;
	}	
	
	public static void stopGame() 
	{
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		SoundManager.stop();
		System.exit(0);
	}

	public long getTime(int tickRate) 
	{
		return (Sys.getTime() * tickRate) / Sys.getTimerResolution();
	}
	
	public static void log(String s)
	{
		System.out.println(s);
	}
}

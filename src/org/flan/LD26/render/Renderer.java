package org.flan.LD26.render;

import org.flan.LD26.Main;
import org.flan.LD26.entity.Creature;
import org.flan.LD26.entity.CreaturePlayer;
import org.flan.LD26.entity.Entity;
import org.flan.LD26.entity.EntityCreature;
import org.flan.LD26.entity.EntitySnowflakeBoss;
import org.flan.LD26.entity.EntitySword;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Renderer 
{
	public Main main;
	public Renderer instance;
	private static boolean outlines = true;
	
	public Renderer(Main m)
	{
		instance = this;
		main = m;
		try
		{
			initScreen();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		GL11.glViewport(0, 0, 800, 600); 
		GL11.glOrtho(0, 800, 0, 600, -4, 4); 
		GL11.glMatrixMode(GL11.GL_MODELVIEW); 
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING); 
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslatef(0.375F, 0.375F, 0F); //Get the pixels exactly in line with the screen
	}
	
	private void initScreen() throws Exception
	{
		if(main.applet)
		{
			//MainApplet.startLWJGL();
			//Display.setFullscreen(true);
		    DisplayMode[] modes = Display.getAvailableDisplayModes();
		    boolean displayModeSet = false;
		    for(DisplayMode mode : modes)
		    {
		    	if(displayModeSet)
		    		continue;
		    	if(mode.getHeight() == 600 && mode.getWidth() == 800)
		    	{
		    		Display.setDisplayMode(mode);
		    		displayModeSet = true;
		    	}
		    }
		    if(!displayModeSet)
		    {
		    	Display.setDisplayMode(modes[0]);
		    	main.screenHeight = modes[0].getHeight();
		    	main.screenWidth = modes[0].getWidth();
		    }
			return;
		}
		
		Display.create();
		Display.setTitle(Main.title);
		Display.setFullscreen(false);
		Display.setVSyncEnabled(false);
	    DisplayMode[] modes = Display.getAvailableDisplayModes();
	    boolean displayModeSet = false;
	    for(DisplayMode mode : modes)
	    {
	    	if(displayModeSet)
	    		continue;
	    	if(mode.getHeight() == main.screenHeight && mode.getWidth() == main.screenWidth)
	    	{
	    		Display.setDisplayMode(mode);
	    		displayModeSet = true;
	    	}
	    }
	    if(!displayModeSet)
	    {
	    	Display.setDisplayMode(modes[0]);
	    	main.screenHeight = modes[0].getHeight();
	    	main.screenWidth = modes[0].getWidth();
	    }
	}
	
	public void renderScreen()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glPushMatrix();
		if(main.currentLevel != null)
		{
			drawPolygon(new Point[] {new Point(0, 0), new Point(0, 600), new Point(800, 600), new Point(800, 0) }, 0x387AB5);
			GL11.glTranslatef(-main.controller.screenX, -main.controller.screenY, 0);
			drawPolygon(new Point[] {new Point(0, 0), new Point(0, main.currentLevel.height), new Point(main.currentLevel.width, main.currentLevel.height), new Point(main.currentLevel.width, 0) }, main.currentLevel.backgroundColour);

			for(int i = 0; i < 2; i++)
			{
				for(Entity entity : main.currentLevel.entities)
				{
					renderEntity(entity, i);
				}
			}
			for(int i = 0; i < main.currentLevel.trees.length; i++)
			{
				drawPolygon(main.currentLevel.trees[i], 40, main.currentLevel.levelNumber + 2, main.currentLevel.treeAngles[i], main.currentLevel.treeColour);
			}
		}
		GL11.glPopMatrix();
		drawPolygon(new Point[] {new Point(0, 0), new Point(0, 600), new Point(800, 600), new Point(800, 0) }, 0xffffffff - main.whiteness * 0x16000000);
	}
	
	private void renderEntity(Entity entity, int i)
	{
		if(entity.renderPass != i)
			return;
		if(entity instanceof EntityCreature)
		{
			Creature creature = ((EntityCreature)entity).creature;
			if(entity instanceof EntitySnowflakeBoss)
			{
				renderSnowflake((EntitySnowflakeBoss)entity);
			}
			if(creature instanceof CreaturePlayer)
				renderPlayer((EntityCreature)entity, (CreaturePlayer)creature);
			else
				drawPolygon(new Point(entity.posX, entity.posY), entity.drawRadius, entity.sides, entity.angle, entity.colour);
		}
		else if(entity instanceof EntitySword)
		{
			for(int j = 0; j < entity.sides; j++)
			{
				drawPolygon(new Point(entity.posX + (j * 10 + 30) * (float)Math.sin((entity.angle / 360F) * 2F * 3.14159265F), entity.posY + (j * 10 + 30) * (float)Math.cos((entity.angle / 360F) * 2F * 3.14159265F)), 5, entity.sides, entity.angle, entity.colour);

			}
		}
		else
		{
			drawPolygon(new Point(entity.posX, entity.posY), entity.drawRadius, entity.sides, entity.angle, entity.colour);
		}
	}
	
	private void renderPlayer(EntityCreature entity, CreaturePlayer creature)
	{
		if(creature.currentPart() != null)
			drawLine(new Point(entity.posX, entity.posY), new Point(main.controller.mapX, main.controller.mapY), creature.currentPart().colour);
		
		drawPolygon(new Point(entity.posX, entity.posY), creature.radius, creature.sides, entity.angle + ((float)creature.currentlySelectedSide + 0.5F / (float)creature.sides + creature.swingProgress) * 360F, creature.colour);
		for(int i = 0; i < creature.sides; i++)
		{
			if(creature.bodyParts[i] == null)
				continue;
			float edgeDistance = creature.radius * (float)Math.sin((3.14159265F * (1F - 2F / (float)creature.sides)) / 2);
			drawPolygon(new Point(entity.posX + edgeDistance * (float)Math.sin((entity.angle / 360F + ((float)creature.currentlySelectedSide / (float)creature.sides + creature.swingProgress) + ((float)i) / (float)creature.sides) * 2F * 3.14159265F), entity.posY + edgeDistance * (float)Math.cos((entity.angle / 360F + ((float)creature.currentlySelectedSide / (float)creature.sides + creature.swingProgress) + ((float)i) / (float)creature.sides) * 2F * 3.14159265F)), creature.bodyParts[i].radius, creature.bodyParts[i].sides, entity.angle + (((float)creature.currentlySelectedSide + (float)i) / (float)creature.sides + 0.5F / (float)creature.bodyParts[i].sides + creature.swingProgress) * 360F, creature.bodyParts[i].colour);
		}
	}
	
	public void renderSnowflake(EntitySnowflakeBoss boss)
	{
		if(!boss.halfLife){
			drawPolygon(new Point(boss.posX + 70*(float)Math.sin(Math.PI*boss.angle/180), boss.posY + 70*(float)Math.cos(Math.PI*boss.angle/180)), (boss.radius)/3F, 3, 180 + boss.angle, boss.colour);
			drawPolygon(new Point(boss.posX + 70*(float)Math.sin(Math.PI*(boss.angle + 60)/180), boss.posY + 70*(float)Math.cos(Math.PI*(boss.angle + 60)/180)), (boss.radius)/3F, 3, 240 + boss.angle, boss.colour);
			drawPolygon(new Point(boss.posX + 70*(float)Math.sin(Math.PI*(boss.angle + 120)/180), boss.posY + 70*(float)Math.cos(Math.PI*(boss.angle + 120)/180)), (boss.radius)/3F, 3, 300 + boss.angle, boss.colour);
			drawPolygon(new Point(boss.posX + 70*(float)Math.sin(Math.PI*(boss.angle + 180)/180), boss.posY + 70*(float)Math.cos(Math.PI*(boss.angle + 180)/180)), (boss.radius)/3F, 3, boss.angle, boss.colour);
			drawPolygon(new Point(boss.posX + 70*(float)Math.sin(Math.PI*(boss.angle + 240)/180), boss.posY + 70*(float)Math.cos(Math.PI*(boss.angle + 240)/180)), (boss.radius)/3F, 3, 60 + boss.angle, boss.colour);
			drawPolygon(new Point(boss.posX + 70*(float)Math.sin(Math.PI*(boss.angle + 300)/180), boss.posY + 70*(float)Math.cos(Math.PI*(boss.angle + 300)/180)), (boss.radius)/3F, 3, 120 + boss.angle, boss.colour);
		}
		drawPolygon(new Point(boss.posX, boss.posY), boss.radius, 3, boss.angle, boss.colour);
		drawPolygon(new Point(boss.posX, boss.posY), boss.radius, 3, boss.angle + 180F, boss.colour);
	}
	
	
	public void drawPolygon(Point centre, float radius, int sides, float angle, int colour)
	{
		colourise(colour);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glBegin(GL11.GL_POLYGON);
		for(int i = 0; i < sides; i++)
		{
			GL11.glVertex2f(centre.x + radius * (float)Math.sin((angle / 360F + (float)i / (float)sides) * 2F * 3.14159265F), centre.y + radius * (float)Math.cos((angle / 360F + (float)i / (float)sides) * 2F * 3.14159265F));
		}
		GL11.glEnd();
		
		if(outlines)
		{
			colourise(0x000000);
			for(int i = 0; i < sides; i++)
			{		
				GL11.glLineWidth(2.0F);
				GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(centre.x + radius * (float)Math.sin((angle / 360F + (float)i / (float)sides) * 2F * 3.14159265F), centre.y + radius * (float)Math.cos((angle / 360F + (float)i / (float)sides) * 2F * 3.14159265F));
				GL11.glVertex2f(centre.x + radius * (float)Math.sin((angle / 360F + (float)((i + 1) % sides) / (float)sides) * 2F * 3.14159265F), centre.y + radius * (float)Math.cos((angle / 360F + (float)((i + 1) % sides) / (float)sides) * 2F * 3.14159265F));
				GL11.glEnd();
			}
		}
	}
	
	public void drawLine(Point begin, Point end, int colour)
	{
		colourise(colour);
		GL11.glLineWidth(2.0F);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(begin.x, begin.y);
		GL11.glVertex2f(end.x, end.y);
		GL11.glEnd();
	}
	
	public void drawPolygon(Point[] points, int colour)
	{
		colourise(colour);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glBegin(GL11.GL_POLYGON);
		for(int i = 0; i < points.length; i++)
		{
			GL11.glVertex2f(points[i].x, points[i].y);
		}
		GL11.glEnd();
		
		if(outlines)
		{
			colourise(0x000000);
			for(int i = 0; i < points.length; i++)
			{				
				GL11.glLineWidth(2.0F);
				GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(points[i].x, points[i].y);			
				GL11.glVertex2f(points[((i + 1) % points.length)].x, points[((i + 1) % points.length)].y);
				GL11.glEnd();
			}
		}
	}
	
	public void colourise(int colour)
	{
		float alpha = ((colour >> 24) & 0xff) / 255F;
		float red = ((colour >> 16) & 0xff) / 255F;
		float green = ((colour >> 8) & 0xff) / 255F;
		float blue = (colour & 0xff) / 255F;
		GL11.glColor4f(red, green, blue, 1.0F - alpha);
	}
}

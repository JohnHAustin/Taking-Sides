package org.flan.LD26;

import org.flan.LD26.entity.CreaturePlayer;
import org.flan.LD26.entity.EntityCreature;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Controller 
{
	private Main main;
	
	public float screenX = 4000, screenY = 3000;
	public float mapX, mapY;
	
	public static int keyUp = Keyboard.KEY_W;
	public static int keyDown = Keyboard.KEY_S;
	public static int keyLeft = Keyboard.KEY_A;
	public static int keyRight = Keyboard.KEY_D;
	public static int keyAltUp = Keyboard.KEY_UP;
	public static int keyAltDown = Keyboard.KEY_DOWN;
	public static int keyAltLeft = Keyboard.KEY_LEFT;
	public static int keyAltRight = Keyboard.KEY_RIGHT;
	public static int keyWepPlus = Keyboard.KEY_E;
	public static int keyWepMinus = Keyboard.KEY_Q;
	
	public static int keyPause = Keyboard.KEY_P;
	
	private static int moveWepDelay;
	
	
	
	public Controller(Main m)
	{
		main = m;
	}
	
	public void tick()
	{
		if((Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(keyPause)) && main.pauseDelay < 0)
		{
			main.paused = !main.paused;
			main.pauseDelay = 10;
		}
		if(main.paused)
			return;
		Keyboard.poll();
		Mouse.poll();
		moveWepDelay--;
		if(main.currentLevel != null && main.currentLevel.playerEntity != null)
		{
			EntityCreature player = main.currentLevel.playerEntity;
			CreaturePlayer playerCreature = (CreaturePlayer)player.creature;
			
			screenX += (player.posX - 400 - screenX) / 2;
			screenY += (player.posY - 300 - screenY) / 2;
			
			int moveUp = 0;
			int moveRight = 0;
			
			if(Keyboard.isKeyDown(keyUp) || Keyboard.isKeyDown(keyAltUp))
				moveUp++;
			if(Keyboard.isKeyDown(keyDown) || Keyboard.isKeyDown(keyAltDown))
				moveUp--;
			if(Keyboard.isKeyDown(keyLeft) || Keyboard.isKeyDown(keyAltLeft))
				moveRight--;
			if(Keyboard.isKeyDown(keyRight) || Keyboard.isKeyDown(keyAltRight))
				moveRight++;
			
			player.motionX += moveRight * playerCreature.actualSpeed;
			player.motionY += moveUp * playerCreature.actualSpeed;
			
			int moveWep = 0;
			if(Keyboard.isKeyDown(keyWepMinus))
				moveWep++;
			if(Keyboard.isKeyDown(keyWepPlus))
				moveWep--;
			int dWheel = Mouse.getDWheel();
			if(dWheel > 0)
				moveWep = 1;
			if(dWheel < 0)
				moveWep = -1;
	
			if(moveWep != 0 && moveWepDelay < 0)
			{
				moveWepDelay = 4;
				playerCreature.currentlySelectedSide += moveWep;
				playerCreature.swingProgress = -(float)moveWep / (float)playerCreature.sides;
				if(playerCreature.currentlySelectedSide < 0)
					playerCreature.currentlySelectedSide += playerCreature.sides;
				if(playerCreature.currentlySelectedSide >= playerCreature.sides)
					playerCreature.currentlySelectedSide -= playerCreature.sides;
			}
			

			
			int x = Mouse.getX();
			int y = Mouse.getY();
			
			mapX = screenX + x;
			mapY = screenY + y;
			
			player.angle = ((float)Math.atan2(mapX - player.posX, mapY - player.posY) * 180F / 3.14159265F);

			for(int i = 0; i < 2; i++)
			{
				if(Mouse.isButtonDown(i) && playerCreature.bodyParts[(playerCreature.sides - playerCreature.currentlySelectedSide) % playerCreature.sides] != null)
				{
					playerCreature.bodyParts[(playerCreature.sides - playerCreature.currentlySelectedSide) % playerCreature.sides].onMouseHeld(player, mapX, mapY, i);
				}
			}
			
		}
		/*
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Main.stopGame();
		*/
	}
}

package org.flan.LD26.world;

import java.util.ArrayList;
import java.util.Random;

import org.flan.LD26.Main;
import org.flan.LD26.SoundManager;
import org.flan.LD26.entity.Entity;
import org.flan.LD26.entity.EntityCreature;
import org.flan.LD26.entity.EntityFireSlime;
import org.flan.LD26.entity.EntitySlime;
import org.flan.LD26.entity.EntityWorm;
import org.flan.LD26.render.Point;

public class Level 
{
	public Main main;
	public Random rand = new Random();
	public int backgroundColour = 0x99BC56;
	public int treeColour = 0x497228;
	public int levelNumber;
	public int bladeUpgradesSpawned = 0;
	public int gunUpgradesSpawned = 0;
	public int teleportUpgradesSpawned = 0;
	public int scatterGunUpgradesSpawned = 0;
	public int fireRingUpgradesSpawned = 0;
	public int height = 3000, width = 4000;
	public int songTimer = -100;
	public boolean bossLevel;
	public Point[] trees;
	public float[] treeAngles;
	public EntityCreature playerEntity;
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> entitiesToSpawn = new ArrayList<Entity>();
	
	public Level(Main m, int num, boolean boss)
	{
		main = m;
		levelNumber = num;
		bossLevel = boss;
		if(!boss)
			generateTrees();
		playerEntity = new EntityCreature(this, 2000, 1500, main.thePlayer);
		playerEntity.sides = main.thePlayer.sides;
		playerEntity.fireTimer = 0;
		playerEntity.creature.baseSpeed = playerEntity.creature.actualSpeed = playerEntity.sides;
		spawnEntity(playerEntity);
	}
	
	private void generateTrees()
	{
		trees = new Point[height * width / 100000];
		treeAngles = new float[height * width / 100000];
		for(int i = 0; i < height * width / 100000; i++)
		{
			trees[i] = new Point(rand.nextInt(width - 80) + 40, rand.nextInt(height - 80) + 40);
			treeAngles[i] = rand.nextFloat() * 360F;
		}
		
		int numSlimes = 0;
		int numWorms = 0;
		int numFireSlimes = 0;
		
		if(levelNumber == 1)
		{
			numSlimes = 200;
			numWorms = 7;
			numFireSlimes = 0;
		}
		if(levelNumber == 2)
		{
			numSlimes = 100;
			numWorms = 20;
			numFireSlimes = 10;
		}
		if(levelNumber == 3)
		{
			numSlimes = 50;
			numWorms = 12;
			numFireSlimes = 150;
		}
		
		for(int i = 0; i < numSlimes; i++)
		{
			EntitySlime slime = new EntitySlime(this, rand.nextInt(width), rand.nextInt(height));
			spawnEntity(slime);
		}
		
		for(int i = 0; i < numWorms; i++)
		{
			EntityWorm worm = new EntityWorm(this, rand.nextInt(width), rand.nextInt(height), levelNumber + 2, rand.nextInt(10) + 10, rand.nextInt(30) + 10);
			spawnEntity(worm);
		}
		
		for(int i = 0; i < numFireSlimes; i++)
		{
			EntityFireSlime slime = new EntityFireSlime(this, rand.nextInt(width), rand.nextInt(height));
			spawnEntity(slime);
		}
	}
	
	public void update()
	{
		entities.addAll(entitiesToSpawn);
		entitiesToSpawn = new ArrayList<Entity>();
		ArrayList<Entity> deadEntities = new ArrayList<Entity>();
		songTimer++;
		if(songTimer % 1600 == 0){
			if(rand.nextBoolean()) SoundManager.playMusic("LD26retheme", 1F, 1F);
		else SoundManager.playMusic("LD26Ambient", 1F, 1F);
		}
		for(Entity entity : entities)
		{
			entity.update();
			if(entity.isDead)
				deadEntities.add(entity);
		}
		entities.removeAll(deadEntities);
	}
	
	public void spawnEntity(Entity entity)
	{
		entitiesToSpawn.add(entity);
	}
}

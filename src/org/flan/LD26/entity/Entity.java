package org.flan.LD26.entity;

import java.util.Random;

import org.flan.LD26.world.Level;

public class Entity 
{
	public static Random rand = new Random();
	public Level level;
	public float posX, posY;
	public float collRadius;
	public float motionX, motionY;
	public float angle;
	public boolean isDead = false;
	public boolean collides = true;
	public int ticksAlive;
	public int renderPass = 1;
	public int fireProofing = 0;
	public boolean isOnFire = false;
	
	public int sides;
	public int colour = 0xffffff;
	public float drawRadius;
	
	public Entity(Level l, float x, float y)
	{
		level = l;
		setPosition(x, y);
	}
	
	public void update()
	{
		ticksAlive++;
		setPosition(posX + motionX, posY + motionY);
		for(Entity entity : level.entities)
		{
			float dX = entity.posX - posX;
			float dY = entity.posY - posY;
			float dist = (float)Math.sqrt(dX * dX + dY * dY);
			if(dist < entity.collRadius + collRadius)
				onCollidedWithEntity(entity);
		}
		if(isOnFire && ticksAlive % 4 == 0)
		{
			level.spawnEntity(new EntityFireParticle(this));
			if(fireProofing == 0)
				attackEntityFrom(this, 1);
		}
	}
	
	public void setPosition(float x, float y)
	{
		//TODO : Collision?
		if(collides)
		{
			if(x < collRadius)
				x = collRadius;
			if(x > level.width - collRadius)
				x = level.width - collRadius;
			if(y < collRadius)
				y = collRadius;
			if(y > level.height - collRadius)
				y = level.height - collRadius;
		}
		posX = x;
		posY = y;
	}
	
	public void onCollidedWithEntity(Entity entity)
	{
		
	}
	
	public void attackEntityFrom(Entity attacker, int damage)
	{
		
	}
	
	public void setDead()
	{
		if(!isDead)
		{
			isDead = true;
			dropItems();
		}
	}
	
	public void dropItems()
	{
		
	}
}

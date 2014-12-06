package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;
import org.flan.LD26.world.BossLevel;
import org.flan.LD26.world.Level;

public class EntityPowerup extends Entity 
{
	public int type; //0 = health, 1 = 
	public int radius;
	
	public static final int[] colours = new int[] { 0xff0000, 0x00ff00, 0xffffff };
	public static final int[] sideses = new int[] { 4 , 4, 3 };
	public static final int[] radii = new int[] { 10, 10, 20 };
	
	public EntityPowerup(Level l, float x, float y, int i) 
	{
		super(l, x, y);
		type = i;
		drawRadius = radius = radii[i];
		collRadius = 20;
		colour = colours[i];
		sides = sideses[i];
	}

	@Override
	public void update()
	{
		super.update();
		angle += 3F;
		drawRadius = radius + 3 * (float)Math.sin((float)ticksAlive / 20F);
		
		if(ticksAlive % 4 == 0)
		{
			EntityFireParticle particle = new EntityFireParticle(this);
			particle.colour = 0xffffff;
			level.spawnEntity(particle);
		}
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if(entity == level.playerEntity)
		{
			CreaturePlayer player = (CreaturePlayer)level.playerEntity.creature;
			switch(type)
			{
			case 0 : //health
			{
				player.health += 50; 
				if(player.health > player.maxHealth)
					player.health = player.maxHealth;
				setDead();
				SoundManager.play("LD26PickupB", 1F, 1F);
				break;
			}
			case 1 : //speed
			{
				player.actualSpeed += 2;
				setDead();
				SoundManager.play("LD26PickupB", 1F, 1F);
				break;
			}
			case 2 : //level up
			{
				if(!level.bossLevel) 
				{
					level.main.currentLevel = new BossLevel(level.main, level.levelNumber);
					player.addSides(1);
				}
				else level.main.currentLevel = new Level(level.main, level.levelNumber + 1, false);
				level.main.whiteness = 40;
				setDead();
				SoundManager.play("LD26PickupA", 1F, 1F);
				break;
			}
			}
		}
	}
}

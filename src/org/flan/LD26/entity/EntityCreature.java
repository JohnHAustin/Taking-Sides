package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;
import org.flan.LD26.world.Level;

public class EntityCreature extends Entity
{
	public Creature creature;
	public boolean replace = true;
	public int takeKnockback = 10;
	public int bloodColour = 0xff0000;
	public int bloodAmount = 10;
	
	public EntityCreature(Level l, float x, float y, Creature c)
	{
		super(l, x, y);
		creature = c;
		sides = 3;
		collRadius = 15;
	}
	
	public void update()
	{
		super.update();
		creature.update(this);
		motionX *= 0.5F;
		motionY *= 0.5F;
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		 
	}
	
	@Override
	public void attackEntityFrom(Entity attacker, int damage)
	{
		float dX = posX - attacker.posX;
		float dY = posY - attacker.posY;
		float dist = (float)Math.sqrt(dX * dX + dY * dY);
		motionX += takeKnockback * dX / (dist + 1);
		motionY += takeKnockback * dY / (dist + 1);
		
		SoundManager.play("LD26Hurt", 1F, 1F);
		
		for(int i = 0; i < rand.nextInt(bloodAmount); i++)
		{
			EntityBloodSplat splat = new EntityBloodSplat(this, 0, 0);
			if(attacker instanceof EntityBullet)
				splat = new EntityBloodSplat(this, attacker.motionX, attacker.motionY);
			level.spawnEntity(splat);
		}
		
		creature.health -= damage;
		if(creature.health <= 0){
			setDead();
			if(creature instanceof CreaturePlayer) 
			{
				SoundManager.playMusic("LD26GameOver", 1F, 1F);
				level.main.thePlayer = new CreaturePlayer();
				level.main.currentLevel = new Level(level.main, 1, false);
				level.main.whiteness = 40;
			}
		}
	}
}

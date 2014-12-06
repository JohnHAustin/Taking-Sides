package org.flan.LD26.entity;

public class EntityBloodSplat extends Entity
{
	EntityCreature eCreature;
	
	public EntityBloodSplat(EntityCreature e, float dX, float dY) 
	{
		super(e.level, e.posX, e.posY);
		eCreature = e;
		colour = e.bloodColour;
		collides = true;
		drawRadius = Math.max(5, e.drawRadius / 10);
		sides = e.sides;
		angle = rand.nextFloat() * 360F;
		renderPass = 0;
		motionX = ((float)rand.nextGaussian() / 5 + dX * rand.nextFloat() / 10) * e.collRadius;
		motionY = ((float)rand.nextGaussian() / 5 + dY * rand.nextFloat() / 10) * e.collRadius;
	}

	public void update()
	{
		super.update();
		motionX *= 0.5F;
		motionY *= 0.5F;
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if(entity instanceof EntityCreature && ((EntityCreature)entity).healable)
		{
			Creature creature = ((EntityCreature)entity).creature;
			if(!eCreature.equals(entity) || creature instanceof CreaturePlayer)
			{
				creature.health += 4;
				if(creature instanceof CreaturePlayer && creature.health > creature.maxHealth)
						creature.health = creature.maxHealth;
				setDead();
			}
		}
	}
}

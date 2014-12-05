package org.flan.LD26.entity;

public class EntityBloodSplat extends Entity
{
	public static int bloodLife = 100;
	
	public EntityBloodSplat(EntityCreature e, float dX, float dY) 
	{
		super(e.level, e.posX, e.posY);
		colour = e.bloodColour;
		collRadius = 0;
		collides = true;
		drawRadius = e.drawRadius / 10;
		sides = e.sides;
		angle = rand.nextFloat() * 360F;
		renderPass = 0;
		if(drawRadius < 5)
			drawRadius = 5;
		motionX = ((float)rand.nextGaussian() / 5 + dX * rand.nextFloat() / 10) * e.collRadius;
		motionY = ((float)rand.nextGaussian() / 5 + dY * rand.nextFloat() / 10) * e.collRadius;
	}

	public void update()
	{
		super.update();
		motionX *= 0.5F;
		motionY *= 0.5F;
		if(bloodLife > 0 && ticksAlive >= bloodLife)
			setDead();
	}
}

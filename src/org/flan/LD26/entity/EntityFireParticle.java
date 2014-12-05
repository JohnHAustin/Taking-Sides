package org.flan.LD26.entity;

public class EntityFireParticle extends Entity
{
	public static int life = 10;
	
	public EntityFireParticle(Entity e) 
	{
		super(e.level, e.posX + (float)Entity.rand.nextGaussian() * 10F, e.posY + (float)Entity.rand.nextGaussian() * 10F);
		colour = Entity.rand.nextBoolean() ? 0x000000 : 0xff8800;
		collRadius = 0;
		collides = false;
		drawRadius = e.drawRadius / 10;
		sides = e.sides;
		angle = rand.nextFloat() * 360F;
		renderPass = 1;
		if(drawRadius < 5)
			drawRadius = 5;
		motionX = (float)rand.nextGaussian() / 5F;
		motionY = (float)rand.nextFloat() * 2F;
	}
	
	public void update()
	{
		super.update();
		motionX += (float)rand.nextGaussian() * collRadius / 5F;
		if(ticksAlive >= life && rand.nextInt(2) == 0)
			setDead();
	}
}

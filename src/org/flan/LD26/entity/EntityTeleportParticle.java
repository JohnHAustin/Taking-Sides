package org.flan.LD26.entity;

import org.flan.LD26.world.Level;

public class EntityTeleportParticle extends Entity 
{
	public static final int life = 20;
	
	public EntityTeleportParticle(Level l, float x, float y, int s) 
	{
		super(l, x, y);
		colour = 0x00FFFA;
		sides = s;
		collides = false;
		drawRadius = 5;
		angle = rand.nextFloat() * 360F;
		renderPass = 0;
	}

	@Override
	public void update()
	{
		super.update();
		if(ticksAlive >= life && rand.nextInt(4) == 0)
			setDead();
		motionX += rand.nextGaussian();
		motionY += rand.nextGaussian();
	}
}

package org.flan.LD26.entity;

public class EntitySlimeBall extends Entity 
{
	public EntitySlime slime;
	public static final int life = 100; 
	
	public EntitySlimeBall(EntitySlime slime, Entity attacked) 
	{
		super(slime.level, slime.posX, slime.posY);
		this.slime = slime;
		colour = slime.colour;
		drawRadius = slime.drawRadius / 5;
		collRadius = drawRadius;
		sides = slime.sides;
		renderPass = 0;
		angle = rand.nextFloat() * 360F;
		motionX = (attacked.posX - slime.posX) / 10F;
		motionY = (attacked.posY - slime.posY) / 10F;
	}
	
	@Override
	public void update()
	{
		super.update();
		motionX *= 0.9F;
		motionY *= 0.9F;
		if(ticksAlive >= life)
			setDead();
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if(entity != slime)
		{
			entity.attackEntityFrom(this, sides);
			if(entity instanceof EntityCreature)
				setDead();
		}
	}

}

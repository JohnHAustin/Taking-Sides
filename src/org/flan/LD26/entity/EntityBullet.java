package org.flan.LD26.entity;

import org.flan.LD26.world.Level;

public class EntityBullet extends Entity 
{
	public Entity parent;
	public int life = 10;
	
	public EntityBullet(Level l, float x, float y, float angle, float bulletSpeed, int s) 
	{
		super(l, x, y);
		sides = s;
		colour = 0x222222;
		drawRadius = 6;
		collides = false;
		motionX = bulletSpeed * (float)Math.sin(angle * 3.14159265F / 180F);
		motionY = bulletSpeed * (float)Math.cos(angle * 3.14159265F / 180F);
		this.angle = angle;
	}
	
	public EntityBullet(EntityCreature p, float bulletSpeed, int s) 
	{
		super(p.level, p.posX, p.posY);
		parent = p;
		sides = s;
		colour = 0x222222;
		drawRadius = 6;
		collides = false;
		motionX = bulletSpeed * (float)Math.sin(p.angle * 3.14159265F / 180F);
		motionY = bulletSpeed * (float)Math.cos(p.angle * 3.14159265F / 180F);
		angle = p.angle + 180;
	}
	
	@Override
	public void update()
	{
		super.update();
		if(ticksAlive > life && rand.nextBoolean())
			setDead();
	}
	
	public void onCollidedWithEntity(Entity entity)
	{
		if(entity != parent)
		{
			entity.attackEntityFrom(this, sides * 2);
		}
	}
}

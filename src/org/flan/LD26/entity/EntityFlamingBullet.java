package org.flan.LD26.entity;

import org.flan.LD26.world.Level;

public class EntityFlamingBullet extends EntityBullet 
{

	public EntityFlamingBullet(Level l, float x, float y, float angle,
			float bulletSpeed, int s) 
	{
		super(l, x, y, angle, bulletSpeed, s);
		colour = 0xff8800;
		life = 20;
	}
	
	public EntityFlamingBullet(Level l, float x, float y, float angle,
			float bulletSpeed, int s, Entity entity) 
	{
		super(l, x, y, angle, bulletSpeed, s);
		parent = entity;
		colour = 0xff8800;
		life = 20;
	}

	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if(!entity.fireProof && entity != parent)
		{
			entity.attackEntityFrom(this, sides);
			entity.fireTimer += 30;
		}
	}
}

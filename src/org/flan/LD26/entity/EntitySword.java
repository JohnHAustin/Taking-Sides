package org.flan.LD26.entity;

public class EntitySword extends Entity 
{
	public EntityCreature player;
	public float startAngle;
	
	public EntitySword(EntityCreature p, float a, int s) 
	{
		super(p.level, p.posX, p.posY);
		player = p;
		angle = startAngle = a;
		sides = s;
		colour = 0xA6A8AD;
		collides = false;
		collRadius = 30 + 10 * sides;
	}
	
	@Override
	public void update()
	{
		super.update();
		setPosition(player.posX, player.posY);
		angle += 36F;
		if(angle >= startAngle + 360F)
			setDead();
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if((entity != player && ticksAlive % 4 == 0))
			entity.attackEntityFrom(player, sides);
	}

}

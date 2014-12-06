package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;

public class BodyPartFireRing extends BodyPart
{
	public int delay;

	public BodyPartFireRing(int s) 
	{
		super(0xff8800, s);
	}
	
	@Override
	public void onUpdate(EntityCreature creature)
	{
		if(delay > 0)
			delay--;
		radius = 10 - delay / 32; 
	}
	
	@Override
	public void onMouseHeld(EntityCreature creature, float x, float y, int button)
	{
		if(delay > 0)
			return;
		for(int i = 0; i < 30; i++)
		{
			SoundManager.play("LD26Flamethrower", 1F, 1F);
			EntityFlamingBullet bullet = new EntityFlamingBullet(creature.level, creature.posX + (float)Entity.rand.nextGaussian() * 5F, creature.posY + (float)Entity.rand.nextGaussian() * 5F, creature.angle + 12F * i, 20 + (float)Entity.rand.nextGaussian() * 2F, sides, creature);
			creature.level.spawnEntity(bullet);
		}
		delay = 200;
	}
}

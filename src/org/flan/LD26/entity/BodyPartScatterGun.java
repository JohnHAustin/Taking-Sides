package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;

public class BodyPartScatterGun extends BodyPart 
{
	public int delay;
	
	public BodyPartScatterGun(int s) 
	{
		super(0x0000ff, s);
	}
	
	@Override
	public void onUpdate(EntityCreature creature)
	{
		if(delay > 0)
			delay--;
		radius = 10 - delay / 5; 
	}
	
	@Override
	public void onMouseHeld(EntityCreature creature, float x, float y, int button)
	{
		if(delay > 0)
			return;
		int n = 0;
		while (n < creature.sides)
		{
			SoundManager.play("LD26ScatterGun", 1F, 1F);
			EntityBullet bullet = new EntityBullet(creature.level, creature.posX, creature.posY, creature.angle + n*(360F/(creature.sides)), Math.min(15 + sides, 30), sides);
			creature.level.spawnEntity(bullet);
			n++;
		}
		delay = 40;
	}

}

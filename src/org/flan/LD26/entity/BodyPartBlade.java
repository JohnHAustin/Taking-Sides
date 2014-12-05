package org.flan.LD26.entity;

public class BodyPartBlade extends BodyPart
{
	public int delay;
	
	public BodyPartBlade(int s) 
	{
		super(0xA6A8AD, s);
	}

	@Override
	public void onUpdate(EntityCreature creature)
	{
		if(delay > 0)
			delay--;
		radius = 10 - delay / 2; 
	}
	
	@Override
	public void onMouseHeld(EntityCreature creature, float x, float y, int button)
	{
		if(delay > 0)
			return;
		creature.level.spawnEntity(new EntitySword(creature, creature.angle, sides));
		
		delay = 10;
	}
}

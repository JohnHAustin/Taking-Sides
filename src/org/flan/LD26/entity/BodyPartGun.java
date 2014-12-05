package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;

public class BodyPartGun extends BodyPart 
{	
	public int delay;
	
	public BodyPartGun(int s)
	{
		super(0x222222, s);
	}
	
	@Override
	public void onUpdate(EntityCreature p)
	{
		if(delay > 0)
			delay--;
		radius = 10 - delay / 3; 
	}
	
	@Override
	public void onMouseHeld(EntityCreature p, float x, float y, int button)
	{
		if(delay > 0)
			return;
		SoundManager.play("LD26Gun", 1F, 2F);
		EntityBullet bullet = new EntityBullet(p, 30, sides);
		p.level.spawnEntity(bullet);
		delay = 20;
	}
}

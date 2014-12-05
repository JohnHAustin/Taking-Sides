package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;

public class BodyPartTeleport extends BodyPart 
{
	public int delay;
	
	public BodyPartTeleport(int s) 
	{
		super(0x00FFFA, s);
		delay = 10;
	}
	
	@Override
	public void onUpdate(EntityCreature p)
	{
		if(delay > 0)
			delay--;
		radius = 10 - delay / 10; 
	}

	
	@Override
	public void onMouseHeld(EntityCreature p, float x, float y, int button)
	{
		if(delay > 0)
			return;
		for(int i = 0; i < 20; i++)
		{
			EntityTeleportParticle tp = new EntityTeleportParticle(p.level, p.posX + (float)Entity.rand.nextGaussian() * 20F, p.posY + (float)Entity.rand.nextGaussian() * 20F, sides);
			p.level.spawnEntity(tp);
		}
		SoundManager.play("LD26Teleport", 1F, 1F);
		p.setPosition(x, y);
		for(int i = 0; i < 20; i++)
		{
			EntityTeleportParticle tp = new EntityTeleportParticle(p.level, x + (float)Entity.rand.nextGaussian() * 20F, y + (float)Entity.rand.nextGaussian() * 20F, sides);
			p.level.spawnEntity(tp);
		}
		delay = 10 * (10 - sides);
	}

}

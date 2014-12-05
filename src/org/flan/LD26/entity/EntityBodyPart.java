package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;
import org.flan.LD26.world.Level;

public class EntityBodyPart extends Entity 
{
	public BodyPart part;
	
	public EntityBodyPart(Level l, float x, float y, BodyPart p) 
	{
		super(l, x, y);
		part = p;
		drawRadius = 10;
		collRadius = 20;
		colour = part.colour;
		sides = part.sides;
	}

	@Override
	public void update()
	{
		super.update();
		angle += 3F;
		drawRadius = collRadius + 3 * (float)Math.sin((float)ticksAlive / 20F);
		
		if(ticksAlive % 4 == 0)
		{
			EntityFireParticle particle = new EntityFireParticle(this);
			particle.colour = 0xffffff;
			level.spawnEntity(particle);
		}
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if(entity == level.playerEntity)
		{
			CreaturePlayer player = (CreaturePlayer)level.playerEntity.creature;
			boolean absorbed = false;
			for(int i = 0; i < player.sides; i++)
			{
				BodyPart bodyPart = player.bodyParts[i];
				if(bodyPart != null && part.getClass() == bodyPart.getClass())
				{
					bodyPart.sides++;
					absorbed = true;
				}
			}
			if(!absorbed)
			{
				for(int i = 0; i < player.sides; i++)
				{
					if(player.bodyParts[i] == null)
					{
						player.bodyParts[i] = part;
						level.main.currentLevel = new Level(level.main, level.levelNumber + 1, false);
						level.main.whiteness = 40;
						player.maxHealth += 50;
						player.health += 50;
						
					}
				}
			}
			SoundManager.play("LD26PickupA", 1F, 1F);
			setDead();
		}
	}
}

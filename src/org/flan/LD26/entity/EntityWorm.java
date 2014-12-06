package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;
import org.flan.LD26.world.Level;

public class EntityWorm extends EntityCreature
{
	public boolean targetingPlayer;
	public int segment;
	public EntityWorm wormInFront;
	public EntityWorm wormBehind;
	public float speed;
	public int random;
	public int random2;
	public EntityWorm[] wormParts;
	
	public EntityWorm(Level l, float x, float y, int s, int length, int rad) 
	{
		super(l, x, y, new Creature(50));
		colour = 0x00ff00;
		collides = true;
		collRadius = drawRadius = rad;
		healable = false;
		wormParts = new EntityWorm[length];
		wormParts[0] = this;
		wormBehind = new EntityWorm(this, 1, length);
		random = rand.nextInt(20) + 10;
		random2 = rand.nextInt(30) + 10;
		sides = s;
		level.spawnEntity(wormBehind);
	}
	
	public EntityWorm(EntityWorm w, int i, int length)
	{
		super(w.level, w.posX + 10, w.posY + 10, new Creature(50));
		wormInFront = w;
		wormParts = w.wormParts;
		wormParts[i] = this;
		sides = wormInFront.sides;
		colour = wormInFront.colour;
		collides = true;
		collRadius = drawRadius = wormInFront.drawRadius;
		if(i < length - 1)
		{
			wormBehind = new EntityWorm(this, i + 1, length);
			level.spawnEntity(wormBehind);

		}
		random = rand.nextInt(20) + 10;
		random2 = rand.nextInt(30) + 10;
	}
	
	@Override
	public void update()
	{
		super.update();
		if(wormInFront != null && wormInFront.isDead){
			wormInFront = null;
			SoundManager.play("LD26Worm", 1F, 1F);
		}
		if(wormBehind != null && wormBehind.isDead)
			wormBehind = null;
		if(wormInFront != null)
		{
			float dX = wormInFront.posX - posX;
			float dY = wormInFront.posY - posY;
			float distance = (float)Math.sqrt(dX * dX + dY * dY);
			if(distance == 0)
			{
				dX = 1;
				dY = 0;
				distance = 1;
			}
			setPosition(wormInFront.posX - collRadius * dX / distance, wormInFront.posY - collRadius * dY / distance);
			angle = angle + (wormInFront.angle - angle) * 0.2F;
			return;
		}
		EntityCreature player = level.playerEntity;
		float dX = player.posX + 200F * (float)Math.cos((float)ticksAlive / random) * (float)Math.sin((float)ticksAlive / random2) - posX;
		float dY = player.posY + 200F * (float)Math.cos((float)ticksAlive / random) * (float)Math.cos((float)ticksAlive / random2) - posY;
		float distance = (float)Math.sqrt(dX * dX + dY * dY);
		if(targetingPlayer)
		{
			speed += 1F;
			speed *= 0.8F;
			motionX += speed * dX / (distance + 1F);
			motionY += speed * dY / (distance + 1F);
			setPosition(posX + speed * dX / (distance + 1F), posY + speed * dY / (distance + 1F));
		}
		else
		{
			speed *= 0.8F;
			motionX += 2F * rand.nextGaussian();
			motionY += 2F * rand.nextGaussian();
		}
		if(Math.sqrt(dX * dX + dY * dY) <= 250)
		{
			targetingPlayer = true;
		}
		if(Math.sqrt(dX * dX + dY * dY) >= 500)
		{
			targetingPlayer = false;
		}
		angle = ((float)Math.atan2(motionX, motionY) * 180F / 3.14159265F);
		motionX *= 0.9F;
		motionY *= 0.9F;
		
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if(entity == level.playerEntity)
			entity.attackEntityFrom(this, 4);
	}
	
	@Override
	public void dropItems()
	{
		boolean isPartStillLiving = false;
		boolean dropped = false;
		int randomNum = rand.nextInt(60);
		
		for(int i = 0; i < wormParts.length; i++)
		{
			if(wormParts[i] != null && !wormParts[i].isDead)
			{
				isPartStillLiving = true;
			}
		}
		
		if(!isPartStillLiving)
		{
			if(!level.bossLevel)
			{
				if(replace)
				{
					level.spawnEntity(new EntityWorm(level, rand.nextInt(level.width), rand.nextInt(level.height), sides, wormParts.length, (int)collRadius));
					replace = false;
				}
				else
					replace = true;
				
				if(!dropped && level.levelNumber == 1)
				{
					level.spawnEntity(new EntityPowerup(level, posX, posY, 2));
					dropped = true;
				}
				
				if(level.levelNumber >= 2)
				{
					if(!dropped && randomNum < 5 && level.main.thePlayer.bodyParts[0].sides + level.bladeUpgradesSpawned < level.levelNumber + 3)
					{
						level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartBlade(3)));
						++level.bladeUpgradesSpawned;
						dropped = true;
					}
					if(!dropped && randomNum < 9 && level.main.thePlayer.bodyParts[1].sides + level.gunUpgradesSpawned < level.levelNumber + 3)
					{
						level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartGun(3)));
						++level.gunUpgradesSpawned;
						dropped = true;
					}
					if(!dropped && randomNum < 11 && level.main.thePlayer.bodyParts[2].sides + level.teleportUpgradesSpawned < level.levelNumber + 3)
					{
						level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartTeleport(3)));
						++level.teleportUpgradesSpawned;
						dropped = true;
					}
					if(!dropped && randomNum < 13 && level.main.thePlayer.bodyParts[3].sides + level.scatterGunUpgradesSpawned < level.levelNumber + 3)
					{
						level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartScatterGun(3)));
						++level.scatterGunUpgradesSpawned;
						dropped = true;
					}
				}
			}
		}
		if(!dropped && randomNum > 38)
		{
			level.spawnEntity(new EntityPowerup(level, posX, posY, rand.nextInt(2)));
			dropped = true;
		}
	}

}

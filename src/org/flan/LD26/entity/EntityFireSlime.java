package org.flan.LD26.entity;

import org.flan.LD26.world.Level;

public class EntityFireSlime extends EntitySlime 
{

	public EntityFireSlime(Level l, float x, float y)
	{
		super(l, x, y, new Creature(50));
		fireProof = true;
		colour = 0xff6E00;
		bloodColour = 0x000000;
		fireTimer = 1;
	}

	@Override
	public void dropItems()
	{
		boolean dropped = false;
		int randomNum = rand.nextInt(60);
		
		if(!level.bossLevel)
		{
			if(replace)
			{
				level.spawnEntity(new EntityFireSlime(level, rand.nextInt(level.width), rand.nextInt(level.height)));
				replace = false;
			}
			else
				replace = true;
			
			if(!dropped && level.levelNumber == 2)
			{
				level.spawnEntity(new EntityPowerup(level, posX, posY, 2));
				dropped = true;
			}
			
			if(level.levelNumber >= 3)
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
				if(!dropped && randomNum < 13 && level.main.thePlayer.bodyParts[4].sides + level.fireRingUpgradesSpawned < level.levelNumber + 3)
				{
					level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartFireRing(3)));
					++level.fireRingUpgradesSpawned;
					dropped = true;
				}
			}
		}
		
		if(!dropped && randomNum > 38)
		{
			level.spawnEntity(new EntityPowerup(level, posX, posY, rand.nextInt(2)));
			dropped = true;
		}
	}
	
	@Override
	public void attackEntityFrom(Entity attacker, int damage)
	{
		if(!(attacker instanceof EntityPentagonBoss))
			super.attackEntityFrom(attacker, damage);
	}
}

package org.flan.LD26.entity;

import org.flan.LD26.world.Level;

public class EntityFireSlime extends EntitySlime 
{

	public EntityFireSlime(Level l, float x, float y)
	{
		super(l, x, y, new Creature(50));
		fireProofing = 10;
		colour = 0xff6E00;
		bloodColour = 0x000000;
		fireTimer = 1;
	}

	@Override
	public void dropItems()
	{
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
			
			if(level.levelNumber == 2)
				level.spawnEntity(new EntityPowerup(level, posX, posY, 2));
			
			if(level.levelNumber >= 3)
			{
				if(randomNum < 5 && level.main.thePlayer.bodyParts[0].sides + level.bladeUpgradesSpawned < level.levelNumber + 3)
				{
					level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartBlade(3)));
					++level.bladeUpgradesSpawned;
				}
				if(randomNum < 9 && level.main.thePlayer.bodyParts[1].sides + level.gunUpgradesSpawned < level.levelNumber + 3)
				{
					level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartGun(3)));
					++level.gunUpgradesSpawned;
				}
				if(randomNum < 11 && level.main.thePlayer.bodyParts[2].sides + level.teleportUpgradesSpawned < level.levelNumber + 3)
				{
					level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartTeleport(3)));
					++level.teleportUpgradesSpawned;
				}
				if(randomNum < 13 && level.main.thePlayer.bodyParts[4].sides + level.fireRingUpgradesSpawned < level.levelNumber + 3)
				{
					level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartFireRing(3)));
				}
			}
		}
		
		if(randomNum > 38)
			level.spawnEntity(new EntityPowerup(level, posX, posY, rand.nextInt(2)));
	}
	
	@Override
	public void attackEntityFrom(Entity attacker, int damage)
	{
		if(attacker instanceof EntityPentagonBoss || attacker instanceof EntityFlamingBullet)
			return;
		super.attackEntityFrom(attacker, damage);
	}
}

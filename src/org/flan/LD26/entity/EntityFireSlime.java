package org.flan.LD26.entity;

import org.flan.LD26.world.Level;

public class EntityFireSlime extends EntitySlime 
{

	public EntityFireSlime(Level l, float x, float y) {
		super(l, x, y, new Creature(50));
		fireProofing = 10;
		colour = 0xff6E00;
		bloodColour = 0x000000;
		fireTimer = 1;
	}

	@Override
	public void dropItems()
	{
		if(!level.bossLevel && level.levelNumber == 2)
			level.spawnEntity(new EntityPowerup(level, posX, posY, 2));
		if(!level.bossLevel && level.levelNumber >= 3 && rand.nextInt(40) == 0)
			level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartFireRing(3)));
		if(!level.bossLevel && level.levelNumber >= 2 && rand.nextInt(20) == 0)
			level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartScatterGun(3)));
		
		if(rand.nextInt(5) == 0)
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

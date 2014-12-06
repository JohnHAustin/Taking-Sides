package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;
import org.flan.LD26.world.Level;

public class EntityPentagonBoss extends EntityCreature {

	public int radius = 75;
	/**
	 * This value between 0 and 100 determines how many ticks
	 * will pass before the boss fires.
	 */
	public int recharge = 100;
	public boolean halfLife = false;
	private float angularVelocity = 5;
	public int spawns = 0;
	public int numSpawns = 8;
	
	public EntityPentagonBoss(Level l, float x, float y) 
	{
		super(l, 400, 300, new Creature(2000));
		drawRadius = collRadius = radius;
		colour = 0xff8800;
		bloodColour = 0xff0000;
		takeKnockback = 0;
		sides = 4;
	}

	public void update()
	{
		super.update();	
		recharge --;
		angularVelocity = ((100 - recharge)*recharge) / 300F;
		angle += angularVelocity;
		if(recharge == 0) {
			for(int i = 0; i < 30; i++)
			{
				SoundManager.play("LD26Flamethrower", 1F, 1F);
				EntityFlamingBullet bullet = new EntityFlamingBullet(level, posX + (float)Entity.rand.nextGaussian() * 5F, posY + (float)Entity.rand.nextGaussian() * 5F, angle + 12F * i, 20 + (float)Entity.rand.nextGaussian() * 2F, sides, this);
				level.spawnEntity(bullet);
			}
			recharge = 100;
		}
		if(creature.health < creature.maxHealth * (numSpawns - spawns) / numSpawns){
			spawns++;
			for(int k = 0; k < 8; k++) level.spawnEntity(new EntityFireSlime(level, posX + 80*(float)Math.sin(Math.PI*k/4), posY + 80*(float)Math.cos(Math.PI*k/4)));
		}

		colour = 0xffff00 - (0xff * Math.abs(30 - recharge) / 60) * 0x100;
	}
	
	@Override
	public void attackEntityFrom(Entity entity, int damage)
	{
		if(entity instanceof EntityFireSlime)
			return;
		super.attackEntityFrom(entity, damage);
		SoundManager.play("LD26SlimeHurt", 0.8F, 1F);
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
		//level.spawnEntity(new EntityPowerup(level, posX + (float)rand.nextGaussian() * 20F, posY + (float)rand.nextGaussian() * 20F, 2));
		level.spawnEntity(new EntityBodyPart(level, posX + (float)rand.nextGaussian() * 20F, posY + (float)rand.nextGaussian() * 20F, new BodyPartFireRing(3)));

	}
}

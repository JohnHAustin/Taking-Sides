package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;
import org.flan.LD26.world.Level;

public class EntitySnowflakeBoss extends EntityCreature
{
	public int radius = 100;
	public int recharge = 160;
	public boolean halfLife = false;
	private float angularVelocity;
	
	public EntitySnowflakeBoss(Level l, float x, float y)
	{
		super(l, 400, 300, new Creature(800));
		collRadius = radius - 10;
		colour = 0xffffff;
		bloodColour = 0xff0000;
		takeKnockback = 1;
	}

	public void update()
	{
		super.update();	
		recharge --;
		if (recharge < 60) 
			angularVelocity += 3.142;
		angle += angularVelocity;
		float dX = level.playerEntity.posX - posX;
		float dY = level.playerEntity.posY - posY;
		float dist = (float)Math.sqrt((posX - 400) * (posX - 400) + (posY - 300) * (posY - 300));
		if(recharge < 60) {
			motionX = 8F*dX/((float)Math.sqrt(dX * dX + dY * dY) + 1F);
			motionY = 8F*dY/((float)Math.sqrt(dX * dX + dY * dY) + 1F);
		}
		else if(dist > 10){
			
			motionX += 10F*+(400 - posX)/(dist + 1);
			motionY += 10F*+(300 - posY)/(dist + 1);
		}
		if(creature.health < 400 && !halfLife){
			halfLife = true;
			for(int k = 0; k < 12; k++) level.spawnEntity(new EntitySlime(level, posX + 40*(float)Math.sin(Math.PI*k/6), posY + 40*(float)Math.cos(Math.PI*k/6)));
		}
		if(recharge == 0) {
			recharge = 160;
			angularVelocity = 0;
		}
		//colour = 0xffffff - (0xff * Math.abs(80 - recharge) / 80) * 0x100 - (0xff * Math.abs(80 - recharge) / 80);
		colour = 0xffffff - (0xff * Math.abs(80 - recharge) / 80) * 0x100;
	}
	
	@Override
	public void attackEntityFrom(Entity entity, int damage)
	{
		super.attackEntityFrom(entity, damage);
		SoundManager.play("LD26SlimeHurt", 1F, 1F);
	}
	
	@Override
	public void dropItems()
	{
		for(int n = 0; n < 12; n++) level.spawnEntity(new EntitySlime(level, posX + 40*(float)Math.sin(Math.PI*n/6), posY + 40*(float)Math.cos(Math.PI*n/6)));
		level.spawnEntity(new EntityBodyPart(level, posX + (float)rand.nextGaussian() * 20F, posY + (float)rand.nextGaussian() * 20F, new BodyPartScatterGun(3)));
	}
	
	@Override
	public void onCollidedWithEntity(Entity entity)
	{
		if(entity == level.playerEntity)
			entity.attackEntityFrom(this, 4);
	}
}

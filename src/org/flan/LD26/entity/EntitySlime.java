package org.flan.LD26.entity;

import org.flan.LD26.SoundManager;
import org.flan.LD26.render.Point;
import org.flan.LD26.world.Level;

public class EntitySlime extends EntityCreature 
{
	public Point jumpingFrom, jumpingTo;
	public float jump;
	public int jumpDelay = 20;
	public int radius;
	private float blobbiness;
	private float angularVelocity;
	private float slimeDelay;
		
	public EntitySlime(Level l, float x, float y, Creature c) 
	{
		super(l, x, y, c);
		jumpingFrom = jumpingTo = new Point(x, y);
		collRadius = radius = creature.maxHealth;
		sides = l.levelNumber + 2;
		int green = rand.nextInt(0x40);
		int blue = rand.nextInt(0x80);
		colour = 0xff2040 + green * 0x100 + blue;
		bloodColour = 0xff0000 + green / 2 * 0x100 + blue / 2;
	}
	
	public EntitySlime(Level l, float x, float y) 
	{
		this(l, x, y, new Creature(rand.nextInt(50) + 10));
	}

	public void update()
	{
		super.update();
		blobbiness += rand.nextGaussian();
		blobbiness *= 0.8F;		
		angularVelocity += rand.nextGaussian();
		angularVelocity *= 0.8F;
		angle += angularVelocity;
		if(jumpDelay == 0)
		{
			jumpingFrom = new Point(posX, posY);
			jumpingTo = new Point(jumpingFrom.x + (float)rand.nextGaussian() * radius, jumpingFrom.y + (float)rand.nextGaussian() * radius);
		}
		jumpDelay--;
		drawRadius = radius + blobbiness;
		if(jump < 1F && jumpDelay < 0)
		{
			drawRadius = radius * (1 + (jump * jump - jump)) + blobbiness;
			jump += 1F / radius;
			float jumpSmooth = (3 - 2 * jump) * jump * jump;
			setPosition(jumpingFrom.x + (jumpingTo.x - jumpingFrom.x) * jumpSmooth, jumpingFrom.y + (jumpingTo.y - jumpingFrom.y) * jumpSmooth);
		}
		float dX = level.playerEntity.posX - posX;
		float dY = level.playerEntity.posY - posY;
		if(jump >= 1F)
		{
			if(Math.sqrt((dX * dX + dY * dY)) <= 500 && rand.nextInt(6) == 0)
				SoundManager.play("LD26SlimeSquish", 1F, 1F);
			jump = 0F;
			jumpDelay = rand.nextInt(40);
		}
		
		if(slimeDelay > 0)
			slimeDelay--;

		if(slimeDelay <= 0 && Math.sqrt(dX * dX + dY * dY) <= 250)
		{
			level.spawnEntity(new EntitySlimeBall(this, level.playerEntity));
			slimeDelay = 20;
		}
	}
	
	@Override
	public void dropItems()
	{
		if(rand.nextInt(3) == 0)
			level.spawnEntity(new EntityPowerup(level, posX, posY, rand.nextInt(2)));
		if(!level.bossLevel)
		{
			if(rand.nextInt(40) == 0)
				level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartBlade(3)));
			if(rand.nextInt(60) == 0)
				level.spawnEntity(new EntityBodyPart(level, posX, posY, new BodyPartGun(3)));
			
			if(replace)
			{
				level.spawnEntity(new EntitySlime(level, rand.nextInt(level.width), rand.nextInt(level.height)));
				replace = false;
			}
			else
				replace = true;
		}
	}
	
	@Override
	public void attackEntityFrom(Entity entity, int damage)
	{
		super.attackEntityFrom(entity, damage);
		SoundManager.play("LD26SlimeHurt", 1F, 1F);
	}
}

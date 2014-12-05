package org.flan.LD26.world;

import java.util.ArrayList;

import org.flan.LD26.Main;
import org.flan.LD26.SoundManager;
import org.flan.LD26.entity.Entity;
import org.flan.LD26.entity.EntityPentagonBoss;
import org.flan.LD26.entity.EntitySnowflakeBoss;
import org.flan.LD26.render.Point;

public class BossLevel extends Level 
{
	public BossLevel(Main m, int num) 
	{
		super(m, num, true);
		height = 600;
		width = 800;
		trees = new Point[0];
		treeAngles = new float[0];
		songTimer = -1;
		if(num == 2)
			spawnEntity(new EntityPentagonBoss(this, 400, 300));
		else spawnEntity(new EntitySnowflakeBoss(this, 400, 300));
		playerEntity.setPosition(200, 300);
	}

	@Override
	public void update()
	{
		entities.addAll(entitiesToSpawn);
		entitiesToSpawn = new ArrayList<Entity>();
		ArrayList<Entity> deadEntities = new ArrayList<Entity>();
		songTimer++;
		if(songTimer % 1500 == 0) SoundManager.playMusic("LD26rereboss", 1F, 1F);
		for(Entity entity : entities)
		{
			entity.update();
			if(entity.isDead)
				deadEntities.add(entity);
		}
		entities.removeAll(deadEntities);
	}
}

package org.flan.LD26.entity;

public class Creature 
{
	public float baseSpeed = 3;
	public float actualSpeed = 3;
	public int maxHealth = 50;
	public int health = 50;
	
	public Creature()
	{
		
	}
	
	public Creature(int health)
	{
		maxHealth = this.health = health;
	}
	
	public void update(EntityCreature c)
	{
		actualSpeed = (actualSpeed - baseSpeed) * 0.98F + baseSpeed;
	}
}

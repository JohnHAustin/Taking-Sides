package org.flan.LD26.entity;

public class Creature 
{
	public float speed = 3;
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
		speed = (speed - 3) * 0.98F + 3;
	}
}

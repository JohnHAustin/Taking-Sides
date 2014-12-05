package org.flan.LD26.entity;

public class BodyPart 
{
	public int colour = 0xffffff;
	public int sides = 4;
	public float radius = 7;
	public float extension;
	
	public BodyPart(int c, int s)
	{
		colour = c;
		sides = s;
	}
	
	public void onUpdate(EntityCreature creature)
	{
		
	}
	
	public void onMouseHeld(EntityCreature creature, float x, float y, int button)
	{
		
	}
}

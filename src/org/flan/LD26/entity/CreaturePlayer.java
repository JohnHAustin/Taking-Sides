package org.flan.LD26.entity;

public class CreaturePlayer extends Creature 
{
	public int sides = 3;
	public int colour = 0xffffff;
	public int radius = 30;
	public int currentlySelectedSide;
	public float swingProgress;
	public BodyPart[] bodyParts;
	
	public CreaturePlayer()
	{
		bodyParts = new BodyPart[sides];
		bodyParts[0] = new BodyPartBlade(3);
		bodyParts[1] = new BodyPartGun(3);
		bodyParts[2] = new BodyPartTeleport(3);
		maxHealth = health = 200;
	}
	
	public void addSides(int numSides)
	{
		BodyPart[] newParts = new BodyPart[sides + numSides];
		for(int i = 0; i < sides; i++)
			newParts[i] = bodyParts[i];
		sides += numSides;
		bodyParts = newParts;
	}
	
	public void update(EntityCreature c)
	{
		super.update(c);
		swingProgress *= 0.5F;
		for(int i = 0; i < sides; i++)
		{
			if(bodyParts[i] != null)
				bodyParts[i].onUpdate(c);
		}
		colour = 0xff0000 + (0xff * health / maxHealth) * 0x100 + (0xff * health / maxHealth);
	}
	
	public BodyPart currentPart()
	{
		return bodyParts[(sides - currentlySelectedSide) % sides];
	}
}

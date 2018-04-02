package edu.bradley.cmcpartlin.tutorial.capabilities;

public interface IMaxMovement {
	float getBonusMaxMovement();
	void setBonusMaxMovement(float maxMovement);
	
	void addBonusMaxMovement(float movementToAdd);
	void synchronize();	
}

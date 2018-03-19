package edu.bradley.cmcpartlin.tutorial.capabilities;

//a capability to provide a max health bonus to an entity
public interface IMaxHealth {
	float getBonusHealth();
	void setBonusMaxHealth(float maxHealth);
	
	void addBonusMaxHealth(float healthToAdd);  //add health to entity
	void synchronize();							//synchronize with clients
	

}

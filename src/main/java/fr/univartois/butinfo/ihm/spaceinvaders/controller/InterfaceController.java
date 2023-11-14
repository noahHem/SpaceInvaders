package fr.univartois.butinfo.ihm.spaceinvaders.controller;

import fr.univartois.butinfo.ihm.spaceinvaders.model.AbstractMovable;
import fr.univartois.butinfo.ihm.spaceinvaders.model.GameGrid;
import fr.univartois.butinfo.ihm.spaceinvaders.model.SpaceInvadersGame;
import javafx.beans.property.IntegerProperty;

public interface InterfaceController {
	public void setGame(SpaceInvadersGame game);
	public void setGrille(GameGrid grille);
	public void addObjet(AbstractMovable objet);
	public void delObjet(AbstractMovable objet);
	public void endGame(String message);
	public void setScore(IntegerProperty score);
	public void setHealth(IntegerProperty health);
}

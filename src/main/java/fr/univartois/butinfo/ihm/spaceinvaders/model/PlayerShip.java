/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2022-2023 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.ihm.spaceinvaders.model;

import fr.univartois.butinfo.ihm.spaceinvaders.controller.SpaceInvadersController;
import javafx.scene.image.Image;

/**
 * La classe PlayerShip représente le vaisseau du joueur sur la grille du Space-Invaders.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class PlayerShip extends AbstractMovable {

    /**
     * Crée une nouvelle instance de PlayerShip.
     *
     * @param game Le jeu de Space-Invaders dans lequel l'objet se déplace.
     * @param sprite L'image représentant l'objet.
     */
    public PlayerShip(SpaceInvadersGame game, Image sprite) {
        super(game, sprite, 3);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.ihm.spaceinvaders.model.AbstractMovable#decrementHealth()
     */


    @Override
    public void decrementHealth() {
        super.decrementHealth();

        if (getHealth().get() == 0) {
            game.playerIsDead();
        }
    }

}

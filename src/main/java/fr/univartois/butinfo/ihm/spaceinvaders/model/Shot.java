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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * La classe Shot représente un tir se déplaçant sur la grille du jeu Space-Invaders.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Shot extends AbstractMovable {

    /**
     * La Timeline permettant à ce tir de se déplacer seul.
     */
    private Timeline timeline;

    /**
     * Le nombre de tuiles sur lesquelles ce tir passe à chaque étape de déplacement.
     * Le signe de ce nombre indique la direction du déplacement.
     */
    private final int tilesPerStep;

    /**
     * Crée une nouvelle instance de Shot.
     *
     * @param game Le jeu de Space-Invaders dans lequel l'objet se déplace.
     * @param sprite L'image représentant l'objet.
     * @param tilesPerStep Le nombre de tuiles sur lesquelles le tir passe à chaque
     *        étape de déplacement.
     *        Le signe de ce nombre indique la direction du déplacement.
     */
    public Shot(SpaceInvadersGame game, Image sprite, int tilesPerStep) {
        super(game, sprite, 1);
        this.tilesPerStep = tilesPerStep;
    }

    /**
     * Anime ce tir afin qu'il se déplace seul.
     */
    public void animate() {
        timeline = new Timeline(new KeyFrame(Duration.millis(250), e -> game.move(this)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Donne le nombre de tuiles sur lesquelles ce tir passe à chaque étape de
     * déplacement.
     *
     * @return Le nombre de tuiles sur lesquelles ce tir passe à chaque étape de
     *         déplacement.
     */
    public int getTilesPerStep() {
        return tilesPerStep;
    }

    /**
     * Arrête le déplacement de ce tir (par exemple, lorsqu'il atteint une cible ou les
     * limites de la grille).
     */
    public void stop() {
        timeline.stop();
    }

}

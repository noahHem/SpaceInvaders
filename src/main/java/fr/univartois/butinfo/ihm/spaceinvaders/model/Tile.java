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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * La classe Tile représente une tuile composant la grille du jeu Space-Invaders.
 * Une fois créée, une telle tuile reste fixe dans la grille : c'est son contenu qui
 * change au cours du jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Tile {

    /**
     * La ligne où cette tuile est positionnée sur la grille.
     */
    private final int row;

    /**
     * La colonne où cette tuile est positionnée sur la grille.
     */
    private final int column;

    /**
     * L'éventuel objet mobile qui peut se trouver sur cette tuile.
     */
    private final ObjectProperty<AbstractMovable> movableObject;

    /**
     * Crée une nouvelle instance de Tile.
     *
     * @param row La ligne où la tuile est positionnée sur la grille.
     * @param column La colonne où la tuile est positionnée sur la grille.
     */
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.movableObject = new SimpleObjectProperty<AbstractMovable>();
    }

    /**
     * Donne la ligne où cette tuile est positionnée sur la grille.
     *
     * @return La ligne où cette tuile est positionnée sur la grille.
     */
    public int getRow() {
        return row;
    }

    /**
     * Donne la colonne où cette tuile est positionnée sur la grille.
     *
     * @return La colonne où cette tuile est positionnée sur la grille.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Donne l'objet mobile se trouvant sur cette tuile.
     *
     * @return L'objet mobile sur cette tuile.
     */
    public AbstractMovable getMovable() {
        return movableObject.get();
    }

    /**
     * Modifie l'objet mobile se trouvant sur cette tuile.
     *
     * @param movableObject Le nouvel objet mobile sur cette tuile.
     */
    public void setMovable(AbstractMovable movableObject) {
        this.movableObject.set(movableObject);
    }

    /**
     * Retire l'objet mobile se trouvant actuellement sur cette tuile.
     */
    public void removeMovable() {
        this.movableObject.set(null);;
    }

    /**
     * Vérifie si cette tuile est vide, c'est-à-dire qu'aucun objet mobile ne se
     * trouve dessus.
     *
     * @return Si cette tuile est vide.
     */
    public boolean isEmpty() {
        return movableObject == null;
    }

    public ObjectProperty<AbstractMovable> getMovableProperty() {
    	return movableObject;
    }
}

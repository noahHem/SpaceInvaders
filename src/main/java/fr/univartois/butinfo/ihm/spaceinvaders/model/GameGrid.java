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

/**
 * La classe GameGrid représente la grille du jeu Space-Invaders, sur laquelle
 * les objets mobiles se déplacent.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class GameGrid {

    /**
     * Le nombre de lignes de tuiles dans cette grille.
     */
    private int height;

    /**
     * Le nombre de colonnes de tuiles dans cette grille.
     */
    private int width;

    /**
     * Les tuiles qui constituent cette grille.
     */
    private Tile[][] tiles;

    /**
     * Crée une nouvelle instance de GameGrid.
     *
     * @param width Le nombre de lignes de tuiles dans la grille.
     * @param height Le nombre de colonnes de tuiles dans la grille.
     */
    public GameGrid(int height, int width) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];
        init();
    }

    /**
     * Crée les tuiles qui constituent cette grille.
     */
    private void init() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    /**
     * Donne le nombre de lignes de tuiles dans cette grille.
     *
     * @return Le nombre de lignes de tuiles dans cette grille.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Donne le nombre de colonnes de tuiles dans cette grille.
     *
     * @return Le nombre de colonnes de tuiles dans cette grille.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Vérifie si une position se trouve sur cette grille.
     *
     * @param row L'indice de ligne à vérifier.
     * @param column L'indice de colonne à vérifier.
     *
     * @return Si la position se trouve bien sur la grille.
     */
    public boolean isOnGrid(int row, int column) {
        return ((0 <= row) && (row < height))
                && ((0 <= column) && (column < width));
    }

    /**
     * Donne une tuile à une position donnée.
     *
     * @param row La ligne de la tuile à récupérer.
     * @param column La colonne de la tuile à récupérer.
     *
     * @return La tuile à la position donnée.
     */
    public Tile get(int row, int column) {
        return tiles[row][column];
    }

}

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

import java.util.Random;

import fr.univartois.butinfo.ihm.spaceinvaders.controller.InterfaceController;
import fr.univartois.butinfo.ihm.spaceinvaders.controller.SpaceInvadersController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * La classe SpaceInvadersGame permet de gérer une partie du jeu Space-Invaders.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class SpaceInvadersGame {

    private SpaceInvadersController spaceController = new SpaceInvadersController();
	private InterfaceController interfaceController;
    /**
     * Le générateur de nombres aléatoires utilisé dans la partie.
     */
    private static final Random RANDOM = new Random();

    /**
     * L'objet permettant de créer les sprites représentant les objets mobiles du jeu.
     */
    private SpriteStore spriteStore;

    /**
     * La grille sur laquelle les objets du jeu se déplacent.
     */
    private GameGrid grid;

    /**
     * Le vaisseau du joueur.
     */
    private PlayerShip ship;

    /**
     * Le nombre d'aliens encore vivants.
     */
    private int nbRemainingAliens;

    /**
     * La ligne où les aliens se trouvent actuellement.
     */
    private int alienRow = 1;

    /**
     * La direction (gauche-droite) dans laquelle les aliens se dirigent.
     * Elle correspond à la valeur ajoutée à la colonne actuelle des aliens lors d'un
     * déplacement.
     */
    private int alienDirection = +1;

    /**
     * Le score du joueur (en nombre d'aliens éliminés).
     */
    private final IntegerProperty score = new SimpleIntegerProperty();

    
	public void setInterfaceController(InterfaceController interfaceController) {
		this.interfaceController = interfaceController;
	}
	

    public IntegerProperty getScore() {
		return score;
	}


	/**
     * Crée une nouvelle instance de SpaceInvadersGame.
     *
     * @param height La hauteur sur laquelle les objets du jeu peuvent se déplacer (en
     *        tuiles).
     * @param width La largeur sur laquelle les objets du jeu peuvent se déplacer (en
     *        tuiles).
     */
    public SpaceInvadersGame(int height, int width) {
        this.grid = new GameGrid(height, width);
        this.spriteStore = new SpriteStore();
    }

    /**
     * Prépare une partie de Space-Invaders sur le contrôleur de l'application.
     */
    public void prepare() {
        interfaceController.setGrille(grid);
        
    }

    /**
     * Démarre cette partie de Space-Invaders, en créant les différents objets présents au
     * début de la partie et pouvant se déplacer.
     */
    public void start() {
        // On crée d'abord le vaisseau du joueur.
        this.ship = new PlayerShip(this, spriteStore.createSprite("ship"));
        int row = grid.getHeight() - 3;
        int column = grid.getWidth() / 2;
        ship.setPosition(row, column);
        grid.get(row, column).setMovable(ship);

        // On ajoute ensuite les aliens.
        nbRemainingAliens = grid.getWidth() / 3;
        for (int i = 0; i < nbRemainingAliens; i++) {
            AlienShip alien = new AlienShip(this, spriteStore.createSprite("alien"));
            alien.setPosition(1, 2 * i);
            grid.get(1, 2 * i).setMovable(alien);
            alien.animate();
        }
        

    }

    /**
     * Déplace le vaisseau du joueur vers la gauche.
     */
    public void moveLeft() {
        moveTo(ship, ship.getRow(), ship.getColumn() - 1);
    }

    /**
     * Déplace le vaisseau du joueur vers la droite.
     */
    public void moveRight() {
        moveTo(ship, ship.getRow(), ship.getColumn() + 1);
    }

    /**
     * Déplace un vaisseau alien à sa prochaine position.
     *
     * @param alien Le vaisseau alien à déplacer.
     */
    public void move(AlienShip alien) {
        // On détermine la prochaine position de l'alien.
        int row = alienRow;
        int column = alien.getColumn();
        int nextColumn = column + alienDirection;

        // Si l'on est sur un bord, il faut un traitement particulier pour gérer le rebond.
        if (alien.getRow() != alienRow) {
            if (column == grid.getWidth() - 1) {
                nextColumn = column;
            } else if (column == 0) {
                nextColumn = 2;
            }
        }

        // Si l'on atteint un bord, tous les aliens changent de direction et descendent.
        if (!grid.isOnGrid(row, nextColumn)) {
            alienRow++;
            alienDirection *= -1;
        }

        // On déplace l'alien.
        moveTo(alien, alienRow, nextColumn);
        fireAlienShot(alien);

        if (alienRow == grid.getHeight() - 3) {
            // La partie est terminée.
            alienReachedPlanet();
        }
    }

    /**
     * Déplace un objet mobile à sa prochaine position.
     *
     * @param movable L'objet à déplacer.
     * @param newRow La nouvelle ligne pour l'objet.
     * @param newColumn La nouvelle colonne pour l'objet.
     */
    private void moveTo(AbstractMovable movable, int newRow, int newColumn) {
        if (grid.isOnGrid(newRow, newColumn)) {
            Tile previous = grid.get(movable.getRow(), movable.getColumn());
            previous.removeMovable();

            Tile next = grid.get(newRow, newColumn);
            next.setMovable(movable);

            movable.setPosition(newRow, newColumn);
        }
    }

    /**
     * Déplace un tir à sa prochaine position.
     *
     * @param shot Le tir à déplacer.
     */

    public void move(Shot shot) {
        // On déplace le tir de sa position courante.
        int row = shot.getRow();
        int column = shot.getColumn();
        int nextRow = row + shot.getTilesPerStep();
        grid.get(row, column).removeMovable();

        if (grid.isOnGrid(nextRow, column) && (nextRow != grid.getHeight() - 1)) {
            // On regarde si un objet se trouve sur la prochaine tuile.
            Tile next = grid.get(nextRow, column);
            AbstractMovable movable = next.getMovable();
            if (movable == null) {
                next.setMovable(shot);
                shot.setPosition(nextRow, column);

            } else {
                // Le tir a atteint une cible.
                movable.decrementHealth();

                shot.stop();

            }

        } else {
            // Le tir est sorti de la grille.
            shot.stop();
        }
    }

    /**
     * Déclenche un tir depuis le vaisseau du joueur.
     */
    public void fireShot() {
        fireShot(ship.getRow() - 1, ship.getColumn(), -1, "shot");
    }

    /**
     * Déclenche un tir depuis un vaisseau alien.
     *
     * @param alien Le vaisseau alien qui tire.
     */
    public void fireAlienShot(AlienShip alien) {
        if (RANDOM.nextInt(5) == 0) {
            fireShot(alien.getRow() + 1, alien.getColumn(), +1, "alien-shot");
        }
    }

    /**
     * Déclenche un tir depuis une position donnée.
     *
     * @param row La ligne de départ du tir.
     * @param column la colonne de départ du tir.
     * @param direction La direction du tir.
     * @param name Le nom de l'image représentant le tir.
     */
    private void fireShot(int row, int column, int direction, String name) {
        Shot shot = new Shot(this, spriteStore.createSprite(name), direction);
        shot.setPosition(row, column);
        grid.get(row, column).setMovable(shot);
        shot.animate();
    }

    /**
     * Retire un objet mobile du jeu.
     *
     * @param movable L'objet à retirer.
     */
    public void removeMovable(AbstractMovable movable) {
        interfaceController.delObjet(movable);
        grid.get(movable.getRow(), movable.getColumn()).removeMovable();
    }

    /**
     * Met à jour le score du joueur lorsqu'un alien est tué.
     * Si c'était le dernier, le joueur gagne la partie.
     */


    public void alienIsDead() {
        nbRemainingAliens--;
        this.score.setValue(score.getValue()+1);
        interfaceController.setScore(this.score);

        
        if (nbRemainingAliens == 0) {
            // Tous les aliens ont été tués : le niveau en cours est terminé.
            gameOver("YOU WIN!");
        }
    }

    /**
     * Termine la partie lorsque le joueur est tué.
     */
    public void playerIsDead() {
        gameOver("YOU HAVE BEEN KILLED!");
    }

    /**
     * Termine la partie lorsque les aliens atteignent la planète.
     */
    public void alienReachedPlanet() {
        gameOver("ALIENS INVADED THE PLANET!");
    }

    /**
     * Termine la partie en cours.
     *
     * @param message Le message indiquant le résultat de la partie.
     */
    private void gameOver(String message) {
        interfaceController.endGame(message);
    }

}

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

package fr.univartois.butinfo.ihm.spaceinvaders.controller;

import java.net.URL;

import fr.univartois.butinfo.ihm.spaceinvaders.model.AbstractMovable;
import fr.univartois.butinfo.ihm.spaceinvaders.model.GameGrid;
import fr.univartois.butinfo.ihm.spaceinvaders.model.SpaceInvadersGame;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SpaceInvadersController implements InterfaceController {

	private static final int NB_COLONNES = 14;
	private static final int NB_LIGNES = 14;

	private Label[][] grid = new Label[NB_COLONNES][NB_LIGNES];
	
	Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}


	
	@FXML
	private ImageView coeur1;

	@FXML
	private ImageView coeur2;

	@FXML
	private ImageView coeur3;

	@FXML
	private GridPane grilleJeu;

	@FXML
	private Label score;
	
	@FXML
	private Label endLabel;


	@FXML
	void initialize() {
		for (int i = 0; i < NB_COLONNES; i++) {
			for (int j = 0; j < NB_LIGNES; j++) {
				Label newLabel = new Label();
				newLabel.setPrefSize(60,60);
				if (i < NB_LIGNES - 1 ) {
					newLabel.setBackground(createBackground("back"));
				}
				else {
					newLabel.setBackground(createBackground("land"));
				}
				grid[i][j] = newLabel;
				grilleJeu.add(newLabel, j, i);
			}
		}
	}

	// Méthodes importées
	private Background createBackground(String name) {
		URL urlImage = getClass().getResource("../../images/" + name + ".png");
		BackgroundImage backgroundImage = new BackgroundImage(new Image(urlImage.toExternalForm(), 60, 60, true, false),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		return new Background(backgroundImage);
	}

	
	private SpaceInvadersGame game;

	
	@Override
	public void setGame(SpaceInvadersGame game) {
		this.game = game;	
	}

	@Override
	public void setGrille(GameGrid grille) {
		for (int i=0;i<NB_COLONNES;i++) {
			for (int j=0;j<NB_LIGNES;j++) {
				int column = i;
				int row = j;
				grille.get(i,j).getMovableProperty().addListener((p,o,n)->{
					if (n==null) {
						grid[column][row].setGraphic(null);
					}
					else {
						grid[column][row].setGraphic(createGraphicFor(n));
					}

				}
				);
			}
		}
		
		stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.RIGHT) {
				game.moveRight();

			} else if (e.getCode() == KeyCode.LEFT) {
				game.moveLeft();

			}
		});
		
		stage.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (" ".equals(e.getCharacter())) {
				game.fireShot();
			}	
		});
		
	}

	@Override
	public void addObjet(AbstractMovable objet) {
		int row = objet.getRow();
		int column = objet.getColumn();
		grid[row][column].setGraphic(createGraphicFor(objet));
	}

	@Override
	public void delObjet(AbstractMovable objet) {
		int row = objet.getRow();
		int column = objet.getColumn();
		grid[row][column].setGraphic(null);
	}

	@Override
	public void endGame(String message) {
		endLabel.setText(message);
	}

	public int getNbColonnes() {
		return NB_COLONNES;
	}

	public int getNbLignes() {
		return NB_LIGNES;
	}

	@Override
	public void setScore(IntegerProperty score) {
		this.score.setText("Score : "+score.getValue());
	}

	@Override
	public void setHealth(IntegerProperty health) {
		health.addListener((p,o,n) -> {
			if (n.equals(2)) {
				this.coeur3.setVisible(false);
			}
			if (n.equals(1)) {
				this.coeur2.setVisible(false);
			}
			if (n.equals(0)) {
				this.coeur1.setVisible(false);
			}
		});
	}

	private Node createGraphicFor(AbstractMovable movable) {
		  HBox box = new HBox();
		  box.setPrefHeight(50);
		  box.setPrefWidth(50);
		  box.setAlignment(Pos.CENTER);

		  ImageView view = new ImageView(movable.getSprite());
		  box.getChildren().add(view);

		  return box;
		}

}

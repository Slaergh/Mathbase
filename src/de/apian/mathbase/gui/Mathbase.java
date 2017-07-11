/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Hauptklasse des Programms mit umfassenden Kontrollmöglichkeiten.
 *
 * Der Konstruktor sollte niemals explizit aufgerufen werden; ein auf diese Weise erzeugtes <tt>Mathbase</tt>-Objekt
 * besitzt keinen realen Nutzen, weil es keinerlei Referenz auf das Hauptfenster <tt>stage</tt> hält,
 * die es einzig und allein durch die Erzeugung von JavaFX beim Programmstart höchstpersönlich erhält.
 *
 * @author Nikolas Kirschstein
 * @version 1.0
 * @since 1.0
 */
public class Mathbase extends Application {
    /**
     * Statische Instanzreferenz auf das Singleton <tt>Mathbase</tt>
     *
     * @since 1.0
     */
    private static Mathbase instance;
    /**
     * Referenz auf das Hauptfenster <tt>stage</tt>
     *
     * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Stage</a>
     * @since 1.0
     */
    private Stage stage;

    /**
     * Ghost-Konstruktor, der wegen JavaFX <tt>public</tt>-Sichtbarkeit benötigt
     */
    public Mathbase() {
        instance = this;
    }

    /**
     * Hauptmethode der Applikation
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        launch(args); // ruft die statische Methode launch() auf, welche wiederum die Applikation startet
    }

    /**
     * Singleton-Instanzoperation
     *
     * @return Die einzige Instanz von <tt>Mathbase</tt>
     */
    static Mathbase getInstance() {
        return instance;
    }

    /**
     * Einstiegsmethode der JavaFX-Anwendung
     *
     * @param stage vom Framework übergebenes Hauptfenster
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        initStage();
        stage.show();
    }

    /**
     * Methode zum Wechseln der aktuellen Ansicht
     *
     * @param view anzuzeigende <tt>View</tt>
     */
    void changeTo(View view) {
        stage.setScene(new Scene(view.getComponent()));
    }

    /**
     * Getter-Methode für die momentan auf der Bühne befindliche Szene
     *
     * @return aktuell im Hauptfenster angezeigte Szene
     */
    Scene getCurrentScene() {
        return stage.getScene();
    }

    /**
     * Delegierte Methode zur Initialisierung des Hauptfensters
     */
    private void initStage() {
        stage.setTitle("Mathbase 1.0");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/de/apian/mathbase/images/icon.png")));
        stage.setOnCloseRequest(e -> cleanUp());
        changeTo(MainMenu.getInstance());
    }

    /**
     * Routine bei Beendigung des Programms
     */
    private void cleanUp() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Mathbase");
        alert.setHeaderText("Eine Mitteilung...");
        alert.setContentText("Sie widerwärtiger Unhold!");
        alert.initOwner(stage);
        alert.initStyle(StageStyle.DECORATED);
        alert.showAndWait();
    }
}
package com.molodos.codingchallenge.gui;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlgorithmGUI extends Application {

    private static volatile AlgorithmGUI algorithmGUI;

    public static AlgorithmGUI startGUI() {
        algorithmGUI = null;
        new Thread(() -> launch(AlgorithmGUI.class)).start();
        while(algorithmGUI == null);
        return algorithmGUI;
    }

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.LEFT);

        Tab tab1 = new Tab("Planes", new Label("Show all planes available"));
        Tab tab2 = new Tab("Cars"  , new Label("Show all cars available"));
        Tab tab3 = new Tab("Boats" , new Label("Show all boats available"));
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("AlgorithmGUI");

        try {
            scene.getStylesheets().add(AlgorithmGUI.class.getResource("AlgorithmGUI.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error loading GUI styles, continuing with default styles...");
        }

        primaryStage.show();

        algorithmGUI = this;
    }
}

package com.molodos.codingchallenge.gui;

import com.molodos.codingchallenge.models.Item;
import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.Truck;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.text.DecimalFormat;

/**
 * This class defines the GUI functionalities to display execution information.
 *
 * @author Michael Weichenrieder
 */
public class AlgorithmGUI extends Application {

    // Static Strings shown in the GUI
    private static final String TITLE = "Algorithmus GUI";
    private static final String LOADING = "Wird berechnet...";

    // Object to query execution data to be displayed from
    private static volatile DisplayData displayData;

    /**
     * Creates and shows a new GUI from the DisplayData object.
     *
     * @param displayData DisplayData object to query execution data to be displayed from
     */
    public static void startGUI(DisplayData displayData) {
        // Save the DisplayData object
        AlgorithmGUI.displayData = displayData;

        // Start the GUI in a new Thread
        new Thread(() -> launch(AlgorithmGUI.class)).start();
    }

    /**
     * Build and show the GUI and start a timer to query new data from execution.
     *
     * @param primaryStage Stage object to add the GUI elements to
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize tab pane as root pane
        TabPane rootPane = new TabPane();

        // Create tabs
        Tab items = new Tab("Verfügbare Hardware", getItemTable(displayData.getInitialList()));
        Tab trucks = new Tab("Verfügbare Transporter", getTruckTable(displayData.getInitialTrucks()));
        Tab initial = new Tab("Nach erster Beladung", getLoadingPane());
        Tab optimization = new Tab("Optimierungsvorgänge", getLoadingPane());
        Tab solution = new Tab("Finale Beladung", getLoadingPane());

        // Set all tabs to not closeable
        items.setClosable(false);
        trucks.setClosable(false);
        initial.setClosable(false);
        optimization.setClosable(false);
        solution.setClosable(false);

        // Add tabs to tab pane
        rootPane.getTabs().addAll(items, trucks, initial, optimization, solution);

        // Initialize main scene with root pane, title and styles (if found)
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(TITLE);
        primaryStage.getIcons().add(new Image("file:truck.png"));
        try {
            scene.getStylesheets().add(AlgorithmGUI.class.getResource("AlgorithmGUI.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error loading GUI styles, continuing with default styles...");
        }
        primaryStage.show();

        // Update GUI from DisplayData
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // TODO: Query execution data
            }
        };
        timer.start();
    }

    /**
     * Formats an item list as a table.
     *
     * @param itemList Item list to be formatted
     * @return Table containing the item lists information
     */
    private Node getItemTable(ItemList itemList) {
        // Create table
        TableView<Item> table = new TableView<>();

        // Create and map columns
        TableColumn<Item, String> name = new TableColumn<>("Hardware");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, Integer> units = new TableColumn<>("Anzahl Verfügbar");
        units.setCellValueFactory(new PropertyValueFactory<>("units"));
        TableColumn<Item, Double> weight = new TableColumn<>("Gewicht");
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        TableColumn<Item, Double> value = new TableColumn<>("Nutzwert");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        TableColumn<Item, Double> efficiency = new TableColumn<>("Effizienz");
        efficiency.setCellValueFactory(new PropertyValueFactory<>("efficiency"));

        // Set localized double formatter
        DecimalFormat decimalFormat = new DecimalFormat("0.#####");
        DecimalFormat decimalFormatGrams = new DecimalFormat("0.#####g");
        weight.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decimalFormatGrams.format(price));
                }
            }
        });
        value.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decimalFormat.format(price));
                }
            }
        });
        efficiency.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decimalFormat.format(price));
                }
            }
        });

        // Add columns
        table.getColumns().add(name);
        table.getColumns().add(units);
        table.getColumns().add(weight);
        table.getColumns().add(value);
        table.getColumns().add(efficiency);

        // Add items to list
        table.getItems().addAll(itemList.getItems());

        // Return the created table
        return table;
    }

    /**
     * Formats a truck array as a table.
     *
     * @param trucks Trucks to be formatted
     * @return Table containing the trucks information
     */
    private Node getTruckTable(Truck[] trucks) {
        // Create table
        TableView<Truck> table = new TableView<>();

        // Create and map columns
        TableColumn<Truck, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Truck, Double> capacity = new TableColumn<>("Kapazität");
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        TableColumn<Truck, Double> driverWeight = new TableColumn<>("Gewicht Fahrer");
        driverWeight.setCellValueFactory(new PropertyValueFactory<>("driverWeight"));
        TableColumn<Truck, Double> remainingCapacity = new TableColumn<>("Übrige Kapazität");
        remainingCapacity.setCellValueFactory(new PropertyValueFactory<>("remainingCapacity"));

        // Set localized double formatter
        DecimalFormat decimalFormatGrams = new DecimalFormat("0.#####g");
        capacity.setCellFactory(tc -> new TableCell<Truck, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decimalFormatGrams.format(price));
                }
            }
        });
        driverWeight.setCellFactory(tc -> new TableCell<Truck, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decimalFormatGrams.format(price));
                }
            }
        });
        remainingCapacity.setCellFactory(tc -> new TableCell<Truck, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decimalFormatGrams.format(price));
                }
            }
        });

        // Add columns
        table.getColumns().add(name);
        table.getColumns().add(capacity);
        table.getColumns().add(driverWeight);
        table.getColumns().add(remainingCapacity);

        // Add trucks to list
        table.getItems().addAll(trucks);

        // Return the created table
        return table;
    }

    /**
     * Retrieve a pane just containing a centering loading information text.
     *
     * @return Loading pane
     */
    private Node getLoadingPane() {
        // Create a pane and a text
        StackPane loading = new StackPane();
        Text loadingText = new Text(LOADING);

        // Canter the text on the pane
        loadingText.setTextAlignment(TextAlignment.CENTER);
        loading.getChildren().add(loadingText);
        StackPane.setAlignment(loadingText, Pos.CENTER);

        // Return pane
        return loading;
    }
}

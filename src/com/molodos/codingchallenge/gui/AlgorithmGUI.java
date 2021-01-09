package com.molodos.codingchallenge.gui;

import com.molodos.codingchallenge.models.Item;
import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.Truck;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

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
        Tab items = new Tab("Verfügbare Hardware", getItemTable(displayData.getInitialList(), true, "Anzahl verfügbar"));
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
        final long[] lastCheck = {0};
        final int[] updatesDone = {0};
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Check for new data five times per second
                if (System.currentTimeMillis() - lastCheck[0] > 200) {
                    lastCheck[0] = System.currentTimeMillis();
                    switch (updatesDone[0]) {
                        case 0:
                            // Check if initial loading is done
                            if (displayData.getAfterFirstLoadList() != null) {
                                initial.setContent(getDetailsTabber(displayData.getAfterFirstLoadTrucks(), displayData.getAfterFirstLoadList()));
                                rootPane.getSelectionModel().select(initial);
                                updatesDone[0]++;
                            }
                            break;
                        case 1:
                            // Check if final solution arrived
                            if (displayData.getAfterOptimizationList() != null) {
                                solution.setContent(getDetailsTabber(displayData.getAfterOptimizationTrucks(), displayData.getAfterOptimizationList()));
                                rootPane.getSelectionModel().select(solution);
                                updatesDone[0]++;
                            }
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Creates a tabber with tables for snapshot details.
     *
     * @param trucks Trucks to display
     * @param items  Unloaded items to display
     * @return Created tabber
     */
    private Node getDetailsTabber(Truck[] trucks, ItemList items) {
        // Initialize tab pane as root pane
        TabPane detailsTabber = new TabPane();

        // Create overview tab
        Tab overview = new Tab("Übersicht", getLoadingPane());
        overview.setClosable(false);
        detailsTabber.getTabs().add(overview);

        // Create load list tab
        Tab loadList = new Tab("Ladeliste", getTruckLoadTable(trucks, items));
        loadList.setClosable(false);
        detailsTabber.getTabs().add(loadList);

        // Create items left tab
        Tab itemsLeft = new Tab("Übrige Hardware", getItemTable(items, false, "Anzahl übrig"));
        itemsLeft.setClosable(false);
        detailsTabber.getTabs().add(itemsLeft);

        // Set design
        detailsTabber.setSide(Side.LEFT);

        // Return tabber
        return detailsTabber;
    }

    /**
     * Formats an item list as a table.
     *
     * @param itemList   Item list to be formatted
     * @param details    Show detail columns like efficiency, value and weight
     * @param unitHeader Title of the units column
     * @return Table containing the item lists information
     */
    private Node getItemTable(ItemList itemList, boolean details, String unitHeader) {
        // Create table
        TableView<Item> table = new TableView<>();

        // Create and map base columns
        TableColumn<Item, String> name = new TableColumn<>("Hardware");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, Integer> units = new TableColumn<>(unitHeader);
        units.setCellValueFactory(new PropertyValueFactory<>("units"));

        // Add base columns
        table.getColumns().add(name);
        table.getColumns().add(units);

        // Create and map detail columns
        if (details) {
            TableColumn<Item, Double> weight = new TableColumn<>("Gewicht");
            weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
            TableColumn<Item, Double> value = new TableColumn<>("Nutzwert");
            value.setCellValueFactory(new PropertyValueFactory<>("value"));
            TableColumn<Item, Double> efficiency = new TableColumn<>("Effizienz");
            efficiency.setCellValueFactory(new PropertyValueFactory<>("efficiency"));

            // Set localized double formatter if details enabled
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
            // Add detail columns
            table.getColumns().add(weight);
            table.getColumns().add(value);
            table.getColumns().add(efficiency);
        }

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
     * Formats a truck array as a load list table.
     *
     * @param trucks Trucks to be formatted
     * @param items  List of items to add lines for
     * @return Table containing the trucks load list
     */
    private Node getTruckLoadTable(Truck[] trucks, ItemList items) {
        // Create table
        TableView<Item> table = new TableView<>();

        // Create and add item name column
        TableColumn<Item, String> item = new TableColumn<>("Hardware");
        item.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(item);

        // Create and add columns for unit counts of all trucks
        for (Truck truck : trucks) {
            TableColumn<Item, Integer> units = new TableColumn<>("Einheiten " + truck.getName());
            units.setCellValueFactory(new Callback() {
                @Override
                public Object call(Object param) {
                    if (param instanceof TableColumn.CellDataFeatures) {
                        TableColumn.CellDataFeatures cellData = (TableColumn.CellDataFeatures) param;
                        if (cellData.getValue() instanceof Item) {
                            Item item = (Item) cellData.getValue();
                            return new SimpleIntegerProperty(truck.getUnits(item));
                        }
                    }
                    return null;
                }
            });
            table.getColumns().add(units);
        }

        // Create and add total count column
        TableColumn<Item, Integer> total = new TableColumn<>("Einheiten Gesamt");
        total.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object param) {
                if (param instanceof TableColumn.CellDataFeatures) {
                    TableColumn.CellDataFeatures cellData = (TableColumn.CellDataFeatures) param;
                    if (cellData.getValue() instanceof Item) {
                        Item item = (Item) cellData.getValue();
                        int totalUnits = 0;
                        for (Truck truck : trucks) {
                            totalUnits += truck.getUnits(item);
                        }
                        return new SimpleIntegerProperty(totalUnits);
                    }
                }
                return null;
            }
        });
        table.getColumns().add(total);

        // Add item lines to list
        table.getItems().addAll(items.getItems());

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

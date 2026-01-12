package application;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private final Map<String, WeatherData> weatherDB = new HashMap<>();

    @Override
    public void start(Stage stage) {

        // ======== Add Ethiopian cities and personalized tips ========
        weatherDB.put("Addis Ababa", new WeatherData("23°C", "Partly Cloudy", "🌱 Water your plants today"));
        weatherDB.put("Adama", new WeatherData("30°C", "Sunny", "☀️ Great for drying crops"));
        weatherDB.put("Mekelle", new WeatherData("28°C", "Windy", "🍃 Windy day for outdoor tasks"));
        weatherDB.put("Bahir Dar", new WeatherData("27°C", "Cloudy", "☁️ Enjoy sightseeing today"));
        weatherDB.put("Hawassa", new WeatherData("25°C", "Rainy", "🌧️ Collect rainwater if you can"));
        weatherDB.put("Gondar", new WeatherData("26°C", "Partly Cloudy", "🌱 Good for garden work"));
        weatherDB.put("Dire Dawa", new WeatherData("32°C", "Hot", "💧 Stay hydrated!"));
        weatherDB.put("Jimma", new WeatherData("24°C", "Humid", "💧 Perfect for coffee plants"));
        weatherDB.put("Harar", new WeatherData("29°C", "Sunny", "☀️ Great for outdoor activities"));
        weatherDB.put("Shashamane", new WeatherData("25°C", "Partly Cloudy", "🌱 Water your garden"));
        weatherDB.put("Korem", new WeatherData("22°C", "Cloudy", "☁️ Ideal for planting vegetables"));

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F1F8E9;"); // white-green background

        // ================= TOP BAR =================
        Text cityTitle = new Text("Addis Ababa");
        cityTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 30));
        cityTitle.setFill(Color.web("#1B5E20"));

        ComboBox<String> cityComboBox = new ComboBox<>();
        cityComboBox.setEditable(true);
        cityComboBox.setPromptText("Search city");
        cityComboBox.getEditor().getStyleClass().add("city-field"); // apply CSS

        ObservableList<String> cities = FXCollections.observableArrayList(weatherDB.keySet());
        FilteredList<String> filteredCities = new FilteredList<>(cities, p -> true);
        cityComboBox.setItems(filteredCities);

        // Autocomplete
        cityComboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            final String typed = newText.toLowerCase();
            filteredCities.setPredicate(city -> city.toLowerCase().startsWith(typed));
            if (!cityComboBox.isShowing())
                cityComboBox.show();
        });

        Button searchBtn = new Button("Search");
        searchBtn.getStyleClass().add("refresh-btn");
        searchBtn.disableProperty().bind(Bindings.isEmpty(cityComboBox.getEditor().textProperty()));

        HBox searchBox = new HBox(8, cityComboBox, searchBtn);
        searchBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topBar = new BorderPane();
        topBar.setCenter(cityTitle);
        topBar.setRight(searchBox);
        topBar.setPadding(new Insets(10));
        root.setTop(topBar);

        // ================= CENTER =================
        Label temperature = new Label("23°C");
        temperature.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
        temperature.setTextFill(Color.web("#2E7D32"));

        Label condition = new Label("Partly Cloudy");
        condition.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        condition.setTextFill(Color.web("#1565C0"));

        Label tip = new Label("🌱 Water your plants today");
        tip.setFont(Font.font("Arial", FontPosture.ITALIC, 16));
        tip.setTextFill(Color.web("#FF6F00"));

        Ellipse leaf = new Ellipse(40, 60);
        leaf.setFill(Color.web("#2E7D32"));
        leaf.setRotate(25);

        RotateTransition rotate = new RotateTransition(Duration.seconds(4), leaf);
        rotate.setFromAngle(20);
        rotate.setToAngle(60); // bigger swing
        rotate.setAutoReverse(true);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

        VBox centerBox = new VBox(15, temperature, condition, leaf, tip);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));
        root.setCenter(centerBox);

        // ================= BOTTOM FORECAST =================
        HBox forecastBox = new HBox(20,
                createForecast("Mon", "22°C"),
                createForecast("Tue", "24°C"),
                createForecast("Wed", "21°C"),
                createForecast("Thu", "23°C"),
                createForecast("Fri", "25°C"));
        forecastBox.setAlignment(Pos.CENTER);
        forecastBox.setPadding(new Insets(10));
        root.setBottom(forecastBox);

        // ================= SEARCH ACTION =================
        Runnable searchAction = () -> {
            String city = cityComboBox.getEditor().getText().trim();
            if (!city.isEmpty()) {
                WeatherData w = weatherDB.entrySet().stream()
                        .filter(entry -> entry.getKey().equalsIgnoreCase(city))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .orElse(null);

                if (w != null) {
                    cityTitle.setText(city);
                    temperature.setText(w.temperature);
                    condition.setText(w.condition);
                    tip.setText(w.tip);
                } else {
                    cityTitle.setText(city);
                    temperature.setText("--°C");
                    condition.setText("Weather unknown");
                    tip.setText("❌ No weather data for this city");
                }
                cityComboBox.getEditor().clear();
            }
        };

        searchBtn.setOnAction(e -> searchAction.run());

        // Press Enter to search
        cityComboBox.getEditor().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                searchAction.run();
        });

        Scene scene = new Scene(root, 450, 600);

        // Optional: add CSS if available
        if (getClass().getResource("style.css") != null) {
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        }

        stage.setTitle("Nati's Ethiopian Weather App");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createForecast(String day, String temp) {
        Label d = new Label(day);
        d.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Label t = new Label(temp);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        VBox box = new VBox(5, d, t);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    static class WeatherData {
        String temperature;
        String condition;
        String tip;

        WeatherData(String temperature, String condition, String tip) {
            this.temperature = temperature;
            this.condition = condition;
            this.tip = tip;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

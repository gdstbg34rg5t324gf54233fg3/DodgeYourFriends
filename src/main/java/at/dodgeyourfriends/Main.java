package at.dodgeyourfriends;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static int BUTTON_HEIGHT = 150;
    public static int BUTTON_WIDTH = 150;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root, 300, 150);

        ObservableList<String> selectRegionList = FXCollections.observableArrayList();

        ComboBox<String> cbSelectRegion = new ComboBox<>();
        cbSelectRegion.setItems(selectRegionList);

        selectRegionList.addAll("EUW", "NA", "EUNE", "OCE", "LAS", "LAN", "BR", "TR", "RU", "KR", "JP", "PBE");
        cbSelectRegion.getSelectionModel().selectFirst();

        HBox buttonPlacement = new HBox(5);
        buttonPlacement.setPadding(new Insets(10));
        buttonPlacement.setAlignment(Pos.CENTER);

        Button btnOnline = new Button("ONLINE");
        Button btnOffline = new Button("OFFLINE");

        btnOnline.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btnOffline.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        if(!ExecFirewallCommand.checkFirewallEntry()) {
            btnOnline.setStyle("-fx-base: #96FFA9");
        }
        else {
            btnOffline.setStyle("-fx-base: #FF9696");
        }

        btnOnline.setOnMouseClicked(mouseEvent -> {
            try {
                ExecFirewallCommand.openRegion();
                btnOnline.setStyle("-fx-base: #96FFA9");
                btnOffline.setStyle(null);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        btnOffline.setOnMouseClicked(mouseEvent -> {
            String region = "172.65.252.238";

            switch (cbSelectRegion.getSelectionModel().getSelectedItem()) {
                case "EUW" -> region = "172.65.252.238";
                case "NA" -> region = "172.65.244.155";
                case "EUNE" -> region = "172.65.223.136";
                case "OCE" -> region = "172.65.208.61";
                case "LAS" -> region = "172.65.194.233";
                case "LAN" -> region = "172.65.250.49";
                case "BR" -> region = "172.65.212.1";
                case "TR" -> region = "172.65.202.166";
                case "RU" -> region = "172.65.192.156";
                case "KR" -> region = "172.65.226.99";
                case "JP" -> region = "172.65.217.212";
                case "PBE" -> region = "172.65.223.16";
            }

            try {
                System.out.println(region);
                ExecFirewallCommand.blockRegion(region);
                btnOffline.setStyle("-fx-base: #FF9696");
                btnOnline.setStyle(null);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        buttonPlacement.getChildren().addAll(btnOnline, btnOffline);

        root.getChildren().addAll(cbSelectRegion, buttonPlacement);

        primaryStage.setTitle("DodgeYourFriends");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("DodgeYourFriends.png"));
        primaryStage.show();
    }
}
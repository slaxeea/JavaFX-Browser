package WebView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import static java.util.logging.Logger.global;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author kappe
 */
public class Starter extends Application {

    public static WebView wv = new WebView();
    public static WebEngine engine = wv.getEngine();

    @Override
    public void start(Stage stage) throws MalformedURLException, IOException {
           
        /*
        URL url = getClass().getResource("Browser.html");
        engine.load(url.toExternalForm());

        VBox root = new VBox();
        root.getChildren().addAll(wv);

        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);

        stage.setTitle("SIMON SIN BROWSER");
        stage.show();
        */
        
        Parent root = FXMLLoader.load(getClass().getResource("Browser.fxml"));
        
        Scene scene = new Scene(root, 1100, 600);
        
        stage.setTitle("SIMON SIN BROWSER");
        stage.setFullScreen(false);
        
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);

        stage.initStyle(StageStyle.DECORATED);

        stage.setResizable(true);

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

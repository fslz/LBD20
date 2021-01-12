import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import View.*;

public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // The start method provides an input reference of a stage, therefore it's unnecessary to instantiate a new stage
        // Instantiate loader in order to load the .fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"));
        // Set the Parent (or Root node) of the scene by loading the .fmxl file
        Parent root = fxmlLoader.load();
        // Set the title of the stage (window)
        stage.setTitle("Login");
        // Set a new Scene, with the root Node attached, to the stage
        stage.setScene(new Scene(root));
        // Set the stage to not be resizable
        stage.setResizable(false);
        // Finally show the stage
        stage.show();

    }

    public static void main(String[] args) {

        // The launch method automatically calls the start() method
        launch(args);

    }

}

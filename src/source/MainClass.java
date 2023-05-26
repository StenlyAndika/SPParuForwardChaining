package source;

import connection.BridgeCon;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Stenly Andika
 */
public class MainClass extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/FormMenuAdmin.fxml"));
            BridgeCon Con = new BridgeCon();
            Con.setDragged(root, stage);
            Scene scene = new Scene(root);

            stage.setTitle("Sistem Pakar Diagnosis Paru-Paru");
            stage.initStyle(StageStyle.UNDECORATED);
            Image applicationIcon = new Image(getClass().getResourceAsStream("/img/AppIcon.png"));
            stage.getIcons().add(applicationIcon);
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

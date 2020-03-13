import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
//        final MyController controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setTitle("TASL APP");
        primaryStage.setScene(scene);
//        primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                controller.shutdown();
//                Platform.exit();
//            }
//        });
        primaryStage.show();
    }

    public static  void main(String[] args){
        launch(args);
    }
}

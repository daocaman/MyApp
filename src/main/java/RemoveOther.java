import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RemoveOther {
    @FXML
    private Button btn_remove;

    final DirectoryChooser fileChooser = new DirectoryChooser();

    final ArrayList<String> data = new ArrayList<String>(Arrays.asList("png", "jpg"));

    private File tmp;

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public void removeFileDir(File f) {
        for (File t1 : f.listFiles()) {
            if (!t1.isDirectory()) {
                String ext = getExtensionByApacheCommonLib(t1.getName()).toLowerCase();
                if (!data.contains(ext)) {
                    System.out.println(t1.getName());
                    t1.delete();
                } else {
                    BufferedImage bimg = null;
                    try {
                        bimg = ImageIO.read(t1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int width = bimg.getWidth();
                    int height = bimg.getHeight();
                    if (width < 100 && height < 100) {
                        t1.delete();
                    }
                }
            } else {
                removeFileDir(t1);
            }

        }
    }

    public void doIt(ActionEvent actionEvent) {
        fileChooser.setTitle("title");
        tmp = fileChooser.showDialog(btn_remove.getScene().getWindow());
        if (tmp != null) {
            removeFileDir(tmp);
        }
    }
}

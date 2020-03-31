import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RemoveOther {
    @FXML
    private Button btn_RO_choose;

    @FXML
    private Button btn_RO_delete;

    @FXML
    private TextField tv_RO_folder;

    @FXML
    private Label lb_RO_infor;

    @FXML
    private Label lb_RO_file;

    @FXML
    private Label lb_RO_fol;

    private File chooseFolder;

    final DirectoryChooser fileChooser = new DirectoryChooser();

    final ArrayList<String> data = new ArrayList<String>(Arrays.asList("png", "jpg"));

    private File tmp;

    private int count = 0;

    private int count1 = 0;

    private File crrFolder = null;

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public void removeFileDir(File f) {
        for (File t1 : f.listFiles()) {
            if (!t1.isDirectory()) {
                String ext = getExtensionByApacheCommonLib(t1.getName()).toLowerCase();
                if (!data.contains(ext) && !Book.TYPE_BOOK.contains(ext)) {
                    System.out.println(t1.getName());
                    count += 1;
                    deleteFile(t1);
                } else {

                    BufferedImage bimg = null;
                    try {
                        bimg = ImageIO.read(t1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int width = bimg.getWidth();
                    int height = bimg.getHeight();
                    if (width < 100 || height < 100) {
                        count += 1;
                        deleteFile(t1);
                    }
                }
            } else {
                crrFolder = t1;
                count1 += 1;
                removeFileDir(t1);
            }
        }
    }

    public void deleteFile(File f) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                f.delete();
            }
        });
        thread.start();
    }


    public void doIt(ActionEvent actionEvent) {
        count = 0;
        count1 = 0;
        lb_RO_file.setText("");
        lb_RO_fol.setText("");
        try {
            if (tmp != null) {
                removeFileDir(tmp);
            }
            lb_RO_file.setText(String.valueOf(count));
            lb_RO_fol.setText(String.valueOf(count1));
            lb_RO_infor.setText("Hoàn thành");
            lb_RO_infor.getStyleClass().removeAll();
            lb_RO_infor.getStyleClass().addAll("h5", "text-success");
        } catch (Exception e) {
            lb_RO_infor.setText("error, " + crrFolder.getName());
            lb_RO_infor.getStyleClass().removeAll();
            lb_RO_infor.getStyleClass().addAll("h5", "text-danger");
        }

    }

    public void openFolder(ActionEvent actionEvent) {
        fileChooser.setTitle("title");
        tmp = fileChooser.showDialog(btn_RO_delete.getScene().getWindow());
        if (tmp != null)
            tv_RO_folder.setText(tmp.getName());
    }
}

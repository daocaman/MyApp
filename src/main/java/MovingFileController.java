import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MovingFileController {

    @FXML
    private Button btn_mv_source, btn_mv_target;

    @FXML
    private ListView lv_mv_file;

    @FXML
    private TextField tv_mv_source, tv_mv_target;

    @FXML
    private Label lb_mv_f;

    @FXML
    private Label lb_mv_p;

    @FXML
    private ComboBox cb_mv_f;

    final DirectoryChooser fileChooser = new DirectoryChooser();

    private File srcF = null, tarF = null;

    @FXML
    private void initialize() {

    }

    public File openFileChooser(String title) {
        fileChooser.setTitle(title);
        File f = fileChooser.showDialog(tv_mv_source.getScene().getWindow());
        return f;
    }

    public void openDirS(ActionEvent actionEvent) {
        srcF = openFileChooser("Choose source folder");
        if (srcF != null)
            tv_mv_source.setText(srcF.getName());
    }

    public void openDirT(ActionEvent actionEvent) {
        tarF = openFileChooser("Choose destination foler");
        if (tarF != null)
            tv_mv_target.setText(tarF.getName());
    }

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    private static void fileCopyUsingApacheCommons(String srcFile, String dstFile) {
        File fileToCopy = new File(srcFile);
        File newFile = new File(dstFile);
        try {
            FileUtils.copyFile(fileToCopy, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveFiles(File src) {
        for (File f : src.listFiles()) {
            if (f.isDirectory()) {
                moveFiles(f);
            } else {
                if (getExtensionByApacheCommonLib(f.getName()).equalsIgnoreCase((String) cb_mv_f.getValue())) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fileCopyUsingApacheCommons(f.getAbsolutePath(), tarF.getAbsolutePath() + "/" + f.getName());
                        }
                    });
                    thread.start();
                    lv_mv_file.getItems().add(f.getName());
                } else if (((String) cb_mv_f.getValue()).isEmpty()) {
                    Notify.update(lb_mv_p, "Extension file not found/ not selected", Notify.DANGER);
                }
            }
        }

    }

    public void move(ActionEvent actionEvent) {
        if (tarF != null && srcF != null) {
            moveFiles(srcF);
            lb_mv_f.setText(String.valueOf(lv_mv_file.getItems().size()));
            Notify.update(lb_mv_p, "Completed", Notify.SUCCESS);
        } else {
            Notify.update(lb_mv_p, "Error null file", Notify.DANGER);
        }
    }
}

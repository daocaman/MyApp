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
        srcF = openFileChooser("Chọn thư mục nguồn");
        if (srcF != null)
            tv_mv_source.setText(srcF.getName());
    }

    public void openDirT(ActionEvent actionEvent) {
        tarF = openFileChooser("Chọn thư mục đích");
        if (tarF != null)
            tv_mv_target.setText(tarF.getName());
    }

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    private static void fileCopyUsingApacheCommons(String srcFile, String dstFile)
    {
        File fileToCopy = new File(srcFile);
        File newFile = new File(dstFile);

        try {
            FileUtils.copyFile(fileToCopy, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(ActionEvent actionEvent)  {
        int count = 0;
        if (tarF != null && srcF != null) {
            for(File f : srcF.listFiles()){
                if(f.isDirectory()){
                    for(File tmp : f.listFiles()){
                        if(getExtensionByApacheCommonLib(tmp.getName()).equalsIgnoreCase((String)cb_mv_f.getValue())){
                            count+=1;
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    fileCopyUsingApacheCommons(tmp.getAbsolutePath(), tarF.getAbsolutePath()+"/"+tmp.getName());
                                }
                            });
                            thread.start();
                            lv_mv_file.getItems().add(tmp.getName());
                            lb_mv_p.setText("Files: "+String.valueOf(count));
                        }
                    }
                }
            }
        }

    }
}

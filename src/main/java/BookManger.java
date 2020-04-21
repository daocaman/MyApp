import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BookManger {
    @FXML
    private Button btn_bm_open;

    @FXML
    private Button btn_bm_statistic;

    @FXML
    private Button btn_bm_save;

    @FXML
    private TextField tv_bm_srcFolder;

    private File src;

    private Workbook wb;

    final DirectoryChooser dirChooser = new DirectoryChooser();


//    Open folder
    public void browseAction(ActionEvent actionEvent) {
        tv_bm_srcFolder.setText("");
        src = null;
        dirChooser.setTitle("Choose folder");
        src = dirChooser.showDialog(btn_bm_open.getScene().getWindow());
        if(src != null){
            tv_bm_srcFolder.setText(src.getName());
        }
    }

    public void statisticAction(ActionEvent actionEvent) {
        if(src!= null){
            wb = new XSSFWorkbook();
        }
    }


//    public void openFileLocation(MouseEvent mouseEvent) throws IOException {
//        Desktop.getDesktop().open(new File("C:\\xampp"));
//    }


    

}


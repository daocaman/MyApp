import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

public class NumberFile {

    private ObservableList<ChangingRow> tvObservableList = FXCollections.observableArrayList();

    final DirectoryChooser fileChooser = new DirectoryChooser();

    private File srcFolder = null;

    @FXML
    ToggleGroup groupSort;

    @FXML
    Label lb_nf_information;

    @FXML
    Button btn_nf_choose;

    @FXML
    Button btn_nf_rename;

    @FXML
    TextField tv_nf_folder, tv_nf_prefix;

    @FXML
    TableView tb_nf_changing;


    @FXML
    private void initialize() {
        TableColumn<String, ChangingRow> column1 = new TableColumn<String, ChangingRow>("No.");
        column1.setCellValueFactory(new PropertyValueFactory<String, ChangingRow>("no"));

        TableColumn<String, ChangingRow> column2 = new TableColumn<String, ChangingRow>("Old name");
        column2.setCellValueFactory(new PropertyValueFactory<String, ChangingRow>("oldName"));

        TableColumn<String, ChangingRow> column3 = new TableColumn<String, ChangingRow>("New name");
        column3.setCellValueFactory(new PropertyValueFactory<String, ChangingRow>("newName"));

        tb_nf_changing.getColumns().addAll(column1, column2, column3);

        tb_nf_changing.setItems(tvObservableList);
        tb_nf_changing.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    }

    public File openFileChooser(String title) {
        fileChooser.setTitle(title);
        File f = fileChooser.showDialog(btn_nf_choose.getScene().getWindow());
        return f;
    }

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public void openFolder(ActionEvent actionEvent) {
        srcFolder = openFileChooser("Choose folder");
        if (srcFolder != null) {
            tv_nf_folder.setText(srcFolder.getName());
        }
    }

    public void rename(ActionEvent actionEvent) {
        if (srcFolder != null) {
            File[] lstFiles = srcFolder.listFiles();
            Arrays.sort(lstFiles, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                }
            });

            for (int i = 0; i < lstFiles.length; i++) {
                File nwFile = new File(srcFolder.getAbsolutePath() + "/" + newName(i, lstFiles.length) + "." + getExtensionByApacheCommonLib(lstFiles[i].getName()));
                tvObservableList.add(new ChangingRow(i, lstFiles[i].getName(), nwFile.getName()));
                try {
                    Files.move(lstFiles[i].toPath(), nwFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Notify.update(lb_nf_information, "Error null folder", Notify.DANGER);
        }

    }

    public String newName(int crr, int size) {
        String sz = String.valueOf(size);
        String res = "";
        switch (sz.length()) {
            case 1:
                res = String.format("%01d", crr);
                break;
            case 2:
                res = String.format("%02d", crr);
                break;
            case 3:
                res = String.format("%03d", crr);
                break;
            case 4:
                res = String.format("%04d", crr);
                break;
            default:
                break;
        }

        return tv_nf_prefix.getText().isEmpty() ? res : tv_nf_prefix.getText() + "_" + res;
    }
}

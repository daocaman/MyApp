import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BookManger {
    @FXML
    private Button btn_bm_open;

    @FXML
    private Button btn_bm_statistic;

    @FXML
    private Button btn_bm_save;

    @FXML
    private TextField tv_bm_srcFolder;

    @FXML
    private Label lb_bm_files;

    @FXML
    private Label lb_bm_directories;

    @FXML
    private javafx.scene.control.Hyperlink hyperlink_bm;

    @FXML
    private Label lb_bm_information;

    private File src, excelFile;

    private Workbook wb;

    private FileOutputStream fileOut;

    final DirectoryChooser dirChooser = new DirectoryChooser();

    private int countSheet, books;

    private static final Logger log = LogManager.getLogger(BookManger.class);

    private String[] colums = {"STT", "Tên", "Loại"};

    //    Open folder
    public void browseAction(ActionEvent actionEvent) {
        log.info("Open Browsing!!!");
        tv_bm_srcFolder.setText("");
        src = null;
        dirChooser.setTitle("Choose folder");
        src = dirChooser.showDialog(btn_bm_open.getScene().getWindow());
        if (src != null) {
            tv_bm_srcFolder.setText(src.getName());
        }
    }

    public void statisticAction(ActionEvent actionEvent) {
        Notify.clear(lb_bm_information);
        countSheet = 0;
        books = 0;
        if (src != null) {
            wb = new XSSFWorkbook();
            createSheetFromDirectory(src, wb);
            updateInformation();
        } else {
            Notify.update(lb_bm_information, "Missing sources", Notify.DANGER);
        }

    }

    private void updateInformation() {
        countSheet = wb.getNumberOfSheets();
        Notify.update(lb_bm_directories, String.valueOf(countSheet), Notify.PRIMARY);
        Notify.update(lb_bm_files, String.valueOf(books), Notify.PRIMARY);
        Notify.update(lb_bm_information, "Finished getting data!", Notify.SUCCESS);
    }

    private void createSheetFromDirectory(File src, Workbook wb) {
        File[] files = src.listFiles();
        ArrayList<Book> ebooks = new ArrayList<>();
        countSheet += 1;
        for (File f : files) {
            if (!f.isDirectory()) {
                Book eb = new Book(f.getName());
                ebooks.add(eb);
                books += 1;
            } else {
//                System.out.println(f.getName());
                createSheetFromDirectory(f, wb);
            }
        }
        putContaintSheet(src, ebooks);
    }

    private void putContaintSheet(File src, ArrayList<Book> ebooks) {
        if (ebooks.size() == 0)
            return;
        try {
            Sheet sheet = wb.createSheet();

            CellStyle headerCellStyle = createHeaderStyle();

            Row tmp = sheet.createRow(0);

            Font myFont = (Font) wb.createFont();
            myFont.setBold(true);
            myFont.setFontHeightInPoints((short) 18);
            myFont.setColor(IndexedColors.DARK_GREEN.getIndex());
            myFont.setFontName("Times New Roman");

            CellStyle myStyle = wb.createCellStyle();
            myStyle.setFont((org.apache.poi.ss.usermodel.Font) myFont);
            myStyle.setAlignment(HorizontalAlignment.CENTER);

            Cell titleCell = tmp.createCell(1);
            titleCell.setCellValue(src.getName());

            CellUtil.setAlignment(titleCell, HorizontalAlignment.CENTER);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 2));

            titleCell.setCellStyle(myStyle);

            Cell cellLink = tmp.createCell(0);


            cellLink.setCellValue("Link");
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));

            Hyperlink hyperlink = wb.getCreationHelper().createHyperlink(HyperlinkType.URL);
            log.info("Test: " + src.toURI());
            hyperlink.setAddress(src.toURI().toString());
            cellLink.setHyperlink(hyperlink);

            // Create a Row
            Row headerRow = sheet.createRow(2);

            // Create cells
            for (int i = 0; i < colums.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colums[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 3;
            for (Book eb : ebooks) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(rowNum - 3);

                row.createCell(1).setCellValue(eb.getName());

                row.createCell(2).setCellValue(eb.getType());
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < colums.length; i++) {
                sheet.autoSizeColumn(i, true);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //  cell filename style (Times New Roman, 13, Sky blue color)
    private CellStyle createHeaderStyle() {

        Font headerFont = (Font) wb.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 13);
        headerFont.setColor(IndexedColors.SKY_BLUE.getIndex());
        headerFont.setFontName("Times New Roman");

        CellStyle headerCellStyle = wb.createCellStyle();
        headerCellStyle.setFont((org.apache.poi.ss.usermodel.Font) headerFont);
        return headerCellStyle;

    }

    public void Save(ActionEvent actionEvent) {
        log.info("Open Saving Place");

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel files (.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extensionFilter);

        excelFile = fileChooser.showSaveDialog(btn_bm_open.getScene().getWindow());
        if (excelFile != null) {
            try {

                log.info("Saving file");
                fileOut = new FileOutputStream(excelFile);
                wb.write(fileOut);
                fileOut.close();
                wb.close();

                log.info("Saved");
                hyperlink_bm.setText(excelFile.getName());
                hyperlink_bm.setVisible(true);

                log.info("Update link");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.warn("Missing sources");
            Notify.update(lb_bm_information, "Missing sources", Notify.DANGER);
        }

    }

    public void OpenFile(ActionEvent actionEvent) {
        File savePlace = excelFile.getParentFile();
        if (savePlace != null) {
            log.info("Not null parent");
            try {
                Desktop.getDesktop().open(savePlace);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


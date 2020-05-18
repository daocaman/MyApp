import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
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

    private File src;

    private Workbook wb;

    final DirectoryChooser dirChooser = new DirectoryChooser();

    private int countSheet, books;

    private String[] colums = {"STT", "Tên", "Loại"};

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
            createSheetFromDirectory(src, wb);
        }else{

        }


    }

    private void createSheetFromDirectory(File src, Workbook wb) {
        File[] files = src.listFiles();
        ArrayList<Book> ebooks = new ArrayList<>();
        for (File f : files) {
            if (!f.isDirectory()) {
                Book eb = new Book(f.getName());
                ebooks.add(eb);
            } else {
                System.out.println(f.getName());
                createSheetFromDirectory(f, wb);
            }
        }
        putContaintSheet(src.getName(), ebooks, wb);
    }

    private void putContaintSheet(String name, ArrayList<Book> ebooks, Workbook wb) {
        if (ebooks.size() == 0)
            return;
        try {
            Sheet sheet = wb.createSheet();
            // Create a Font for styling header cells

            Font headerFont = (Font) wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 13);
            headerFont.setColor(IndexedColors.SKY_BLUE.getIndex());
            headerFont.setFontName("Times New Roman");

            // Create a CellStyle with the font
            CellStyle headerCellStyle = wb.createCellStyle();
            headerCellStyle.setFont((org.apache.poi.ss.usermodel.Font) headerFont);

            Row tmp = sheet.createRow(0);

            Font myFont = (Font) wb.createFont();
            myFont.setBold(true);
            myFont.setFontHeightInPoints((short) 18);
            myFont.setColor(IndexedColors.DARK_GREEN.getIndex());
            myFont.setFontName("Times New Roman");

            CellStyle myStyle = wb.createCellStyle();
            myStyle.setFont((org.apache.poi.ss.usermodel.Font) myFont);
            myStyle.setAlignment(HorizontalAlignment.CENTER);

            Cell  cell1 = tmp.createCell(1);
            cell1.setCellValue(name);
            CellStyle cellStyle = cell1.getCellStyle();
            CellUtil.setAlignment(cell1, HorizontalAlignment.CENTER);
            sheet.addMergedRegion(new CellRangeAddress(0,1,1,2));

            cell1.setCellStyle(myStyle);

            cell1 = tmp.createCell(0);
            cell1.setCellValue("Thư mục: ");
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));



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

    public void Save(ActionEvent actionEvent) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(src.getName() + ".xlsx");
            wb.write(fileOut);
            fileOut.close();

            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


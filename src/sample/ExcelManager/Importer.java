package sample.ExcelManager;


import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.LoggingManager.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Importer {

    public ImporterResult importFromFile(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File", "*.xlsx"));

        String path = new File("").getAbsolutePath();
        System.out.println(path);
        File file = new File(path + "/src/sample/resources/Excel/");
        fileChooser.setInitialDirectory(file);
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Logger.info(Importer.class.getName(), "importFromFile", "importing from an Excel file...");
            FileInputStream fis = new FileInputStream(selectedFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.rowIterator();
            Logger.info(Importer.class.getName(), "importFromFile", "importing process is complete!");
            return new ImporterResult(iterator);
        }

        return null;
    }

}

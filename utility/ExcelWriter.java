/*
NOTE: THIS VERSION WILL PRINT THE EXCEL INTO ROWS INSTEAD OF COLUMNS!
        LATER PRINT IN COLUMNS
 */
package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jasmi
 */
public class ExcelWriter {

    CollectionOfFields CoF;

    public ExcelWriter(CollectionOfFields CoF) {
        this.CoF = CoF;
    }

    public void Write() throws FileNotFoundException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Field Data");
          
        for (int i = 0; i < CoF.getCollection().get(0).getFieldData().size(); i++) {
            Row row = sheet.createRow(i);

            for (int j = 0; j < CoF.getNumberOfColumns(); j++) { 
                Cell cell = row.createCell(j);
                cell.setCellValue(CoF.getCollection().get(j).getFieldData().get(i));
                System.out.println("i: " + i);
                System.out.println("j: " + j);
            }
        }

        System.out.println("Writing out file...");

        FileOutputStream outFile = new FileOutputStream(new File("testingupdate.xlsx"));
        workbook.write(outFile);
        outFile.close();

        System.out.println("File written.");
    }

}

/*
 * IMPORTANTE INFORMATION:
 * Only working for Java 8
 * For more information refer to the "ExcelReader Read Me.txt" file.
 * Heavily refer: https://www.callicoder.com/java-read-excel-file-apache-poi/
 */
package utility;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *
 * @author jasmi
 */
public class ExcelReader {

    private ArrayList<String> ParsedData;       // Holds the data from file
    private CollectionOfFields CoF;
    int rowSize;
    public static final String XLSX_FILE_PATH = "./DroneSample.xlsx";
    public static final String UPDATED_XLSX_FILE_PATH = "./update.xlsx";

    // Constructor to instantiate
    public ExcelReader() {
        ParsedData = new ArrayList<>();
    }
    
    // Reads the excel file
    public void Read() throws IOException, InvalidFormatException {

        PrefixExcel();

        Sheet sheet = null;
        try {
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = WorkbookFactory.create(new File(UPDATED_XLSX_FILE_PATH));
         
            // Get a sheet and get its cells (data)
            // Getting the Sheet at index zero
            sheet = workbook.getSheetAt(0);

            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            // Print out the data
            //System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    //System.out.print(">" + cellValue + "<\t");
                    ParsedData.add(cellValue);

                }
                //System.out.println();
            }

            // Get the size of the row
            rowSize = sheet.getRow(0).getLastCellNum();
            
            CoF = new CollectionOfFields(ParsedData, rowSize);

            // Closing the workbook
            workbook.close();
        } catch (Exception e) {
            System.out.println("\nSomething when wrong: \n" + e);
        }
    }

    // Pre-fix and edit the excel for easier reading
    // Probably a better way to do it, but for now do this
    public void PrefixExcel() throws InvalidFormatException {
        System.out.println("PrefixExecel Beginning...");
        try {
            
            Workbook workbook = WorkbookFactory.create(new File(XLSX_FILE_PATH));

            Sheet sheet = workbook.getSheetAt(0);
            Cell cell = null;

            for (Row row : sheet) {
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                }
            }

            FileOutputStream outFile = new FileOutputStream(new File("update.xlsx"));
            workbook.write(outFile);
            outFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("PrefixExecel Finished...");
    }    
       
    // Get the data collection
    CollectionOfFields getCollection(){
        return CoF;
    }

}

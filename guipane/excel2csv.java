/**************
 * required files 
 * poi-3.16.jar
 * poi-ooxml-3.16.jar 
 * to be added as dependencies to project
 * both are located in jar files
 ***************/

package guipane;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ConvertXLSXToCSV {
 
    private static void excel2csv(File xlsxFile, int sheetIdx) throws Exception {
 
        FileInputStream fileInStream = new FileInputStream(xlsxFile);
 
        // Open the xlsx and get the requested sheet from the workbook
        XSSFWorkbook workBook = new XSSFWorkbook(fileInStream);
        XSSFSheet selSheet = workBook.getSheetAt(sheetIdx);
 
        // Iterate through all the rows in the selected sheet
        Iterator<row> rowIterator = selSheet.iterator();
        while (rowIterator.hasNext()) {
 
            Row row = rowIterator.next();
 
            // Iterate through all the columns in the row and build ","
            // separated string
            Iterator<cell> cellIterator = row.cellIterator();
            StringBuffer sb = new StringBuffer();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (sb.length() != 0) {
                    sb.append(",");
                }
                 
                // If you are using poi 4.0 or over, change it to
                // cell.getCellType
                switch (cell.getCellTypeEnum()) {
                case STRING:
                    sb.append(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    sb.append(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    sb.append(cell.getBooleanCellValue());
                    break;
                default:
                }
            }
            System.out.println(sb.toString());
        }
        workBook.close();
    }
    //Tested and works 4/12/2020
    public static void main(String[] args) throws Exception {
        File myFile = new File("INSERT FILE NAME HERE");
        int sheetIdx = 0; // 0 for first sheet
 
        excel2csv(myFile, sheetIdx);
    }
}
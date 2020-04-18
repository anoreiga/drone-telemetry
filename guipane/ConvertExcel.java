package guipane;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ConvertExcel {

    static String COMMA_SEPARATED = ",";

    public static void echoAsCSV(Sheet sheet, String filePath) {
        String fullString = "";
        Row row = null;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
// with double quotes - eg: "row a2","row b2","row c2","row d2"
/* for (int j = 0; j < row.getLastCellNum(); j++) {
if(j==row.getLastCellNum()-1){
if(fullString.isEmpty()){
fullString = "\"" + row.getCell(j)+"\"";
}else{
fullString = fullString + "\"" + row.getCell(j)+"\""+"\n";
}
}
else{
if(fullString.isEmpty()){
fullString = "\"" + row.getCell(j) +"\""+ COMMA_SEPARATED;
}else{
fullString = fullString +"\"" + row.getCell(j) +"\""+ COMMA_SEPARATED;
}
}
}*/
// Without double quotes - eg: row a2,row b2,row c2,row d2 --> Recommended
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if (j == row.getLastCellNum() - 1) {
                    if (fullString.isEmpty()) {
                        fullString = "" + row.getCell(j) + "";
                    } else {
                        fullString = fullString + "" + row.getCell(j) + "" + "\n";
                    }
                } else {
                    if (fullString.isEmpty()) {
                        fullString = "" + row.getCell(j) + "" + COMMA_SEPARATED;
                    } else {
                        fullString = fullString + "" + row.getCell(j) + "" + COMMA_SEPARATED;
                    }
                }
            }
        }
        try {
            FileUtils.writeStringToFile(new File(filePath.replaceAll(".xlsx", ".csv")), fullString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
//public static void main(String[] args) {
    public void Convert(String path) {
        String excelFile = path;
        InputStream inp = null;
        
        String filePath = excelFile;
        
        try {
            inp = new FileInputStream(filePath);
            Workbook wb = WorkbookFactory.create(inp);
            System.out.println(wb.getSheetAt(0).getSheetName());
            echoAsCSV(wb.getSheetAt(0), filePath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConvertExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inp.close();
            } catch (IOException ex) {
                Logger.getLogger(ConvertExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

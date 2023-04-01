package guru.hw_10;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ReadZipFilesTest {
    private static String pathToZip = "hw_10/files.zip";
    private ClassLoader cl = ReadZipFilesTest.class.getClassLoader();

    @Test
    @DisplayName("Чтение csv из zip-файла")
    void readCsvTest() throws Exception {

        try (InputStream inputStream = cl.getResourceAsStream(pathToZip);
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().equals("totalProfit.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zipInputStream));
                    List<String[]> stringsCsv = csvReader.readAll();

                    Assertions.assertArrayEquals(
                            new String[]{"Region", "Country", "Item Type", "Sales Channel",
                                    "Order Priority", "Order Date", "Order ID", "Ship Date",
                                    "Units Sold", "Unit Price", "Unit Cost", "Total Revenue",
                                    "Total Cost", "Total Profit"},
                            stringsCsv.get(0));
                    Assertions.assertArrayEquals(
                            new String[]{"Sub-Saharan Africa", "Republic of the Congo", "Personal Care", "Offline",
                                    "M", "7/14/2015", "770463311", "8/25/2015",
                                    "6070", "81.73", "56.67", "496101.10",
                                    "343986.90", "152114.20"},
                            stringsCsv.get(9));
                    Assertions.assertArrayEquals(
                            new String[]{"Australia and Oceania", "Samoa ", "Cosmetics", "Online",
                                    "H", "7/20/2013", "670854651", "8/7/2013",
                                    "9654", "437.20", "263.33", "4220728.80",
                                    "2542187.82", "1678540.98"},
                            stringsCsv.get(80));
                    Assertions.assertEquals(101, stringsCsv.size());
                }
            }
        }
    }

    @Test
    @DisplayName("Чтение xlsx из zip-файла")
    void readXlsxTest() throws Exception {

        try (InputStream inputStream = cl.getResourceAsStream(pathToZip);
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().equals("seafood.xlsx")) {
                    XLS xls = new XLS(zipInputStream);

                    Assertions.assertTrue(xls.excel.getSheetName(0).equals("Source Data") &&
                            xls.excel.getSheetName(1).equals("PivotTable Template") &&
                            xls.excel.getSheetName(2).equals("Sample PivotTable Report"));

                    Assertions.assertEquals(286, xls.excel.getSheetAt(0).getLastRowNum());

                    Row rowForCheck = xls.excel.getSheetAt(0).getRow(16);
                    Assertions.assertTrue(rowForCheck.getCell(0).getStringCellValue().equals("Beverages") &&
                            rowForCheck.getCell(1).getStringCellValue().equals("Côte de Blaye") &&
                            rowForCheck.getCell(2).getNumericCellValue() == 1317.5 &&
                            rowForCheck.getCell(3).getStringCellValue().equals("Qtr 4"));

                    rowForCheck = xls.excel.getSheetAt(0).getRow(173);
                    Assertions.assertTrue(rowForCheck.getCell(0).getStringCellValue().equals("Grains/Cereals") &&
                            rowForCheck.getCell(1).getStringCellValue().equals("Filo Mix") &&
                            rowForCheck.getCell(2).getNumericCellValue() == 742 &&
                            rowForCheck.getCell(3).getStringCellValue().equals("Qtr 2"));

                    rowForCheck = xls.excel.getSheetAt(2).getRow(13);
                    Assertions.assertTrue(rowForCheck.getCell(1).getStringCellValue().equals("Grand Total") &&
                            rowForCheck.getCell(2).getNumericCellValue() == 608846.76);
                }
            }
        }
    }

    @Test
    @DisplayName("Чтение pdf из zip-файла")
    void readPdfTest() throws Exception {

        try (InputStream inputStream = cl.getResourceAsStream(pathToZip);
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().equals("waterTrips.pdf")) {
                    PDF pdfReader = new PDF(zipInputStream);

                    Assertions.assertEquals(10, pdfReader.numberOfPages);
                    Assertions.assertEquals(28208, pdfReader.text.length());
                    Assertions.assertEquals(3976877, pdfReader.content.length);

                    Assertions.assertTrue(pdfReader.text.contains(
                            "Vestibulum eleifend, ligula a scelerisque vehicula, risus justo ultricies ligula, et interdum lorem"));
                    Assertions.assertTrue(pdfReader.text.contains(
                            "Nam rutrum fringilla risus, nec sollicitudin risus rhoncus ut"));
                    Assertions.assertTrue(pdfReader.text.contains(
                            "pulvinar mi. Aenean accumsan eros nulla, ac tincidunt erat viverra id. Integer laoreet hendrerit"
                    ));
                }
            }
        }
    }
}

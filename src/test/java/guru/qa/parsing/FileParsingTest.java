package guru.qa.parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class FileParsingTest {

  private ClassLoader cl = FileParsingTest.class.getClassLoader();

  @Test
  void pdfParseTest() throws Exception {
    Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
    File download = $("a[href*='junit-user-guide-5.9.2.pdf']").download();
    PDF pdf = new PDF(download);
    Assertions.assertEquals(
        "Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein",
        pdf.author
    );
  }

  @Test
  void xlsParseTest() throws Exception {
    Selenide.open("https://excelvba.ru/programmes/Teachers?ysclid=lfcu77j9j9951587711");
    File download = $("a[href*='teachers.xls']").download();
    XLS xls = new XLS(download);

    Assertions.assertTrue(
        xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue()
            .startsWith("1. Суммарное количество часов планируемое на штатную по всем разделам плана")
    );
  }

  @Test
  void csvParseTest() throws Exception {
    try (InputStream is = cl.getResourceAsStream("qaguru.csv");
         InputStreamReader isr = new InputStreamReader(is)) {
      CSVReader csvReader = new CSVReader(isr);
      List<String[]> content = csvReader.readAll();
      Assertions.assertArrayEquals(new String[] {"Тучс", "JUnit5"}, content.get(1));
    }
  }


}

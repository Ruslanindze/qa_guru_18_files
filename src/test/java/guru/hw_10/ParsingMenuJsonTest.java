package guru.hw_10;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.parsing.FileParsingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class ParsingMenuJsonTest {
    private ClassLoader cl = FileParsingTest.class.getClassLoader();
    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Проверка парсинга json-файла")
    void test() throws IOException {

        try (InputStream inputStream = cl.getResourceAsStream("hw_10/menu.json");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            MenuJson menuJson = objectMapper.readValue(inputStreamReader, MenuJson.class);


            Assertions.assertEquals("SVG Viewer", menuJson.getMenu().getHeader());

            Assertions.assertEquals("Open", menuJson.getMenu().getItems().get(0).getId());
            Assertions.assertNull(menuJson.getMenu().getItems().get(0).getLabel());

            Assertions.assertEquals("Pause", menuJson.getMenu().getItems().get(8).getId());
            Assertions.assertNull(menuJson.getMenu().getItems().get(8).getLabel());

            Assertions.assertNull(menuJson.getMenu().getItems().get(2));
            Assertions.assertNull(menuJson.getMenu().getItems().get(6));
            Assertions.assertNull(menuJson.getMenu().getItems().get(10));
            Assertions.assertNull(menuJson.getMenu().getItems().get(19));

            Assertions.assertEquals("ZoomOut", menuJson.getMenu().getItems().get(4).getId());
            Assertions.assertEquals("Zoom Out", menuJson.getMenu().getItems().get(4).getLabel());

            Assertions.assertEquals("ViewSVG", menuJson.getMenu().getItems().get(16).getId());
            Assertions.assertEquals("View SVG", menuJson.getMenu().getItems().get(16).getLabel());

            Assertions.assertEquals("About", menuJson.getMenu().getItems().get(21).getId());
            Assertions.assertEquals("About Adobe CVG Viewer...", menuJson.getMenu().getItems().get(21).getLabel());

            Assertions.assertTrue(menuJson.getMenu().getItems().size() == 22);
        }
    }
}

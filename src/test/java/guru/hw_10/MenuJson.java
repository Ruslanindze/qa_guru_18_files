package guru.hw_10;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class MenuJson {
    public Menu menu;

    public Menu getMenu() {
        return menu;
    }

    //-----------------------------------
    static class Menu {
        public String header;
        List<Items> items;

        public String getHeader() {
            return header;
        }

        public List<Items> getItems() {
            return items;
        }

        //-----------------------------------
        @JsonInclude(JsonInclude.Include.NON_NULL)
        static class Items {
            String id;
            String label;

            public String getId() {
                return id;
            }

            public String getLabel() {
                return label;
            }
        }
    }


}

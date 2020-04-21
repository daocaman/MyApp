import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Book {
    public final static ArrayList<String> TYPE_BOOK = new ArrayList<>(Arrays.asList("epub", "mobi", "azw3"));

    private String name;
    private String type;

    public Book(String filename) {
        this.name = FilenameUtils.getName(filename);
        this.type = FilenameUtils.getExtension(filename);
    }

    public Book(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

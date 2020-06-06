public class BookRow {
    private String filename;
    private String sheet;
    private String foldername;
    private String path;

    public BookRow(String filename, String sheet, String foldername, String path) {
        this.filename = filename;
        this.sheet = sheet;
        this.foldername = foldername;
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "BookRow{" +
                "filename='" + filename + '\'' +
                ", sheet='" + sheet + '\'' +
                ", foldername='" + foldername + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

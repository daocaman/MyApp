public class ChangingRow {
    private int no;
    private String oldName;
    private String newName;

    public ChangingRow(int no, String oldName, String newName) {
        this.no = no;
        this.oldName = oldName;
        this.newName = newName;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Override
    public String toString() {
        return "ChangingRow{" +
                "no=" + no +
                ", oldName='" + oldName + '\'' +
                ", newName='" + newName + '\'' +
                '}';
    }
}

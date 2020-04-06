import javafx.scene.control.Label;

public class Notify {
    public static final String ALERT_NULL = "File not found";
    public static final String ALERT_ERROR = "Error";
    public static final int INFO = 1;
    public static final int DANGER = 2;
    public static final int SUCCESS = 3;
    public static final int WARNING = 4;
    public static final int PRIMARY = 5;

    public static void update(Label lb, String msg, int style) {
        lb.setText(msg);
        updateColor(lb, style);
    }

    private static void updateColor(Label lb, int style) {
        lb.getStyleClass().removeAll();
        switch (style) {
            case INFO:
                lb.getStyleClass().addAll("h5", "text-info");
                break;
            case DANGER:
                lb.getStyleClass().addAll("h5", "text-danger");
                break;
            case SUCCESS:
                lb.getStyleClass().addAll("h5", "text-success");
                break;
            case WARNING:
                lb.getStyleClass().addAll("h5", "text-warning");
                break;
            case PRIMARY:
                lb.getStyleClass().addAll("h5", "text-primary");
                break;
            default:
                break;

        }
    }
}

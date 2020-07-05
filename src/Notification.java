import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Notification {
    int campaignId;
    private LocalDateTime date;
    private ArrayList<String> vouchersCode;
    enum NotificationType{
        EDIT, CANCEL;
    }
    NotificationType stat;

    public Notification(int campaignId, String date, NotificationType stat) {
        this.campaignId = campaignId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = LocalDateTime.parse(date, formatter);
        this.stat = stat;
        vouchersCode = new ArrayList<>();
    }

    public void setVouchersCode(ArrayList<String> vouchersCode) {
        this.vouchersCode = vouchersCode;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return campaignId + ";" + vouchersCode.toString() + ";" + date.format(formatter) + ";" + stat;
    }
}
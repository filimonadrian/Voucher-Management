import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Voucher {
    private int voucherID;
    private String voucherCode;
    private int campaignID;
    private String email;
    private LocalDateTime usesDate;
    enum VoucherStatusType{
        USED, UNUSED;
    }
    VoucherStatusType stat;

    public Voucher(int voucherID, String voucherCode, int campaignID, String email) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
        this.campaignID = campaignID;
        this.email = email;
        this.stat = VoucherStatusType.UNUSED;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getUsesDate() {
        return usesDate;
    }

    public void setUsesDate(LocalDateTime usesDate) {
        this.usesDate = usesDate;
    }

    public VoucherStatusType getStat() {
        return stat;
    }

    @Override
    public String toString() {
        return voucherID + ";" + stat + ";" + email + ";";
    }
}
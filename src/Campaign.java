import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Campaign {

    private String name, description;
    private int campaignId;
    private static int voucherId = 1;
    String voucherCode;
    private int totalVouchers, availableVouchers;
    private LocalDateTime startDate, finishDate;
    private ArrayList<User> observers;
    CampaignVoucherMap<String, ArrayList> vouchers;
    enum CampaignStatusType{
        NEW, STARTED, EXPIRED, CANCELLED;
    }
    CampaignStatusType stat;

    public Campaign(String name, String description,
                    int campaign_id, int totalVouchers,
                    String startDate, String finishDate) {
        this.name = name;
        this.description = description;
        this.campaignId = campaign_id;
        this.totalVouchers = totalVouchers;
        this.availableVouchers = totalVouchers;
        this.voucherCode = String.valueOf(voucherId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.startDate = LocalDateTime.parse(startDate, formatter);
        this.finishDate = LocalDateTime.parse(finishDate, formatter);
        this.vouchers = new CampaignVoucherMap<>();
        this.observers = new ArrayList<>();
        this.stat = CampaignStatusType.NEW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCampaign_id() {
        return campaignId;
    }

    public void setCampaign_id(int campaign_id) {
        this.campaignId = campaign_id;
    }

    public int getTotalVouchers() {
        return totalVouchers;
    }

    public void setTotalVouchers(int totalVouchers) {
        this.totalVouchers = totalVouchers;
    }

    public int getAvailableVouchers() {
        return availableVouchers;
    }

    public void setAvailableVouchers(int availableVouchers) {
        this.availableVouchers = availableVouchers;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finish_date) {
        this.finishDate = finish_date;
    }

    public void setVouchers(CampaignVoucherMap vouchers) {
        this.vouchers = vouchers;
    }

    //toate voucherele din campania respectiva
    public ArrayList<Voucher> getVouchers() {
        ArrayList<Voucher> vouchersInThisCampaign = new ArrayList<>();
        ArrayList<Voucher> aux;
        for (int i = 0; i < vouchers.size(); i++){
            aux = vouchers.col.get(i).getValue();
            vouchersInThisCampaign.addAll(aux);
        }
        return vouchersInThisCampaign;
    }

    public Voucher getVoucher(String code) {
        ArrayList<Voucher> aux;
        for (int i = 0; i < vouchers.size(); i++){
            aux = vouchers.col.get(i).getValue();
            for (int j = 0; j < aux.size(); j++){
                if (aux.get(j).getVoucherCode().equals(code)){
                    return aux.get(j);
                }
            }
        }
        return null;
    }


    public Voucher generateVoucher(String email, String voucherType, float value){

        if (voucherType.equals("GiftVoucher")){
            voucherCode = String.valueOf(voucherId);
            GiftVoucher newVoucher = new GiftVoucher(voucherId, voucherCode,
                    campaignId, email, value);
            voucherId++;
            newVoucher.stat = Voucher.VoucherStatusType.UNUSED;
            vouchers.addVoucher(newVoucher);
            this.availableVouchers--;
            return newVoucher;

        } else {
            voucherCode = String.valueOf(voucherId);
            LoyalityVoucher newVoucher = new LoyalityVoucher(voucherId, voucherCode,
                    campaignId, email, value);
            voucherId++;
            newVoucher.stat = Voucher.VoucherStatusType.UNUSED;
            vouchers.addVoucher(newVoucher);
            this.availableVouchers--;
            return newVoucher;
        }

    }

    public boolean redeemVoucher(int voucherId, LocalDateTime date){

        ArrayList<Voucher> vouchersInThisCampaign = getVouchers();
        for (Voucher i : vouchersInThisCampaign){
            if (i.getVoucherID() == voucherId){

                if (date.isBefore(this.getFinishDate()) &&
                        (this.stat == CampaignStatusType.NEW || this.stat == CampaignStatusType.STARTED ) &&
                        i.stat == Voucher.VoucherStatusType.UNUSED) {
                    i.stat = Voucher.VoucherStatusType.USED;
                    i.setUsesDate(date);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<User> getObservers() {
        return observers;
    }

    public void addObserver(User user){
        if (!observers.contains(user))
            observers.add(user);
    }

    public void removeObserver(User user){
        for (int i = 0; i < observers.size(); i++){
            if (observers.get(i).getEmail().equals(user.getEmail())){
                observers.remove(i);
                break;
            }
        }
    }

    public void notifyAllObservers(Notification notification){
        for (User user : observers) {
            user.update(notification);
        }
    }


}
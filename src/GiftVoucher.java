public class GiftVoucher extends Voucher{
    float amount;

    public GiftVoucher(int voucher_id, String voucher_code,
                       int campaign_id, String email, float amount) {
        super(voucher_id, voucher_code, campaign_id, email);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return super.toString() + amount + ";" + getCampaignID() + ";" + getUsesDate();
    }
}
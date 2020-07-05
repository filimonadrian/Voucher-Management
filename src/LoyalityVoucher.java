public class LoyalityVoucher extends Voucher {
    float sale;

    public LoyalityVoucher(int voucher_id, String voucher_code,
                           int campaign_id, String email, float sale) {
        super(voucher_id, voucher_code, campaign_id, email);
        this.sale = sale;
    }

    @Override
    public String toString() {
        return super.toString() + sale + ";" + getCampaignID() + ";" + getUsesDate();
    }
}
import java.util.ArrayList;

public class UserVoucherMap<K, V> extends ArrayMap<Integer, ArrayList<Voucher>> {
    //cheia este reprezentata de
    //ID-ul unei campanii, iar valoarea este reprezentata de colectia de vouchere primite in cadrul
    //campaniei respective.

    boolean addVoucher(Voucher voucher){
        if (this.containsKey(voucher.getCampaignID())){
            for (int i = 0; i < this.col.size(); i++){
                if (this.col.get(i).getKey() == voucher.getCampaignID())
                    this.col.get(i).getValue().add(voucher);
                return true;
            }
        }

        ArrayList<Voucher> a = new ArrayList<>();
        a.add(voucher);
        return put(voucher.getCampaignID(), a) != null;
    }

}
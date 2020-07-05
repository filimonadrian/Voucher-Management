import java.util.ArrayList;

public class CampaignVoucherMap<K, V> extends ArrayMap<String, ArrayList> {
    //cheia este
    //reprezentata de email-ul unui utilizator, iar valoarea este reprezentata de colectia de vouchere
    //distribuite in cadrul campaniei respective


    boolean addVoucher(Voucher voucher){
        if (this.containsKey(voucher.getEmail())){
            for (int i = 0; i < this.col.size(); i++){
                if (this.col.get(i).getKey().equals(voucher.getEmail()))
                    col.get(i).getValue().add(voucher);
                return true;
            }
        }

        ArrayList<Voucher> a = new ArrayList<>();
        a.add(voucher);
        return put(voucher.getEmail(), a) != null;
    }

}
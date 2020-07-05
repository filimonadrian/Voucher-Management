import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class VMS {
    // All existing campaigns and all users who can receive vouchers

    ArrayList<Campaign> campaigns;
    ArrayList<User> users;
    private static VMS single_instance;

    private VMS() {
        campaigns = new ArrayList<Campaign>();
        users = new ArrayList<User>();
    }

    public static VMS getInstance() {
        if (single_instance == null)
            single_instance = new VMS();

        return single_instance;
    }

    public ArrayList<Campaign> getCampaigns() {
        return campaigns;
    }

    public Campaign getCampaign(Integer id) {
        for (Campaign i : campaigns) {
            if (i.getCampaign_id() == id) {
                return i;
            }
        }
        return null;
    }

    public void addCampaign(Campaign campaign) {
        campaigns.add(campaign);
    }

    public void updateCampaign(Integer id, Campaign campaign) {
        Campaign aux = getCampaign(id);
        if (aux.stat == Campaign.CampaignStatusType.NEW) {
            // se poate modifica orice in afara de numarul de vouchere disponibile
            // daca totusi se incearca modificarea
            // pastrez vechiul numar de vouchere(il pun in campania primita ca parametru)
            // apoi pun campania primita ca parametru in locul celei vechi
            if (campaign.getAvailableVouchers() != aux.getAvailableVouchers()) {
                campaign.setAvailableVouchers(aux.getAvailableVouchers());
                aux = campaign;
                System.out.println("You can't modify number of AVAILABLE VOUCHERS");
            }
        }

        if (aux.stat == Campaign.CampaignStatusType.STARTED) {
            // aici se poate modifica doar numarul total de vouchere si data de finalizare
            // pastrez campania si dau update la cele 2 valori
            aux.setFinishDate(campaign.getFinishDate());

            // daca noul numar maxim de vouchere este mai mare decat nr de vouchere distribuite
            // setez noul numar maxim de vouchere
            // cresc/scad numarul de vouchere disponibile cu diferenta dintre cele 2 maxime
            if (campaign.getTotalVouchers() > aux.getTotalVouchers() - aux.getAvailableVouchers()) {
                aux.setTotalVouchers(campaign.getTotalVouchers());
                aux.setAvailableVouchers(aux.getAvailableVouchers() +
                        campaign.getTotalVouchers() - aux.getTotalVouchers());
            }
        }
    }

    public void voucherDistribution(String email, String voucherType, float value, int campaignId) {
        if (this.getUserWithEmail(email) != null) {
            Campaign camp = this.getCampaign(campaignId);
            // verific daca mai am vouchere disponibile
            if (camp.getAvailableVouchers() > 0) {
                // generez voucherul si se adauga automat in dictionarul campaniei
                // il adaug manual in dictionarul utilizatorului cu emailul de pe voucher
                Voucher v = camp.generateVoucher(email, voucherType, value);
                this.getUserWithEmail(email).receivedVouchers.addVoucher(v);
                // adaug userul care a primit voucherul in observatorii campaniei
                camp.addObserver(this.getUserWithEmail(email));
            }
        }
    }

    public void multipleVoucherDistribution(File f, int campaignId) {
        String email, voucherType;
        float value;
        int emailsNumber;
        try {
            Scanner s = new Scanner(f);

            emailsNumber = Integer.parseInt(s.next());
            s.useDelimiter("[;\n]");

            while (s.hasNext()) {
                email = s.next();
                voucherType = s.next();
                value = Float.parseFloat(s.next());
                this.voucherDistribution(email, voucherType, value, campaignId);
            }
            s.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cancelCampaign(Integer id) {
        Campaign aux = getCampaign(id);
        if (aux.stat == Campaign.CampaignStatusType.NEW ||
                aux.stat == Campaign.CampaignStatusType.STARTED) {
            aux.stat = Campaign.CampaignStatusType.CANCELLED;
        }
    }

    public User getUserWithId(int userId) {
        for (User i : users) {
            if (i.getUserId() == userId)
                return i;
        }
        return null;
    }

    public User getUserWithEmail(String email) {
        for (User i : users) {
            if (i.getEmail().equals(email))
                return i;
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }


}
import java.util.ArrayList;

public class User {
    int userId;
    private String name, password, email;
    UserVoucherMap<Integer, ArrayList<Voucher>> receivedVouchers;
    ArrayList<Notification> notifications;
    enum UserType {
        ADMIN, GUEST;
    }
    UserType stat;

    public User(int userId, String name,
                String password, String email, UserType stat) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.stat = stat;
        notifications = new ArrayList<>();
        receivedVouchers = new UserVoucherMap<Integer, ArrayList<Voucher>>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotifications(Notification notification) {
        notifications.add(notification);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    //toate voucherele unui utilizator
    public ArrayList<Voucher> getVouchers() {
        ArrayList<Voucher> vouchersOfThisUser = new ArrayList<>();
        ArrayList<Voucher> aux;
        for (int i = 0; i < receivedVouchers.size(); i++){
            aux = receivedVouchers.col.get(i).getValue();
            vouchersOfThisUser.addAll(aux);
        }
        return vouchersOfThisUser;
    }

    public ArrayList<String> getVouchersCodeInCampaign(int campaignId) {
        ArrayList<String> vouchersCode = new ArrayList<>();
        ArrayList<Voucher> aux = receivedVouchers.get(campaignId);
        for (int i = 0; i < aux.size(); i++){
            vouchersCode.add(aux.get(i).getVoucherCode());
        }
        return vouchersCode;
    }

    public boolean update(Notification notification) {
        if (notifications.contains(notification)) {
            return notifications.add(notification);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + userId + ";"+ name + ";" + email + ";" + stat + "]";
    }
}
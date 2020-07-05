import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        VMS vms = VMS.getInstance();

        try {
            Scanner s = new Scanner(new File(
                    "/home/adrian/Documents/Voucher-Management/src/teste/test08/input/campaigns.txt"));

            int campaignsNumber;
            String curentDate;
            int id;
            String name, description, startDate, endDate;
            int budget;
            String strategy;
            campaignsNumber = Integer.parseInt(s.next());
            s.next();
            curentDate = s.nextLine();

            s.useDelimiter("[;\n]");

            while (s.hasNext()) {
                id = Integer.parseInt(s.next());
                name = s.next();
                description = s.next();
                startDate = s.next();
                endDate = s.next();
                budget = Integer.parseInt(s.next());
                strategy = s.next();

                vms.campaigns.add(new Campaign(name, description, id, budget, startDate, endDate));


            }
            s.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }

        int usersNumber;
        String curentDate;
        int id;
        String name, password, email, type;

        try {
            Scanner s = new Scanner(new File(
                    "/home/adrian/Documents/Voucher-Management/src/teste/test08/input/users.txt"));

            usersNumber = Integer.parseInt(s.next());

            s.useDelimiter("[;\n]");

            while (s.hasNext()) {
                id = Integer.parseInt(s.next());
                name = s.next();
                password = s.next();
                email = s.next();
                type = s.next();

                vms.users.add(new User(id, name, password, email, User.UserType.valueOf(type)));
            }
            s.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }


        String curentDateEvents;
        int userId;
        int eventsNumber, campaignId, budget, voucherId;
        float value;
        String campaignName, campaignDescription, startDate, endDate, strategy, voucherType;
        String localDate;
        String aux;
        try {
            Scanner s = new Scanner(new File(
                    "/home/adrian/Documents/Voucher-Management/src/teste/test08/input/events.txt"));
            s.useDelimiter("[;\n]");

            curentDateEvents = s.next();
            eventsNumber = Integer.parseInt(s.next());

            while (s.hasNext()) {
                userId = Integer.parseInt(s.next());

                aux = s.next();
                //System.out.println(userId + " " + aux);

                switch (aux){
                    case "addCampaign":
                        campaignId = Integer.parseInt(s.next());
                        campaignName = s.next();
                        campaignDescription = s.next();
                        startDate = s.next();
                        endDate = s.next();
                        budget = Integer.parseInt(s.next());
                        strategy = s.next();

                        if (vms.getUserWithId(userId) != null &&
                                vms.getUserWithId(userId).stat == User.UserType.ADMIN){
                            Campaign camp = new Campaign(campaignName, campaignDescription,
                                    campaignId, budget, startDate, endDate);
                            vms.addCampaign(camp);
                        }

                        break;

                    case "editCampaign":
                        campaignId = Integer.parseInt(s.next());
                        campaignName = s.next();
                        campaignDescription = s.next();
                        startDate = s.next();
                        endDate = s.next();
                        budget = Integer.parseInt(s.next());

                        if (vms.getUserWithId(userId) != null &&
                                vms.getUserWithId(userId).stat == User.UserType.ADMIN){

                            Campaign camp = new Campaign(campaignName, campaignDescription,
                                    campaignId, budget, startDate, endDate);
                            vms.updateCampaign(campaignId, camp);

                            ArrayList<User> usersInThisCampaign = vms.getCampaign(campaignId).getObservers();
                            for (User i : usersInThisCampaign){
                                Notification notification = new Notification(
                                        campaignId, curentDateEvents, Notification.NotificationType.EDIT);
                                notification.setVouchersCode(i.getVouchersCodeInCampaign(campaignId));
                                i.addNotifications(notification);
                            }
                        }
                        break;

                    case "cancelCampaign":
                        campaignId = Integer.parseInt(s.next());

                        if (vms.getUserWithId(userId) != null &&
                                vms.getUserWithId(userId).stat == User.UserType.ADMIN){

                            vms.cancelCampaign(campaignId);

                            ArrayList<User> usersInThisCampaign = vms.getCampaign(campaignId).getObservers();
                            for (User i : usersInThisCampaign){
                                Notification notification = new Notification(
                                        campaignId, curentDateEvents, Notification.NotificationType.CANCEL);
                                notification.setVouchersCode(i.getVouchersCodeInCampaign(campaignId));
                                i.addNotifications(notification);
                            }
                        }

                        break;

                    case "generateVoucher":
                        campaignId = Integer.parseInt(s.next());
                        email = s.next();
                        voucherType = s.next();
                        value = Float.parseFloat(s.next());

                        //functia pentru bonus - trebuie decomentata ca sa functioneze
/*
                        File emailFile = new File(
                                "/home/adrian/Documents/labPOO/VoucherManagementService/src/teste//emails.txt");
                        vms.multipleVoucherDistribution(emailFile, campaignId);
  */
                        if (vms.getUserWithId(userId) != null &&
                                vms.getUserWithId(userId).stat == User.UserType.ADMIN)
                            vms.voucherDistribution(email, voucherType, value, campaignId);

                        break;

                    case "redeemVoucher":
                        campaignId = Integer.parseInt(s.next());
                        voucherId = Integer.parseInt(s.next());
                        localDate = s.next();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime date = LocalDateTime.parse(localDate, formatter);

                        vms.getCampaign(campaignId).redeemVoucher(voucherId, date);

                        break;

                    case "getVouchers":

                        if (vms.getUserWithId(userId) != null &&
                                vms.getUserWithId(userId).stat == User.UserType.GUEST){
                            System.out.println(vms.getUserWithId(userId).getVouchers());
                        }

                        break;

                    case "getObservers":
                        campaignId = Integer.parseInt(s.next());

                        if (vms.getUserWithId(userId) != null &&
                                vms.getUserWithId(userId).stat == User.UserType.ADMIN){
                            System.out.println(vms.getCampaign(campaignId).getObservers());
                        }

                        break;

                    case "getNotifications":

                        if (vms.getUserWithId(userId) != null &&
                                vms.getUserWithId(userId).stat == User.UserType.GUEST){
                            System.out.println(vms.getUserWithId(userId).getNotifications());
                        }

                        break;

                    case "getVoucher":
                        campaignId = Integer.parseInt(s.next());
                        break;
                }
            }

            s.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
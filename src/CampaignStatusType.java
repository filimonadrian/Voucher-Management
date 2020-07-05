public enum CampaignStatusType {
    NEW("NOU"), STARTED("INCEPUT"), EXPIRED("EXPIRAT"), CANCELLED("BAFTA");

    private final String semnificatie;

    CampaignStatusType(String s){
        semnificatie = s;
    }

    public String getSemnificatie(){
        return semnificatie;
    }
}
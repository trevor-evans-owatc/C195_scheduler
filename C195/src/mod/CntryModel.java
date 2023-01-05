package mod;

public class CntryModel {
    private int cId;
    private String cntry;

    /**
     * constructor for cntryModel
     * @param cId Int variable holding country's Identification
     * @param cntry String object holding name of Country
     */
    public CntryModel(int cId, String cntry) {
        this.cId = cId;
        this.cntry = cntry;
    }

    /**
     * getCntryId is the getter for country's identification number
     * @return cntryId Integer value of Country ID
     */
    public int getCntryId() {return cId;}

    /**
     * setCntryId is the setter for country's identification number
     * @param cId Int object holding identification number for Country
     */
    public void setCntryId(int cId) {this.cId = cId;}

    /**
     * GetCntry is the getter method for the name of the country
     * @return String object holding country's name
     */
    public String getCntry() {return cntry;}

    /**
     * SetCntry is the setter for country name
     * @param cntry String object holding name of the country
     */
    public void setCountry(String cntry) {this.cntry = cntry;}
}


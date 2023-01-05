package mod;


public class DivModel {
    private int divId;
    private String div;
    private int countryId;

    /** @param divId Int value of Division ID
     * @param div String value of Division Name
     * @param countryId Int value of Price
     */
    public DivModel (int divId, String div, int countryId) {
        this.divId = divId;
        this.div = div;
        this.countryId = countryId;
    }

    /**
     * Division Identification setter
     * @param divId Integer variable holding the division's identification number
     */
    public void setDivId(int divId) {
        this.divId = divId;
    }

    /**
     * DivID getter for division's identification number
     * @return Integer variable holding division's identification numver
     */
    public int getDivId() {return divId;}

    /**
     * Setter for division's name
     * @param div String object holding name of Division
     */
    public void setDiv(String div) {this.div = div;}

    /**
     * Getter for division's name
     * @return  String object holding Division's name
     */
    public String getDiv() {return div;}

    /**
     * setter for the integer representing the residing country
     * @param countryId integer variable holding country's identification number
     */
    public void setCountryId(int countryId) {this.countryId = countryId;}

    /**
     * getter for the integer representing the residing country
     * @return Integer variable holding the country
     */
    public int getCountryId() {return countryId;}

}
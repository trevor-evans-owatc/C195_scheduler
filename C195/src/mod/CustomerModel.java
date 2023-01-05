package mod;

public class CustomerModel {
    private int Customer_ID;
    private String Customer_Name;
    private String address;
    private String Postal_Code;
    private String Phone;
    private String Division;
    private String country;
    private int Division_ID;

    /**
     * @param Customer_ID Integer variable holding customer's identification
     * @param custName String object holding Customer's Name
     * @param address String object holding customer's Address
     * @param postalCode String object holding customer's Postal Code
     * @param phoneNum String object holding customer's Phone Number
     * @param div String object holding name of division
     * @param country String pbject holding the name of the Country
     * @param divId Int variable holing division's identification number
     */
    public CustomerModel(int Customer_ID, String custName, String address, String postalCode, String phoneNum,
                         String div, String country, int divId) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = custName;
        this.address = address;
        this.Postal_Code = postalCode;
        this.Phone = phoneNum;
        this.Division = div;
        this.country = country;
        this.Division_ID = divId;
    }

    /**
     * getter for customer's identification number
     * @return custId
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /** Setter for customer's identification number
     * @param custId Integer variable holding Customer's identification number
     */
    public void setCustId(int custId) {this.Customer_ID = Customer_ID;}

    /** Getter for customer's name
     * @return custName
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /** Setter for Customer's Name
     * @param customer_Name String object holding Customer's Name
     */
    public void setCustomer_Name(String customer_Name) {
        this.Customer_Name = customer_Name;
    }

    /** Address getter
     * @return address String object holding Address
     */
    public String getAddress() {
        return address;
    }

    /** Address setter
     * @param address String object holding Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** postalCode getter
     * @return postalCode
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /** postalCode setter
     * @param postal_Code String object holding Postal Code
     */
    public void setPostal_Code(String postal_Code) {
        this.Postal_Code = postal_Code;
    }

    /** Getter for Phone Number
     * @return phoneNum
     */
    public String getPhone() {
        return Phone;
    }

    /** Setter for Phone Number
     * @param phone String object holding Phone Number
     */
    public void setPhone(String phone) {
        this.Phone = phone;
    }

    /**
     * Division name getter
     * @return division
     */
    public String getDivision() {
        return Division;
    }

    /**
     * Setter for the name of Division
     * @param division String object holding division's name
     */
    public void setDivision(String division) {this.Division = division;}

    /** Gets Country Name
     * @return country String value of Country Name */
    public String getCountry() {
        return country;
    }

    /** Setter for name of Country Name
     * @param country String value of Country Name*/
    public void setCountry(String country) {
        this.country = country;
    }

    /** Gets Division ID
     * @return divisionId Integer value of Division ID*/
    public int getDivision_ID() {
        return Division_ID;
    }

    /** Sets Division ID
     * @param division_ID Integer value of Division ID*/
    public void setDivision_ID(int division_ID) {
        this.Division_ID = division_ID;
    }
}

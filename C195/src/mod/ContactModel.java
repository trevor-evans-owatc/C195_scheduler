package mod;

public class ContactModel {
    //Creating local variables for ContactModel
    private int Id;
    private String Name;
    private String Email;

    /**
     * Constructor
     * @param Id Integer variable holding identification number
     * @param Name String object holding contact name
     * @param Email string object holding contact email
     */
    public ContactModel(int Id, String Name, String Email) {
        this.Id = Id;
        this.Email = Email;
        this.Name = Name;
    }

    //Setters for ContactModel Object

    /**
     * Email Setter
     * @param Email String holding the email of contactModel
     */
    public void setEmail(String Email) {this.Email = Email;}

    /**
     * ID Setter
     * @param Id Integer variable holding contact identification number
     */
    public void setId(int Id) {this.Id = Id;}

    /**
     * Name Setter
     * @param Name String object holding Name for ContactModel
     */
    public void setName(String Name) {this.Name = Name;}

    // Getters for ContactModel Object

    /**
     * Email getter
     * @return Email String value Email
     */
    public String getEmail() {return Email;}

    /**
     * ID getter gets identification number
     * @return Id integer variable holding ID
     */
    public int getId() {return Id;}

    /**
     * Name getter
     * @return Name String object holding name
     */
    public String getName() {return Name;}
}


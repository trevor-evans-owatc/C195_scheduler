package mod;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class AptModel {
    // Local Variables
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDate Start;
    private LocalDateTime strTime;
    private LocalDate End;
    private LocalDateTime endTime;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;
    private String Contact_Name;

    /**
     * constructor for ApptModel
     * @param aptId Int variable to hold appointment's identification number
     * @param title String object holding the title of the appointment
     * @param descript String object describing the appointment
     * @param location String object holding appointment's location
     * @param type String object holding type of appointment
     * //@param strDate LocalDate object holding starting date
     * @param strTime LocalDateTime object holding starting time
     * //@param endDate LocalDate object holding date of appointment's end
     * @param endTime LocalDateTime object time of appointment's end
     * @param customerId Int variable holding customer's identification number
     * @param userId Int variable holding the user's identification number
     * @param contactId Int variable holding contact's identification number
     * @param contactName String object holding contact's name
     */
    public AptModel(int aptId, String title, String descript, String location,
                    String type, LocalDate strDate, LocalDateTime strTime, LocalDate endDate,
                    LocalDateTime endTime, int customerId, int userId, int contactId,
                    String contactName) {
        this.Appointment_ID = aptId;
        this.Title = title;
        this.Description = descript;
        this.Location = location;
        this.Type = type;
        this.Start = strDate;
        this.strTime = strTime;
        this.End = endDate;
        this.endTime = endTime;
        this.Customer_ID = customerId;
        this.User_ID = userId;
        this.Contact_ID = contactId;
        this.Contact_Name = contactName;
    }

    /*public AptModel(int aptId, String title, String descript, String location,
                    String type, LocalDateTime strTime,
                    LocalDateTime endTime, int customerId, int userId, int contactId,
                    String contactName) {
        this.Appointment_ID = aptId;
        this.Title = title;
        this.Description = descript;
        this.Location = location;
        this.Type = type;
        //this.Start = strDate;
        this.strTime = strTime;
        //this.End = endDate;
        this.endTime = endTime;
        this.Customer_ID = customerId;
        this.User_ID = userId;
        this.Contact_ID = contactId;
        this.Contact_Name = contactName;
    }*/
    // Setters for AptModel
    /**
     * setLocation is the setter for the location of the Appointment object
     * @param location String object holding the appointment's location
     */
    public void setLocation(String location) {this.Location = location;}

    /**
     * setContactId is the setter of the identification number for
     * contact associated with the appointment object
     * @param contact_ID Integer variable holding the contacts identification number
     */
    public void setContact_ID(int contact_ID) {this.Contact_ID = contact_ID;}

    /**
     * setApptId is the setter for the appointment object's identification number
     * @param aptId Integer variable holding identification number
     */
    public void setAptId(int aptId) {this.Appointment_ID = aptId;}

    /**
     * setStrtDate is the setter for the date that the appointment object is
     * going to start
     * @param start LocalDate object holding starting date of Appointment
     */
    public void setStart(LocalDate start) {this.Start = start;}

    /**
     * setEndDate is the setter for the ending date of the appointment object
     * @param end LocalDate object holding the date the appointment is to end
     */
    public void setEnd(LocalDate end) {this.End = end;}

    /**
     * setType is the setter for the type of appointment associated with
     * the appointment object.
     * @param type String object to reference the value of the type of appointment
     */
    public void setType(String type) {this.Type = type;}

    /**
     * setDescript is the setter for the description of the Appointment object
     * @param description String object holding description of this appointment
     */
    public void setDescription(String description) {this.Description = description;}

    /**
     * Setter for the identification number of the customer associated with
     * the appointment object
     * @param customer_ID Integer variable holding customer's identification number
     */
    public void setCustomer_ID(int customer_ID) {this.Customer_ID = customer_ID;}

    /**
     * setTitle is the setter for the title String of the Appointment object
     * @param title String object to hold the title
     */
    public void setTitle(String title) {this.Title = title;}

    /**
     * setStrTime is the setter for the time that the appointment is
     * going to start
     * @param strTime LocalDateTime object holding the starting time of the appointment
     */
    public void setStrTime(LocalDateTime strTime) {this.strTime = strTime;}

    /**
     * Setter for the time of the appointment's end
     * @param endTime LocalDateTime object holding the ending time of the appointment
     */
    public void setEndTime(LocalDateTime endTime) {this.endTime = endTime;}

    /**
     * Setter for the identification number of the contact associated with the
     * appointment object
     * @param user_ID Integer variable holding contact's identification number
     */
    public void setUser_ID(int user_ID) {this.User_ID = user_ID;}

    /**
     * setContactName is the setter for name of the contact associated with
     * the Appointment object
     * @param contactName String object holding the name of the contact
     */
    public void setContactName(String contactName) {this.Contact_Name = contactName;}

    // Getters for aptModel
    /**
     * getAptId is the getter for the appointment object's identification number
     * @return appointmentId Integer variable holding appointment identification number
     */
    public int getAppointment_ID() {return Appointment_ID;}



    /**
     * getTitle is the getter for the title associated with the appointment object
     * @return title String object holding the title of the appointment
     */
    public String getTitle() {return Title;}



    /**
     * getDescript is the getter for the description of the
     * Appointment object
     * @return String holding the description
     */
    public String getDescription() {
        return Description;}


    /**
     * getLocation is the getter for the location of the appointment
     * @return String holding the location of the appointment
     */
    public String getLocation() {return Location;}

    /**
     * getType is the getter for the type of appointment associated with
     * the Appointment object.
     * @return String holding appointment's type
     */
    public String getType() {return Type;}



    /**
     * getStrDate is the getter for the date that the appointment is to start
     * @return LocalDate referencing the appointment's start date
     */
    public LocalDate getStart() {return Start;}



    /**
     * getStrtTime is the getter for the starting time of the appointment object
     * @return LocalDateTime object of Appointment's starting time
     */
    public LocalDateTime getStrTime() {return strTime;}

    /**
     * getEndDate is the getter for the date that the appointment object is set to end
     * @return localDate holding the date of the appointment's end
     */
    public LocalDate getEnd() {return End;}

    /**
     * Getter for the time object representing when the appointment is to end
     * @return LocalDateTime object holding the end time of the appointment
     */
    public LocalDateTime getEndTime() {return endTime;}

    /**
     * Getter for the identification number of the customer associated the
     * the appointment object
     * @return customerId Integer variable holding identification number of the customer
     */
    public int getCustomer_ID() {return Customer_ID;}

    /**
     *  Getter for user's identification number when accessing the appointment
     *  object
     * @return userId Integer variable holding user's identification number
     */
    public int getUser_ID() {return User_ID;}

    /**
     * getter for identification of the contact associated with
     * the appointment object
     * @return contactId Integer variable holding the contact's identification number
     */
    public int getContact_ID() {return Contact_ID;}


    /**
     * getName is the getter for the contact's name associated with
     * the Appointment object
     * @return contactName String value of Contact Name
     */
    public String getName(){return Contact_Name;}
}


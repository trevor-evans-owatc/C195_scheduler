package control;

//DataBase imports

import dbAccess.AptDb;
import dbAccess.ContactDb;
import dbAccess.CustomerDb;
import dbAccess.UserDb;
import javafx.collections.transformation.SortedList;
import mod.AptModel;
import mod.ContactModel;
import mod.CustomerModel;
import mod.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.random.*;

/**
 * CreateApt is the controller class for creating an appointment
 */
public class CreateApt implements Initializable{
    RandomGenerator randy = new Random();
    private final int aptId = randy.nextInt();
    // time zones holders
    private ZonedDateTime StrtTimeConversion;
    private ZonedDateTime EndTimeConversion;

    // Finalized strings for alerts
    private final String businessHours = "Business Hours are between 8AM and 10PM EST";
    private final String errTitle = "Error Dialog";
    private final String loadErr = "Load Screen Error";

    //Combo Boxes
    @FXML
    private ComboBox<String> EndTimeBox;
    @FXML
    private ComboBox<String> TypeBox;
    @FXML
    private ComboBox<String> ContactBox;
    @FXML
    private ComboBox<String> StrtTimeBox;
    @FXML
    private ComboBox<Integer> UsrIdBox;
    @FXML
    private ComboBox<Integer> CustomIdBox;

    // Creating DatePicker variables
    @FXML
    private DatePicker EndDatePicker;
    @FXML
    private DatePicker StrtDatePicker;

    //Creating text fields
    @FXML
    private TextField TitleTxtField;
    @FXML
    private TextField DescripTxtField;
    @FXML
    private TextField LocationTxtField;
    @FXML
    private TextField ApntIDTxt;

    // Creating Labels
    @FXML
    private Label Title;
    @FXML
    private Label StrtTimeLbl;
    @FXML
    private Label EndTimeLbl;
    @FXML
    private Label UsrIDLbl;
    @FXML
    private Label StrtLbl;
    @FXML
    private Label EndLbl;
    @FXML
    private Label TypeLbl;
    @FXML
    private Label CustIDLbl;
    @FXML
    private Label ContactLbl;
    @FXML
    private Label LocationLbl;
    @FXML
    private Label AppIDLbl;
    @FXML
    private Label Header;
    @FXML
    private Label DescriptLbl;

    // Creating Buttons for GUI control
    @FXML
    private Button SaveButt;
    @FXML
    private Button HomeButt;
    @FXML
    private Button cxButt;

    /**
     * CustomerSelect is the action event for when a customer's identification number is selected.
     * @param event ActionEvent triggered on save button clicked
     */
    @FXML
    void CustomerSelect(ActionEvent event){
    }
    /**
     * convertToEst converts to and returns  Eastern Standard Time object
     * @param time LocalDateTime holding the sys date-time
     * @return converted ZonedDateTime object
     */
    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time,ZoneId.of("America/New_York"));
    }

    /**
     * home is the controller for HomeButt. It navigates the user to the Nav screen when event is triggered.
     * @param event ActionEvent triggered on HomeButt clicked
     */
    @FXML
    void home(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/Nav.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }
    /**
     * save is the controller for the save button. It checks the entered information for validity
     * via a call to the isValid function. If info is valid it creates and saves an appointment object
     * @param event ActionEvent triggered on save button clicked
     */
    @FXML
    void Save(ActionEvent event) throws SQLException {

        //making a call to the is valid function to check entries validity
        boolean valid = isValid(
                TitleTxtField.getText(),
                DescripTxtField.getText(),
                LocationTxtField.getText(),
                ApntIDTxt.getText()
        );

        if (valid) {
            try {
                boolean success = AptDb.createApt(
                        TitleTxtField.getText(),
                        ContactBox.getValue(),
                        DescripTxtField.getText(),
                        UsrIdBox.getSelectionModel().getSelectedItem(),
                        LocationTxtField.getText(),
                        TypeBox.getValue(),
                        LocalDateTime.of(StrtDatePicker.getValue(),
                                LocalTime.parse(StrtTimeBox.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(EndDatePicker.getValue(),
                                LocalTime.parse(EndTimeBox.getSelectionModel().getSelectedItem())),
                        CustomIdBox.getValue());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully created new appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/view/Apt.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setContentText("Load Screen Error.");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save new appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Error: had a bug allowing the user to create an appointment with the exact same start and end times as another
     * appointment.
     * Fix: created checkBusinessHr class and check Overlap method to organize my code better and better
     * ensure the code is catching potential errors.
     *
     * isValid method checks the combo box containing contacts. First it ensures
     * that it is not empty. It then, ensures all fields contain the correct
     * variable types
     * @param title String object containing Appointment Title
     * @param description String object holding description of the appointment
     * @param location String object holding location of the appointment
     * @param appointmentId String object holding Appointment's identification
     * @return Returns true if valid else false
     */
    private boolean isValid (String title, String description, String location, String appointmentId) throws SQLException {
        if (ContactBox.getSelectionModel().isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Contact Required");
            return false;
        }else if (title.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Title Required");
            return false;
        }else if (description.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Description Required");
            return false;
        }else if (location.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Location Required");
            return false;
        }else if (appointmentId.isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Appointment ID Required");
            return false;
        }else if (TypeBox.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Type Required");
            return false;
        }else if (StrtDatePicker.getValue() == null) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Start Date Required");
            return false;
        }else if (StrtTimeBox.getSelectionModel().isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Start Time Required");
            return false;
        }else if (EndDatePicker.getValue() == null){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "End Date Required");
            return false;
        }else if (EndTimeBox.getSelectionModel().isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "End Time Required");
            return false;
        } else if (CustomIdBox.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Customer ID Required");
            return false;
        }else if (UsrIdBox.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "User ID Required");
            return false;
        }

        // additional date validation

        LocalTime startTime = LocalTime.parse(StrtTimeBox.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(EndTimeBox.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Start Time Should Reside Before End Time");
            return false;
        }

        LocalDate startDate = StrtDatePicker.getValue();
        LocalDate endDate = EndDatePicker.getValue();

        if (!startDate.equals(endDate)){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Appointments should start and end on the same day");
            return false;
        }

        // Check for overlapping appointments and/or appointments outside business hours.

        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);

        /*LocalDateTime proposedAppointmentStart;
        LocalDateTime proposedAppointmentEnd;


        try {
            ObservableList<AptModel> appointments = AptDb.getByCustId(CustomIdBox.getSelectionModel().getSelectedItem());
            for (AptModel appointment: appointments) {
                proposedAppointmentStart = appointment.getStart().atTime(appointment.getStrTime().toLocalTime());
                proposedAppointmentEnd = appointment.getEnd().atTime(appointment.getEndTime().toLocalTime());

                System.out.println("New Entry: " + proposedAppointmentStart + "\nexisting apt Start: " + selectedStart);
                System.out.println("New Entry: " + proposedAppointmentEnd + "\nExisting apt End: " + selectedEnd);
                if (proposedAppointmentStart.isAfter(selectedStart) && proposedAppointmentStart.isBefore(selectedEnd)) {
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointment time overlaps with an existing appointment");
                    /*Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                } else if (proposedAppointmentEnd.isAfter(selectedStart) && proposedAppointmentEnd.isBefore(selectedEnd)) {
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointment time overlaps with an existing appointment");
                    /*Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                }else if(proposedAppointmentStart == (selectedStart)|| proposedAppointmentEnd == (selectedEnd)){
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointment time overlaps with an existing appointment");
                    return false;
                }
               /*ObservableList<AptModel> apts = AptDb.getApts();
                for (int i = 0; i < apts.size(); i++){
                    AptModel apt = apts.get(i);
                    if((proposedAppointmentStart.isBefore(apt.getStrTime())&&proposedAppointmentEnd.isAfter(apt.getStrTime()))||
                            (proposedAppointmentStart.equals(apt.getStrTime()))||(proposedAppointmentEnd).equals(apt.getEndTime())||
                            (proposedAppointmentStart.equals(apt.getEndTime()))||(proposedAppointmentEnd.equals(apt.getStrTime()))||
                            (proposedAppointmentStart.isBefore(apt.getEndTime())&&proposedAppointmentEnd.isAfter(apt.getEndTime()))) {
                        Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "This time interferes with appointment: " + apt.getName()+
                                " at " + apt.getStrTime()+ " to " + apt.getEndTime());
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // check if between business hours
        StrtTimeConversion = convertToEST(LocalDateTime.of(StrtDatePicker.getValue(),
                LocalTime.parse(StrtTimeBox.getSelectionModel().getSelectedItem())));
        EndTimeConversion = convertToEST(LocalDateTime.of(EndDatePicker.getValue(),
                LocalTime.parse(EndTimeBox.getSelectionModel().getSelectedItem())));*/

        return checkOverlap(selectedStart, selectedEnd);
    }

    /**
     * checkBusinessHr is a function that validates whether the times entered by the user fall within business hours.
     * @param strT a localDateTime object holding the entered start time.
     * @param endT a localDateTime object holding the entered end time.
     * @return false if test not passed true if tests are passed
     */
    private boolean checkBusinessHr(LocalTime strT, LocalTime endT){
        if (strT.isAfter(LocalTime.of(22,00))){
            Lert er = new Lert(Alert.AlertType.ERROR, errTitle, businessHours);
            return false;
        }else if(endT.isBefore(LocalTime.of(8,00))){
            Lert er = new Lert(Alert.AlertType.ERROR, errTitle, businessHours);
            return false;
        }else{
            return true;
        }
    }

    /**
     * checkOverlap checks if the proposed appointment overlaps with any existing appointments
     * @param strT localDateTime of the value to the start time entered by the user
     * @param endT localDateTime object holding the value of the date and time entered by the user
     * @return boolean value that is true if all tests are passed
     */
    private boolean checkOverlap(LocalDateTime strT, LocalDateTime endT) throws SQLException{

        boolean passTest = true;
        try {
            ObservableList<AptModel> appointments = AptDb.getApts();
            for(AptModel apt:appointments){
                if(apt.getStrTime().isEqual(strT)){
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointment time overlaps with an existing appointment");
                    passTest = false;
                    break;
                }else if(apt.getEndTime().isEqual(endT)){
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointment time overlaps with an existing appointment");
                    passTest = false;
                    break;
                }else if (apt.getStrTime().isBefore(strT)&&apt.getEndTime().isAfter(strT)){
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointment time overlaps with an existing appointment");
                    passTest = false;
                    break;
                }else if(apt.getStrTime().isBefore(endT)&&apt.getEndTime().isAfter(endT)){
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointment time overlaps with an existing appointment");
                    passTest = false;
                    break;
                }else if(strT.isBefore(apt.getStrTime())&& !endT.isBefore(apt.getStrTime())){
                    Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "These times overlap with: " +
                            apt.getName() + " From " + apt.getStrTime() + " to " + apt.getEndTime());
                    passTest = false;
                    break;
                }
            }
            if(passTest){
                passTest = checkBusinessHr(strT.toLocalTime(), endT.toLocalTime());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return passTest;
    }



    @FXML
    void PickStartDate(ActionEvent event) {
        EndDatePicker.setValue(StrtDatePicker.getValue());
    }


    @FXML
    void PickEndDate(ActionEvent event) {

    }

    @FXML
    void SelectStartTime(ActionEvent event) {

    }

    @FXML
    void SelectEndTime(ActionEvent event) {

    }

    @FXML
    void SelectType(ActionEvent event) {

    }

    /**
     * Cancel is the vent handler for the cancel button. It navigates to Appointments window
     * disregarding any changes made when activated
     * @param event ActionEvent activates on cancel button clicked
     */
    @FXML
    void Cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Appointments?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/Apt.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Load Screen Error");
                /*alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();*/
            }
        }
    }

    /**
     * Home is the event handler for the home home button. It navigates to the home window
     * disregarding any changes when activated..
     * @param event ActionEvent activated on home button clicked
     */
    @FXML
    void Home(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/Nav.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Home");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, errTitle, loadErr);
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();*/
        }
    }

    /**
     * addTimes populates a combo box with times set in intervals of fifteen minutes
     */
    private void addTimes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }

        StrtTimeBox.setItems(time);
        EndTimeBox.setItems(time);
    }

    /**
     * addContacts populates a combo box with contact objects' names
     */
    private void addContacts() {
        ObservableList<String> contactComboList = FXCollections.observableArrayList();

        try {
            ObservableList<ContactModel> contacts = ContactDb.getContacts();
            if (contacts != null){
                for (ContactModel contact: contacts) {
                    if (!contactComboList.contains(contact.getName())) {
                        contactComboList.add(contact.getName());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ContactBox.setItems(contactComboList);
    }

    /**
     * addId populates a combo box with customer objects' identification number
     */
    private void addId() {
        ObservableList<Integer> customerIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<CustomerModel> customers = CustomerDb.getCustomers();
            if (customers != null) {
                for (CustomerModel customer: customers) {
                    customerIDComboList.add(customer.getCustomer_ID());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CustomIdBox.setItems(customerIDComboList);
    }

    /**
     * addUserId populates a combo box with users' identification
     */
    private void addUserId() {
        ObservableList<Integer> userIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<UserModel> users = UserDb.getUsers();
            if (users != null) {
                for (UserModel user: users) {
                    userIDComboList.add(user.getUserId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UsrIdBox.setItems(userIDComboList);
    }

    /**
     * addType populates a combo box with appointment types
     */
    private void addType() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.addAll("Planning Session", "De-Briefing", "Follow-up", "Pre-Briefing", "Open Session");

        TypeBox.setItems(typeList);
    }

    /**
     *  initialize is the initializer for CreateApt control class
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTimes();
        addContacts();
        addId();
        addUserId();
        addType();



    }
}


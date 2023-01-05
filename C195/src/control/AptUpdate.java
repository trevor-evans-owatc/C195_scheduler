package control;


import dbAccess.AptDb;
import dbAccess.ContactDb;
import dbAccess.CustomerDb;
import dbAccess.UserDb;
import mod.AptModel;
import mod.ContactModel;
import mod.CustomerModel;

import mod.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;


public class AptUpdate implements Initializable {

    private ZonedDateTime StartDateTimeConversion;
    private ZonedDateTime EndDateTimeConversion;

    private static AptModel selectedAppointment;

    @FXML
    private DatePicker strDate;

    @FXML
    private DatePicker EndDate;

    @FXML
    private Button CancelButt;

    @FXML
    private Button HomeButt;

    @FXML
    private Label sTimeLbl;

    @FXML
    private Button SaveButt;

    @FXML
    private TextField AptIdField;

    @FXML
    private TextField DescripField;

    @FXML
    private TextField CustomerIdField;

    @FXML
    private TextField LocationField;

    @FXML
    private TextField TitleField;

    @FXML
    private Label EndTimeLbl;

    @FXML
    private Label DesLbl;

    @FXML
    private Label AptIdLbl;

    @FXML
    private Label TitleLbl;

    @FXML
    private Label TypeLbl;

    @FXML
    private Label LocationLbl;

    @FXML
    private Label Header;

    @FXML
    private Label EndDateLbl;

    @FXML
    private Label ContactLbl;

    @FXML
    private Label StrDateLbl;

    @FXML
    private Label CustomerIdLbl;

    @FXML
    private Label UsrIdLbl;

    @FXML
    private ComboBox<Integer> CustomerIdBox;

    @FXML
    private ComboBox<String> ContactBox;
    @FXML
    private ComboBox<Integer> UsrIdBox;

    @FXML
    private ComboBox<String> TypeBox;

    @FXML
    private ComboBox<String> strTimeBox;

    /**
     * convertToEst converts to and returns  Eastern Standard Time object
     * @param time LocalDateTime holding the sys date-time
     * @return converted ZonedDateTime object
     */
    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    private ZonedDateTime convertToTimeZone(LocalDateTime time, String zoneId) {
        return ZonedDateTime.of(time, ZoneId.of(zoneId));
    }

    @FXML
    void PickStartDate(ActionEvent event) {

    }

    @FXML
    private ComboBox<String> EndTimeBox;

    @FXML
    void PickEndDate(ActionEvent event) {

    }

    /**
     * save is the controller for the save button. It checks the entered information for validity
     * via a call to the isValid function. If info is valid it creates and saves an appointment object
     * @param event ActionEvent triggered on save button clicked
     */
    @FXML
    void Save(ActionEvent event) {
        boolean valid = validateApt(
                TitleField.getText(),
                DescripField.getText(),
                LocationField.getText(),
                AptIdField.getText()
        );

        if (valid) {
            try {

                boolean success = AptDb.updateApt(
                        ContactBox.getSelectionModel().getSelectedItem(),
                        TitleField.getText(),
                        DescripField.getText(),
                        LocationField.getText(),
                        TypeBox.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(strDate.getValue(),
                                LocalTime.parse(strTimeBox.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(EndDate.getValue(),
                                LocalTime.parse(EndTimeBox.getSelectionModel().getSelectedItem())),
                        CustomerIdBox.getSelectionModel().getSelectedItem(),
                        UsrIdBox.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(AptIdField.getText()));

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success: Appointments updated!");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/view/Apt.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
                        }
                    }
                } else {
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Update Failed");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * validateApt checks the user's entries to ensure validity of an appointment object.
     * @param title String object to hold title of appointment
     * @param description String object to hold the description of the appointment
     * @param location String object to hold the appointment's location
     * @param appointmentId String object holding the appointment's identification
     * @return True if valid else false
     */
    private boolean validateApt(String title, String description, String location, String appointmentId){
        if (ContactBox.getSelectionModel().isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "contact is required");
            return false;
        } else if (title.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Title required");
            return false;
        } else if (description.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Description required");
            return false;
        } else if (location.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Location required");
            return false;
        } else if (TypeBox.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Type required");
            return false;
        } else if (appointmentId.isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Appointment required");
            return false;
        } else if (strDate.getValue() == null) {
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Start date required");
            return false;
        }else if (strTimeBox.getSelectionModel().isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Start time required");
            return false;
        }else if (EndDate.getValue() == null){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "End date required");
            return false;
        }else if (EndDate.getValue().isBefore(strDate.getValue())) {
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Start and end dates should fall on the same day");
            return false;
        }else if (EndTimeBox.getSelectionModel().isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "End Time Required");
            return false;
        }else if (CustomerIdBox.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Customer ID Required");
            return false;
        }else if (UsrIdBox.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "User ID Required");
            return false;
        }

        // Validate dates

        LocalTime startTime = LocalTime.parse(strTimeBox.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(EndTimeBox.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                    "Appointment start time must be before end time.");
            return false;
        };

        LocalDate startDate = strDate.getValue();
        LocalDate endDate = EndDate.getValue();

        if (!startDate.equals(endDate)){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Start and end dates should fall on the same day");
            return false;
        };

        // Check for overlapping appointments

        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);

        LocalDateTime proposedAppointmentStart;
        LocalDateTime proposedAppointmentEnd;


        try {
            ObservableList<AptModel> appointments = AptDb.getByCustId(CustomerIdBox.getSelectionModel().getSelectedItem());
            for (AptModel appointment: appointments) {
                proposedAppointmentStart = appointment.getStart().atTime(appointment.getStrTime().toLocalTime());
                proposedAppointmentEnd = appointment.getEnd().atTime(appointment.getEndTime().toLocalTime());

                if (proposedAppointmentStart.isAfter(selectedStart) && proposedAppointmentStart.isBefore(selectedEnd)) {
                    Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                            "Appointments must not overlap with existing customer appointments.");
                    return false;
                } else if (proposedAppointmentEnd.isAfter(selectedStart) && proposedAppointmentEnd.isBefore(selectedEnd)) {
                    Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog",
                            "Appointments must not overlap with existing customer appointments.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // check if within business hours
        StartDateTimeConversion = convertToEST(LocalDateTime.of(strDate.getValue(), LocalTime.parse(strTimeBox.getSelectionModel().getSelectedItem())));
        EndDateTimeConversion = convertToEST(LocalDateTime.of(EndDate.getValue(), LocalTime.parse(EndTimeBox.getSelectionModel().getSelectedItem())));

        if (StartDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog",
                    "Appointments must be within business hours 8AM - 10PM EST.");
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Business houts are 8AM - 10PM EST");
            return false;
        }

        if (StartDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Business houts are 8AM - 10PM EST");
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Business houts are 8AM - 10PM EST");
            return false;
        }

        return true;
    }

    /** Method to receive selected appointment from Appointment View
     * @param appointment Selected Appointment
     */
    public static void receiveSelectedAppointment(AptModel appointment) {
        selectedAppointment = appointment;
    }

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
                Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load screen error");
            }
        }
    }

    /** Navigates to Home View
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Home Screen when clicked
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
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load screen error");
        }
    }

    /** Populates Start Time and End Time Combo Boxes in 15 minute increments
     */
    private void populateTimeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }

        strTimeBox.setItems(time);
        EndTimeBox.setItems(time);
    }

    /** Populates Contact Combo Box with Contacts List
     */
    private void populateContactComboBox() {
        ObservableList<String> contactComboList = FXCollections.observableArrayList();

        try {
            ObservableList<ContactModel> contacts = ContactDb.getContacts();

            System.out.println("Contcacts #"+contacts.size());
            if (contacts != null){
                for (ContactModel contact: contacts) {
                    System.out.println("Contact"+contact.getId()+ " " + contact.getName() + " " + contact.getEmail());
                    if (!contactComboList.contains(contact.getName())) {
                        contactComboList.add(contact.getName());
                    }
                }
            }else{System.out.println("Contacts is null");}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ContactBox.setItems(contactComboList);
    }


    /** Populates Customer ID Combo Box with Customer ID List
     */
    private void populateCustomerIDComboBox() {
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

        CustomerIdBox.setItems(customerIDComboList);
    }

    /** Populates User ID Combo Box with User ID List
     */
    private void populateUserIDComboBox() {
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

    /** Populates Type Combo Box with hardcoded List
     */
    private void populateTypeComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.addAll("Planning Session", "De-Briefing", "Follow-up", "Pre-Briefing", "Open Session");


        TypeBox.setItems(typeList);
    }

    /**
     *  Initialize populates the lists and boxes needed for the user to interact with this display
     *  Catches Exception, throws alert, and prints stacktrace.
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTimeComboBoxes();
        populateContactComboBox();
        populateCustomerIDComboBox();
        populateUserIDComboBox();
        populateTypeComboBox();

        try {
            AptModel appointment = AptDb.getById(selectedAppointment.getAppointment_ID());

            ZonedDateTime zonedStartTime =
                    convertToTimeZone(appointment.getStart().atTime(appointment.getStrTime().toLocalTime()),
                            String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ZonedDateTime zonedEndTime =
                    convertToTimeZone(appointment.getEnd().atTime(appointment.getEndTime().toLocalTime()),
                            String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));

            if (appointment != null) {
                ContactBox.getSelectionModel().select(appointment.getName());
                TitleField.setText(appointment.getTitle());
                DescripField.setText(appointment.getDescription());
                LocationField.setText(appointment.getLocation());
                TypeBox.getSelectionModel().select(appointment.getType());
                UsrIdBox.getSelectionModel().select(Integer.valueOf(appointment.getUser_ID()));
                AptIdField.setText(String.valueOf(appointment.getAppointment_ID()));
                strDate.setValue(appointment.getStart());
                strTimeBox.getSelectionModel().select(String.valueOf(zonedStartTime.toLocalTime()));
                EndDate.setValue(appointment.getEnd());
                EndTimeBox.getSelectionModel().select(String.valueOf(zonedEndTime.toLocalTime()));
                CustomerIdBox.getSelectionModel().select(Integer.valueOf(appointment.getCustomer_ID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


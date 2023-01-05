package control;


import dbAccess.AptDb;
import dbAccess.ContactDb;
import dbAccess.CustomerDb;
import mod.AptModel;
import mod.ContactModel;
import mod.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class Report implements Initializable{

    @FXML
    private RadioButton typRadButt;

    @FXML
    private ToggleGroup typOrMnthToggelGroup;
    @FXML
    private Button HomeButt;

    @FXML
    private Label Header;

    @FXML
    private AnchorPane Records2Label;

    @FXML
    private Label Records3Label;

    @FXML
    private Button GenerateButton2;

    @FXML
    private Button GenerateButton1;

    @FXML
    private Label Records1Label;

    @FXML
    private Button GenerateButton;

    @FXML
    private RadioButton MonthRadButt;

    @FXML
    private ComboBox<Integer> ContactCombo;

    @FXML
    private ComboBox<Integer> CustomerCombo;

    /**
     * Error: When the months radio button set a report displays with Zeros counted on for all months. The
     * number doesn't change corresponding to the actual number of appointments scheduled.
     * Fix: reading through my code I realized that I had unnecessary breaks in the for loop that steps through
     * each appointment object. This was causing the loop to break after just one iteration. I removed the break
     * commands resulting in the report generating and displaying properly.
     *
     *
     * genByTypOrTime, generates a report based on Type of Month
     * The report generated totals the number of appointments per Type and per Month
     * @param event Generates report based on Radio Button selected triggered on generate button clicked
     */
    @FXML
    void genByTypOrTime(ActionEvent event) {
        // list of the types to choose from
        ObservableList<String> planning = FXCollections.observableArrayList();
        ObservableList<String> debriefing = FXCollections.observableArrayList();
        ObservableList<String> followup = FXCollections.observableArrayList();
        ObservableList<String> prebriefing = FXCollections.observableArrayList();
        ObservableList<String> open = FXCollections.observableArrayList();

        // list of Months
        ObservableList<Integer> january = FXCollections.observableArrayList();
        ObservableList<Integer> february = FXCollections.observableArrayList();
        ObservableList<Integer> march = FXCollections.observableArrayList();
        ObservableList<Integer> april = FXCollections.observableArrayList();
        ObservableList<Integer> may = FXCollections.observableArrayList();
        ObservableList<Integer> june = FXCollections.observableArrayList();
        ObservableList<Integer> july = FXCollections.observableArrayList();
        ObservableList<Integer> august = FXCollections.observableArrayList();
        ObservableList<Integer> september = FXCollections.observableArrayList();
        ObservableList<Integer> october = FXCollections.observableArrayList();
        ObservableList<Integer> november = FXCollections.observableArrayList();
        ObservableList<Integer> december = FXCollections.observableArrayList();

        try {
            ObservableList<AptModel> appointments = AptDb.getApts();

            if (appointments != null) {
                for (AptModel appointment : appointments) {
                    String type = appointment.getType();
                    LocalDate date = appointment.getStart();

                    if (type.equals("Planning Session")) {
                        planning.add(type);
                    }else if(type.equals("De-Briefing")){
                        debriefing.add(type);
                    }else if(type.equals("Follow-up")){
                        followup.add(type);
                    }else if(type.equals("Pre-Briefing")){
                        prebriefing.add(type);
                    }else if(type.equals("Open Session")){
                        open.add(type);
                    }else{
                        //Throw error and reloads page
                    }
                    if (date.getMonth().equals(Month.of(1))) {
                        january.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(2))) {
                        february.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(3))) {
                        march.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(4))) {
                        april.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(5))) {
                        may.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(6))) {
                        june.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(7))) {
                        july.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(8))) {
                        august.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(9))) {
                        september.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(10))) {
                        october.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(11))) {
                        november.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(12))) {
                        december.add(date.getMonthValue());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (typRadButt.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report: number of customer Appointments there by Type");
            alert.setContentText("Total number of Customer Appointments by Type: " +
                    "\nPlanning Session: " + planning.size() +
                    "\nDe-Briefing: " + debriefing.size() +
                    "\nFollow-up: " + followup.size() +
                    "\nPre-Briefing: " + prebriefing.size() +
                    "\nOpen Session: " + open.size());
            alert.setResizable(true);
            alert.showAndWait();
        }

        if (MonthRadButt.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report: Customer Appointment Count by Month");
            alert.setContentText("Total number of Customer Appointments by Month: " +
                    "\nJanuary: " + january.size() +
                    "\nFebruary: " + february.size() +
                    "\nMarch: " + march.size() +
                    "\nApril: " + april.size() +
                    "\nMay: " + may.size() +
                    "\nJune: " + june.size() +
                    "\nJuly: " + july.size() +
                    "\nAugust: " + august.size() +
                    "\nSeptember: " + september.size() +
                    "\nOctober: " + october.size() +
                    "\nNovember: " + november.size() +
                    "\nDecember: " + december.size()
            );
            alert.showAndWait();
        }
    }

    /**
     * genContact generates appointment reports by selected contact
     *  Schedules are displayed on a dialog box per appointment on file for selected Contact
     *  @param event Generates report based on Combo Box field selected activated on Generate button clicked
     */
    @FXML
    void genContact(ActionEvent event) {
        Integer contactID = ContactCombo.getSelectionModel().getSelectedItem();
        try {
            ObservableList<AptModel> appointments = AptDb.aptGetByContact(contactID);


            if (appointments != null) {
                for (AptModel appointment: appointments) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Report: Customer Appointment by Contact ID");

                    alert.setContentText("Appointments by Contact ID #" + contactID + ": " +
                            "\nAppointment ID: " + appointment.getAppointment_ID() +
                            "\nTitle: " + appointment.getTitle() +
                            "\nType: " + appointment.getType() +
                            "\nDescription: " + appointment.getDescription() +
                            "\nStart Date: " + appointment.getStart() +
                            "\nStart Time: " + appointment.getStrTime() +
                            "\nEnd Date: " + appointment.getEnd() +
                            "\nEnd Time: " + appointment.getEndTime() +
                            "\nCustomer ID: " + appointment.getCustomer_ID() +
                            "\nUser ID: " + appointment.getUser_ID()
                    );

                    alert.setResizable(true);
                    alert.showAndWait();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * genCustId generates the total of appointments per customer ID
     * @param event Generates report based on Combo Box field selected activated on Generate button clicked
     */
    @FXML
    void genCustID(ActionEvent event) {
        Integer customerID = CustomerCombo.getSelectionModel().getSelectedItem();
        try {
            ObservableList<AptModel> appointments = AptDb.getByCustId(customerID);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report: Customer Appointment Count by Customer ID");
            alert.setContentText("Total number of Customer Appointments by Customer ID #" + customerID + ": " + appointments.size());
            alert.setResizable(true);
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Home redirects user to the Nav display screen
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
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }
    }

    /**
     * popContIDBox populates Contact ID Combo Box with Contact ID List
     */
    private void popcontIDBox() {
        ObservableList<Integer> contactIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<ContactModel> contacts = ContactDb.getContacts();
            if (contacts != null) {
                for (ContactModel contact: contacts) {
                    if (!contactIDComboList.contains(contact.getId())) {
                        contactIDComboList.add(contact.getId());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ContactCombo.setItems(contactIDComboList);
    }

    /**
     * popCustomIDBox populates Customer ID Combo Box with Customer ID List
     */
    private void popCustomIDBox() {
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

        CustomerCombo.setItems(customerIDComboList);
    }

    /** initialize, initializes the combo boxes in the Reports view.
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typRadButt.setToggleGroup(typOrMnthToggelGroup);
        MonthRadButt.setToggleGroup(typOrMnthToggelGroup);
        typRadButt.setSelected(true);
        MonthRadButt.setSelected(false);
        popCustomIDBox();
        popcontIDBox();
    }
}

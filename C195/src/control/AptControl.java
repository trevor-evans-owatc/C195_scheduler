package control;

import dbAccess.AptDb;
import mod.AptModel;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.SQLException;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

public class AptControl implements Initializable{

    // Creating a list to hold appointment model objects
    static ObservableList<AptModel> apts;

    // Creates a tableView object and the columns for the display
    @FXML
    private TableView<AptModel> AptTable;
    @FXML
    private TableColumn<?, ?> TitleCol;
    @FXML
    private TableColumn<?, ?> AptIDCol;
    @FXML
    private TableColumn<?, ?> StrtTimeCol;
    @FXML
    private TableColumn<?, ?> EndTimeCol;
    @FXML
    private TableColumn<?, ?> TypeCol;
    @FXML
    private TableColumn<?, ?> CustIDCol;
    @FXML
    private TableColumn<?, ?> UsrIDCol;
    @FXML
    private TableColumn<?, ?> ContactCol;
    @FXML
    private TableColumn<?, ?> DescriptionCol;
    @FXML
    private TableColumn<?, ?> LocationCol;



    // Controls for the buttons
    @FXML
    private Button UpdateCustButt;
    @FXML
    private Button SearchButt;
    @FXML
    private Button createAptButt;
    @FXML
    private Button HomeButt;
    @FXML
    private Button DeleteAptButt;

    //Text field for search button control
    @FXML
    private TextField SearchField;

    //Toggle group
    @FXML
    private ToggleGroup ChangeView;

    //Radio buttons for the toggle group
    @FXML
    private RadioButton AllRadButt;
    @FXML
    private RadioButton MonthRadButt;
    @FXML
    private RadioButton WeekRadButt;

    //Header Label
    @FXML
    private Label Header;

    /**
     * Error ChangeView wasn't populating the cells with weeks appointments and months appointments when
     * view buttonGroup changed.
     * Fix: I realized it lacking the code to setCellValueFactory. I added a function called
     * populateCells. That function does the work to repopulate each cell and refresh the tables display.
     * ChangeView sets Appointment Table dependent on Radio Button selected by User.
     * Catches SQL exception
     * @param event Handles the event of radio button selection, updating the window
     */
    @FXML
    void ChangeViewControl(ActionEvent event) {

        if (AllRadButt.isSelected()) {
            try {
                apts = AptDb.getApts();
                //AptTable.setItems(apts);
                populateCells(apts);

                /*AptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
                TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
                DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
                LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
                ContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
                TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
                StrtTimeCol.setCellValueFactory(new PropertyValueFactory<>("strTime"));
                EndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
                CustIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
                UsrIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

                AptTable.refresh();*/
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ChangeView.getSelectedToggle().equals(MonthRadButt) || MonthRadButt.isSelected()) {
            try {
                /*apts = AptDb.getMonthsApts();
                AptTable.setItems(apts);
                AptTable.refresh();*/
                apts = AptDb.getMonthsApts();
                populateCells(apts);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ChangeView.getSelectedToggle().equals(WeekRadButt) || WeekRadButt.isSelected()) {
            try {
                /*apts = AptDb.getWeeksApts();
                AptTable.setItems(apts);
                AptTable.refresh();*/
                apts = AptDb.getWeeksApts();
                populateCells(apts);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * populateCells populates the table cells with the appointment objects that the user has requested
     * @param appoints ObservableList of appointment model objects
     */
    private void populateCells(ObservableList<AptModel> appoints){
        AptTable.setItems(appoints);
        AptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        ContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        StrtTimeCol.setCellValueFactory(new PropertyValueFactory<>("strTime"));
        EndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        CustIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        UsrIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

        AptTable.refresh();

    }

    /**
     * CreateApt is the event handler for the create appointment button.
     * It redirects to the CreateApt view when activated
     * @param event ActionEvent activated on createAptButt clicked
     */
    @FXML
    void CreateApt(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/CreateApt.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create New Appointment");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog","Load Screen Error.");
        }
    }

    /**
     * updateApt is the controller class for update appointment button
     * It redirects to AptUpdate window when activated.
     * @param event ActionEvent activated on update button clicked
     */
    @FXML
    void updateApt(ActionEvent event) {
        AptUpdate.receiveSelectedAppointment(AptTable.getSelectionModel().getSelectedItem());

        if (AptTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene =
                        FXMLLoader.load(getClass().getResource("/view/AptUpdate.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Appointment");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog","load Screen Error");
            }
        } else {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog","You must select an appointment to update.");
        }
    }

    /**
     * delApt is the event controller for the delApt button.
     * deletes a selected appointment object from the AptDb and reloads the
     * observableLists associated with the database when activated.
     * @param event ActionEvent activated on delAptButt clicked
     */
    @FXML
    void delApt(ActionEvent event) {
        AptModel selectedAppointment = AptTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Select an appointment to delete");
        } else if (AptTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "This will delete the selected appointment. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful =
                            AptDb.delApt(AptTable.getSelectionModel().getSelectedItem().getAppointment_ID());

                    if (deleteSuccessful) {
                                String cont;
                        cont = ("Successfully deleted Appointment ID: "
                                + selectedAppointment.getAppointment_ID() + " Type: " + selectedAppointment.getType());
                        Lert al = new Lert(Alert.AlertType.INFORMATION, "Successful Delete",cont);
                        apts = AptDb.getApts();
                        AptTable.setItems(apts);
                        AptTable.refresh();
                    } else {
                        Lert al = new Lert(Alert.AlertType.ERROR,"Error Dialog","Could not delete appointment.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Home is the event handler for the home button.
     * It redirects to the Nav view when activated.
     * @param event ActionEvent active on homeButt clicked
     */
    @FXML
    void Home(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/Nav.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Navigation Screen");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog","Load Screen Error.");
        }
    }


    /**
     * SearchApt is the event handler for the search. It
     * updates the appointments list based on the search entry when activated.
     * @param event ActionEvent activates on search button clicked.
     */
    @FXML
    void SearchAppointments(ActionEvent event) {
        ObservableList<AptModel> updateTable = lookupApt(SearchField.getText());
        AptTable.setItems(updateTable);
    }

    /**
     * lookupApt gets an Appointment List based on Search val
     * @param val String value of search text
     * @return ObservableList List of Appointments
     */
    private static ObservableList<AptModel> lookupApt(String val) {
        ObservableList<AptModel> appointmentList = FXCollections.observableArrayList();

        for (AptModel appointment: apts) {
            if (appointment.getTitle().contains(val)) {
                appointmentList.add(appointment);
            } else if (Integer.toString(appointment.getAppointment_ID()).contains(val)) {
                appointmentList.add(appointment);
            }
        }
        return appointmentList;
    }

    /**
     * Error: The initialize method wasn't displaying the appointments time with the date.
     * Fix: I found that I had the setSellValueFactory loading in a date object rather than a DateTime object.
     * I changed the call to date/time object. it seems to have fixed the issue.
     *
     *
     * initialize is the initializer function for the AptControl class
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AllRadButt.setToggleGroup(ChangeView);
        WeekRadButt.setToggleGroup(ChangeView);
        MonthRadButt.setToggleGroup(ChangeView);

        AllRadButt.setSelected(true);
        WeekRadButt.setSelected(false);
        MonthRadButt.setSelected(false);
        try {
            apts = AptDb.getApts();
            populateCells(apts);


            /*AptTable.setItems(apts);
            AptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
            ContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            StrtTimeCol.setCellValueFactory(new PropertyValueFactory<>("strTime"));
            EndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            CustIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            UsrIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));*/

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

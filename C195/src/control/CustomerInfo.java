package control;


import dbAccess.AptDb;
import dbAccess.CustomerDb;
import javafx.fxml.Initializable;
import mod.AptModel;
import mod.CustomerModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerInfo implements Initializable {


    //populate an observable list with customerModel objects
    static ObservableList<CustomerModel> customers;

    @FXML
    private TextField searchTxtFld;

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private Button HomeButt;

    @FXML
    private Button SearchButt;

    @FXML
    private TableView<CustomerModel> customerTbl;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private Button CreateCustButt;

    @FXML
    private TableColumn<?, ?> cntryCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private Button DeleteCustButt;

    @FXML
    private Label Header;

    @FXML
    private TableColumn<?, ?> postalCodeCol;

    @FXML
    private TableColumn<?, ?> diviCol;

    @FXML
    private Button UpdateButt;

    @FXML
    private TableColumn<?, ?> custIdCol;

    @FXML
    private Button AptButt;

    /**
     * CreateCustomer is the controller for the create customer button is clicked. It navigates to
     * the CreateCustomer window when activated
     * @param event ActionEvent activated on click of create customer button
     */
    @FXML
    void CreateCustomer(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CreateCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create New Customer");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }
    }

    /**
     * CancelEvent is the controller class for when the cancel button is activated.
     * It redirects the user to the Nav screen.
     * @param event ActionEvent activates on cancel button clicked
     */
    @FXML
    void CancelEvent(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/Nav.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Navigation");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }
    }

    /**
     * UpdateCustomer is the controller for the update customer button
     * It navigates to the UpdateCustomer window when clicked
     * Navigates to Update Customer View when activated
     * @param event ActionEvent activates on click of the update customer button
     */
    @FXML
    void UpdateCustomer(ActionEvent event) {

        CustomerUpdate.receiveCustomer(customerTbl.getSelectionModel().getSelectedItem());

        if (customerTbl.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerUpdate.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Existing Customer");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
            }
        } else {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Please select a customer to update");
        }
    }



    /**
     * checkAppointmets validates whether a customer is allowed to be deleted
     * based on whether they have appointments
     * @param selectedCustomer Customer that user selected
     * @return true if the customer object can be deleted, else false
     */
    private boolean checkAppointments(CustomerModel selectedCustomer) {
        try {
            ObservableList appointments = AptDb.getByCustId(selectedCustomer.getCustomer_ID());
            if (appointments != null && appointments.size() > 0) {
                String apts = "";
                StringBuilder aptDis = new StringBuilder(apts);
                for(int i = 0; i<appointments.size();i++){
                    AptModel apoint = (AptModel) appointments.get(i);
                    aptDis.append("ID: " + apoint.getAppointment_ID() + "      on: " + apoint.getStart().toString() +
                            "     at: " + apoint.getStrTime().toString() + "\n");
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * AptDisplay is the event handler for the appointments button. It calls the checkAppointments class. If
     * checkAppointments returns false it displays a message stating that there are no appoints for selected customer
     * if a customer is not selected it prompts the user to select a customer to see their appointments.
     * @param event activated on AptButt clicked
     */
    @FXML
    void AptDisplay(ActionEvent event) throws SQLException {
        if(customerTbl.getSelectionModel().getSelectedItem() != null){
            if(!checkAppointments(customerTbl.getSelectionModel().getSelectedItem())){
                Lert dis = new Lert(Alert.AlertType.INFORMATION, "info", "Selected customer does not have any scheduled appointment");
            }else {
                ObservableList appointments = AptDb.getByCustId(customerTbl.getSelectionModel().getSelectedItem().getCustomer_ID());
                String apts = "";
                StringBuilder aptDis = new StringBuilder(apts);
                for (int i = 0; i < appointments.size(); i++) {
                    AptModel apoint = (AptModel) appointments.get(i);
                    aptDis.append("ID: " + apoint.getAppointment_ID() + "      on: " + apoint.getStart().toString() +
                            "     at: " + apoint.getStrTime().toString() + "\n");
                }
                Lert dis = new Lert(Alert.AlertType.INFORMATION,
                        customerTbl.getSelectionModel().getSelectedItem().getCustomer_Name() + "'s upcoming appointment",
                        aptDis.toString(), true);
            }
        }else{
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Select customer to display appointments.");
        }
    }
    /**
     * ERROR: There was a bug with deleteCustomer. It would not delete customers who did not have appointments.
     * Fix: I reversed the boolean value to check for appointments with (!) command. This has seemed to resolve the issue.
     *
     *
     * DeleteCustomer checks to ensure a customer is selected. If so, it then checks to
     * ensure that customer object is allowed to be removed. If it passes those checks
     * the removes the customer object from CustomerDb
     * @param event ActionEvent activates on click of Delete Customer button
     */
    @FXML
    void DeleteCustomer(ActionEvent event) {

        CustomerModel selectedCustomer = customerTbl.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Customer must be selected for deletion");
        } else if (customerTbl.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected customer. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {

                    boolean valid = checkAppointments(selectedCustomer);
                    if (!valid) {
                        boolean deleteSuccessful = CustomerDb.delete(customerTbl.getSelectionModel().getSelectedItem().getCustomer_ID());

                        if (deleteSuccessful) {
                            Lert info = new Lert(Alert.AlertType.INFORMATION, "Success", "Customer was successfully deleted");
                            customers = CustomerDb.getCustomers();
                            customerTbl.setItems(customers);
                            customerTbl.refresh();
                        } else {
                            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Could not delete customer");
                        }
                    } else {
                        Lert er = new Lert(Alert.AlertType.ERROR,
                                "Error Dialog", "Can't delete a customer with scheduled appointments");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * controller for the Home button. It navigates user to main window when activated
     * @param event ActionEvent activated on click of the Home button
     */
    @FXML
    void Home(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Home");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }
    }

    /**
     * SearchCustomer controller for the search cusomer button.
     * It looks up customer and upates the CustomerTable if found
     * @param event ActionEvent activated on click of Search Customer button
     */
    @FXML
    void SearchCustomers(ActionEvent event) {
        ObservableList<CustomerModel> updateTable = lookupCustomer(searchTxtFld.getText());
        customerTbl.setItems(updateTable);
    }

    /**
     * lookUpCustomer compares parameters with existing customers in the customer
     * list, then returns an observables list of sound customer info
     * @param input String object holding user's text when SearchCustomer ActionEvent activates
     * @return A List of Customers
     */
    private static ObservableList<CustomerModel> lookupCustomer(String input) {
        ObservableList<CustomerModel> customerList = FXCollections.observableArrayList();

        for (CustomerModel customer: customers) {
            if (customer.getCustomer_Name().contains(input)) {
                customerList.add(customer);
            } else if (Integer.toString(customer.getCustomer_ID()).contains(input)) {
                customerList.add(customer);
            }
        }
        return customerList;
    }

    /**
     * Initializer for CustomerInfo control class
     *  @param location resolves relative paths
     *  @param resources localize root object
     */
    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customers = CustomerDb.getCustomers();
            customerTbl.setItems(customers);
            custIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            diviCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
            cntryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
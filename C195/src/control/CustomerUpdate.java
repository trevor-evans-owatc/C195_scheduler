package control;

import dbAccess.CntryDb;
import dbAccess.CustomerDb;
import dbAccess.DivDb;
import mod.CntryModel;
import mod.CustomerModel;
import mod.DivModel;

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
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerUpdate implements Initializable {


    // creating holder for the customer object that was selected to be updated
    private static CustomerModel selectedCustomer;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PhoneLabel;

    @FXML
    private Button HomeButt;

    @FXML
    private TextField PostalCodeTextField;

    @FXML
    private TextField AddressTextField;

    @FXML
    private Button CancelButt;

    @FXML
    private TextField NameTextField;

    @FXML
    private Button SaveButt;

    @FXML
    private Label Header;

    @FXML
    private Label IDLabel;

    @FXML
    private Label DivisionLabel;

    @FXML
    private ComboBox<String> CountryCombo;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Label AddressLabel;

    @FXML
    private Label CountryLabel;

    @FXML
    private ComboBox<String> DivisionCombo;

    @FXML
    private TextField PhoneTextField;

    @FXML
    private TextField IDTextField;

    /**
     * selectCountry is a controller that populates a combo box with divisions
     * within the selected country
     * @param event ActionEvent activated on country selected
     */
    @FXML
    void SelectCountry(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<DivModel> divisions = DivDb.getByCountry
                    (CountryCombo.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (DivModel division: divisions) {
                    divisionList.add(division.getDiv());
                }
            }
            DivisionCombo.setItems(divisionList);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Save button controller, updates customer information after validating the information
     * via the validate function
     * @param event ActionEvent activated on save button clicked
     */
    @FXML
    void Save(ActionEvent event) {
        boolean valid = validateNotEmpty(
                NameTextField.getText(),
                AddressTextField.getText(),
                PostalCodeTextField.getText(),
                PhoneTextField.getText());

        if (valid) {
            try {
                boolean success = CustomerDb.updateInfo(
                        Integer.parseInt(IDTextField.getText()),
                        NameTextField.getText(),
                        AddressTextField.getText(),
                        PostalCodeTextField.getText(),
                        PhoneTextField.getText(),
                        DivisionCombo.getValue());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully updated customer");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load
                                    (getClass().getResource("/view/CustomerInfo.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "load Screen Error");
                            /*alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setContentText("Load Screen Error.");
                            alert.showAndWait();*/
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save new customer");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * validateNotEmpty steps through the required text fields and ensures that
     * the user did not leave any of them empty.
     * @param name String value of Customer Name
     * @param address String value of Customer Address
     * @param postalCode String value of Customer Postal Code
     * @param phone String value of Customer Phone Number
     * @return true if information is present and valid, else false
     */
    private boolean validateNotEmpty(String name, String address, String postalCode, String phone){
        if (name.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Name is required.");
            //alerter("Error Dialog", "Name is required.", Alert.AlertType.ERROR);
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setContentText("Name is required.");
             alert.showAndWait();*/
            return false;
        }else if (address.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Address Required");
            /*alerter("Error Dialog", "Address  is required.", Alert.AlertType.ERROR);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setContentText("Address is required.");
             alert.showAndWait();*/
            return false;
        } else if (postalCode.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Postal Code Required");
            /*alerter("Error Dialog", "Postal code is required.", Alert.AlertType.ERROR);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setContentText("Postal Code is required.");
             alert.showAndWait();*/
            return false;
        }else if (phone.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Phone Number Required");
            /*alerter("Error Dialog", "Phone Number is required.", Alert.AlertType.ERROR);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setContentText("Phone Number is required.");
             alert.showAndWait();*/
            return false;
        }else if (DivisionCombo.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Division Required");
            /*alerter("Error Dialog", "Division is required.", Alert.AlertType.ERROR);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setContentText("Division is required.");
             alert.showAndWait();*/
            return false;
        }else if (CountryCombo.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Country Required");
           /*alerter("Error Dialog", "Country is required.", Alert.AlertType.ERROR);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setContentText("Country is required.");
             alert.showAndWait();*/
            return false;
        }else{return true;}
    }

    /**
     * receiveCustomer populates the selectedCustomer object with a reference to
     * the customer object ricieved in the parameter
     * @param customer Selected Customer
     */
    public static void receiveCustomer(CustomerModel customer) {
        selectedCustomer = customer;
    }

    /**
     * cancel is the button controller for the cancel button. It redirects to the customer view
     * without making any changes to the customer object
     * @param event ActionEvent activated on click of the cancel button
     */
    @FXML
    void Cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Customers?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerInfo.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
                /*alerter("Error Dialog", "Load Screen Error", Alert.AlertType.ERROR);
                /**alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Error Dialog");
                 alert.setContentText("Load Screen Error.");
                 alert.showAndWait();*/
            }
        }
    }

    /**
     *  HomeButt redirects to the Nav page view when activated
     * @param event ActionEvent activated on homeButt clicked
     */
    @FXML
    void home(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/Nav.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Home");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
            /*alerter("Error Dialog", "Load Screen Error", Alert.AlertType.ERROR);
            /**Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setContentText("Load Screen Error.");
             alert.showAndWait();*/
        }
    }

    /**
     * setDivisionCombo populates a combo box with division objects
     */
    @FXML
    private void setDivision(){
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        try {
            ObservableList<DivModel> divisions = DivDb.getDivisions();;
            if (divisions != null) {
                for (DivModel division: divisions) {
                    divisionList.add(division.getDiv());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DivisionCombo.setItems(divisionList);
    }


    /**
     * setCountryCombo populates a combo box with country objects
     */
    private void setCountryCombo(){
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            ObservableList<CntryModel> countries = CntryDb.getCountries();;
            if (countries != null) {
                for (CntryModel country: countries) {
                    countryList.add(country.getCntry());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CountryCombo.setItems(countryList);
    }
    /**
     * initializer function for the CustomerUpdate MVC paradigm
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate dropdown for division and country
        setDivision();
        setCountryCombo();

        // Populate text fields with existing data
        IDTextField.setText(Integer.toString(selectedCustomer.getCustomer_ID()));
        NameTextField.setText(selectedCustomer.getCustomer_Name());
        PostalCodeTextField.setText(selectedCustomer.getPostal_Code());
        AddressTextField.setText(selectedCustomer.getAddress());
        PhoneTextField.setText(selectedCustomer.getPhone());
        CountryCombo.getSelectionModel().select(selectedCustomer.getCountry());
        DivisionCombo.getSelectionModel().select(selectedCustomer.getDivision());
    }
}


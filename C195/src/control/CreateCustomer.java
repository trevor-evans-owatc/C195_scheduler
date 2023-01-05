package control;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dbAccess.CntryDb;
import dbAccess.CustomerDb;
import dbAccess.DivDb;
import javafx.fxml.Initializable;
import mod.CntryModel;
import mod.DivModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreateCustomer implements Initializable {

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
     * SelectCountry populates a combo box with a list of divisions linked to the selected country
     * when the actionevent is triggered
     * @param event ActionEvent triggered on country select
     */
    @FXML
    void SelectCountry(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<DivModel> divisions = DivDb.getByCountry(CountryCombo.getSelectionModel().getSelectedItem());
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

    @FXML
    void SelectDivision(ActionEvent event) {

    }

    /** Creates Customer
     * Calls validation function
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent creates Customer if valid when Save button is clicked
     */
    @FXML
    void Save(ActionEvent event) throws SQLException {
        boolean valid = validateNotEmpty(
                NameTextField.getText(),
                AddressTextField.getText(),
                PostalCodeTextField.getText(),
                PhoneTextField.getText());

        if (valid) {
            try {
                boolean success = CustomerDb.addCustomer(
                        NameTextField.getText(),
                        AddressTextField.getText(),
                        PostalCodeTextField.getText(),
                        PhoneTextField.getText(),
                        DivisionCombo.getValue());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully created new customer");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerInfo.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Lert er = new Lert(Alert.AlertType.ERROR,"Error Dialog", "Load Screen Error");
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
     * The validates that the customer fields are not empty and are holding the
     * correct varible/object types
     * @param name String object holding the customers name
     * @param address String object holding the customers address
     * @param postalCode String object holding the customers postal code
     * @param phone String object holding the customer's phone number
     * @return returns true if valid otherwise false
     */
    private boolean validateNotEmpty(String name, String address, String postalCode, String phone){
        if (name.isEmpty()){

            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Name is required");
            return false;
        } else if (address.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Address is required");
            return false;
        } else if (postalCode.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Postal Code is required");
            return false;
        } else if (phone.isEmpty()){
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Phone Number is required");
            return false;
        } else if (DivisionCombo.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Division is required");
            return false;
        } else if (CountryCombo.getSelectionModel().isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Country is required");
            return false;
        }else{return true;}
    }

    /**
     * Cancel is the event handler for the cancel button,
     * it return's the user to the customers window without saving
     * the new customer information when the action event is triggered
     * @param event ActionEvent triggered on cancel button clicked
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
                Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
            }
        }
    }

    /**
     * Home is the event handler for the home button, it returns the user
     * to the home window without saving any changes. This occurs when the
     * action event is triggered.
     * @param event ActionEvent triggered on home button clicked
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
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }
    }

    /**
     * setDivisionCombo populates a combo box with the various divisions that
     * the user can select from.
     */
    private void setDivisionCombo(){
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
     * setCountryCombo populates a combo box with each country that the
     * user can choose from.
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
     * initialize is the initializer for the CreateCustomer controller class
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        setDivisionCombo();
        setCountryCombo();
    }
}


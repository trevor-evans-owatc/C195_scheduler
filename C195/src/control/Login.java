package control;


import dbAccess.AptDb;
import dbAccess.UserDb;
import mod.AptModel;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

interface LogActivity{public String fileName();}

public class Login implements Initializable{

    /**
     * LogActivity using a Lambda Expression it returns a report of the Login Activity
     */
    LogActivity logReport = () -> {
        return "login_activity.txt";
    };

    private ResourceBundle resourceBundle;

    //labels for controller
    @FXML
    private Label UsernameLbl;
    @FXML
    private Label Ttl;
    @FXML
    private Label DescripLbl;
    @FXML
    private Label timezonelbl;
    @FXML
    private Label LocationLbl;

    // Buttons for this controller
    @FXML
    private Button LoginButt;
    @FXML
    private Button CancelButt;

    // TextFeilds for this controller
    @FXML
    private Label TZDis;
    @FXML
    private TextField UsrNameFld;
    @FXML
    private TextField PwordFld;
    @FXML
    private Label loacalDis;


    /**
     * The login method makes use of helper functions to create a new log activity .txt file
     * Uses various try/catch statements for exception handling
     * @param event ActionEvent Logs into application when logout button is clicked
     */
    @FXML
    void Login(ActionEvent event) {
        validateName(UsrNameFld.getText());
        validatePassword(PwordFld.getText());

        setFile();

        try {
            boolean isValid = UserDb.validateCredentials(UsrNameFld.getText(), PwordFld.getText());

            // Tester printing out whether the correct boolean value got returned
            if (isValid) {
                SuccessfulLogin();
                try {
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/view/Nav.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Navigation Screen");
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();

                    Lert er = new Lert(Alert.AlertType.ERROR,"errorDialog","loadScreenError");
                }
            } else {

                loginFailed();
                Lert er = new Lert(Alert.AlertType.ERROR,"errorDialog","incorrectUsernamePassword");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ValidateName field is empty alerts user; so to avoid
     * NullPointerExceptions
     * @param username Text Field Value for Username
     */
    private void validateName(String username) {
        if (username.isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR,"errorDialog", "usernameRequired");
        }
    }

    /**
     * ValidatePassword alerts the user if Pword field is empty;
     * avoiding NullPointerExceptions
     * @param password String containing input in PwordFld
     */
    private void validatePassword(String password) {
        if (password.isEmpty()) {
            Lert er = new Lert(Alert.AlertType.ERROR, "errorDialog","passwordRequired");
        }
    }

    /**
     * setFile function checks if a logReport file exists, if not,
     * it creates a login_activity.txt file
     * It handles exceptions and prints stacktrace.
     * Retrieves file name value from Lambda Expression
     * @exception IOException
     */
    private void setFile() {
        try {
            File newfile = new File(logReport.fileName());
            if (newfile.createNewFile()) {
                System.out.println("New File: " + newfile.getName() + " created");
            } else {
                System.out.println("File Location: " + newfile.getPath() + " already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * AlertApt alerts the user of upcoming appointments within 15 minutes.
     * It will handle related SQL exceptions
     */
    private void alertApt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimePlus15 = localDateTime.plusMinutes(15);

        ObservableList<AptModel> upcomingAppointments = FXCollections.observableArrayList();


        try {
            ObservableList<AptModel> appointments = AptDb.getApts();

            if (appointments != null) {
                for (AptModel appointment : appointments) {
                    if (appointment.getStrTime().isAfter(localDateTime) &&
                            appointment.getStrTime().isBefore(localDateTimePlus15)) {
                        upcomingAppointments.add(appointment);

                        if (Locale.getDefault().getLanguage().equals("fr") ||
                         Locale.getDefault().getLanguage().equals("en")) {
                         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle(resourceBundle.getString("appointmentAlert"));
                         alert.setContentText(
                         resourceBundle.getString("lessThanFifteenMin") +
                         "\n" +
                         resourceBundle.getString("appointmentId") +
                         " " +
                         +appointment.getAppointment_ID() +
                         "\n" +
                         resourceBundle.getString("date") +
                         " " +
                         appointment.getStart() +
                         "\n" +
                         resourceBundle.getString("time") +
                         " " +
                         appointment.getStrTime().toLocalTime());
                         alert.setResizable(true);
                         alert.showAndWait();
                         }
                    }
                }

                if (upcomingAppointments.size() < 1) {
                    if (Locale.getDefault().getLanguage().equals("fr") ||
                     Locale.getDefault().getLanguage().equals("en")) {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle(resourceBundle.getString("appointmentAlert"));
                     alert.setContentText(resourceBundle.getString("noUpcomingAppointments"));
                     alert.setResizable(true);
                     alert.showAndWait();
                     }
                }
            }
            else {
                if (Locale.getDefault().getLanguage().equals("fr") ||
                 Locale.getDefault().getLanguage().equals("en")) {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle(resourceBundle.getString("appointmentAlert"));
                 alert.setContentText(resourceBundle.getString("noUpcomingAppointments"));
                 alert.setResizable(true);
                 alert.showAndWait();
                 }
            }

        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * successfulLogin retrieves file name value from Lambda Expression and informs
     * user of a file login success
     */
    private void SuccessfulLogin() {

        alertApt();

        try {
            FileWriter fileWriter = new FileWriter(logReport.fileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Successful Login: " + UsrNameFld.getText() + " Password: " +
                    PwordFld.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loginFailed writes failed logins to the login_activity.txt file
     * Retrieves file name value from Lambda Expression
     * @throws IOException e to handle any related file input/output exceptions
     */
    private void loginFailed() {
        try {
            FileWriter fileWriter = new FileWriter(logReport.fileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Failed Login: " + UsrNameFld.getText() + "\nPassword: " + PwordFld.getText() +
                    "\nTimestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * CancelButt is the event handler for the cancel button it redirects to an app termination
     * prompt dialog box when activated.
     * @param event ActionEvent activated on cancel button clicked
     */
    @FXML
    void CancelButt(ActionEvent event) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("cancelError"));
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * initialize is the initializer for login controller
     * @param location Location to resolve relative paths
     * @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {

            Ttl.setText(resourceBundle.getString("title"));
            LocationLbl.setText(resourceBundle.getString("location"));
            loacalDis.setText(resourceBundle.getString("country"));
            timezonelbl.setText(resourceBundle.getString("timezone"));
            TZDis.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            LoginButt.setText(resourceBundle.getString("login"));
            CancelButt.setText(resourceBundle.getString("cancel"));
        }
    }
}

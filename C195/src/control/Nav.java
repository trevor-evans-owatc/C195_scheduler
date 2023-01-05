package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Nav {

    /**
     * LogActivity handles the txt file name using a Lambda expression
     */
    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };

    //FXML controller objects
    @FXML
    private Label Header;

    @FXML
    private Button AptsButt;

    @FXML
    private Button CustomersButt;

    @FXML
    private Button ReportsButt;

    @FXML
    private Button LogoutButt;

    /**
     * Logout method allows the user to logout. it calls the createFile and logoutSuccess
     * methods add the activity to a log file
     * @param event ActionEvent Logs out of application when logout button is clicked
     */
    @FXML
    void Logout(ActionEvent event) {
        createFile();
        logoutSuccess();

        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }
    }

    /**
     * The logoutSuccess function retrieves from lambda expression to help write
     * write the users logout to login_activity.txt
     */
    private void logoutSuccess() {
        try {
            FileWriter fileWriter = new FileWriter(logActivity.fileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Successful Logout: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create file retrives from Lambda expression to help create login_activity.txt file
     */
    private void createFile(){
        try {
            File newfile = new File(logActivity.fileName());
            if (newfile.createNewFile()) {
                System.out.println("File created:" + newfile.getName());
            } else {
                System.out.println("File already exists. Location: "+ newfile.getPath());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /** Customers navigates to the customer View when action event occurs
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Customers Screen when clicked
     */
    @FXML
    void Customers(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerInfo.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }
    }

    /** Appointments navigates to the Appointment view when action event occurs
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Appointments Screen when clicked
     */
    @FXML
    void Appointments(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/Apt.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointments");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");

        }
    }

    /** Reports navigates to the the reports view when action event occurs
     * @param event ActionEvent navigates to Reports Screen when clicked
     */
    @FXML
    void Reports(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Reports");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Lert er = new Lert(Alert.AlertType.ERROR, "Error Dialog", "Load Screen Error");
        }

    }
}
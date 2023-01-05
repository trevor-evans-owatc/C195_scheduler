package dbAccess;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mod.AptModel;
import mod.ContactModel;
import java.time.LocalDateTime;

/**
 * AptDb accesses and manipulates data pertaining to the appointment objects.
 */
public class AptDb {

    /**
     * getApts gets a list of all appointment objects.
     * @return ObservableList of appointment objects
     * @throws SQLException
     */
    public static ObservableList<AptModel> getApts() throws SQLException {
        ObservableList<AptModel> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                AptModel newAppointment = new AptModel(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * getMonthsApts is an appointment getter that retrieves a list
     * of appointment objects created in the past thirty days
     * @return ObservableList of appointments made in the past 30dy
     * @throws SQLException
     */
    public static ObservableList<AptModel> getMonthsApts() throws SQLException {
        ObservableList<AptModel> appointments = FXCollections.observableArrayList();


        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime lastMonth = todaysDate.minusDays(30);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start < ? AND Start > ?;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setDate(1, java.sql.Date.valueOf(todaysDate.toLocalDate()));
        preparedStatement.setDate(2, java.sql.Date.valueOf(lastMonth.toLocalDate()));

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                AptModel newAppointment = new AptModel(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * getWeeksApts is a getter for appointment objects.
     * It retrieves a list of the appointments from the last seven days
     * @return ObservableList of appointments made in the past 7dy
     * @throws SQLException
     */
    public static ObservableList<AptModel> getWeeksApts() throws SQLException {
        ObservableList<AptModel> appointments = FXCollections.observableArrayList();

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime lastWeek = currentDate.minusDays(7);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start < ? AND Start > ?;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setDate(1, java.sql.Date.valueOf(currentDate.toLocalDate()));
        preparedStatement.setDate(2, java.sql.Date.valueOf(lastWeek.toLocalDate()));

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                AptModel newAppointment = new AptModel(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * The CreateApt method accepts the required variables in its
     * parameters needed to create an Appointment object.
     * @param title Appointment String object value
     * @param contName String value of Appointment Contact Name
     * @param description String value of Appointment Description
     * @param usrID Int value of User ID
     * @param location String value of Appointment Location
     * @param type String value of Appointment Type
     * @param start LocalDateTime value holding start date/time for the mew Appointment
     * @param end LocalDateTime value holding End for appointment
     * @param customerId Int value holding CustomerId
     * @return true if new appointment was created successfully, otherwise it returns false
     * @throws SQLException To catch SQLException.
     */
    public static boolean createApt(String title, String contName, String description,
                                    int usrID, String location, String type,
                                    LocalDateTime start, LocalDateTime end, int customerId)
            throws SQLException {

        ContactModel cont = ContactDb.getByName(contName);

        String insertState = "INSERT INTO APPOINTMENTS(Title, Description, Location, Type, Start, End, " +
                "Customer_ID, User_ID, Contact_ID) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), insertState);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, usrID);
        preparedStatement.setInt(9, cont.getId());

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Changes in rows: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * delApt accepts an ID of an appointment in its parameters.
     * It then searches the appointments DB.
     * If id is found it deletes the associated appointment object.
     * @param appointmentId Int value holding the appointment ID
     * @return True if the appointment was found and deleted else false
     * @throws SQLException To catch SQLException.
     */
    public static boolean delApt(int appointmentId) throws SQLException {
        String insertStatement = "DELETE from appointments WHERE Appointment_Id=?";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setInt(1, appointmentId);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * updateApt accepts the values of an appointment Object. It then updates the new information
     * to the specified object.
     * @param contName String object to hold name
     * @param title String object to hold the title
     * @param descrip String object to hold a description of the appointment
     * @param location String object holding the persons location
     * @param type String object to hold explanation of appointment type
     * @param start LocalDateTime holds the date/time the appointment is set to start
     * @param end LocalDateTime holds date/time the appointment is set to end
     * @param customerId Integer value holding customers unique identification
     * @param userID Integer holding the users unique identification
     * @param aptId Integer holding the appointment's unique Identification
     * @return true if the appointment was successfully updated else returns false
     * @throws SQLException To handle SQL Exceptions
     */
    public static boolean updateApt(String contName, String title, String descrip, String location,
                                    String type, LocalDateTime start, LocalDateTime end, Integer customerId,
                                    Integer userID, Integer aptId) throws SQLException {
        ContactModel cont = ContactDb.getByName(contName);

        String updateStatement =
                "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID = ?;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), updateStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, descrip);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, cont.getId());
        preparedStatement.setInt(9, userID);
        preparedStatement.setInt(10, aptId);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Changes made at Row: " + preparedStatement.getUpdateCount());
            }
            else {
                System.out.println("No changes were made.");
            }
            return true;
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * The getByCustId method retrieves appointments associated with the customer's
     * identification number, accepted in its parameters.
     * @param CustId integer value to the Identification number of the customer
     * @return AptList an ObservableList of Appointments
     * @throws SQLException To handle SQL exceptions
     */
    public static ObservableList<AptModel> getByCustId(int CustId) throws SQLException {
        ObservableList<AptModel> appointments = FXCollections.observableArrayList();

        String queryStatement =
                "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID=?;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setInt(1, CustId);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // using a while loop to step through result set
            while (resultSet.next()) {
                AptModel newAppointment = new AptModel(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * GetById retrieves an appointment object associated with the AptId
     * @param AptId Integer that holds the appointment Identification number
     * @return thisApt The found Appointment object
     * @throws SQLException To handle SQL Exceptions
     */
    public static AptModel getById(int AptId) throws SQLException {

        String queryStatement =
                "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Appointment_ID=?;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setInt(1, AptId);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Step through the resultSet
            while (resultSet.next()) {
                AptModel thisApt = new AptModel(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                return thisApt;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /** aptGetByContact method retrieves an appointment by the contId
     * @param contId Integer value holding the contact Identification number
     * @return ObservableList List of Appointments
     * @throws SQLException Catches SQLException, prints stacktrace, and error message.
     */
    public static ObservableList<AptModel> aptGetByContact(int contId) throws SQLException {
        ObservableList<AptModel> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE a.Contact_ID=?;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setInt(1, contId);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Using a while loop to step through the result
            while (resultSet.next()) {
                AptModel newAppointment = new AptModel(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

}


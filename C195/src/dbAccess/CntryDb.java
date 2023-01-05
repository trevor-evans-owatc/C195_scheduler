package dbAccess;

import mod.CntryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CntryDb class handles the database access of the Country Collection.
 */
public class CntryDb {

    /**
     * getCountries is a getter for a list of country objects read out of a
     * database
     * @return ObservableList containing all country objects from query statement
     * @throws SQLException to handle SQL errors
     */
    public static ObservableList<CntryModel> getCountries() throws SQLException {
        ObservableList<CntryModel> countries = FXCollections.observableArrayList();

        String queryState = "SELECT * FROM countries;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryState);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                CntryModel newCountry = new CntryModel(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );

                countries.add(newCountry);
            }
            return countries;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * getCountryId queries countries collection and returns the country
     * object that matches the name accepted in its parameters.
     * @param cntry String Object referencing the country's name
     * @return Country Object
     * @throws SQLException to handle SQL errors
     */
    public static CntryModel getCountryId(String cntry) throws SQLException {

        String queryStatement = "SELECT * FROM countries WHERE Country=?";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, cntry);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                CntryModel newCountry = new CntryModel(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                return newCountry;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
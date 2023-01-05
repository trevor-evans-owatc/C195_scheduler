package dbAccess;

import mod.CntryModel;
import mod.DivModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivDb {
    /**
     * getDevisions gets an observable list populated with the division objects
     * @return ObservableList containing Divisions
     * @throws SQLException to handle SQL related errors
     */
    public static ObservableList<DivModel> getDivisions() throws SQLException {
        ObservableList<DivModel> divisions = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM first_level_divisions;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {

                DivModel newDivision = new DivModel(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("COUNTRY_ID")
                );

                divisions.add(newDivision);
            }
            return divisions;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * getId is the getter for division's identification number paired to
     * the divisions name.
     * @param division String object referencing a Division's Name
     * @return Division Object
     * @throws SQLException to handle SQL related errors
     */
    public static DivModel getId(String division) throws SQLException {
        String queryStatement = "SELECT * FROM first_level_divisions WHERE Division=?";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, division);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                DivModel newDivision = new DivModel(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("COUNTRY_ID")
                );
                return newDivision;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * getByCountry gets a observable list populated with division objects
     * that reside with a referenced country
     * @param country String object referencing a country's Name
     * @return ObservableList containing Division Objects
     * @throws SQLException to handle SQL related errors
     */
    public static ObservableList<DivModel> getByCountry(String country) throws SQLException {
        CntryModel newCountry = CntryDb.getCountryId(country);

        ObservableList<DivModel> divisions = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID=?;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setInt(1, newCountry.getCntryId());

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                DivModel newDivision = new DivModel(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("COUNTRY_ID")
                );

                divisions.add(newDivision);
            }
            return divisions;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}


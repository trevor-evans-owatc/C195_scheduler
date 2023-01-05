package dbAccess;

import java.sql.*;

public class MakeQuery {
    private static PreparedStatement statement;

    /**
     * SetPrepared statement is the setter for PreparedStatement objects
     * @param connect Database Connection
     * @param sqlStatement SQL Statement
     * @throws SQLException to handle SQL related runtime errors
     */
    public static void setPreparedStatement(Connection connect, String sqlStatement) throws SQLException {
        statement = connect.prepareStatement(sqlStatement);
    }

    /**
     * Prepared Statement getter
     * @return PreparedStatement
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}

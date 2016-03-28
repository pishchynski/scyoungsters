package org.ctttest.service;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.sql.*;

import com.mysql.jdbc.MysqlErrorNumbers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.sql.DriverManager.*;

/**
 * Created by Pavel Pishchynski on 10.03.2016.
 */
@WebService()
public class NamesService {
    private static final Logger logger = LogManager.getLogger(NamesService.class);

    @WebMethod
    public String save(int number, String name) {
        logger.info("Method 'save' called.");
        String query = "INSERT INTO users (number, name)" + " VALUES (?, ?)";
        try{
            Connection conn = connectToDB();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, number);
            preparedStmt.setString(2, name);
            preparedStmt.execute();
            logger.info("SQL Query executed!");
            conn.close();
        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                logger.error("Tried to insert duplicate number into DB!");
                return "ERROR";
            }
            logger.error(
                    "SQL error message: " + e.getMessage() +
                    "\nError code:" + e.getErrorCode() +
                    "\nSQL State:" + e.getSQLState() +
                    "\n" + e
            );
            return "MySQL error! See logs for more info.";
        }
        logger.info("New entry successful added to DB.");
        return "OK";
    }

    private Connection connectToDB() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = null;
            conn = getConnection("jdbc:mysql://localhost:3306/ctttest", "root", "");
            logger.info("Connection to DB resolved");
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return conn;
    }

    public static void main(String[] argv) {
        Object implementor = new NamesService();
        String address = "http://localhost:9000/NamesService";
        Endpoint.publish(address, implementor);
    }
}

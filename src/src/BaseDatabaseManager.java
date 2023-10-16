import java.sql.*;

/**
 *
 * @author oguzy
 */
public abstract class BaseDatabaseManager {
    public abstract Connection connect(String username, String password);
    public abstract String[][] getTurkishWordFromEnglish(Connection conn, String EnglishWord);
    public abstract String[][] getEnglishWordFromTurkish(Connection conn, String TurkishWord);
}

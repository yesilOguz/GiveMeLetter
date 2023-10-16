
import java.sql.Connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oguzy
 */
public class MySQLDatabaseManager extends BaseDatabaseManager{
        
    @Override
    public Connection connect(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/tr-en";
        
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            
            conn = null;
        }
        
        return conn;    
    }
    
        private String[][] getTranslate(Connection conn, String word, int translateIdColumn,
            tableNames searchForIdTable, tableNames searchForTranslateTable){
        
        try {
            Statement statement = conn.createStatement();            
            Statement statementForCount = conn.createStatement();
            
            int sizeOfStatements;
            int i = 0;
            
            String findEnglishSql = " FROM " + searchForIdTable.toString() + " WHERE word LIKE '%" + word + "%'";
            
            ResultSet countOfFindEnglish =
                    statementForCount.executeQuery("SELECT COUNT(*)" + findEnglishSql);
            
            countOfFindEnglish.next();
            sizeOfStatements = countOfFindEnglish.getInt(1);
            
            ResultSet findEnglish = 
                    statement.executeQuery("SELECT *" + findEnglishSql);
            
            String[][] translatedTexts = new String[2][sizeOfStatements];
            
            Statement whileStatement = conn.createStatement();
                        
            while(findEnglish.next()){
                String id = findEnglish.getString(1);
                String wordInEnglish = findEnglish.getString(2);
                
                translatedTexts[0][i] = wordInEnglish;
                
                ResultSet findTranslateId = null;
                if(searchForIdTable == tableNames.english){
                    findTranslateId = 
                        whileStatement.executeQuery("SELECT * FROM translate WHERE english_id = '" + id + "'");
                }
                else{
                    findTranslateId = 
                        whileStatement.executeQuery("SELECT * FROM translate WHERE turkish_id = '" + id + "'");
                }
                
                findTranslateId.next();
                String translatedId = findTranslateId.getString(translateIdColumn); // 4 for turkish 3 for english

                ResultSet translatedSet = 
                        whileStatement.executeQuery("SELECT * FROM " + searchForTranslateTable.toString() + " WHERE id = '" + translatedId + "'");

                translatedSet.next();
                translatedTexts[1][i] = translatedSet.getString(2);
                //System.out.println(i);
                i++;
            }
            
            return translatedTexts;
            
        } catch (SQLException ex) {
            // do something
            ex.printStackTrace();
            return null;
        } 
        
    }
    
    @Override
    public String[][] getTurkishWordFromEnglish(Connection conn, String EnglishWord) {
        
        return getTranslate(conn, EnglishWord, 4,
                tableNames.english, tableNames.turkish);
        
    }

    @Override
    public String[][] getEnglishWordFromTurkish(Connection conn, String TurkishWord) {
        return getTranslate(conn, TurkishWord, 3,
                tableNames.turkish, tableNames.english);
    }
    
    enum tableNames{
        turkish,
        english
    }
    
}

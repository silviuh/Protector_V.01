package game_managers.db;

import Constants.Constants;
import com.google.gson.Gson;
import ecs_container.Actors.enemies.Enemy;
import ecs_container.towers.Tower;
import factories.EnemyFactory;
import graphic_context.SpriteSheet;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class DBManager {
    private static      DBManager dbManager = null;
    public static final String    DB_SERVER = "org.sqlite.JDBC";
    public static final String    DB_NAME   = "jdbc:sqlite:ProtectorDB.db";

    private PreparedStatement preparedStatement = null;
    private Statement         statement         = null;
    private Connection        connection        = null;

    private static ReentrantLock singletonLock;

    private DBManager() {
        try {
            initDataBase();
        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
        }
    }

    public static DBManager getInstance() {
        singletonLock = new ReentrantLock();
        if (dbManager == null) {
            try {
                singletonLock.lock();
                dbManager = new DBManager();
            } finally {
                singletonLock.unlock();
            }
        }
        return dbManager;
    }

    public void openConnection() throws ClassNotFoundException, SQLException {
        Class.forName( DBManager.DB_SERVER );
        this.connection = DriverManager.getConnection( DBManager.DB_NAME );
        this.connection.setAutoCommit( false );
    }

    public void closeConnection() throws SQLException {
        this.connection.commit();
        this.connection.close();
    }

    private void initDataBase() throws SQLException {
        try {
            Class.forName( DBManager.DB_SERVER );
            this.connection = DriverManager.getConnection( DBManager.DB_NAME );
            this.connection.setAutoCommit( false );

            this.statement = connection.createStatement();
            createTable( "MapFiles" );
            createTable( "HighScores" );
            createTable( "GameSavings" );

            /*
            INSERTIntoMapFiles(
                    "C:\\Users\\silviu\\IdeaProjects\\Protector_Maven\\src\\main\\java\\config_files\\level_1.txt",
                    1,
                    200
            );
             */
            // SELECTFileContent( 1 );

            /*
             INSERTIntoMapFiles(
                    "C:\\Users\\silviu\\IdeaProjects\\Protector_Maven\\src\\main\\java\\config_files\\level_1.txt",
                    2
            );
             INSERTIntoMapFiles(
                    "C:\\Users\\silviu\\IdeaProjects\\Protector_Maven\\src\\main\\java\\config_files\\level_1.txt",
                    3
            );
             */
            /*
            INSERTIntoHighScores( 200 );
            INSERTIntoHighScores( 400 );
            INSERTIntoHighScores( 600 );
            INSERTIntoHighScores( 50 );
            INSERTIntoHighScores( 1000 );
            INSERTIntoHighScores( 4000 );
            INSERTIntoHighScores( 250 );
            SELECTHighScores( 4 );
        */



            /*
            ArrayList< Enemy > enemies = new ArrayList<>();
            int                i       = 0;
            while (i < 100) {
                Constants.enemyType currentEnemyType = Constants.enemyType.values()[new Random().nextInt( ( int ) Constants.enemyType.values().length )];
                enemies.add(
                        EnemyFactory.createInstance(
                                null, // 11
                                currentEnemyType
                        )
                );
                i++;
            }
             */

            /*
            INSERTIntoGameSavings(
                    "enemies",
                    "towers",
                    40,
                    2000,
                    1,
                    ( float ) 255.5
            );

            SELECTGameSavings(
                    "05:00:24",
                    "19-05-2020"
            );
             */


        } catch ( ClassNotFoundException | SQLException throwables ) {
            throwables.printStackTrace();
        } finally {
            this.statement.close();
            this.connection.commit();
            this.connection.close();
        }
        System.out.println( "DBManager was initialized successfully..." );
    }


    private void createTable(String tableName) throws SQLException {
        switch (tableName) {
            case "MapFiles": {
                this.preparedStatement = connection.prepareStatement( "CREATE TABLE MapFiles " +
                        "(LEVELNUMBER INT NOT NULL," +
                        "NAME TEXT NOT NULL," +
                        "CONTENT TEXT NOT NULL," +
                        "NR_OF_MONSTERS_PER_LEVEL INT NOT NULL" +
                        ")"
                );
                this.preparedStatement.executeUpdate();
                break;
            }
            case "HighScores": {
                this.preparedStatement = connection.prepareStatement(
                        "CREATE TABLE HighScores " +
                                "(" +
                                "CURRENT_TIME CHAR NOT NULL," +
                                "DATE CHAR NOT NULL," +
                                "SCORE INT NOT NULL" +
                                ")"
                );
                this.preparedStatement.executeUpdate();
                break;
            }
            case "GameSavings": {
                this.preparedStatement = connection.prepareStatement(
                        "CREATE TABLE GameSavings " +
                                "(" +
                                "CURRENT_TIME CHAR NOT NULL," +
                                "DATE CHAR NOT NULL," +
                                "SCORE INT NOT NULL," +
                                "LIFE INT NOT NULL," +
                                "LEVEL INT NOT NULL," +
                                "MONEY REAL NOT NULL," +
                                "TOWERS CHAR  NULL," + // ! make it not null
                                "MOBS CHAR NOT NULL" +
                                ")"
                );
                this.preparedStatement.executeUpdate();
                break;
            }
        }
        this.preparedStatement.close();
    }

    public void INSERTIntoMapFiles(String fileName, int levelNumber, int numberOfMonsters) throws SQLException {
        String fileContent = null;

        if (fileName != null) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream( fileName );
            } catch ( FileNotFoundException e ) {
                e.printStackTrace();
            }
            try {
                fileContent = IOUtils.toString( fileInputStream, "UTF-8" );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        this.preparedStatement = connection.prepareStatement(
                "INSERT INTO MapFiles " +
                        "(LEVELNUMBER, NAME, CONTENT, NR_OF_MONSTERS_PER_LEVEL) VALUES(?, ?, ?, ?);"
        );
        this.preparedStatement.setInt( 1, levelNumber );
        this.preparedStatement.setString( 2, fileName );
        this.preparedStatement.setString( 3, fileContent );
        this.preparedStatement.setInt( 4, numberOfMonsters );
        this.preparedStatement.executeUpdate();
    }

    public String SELECTFileContent(int levelNumber) throws SQLException {
        this.preparedStatement = connection.prepareStatement(
                "SELECT CONTENT FROM MapFiles WHERE LEVELNUMBER  = ?;"
        );
        this.preparedStatement.setInt( 1, levelNumber );
        ResultSet resultSet = this.preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getString( "CONTENT" );
    }

    public int SELECTNR_OF_MONSTERS_PER_LEVEL(int levelNumber) throws SQLException {
        this.preparedStatement = connection.prepareStatement(
                "SELECT NR_OF_MONSTERS_PER_LEVEL FROM MapFiles WHERE LEVELNUMBER  = ?;"
        );
        this.preparedStatement.setInt( 1, levelNumber );
        ResultSet resultSet = this.preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt( "NR_OF_MONSTERS_PER_LEVEL" );
    }

    public void INSERTIntoHighScores(int highScore) throws SQLException {
        DateTimeFormatter formatter   = DateTimeFormatter.ofPattern( "HH:mm:ss" );
        String            currentTime = LocalTime.now().format( formatter );
        formatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
        String currentDate = LocalDate.now().format( formatter );

        this.preparedStatement = connection.prepareStatement(
                "INSERT INTO HighScores " +
                        "(CURRENT_TIME, DATE, SCORE) VALUES(?, ?, ?);"
        );
        this.preparedStatement.setString( 1, currentTime );
        this.preparedStatement.setString( 2, currentDate );
        this.preparedStatement.setInt( 3, highScore );
        this.preparedStatement.executeUpdate();
    }

    public ArrayList< Integer > SELECTHighScores(int lastNrOfScores) throws SQLException {
        ArrayList< Integer > scores = new ArrayList<>();

        this.preparedStatement = connection.prepareStatement(
                "SELECT SCORE FROM HighScores ORDER BY SCORE DESC LIMIT ?"
        );
        this.preparedStatement.setInt( 1, lastNrOfScores );
        ResultSet resultSet = this.preparedStatement.executeQuery();

        while (resultSet.next()) {
            int currentScore = resultSet.getInt( "SCORE" );
            scores.add( currentScore );
        }

        for (Integer score : scores) {
            System.out.println( "SCORE: " + score );
        }

        resultSet.close();
        return scores;
    }

    public void INSERTIntoGameSavings(String serializedEnemies, String serializedTowers, int currentLife,
                                      int currentScore, int currentLevel, float currentMoney) throws SQLException {
        DateTimeFormatter formatter   = DateTimeFormatter.ofPattern( "HH:mm:ss" );
        String            currentTime = LocalTime.now().format( formatter );
        formatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
        String currentDate = LocalDate.now().format( formatter );

        this.preparedStatement = connection.prepareStatement(
                "INSERT INTO GameSavings " +
                        "(CURRENT_TIME, DATE, SCORE, LIFE, LEVEL, MONEY, TOWERS, MOBS) VALUES(?, ?, ?, ?, ?, ?, ?, ?);"
        );

        this.preparedStatement.setString( 1, currentTime );
        this.preparedStatement.setString( 2, currentDate );
        this.preparedStatement.setInt( 3, currentScore );
        this.preparedStatement.setInt( 4, currentLife );
        this.preparedStatement.setInt( 5, currentLevel );
        this.preparedStatement.setFloat( 6, currentMoney );
        this.preparedStatement.setString( 7, serializedEnemies );
        this.preparedStatement.setString( 8, serializedTowers );
        this.preparedStatement.executeUpdate();
    }

    // <String, ?>
    public HashMap< String, Object > SELECTGameSavings(String time, String date) throws SQLException {
        HashMap< String, Object > containerPackage = new HashMap< String, Object >();

        // SELECT * FROM GameSavings WHERE CURRENT_TIME = ?, DATE = ?;
        this.preparedStatement = connection.prepareStatement(
                "SELECT * FROM GameSavings WHERE (CURRENT_TIME = ? AND DATE = ?);"
        );
        this.preparedStatement.setString( 1, time );
        this.preparedStatement.setString( 2, date );
        ResultSet resultSet = this.preparedStatement.executeQuery();

        while (resultSet.next()) {
            String  currentTime  = resultSet.getString( "CURRENT_TIME" );
            String  currentDate  = resultSet.getString( "DATE" );
            Integer currentScore = resultSet.getInt( "SCORE" );
            Integer life         = resultSet.getInt( "LIFE" );
            Integer level        = resultSet.getInt( "LEVEL" );
            Float   money        = resultSet.getFloat( "MONEY" );
            String  towers       = resultSet.getString( "TOWERS" );
            String  mobs         = resultSet.getString( "MOBS" );

            containerPackage.put( "CURRENT_TIME", currentTime );
            containerPackage.put( "DATE", currentDate );
            containerPackage.put( "SCORE", currentScore );
            containerPackage.put( "LIFE", life );
            containerPackage.put( "LEVEL", level );
            containerPackage.put( "MONEY", money );
            containerPackage.put( "TOWERS", towers );
            containerPackage.put( "MOBS", mobs );
        }

        for (Map.Entry< String, Object > entry : containerPackage.entrySet()) {
            System.out.println( entry.getKey() + ":  " + entry.getValue() );
        }

        resultSet.close();
        return containerPackage;
    }
}

package game_managers.db;

import Constants.Constants;
import com.google.gson.Gson;
import ecs_container.Actors.enemies.Enemy;
import ecs_container.towers.Tower;
import factories.EnemyFactory;
import graphic_context.SpriteSheet;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.File;
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

/**
 * Manager class, used to create, administrate and update the SQLite data base.
 */
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

    /**
     * <p>used to access the data base for queries, updates and other operations</p>
     * <p>there is only one connection to the data base, in order to avoid file locking and to ease access.</p>
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void openConnection() throws ClassNotFoundException, SQLException {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        if (connection == null || connection.isClosed()) {
            Class.forName( DBManager.DB_SERVER );
            this.connection = DriverManager.getConnection( DBManager.DB_NAME );
            this.connection.setAutoCommit( false );
        }
    }

    /**
     * <p>used to close the singleton db connection.</p>
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        this.connection.commit();
        this.connection.close();
    }

    /**
     * Creates the database tables and stores map files configurations.
     * @throws SQLException
     */
    private void initDataBase() throws SQLException {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        try {
            boolean wasCreated = false;
            File    dbFile     = new File( Constants.DB_URL );
            if (dbFile.exists())
                wasCreated = true;

            openConnection();
            this.statement = connection.createStatement();

            if (!wasCreated) {
                createTable( "MapFiles" );
                createTable( "HighScores" );
                createTable( "GameSavings" );

                INSERTIntoMapFiles(
                        "C:\\Users\\silviu\\IdeaProjects\\Protector_Maven\\src\\main\\java\\config_files\\level_1.txt",
                        1,
                        200
                );

                INSERTIntoMapFiles(
                        "C:\\Users\\silviu\\IdeaProjects\\Protector_Maven\\src\\main\\java\\config_files\\level_2.txt",
                        2,
                        300
                );

                INSERTIntoMapFiles(
                        "C:\\Users\\silviu\\IdeaProjects\\Protector_Maven\\src\\main\\java\\config_files\\level_3.txt",
                        3,
                        400
                );
            } else {
                System.out.println( "DataBase was already created..." );
            }
        } catch ( ClassNotFoundException | SQLException throwables ) {
            throwables.printStackTrace();
        } finally {
            this.statement.close();
            closeConnection();
        }
        System.out.println( "DBManager was initialized successfully..." );
    }

    /**
     * used to create a table in the local db
     * @param tableName
     * @throws SQLException
     */
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

    /**
     * insert operation in the MapFiles table
     * @param fileName name of the text file that holds the map data
     * @param levelNumber current level number
     * @param numberOfMonsters number of monsters per level
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void INSERTIntoMapFiles(String fileName, int levelNumber, int numberOfMonsters) throws SQLException, ClassNotFoundException{
        openConnection();
        String fileContent = null;

        if (fileName != null) {
            try (FileInputStream fileInputStream = new FileInputStream( fileName )) {
                fileContent = IOUtils.toString( fileInputStream, "UTF-8" );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        } else {
            closeConnection();
            return;
        }

        if (connection != null && fileContent != null) {
            try {
                this.preparedStatement = connection.prepareStatement(
                        "INSERT INTO MapFiles " +
                                "(LEVELNUMBER, NAME, CONTENT, NR_OF_MONSTERS_PER_LEVEL) VALUES(?, ?, ?, ?);"
                );
                this.preparedStatement.setInt( 1, levelNumber );
                this.preparedStatement.setString( 2, fileName );
                this.preparedStatement.setString( 3, fileContent );
                this.preparedStatement.setInt( 4, numberOfMonsters );
                this.preparedStatement.executeUpdate();
            } finally {
                this.preparedStatement.close();
            }
        }

        // closeConnection();
    }

    /**
     * selects from the MapFiles table, used to retrieve the file data
     * @param levelNumber the coresponding level number for the map
     * @return returns the selected text file content
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String SELECTFileContent(int levelNumber) throws SQLException, ClassNotFoundException {
        openConnection();
        ResultSet resultSet   = null;
        String    fileContent = null;

        try {
            this.preparedStatement = connection.prepareStatement(
                    "SELECT CONTENT FROM MapFiles WHERE LEVELNUMBER  = ?;"
            );
            this.preparedStatement.setInt( 1, levelNumber );
            resultSet = this.preparedStatement.executeQuery();
            resultSet.next();
            fileContent = resultSet.getString( "CONTENT" );
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            this.preparedStatement.close();
        }

        closeConnection();
        return fileContent;
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

    /**
     * inserts into the HighScore table.
     * <p>The table gets updated constantly, whenever the user exits or saves the current progress in game.</p>
     * @param highScore the current player score
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void INSERTIntoHighScores(int highScore) throws SQLException, ClassNotFoundException {
        openConnection();

        DateTimeFormatter formatter   = DateTimeFormatter.ofPattern( "HH:mm:ss" );
        String            currentTime = LocalTime.now().format( formatter );
        formatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
        String currentDate = LocalDate.now().format( formatter );

        try {
            this.preparedStatement = connection.prepareStatement(
                    "INSERT INTO HighScores " +
                            "(CURRENT_TIME, DATE, SCORE) VALUES(?, ?, ?);"
            );
            this.preparedStatement.setString( 1, currentTime );
            this.preparedStatement.setString( 2, currentDate );
            this.preparedStatement.setInt( 3, highScore );
            this.preparedStatement.executeUpdate();
        } finally {
            this.preparedStatement.close();
        }

        closeConnection();
    }

    /**
     * used for selecting the lastNrOfScores from the db and output them to the leaderbord menu.
     * @param lastNrOfScores retrieves the lastNrOfScores from the db file.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList< Integer > SELECTHighScores(int lastNrOfScores) throws SQLException, ClassNotFoundException {
        openConnection();
        ArrayList< Integer > scores    = new ArrayList<>();
        ResultSet            resultSet = null;

        try {
            this.preparedStatement = connection.prepareStatement(
                    "SELECT SCORE FROM HighScores ORDER BY SCORE DESC LIMIT ?"
            );
            this.preparedStatement.setInt( 1, lastNrOfScores );
            resultSet = this.preparedStatement.executeQuery();

            while (resultSet.next()) {
                int currentScore = resultSet.getInt( "SCORE" );
                scores.add( currentScore );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            this.preparedStatement.close();
        }

        closeConnection();
        return scores;
    }

    /**
     * <p>Used to update the current game progress, whenever the players pressed the save game button.</p>
     * @param serializedEnemies a serialized string of enemies. it's later used to restore game progress.
     * @param serializedTowers a serialized string of towers. it's later used to restore game progress.
     * @param currentLife remaining life in the game session
     * @param currentScore the current score in the game session
     * @param currentLevel the current level in the game session
     * @param currentMoney the current amount of money in the game session
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void INSERTIntoGameSavings(String serializedEnemies, String serializedTowers, int currentLife,
                                      int currentScore, int currentLevel, int currentMoney) throws SQLException, ClassNotFoundException {
        openConnection();

        DateTimeFormatter formatter   = DateTimeFormatter.ofPattern( "HH:mm:ss" );
        String            currentTime = LocalTime.now().format( formatter );
        formatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
        String currentDate = LocalDate.now().format( formatter );

        try {
            this.preparedStatement = connection.prepareStatement(
                    "INSERT INTO GameSavings " +
                            "(CURRENT_TIME, DATE, SCORE, LIFE, LEVEL, MONEY, TOWERS, MOBS) VALUES(?, ?, ?, ?, ?, ?, ?, ?);"
            );
            this.preparedStatement.setString( 1, currentTime );
            this.preparedStatement.setString( 2, currentDate );
            this.preparedStatement.setInt( 3, currentScore );
            this.preparedStatement.setInt( 4, currentLife );
            this.preparedStatement.setInt( 5, currentLevel );
            this.preparedStatement.setInt( 6, currentMoney );
            this.preparedStatement.setString( 7, serializedTowers );
            this.preparedStatement.setString( 8, serializedEnemies );
            this.preparedStatement.executeUpdate();
        } finally {
            this.preparedStatement.close();
        }

        closeConnection();
    }

    public HashMap< String, Object > SELECTGameSavingsByTimeAndDate(String time, String date) throws SQLException, ClassNotFoundException {
        openConnection();
        ResultSet                 resultSet        = null;
        HashMap< String, Object > containerPackage = new HashMap< String, Object >();

        try {
            this.preparedStatement = connection.prepareStatement(
                    "SELECT * FROM GameSavings WHERE (CURRENT_TIME = ? AND DATE = ?);"
            );
            this.preparedStatement.setString( 1, time );
            this.preparedStatement.setString( 2, date );
            resultSet = this.preparedStatement.executeQuery();

            while (resultSet.next()) {
                String  currentTime  = resultSet.getString( "CURRENT_TIME" );
                String  currentDate  = resultSet.getString( "DATE" );
                Integer currentScore = resultSet.getInt( "SCORE" );
                Integer life         = resultSet.getInt( "LIFE" );
                Integer level        = resultSet.getInt( "LEVEL" );
                Integer money        = resultSet.getInt( "MONEY" );
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
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            this.preparedStatement.close();
        }

        for (Map.Entry< String, Object > entry : containerPackage.entrySet()) {
            System.out.println( entry.getKey() + ":  " + entry.getValue() );
        }

        closeConnection();
        return containerPackage;
    }

    /**
     * used for restoring the saved game session
     * @param n selects the last n game savings from the db. the savings will be print to the load game Menu
     * @return returns a hashMap container, used to retrieve information
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList< HashMap< String, Object > > SELECTLastNGameSavings(int n) throws SQLException, ClassNotFoundException {
        openConnection();
        ArrayList< HashMap< String, Object > > container = new ArrayList<>();
        ResultSet                              resultSet = null;

        try {
            this.preparedStatement = connection.prepareStatement(
                    "SELECT * FROM GameSavings ORDER BY DATE, CURRENT_TIME DESC LIMIT ?"
            );
            // "SELECT * FROM GameSavings ORDER BY id DESC LIMIT ?;"
            this.preparedStatement.setInt( 1, n );
            resultSet = this.preparedStatement.executeQuery();

            while (resultSet.next()) {
                HashMap< String, Object > pack         = new HashMap< String, Object >();
                String                    currentTime  = resultSet.getString( "CURRENT_TIME" );
                String                    currentDate  = resultSet.getString( "DATE" );
                Integer                   currentScore = resultSet.getInt( "SCORE" );
                Integer                   life         = resultSet.getInt( "LIFE" );
                Integer                   level        = resultSet.getInt( "LEVEL" );
                Integer                   money        = resultSet.getInt( "MONEY" );
                String                    towers       = resultSet.getString( "TOWERS" );
                String                    mobs         = resultSet.getString( "MOBS" );

                pack.put( "CURRENT_TIME", currentTime );
                pack.put( "DATE", currentDate );
                pack.put( "SCORE", currentScore );
                pack.put( "LIFE", life );
                pack.put( "LEVEL", level );
                pack.put( "MONEY", money );
                pack.put( "TOWERS", towers );
                pack.put( "MOBS", mobs );

                container.add( pack );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            preparedStatement.close();
        }

        closeConnection();
        return container;
    }
}

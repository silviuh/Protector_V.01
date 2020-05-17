package game_managers.db;

import java.sql.*;
import java.sql.DriverManager;
import java.util.concurrent.locks.ReentrantLock;

public class DBManager {
    private static      DBManager dbManager = null;
    public static final String    DB_SERVER = "org.sqlite.JDBC";
    public static final String    DB_NAME   = "jdbc:sqlite:ProtectorDB.db";

    private PreparedStatement preparedStatement = null;
    private Connection        connection        = null;

    private static ReentrantLock singletonLock;

    private DBManager() {

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

    private void initDataBase() throws SQLException {
        try {
            Class.forName( DBManager.DB_SERVER );
            this.connection = DriverManager.getConnection( DBManager.DB_NAME );
            this.connection.setAutoCommit( false );


        } catch ( ClassNotFoundException | SQLException throwables ) {
            throwables.printStackTrace();
        } finally {

            this.preparedStatement.close();
            this.connection.commit();
            this.connection.close();
        }
        System.out.println( "DBManager was initialized successfully..." );
    }


    private void createTable(String tableName, String sqlTranzaction) throws SQLException {
        switch (tableName) {
            case "MapFiles": {
                this.preparedStatement = connection.prepareStatement(
                        "CREATE TABLE MapFiles " +
                                "(" +
                                "LEVEL_NUMBER INT NOT NULL," +
                                "NAME VARCHAR NOT NULL" +
                                "CONTENT BLOB NOT NULL" +
                                ")"
                );
                this.preparedStatement.executeUpdate();
                break;
            }
            case "HighScores": {
                this.preparedStatement = connection.prepareStatement(
                        "CREATE TABLE HighScores " +
                                "(" +
                                "PLAYER_HASH INT NOT NULL," +
                                "DATE CHAR NOT NULL" +
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
                                "PLAYER_HASH INT NOT NULL," +
                                "DATE CHAR NOT NULL" +
                                "SCORE INT NOT NULL" +
                                "MONEY REAL NOT NULL" +
                                "TOWERS BLOB NOT NULL" +
                                "MOBS BLOB NOT NULL" +
                                ")"
                );
                this.preparedStatement.executeUpdate();
                break;
            }
        }
    }
}

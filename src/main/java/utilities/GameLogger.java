package utilities;

import Constants.Constants;
import game_managers.logicManagers.GamePanel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.*;

public class GameLogger {
    private final Logger      logger      = Logger.getLogger( GameLogger.class.getName() );
    private       FileHandler fileHandler = null;

    public GameLogger() {
        logger.setUseParentHandlers( false );

        SimpleDateFormat format = new SimpleDateFormat( "M-d_HHmmss" );
        try {
            fileHandler = new FileHandler( Constants.LOG_FILE_URL
                    + format.format( Calendar.getInstance().getTime() ) + ".log" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        fileHandler.setFormatter( new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat logTime = new SimpleDateFormat( "MM-dd-yyyy HH:mm:ss" );
                Calendar         cal     = new GregorianCalendar();
                cal.setTimeInMillis( record.getMillis() );
                return record.getLevel()
                        + logTime.format( cal.getTime() )
                        + " || "
                        + record.getSourceClassName().substring(
                        record.getSourceClassName().lastIndexOf( "." ) + 1,
                        record.getSourceClassName().length() )
                        + "."
                        + record.getSourceMethodName()
                        + "() : "
                        + record.getMessage() + "\n";
            }
        } );

        logger.addHandler( fileHandler );
    }

    public void log(String Message) {
        logger.info( Message );
    }
}

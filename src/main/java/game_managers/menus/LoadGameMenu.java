package game_managers.menus;

import Constants.Constants;
import game_managers.db.DBManager;
import game_managers.logicManagers.GameMainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LoadGameMenu extends JPanel {
    Dimension screenSize;
    int       screenWidth;
    int       screenHeight;

    private static volatile LoadGameMenu                           loadGameMenu;
    private static          ReentrantLock                          singletonLock;
    private                 ArrayList< Font >                      fonts;
    private                 JButton[]                              loadGameButtons;
    private                 JButton                                backToMyMenuBtn;
    private                 JButton                                exitBtn;
    private                 GameMainFrame                          mainFrameReference;
    private                 Image                                  animatedGif = null;
    private                 JLabel                                 textLabel   = null;
    private                 ArrayList< HashMap< String, Object > > savings;

    public LoadGameMenu(GameMainFrame mainFrameReference) {
        this.mainFrameReference = mainFrameReference;

        try {
            initializeVariables();
        } catch ( IOException | FontFormatException | SQLException | ClassNotFoundException e ) {
            e.printStackTrace();
        }

        initializeLayout();
        initializeFunctionality();
    }

    public static game_managers.menus.LoadGameMenu getInstance(GameMainFrame mainFrameReference) {
        singletonLock = new ReentrantLock();
        if (loadGameMenu == null) {
            try {
                singletonLock.lock();
                loadGameMenu = new game_managers.menus.LoadGameMenu(
                        mainFrameReference
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return loadGameMenu;
    }

    public void initializeVariables() throws IOException, FontFormatException, SQLException, ClassNotFoundException {

        animatedGif = Toolkit.getDefaultToolkit().createImage( Constants.LOAD_GAME_BACKGROUND_GIF );
        fonts = new ArrayList< Font >();
        File            fontSource    = new File( Constants.KENVECTOR_FUTURE_THIN_URL );
        FileInputStream in            = new FileInputStream( fontSource );
        Font            titleFont     = Font.createFont( Font.TRUETYPE_FONT, in );
        Font            titleFont32Pt = titleFont.deriveFont( 64f );

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width / 2;
        screenHeight = screenSize.height / 2;
        fonts.add( titleFont );
        fonts.add( titleFont32Pt );

        DBManager dbManager = mainFrameReference.getDbManager();
        savings = new ArrayList< HashMap< String, Object > >( Constants.NR_OF_AVAILABLE_GAME_SAVINGS );

        dbManager.openConnection();
        int                                    i         = 0;
        ArrayList< HashMap< String, Object > > container = dbManager.SELECTLastNGameSavings( 4 );
        while (i < 4) {
            savings.add(
                    container.get( i )
            );
            i++;
        }
        dbManager.closeConnection();
    }

    public void initializeLayout() {
        loadGameButtons = new JButton[savings.size()];
        int backToMenuX = 150;
        int backToMenuY = screenSize.height - 180;
        int exitX       = screenSize.width - 350;
        int exitY       = screenSize.height - 180;

        this.setLayout( null );

        for (int i = 0; i < savings.size(); i++) {
            loadGameButtons[i] = new JButton(
                    savings.get( i ).get( "DATE" ) + " || " + savings.get( i ).get( "CURRENT_TIME" )
            );
            loadGameButtons[i].setHorizontalTextPosition( SwingConstants.CENTER );
            loadGameButtons[i].setOpaque( false );
            loadGameButtons[i].setForeground( Color.YELLOW );
            loadGameButtons[i].setBackground( Color.gray );
            loadGameButtons[i].setAlignmentX( Component.CENTER_ALIGNMENT );
            loadGameButtons[i].setSize( Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT );
            loadGameButtons[i].setLocation(
                    200,
                    200 * i
            );
        }

        backToMyMenuBtn = new JButton(
                Constants.BACK_TO_MAIN_MENU_LABEL,
                new ImageIcon( Constants.MENU_BUTTON_ICON_URL )
        );
        backToMyMenuBtn.setHorizontalTextPosition( SwingConstants.CENTER );
        backToMyMenuBtn.setOpaque( true );
        backToMyMenuBtn.setForeground( Color.BLACK );
        backToMyMenuBtn.setBackground( Color.gray );
        backToMyMenuBtn.setText( Constants.BACK_TO_MAIN_MENU_LABEL );
        backToMyMenuBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
        backToMyMenuBtn.setSize( Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT );
        backToMyMenuBtn.setLocation(
                backToMenuX,
                backToMenuY
        );


        exitBtn = new JButton(
                Constants.EXIT_GAME_LABEL,
                new ImageIcon( Constants.MENU_BUTTON_ICON_URL )
        );
        exitBtn.setHorizontalTextPosition( SwingConstants.CENTER );
        exitBtn.setOpaque( true );
        exitBtn.setForeground( Color.BLACK );
        exitBtn.setBackground( Color.gray );
        exitBtn.setText( Constants.EXIT_GAME_LABEL );
        exitBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
        exitBtn.setSize( Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT );
        exitBtn.setLocation(
                exitX,
                exitY
        );
    }

    public void initializeFunctionality() {
        backToMyMenuBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameReference.getPanelSwitcher().show(
                        mainFrameReference.getMainPanelContainer(),
                        "MAIN_MENU_PANEL"
                );
            }
        } );

        exitBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit( 0 );
            }
        } );

        for (int i = 0; i < loadGameButtons.length; i++) {
            add( loadGameButtons[i] );
        }
        add( backToMyMenuBtn );
        add( exitBtn );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent( g );

        g.drawImage(
                animatedGif,
                0,
                0,
                screenSize.width,
                screenSize.height,
                this
        );

        g.setFont( fonts.get( 1 ) );
        g.setColor( Color.WHITE );

    }
}
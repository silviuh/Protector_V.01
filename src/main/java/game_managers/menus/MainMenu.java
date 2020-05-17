package game_managers.menus;

import Constants.Constants;
import game_managers.logicManagers.GameMainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainMenu extends JPanel {
    private static volatile MainMenu          mainMenu;
    private static          ReentrantLock     singletonLock;
    private                 ArrayList< Font > fonts;
    private                 String            title;
    private                 JButton           playGameBtn;
    private                 JButton           helpBtn;
    private                 JButton           highScoreBtn;
    private JButton       exitBtn;
    private GameMainFrame mainFrameReference;
    private Image         animatedGif = null;

    Dimension screenSize;
    int       screenWidth;
    int       screenHeight;


    public MainMenu(GameMainFrame mainFrameReference) {
        this.mainFrameReference = mainFrameReference;

        try {
            initializeVariables();
        } catch ( IOException | FontFormatException e ) {
            e.printStackTrace();
        }

        initializeLayout();
        initializeFunctionality();
    }

    public static MainMenu getInstance(GameMainFrame mainFrameReference) {
        singletonLock = new ReentrantLock();
        if (mainMenu == null) {
            try {
                singletonLock.lock();
                mainMenu = new MainMenu(
                        mainFrameReference
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return mainMenu;
    }

    public void initializeVariables() throws IOException, FontFormatException {
        animatedGif = Toolkit.getDefaultToolkit().createImage( Constants.BACKGROUND_GIF_URL );
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

        title = "PROTECTOR V.01";
    }

    public void initializeLayout() {
        setLayout( null );
        playGameBtn = new JButton(
                Constants.PLAY_GAME_BTN_LABEL,
                new ImageIcon( Constants.MENU_BUTTON_ICON_URL )
        );
        playGameBtn.setHorizontalTextPosition( SwingConstants.CENTER );
        playGameBtn.setOpaque( true );
        playGameBtn.setForeground( Color.BLACK );
        playGameBtn.setBackground( Color.gray );
        playGameBtn.setText( Constants.PLAY_GAME_BTN_LABEL );
        playGameBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
        playGameBtn.setSize( Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT );
        playGameBtn.setLocation(
                screenWidth - Constants.MAIN_MENU_BUTTON_WIDTH / 2,
                screenHeight - Constants.MENU_CENTER_Y_SPACING
        );


        helpBtn = new JButton(
                Constants.HELP_BTN_LABEL,
                new ImageIcon( Constants.MENU_BUTTON_ICON_URL )
        );
        helpBtn.setHorizontalTextPosition( SwingConstants.CENTER );
        helpBtn.setOpaque( true );
        helpBtn.setForeground( Color.BLACK );
        helpBtn.setBackground( Color.gray );
        helpBtn.setText( Constants.HELP_BTN_LABEL );
        helpBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
        helpBtn.setSize( Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT );
        helpBtn.setLocation(
                screenWidth - Constants.MAIN_MENU_BUTTON_WIDTH / 2,
                screenHeight - Constants.MENU_CENTER_Y_SPACING + Constants.MAIN_MENU_BUTTON_PADDING
        );


        highScoreBtn = new JButton(
                Constants.HIGH_SCORE_BTN_LABEL,
                new ImageIcon( Constants.MENU_BUTTON_ICON_URL )
        );
        highScoreBtn.setHorizontalTextPosition( SwingConstants.CENTER );
        highScoreBtn.setOpaque( true );
        highScoreBtn.setForeground( Color.BLACK );
        highScoreBtn.setBackground( Color.gray );
        highScoreBtn.setText( Constants.HIGH_SCORE_BTN_LABEL );
        highScoreBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
        highScoreBtn.setSize( Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT );
        highScoreBtn.setLocation(
                screenWidth - Constants.MAIN_MENU_BUTTON_WIDTH / 2,
                screenHeight - Constants.MENU_CENTER_Y_SPACING + Constants.MAIN_MENU_BUTTON_PADDING * 2
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
                screenWidth - Constants.MAIN_MENU_BUTTON_WIDTH / 2,
                screenHeight - Constants.MENU_CENTER_Y_SPACING + Constants.MAIN_MENU_BUTTON_PADDING * 3
        );


    }

    public void initializeFunctionality() {
        playGameBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameReference.getPanelSwitcher().show(
                        mainFrameReference.getMainPanelContainer(),
                        "GAME_PANEL"
                );
                mainFrameReference.getGamePanel().startGame();
            }
        } );

        helpBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameReference.getPanelSwitcher().show(
                        mainFrameReference.getMainPanelContainer(),
                        "HELP_PANEL"
                );
            }
        } );


        highScoreBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        } );


        exitBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit( 0 );
            }
        } );


        add( playGameBtn );
        add( highScoreBtn );
        add( helpBtn );
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

        int x = screenWidth - Constants.MAIN_MENU_BUTTON_WIDTH / 2 - Constants.TITLE_CENTER_WIDTH_PADDING;
        int y = ( screenHeight / 2 - Constants.MENU_CENTER_Y_SPACING );

        g.setFont( fonts.get( 1 ) );
        g.setColor( Color.GRAY );
        g.drawString(
                title,
                x,
                y
        );
    }
}

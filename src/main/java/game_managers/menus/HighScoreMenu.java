package game_managers.menus;

import Constants.Constants;
import game_managers.logicManagers.GameMainFrame;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class HighScoreMenu extends JPanel {
    Dimension screenSize;
    int       screenWidth;
    int       screenHeight;

    private static          ArrayList< Integer > scores;
    private static          JLabel[]             scoreLabels;
    private static volatile HighScoreMenu        highScoreMenu;
    private static          ReentrantLock        singletonLock;
    private                 ArrayList< Font >    fonts;
    private                 JButton              backToMyMenuBtn;
    private                 JButton              exitBtn;
    private                 GameMainFrame        mainFrameReference;
    private                 Image                animatedGif = null;

    public HighScoreMenu(GameMainFrame mainFrameReference) {
        this.mainFrameReference = mainFrameReference;

        try {
            initializeVariables();
        } catch ( IOException | FontFormatException e ) {
            e.printStackTrace();
        }

        initializeLayout();
        initializeFunctionality();
    }

    public static HighScoreMenu getInstance(GameMainFrame mainFrameReference) {
        singletonLock = new ReentrantLock();
        if (highScoreMenu == null) {
            try {
                singletonLock.lock();
                highScoreMenu = new HighScoreMenu(
                        mainFrameReference
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return highScoreMenu;
    }

    public void initializeVariables() throws IOException, FontFormatException {
        scores = new ArrayList<>( Constants.MAX_NUMBER_OF_SCORES_IN_LEADERBORD );
        animatedGif = Toolkit.getDefaultToolkit().createImage( Constants.HELP_BACKGROUND_GIF );
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
    }

    public void initializeLayout() {
        int backToMenuX = 150;
        int backToMenuY = screenSize.height - 180;
        int exitX       = screenSize.width - 350;
        int exitY       = screenSize.height - 180;

        this.setLayout( null );
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

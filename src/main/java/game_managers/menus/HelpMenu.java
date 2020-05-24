package game_managers.menus;

import Constants.Constants;
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
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class HelpMenu extends JPanel {
    Dimension screenSize;
    int       screenWidth;
    int       screenHeight;
    String    formattedHelpText;
    String    rawText;

    private static volatile HelpMenu          helpMenu;
    private static          ReentrantLock     singletonLock;
    private                 ArrayList< Font > fonts;
    private                 String            title;
    private                 String            creator;
    private                 JButton           backToMyMenuBtn;
    private                 JButton           exitBtn;
    private                 GameMainFrame     mainFrameReference;
    private                 Image             animatedGif = null;
    private                 JLabel            textLabel   = null;


    public HelpMenu(GameMainFrame mainFrameReference) {
        this.mainFrameReference = mainFrameReference;

        try {
            initializeVariables();
        } catch ( IOException | FontFormatException e ) {
            e.printStackTrace();
        }

        initializeLayout();
        initializeFunctionality();
    }

    public static HelpMenu getInstance(GameMainFrame mainFrameReference) {
        singletonLock = new ReentrantLock();
        if (helpMenu == null) {
            try {
                singletonLock.lock();
                helpMenu = new HelpMenu(
                        mainFrameReference
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return helpMenu;
    }

    public void initializeVariables() throws IOException, FontFormatException {
        rawText = readHelpText( Constants.HELP_TEXT_CONFIG_TXT );
        formattedHelpText = rawText.replace(
                "\n", "<br>"
        );
        formattedHelpText = "<html><font size='5'>" +
                formattedHelpText +
                "</font></html>";

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

        title = "PROTECTOR V.01";
        creator = "CREATED BY HARTAN SILVIU";
    }

    public void initializeLayout() {
        int backToMenuX = 150;
        int backToMenuY = screenSize.height - 180;
        int exitX       = screenSize.width - 350;
        int exitY       = screenSize.height - 180;

        this.setLayout( null );
        textLabel = new JLabel( formattedHelpText );
        textLabel.setBounds(
                screenWidth - Constants.TEXT_X_PADDING,
                screenHeight - Constants.TEXT_Y_PADDING,
                Constants.TEXT_SIZE,
                Constants.TEXT_SIZE
        );
        textLabel.setForeground( Constants.dollarSign );

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
        add( textLabel );
    }

    private String readHelpText(String filePath) {
        String content = "";

        try {
            content = new String( Files.readAllBytes( Paths.get( filePath ) ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return content;
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

        int xTitle   = screenWidth - Constants.MAIN_MENU_BUTTON_WIDTH / 2 - Constants.TITLE_CENTER_WIDTH_PADDING;
        int yTitle   = ( screenHeight / 2 - Constants.MENU_CENTER_Y_SPACING );
        int xCreator = screenWidth - Constants.MAIN_MENU_BUTTON_WIDTH / 2 - Constants.TITLE_CENTER_WIDTH_PADDING + 270;
        int yCreator = ( screenHeight / 2 - Constants.MENU_CENTER_Y_SPACING + 100 );

        g.setFont( fonts.get( 1 ) );
        g.setColor( Color.WHITE );

        g.drawString(
                title,
                xTitle,
                yTitle
        );
        g.drawString(
                creator,
                xCreator,
                yCreator
        );
    }
}

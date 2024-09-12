package ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.EventLog;
import ui.Mixologist;
import ui.controller.*;

// Represents the main ui for the Mixologist game
public class ContentView extends JFrame implements ActionListener, WindowListener {

    protected static final int WIDTH = 800;
    protected static final int HEIGHT = 600;

    protected Controller controller;
    protected JPanel splitPlane;
    protected JPanel sidebar;

    // EFFECTS: creates the ui for the Mixologist game
    public ContentView() {
        super("Mixologist");
        controller = new Controller();
        init();
    }

    // MODIFIES: this
    // EFFECTS: initalizes the JPanel shown on start
    public void init() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
        initalizeSplitView();
        addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates a split plane
    public void initalizeSplitView() {
        splitPlane = new JPanel();
        splitPlane.setLayout(new BoxLayout(splitPlane, BoxLayout.LINE_AXIS));
        splitPlane.add(initalizeContentView());
        splitPlane.add(createSidebar());
        add(splitPlane);
    }

    // MODIFIES: this
    // EFFECTS: creates the left panel of the split plane
    private JPanel initalizeContentView() {
        JPanel contentView = new JPanel();
        contentView.setLayout(new BorderLayout());
        // from: https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
        BufferedImage barImage;
        try {
            barImage = ImageIO.read(new File("./images/cover5.png"));
            JLabel picLabel = new JLabel(new ImageIcon(barImage));
            contentView.add(picLabel);
        } catch (IOException e) {
            System.out.println("Unable to locate image");
        }
        contentView.setPreferredSize(new Dimension((WIDTH * 3) / 4, HEIGHT));
        contentView.setVisible(true);
        return contentView;
    }

    // MODIFIES: this
    // EFFECTS: creates the right panel of the split plane
    private JPanel createSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1));
        sidebar.setSize(new Dimension(0, 0));

        SidebarButton mixButton = new SidebarButton("Mix a drink", "mix", this);
        sidebar.add(mixButton);

        SidebarButton menuButton = new SidebarButton("View Menu", "menu", this);
        sidebar.add(menuButton);

        SidebarButton recipesButton = new SidebarButton("View Recipes", "recipes", this);
        sidebar.add(recipesButton);

        JButton ingredientsButton = new SidebarButton("Ingredients", "ingredients", this);
        sidebar.add(ingredientsButton);

        return sidebar;
    }

    // MODIFIES: this
    // EFFECTS: updates this to display the correct JPanel based on the selected button
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("mix")) {
            if (controller.getMenu().getIngredients().size() <= 0) {
                JOptionPane.showMessageDialog(this, "Not enough ingredients to mix a drink");
                return;
            }
        }

        addSubView(e.getActionCommand());
    }

    // MODIFIES: this
    // EFFECTS: sets the visibillity of this to true, and updates the JPanel
    public void reset() {
        splitPlane.setVisible(true);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: sets the current view to be a the specified view
    private void addSubView(String view) {
        splitPlane.setVisible(false);
        switch (view) {
            case "mix":
                add(new MixingView(this));
                break;
            case "menu":
                add(new MenuView(this));
                break;
            case "recipes":
                add(new RecipesView(this));
                break;
            case "ingredients":
                add(new IngredientsView(this));
                break;
            default:
                reset();
        }

        revalidate();
        repaint();
    }

    // EFFECTS: when this window is closed print all logged
    // events to console
    @Override
    public void windowClosed(WindowEvent e) {
        // do nothing
    }

    // EFFECTS: does nothing
    @Override
    public void windowOpened(WindowEvent e) {
       // do nothing
    }

     // EFFECTS: does nothing
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window Closed");
        Mixologist.printLog(EventLog.getInstance());
        this.dispose();
    }

     // EFFECTS: does nothing
    @Override
    public void windowIconified(WindowEvent e) {
       // do nothing
    }

     // EFFECTS: does nothing
    @Override
    public void windowDeiconified(WindowEvent e) {
      // do nothing
    }

     // EFFECTS: does nothing
    @Override
    public void windowActivated(WindowEvent e) {
        // do nothing
    }

     // EFFECTS: does nothing
    @Override
    public void windowDeactivated(WindowEvent e) {
        // do nothing
    }
}

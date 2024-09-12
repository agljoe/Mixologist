package ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Menu;
import model.Drink;

// Repesents the GUI for view / editing the menu
public class MenuView extends SubView {

    private JList<String> drinksList;
    private DefaultListModel<String> listModel;
    private JScrollPane listScroller;

    // EFFECTS: constructs a new MenuView with a parentView of contentView
    public MenuView(ContentView contentView) {
        super(contentView);
    }

    // MODIFIES: this
    // EFFECTS: creates the left JPanel of the split plane
    @Override
    protected JPanel initalizeContentView() {
        contentView = new JPanel();
        contentView.setLayout(new OverlayLayout(contentView));
          // from: https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
        BufferedImage menuImage;
        try {
            menuImage = ImageIO.read(new File("./images/Cocktail-Menu-Vector-1613866797.jpg"));
            Image scaledMenuImage = menuImage.getScaledInstance(600, 600, Image.SCALE_FAST);
            JLabel picLabel = new JLabel(new ImageIcon(scaledMenuImage));
            contentView.add(picLabel);
        } catch (IOException e) {
            System.out.println("Unable to locate image");
        }
        displayMenu();
        return contentView;
    }

    // MODIFIES: this
    // EFFECTS: creates the right JPanel of the splitview
    // with navigation / action buttons
    // buttons pass their command to actoinPerformed()
    @Override
    protected JPanel intializeSidebar() {
        sidebar.setLayout(new GridLayout(0, 1));
        sidebar.setSize(new Dimension(0, 0));

        addRemoveButton("Drink");

        SidebarButton viewRecipeButton = new SidebarButton("View Drink", "view", this);
        sidebar.add(viewRecipeButton);

        addPersistenceButtons("Menu");
        addBackButton();

        return sidebar;
    }

    // MODIFIES: this
    // EFFECTS: exectutes the command sent from the pressed button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("remove")) {
            removeDrink();
        }

        if (e.getActionCommand().equals("save")) {
            saveMenu();
        }

        if (e.getActionCommand().equals("load")) {
            loadMenu();
        }

        if (e.getActionCommand().equals("back")) {
            goBack();
        }
    }

    // MODIFIES: this
    // EFFECTS: constructs a JList with drink and adds it to this.contentView
    private void displayMenu() {
        Menu menu = parentView.controller.getMenu();
      
        listModel = new DefaultListModel<String>();

        for (Drink drink : menu.getMenu()) {
            listModel.addElement(drink.getName());
        }

        // from https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
        drinksList = new JList<String>(listModel);
        drinksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        drinksList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        drinksList.setVisibleRowCount(20);
        listScroller = new JScrollPane(drinksList);
        listScroller.setPreferredSize(new Dimension((ContentView.HEIGHT * 3) / 4, ContentView.WIDTH));
        listScroller.setOpaque(false);
        contentView.add(listScroller, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: removes the specified drink from the menu,
    // creates an event for removing a drink from menu, and logs it
    public void removeDrink() {
        int index = drinksList.getSelectedIndex();
        listModel.remove(index);
        parentView.controller.removeDrinkFromMenu(index);
    }

    // EFFECTS: saves the curent menu to /data/menu.json
    public void saveMenu() {
        try {
            parentView.controller.saveMenu();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to locate file at ./data/menu.json");
        }
    }

    // MODIFIES: this
    // loads save menu from /data/menu.json,
    // and sets this to display loaded menu
    public void loadMenu() {
        try {
            parentView.controller.loadMenu();
            contentView.remove(listScroller);
            displayMenu();
            revalidate();
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                    "Failed to load ingredients from ./data/menu.json, file may be corrupted or missing");
        }
    }
}

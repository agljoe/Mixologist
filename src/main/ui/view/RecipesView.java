package ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.util.List;

import model.Drink;

// Represents the GUI of view / editing recipes
public class RecipesView extends SubView {

    private JList<String> drinksList;
    private DefaultListModel<String> listModel;
    private JScrollPane listScroller;

    // EFFECTS: constructs a new RecipesView with parentView of contentView
    public RecipesView(ContentView contentView) {
        super(contentView);
    }

    // MODIFIES: this
    // EFFECTS: creates the left panel of the split plane
    @Override
    protected JPanel initalizeContentView() {
        contentView = new JPanel();
        contentView.setLayout(new BorderLayout());
        contentView.setPreferredSize(new Dimension((ContentView.HEIGHT * 3) / 4, ContentView.WIDTH));
        displayRecipes();
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

        SidebarButton viewRecipeButton = new SidebarButton("View Recipe", "view", this);
        sidebar.add(viewRecipeButton);

        addRemoveButton("Recipe");

        addPersistenceButtons("Recipes");

        addBackButton();

        return sidebar;
    }

    // MODIFIES: this
    // EFFECTS: exectutes the command sent from the pressed button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            viewRecipe();
        }

        if (e.getActionCommand().equals("remove")) {
            removeRecipe();
        }

        if (e.getActionCommand().equals("save")) {
            saveRecipes();
        }

        if (e.getActionCommand().equals("load")) {
            loadRecipes();
        }

        if (e.getActionCommand().equals("back")) {
            goBack();
        }
    }

    // MODIFIES: this
    // EFFECTS: constructs a JList with recipes and adds it to this.contentView
    private void displayRecipes() {
        List<Drink> recipes = parentView.controller.getRecipes();
      
        listModel = new DefaultListModel<String>();

        for (Drink d : recipes) {
            listModel.addElement(formatDrinkText(d));
        }

        // from https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
        drinksList = new JList<String>(listModel);
        drinksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        drinksList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        drinksList.setVisibleRowCount(20);
        listScroller = new JScrollPane(drinksList);
        listScroller.setPreferredSize(new Dimension((ContentView.HEIGHT * 3) / 4, ContentView.WIDTH)); 
        contentView.add(listScroller);
    }

    // MODIFIES: this
    // EFFECTS displays all the steps to make the selected drink
    private void viewRecipe() {
        int index = drinksList.getSelectedIndex();
        JOptionPane.showMessageDialog(this, formatRecipe(index));
    }

    // EFFECTS: removes the recipe the specified index
    private void removeRecipe() {
        int index = drinksList.getSelectedIndex();
        listModel.remove(index);
        parentView.controller.removeRecipe(index);
    }

    // EFFECTS: saves recipes in drinksList to ./data/recipes.json
    private void saveRecipes() {
        try {
            parentView.controller.saveRecipes();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to locate file at ./recipes/menu.json");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads saved recipes from /data/menu.json,
    // and sets this to display the loaded recipes
    private void loadRecipes() {
        try {
            parentView.controller.loadRecipes();
            contentView.remove(listScroller);
            displayRecipes();
            revalidate();
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                    "Failed to load ingredients from ./data/menu.json, file may be corrupted or missing");
        }
    }

    // REQUIRES: d is a non null drink
    // EFFECTS: returns a string in the format:
    // name [Sweetness: x, Strength: y, Thickness: z]
    private String formatDrinkText(Drink d) {
        return d.getName()
            + "[Sweetness: " + d.getSweetness()
            + ", Strength: " + d.getStrength()
            + ", Thickness: " + d.getThickness() + "]";
    }

    // EFFECTS: returns a multiline string for
    // each step of the selected drinks recipe
    private String formatRecipe(int index) {
        Drink selectedDrink = parentView.controller.getRecipes().get(index);
        String recipe = selectedDrink.getName();

        for (String s : selectedDrink.getRecipe()) {
            recipe = recipe + "\n" + s;
        }

        return recipe;
    }
}

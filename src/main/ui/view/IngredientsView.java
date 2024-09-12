package ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.util.List;

import model.Ingredient;

// Represents the GUI for viewing/editing ingredients
public class IngredientsView extends SubView {

    private static final int TEXT_FIELD_COLS = 10;
    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    private JList<String> ingredientsList;
    private DefaultListModel<String> listModel;
    private JScrollPane listScroller;
    private JPanel inputPanel;

    private JTextField nameField;
    private JTextField sweetnessField;
    private JTextField strengthField;
    private JTextField thicknessField;


    // EFFECTS: contrusts an IngredientsView JPanel with a parent view of contentView
    public IngredientsView(ContentView contentView) {
        super(contentView);
    }

    // MODIFIES: this
    // EFFECTS: creates the left JPanel of the splitview
    @Override
    protected JPanel initalizeContentView() {
        contentView = new JPanel();
        contentView.setLayout(new BorderLayout());
        displayIngredients();
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

        addRemoveButton("Ingredient");

        SidebarButton addIngredientButton = new SidebarButton("Add Ingredient", "add", this);
        sidebar.add(addIngredientButton);

        addPersistenceButtons("Ingredients");

        addBackButton();

        revalidate();
        return sidebar;
    }

    // MODIFIES: this
    // EFFECTS: exectutes the command sent from the pressed button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            addIngredient();
        }

        if (e.getActionCommand().equals("remove")) {
            removeIngredient();
        }

        if (e.getActionCommand().equals("save")) {
            saveIngredients();
        }

        if (e.getActionCommand().equals("load")) {
            loadIngredients();
        }

        if (e.getActionCommand().equals("back")) {
            goBack();
        }

        if (e.getActionCommand().equals("clear")) {
            resetText();
        }

        if (e.getActionCommand().equals("create")) {
            createIngredient();
        }
    }

    // MODIFIES: this
    // EFFECTS: if the user is making a new ingredient, hide
    // the JPanel for making ingredients,
    // otherwise return to parentView
    @Override
    protected void goBack() {
        if (!(inputPanel == null) && inputPanel.isVisible()) {
            inputPanel.setVisible(false);
            listScroller.setVisible(true);
        } else {
            super.goBack();;
        }
    }

    // MODIFIES: this
    // EFFECTS: constructs a JList with ingredients and adds it to this.contentView
    private void displayIngredients() {
        List<Ingredient> ingredients = parentView.controller.getMenu().getIngredients();
      
        listModel = new DefaultListModel<String>();

        for (Ingredient i : ingredients) {
            listModel.addElement(formatIngredientText(i));
        }

        // from https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
        ingredientsList = new JList<String>(listModel);
        ingredientsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ingredientsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsList.setVisibleRowCount(20);
        listScroller = new JScrollPane(ingredientsList);
        listScroller.setPreferredSize(new Dimension((ContentView.HEIGHT * 3) / 4, ContentView.WIDTH));
        listScroller.setVisible(true);
        contentView.add(listScroller);
    }

    // MODIFIES: this
    // EFFECTS: creates the JPanel with input fields for a new ingredient
    private void createIngredientMaker() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.add(textFieldsPanel());
        inputPanel.add(buttonPanel());
        inputPanel.setVisible(true);
        inputPanel.setPreferredSize(new Dimension((ContentView.HEIGHT * 3) / 4, ContentView.WIDTH));
        contentView.add(inputPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: should create a 2 x 4 grid with label next to textfield,
    // but instead it just fills horizontal space and i dont know why
    private JPanel textFieldsPanel() {
        JPanel fieldsContiainer = new JPanel();
        GridBagLayout textFieldsGrid = new GridBagLayout();
        initalizeTextFields();

        addobjects(new JLabel("Name:", JLabel.LEFT), fieldsContiainer, textFieldsGrid, 0, 0);
        addobjects(nameField, fieldsContiainer, textFieldsGrid, 1, 0);

        addobjects(new JLabel("Sweetness:", JLabel.LEFT), fieldsContiainer, textFieldsGrid, 0, 1);
        addobjects(sweetnessField, fieldsContiainer, textFieldsGrid, 1, 1);

        addobjects(new Label("Strength:", JLabel.LEFT), fieldsContiainer, textFieldsGrid, 0, 2);
        addobjects(strengthField, fieldsContiainer, textFieldsGrid, 1, 2);

        addobjects(new Label("Thickness:"), fieldsContiainer, textFieldsGrid, 0, 3);
        addobjects(thicknessField, fieldsContiainer, textFieldsGrid, 1, 3);
   
        return fieldsContiainer;
    }

    // MODIFIES: this
    // EFECTS: creates a reset and create button for either
    // reseting the inputs for a new ingredient or
    // creating a new ingredient with the inputs
    private JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(new SidebarButton("Reset", "clear", this));
        buttonPanel.add(new SidebarButton("Create", "create", this));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: initalizes all text fields to have a size of 10 characters
    private void initalizeTextFields() {
        nameField = new JTextField(TEXT_FIELD_COLS);
        sweetnessField = new JTextField(TEXT_FIELD_COLS);
        strengthField = new JTextField(TEXT_FIELD_COLS);
        thicknessField = new JTextField(TEXT_FIELD_COLS);
    }


    /* This method is a combination of the methods found in the top comment of:
    *  https://stackoverflow.com/questions/30656473/how-to-use-gridbaglayout, and
    *  https://stackoverflow.com/questions/9851688/how-to-align-left-or-right-inside-gridbaglayout-cell
    * MODIFIES: this
    * EFFECTS: creates new gridbagconstraints for component, uses gridx and gridy to place component in
    * the specified cell, anchors component based on the column (gridx), sets compontents contraits for
    * layout with gbc and adds the component to container
    */
    private void addobjects(Component component, Container yourcontainer, GridBagLayout layout,
            int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.anchor = (gridx == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (gridx == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.insets = (gridx == 0) ? WEST_INSETS : EAST_INSETS;
        gbc.weightx = (gridx == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;

        layout.setConstraints(component, gbc);
        yourcontainer.add(component);
    }

    // MODIFIES: this
    // EFFECTS: lets the user make a new ingredient by
    // specifying the name, sweetness, strength and thickness,
    // adds the new ingredient to parentView.controller.ingredients,
    // and updates this to display the new ingredient
    private void addIngredient() {
        listScroller.setVisible(false);
        createIngredientMaker();
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: removes the selected ingredient from listModel, and parentView.controller.ingredients
    private void removeIngredient() {
        int index = ingredientsList.getSelectedIndex();

        if (index < 0) {
            return;
        }

        parentView.controller.removeIngredient(index);
        listModel.remove(index);
    }

    // EFFECTS: saves ingredients in ingredientsList to data/ingredients.json
    private void saveIngredients() {
        try {
            parentView.controller.saveIngredients();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to locate file at ./data/ingredients.json");
        }
    }

    // MODIFIES: this
    // EFECTS: loads saved ingredients from data/ingredients.json
    // and updates this to display loaded ingredients
    private void loadIngredients() {
        try {
            parentView.controller.loadIngredients();
            contentView.remove(listScroller);
            displayIngredients();
            revalidate();
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                    "Failed to load ingredients from ./data/ingredients.json, file may be corrupted or missing");
        }
    }

    // REQUIRES: i is a non null ingredient
    // EFFECTS: returns a string in the format: 
    // name [Sweetness: x, Strength: y, Thickness: z]
    private String formatIngredientText(Ingredient i) {
        return i.getName()
            + " [Sweetness: " + i.getSweetness()
            + ", Strength: " + i.getStrength()
            + ", Thickness: " + i.getThickness() + "]";
    }

    // MODIFIES: this
    // EFFECTS: removes all text from text fields
    private void resetText() {
        nameField.setText("");
        sweetnessField.setText("");
        strengthField.setText("");
        thicknessField.setText("");
    }

     // MODIFIES: this
     // EFFECTS creates a new ingredient, hides ingredient making
     // panel and updates ingreients view to show the new ingredient,
    private void createIngredient() {
        String name = nameField.getText();

        int sweetness = 0;
        int strength = 0;
        int thickness = 0;

        try {
            sweetness = Integer.parseInt(sweetnessField.getText());
            strength = Integer.parseInt(strengthField.getText());
            thickness = Integer.parseInt(thicknessField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e);
        }

        parentView.controller.makeNewIngredient(name, sweetness, strength, thickness);
        inputPanel.setVisible(false);
        displayIngredients();
        revalidate();
        repaint();
    }
}

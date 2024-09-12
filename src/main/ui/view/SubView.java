package ui.view;

import java.awt.event.ActionListener;

import javax.swing.*;

// Represents the abstract class for sub views of the main menu
public abstract class SubView extends JPanel implements ActionListener {

    protected ContentView parentView;

    protected JPanel contentView;
    protected JPanel sidebar;

    // Creates a new subview with a left and rigth plane
    public SubView(ContentView contentView) {
        super();
        parentView = contentView;
        sidebar = new JPanel();
        initalizeSubView();
    }

    // MODIFIES: this
    // EFFECTS: initalizes the right and left planes of the split view, 
    // and sets the default divider location such that the left plane is
    // 75% of the minimum width
    private void initalizeSubView() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(initalizeContentView());
        add(intializeSidebar());
    }

    // MODIFIES: this
    // EFFECTS: creates the button for removing an item
    protected void addRemoveButton(String remove) {
        SidebarButton removeDrinkButton = new SidebarButton("Remove " + remove, "remove", this);
        sidebar.add(removeDrinkButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons for saving / loading data
    protected void addPersistenceButtons(String type) {
        SidebarButton saveRecipesButton = new SidebarButton("Save " + type, "save", this);
        sidebar.add(saveRecipesButton);

        SidebarButton loadDrinksButton = new SidebarButton("Load " + type, "load", this);
        sidebar.add(loadDrinksButton);

    }

    // MODIFIES: this
    // EFFECTS: creates the button for backwards navigation
    protected void addBackButton() {
        SidebarButton backButton = new SidebarButton("Go Back", "back", this);
        sidebar.add(backButton);

        revalidate();
    }


    // MODIFIES: this
    // EFFECTS: hides this, and shows parentView
    protected void goBack() {
        this.setVisible(false);
        parentView.reset();
    }


    // MODIFIES: this
    // EFFECTS: creates the left panel of the split plane
    protected abstract JPanel initalizeContentView();

    // MODIFIES: this
    // EFFECTS: creates the right panel of the split plane
    protected abstract JPanel intializeSidebar();
}

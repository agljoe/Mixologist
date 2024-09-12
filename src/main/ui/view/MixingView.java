package ui.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Ingredient;

// Represents the ui for mixing drinks
public class MixingView extends JPanel implements ActionListener {

    private ContentView parentView;
    private JPanel mixingPanel;
    private JPanel menu;
    private JTextField nameField;

    private String name;
    private List<JPanel> menuPanels;
    private int currentMixingPanel = 0;
    private int ingredientsCapacity;
    
    // EFFECTS: Creates a new MixingView with a parent view of ContentView
    // and collection of menu panels
    public MixingView(ContentView contentView) {
        super();
        parentView = contentView;
        menuPanels = new ArrayList<>();
        init();
    }


    // MODIFIES: this
    // EFFECTS: sets the layout and size for this
    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setVisible(true);
        add(createMixingPanel());
        createMenuPanels();
        add(menu);
    }

    // MODIFIES: this
    // EFFECTS: creates the left side of MixingView
    private JPanel createMixingPanel() {
        mixingPanel = new JPanel();
        mixingPanel.setPreferredSize(new Dimension(((ContentView.WIDTH * 3) / 4), ContentView.HEIGHT));
        nameField = new JTextField(20);
        mixingPanel.add(nameField);
        return mixingPanel;
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds all menus to mixingPanels
    private void createMenuPanels() {
        createNamePanel();
        createSizePanel();
        createIcePanel();
        createIngredientsPanel();
        createMixTypePanel();
        menu = new JPanel();
        menu.add(menuPanels.get(currentMixingPanel));
    }
    
    // MODIFIES: this
    // EFFECTS: initializes and adds all nameMenu to mixingPanels
    private void createNamePanel() {
        JPanel namePanel = new JPanel(new GridLayout(0, 1));
        namePanel.setSize(new Dimension(0, 0)); 

        namePanel.add(new SidebarButton("Next", "name", this));

        namePanel.setVisible(true);
        menuPanels.add(namePanel);
    }


    // MODIFIES: this
    // EFFECTS: initializes and adds all createSizePanel to mixingPanels
    private void createSizePanel() {
        JPanel sizePanel = new JPanel(new GridLayout(0, 1));
        sizePanel.setSize(new Dimension(0, 0)); 

        sizePanel.add(new SidebarButton("Small", "sizeSmall", this));
        sizePanel.add(new SidebarButton("Meduim", "sizeMedium", this));
        sizePanel.add(new SidebarButton("Large", "sizeLarge", this));

        sizePanel.setVisible(true);
        menuPanels.add(sizePanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds all iceMenu to mixingPanels
    private void createIcePanel() {
        JPanel icePanel = new JPanel(new GridLayout(0, 1));
        icePanel.setSize(new Dimension(0, 0)); 

        icePanel.add(new SidebarButton("None", "iceNone", this));
        icePanel.add(new SidebarButton("Less", "iceLess", this));
        icePanel.add(new SidebarButton("More", "iceMore", this));

        icePanel.setVisible(true);
        menuPanels.add(icePanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds all ingredientsMenu to mixingPanels
    private void createIngredientsPanel() {
        JPanel ingredientsPanel = new JPanel(new GridLayout(0, 1));
        ingredientsPanel.setSize(new Dimension(0, 0)); 

        List<Ingredient> ingredients = parentView.controller.getMenu().getIngredients();
        int index = 0;

        for (Ingredient i : ingredients) {
            ingredientsPanel.add(new SidebarButton(i.getName(), "ingredient" + index, this));
            index++;
        }

        ingredientsPanel.setVisible(true);
        menuPanels.add(ingredientsPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds all mixTypePanel to mixingPanels
    private void createMixTypePanel() {
        JPanel mixTypePanel = new  JPanel(new GridLayout(0, 1));
        mixTypePanel.setSize(new Dimension(0, 0));

        mixTypePanel.add(new SidebarButton("Stir", "mixStir", this));
        mixTypePanel.add(new SidebarButton("Shake (Thickness -1)", "mixShake", this));
        mixTypePanel.add(new SidebarButton("Don't Mix", "mixNone", this));

        mixTypePanel.setVisible(true);
        menuPanels.add(mixTypePanel);
    }

    // MODIFIES: this
    // EFFECTS: exectutes the command sent from the pressed button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("name")) {
            setName();
        }

        if (e.getActionCommand().contains("size")) {
            setSize(e.getActionCommand().substring(4,e.getActionCommand().length()));
        }

        if (e.getActionCommand().contains("ice")) {
            addIce(e.getActionCommand().substring(3,e.getActionCommand().length()));
        }

        if (e.getActionCommand().contains("ingredient")) {
            addIngredient(e.getActionCommand().substring(10,e.getActionCommand().length()));
        }

        if (e.getActionCommand().contains("mix")) {
            mixDrink((e.getActionCommand().substring(3,e.getActionCommand().length())));
        }
    }

    // MODIFIES: this
    // sets this.name to the text in nameField
    private void setName() {
        this.name = nameField.getText();
        updateContentView();
        updateMenuPanel();
    }


    // MODIFIES: this
    // EFFECTS: creates a new drink with name and size,
    // creates an event for creating a new drink and logs it
    private void setSize(String size) {
        parentView.controller.createDrink(name, size);
        ingredientsCapacity = getIngredientsCapacity(size);
        updateMenuPanel();
    }   

    // MODIFIES: this
    // EFFECTS: adds the specified ice amount,
    // creates an event for adding ice amount and logs it
    private void addIce(String amount) {
        parentView.controller.addIce(amount);
        updateMenuPanel();
    }

    // MODIFIES: this
    // EFFECTS: adds the specified ingredient ingredientsCapacity times,
    // if there is no more room for ingredients go to the next step,
    // creates an event for adding and indgredient, and logs it
    private void addIngredient(String index) {
        try {
            int i = Integer.parseInt(index);
            Ingredient ingredient = parentView.controller.getMenu().getIngredients().get(i);
            parentView.controller.addIngredient(ingredient);
            JOptionPane.showMessageDialog(this, "Added " + ingredient.getName());
            ingredientsCapacity--;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "No ingredient at index " + index);
        }

        if (ingredientsCapacity <= 0) {
            updateMenuPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: mixes the drink based on the users selection,
    // and adds dirnk to menu and recipes, if the drink is mixied creates a event for
    // shaking / stirring and logs it, creates and event to adding the drink to
    // menu and saving the recipe for drink and logs both events 
    private void mixDrink(String mix) {
        if (mix.equals("Stir")) {
            parentView.controller.stirDrink();
        } else if (mix.equals("Shake")) {
            parentView.controller.shakeDrink();
        }

        parentView.controller.addDrinkToMenu();
        parentView.controller.saveDrinkRecipe();

        goBack();
    }

    // MODIFIES: this
    // EFFECTS: returns to parentView after mixing a drink
    private void goBack() {
        parentView.remove(this);
        parentView.reset();
    }

    // MODIFIES: this
    // EFFECTS: hides nameField, and plays a gif of someone pouring a drink
    // on the left JPanel
    private void updateContentView() {
        nameField.setVisible(false);
          // from: https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
        BufferedImage drinkGif;
        try {
            drinkGif = ImageIO.read(new File("./images/tenor-4048409896.gif"));
            JLabel picLabel = new JLabel(new ImageIcon(drinkGif));
            mixingPanel.add(picLabel);
        } catch (IOException e) {
            System.out.println("Unable to locate image");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the menu to be the next menu required
    // to mix a drink
    private void updateMenuPanel() {
        menu.remove(menuPanels.get(currentMixingPanel));
        currentMixingPanel++;
        menu.add(menuPanels.get(currentMixingPanel));
        revalidate();
        repaint();
    }

    // MODIFES: this
    // EFFECTS: returns ingredientsCapacity to be 3, 4, or 5
    // if size is small, medium, or large respectively
    private int getIngredientsCapacity(String size) {
        switch (size) {
            case "Small":   
                return 3;
            case "Medium":   
                return 4;
            case "Large":   
                return 5;
            default:
                return 0;
        }
    }
}

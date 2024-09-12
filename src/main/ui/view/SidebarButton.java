package ui.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

// Represents an abstract button for sidebar in the Mixologist game
public class SidebarButton extends JButton {

    // Creates a new JButton with name, command, and actionListener
    public SidebarButton(String name, String command, ActionListener actionListener) {
        super(name);
        setActionCommand(command);
        addActionListener(actionListener);
    }
}

package ui;

// Repesents the main class for the Mixologist game
public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Mixologist();
            }
        });
    }
}

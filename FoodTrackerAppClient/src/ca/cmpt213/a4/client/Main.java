package ca.cmpt213.a4.client;

import ca.cmpt213.a4.client.view.FoodTrackerUserInterface;
import javax.swing.*;

/**
 * Class Main to run client side of the app, only have 1 command to start the GUI
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FoodTrackerUserInterface("Food Tracker App"));
    }
}

package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.control.ClientAppItemHandler;
import ca.cmpt213.a4.client.model.Consumable;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;


public class FoodTrackerUserInterface extends JFrame implements ActionListener {

    private JPanel mainPanel;
    //Fields for the main panel
    private JButton allButton;
    private JButton expiredButton;
    private JButton notExpiredButton;
    private JButton expiredIn7DaysButton;
    private JButton addItemButton;
    private JScrollPane scrollPane;
    private final JPanel  displayAllItem = new JPanel();
    private DialogBox addItemDialogBox;

    //Function to use handle data in another class
    private ClientAppItemHandler itemHandler = new ClientAppItemHandler();

    /**
     * Default constructor, will add all the necessary components to JFrame
     * @param tittle The Tittle of the app
     */
    public FoodTrackerUserInterface(String tittle) {
        //Create Java default windows application GUI
        super(tittle);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setContentPane(mainPanel);

        //Set preferred size for scroll pane large enough so that it will scroll
        scrollPane.setPreferredSize(new Dimension(1600, 1600));

        //Create a new panel to attach components to scroll pane
        displayAllItem.setLayout(new GridBagLayout());
        //Load data from previous save file
        displayAllItemInList(itemHandler.readPreviousJSONSaveFile());
        //Set the newly created panel as scroll pane viewport
        scrollPane.setViewportView(displayAllItem);

        //Display all item from the previous save file on the initial start up
        //Adding action listener to all the button
        allButton.addActionListener(this);
        expiredButton.addActionListener(this);
        notExpiredButton.addActionListener(this);
        expiredIn7DaysButton.addActionListener(this);
        addItemButton.addActionListener(this);

        //Save on exit to a .json file
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                itemHandler.saveItemListToJSONFile();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        this.setVisible(true);
    }

    //Override an implement how a button would work when clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String option = e.getActionCommand();
        if (option.equalsIgnoreCase("all")) {
            displayAllItemInList(itemHandler.listAll());
        }

        if (option.equalsIgnoreCase("expired")) {
            //ArrayList to store all items that is expired
            ArrayList<Consumable> expiredItem = itemHandler.listExpired();
            Collections.sort(expiredItem);
            displayAllItemInList(expiredItem);
        }

        if (option.equalsIgnoreCase("not expired")) {
            //ArrayList to store all items that are not expired
            ArrayList<Consumable> notExpiredItem = itemHandler.listNonExpired();
            Collections.sort(notExpiredItem);
            displayAllItemInList(notExpiredItem);
        }

        if (option.equalsIgnoreCase("expired in 7 Days")) {
            //ArrayList to store all items that will be expired in 7 days
            ArrayList<Consumable> expiredInSevenDays = itemHandler.listExpiredIn7Days();
            Collections.sort(expiredInSevenDays);
            displayAllItemInList(expiredInSevenDays);
        }

        //If user pressed "Add Item" button
        if (option.equalsIgnoreCase("add item")) {
            //Create new dialog box with no layout
            addItemDialogBox = new DialogBox(this);
            //Use helper method to create and put components into dialog box
            Consumable newItem = addItemDialogBox.createNewItem();
            itemHandler.addConsumableItem(newItem);
            displayAllItemInList(itemHandler.listAll());
        }
    }

    /**
     * Helper method to calculate how many days are left until the expired date
     * @param expiryDate Expiry date of the consumable
     * @return A String of how many days are left
     */
    private String numOfDayLeftString(LocalDate expiryDate) {
        LocalDate today = LocalDate.now();
        String output;
        long daysUntilExpired = today.until(expiryDate, ChronoUnit.DAYS);
        if (daysUntilExpired == 0) { //If the expiry date is today
            output = "This item will expire today";
        } else if (daysUntilExpired > 0) { //If the item is yet expired
            output = "This food item is expired in " + daysUntilExpired + " day(s)";
        } else { //If item is expired
            long numOfDaysExpired = -daysUntilExpired;
            output = "This food item is expired for " + numOfDaysExpired + " day(s)";
        }
        return output;
    }

    /**
     * Pass in an array list, then it would use the object from the array and create components based on it
     * @param listOfItemToDisplay An ArrayList storing all the objects we will created components from
     */
    private void displayAllItemInList(ArrayList<Consumable> listOfItemToDisplay) {
        //Sort it before handling
        Collections.sort(listOfItemToDisplay);

        //Wipe the current screen for a new one
        displayAllItem.removeAll();

        //If array is empty then print no item to display
        if (listOfItemToDisplay.size() == 0) {
            JLabel noItemToDisplay = new JLabel("No Item To Display!");
            displayAllItem.add(noItemToDisplay);
            displayAllItem.revalidate();
            displayAllItem.repaint();
        }

        else {
            //A constraint used to position panels
            GridBagConstraints gbc = new GridBagConstraints();

            //Index for the position
            int index = 0;
            for (Consumable currentItem : listOfItemToDisplay) {
                //Save a copy of the current item to remove it if required
                long ID = currentItem.getID();

                //Storing fields and display it to the scroll panel
                String type = currentItem.getType();
                JPanel newPanel = new JPanel();
                newPanel.setPreferredSize(new Dimension(750, 170));
                newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.PAGE_AXIS));
                TitledBorder borderTittle;
                borderTittle = BorderFactory.createTitledBorder("Item #" + index + "(" + type + ")");
                newPanel.setBorder(borderTittle);
                gbc.gridx = 0;
                gbc.gridy = index;

                newPanel.setVisible(true);
                displayAllItem.add(newPanel, gbc);

                //Create labels for the data we got from the current item of array list of food storage
                JLabel name = new JLabel("Name: " + currentItem.getName());
                newPanel.add(name);

                JLabel notes = new JLabel("Notes: " + currentItem.getNotes());
                newPanel.add(notes);

                JLabel price = new JLabel("Price: " + currentItem.getPrice());
                newPanel.add(price);

                JLabel measurement = new JLabel(type + ": " + currentItem.getMeasurement());
                newPanel.add(measurement);

                JLabel expiryDate = new JLabel("Expiry date: " + currentItem.getExpiryDate().toString());
                newPanel.add(expiryDate);

                JLabel numOfDayLeft = new JLabel(numOfDayLeftString(currentItem.getExpiryDate()));
                newPanel.add(numOfDayLeft);

                //Add a remove button and implement remove function
                JButton removeButton = new JButton("Remove");
                newPanel.add(removeButton);
                removeButton.addActionListener(e -> {
                    itemHandler.removeItem(ID);
                    displayAllItem.remove(newPanel);
                    displayAllItem.revalidate();
                    displayAllItem.repaint();
                });

                //Increment index for the next item
                index++;

                //Refresh the display panel with new components
                displayAllItem.revalidate();
                displayAllItem.repaint();
                newPanel.revalidate();
                newPanel.repaint();
            }
        }
    }


}

package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.control.ClientAppItemHandler;

import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.model.ConsumableFactory;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Class DialogBox extending JDialog, with all components of Add Item window in it
 */
public class DialogBox extends JDialog {
    private final ClientAppItemHandler foodStorage = new ClientAppItemHandler();
    private final ConsumableFactory consumableFactory = new ConsumableFactory();
    private JLabel measurementLabel;

    private JComboBox itemTypeBox;
    private JTextField itemNameTextField;
    private JTextField itemNotesTextField;
    private JTextField itemPriceTextField;
    private JTextField itemMeasurementTextField;
    private DatePicker itemExpiryDatePicker;


    public DialogBox(Frame owner) {
        super(owner, "Add Item", true);
        this.setLayout(null);
        this.setSize(400, 400);
        addItemTypeLabel();
        addItemTypeComboBox();
        implementItemTypeBoxListener();

        addItemNameLabel();
        addItemNameTextField();

        addItemNotesLabel();
        addItemNotesTextField();

        addItemPriceLabel();
        addItemPriceTextField();

        addMeasurementLabel();
        addItemMeasurementTextField();

        addItemExpiryDateLabel();
        addItemExpiryDatePicker();

        implementAddItemButtonListener();
        implementCancelButtonListener();
        this.setVisible(true);
    }

    private void addItemTypeLabel() {
        //Add the label for type combo box
        JLabel itemTypeLabel = new JLabel("Type: ");
        itemTypeLabel.setBounds(10, 10, 80, 25);
        this.add(itemTypeLabel);
    }

    private void addItemNameLabel() {
        //Add label for name text field
        JLabel itemNameLabel = new JLabel("Name: ");
        itemNameLabel.setBounds(10, 60, 80, 25);
        this.add(itemNameLabel);
    }

    private void addItemNotesLabel() {
        //Add the label for notes text field
        JLabel itemNotesLabel = new JLabel("Notes: ");
        itemNotesLabel.setBounds(10, 110, 80, 25);
        this.add(itemNotesLabel);
    }

    private void addItemPriceLabel () {
        //Add the label for price text field
        JLabel priceLabel = new JLabel("Price: ");
        priceLabel.setBounds(10, 160, 80, 25);
        this.add(priceLabel);
    }

    private void addMeasurementLabel() {
        //Add the label for measurement text field
        measurementLabel = new JLabel("Weight: ");
        measurementLabel.setBounds(10, 210, 80, 25);
        this.add(measurementLabel);
    }

    private void addItemExpiryDateLabel() {
        //Create a date picker to choose expiry date
        JLabel datePickerLabel = new JLabel("Expiry Date: ");
        datePickerLabel.setBounds(10, 260, 80, 25);
        this.add(datePickerLabel);
    }

    private void addItemTypeComboBox() {
        //Add the combo box for consumable type
        String[] itemType = {"Food", "Drink"};
        itemTypeBox = new JComboBox(itemType);
        itemTypeBox.setBounds(60, 10, 300, 25);
        this.add(itemTypeBox);
    }

    private void addItemNameTextField() {
        //Add the text field for name
        itemNameTextField = new JTextField();
        itemNameTextField.setBounds(60, 60, 300, 25);
        this.add(itemNameTextField);
    }

    private void addItemNotesTextField() {
        //Add the text field for notes
        itemNotesTextField = new JTextField();
        itemNotesTextField.setBounds(60, 110, 300, 25);
        this.add(itemNotesTextField);
    }

    private void addItemPriceTextField() {
        //Add the text field for price
        itemPriceTextField = new JTextField();
        itemPriceTextField.setBounds(60, 160, 300, 25);
        this.add(itemPriceTextField);
    }

    private void addItemMeasurementTextField() {
        //Add the text field for measurement
        itemMeasurementTextField = new JTextField();
        itemMeasurementTextField.setBounds(60, 210, 300, 25);
        this.add(itemMeasurementTextField);
    }

    private void addItemExpiryDatePicker() {
        itemExpiryDatePicker = new DatePicker();
        itemExpiryDatePicker.setBounds(100, 260, 250, 25);
        this.add(itemExpiryDatePicker);
    }

    private void implementItemTypeBoxListener() {
        //Change measurement text label depends on which item was chosen
        itemTypeBox.addItemListener(e -> {
            if (e.getSource() == itemTypeBox) {
                String type = (String) itemTypeBox.getSelectedItem();
                if (type.equalsIgnoreCase("Food")) {
                    measurementLabel.setText("Weight: ");
                }
                if (type.equalsIgnoreCase("Drink")) {
                    measurementLabel.setText("Volume: ");
                }
            }
        });
    }

    private void implementAddItemButtonListener() {
        //Create a button to add the item
        JButton addItemButton = new JButton("Add");
        addItemButton.setBounds(80, 330, 80, 25);
        this.add(addItemButton);
        addItemButton.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Add")) {
                checkForInvalidInput();
                createNewItem();
                exit();
            }
        });
    }

    private void implementCancelButtonListener() {
        //Create a button to close the add item window
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 330, 80, 25);
        this.add(cancelButton);
        cancelButton.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Cancel")) {
                exit();
            }
        });
    }


    /**
     * Check if any box is left emptied or invalid user input
     */
    private void checkForInvalidInput() {
        if (itemNameTextField.getText().isEmpty()) {
            showErrorMessage("Name can not be empty", "Invalid name");
        }

        if (itemPriceTextField.getText().isEmpty()) {
            showErrorMessage("Price can not empty","Invalid price");
        }

        if (itemMeasurementTextField.getText().isEmpty()) {
            showErrorMessage("Measurement can not be empty", "Invalid measurement");
        }

        if (Double.parseDouble(itemPriceTextField.getText()) <= 0) {
            showErrorMessage("Price can not be negative or equals to 0", "Invalid price");
        }

        if (Double.parseDouble(itemMeasurementTextField.getText()) <= 0) {
            showErrorMessage("Measurement can not be negative or equals to 0", "Invalid measurement");
        }
        if (itemExpiryDatePicker.getDate() == null) {
            showErrorMessage("Expiry Date can not be empty", "Invalid expiry date");
        }
    }

    public Consumable createNewItem() {
        String itemType = itemTypeBox.getSelectedItem().toString();
        String name = itemNameTextField.getText();
        String notes = itemNotesTextField.getText();
        double price = Double.parseDouble(itemPriceTextField.getText());
        double measurement = Double.parseDouble(itemMeasurementTextField.getText());
        LocalDate expiryDate = itemExpiryDatePicker.getDate();
        return consumableFactory.getConsumable(itemType,name,notes,price,measurement,expiryDate);
    }

    private void exit() {
        this.dispose();
    }

    /**
     * A method used to pass error message and error type to display in a JOptionPane
     * @param errorMessage The error message to display to user
     * @param errorType Type of error, is also the name of the error box
     */
    private void showErrorMessage(String errorMessage, String errorType) {
        JOptionPane.showMessageDialog(this,
                errorMessage,
                errorType,
                JOptionPane.WARNING_MESSAGE);
    }

}

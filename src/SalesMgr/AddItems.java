package SalesMgr;

import javax.swing.*;

public class AddItems extends JFrame{
    private JPanel AddItemPanel;
    private JPanel WestLabel;
    private JPanel SouthButton;
    private JPanel EastTextField;
    private JTextField textFieldItemName;
    private JTextField textFieldItemQuantity;
    private JTextField textFieldUnitPrice;
    private JComboBox comboBoxCategory;
    private JButton Addbutton;
    private JButton Exitbutton;

    // Constructor to initialize the form
    public AddItems() {
        setContentPane(AddItemPanel); // This will set the JPanel created in the GUI builder as the content pane
        setTitle("Add New Item");
        setSize(400, 300);  // Set size as needed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // This will close only this form
        setLocationRelativeTo(null); // Center the form on the screen
    }
}

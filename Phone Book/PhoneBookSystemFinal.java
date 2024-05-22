import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PhoneBookSystemFinal extends JFrame {
    private JPanel homePanel;
    private JPanel addContactPanel;
    private JPanel searchPanel;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JTextArea displayArea;
    private JTextField displayNumberField;
    private JTextField displayAddressField;
    private JButton addContactButton;
    private JButton searchButton;
    private JButton exitButton;
    private JButton returnHomeButton;
    private JButton addEntryButton;
    private JButton searchReturnHomeButton;
    private JButton searchContactButton;
    private JTextField searchField;

    private static final String FILE_NAME = "contacts.txt";

    public PhoneBookSystemFinal() {
        setTitle("Phone Book System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        initComponents();

        // Set home panel as default
        setContentPane(homePanel);
    }

    private void initComponents() {
        homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(4, 1));

        addContactPanel = new JPanel();
        addContactPanel.setLayout(new GridLayout(4, 2));

        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(6, 1));

        nameField = new JTextField();
        phoneNumberField = new JTextField();
        addressField = new JTextField();
        displayArea = new JTextArea(10, 30);
        displayNumberField = new JTextField();
        displayAddressField = new JTextField();
        addContactButton = new JButton("Add Contact");
        searchButton = new JButton("Search");
        exitButton = new JButton("Exit");

        addContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setContentPane(addContactPanel);
                revalidate();
                repaint();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setContentPane(searchPanel);
                revalidate();
                repaint();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        returnHomeButton = new JButton("Return to Home Page");
        addEntryButton = new JButton("Add");
        searchReturnHomeButton = new JButton("Return to Home Page");
        searchContactButton = new JButton("Search");
        searchField = new JTextField();

        addEntryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String address = addressField.getText();

                try (FileWriter fw = new FileWriter(FILE_NAME, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println(name + "," + phoneNumber + "," + address);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Contact added successfully!");
                nameField.setText("");
                phoneNumberField.setText("");
                addressField.setText("");
            }
        });

        searchContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchName = searchField.getText();
                displayArea.setText("");
                displayNumberField.setText("");
                displayAddressField.setText("");
                boolean found = false;
                try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 3 && parts[0].equalsIgnoreCase(searchName)) {
                            found = true;
                            displayNumberField.setText(parts[1]);
                            displayAddressField.setText(parts[2]);
                            break; // Exit loop after finding the first match
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (!found) {
                    JOptionPane.showMessageDialog(null, "Details not found.");
                }
            }
        });

        returnHomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setContentPane(homePanel);
                revalidate();
                repaint();
            }
        });

        searchReturnHomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setContentPane(homePanel);
                revalidate();
                repaint();
            }
        });

        addContactPanel.add(new JLabel("Name:"));
        addContactPanel.add(nameField);
        addContactPanel.add(new JLabel("Phone Number:"));
        addContactPanel.add(phoneNumberField);
        addContactPanel.add(new JLabel("Address:"));
        addContactPanel.add(addressField);
        addContactPanel.add(addEntryButton);
        addContactPanel.add(returnHomeButton);

        searchPanel.add(new JLabel("Enter Name to Search:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Number:"));
        searchPanel.add(displayNumberField);
        searchPanel.add(new JLabel("Address:"));
        searchPanel.add(displayAddressField);
        searchPanel.add(searchContactButton);
        searchPanel.add(searchReturnHomeButton);

        homePanel.add(addContactButton);
        homePanel.add(searchButton);
        homePanel.add(exitButton);
        homePanel.add(new JLabel()); // Empty label for spacing
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PhoneBookSystemFinal().setVisible(true);
            }
        });
    }
}

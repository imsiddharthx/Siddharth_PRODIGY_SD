import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PRODIGY_SD_03 extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private Map<String, Contact> contacts;

    public PRODIGY_SD_03() {
        setTitle("Contact Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contacts = loadContacts();
        createWidgets();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createWidgets() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(15);
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(15);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });
        add(addButton, gbc);

        gbc.gridx = 1;
        JButton viewButton = new JButton("View Contacts");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewContacts();
            }
        });
        add(viewButton, gbc);
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            contacts.put(name, new Contact(name, phone, email));
            saveContacts();
            JOptionPane.showMessageDialog(this, "Contact added successfully!");
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewContacts() {
        JFrame viewFrame = new JFrame("View Contacts");
        viewFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        for (String name : contacts.keySet()) {
            Contact contact = contacts.get(name);

            gbc.gridx = 0;
            gbc.gridy = row;
            viewFrame.add(new JLabel(contact.getName()), gbc);

            gbc.gridx = 1;
            viewFrame.add(new JLabel(contact.getPhone()), gbc);

            gbc.gridx = 2;
            viewFrame.add(new JLabel(contact.getEmail()), gbc);

            gbc.gridx = 3;
            JButton editButton = new JButton("Edit");
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editContact(contact);
                }
            });
            viewFrame.add(editButton, gbc);

            gbc.gridx = 4;
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteContact(contact.getName());
                    viewFrame.dispose();
                    viewContacts();
                }
            });
            viewFrame.add(deleteButton, gbc);

            row++;
        }

        viewFrame.pack();
        viewFrame.setLocationRelativeTo(this);
        viewFrame.setVisible(true);
    }

    private void editContact(Contact contact) {
        JFrame editFrame = new JFrame("Edit Contact - " + contact.getName());
        editFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        editFrame.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(contact.getName(), 15);
        editFrame.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        editFrame.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        JTextField phoneField = new JTextField(contact.getPhone(), 15);
        editFrame.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        editFrame.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(contact.getEmail(), 15);
        editFrame.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText();
                String newPhone = phoneField.getText();
                String newEmail = emailField.getText();
                if (contacts.containsKey(contact.getName())) {
                    contacts.remove(contact.getName());
                    contacts.put(newName, new Contact(newName, newPhone, newEmail));
                    saveContacts();
                    JOptionPane.showMessageDialog(editFrame, "Contact updated successfully!");
                    editFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(editFrame, "Contact not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        editFrame.add(saveButton, gbc);

        editFrame.pack();
        editFrame.setLocationRelativeTo(this);
        editFrame.setVisible(true);
    }

    private void deleteContact(String name) {
        if (contacts.containsKey(name)) {
            contacts.remove(name);
            saveContacts();
            JOptionPane.showMessageDialog(this, "Contact deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Map<String, Contact> loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contacts.ser"))) {
            return (Map<String, Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts.ser"))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PRODIGY_SD_03();
            }
        });
    }
}

class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

// Employee class to store employee details
class Employee {
    int id;
    String name, gender, phone, email;

    Employee(int id, String name, String gender, String phone, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }
}

public class AddMultipleEmployees extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfemail;
    JComboBox<String> cbGender;
    JButton addEmployee, saveAll, back;

    ArrayList<Employee> employees = new ArrayList<>(); // to store multiple employees

    AddMultipleEmployees() {
        setLayout(null);

        JLabel heading = new JLabel("Add Multiple Employee Details");
        heading.setBounds(250, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        // Name
        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);

        // Gender
        JLabel labeleducation = new JLabel("Gender");
        labeleducation.setBounds(50, 200, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);

        String gender[] = {"Male", "Female"};
        cbGender = new JComboBox<>(gender);
        cbGender.setBackground(Color.WHITE);
        cbGender.setBounds(200, 200, 150, 30);
        add(cbGender);

        // Phone
        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(50, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(200, 250, 150, 30);
        add(tfphone);

        // Email
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        // Add Employee Button (stores in ArrayList)
        addEmployee = new JButton("Add Employee");
        addEmployee.setBounds(100, 400, 150, 40);
        addEmployee.setBackground(Color.BLACK);
        addEmployee.setForeground(Color.WHITE);
        addEmployee.addActionListener(this);
        add(addEmployee);

        // Save All to DB
        saveAll = new JButton("Save All");
        saveAll.setBounds(300, 400, 150, 40);
        saveAll.setBackground(Color.BLACK);
        saveAll.setForeground(Color.WHITE);
        saveAll.addActionListener(this);
        add(saveAll);

        // Back Button
        back = new JButton("Back");
        back.setBounds(500, 400, 150, 40);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(800, 600);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addEmployee) {
            // Add employee to ArrayList
            String name = tfname.getText();
            String gender = (String) cbGender.getSelectedItem();
            String phone = tfphone.getText();
            String email = tfemail.getText();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            // Temporary ID 0, real ID assigned on Save All
            employees.add(new Employee(0, name, gender, phone, email));

            JOptionPane.showMessageDialog(null, "Employee added to list! Total: " + employees.size());

            // Clear fields for next entry
            tfname.setText("");
            tfphone.setText("");
            tfemail.setText("");
            cbGender.setSelectedIndex(0);

        } else if (ae.getSource() == saveAll) {
            // Save all employees in ArrayList to DB
            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No employees to save!");
                return;
            }

            try {
                Conn db = new Conn();

                // Get the current max ID from the DB
                Statement stmt = db.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM emp");
                int lastId = 0;
                if (rs.next()) {
                    lastId = rs.getInt(1); // get the max ID, if table is empty will return 0
                }
                rs.close();
                stmt.close();

                // Prepare insert statement
                String sql = "INSERT INTO emp VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = db.getConnection().prepareStatement(sql);

                // Assign unique incremented IDs based on DB max ID
                for (Employee emp : employees) {
                    lastId++; // increment DB max ID
                    emp.id = lastId; // update employee ID
                    ps.setInt(1, emp.id);
                    ps.setString(2, emp.name);
                    ps.setString(3, emp.gender);
                    ps.setString(4, emp.phone);
                    ps.setString(5, emp.email);
                    ps.addBatch();
                }

                ps.executeBatch(); // execute all inserts at once
                JOptionPane.showMessageDialog(null, "All employees saved successfully!");
                employees.clear(); // clear the list

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new AddMultipleEmployees();
    }
}

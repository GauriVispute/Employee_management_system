import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import com.google.gson.Gson;

// Employee class
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

    ArrayList<Employee> employees = new ArrayList<>();

    AddMultipleEmployees() {
        setLayout(null);

        JLabel heading = new JLabel("Add Multiple Employee Details");
        heading.setBounds(250, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);

        JLabel labelgender = new JLabel("Gender");
        labelgender.setBounds(50, 200, 150, 30);
        labelgender.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelgender);

        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cbGender.setBounds(200, 200, 150, 30);
        add(cbGender);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(50, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(200, 250, 150, 30);
        add(tfphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        addEmployee = new JButton("Add Employee");
        addEmployee.setBounds(100, 400, 150, 40);
        addEmployee.addActionListener(this);
        add(addEmployee);

        saveAll = new JButton("Save All");
        saveAll.setBounds(300, 400, 150, 40);
        saveAll.addActionListener(this);
        add(saveAll);

        back = new JButton("Back");
        back.setBounds(500, 400, 150, 40);
        back.addActionListener(this);
        add(back);

        setSize(800, 600);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == addEmployee) {
            String name = tfname.getText();
            String gender = (String) cbGender.getSelectedItem();
            String phone = tfphone.getText();
            String email = tfemail.getText();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            employees.add(new Employee(0, name, gender, phone, email));
            JOptionPane.showMessageDialog(null, "Employee added. Total: " + employees.size());

            tfname.setText("");
            tfphone.setText("");
            tfemail.setText("");
            cbGender.setSelectedIndex(0);
        }

        else if (ae.getSource() == saveAll) {

            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No employees to save!");
                return;
            }

            try {
                Gson gson = new Gson();
                String json = gson.toJson(employees);

                URL url = new URL("http://localhost:8080/api/employees/bulk");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                os.write(json.getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = con.getResponseCode();

                if (responseCode == 200 || responseCode == 201) {
                    JOptionPane.showMessageDialog(null, "All employees saved successfully!");
                    employees.clear();
                } else {
                    JOptionPane.showMessageDialog(null, "Server error: " + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "API connection failed!");
            }
        }

        else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new AddMultipleEmployees();
    }
}

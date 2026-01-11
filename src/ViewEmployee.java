import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    JTextField tfEmployeeId;  // Changed from Choice to JTextField
    JButton search, print, update, back;

    ViewEmployee() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel searchlbl = new JLabel("Enter Employee Id");
        searchlbl.setBounds(20, 20, 150, 20);
        add(searchlbl);

        tfEmployeeId = new JTextField();
        tfEmployeeId.setBounds(180, 20, 150, 25);
        add(tfEmployeeId);

        table = new JTable();

        // Show all employees initially
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from emp");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        search = new JButton("Search");
        search.setBounds(20, 70, 80, 25);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 25);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(220, 70, 80, 25);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(320, 70, 80, 25);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String empId = tfEmployeeId.getText().trim();
            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter an Employee ID");
                return;
            }

            String query = "select * from emp where id = '" + empId + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);

                if (!rs.next()) {
                    // No employee found
                    JOptionPane.showMessageDialog(null, "Employee ID doesn't exist");
                } else {
                    // Move cursor back to first row for DbUtils
                    rs.beforeFirst();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            String empId = tfEmployeeId.getText().trim();
            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter an Employee ID to update");
                return;
            }

            // Optional: check if employee exists before opening update
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from emp where id = '" + empId + "'");
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Employee ID doesn't exist");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            setVisible(false);
            new UpdateEmployee(empId);
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}

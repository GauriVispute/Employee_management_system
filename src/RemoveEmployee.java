import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveEmployee extends JFrame implements ActionListener {

    JTextField cEmpId;
    JButton delete, back;
    JLabel lblname, lblphone, lblemail;

    RemoveEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel labelempId = new JLabel("Employee Id");
        labelempId.setBounds(50, 50, 100, 30);
        add(labelempId);

        cEmpId = new JTextField();
        cEmpId.setBounds(200, 50, 150, 30);
        add(cEmpId);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 100, 100, 30);
        add(labelname);

        lblname = new JLabel();
        lblname.setBounds(200, 100, 200, 30);
        add(lblname);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(50, 150, 100, 30);
        add(labelphone);

        lblphone = new JLabel();
        lblphone.setBounds(200, 150, 200, 30);
        add(lblphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 200, 100, 30);
        add(labelemail);

        lblemail = new JLabel();
        lblemail.setBounds(200, 200, 300, 30);
        add(lblemail);

        // Fetch employee details as user types ID
        cEmpId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                fetchEmployee();
            }
        });

        delete = new JButton("Delete");
        delete.setBounds(80, 300, 100, 30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(220, 300, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/delete.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 600, 400);
        add(image);

        setSize(1000, 400);
        setLocation(300, 150);
        setVisible(true);
    }

    private void fetchEmployee() {
        if (cEmpId.getText().isEmpty()) {
            lblname.setText("");
            lblphone.setText("");
            lblemail.setText("");
            return;
        }

        try {
            Conn c = new Conn();
            String query = "SELECT * FROM emp WHERE ID = ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(cEmpId.getText()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Employee exists, show details
                lblname.setText(rs.getString("NAME"));
                lblphone.setText(rs.getString("PHONE_NO"));
                lblemail.setText(rs.getString("EMAIL"));
            } else {
                // Employee ID doesn't exist, show dialog
                lblname.setText("");
                lblphone.setText("");
                lblemail.setText("");
                JOptionPane.showMessageDialog(null, "Employee ID does not exist");
            }

            rs.close();
            ps.close();
            c.c.close();

        } catch (NumberFormatException e) {
            // Invalid number entered, just clear labels
            lblname.setText("");
            lblphone.setText("");
            lblemail.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == delete) {
            if (cEmpId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Employee ID");
                return;
            }

            // Check if employee exists before deleting
            if (lblname.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Employee ID does not exist");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "DELETE FROM emp WHERE ID = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(cEmpId.getText()));

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Employee Deleted Successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Employee ID not found");
                }

                ps.close();
                c.c.close();

                setVisible(false);
                new Home();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}

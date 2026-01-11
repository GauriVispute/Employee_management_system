import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener{
    
    JTextField  tfphone,  tfemail;
    JLabel lblempId;
    JButton add, back;
    String empId;
    
    UpdateEmployee(String empId) {
        this.empId = empId;
        //getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("UPDATE EMPLOYEE DETAILS");
        heading.setBounds(250, 30, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);
        
        JLabel labelempId = new JLabel("Employee id");
        labelempId.setBounds(50, 150, 150, 30);
        labelempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelempId);
        
        lblempId = new JLabel();
        lblempId.setBounds(200, 150, 150, 30);
        lblempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblempId);
        
        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 200, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);
        
        JLabel lblname = new JLabel();
        lblname.setBounds(200, 200, 150, 30);
        add(lblname);
        
        
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
        

        
        try {
            Conn c = new Conn();
            String query = "select * from emp where id = '"+empId+"'";
            ResultSet rs = c.s.executeQuery(query);
            while(rs.next()) {
                lblname.setText(rs.getString("NAME"));
                tfphone.setText(rs.getString("PHONE_NO"));
                tfemail.setText(rs.getString("EMAIL"));
                lblempId.setText(rs.getString("ID"));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        add = new JButton("Update Details");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);
        
        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);
        
        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String phone = tfphone.getText();
            String email = tfemail.getText();
          
            
            try {
                Conn conn = new Conn();
//                String query = "update emp set  phone = '"+phone+"', email =  '"+email+"' where id = '"+empId+"'";
//                conn.s.executeUpdate(query);
                
                String query = "UPDATE emp SET phone_no = ?, email = ? WHERE id = ?";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, phone);
                pst.setString(2, email);
                pst.setString(3, empId);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Details updated successfully");
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
        new UpdateEmployee("");
    }
}
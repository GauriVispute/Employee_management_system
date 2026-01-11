import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployee extends JFrame implements ActionListener{	
    
	private static int number = 2;
    JTextField tfname,tfGender, tfphone,  tfemail;
    JComboBox cbGender;
    JLabel lblempId;
    JButton add, back;
    
    AddEmployee() {
        //getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);
        
        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);
        
        JLabel labeleducation = new JLabel("Gender");
        labeleducation.setBounds(50,200 , 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);
        
        String gender[] = {"Male", "Female", };
        cbGender = new JComboBox(gender);
        cbGender.setBackground(Color.WHITE);
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
        
        
        
        add = new JButton("Add Details");
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
            String name = tfname.getText();   
            String gender  = (String) cbGender.getSelectedItem();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            
            try {
                Conn db = new Conn();
                
//                String query = "insert into emp values("+number+",'"+name+"','"+gender+"', '"+phone+"', '"+email+"');";
//                conn.s.executeUpdate(query);
                
                String sql = "INSERT INTO emp VALUES (?, ?, ?, ?, ?)";

                PreparedStatement ps = db.getConnection().prepareStatement(sql); 
                
                ps.setInt(1, number);
                ps.setString(2, name);
                ps.setString(3, gender);
                ps.setString(4, phone);
                ps.setString(5, email);
                
                ps.executeUpdate();
                ++number;
                
                JOptionPane.showMessageDialog(null, "Details added successfully");
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
		new AddEmployee();
		
	}

}

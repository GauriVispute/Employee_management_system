import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener{
	private JFrame frame;
	private JLabel heading;
	private JButton clickhere;
	
	
	MainFrame() {
		
		frame = new JFrame();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		
		//heading
		heading  = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
		heading.setBounds(75,30,1200,80);
		heading.setFont(new Font("serif",Font.PLAIN,60));
		heading.setForeground(Color.RED);
		add(heading);
		
		//image
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/front.jpg"));
	    Image i2= i1.getImage().getScaledInstance(1100, 700,Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image  = new JLabel(i3);
		image.setBounds(50,100,1050,500);	
		add(image);
		
		clickhere = new JButton("CLICK HERE TO CONTINUE");
		clickhere.setBounds(400,400,300,70);
		clickhere.setBackground(Color.BLACK);
		clickhere.setForeground(Color.WHITE);
		clickhere.addActionListener(this);
		add(clickhere);
		
		
		
		setSize(1170,650);
		setLocation(50,50);
		setVisible(true);
		
		while(true) {
			heading.setVisible(false);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			heading.setVisible(true);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		new Home();
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}

	

}

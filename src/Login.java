import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener{

	JLabel LoginHeader, EmailLabel, PasswordLabel;
	JTextField EmailText;
	JPasswordField PasswordText;
	JButton Login , SignUp;
	JPanel north, center, south;
	
	Font font = new Font("Serif", Font.BOLD, 20);
	
	DbConnection db;
	
	ResultSet loginvalid;
	
	public Login(DbConnection db) {
		// TODO Auto-generated constructor stub
		
		this.db = db;
		//North
		LoginHeader = new JLabel("Login Form");
		LoginHeader.setFont(font);
		
		north = new JPanel();
		north.add(LoginHeader);
		
		//Center
		EmailLabel = new JLabel("Email");
		PasswordLabel = new JLabel("Password");
		
		EmailText = new JTextField();
		PasswordText = new JPasswordField();
	
		center = new JPanel(new GridLayout(2,2,0,20));
		center.setBorder(new EmptyBorder(70,10,70,10));
		
		center.add(EmailLabel);
		center.add(EmailText);
		center.add(PasswordLabel);
		center.add(PasswordText);
		
		//South
		
		Login = new JButton("Login");
		Login.addActionListener(this);
		SignUp = new JButton("Sign Up Here");
		SignUp.addActionListener(this);
		SignUp.setBorder(null);
		SignUp.setContentAreaFilled(false);
		
		south = new JPanel(new GridLayout(2,1,0,20));
		south.setBorder(new EmptyBorder(0,60,20,60));
		south.add(Login);
		south.add(SignUp);
		
		addcomponent();
		
		setVisible(true);
		setSize(400,400);
		setTitle("Login Form");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	
	void addcomponent() {
	
		this.add(north,BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		
	}
	
	public User userValidation() {
		loginvalid = db.userloginValidation();
		User userLogged = null;
		try {
			while(loginvalid.next()) {
				User user = new User(loginvalid.getObject(1).toString(), loginvalid.getObject(2).toString(), loginvalid.getObject(3).toString(), loginvalid.getObject(4).toString(), loginvalid.getObject(5).toString(), loginvalid.getObject(6).toString(), loginvalid.getObject(7).toString(), loginvalid.getObject(8).toString());
				
				if (EmailText.getText().equals(user.getUserEmail())) {
					if (String.valueOf(PasswordText.getPassword()).equals(user.getUserPassword())) {

						userLogged = user;
					} else {

						
					}
				} else {

				}
			}
		} catch (HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userLogged;
	}
	

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == Login) {
			
			User loggedUser = userValidation();
			if (loggedUser != null) {
				new MainMenu(db, loggedUser);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "please login to a valid account");
			}
		}
		if (e.getSource() == SignUp) {
			new Register(db);
		}
	}
	
	

}

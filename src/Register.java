import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame implements ActionListener{

	JPanel north, center, south;
	JLabel HeaderLabel, IDlabel, userNameLabel, EmailLabel, PhoneLabel, AddressLabel, PasswordLabel, GenderLabel, RoleLabel;
	JTextField IDText, UserNameText, EmailText, PhoneText;
	JTextArea AddressText;
	JPasswordField PasswordText;
	JRadioButton Female, Male;
	JComboBox<String> RoleCombo;
	JButton RegisterButton, SignInButton;
	
	Font font = new Font("Serif", Font.BOLD, 20);
	DbConnection db;
	
	public Register(DbConnection db) {
		// TODO Auto-generated constructor stub
		this.db = db;
		
		//North
		HeaderLabel = new JLabel("Register Form");
		HeaderLabel.setFont(font);
		
		north = new JPanel();
		north.setBorder(new EmptyBorder(10,0,20,0));
		north.add(HeaderLabel);
		
		//center
		
		IDlabel = new JLabel("ID");
		userNameLabel = new JLabel("Username");
		EmailLabel = new JLabel("Email");
		PhoneLabel = new JLabel("Phone");
		AddressLabel = new JLabel("Address");
		PasswordLabel = new JLabel("Password");
		GenderLabel	 = new JLabel("Gender");
		RoleLabel = new JLabel("Role");
		
		String Id = db.newUserId();
		IDText = new JTextField(Id);
		IDText.setEnabled(false);
		UserNameText = new JTextField();
		EmailText = new JTextField();
		PhoneText = new JTextField();
		
		AddressText = new JTextArea();
		AddressText.setLineWrap(true);
		AddressText.setPreferredSize(new Dimension(40,150));
		JScrollPane addscroll = new JScrollPane(AddressText);
//		JPanel AddressText = new JPanel();
//		AddressText.add(addscroll);
		
		PasswordText = new JPasswordField();
		Male = new JRadioButton("Male");
		Female = new JRadioButton("Female");
		
		JPanel genderRad = new JPanel();
		genderRad.add(Male);
		genderRad.add(Female);
		
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(Male);
		genderGroup.add(Female);
		genderGroup.setSelected(Male.getModel(), true);
		
		String[] role = {"Customer", "Admin"};
		RoleCombo = new JComboBox<String>(role);
		
		center = new JPanel(new GridLayout(8,2,0,20));
		center.setBorder(new EmptyBorder(0,10,10,10));
		center.add(IDlabel);
		center.add(IDText);
		center.add(userNameLabel);
		center.add(UserNameText);
		center.add(EmailLabel);
		center.add(EmailText);
		center.add(PhoneLabel);
		center.add(PhoneText);
		center.add(AddressLabel);
		center.add(addscroll);
		center.add(PasswordLabel);
		center.add(PasswordText);
		center.add(GenderLabel);
		center.add(genderRad);
		center.add(RoleLabel);
		center.add(RoleCombo);
		
		//south
		RegisterButton = new JButton("Register");
		RegisterButton.addActionListener(this);
		SignInButton = new JButton("Sign In");
		SignInButton.setBorder(null);
		SignInButton.setContentAreaFilled(false);
		SignInButton.addActionListener(this);
		
		south = new JPanel(new GridLayout(2,1,0,20));
		south.setBorder(new EmptyBorder(10,70,10,70));
		south.add(RegisterButton);
		south.add(SignInButton);
		
		addcomponent();
		
		setVisible(true);
		setSize(400,700);
		setTitle("Register Form");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	void addcomponent() {
		
		this.add(north,BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == RegisterButton) {
			registervalidation();
		}
		if (e.getSource() == SignInButton) {
			new Login(db);
			this.dispose();
		}
	}
	
	void registervalidation() {
		if (UserNameText.getText().length() <= 30 && UserNameText.getText().length() >= 5) {
			if (emailvalidation()) {
				if (PhoneText.getText().length() >= 12 && trynumeric()) {
					if (AddressText.getText().length() >= 12 && AddressText.getText().endsWith(" Street")) {
						if (validatehavealpha(String.valueOf(PasswordText.getPassword())) && validatenumb(String.valueOf(PasswordText.getPassword()))) {
							String gender = (Male.isSelected()) ? "Male" : "Female";
							User user = new User(IDText.getText().toString(), UserNameText.getText().toString(), EmailText.getText().toString(), String.valueOf(PasswordText.getPassword()), gender, AddressText.getText(), PhoneText.getText(), RoleCombo.getSelectedItem().toString());
							db.insertnewUser(user);
							new Login(db);
							this.dispose();
							}  else {	
								JOptionPane.showMessageDialog(this, "Please input a valid password");
							}
						}  else {
						JOptionPane.showMessageDialog(this, "Please Input a valid address");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please Input a valid Phone number");	
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please Input a valid Email");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please Input a valid User Name");			
		}
	}
	
	private boolean trynumeric() {
		boolean valid = true;
		char[] number = PhoneText.getText().toCharArray();
		
		for (char c : number) {
			if (c < 48 || c > 57) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	private boolean validatehavealpha(String arg) {
		// TODO Auto-generated method stub
		boolean havealpha = false;
		arg = arg.toLowerCase();
		char[] string = arg.toCharArray();
				for (char c : string) {
					if (c >= 'a' && c <= 'z') {
						havealpha = true;
						break;
					}
				}
		return havealpha;		
	}
	
	private boolean validatenumb (String arg) {
		boolean havenumb = false;
		char[] string = arg.toCharArray();
		for (char c : string) {
			if (c >= '0' && c <= '9') {
				havenumb = true;
				break;
			}
		}
		return havenumb;
	}
	
	private int countalpha(char onsearch, String inwhere) {
		// TODO Auto-generated method stub
		char[] String = inwhere.toCharArray();
		int count = 0;
		for (char c : String) {
			if (c == onsearch) {
				count += 1;
			}
		}
		return count;
	}

	private boolean emailvalidation() {
		// TODO Auto-generated method stub
		boolean valid = false;
		String email = EmailText.getText();
		
		if (countalpha('@', email) == 1) {
			if (!(email.charAt((email.indexOf('@')+1)) == '.')) {
				if (!(email.startsWith("@") || email.startsWith(".") || email.endsWith("@") || email.endsWith("."))) {
					if (countalpha('.', email.substring((email.indexOf('@')+1))) == 1) {
						valid = true;
					}
				}
			}
		}
		
		
		return valid;
	}
}

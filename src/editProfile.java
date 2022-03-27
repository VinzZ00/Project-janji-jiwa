import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class editProfile extends JInternalFrame implements ActionListener{

	User user;
	DbConnection db;
	JPanel left, right;
	JPanel leftnorth, leftcenter, leftsouth;
	JPanel rightnorth, rightcenter, rightsouth;
	JLabel updateprofilelabel, changepasswordlabel, usernameLabel, userEmail, userPhone, userAddress, userGender, oldPassword, newPassword, confirmPassword;
	JTextField usernameText, emailText, userPhoneText;
	JPasswordField oldpasswordText, newPasswordText, confirmPasswordText;
	JTextArea addressText;
	JRadioButton male, female;
	JButton updateProfile, changePassword;
	
	public editProfile(User user, DbConnection db) {
		// TODO Auto-generated constructor stub
		super("Edit account", false, false, false);
		
		this.db = db;
		this.user = user;
		
		//leftnorth
		updateprofilelabel = new JLabel("Update Profile");
		leftnorth = new JPanel();
		leftnorth.add(updateprofilelabel);
		
		//leftcenter
		usernameLabel = new JLabel("Username");
		userEmail = new JLabel("User Email");
		userPhone = new JLabel("User Phone");
		userAddress = new JLabel("User Address");
		userGender = new JLabel("User Gender");
		
		usernameText = new JTextField();
		emailText = new JTextField();
		userPhoneText = new JTextField();
		addressText = new JTextArea();
		addressText.setLineWrap(true);
		
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
		
		ButtonGroup gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
		gender.setSelected(male.getModel(), true);
		
		JPanel gendPanel = new JPanel(new GridLayout(1,2));
		gendPanel.add(male);
		gendPanel.add(female);
		
		leftcenter = new JPanel(new GridLayout(5,2,0,20));
		leftcenter.add(usernameLabel);
		leftcenter.add(usernameText);
		leftcenter.add(userEmail);
		leftcenter.add(emailText);
		leftcenter.add(userPhone);
		leftcenter.add(userPhoneText);
		leftcenter.add(userAddress);
		leftcenter.add(addressText);
		leftcenter.add(userGender);
		leftcenter.add(gendPanel);
		
		//leftsouth
		
		updateProfile = new JButton("Update Profile");
		updateProfile.addActionListener(this);
		leftsouth = new JPanel();
		leftsouth.add(updateProfile);
		
		left = new JPanel(new BorderLayout(0,20));
		left.add(leftnorth, BorderLayout.NORTH);
		left.add(leftcenter, BorderLayout.CENTER);
		left.add(leftsouth, BorderLayout.SOUTH);
		
		
		
		//right panel
		
		//Rightnorth
		changepasswordlabel = new JLabel("Change Password");
		rightnorth = new JPanel();
		
		rightnorth.add(changepasswordlabel);
		
		//rightcenter
		oldPassword = new JLabel("Old Password");
		newPassword = new JLabel("New Password");
		confirmPassword = new JLabel("Confirm Password");
		
		oldpasswordText = new JPasswordField();
		newPasswordText = new JPasswordField();
		confirmPasswordText = new JPasswordField();
		
		rightcenter = new JPanel(new GridLayout(3,2,0,20));
		rightcenter.setBorder(new EmptyBorder(0,0,200,0));
		rightcenter.add(oldPassword);
		rightcenter.add(oldpasswordText);
		rightcenter.add(newPassword);
		rightcenter.add(newPasswordText);
		rightcenter.add(confirmPassword);
		rightcenter.add(confirmPasswordText);
		
		//rightsouth
		changePassword = new JButton("Change Password");
		changePassword.addActionListener(this);
		rightsouth = new JPanel();
		rightsouth.add(changePassword);
		
		
		right = new JPanel(new BorderLayout(0,20));
		right.add(rightnorth, BorderLayout.NORTH);
		right.add(rightcenter, BorderLayout.CENTER);
		right.add(rightsouth, BorderLayout.SOUTH);
		
		right.setBorder(new EmptyBorder(0,10,20,10));
		left.setBorder(new EmptyBorder(0,10,20,10));
		this.add(left);
		this.add(right);
		
		
		this.setLayout(new GridLayout(1,2,10,0));
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == updateProfile) {
			if ((usernameText.getText().isEmpty() || emailText.getText().isEmpty() || userPhoneText.getText().isEmpty() || addressText.getText().isEmpty())) {
				JOptionPane.showMessageDialog(this, "Please fill the blank form first");
			} else {
			System.out.println("Masuk");
			updateValidation();
			}
		}
		
		if (e.getSource() == changePassword) {
			int confirm = JOptionPane.showConfirmDialog (this, "Would You Like to Save your Previous Note First?","Warning",JOptionPane.YES_NO_OPTION);

			
			if (confirm == JOptionPane.YES_OPTION) {
					if (String.valueOf(oldpasswordText.getPassword()).equals(user.getUserPassword())) {
						if (validatehavealpha(String.valueOf(newPasswordText.getPassword())) && validatenumb(String.valueOf(newPasswordText.getPassword()))) {
							if (newPasswordText.getText().equals(confirmPasswordText.getText())) {
								db.changePassword(user, String.valueOf(newPasswordText.getPassword()));
							} else {
								JOptionPane.showMessageDialog(this,"your new password must be the same with the new password confirmation");
							} 
						} else {
							JOptionPane.showMessageDialog(this, "please input a new password that consist of number and alphabet");
						}
					} else {
						JOptionPane.showMessageDialog(this, "give the valid old password");
					} 
			}
		}
	}
	
	void updateValidation() {
		if (usernameText.getText().length() <= 30 && usernameText.getText().length() >= 5) {
			if (emailvalidation()) {
				if (userPhoneText.getText().length() >= 12 && trynumeric()) {
					if (addressText.getText().length() >= 12 && addressText.getText().endsWith(" Street")) {
						String gender = (male.isSelected()) ? "Male" : "Female";
						User useupdater = new User(user.getUserId(), usernameText.getText().toString(), emailText.getText().toString(), user.getUserPassword(), gender, addressText.getText(), userPhoneText.getText(), user.getUserRole());
						db.UpdateUser(useupdater);
					} else {
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
		char[] number = userPhoneText.getText().toCharArray();
		
		for (char c : number) {
			if (c < 48 || c > 57) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	private boolean emailvalidation() {
		// TODO Auto-generated method stub
		boolean valid = false;
		String email = emailText.getText();
		
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
	
}

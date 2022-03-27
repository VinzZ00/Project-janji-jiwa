import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class MainMenu {

	DbConnection db;
	User user;
	
	public MainMenu(DbConnection db, User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.db = db;
		
		if (user.getUserRole().equals("Admin")) {
			JFrame adminframe = new JFrame();
			JMenuBar mb = new JMenuBar();
			
			JMenu profile = new JMenu("Profile");
			JMenu Manage = new JMenu("Manage");
			
			JMenuItem EditProfile = new JMenuItem("Edit Profile");
			EditProfile.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					adminframe.getContentPane().removeAll();
					adminframe.add(new editProfile(user, db));
				}
			});
			JMenuItem Logoff = new JMenuItem("Log Off");
			Logoff.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new Login(db);
					adminframe.dispose();
				}
			});
			JMenuItem exit = new JMenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			
			JMenuItem manage = new JMenuItem("Manage Baverage");
			manage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					adminframe.getContentPane().removeAll();
					adminframe.add(new ManageBaverage(db));
				}
			});
			
			profile.add(EditProfile);
			profile.add(Logoff);
			profile.add(exit);
			
			Manage.add(manage);
			
			mb.add(profile);
			mb.add(Manage);
			
			String bodyText = String.format("Welcome to Janji Jywa, %s", user.getUserName());
			
			JLabel body = new JLabel(bodyText, SwingConstants.CENTER);
			body.setFont(new Font("SansSerif", Font.PLAIN, 20));
			
			adminframe.add(body, BorderLayout.CENTER);
		
			adminframe.setJMenuBar(mb);
			adminframe.setVisible(true);
			adminframe.setSize(900,600);
			adminframe.setTitle("Register Form");
			adminframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			adminframe.setResizable(false);
			adminframe.setLocationRelativeTo(null);
		}
		
		else if (user.getUserRole().equals("Customer")) {
			JFrame customerFrame = new JFrame();
			
			JMenuBar mb = new JMenuBar();
			
			JMenu profile = new JMenu("Profile");
			
			JMenu transaction = new JMenu("Transaction");
			
			JMenuItem EditProfile = new JMenuItem("Edit Profile");
			EditProfile.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					customerFrame.getContentPane().removeAll();
					customerFrame.add(new editProfile(user, db));
				}
			});
			
			JMenuItem Logoff = new JMenuItem("Log Off");
			Logoff.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new Login(db);
					customerFrame.dispose();
				}
			});
			JMenuItem exit = new JMenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			
			JMenuItem buybaverage = new JMenuItem("Buy Baverage");
			buybaverage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					customerFrame.getContentPane().removeAll();
					customerFrame.add(new BuyBaverage(user, db));
				}
			});
			
			JMenuItem viewTransaction = new JMenuItem("View Transaction History");
			viewTransaction.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					customerFrame.getContentPane().removeAll();
					customerFrame.add(new TransactionHistory(db));
				}
			});
			
			transaction.add(buybaverage);
			transaction.add(viewTransaction);
			
			profile.add(EditProfile);
			profile.add(Logoff);
			profile.add(exit);
			
			mb.add(profile);
			mb.add(transaction);
			
			String bodyText = String.format("Welcome to Janji Jywa, %s", user.getUserName());
			
			JLabel body = new JLabel(bodyText, SwingConstants.CENTER);
			body.setFont(new Font("SansSerif", Font.PLAIN, 20));
			
			customerFrame.add(body, BorderLayout.CENTER);
			
			customerFrame.setJMenuBar(mb);
			
			customerFrame.setVisible(true);
			customerFrame.setSize(900,600);
			customerFrame.setTitle("Register Form");
			customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			customerFrame.setResizable(false);
			customerFrame.setLocationRelativeTo(null);
		}
		
	}

}

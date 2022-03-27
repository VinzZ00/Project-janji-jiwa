import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import java.sql.Connection;

public class DbConnection {
	
	Connection connect;
	ResultSet rs;
	PreparedStatement ps;

	public DbConnection() {
		// TODO Auto-generated constructor stub
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/janjijiwa","root","");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet userloginValidation() {
		try {
			ps = connect.prepareStatement("Select * from user");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public String newUserId() {
		String Id = null;
		try {
			ps = connect.prepareStatement("SELECT UserId FROM `user` ORDER BY `user`.`UserId` ASC");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				int Idnumber = Integer.valueOf(rs.getObject(1).toString().substring(2));
				int newNum = Idnumber + 1;
				System.out.println(Idnumber);
				Id = String.format("US%03d", newNum);
			} else {
				Id = "US001";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Id;
	}
	
	public void insertnewUser(User user) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("INSERT INTO `user`(`UserId`, `UserName`, `UserEmail`, `UserPassword`, `UserGender`, `UserAddress`, `UserPhone`, `UserRole`) VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getUserEmail());
			ps.setString(4, user.getUserPassword());
			ps.setString(5, user.getUserGender());
			ps.setString(6, user.getUserAddress());
			ps.setString(7, user.getUserPhone());
			ps.setString(8, user.getUserRole());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Your account is successfully registered");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UpdateUser(User user) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("UPDATE `user` SET `UserName`=?,`UserEmail`=?,`UserPassword`=?,`UserGender`=?,`UserAddress`=?,`UserPhone`=?,`UserRole`=? WHERE UserId = ? ");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserEmail());
			ps.setString(3, user.getUserPassword());
			ps.setString(4, user.getUserGender());
			ps.setString(5, user.getUserAddress());
			ps.setString(6, user.getUserPhone());
			ps.setString(7, user.getUserRole());
			ps.setString(8, user.getUserId());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Your account is successfully updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changePassword(User user, String newPass) {
		try {
			ps = connect.prepareStatement("Update user set UserPassword = ? where UserId = ?");
			ps.setString(1, newPass);
			ps.setString(2, user.getUserId());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Your Password is successfully changed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getbaverageData() {
		
		try {
			ps = connect.prepareStatement("SELECT * FROM `baverages` ");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
				
	}

	public String getNewbaverageId() {
		// TODO Auto-generated method stub
		String Id = null;
		
		try {
			ps = connect.prepareStatement("SELECT BaveragesId FROM `baverages` ORDER BY `baverages`.`BaveragesId` ASC ");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				String LastId = rs.getObject(1).toString();
				int number = Integer.valueOf(LastId.substring(2)) + 1;
				Id= String.format("BE%03d", number);
			} else {
				Id = "BE001";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Id;
	}

	public void insertbaverage(Baverage baverage) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("INSERT INTO `baverages`(`BaveragesId`, `BaverageName`, `BaverageType`, `BaveragePrice`, `BaverageStock`) VALUES (?,?,?,?,?)");
			ps.setString(1, baverage.getBaverageId());
			ps.setString(2, baverage.getBaverageName());
			ps.setString(3, baverage.getBaverageType());
			ps.setString(4, String.valueOf(baverage.getBaveragePrice()));
			ps.setString(5, String.valueOf(baverage.getBaverageStock()));
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deletebaverage(String baverageId) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("DELETE FROM `baverages` WHERE BaveragesId = ?");
			ps.setString(1, baverageId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updatebaverage(Baverage baverage) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("UPDATE `baverages` SET `BaverageName`=?,`BaverageType`=?,`BaveragePrice`=?,`BaverageStock`=? WHERE BaveragesId = ?");
			ps.setString(1, baverage.getBaverageName());
			ps.setString(2, baverage.getBaverageType());
			ps.setString(3, String.valueOf(baverage.getBaveragePrice()));
			ps.setString(4, String.valueOf(baverage.getBaverageStock()));
			ps.setString(5, baverage.getBaverageId());
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updatebaverageStock(String baverageId, int stockTotal) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("UPDATE `baverages` SET `BaverageStock`= ? WHERE BaveragesId = ?");
			ps.setInt(1, stockTotal);
			ps.setString(2, baverageId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertHeaderTransaction(String transactionId, User user) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    Date date = (Date) Calendar.getInstance().getTime(); 
	    String dateString = formatter.format(date);
	    
	    
	    try {
			ps = connect.prepareStatement("INSERT INTO `headertransaction`(`TransactionId`, `UserId`, `TransationDate`) VALUES (?,?,?)");
			ps.setString(1, transactionId);
			ps.setString(2, user.getUserId());
			ps.setString(3, dateString);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertDetailTransaction(String transactionId, String baverageId, int quantity) {
		try {
			ps = connect.prepareStatement("INSERT INTO `detailtransaction`(`TransactionId`, `BaverageId`, `quantity`) VALUES (?,?,?)");
			ps.setString(1, transactionId);
			ps.setString(2, baverageId);
			ps.setInt(3, quantity);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getTransactionId() {
		String Id = null;
		try {
			ps = connect.prepareStatement("SELECT TransactionId FROM `headertransaction` ORDER BY `headertransaction`.`TransactionId` ASC ");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				int number = (Integer.valueOf(rs.getObject(1).toString().substring(2)) + 1);
				Id = String.format("TR%03d", number);
			} else {
				Id = "TR001";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Id;
	}

	public ResultSet getHeaderTransaction() {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("SELECT * FROM `headertransaction`");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}

	public ResultSet getDetailTransaction(String transactionID) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("SELECT dt.TransactionId, b.BaveragesId, b.BaverageName, b.BaverageType, b.BaveragePrice, dt.quantity FROM baverages b JOIN detailtransaction dt on b.BaveragesId = dt.BaverageId WHERE dt.TransactionId = ?");
			ps.setString(1, transactionID);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}


	
	

}

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class BuyBaverage extends JInternalFrame implements ActionListener{

	JLabel headerLabel, baverageId, baverageName, baverageType, baveragePrice, baverageStock, baverageQuantity;
	JTextField baverageIdField, baverageNameField, baverageTypeField, baveragePriceField, baverageStockField;
	JSpinner baverageQuantityField;
	JTable baverageTable, cartTable;
	JButton addCartButton, removeSelectedCartButton, clearCartButton, checkOut;
	JPanel headerPanel, bodyPanel, footerPanel;
	JScrollPane baverageTableScroll, cartTableScroll;
	DefaultTableModel baverageTableModel, cartTableModel;
	DbConnection db;
	Baverage baverageSelected;
	int indexSelected, cartIndexSelected;
	String TransactionId;
	User user;
	
	public BuyBaverage(User user, DbConnection db) {
		// TODO Auto-generated constructor stub
		super("Buy Baverage", false, false, false);
		this.db = db;
		this.user = user;
		
		TransactionId = db.getTransactionId();
		
//		TransactionId = db.getTransactionId();
		//header
		headerLabel = new JLabel("Buy baverage", SwingConstants.CENTER);
		
		headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		header();
		//body
		
		baverageId = new JLabel("baverage Id");
		baverageIdField = new JTextField();
		baverageIdField.setEnabled(false);
		baveragePrice = new JLabel("baverage Price");
		baveragePriceField = new JTextField();
		baveragePriceField.setEnabled(false);
		
		JPanel grid1 = new JPanel(new GridLayout(1,4,10,0));
		grid1.add(baverageId);
		grid1.add(baverageIdField);
		grid1.add(baveragePrice);
		grid1.add(baveragePriceField);
		
		
		baverageName = new JLabel("baverage Name");
		baverageNameField = new JTextField();
		baverageNameField.setEnabled(false);
		baverageStock = new JLabel("baverage Stock");
		baverageStockField = new JTextField();
		baverageStockField.setEnabled(false);
		
		JPanel grid2 = new JPanel(new GridLayout(1,4,10,0));
		grid2.add(baverageName);
		grid2.add(baverageNameField);
		grid2.add(baverageStock);
		grid2.add(baverageStockField);
		
		
		baverageType = new JLabel("baverage Type");
		baverageTypeField = new JTextField();
		baverageTypeField.setEnabled(false);
		baverageQuantity = new JLabel("baverage Quantity");
		baverageQuantityField = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		
		JPanel grid3 = new JPanel(new GridLayout(1,4,10,0));
		grid3.add(baverageType);
		grid3.add(baverageTypeField);
		grid3.add(baverageQuantity);
		grid3.add(baverageQuantityField);
		
		addCartButton = new JButton("Add to Cart");
		addCartButton.addActionListener(this);
		JPanel grid4 = new JPanel();
		grid4.add(addCartButton);
		
		

		bodyPanel = new JPanel(new GridLayout(4,1,0,10));
		bodyPanel.setBorder(new EmptyBorder(10,10,10,10));
		bodyPanel.add(grid1);
		bodyPanel.add(grid2);
		bodyPanel.add(grid3);
		bodyPanel.add(grid4);
		
		//footer
		cartTabelCreate();
		
		removeSelectedCartButton = new JButton("Remove Selected Cart");
		removeSelectedCartButton.addActionListener(this);
		clearCartButton = new JButton("Clear Cart");
		clearCartButton.addActionListener(this);
		checkOut = new JButton("Check Out");
		checkOut.addActionListener(this);
		
		JPanel buttonPane = new JPanel(new GridLayout(1,3,10,0));
		buttonPane.add(removeSelectedCartButton);
		buttonPane.add(clearCartButton);
		buttonPane.add(checkOut);
		
		
		footerPanel = new JPanel(new BorderLayout(0,10));
		footerPanel.setBorder(new EmptyBorder(10,10,10,10));
		footerPanel.add(cartTableScroll, BorderLayout.CENTER);
		footerPanel.add(buttonPane, BorderLayout.SOUTH);
		
		
		add(headerPanel);
		add(bodyPanel);
		add(footerPanel);

		//Frame Setting
		setVisible(true);
		setLayout(new GridLayout(3,1,0,10));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addCartButton) {

				if (Integer.valueOf(baverageQuantityField.getValue().toString()) != 0) {
					boolean duplicate = false;
					int indexduplicate = 0;
					for (int i = 0; i < cartTable.getRowCount(); i++) {
						if (baverageTableModel.getValueAt(indexSelected, 0).equals(cartTableModel.getValueAt(i, 0))) {
							duplicate = true;
							indexduplicate = i;
						}
					}
					if (!duplicate) {
						if (Integer.valueOf(baverageStockField.getText().toString()) >= Integer.valueOf(baverageQuantityField.getValue().toString())) {
							int subtotal = Integer.valueOf(baverageQuantityField.getValue().toString())
									* baverageSelected.getBaveragePrice();
							Object[] newRecord = { baverageSelected.getBaverageId(), baverageSelected.getBaverageName(),
									baverageSelected.getBaverageType(), baverageSelected.getBaveragePrice(), baverageSelected.getBaverageStock(),
									baverageQuantityField.getValue(), subtotal };

							int afterQuant = baverageSelected.getBaverageStock()
									- Integer.valueOf(baverageQuantityField.getValue().toString());

							cartTableModel.addRow(newRecord);
							baverageTableModel.setValueAt(afterQuant, indexSelected, 4);
							baverageStockField.setText(String.valueOf(afterQuant));
						} 
					} else {
						if (Integer.valueOf(baverageStockField.getText().toString()) >= Integer.valueOf(baverageQuantityField.getValue().toString())) {
							int quantcartBefore = Integer.valueOf(cartTableModel.getValueAt(indexduplicate, 5).toString());
							cartTableModel.setValueAt((quantcartBefore + Integer.valueOf(baverageQuantityField.getValue().toString())), indexduplicate, 5);
							int afterQuant = baverageSelected.getBaverageStock()
									- Integer.valueOf(cartTableModel.getValueAt(indexduplicate, 5).toString());
							baverageTableModel.setValueAt(afterQuant, indexSelected, 4);
							baverageStockField.setText(String.valueOf(afterQuant));
						}
					}
				}
		}
		
		else if (e.getSource() == removeSelectedCartButton) {
			int indexbaverage = 0;
			if (cartIndexSelected > -1) {
				for (int i = 0; i < baverageTableModel.getRowCount(); i++) {
					if (cartTableModel.getValueAt(cartIndexSelected, 0).equals(baverageTableModel.getValueAt(i, 0))) {
						indexbaverage = i;
						break;
					}
				}
				
				baverageTableModel.setValueAt(cartTableModel.getValueAt(cartIndexSelected, 4), indexbaverage, 4);
				baverageStockField.setText(cartTableModel.getValueAt(cartIndexSelected, 4).toString());
				cartTableModel.removeRow(cartIndexSelected);
				
				cartIndexSelected = -1;
			}
			
		}
		
		else if (e.getSource() == clearCartButton) {
			for (int i = 0; i < cartTableModel.getRowCount(); i++) {
				String id = cartTable.getValueAt(i, 0).toString();
				int quantBefore = Integer.valueOf(cartTable.getValueAt(i, 4).toString());
				
				for (int j = 0; j < baverageTableModel.getRowCount(); j++) {
					if (id.equals(baverageTableModel.getValueAt(j, 0))) {
						baverageTableModel.setValueAt(quantBefore, j, 4);
					}
				}
				
			}
			
			if (cartTableModel.getRowCount() != 0) {
				while (cartTable.getRowCount() != 0) {
					cartTableModel.removeRow(0);
				}
			}
		}
		
		else if (e.getSource() == checkOut) {
			db.insertHeaderTransaction(TransactionId, user);
			
			if (cartTableModel.getRowCount() != 0) {
				for (int i = 0; i < cartTableModel.getRowCount(); i++) {
					db.insertDetailTransaction(TransactionId, cartTable.getValueAt(i, 0).toString(), Integer.valueOf(cartTableModel.getValueAt(i, 5).toString()));
				}
				
				for (int i = 0; i < baverageTableModel.getRowCount(); i++) {
					try {
						db.updatebaverageStock(baverageTableModel.getValueAt(i, 0).toString(), Integer.valueOf(baverageTableModel.getValueAt(i, 4).toString()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				while (cartTable.getRowCount() != 0) {
					cartTableModel.removeRow(0);
				}
			}
			
			TransactionId = db.getTransactionId();
		}
	}
	
	private void header() {
		// TODO Auto-generated method stub
		ResultSet baverageTableData = db.getbaverageData();
		
		Vector<String> columnName = new Vector<String>();
		columnName.add("baverage Id");
		columnName.add("baverage Name");
		columnName.add("baverage Type");
		columnName.add("baverage Price");
		columnName.add("baverage Stock");
		
		Vector<Vector<Object>> dataContent = new Vector<Vector<Object>>();
		try {
			while (baverageTableData.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(baverageTableData.getObject(1));
				record.add(baverageTableData.getObject(2));
				record.add(baverageTableData.getObject(3));
				record.add(baverageTableData.getObject(4));
				record.add(baverageTableData.getObject(5));
				
				dataContent.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		baverageTableModel = new DefaultTableModel(dataContent, columnName);
		baverageTable = new JTable(baverageTableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		baverageTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (baverageTable.getSelectedRow() > -1) {
					// TODO Auto-generated method stub
					int index = baverageTable.getSelectedRow();
					indexSelected = index;
					baverageSelected = new Baverage(baverageTable.getValueAt(index, 0).toString(),
							baverageTable.getValueAt(index, 1).toString(), baverageTable.getValueAt(index, 2).toString(),
							Integer.valueOf(baverageTable.getValueAt(index, 3).toString()),
							Integer.valueOf(baverageTable.getValueAt(index, 4).toString()));
					baverageIdField.setText(baverageSelected.getBaverageId());
					baverageNameField.setText(baverageSelected.getBaverageName());
					baverageTypeField.setText(baverageSelected.getBaverageType());
					baveragePriceField.setText(String.valueOf(baverageSelected.getBaveragePrice()));
					baverageStockField.setText(String.valueOf(baverageSelected.getBaverageStock()));
				}
			}
		});
		baverageTable.getTableHeader().setReorderingAllowed(false);
		baverageTableScroll = new JScrollPane(baverageTable);
		baverageTableScroll.setVisible(true);
		headerPanel.add(headerLabel, BorderLayout.NORTH);
		headerPanel.add(baverageTableScroll, BorderLayout.CENTER);
	}
	
	private void refreshproduct() {
		// TODO Auto-generated method stub
		headerPanel.removeAll();
		header();
		headerPanel.repaint();
		headerPanel.revalidate();
	}
	
	private void cartTabelCreate() {
		
		Vector<Vector<Object>> cartContent = new Vector<Vector<Object>>();
		Vector<String> cartColumn = new Vector<String>();
		cartColumn.add("baverage Id");
		cartColumn.add("baverage name");
		cartColumn.add("baverage Type");
		cartColumn.add("baverage Price");
		cartColumn.add("baverage Stock");
		cartColumn.add("baverage Quantity");
		cartColumn.add("SubTotal");
		
		cartTableModel = new DefaultTableModel(cartContent, cartColumn);
		cartTable = new JTable(cartTableModel) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		cartTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				cartIndexSelected = cartTable.getSelectedRow();
				
				
			}
		});
		cartTable.getTableHeader().setReorderingAllowed(false);
		cartTableScroll = new JScrollPane(cartTable);
		setVisible(true);
	}

}

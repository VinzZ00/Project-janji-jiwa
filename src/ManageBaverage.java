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
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageBaverage extends JInternalFrame implements ActionListener{

	DbConnection db;
	JLabel headerLabel, newbaverageIdLabel, newbaverageNameLabel, newbaverageTypeLabel, newbaveragePriceLabel, newbaverageStockLabel, baverageIdLabel, baverageNameLabel, baverageTypeLabel, baveragePriceLabel, baverageStockLabel, addStockLabel;
	JTable baverageTable;
	JScrollPane baverageTableScrollPane;
	DefaultTableModel baveragetableModel;
	JTextField newbaverageIdField, newbaverageNameField, newbaveragePriceField, baverageIdField, baverageNameField, baveragePriceField, baverageStockField;
	JComboBox<String> newbaverageTypeCombo, baverageTypeCombo;
	JSpinner newbaverageStock, addstock;
	JButton insertbaverage, reset, updatebaverage, deletebaverage, addStock;
	JPanel header, body, footer, formsPanel;
	Vector<Vector<Object>> tableContent;
	int indexChoosed;
	
	public ManageBaverage(DbConnection db) {
		super("Manage baverage", false, false, false);
		System.out.println("Masuk");
		// TODO Auto-generated constructor stub
		this.db = db;
		
		String[] baveragetype = {"Boba", "Coffee", "Smoothies"};
		
		//header
		headerLabel = new JLabel("Manage Baverage");
		header = new JPanel();
		header.setBorder(new EmptyBorder(10,10,10,10));
		header.add(headerLabel);
		
		//body
				
				
				newbaverageIdLabel = new JLabel("New baverage Id");
				newbaverageNameLabel = new JLabel("New baverage Name");
				newbaverageTypeLabel = new JLabel("New baverage Type");
				newbaveragePriceLabel = new JLabel("New baverage Price");
				newbaverageStockLabel = new JLabel("New baverage Stock");
				
				
				newbaverageIdField = new JTextField();
				newbaverageIdField.setText(db.getNewbaverageId());
				newbaverageIdField.setEnabled(false);
				
				newbaverageNameField = new JTextField();
				newbaverageNameField = new JTextField();
				
				
				newbaverageTypeCombo = new JComboBox<>(baveragetype);
				
				newbaveragePriceField = new JTextField();
				
				newbaverageStock = new JSpinner(new SpinnerNumberModel(0,0,null,1));
				
				JPanel panelNewbaverage = new JPanel(new GridLayout(5,2,0,10));
				panelNewbaverage.add(newbaverageIdLabel);
				panelNewbaverage.add(newbaverageIdField);
				panelNewbaverage.add(newbaverageNameLabel);
				panelNewbaverage.add(newbaverageNameField);
				panelNewbaverage.add(newbaverageTypeLabel);
				panelNewbaverage.add(newbaverageTypeCombo);
				panelNewbaverage.add(newbaveragePriceLabel);
				panelNewbaverage.add(newbaveragePriceField);
				panelNewbaverage.add(newbaverageStockLabel);
				panelNewbaverage.add(newbaverageStock);
				
				baverageIdLabel = new JLabel("baverage Id");
				baverageNameLabel = new JLabel("baverage Name");
				baverageTypeLabel =  new JLabel ("baverage Type");
				baveragePriceLabel = new JLabel("baverage Price");
				baverageStockLabel = new JLabel("baverageStock");
				
				baverageIdField = new JTextField();
				baverageIdField.setEditable(false);
				baverageNameField = new JTextField();
				
				baverageTypeCombo = new JComboBox<String>(baveragetype);
				
				baveragePriceField = new JTextField();
				baverageStockField = new JTextField();
				baverageStockField.setEditable(false);
				
				JPanel panelbaverage = new JPanel(new GridLayout(5,2,0,10));
				panelbaverage.add(baverageIdLabel);
				panelbaverage.add(baverageIdField);
				panelbaverage.add(baverageNameLabel);
				panelbaverage.add(baverageNameField);
				panelbaverage.add(baverageTypeLabel);
				panelbaverage.add(baverageTypeCombo);
				panelbaverage.add(baveragePriceLabel);
				panelbaverage.add(baveragePriceField);
				panelbaverage.add(baverageStockLabel);
				panelbaverage.add(baverageStockField);
				
				formsPanel = new JPanel(new GridLayout(1,2,10,0));
				formsPanel.add(panelNewbaverage);
				formsPanel.add(panelbaverage);
				
				
				body = new JPanel(new GridLayout(2,1,0,10));
				body.setBorder(new EmptyBorder(10,10,10,10));
				createTable(db.getbaverageData());
				body.add(formsPanel);
		
				//footer
				insertbaverage = new JButton("Insert baverage");
				insertbaverage.addActionListener(this);
				reset = new JButton("Reset");
				reset.addActionListener(this);
				
				JPanel buttonPaneLeft = new JPanel(new GridLayout(2,1,0,10));
				buttonPaneLeft.add(insertbaverage);
				buttonPaneLeft.add(reset);
				
				JPanel buttonPanelRight = new JPanel(new GridLayout(2,1,0,10));
				
				updatebaverage = new JButton("Update baverage");
				updatebaverage.addActionListener(this);
				deletebaverage = new JButton("Delete baverage");
				deletebaverage.addActionListener(this);
				
				addStockLabel = new JLabel("Add Stock");
				addstock = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
				addStock = new JButton("Add Stock");
				addStock.addActionListener(this);
				
				JPanel rightUpper = new JPanel(new GridLayout(1,2,5,0));
				rightUpper.add(updatebaverage);
				rightUpper.add(deletebaverage);
				
				JPanel rightLower = new JPanel(new GridLayout(1,3,5,0));
				rightLower.add(addStockLabel);
				rightLower.add(addstock);
				rightLower.add(addStock);

				buttonPanelRight.add(rightUpper);
				buttonPanelRight.add(rightLower);
				
				
				footer = new JPanel(new GridLayout(1,2,10,0));
				footer.setBorder(new EmptyBorder(10,10,10,10));
				footer.add(buttonPaneLeft);
				footer.add(buttonPanelRight);
				
				//Frame Setting
				add(header, BorderLayout.NORTH);
				add(body, BorderLayout.CENTER);
				add(footer, BorderLayout.SOUTH);
				
				setVisible(true);	
	}
	
	private void createTable(ResultSet baveragedata) {
		// TODO Auto-generated method stub

		Vector<Vector<Object>> tableContent = new Vector<Vector<Object>>();
		
		Vector<String> columnName = new Vector<String>();
		columnName.add("baverage Id");
		columnName.add("baverage Name");
		columnName.add("baverage Type");
		columnName.add("baverage Price");
		columnName.add("baverage Stock");
		
		
		try {
			while (baveragedata.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(baveragedata.getObject(1));
				record.add(baveragedata.getObject(2));
				record.add(baveragedata.getObject(3));
				record.add(baveragedata.getObject(4));
				record.add(baveragedata.getObject(5));
				
				tableContent.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		baveragetableModel = new DefaultTableModel(tableContent, columnName);
		baverageTable = new JTable(baveragetableModel) {
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
				// TODO Auto-generated method stub
				if (baverageTable.getSelectedRow() != -1) {
					int ind = baverageTable.getSelectedRow();
					baverageIdField.setText(baverageTable.getValueAt(ind, 0).toString());
					baverageNameField.setText(baverageTable.getValueAt(ind, 1).toString());
					baverageTypeCombo.setSelectedItem(baverageTable.getValueAt(ind, 2));
					baveragePriceField.setText(baverageTable.getValueAt(ind, 3).toString());
					baverageStockField.setText(baveragetableModel.getValueAt(ind, 4).toString());
					
					indexChoosed = ind;
				}
			}
		});
		baverageTable.getTableHeader().setReorderingAllowed(false);
		baverageTableScrollPane = new JScrollPane(baverageTable);
		baverageTableScrollPane.setVisible(true);
		body.add(baverageTableScrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == reset) {
			newbaverageNameField.setText("");
			newbaverageTypeCombo.setSelectedIndex(0);
			newbaveragePriceField.setText("");
			newbaverageStock.setValue(0);
		}
		
		if (e.getSource() == insertbaverage) {
			if (!(newbaverageNameField.getText().isEmpty() || newbaveragePriceField.getText().isEmpty())) {
				if (validatebaverage()) {
					Baverage baverage = new Baverage(newbaverageIdField.getText(), newbaverageNameField.getText(), newbaverageTypeCombo.getSelectedItem().toString(), Integer.valueOf(newbaveragePriceField.getText()), Integer.valueOf(newbaverageStock.getValue().toString()));
					db.insertbaverage(baverage);
					refresh();
					newbaverageIdField.setText(db.getNewbaverageId());
				} 
			}
		}
		
		if (e.getSource() == deletebaverage) {
			
			int confirmDial = JOptionPane.showConfirmDialog(this, "Are you sure you wanna delete this baverage?", "Confirmation", JOptionPane.YES_NO_OPTION);
			
			boolean confirm = (confirmDial == JOptionPane.YES_OPTION) ? true : false;
			
			if (confirm) {
				String baverageId = baverageIdField.getText();
				db.deletebaverage(baverageId);
				refresh();
			}
		}
		
		if (e.getSource() == updatebaverage) {
			
			
			int confirmDial = JOptionPane.showConfirmDialog(this, "Are you sure you wanna update this baverage?", "Confirmation", JOptionPane.YES_NO_OPTION);
			
			boolean confirm = (confirmDial == JOptionPane.YES_OPTION) ? true : false;
			
			
			if (confirm) {
				Baverage baverage = new Baverage(baverageIdField.getText(), baverageNameField.getText(),
						baverageTypeCombo.getSelectedItem().toString(),
						Integer.valueOf(baveragePriceField.getText().toString()),
						Integer.valueOf(baverageStockField.getText().toString()));
				db.updatebaverage(baverage);
				refresh();
			}
		}
		
		if (e.getSource() == addStock) {
			System.out.println("masuk");
			String baverageId = baverageIdField.getText();
			int stockBefore = Integer.valueOf(baverageTable.getValueAt(indexChoosed, 4).toString());
			int stockTotal = (Integer.valueOf(addstock.getValue().toString()) + stockBefore);  
			
			db.updatebaverageStock(baverageId, stockTotal);
			baverageTable.setValueAt(stockTotal, indexChoosed, 4);
			refresh();
		}
	}
	
	public void refresh() {
		body.removeAll();
		createTable(db.getbaverageData());
		body.add(formsPanel);
		this.repaint();
		this.revalidate();
	}
	
	private boolean validatebaverage() {
		// TODO Auto-generated method stub
		boolean valid = false;
		if (newbaverageNameField.getText().length() >= 5 && baverageNameField.getText().length() <= 30) {
			if (Integer.valueOf(newbaveragePriceField.getText().toString()) > 0) {
				valid = true;
			}
		}
		
		return valid;
		
	}
}

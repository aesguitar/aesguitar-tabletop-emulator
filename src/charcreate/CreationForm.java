package charcreate;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Race;
import util.RaceList;
import util.UF;

import java.awt.Font;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class CreationForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private RaceList rl;
	private JTable statsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RaceList rlist = new RaceList(new File("race-list.txt"));
			rlist.buildList();
			CreationForm dialog = new CreationForm(rlist);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CreationForm(RaceList rl) {
		this.rl = rl;
		setBounds(100, 100, 922, 618);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblName, 10, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, contentPanel);
		contentPanel.add(lblName);
		
		nameField = new JTextField();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, nameField, -3, SpringLayout.NORTH, lblName);
		sl_contentPanel.putConstraint(SpringLayout.WEST, nameField, 6, SpringLayout.EAST, lblName);
		sl_contentPanel.putConstraint(SpringLayout.EAST, nameField, 166, SpringLayout.EAST, lblName);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblRace = new JLabel("Race: ");
		lblRace.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblRace, 0, SpringLayout.NORTH, lblName);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblRace, 39, SpringLayout.EAST, nameField);
		contentPanel.add(lblRace);
		
		JComboBox comboBox = new JComboBox(raceListToVector());
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox, -3, SpringLayout.NORTH, lblName);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, lblRace);
		sl_contentPanel.putConstraint(SpringLayout.EAST, comboBox, 159, SpringLayout.EAST, lblRace);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox);
		
		JLabel lblStats = new JLabel("Stats:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblStats, 25, SpringLayout.SOUTH, lblName);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblStats, 0, SpringLayout.EAST, lblName);
		lblStats.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblStats);
		
		statsTable = new JTable();
		statsTable.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		sl_contentPanel.putConstraint(SpringLayout.NORTH, statsTable, -2, SpringLayout.NORTH, lblStats);
		sl_contentPanel.putConstraint(SpringLayout.WEST, statsTable, 0, SpringLayout.WEST, nameField);
		statsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		statsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		statsTable.getColumnModel().getColumn(0).setPreferredWidth(95);
		statsTable.getColumnModel().getColumn(0).setMinWidth(95);
		statsTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		statsTable.getColumnModel().getColumn(1).setMaxWidth(35);
		setStatsNameColumn();
		statsTable.setRowHeight(25);
		contentPanel.add(statsTable);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(240, 248, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private Vector<String> raceListToVector()
	{
		Vector<String> v = new Vector<String>();
		Iterator<Race> i = rl.getRaceList().iterator();
		while(i.hasNext())
			v.addElement(i.next().getName());
		
		return v;
	}
	
	private void setStatsNameColumn()
	{
		for(int i = 0; i < 6; i++)
		{
			statsTable.getModel().setValueAt(UF.statsList[i], i, 0);
		}
	}
}

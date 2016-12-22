package charcreate;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.Dice;
import util.UF;

import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class SelectStats extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4562647051307121962L;
	private final JPanel contentPanel = new JPanel();
	private JTable statsTable;
	private int[] rolls = new int[6];
	private JComboBox comboBox, comboBox_1, comboBox_2, comboBox_3,comboBox_4,comboBox_5;
	private String boxesError = "Stat selections must be unique.", tableError = "You must roll the stats.";
	private boolean rolled = false;
	private int[] charStats = new int[6];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SelectStats dialog = new SelectStats();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	/**
	 * Create the dialog.
	 */
	public SelectStats() {
		setTitle("Roll Stats");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SelectStats.class.getResource("/icons/dice1.png")));
		setAlwaysOnTop(true);
		setBounds(100, 100, 300, 327);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		statsTable = new JTable();
		statsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		statsTable.setRowHeight(31);
		statsTable.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		statsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"New column"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645591958885246416L;
			Class[] columnTypes = new Class[] {
				Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		sl_contentPanel.putConstraint(SpringLayout.NORTH, statsTable, 10, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, statsTable, 10, SpringLayout.WEST, contentPanel);
		contentPanel.add(statsTable);

		comboBox = new JComboBox(UF.attrList);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox, 3, SpringLayout.NORTH, statsTable);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, statsTable);
		sl_contentPanel.putConstraint(SpringLayout.EAST, comboBox, 156, SpringLayout.EAST, statsTable);
		comboBox.setPreferredSize(new Dimension(150, 25));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox);

		comboBox_1 = new JComboBox(UF.attrList);
		comboBox_1.setSelectedIndex(1);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox_1, 6, SpringLayout.SOUTH, comboBox);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox_1, 6, SpringLayout.EAST, statsTable);
		comboBox_1.setPreferredSize(new Dimension(150, 25));
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox_1);

		comboBox_2 = new JComboBox(UF.attrList);
		comboBox_2.setSelectedIndex(2);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox_2, 6, SpringLayout.SOUTH, comboBox_1);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox_2, 6, SpringLayout.EAST, statsTable);
		comboBox_2.setPreferredSize(new Dimension(150, 25));
		comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox_2);

		comboBox_3 = new JComboBox(UF.attrList);
		comboBox_3.setSelectedIndex(3);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox_3, 6, SpringLayout.SOUTH, comboBox_2);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox_3, 6, SpringLayout.EAST, statsTable);
		comboBox_3.setPreferredSize(new Dimension(150, 25));
		comboBox_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox_3);

		comboBox_4 = new JComboBox(UF.attrList);
		comboBox_4.setSelectedIndex(4);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox_4, 6, SpringLayout.SOUTH, comboBox_3);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox_4, 6, SpringLayout.EAST, statsTable);
		comboBox_4.setPreferredSize(new Dimension(150, 25));
		comboBox_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox_4);

		comboBox_5 = new JComboBox(UF.attrList);
		comboBox_5.setSelectedIndex(5);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox_5, 6, SpringLayout.SOUTH, comboBox_4);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox_5, 6, SpringLayout.EAST, statsTable);
		comboBox_5.setPreferredSize(new Dimension(150, 25));
		comboBox_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox_5);

		JButton btnReroll = new JButton("Roll");
		btnReroll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rollStats();
				rolled = true;
			}
		});
		sl_contentPanel.putConstraint(SpringLayout.NORTH, btnReroll, 6, SpringLayout.SOUTH, statsTable);
		sl_contentPanel.putConstraint(SpringLayout.WEST, btnReroll, 0, SpringLayout.WEST, statsTable);
		sl_contentPanel.putConstraint(SpringLayout.EAST, btnReroll, 0, SpringLayout.EAST, statsTable);
		btnReroll.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(btnReroll);
		
		JLabel lblError = new JLabel("");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblError, 8, SpringLayout.NORTH, btnReroll);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblError, -3, SpringLayout.WEST, comboBox);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblError, 0, SpringLayout.EAST, contentPanel);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblError.setVisible(false);
		contentPanel.add(lblError);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(!rolled)
						{
							lblError.setText(tableError);
							lblError.setVisible(true);
							//System.out.println("Error: table");
						}
						else if(checkComboBoxes())
						{
							lblError.setText(boxesError);
							lblError.setVisible(true);
							//System.out.println("Error: combo boxes.");
						}
						else
						{
							setVisible(false);
							setStats();
							//System.out.println("Done.");
						}
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						dispose();
						System.out.println("Cancelled.");
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void rollStats()
	{
		for(int i = 0; i < 6; i++)
		{
			rolls[i] = Dice.rollSum(4, 6, 0, 3);
			statsTable.getModel().setValueAt(rolls[i], i, 0);
		}
	}

	private boolean checkComboBoxes()
	{
		int[] selectedIndices = {comboBox.getSelectedIndex(),comboBox_1.getSelectedIndex(),comboBox_2.getSelectedIndex(),comboBox_3.getSelectedIndex(),
				comboBox_4.getSelectedIndex(), comboBox_5.getSelectedIndex()};
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = i+1; j < 6; j++)
			{
				if(selectedIndices[i] == selectedIndices[j])
					return true;
			}
		}
		
		return false;	
	}
	
	private void setStats()
	{
		charStats[comboBox.getSelectedIndex()] = (int) statsTable.getValueAt(0, 0); 
		charStats[comboBox_1.getSelectedIndex()] = (int) statsTable.getValueAt(1, 0);
		charStats[comboBox_2.getSelectedIndex()] = (int) statsTable.getValueAt(2, 0);
		charStats[comboBox_3.getSelectedIndex()] = (int) statsTable.getValueAt(3, 0);
		charStats[comboBox_4.getSelectedIndex()] = (int) statsTable.getValueAt(4, 0);
		charStats[comboBox_5.getSelectedIndex()] = (int) statsTable.getValueAt(5, 0);
	}
	
	public int[] getStats()
	{
		return charStats;
	}
	
}

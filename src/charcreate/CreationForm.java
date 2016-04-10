package charcreate;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Class;
import util.ClassList;
import util.Dice;
import util.Race;
import util.RaceList;
import util.UF;

import java.awt.Font;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CreationForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1185464119248273518L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private RaceList rl;
	private ClassList cl;
	private JTable statsTable;
	private JTable bonusTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RaceList rlist = new RaceList(new File("race-list.txt"));
			ClassList clist = new ClassList(new File("class-list.txt"));
			rlist.buildList();
			clist.buildList();
			//System.out.println(clist.get(1).getName());
			CreationForm dialog = new CreationForm(rlist,clist);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CreationForm(RaceList rl, ClassList cl) {
		setTitle("Character Creation");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreationForm.class.getResource("/icons/dnd icon1.png")));
		this.rl = rl;
		this.cl = cl;
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
		nameField.setPreferredSize(new Dimension(300,30));
		
		bonusTable = new JTable();
		sl_contentPanel.putConstraint(SpringLayout.WEST, bonusTable, 539, SpringLayout.WEST, contentPanel);
		bonusTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		bonusTable.setModel(new DefaultTableModel(
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
		));
		bonusTable.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		bonusTable.getColumnModel().getColumn(0).setPreferredWidth(95);
		bonusTable.getColumnModel().getColumn(0).setMinWidth(95);
		bonusTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		bonusTable.getColumnModel().getColumn(1).setMaxWidth(35);
		bonusTable.setRowHeight(30);
		contentPanel.add(bonusTable);
		setStatsNameColumn(bonusTable);
		
		JLabel lblRace = new JLabel("Race:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblRace, 0, SpringLayout.NORTH, lblName);
		lblRace.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblRace);
		
		JComboBox raceBox = new JComboBox(raceListToVector());
		raceBox.setSelectedIndex(0);
		raceBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				setBonuses(rl.get(raceBox.getSelectedIndex()+1));
			}
		});

		sl_contentPanel.putConstraint(SpringLayout.WEST, raceBox, 539, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, raceBox, -204, SpringLayout.EAST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblRace, -6, SpringLayout.WEST, raceBox);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, raceBox, -3, SpringLayout.NORTH, lblName);
		raceBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(raceBox);
		
		JLabel lblStats = new JLabel("Stats:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, bonusTable, -4, SpringLayout.NORTH, lblStats);
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
			/**
			 * 
			 */
			private static final long serialVersionUID = -4777683669536303407L;
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		statsTable.getColumnModel().getColumn(0).setPreferredWidth(95);
		statsTable.getColumnModel().getColumn(0).setMinWidth(95);
		statsTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		statsTable.getColumnModel().getColumn(1).setMaxWidth(35);
		statsTable.setRowHeight(30);
		contentPanel.add(statsTable);
		
		JButton btnRoll = new JButton("Roll Stats");
		sl_contentPanel.putConstraint(SpringLayout.WEST, btnRoll, 9, SpringLayout.EAST, statsTable);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, btnRoll, 1, SpringLayout.NORTH, statsTable);
		btnRoll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rollStats();
			}
		});
		btnRoll.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(btnRoll);
		
		JLabel lblClass = new JLabel("Class:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblClass, 0, SpringLayout.NORTH, lblName);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblClass, 0, SpringLayout.EAST, btnRoll);
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblClass);
		
		JComboBox classBox = new JComboBox(classListToVector());
		sl_contentPanel.putConstraint(SpringLayout.WEST, classBox, 307, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, classBox, 2, SpringLayout.NORTH, nameField);
		sl_contentPanel.putConstraint(SpringLayout.EAST, classBox, -459, SpringLayout.EAST, contentPanel);
		classBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(classBox);
		
		JLabel lblBonuses = new JLabel("Bonuses:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblBonuses, 0, SpringLayout.NORTH, lblStats);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblBonuses, -6, SpringLayout.WEST, bonusTable);
		lblBonuses.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblBonuses);
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
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setStatsNameColumn(statsTable);
		setInitialStats(statsTable);
		//System.out.println(raceBox.getSelectedIndex());
		//rl.get(raceBox.getSelectedIndex()).printRace();
		setBonuses(rl.get(raceBox.getSelectedIndex()+1));
		setModal(true);
	}
	
	private Vector<String> raceListToVector()
	{
		Vector<String> v = new Vector<String>();
		Iterator<Race> i = rl.getRaceList().iterator();
		while(i.hasNext())
			v.addElement(i.next().getName());
		
		return v;
	}
	private Vector<String> classListToVector()
	{
		Vector<String> v = new Vector<String>();
		Iterator<Class> i = cl.getClassList().iterator();
		while(i.hasNext()){
			v.addElement(i.next().getName());
		}
		return v;
	}
	
	private void setStatsNameColumn(JTable table)
	{
		for(int i = 0; i < 6; i++)
		{
			table.getModel().setValueAt(UF.statsList[i], i, 0);
		}
	}
	
	private void setInitialStats(JTable table)
	{
		for(int i = 0; i < 6; i++)
		{
			table.getModel().setValueAt(0, i, 1);
		}
	}
	
	private void rollStats()
	{
		for(int i = 0; i < 6; i++)
		{
			statsTable.getModel().setValueAt(Dice.rollSum(4, 6, 0, 3), i, 1);
		}
	}
	private void setBonuses(Race r)
	{
		//r.printRace();
		int[] bonuses = r.getBonus();
		for(int i = 0; i < 6; i++)
		{
			bonusTable.getModel().setValueAt(Integer.toString(bonuses[i]), i, 1);
		}
	}
}

package charcreate;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Dice;
import util.UF;

import java.awt.Font;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import main.Class;
import main.ClassList;
import main.IdConflictException;
import main.Race;
import main.RaceList;

import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Window.Type;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CreationForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1185464119248273518L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private RaceList rl;
	private ClassList cl;
	private JTable tblScores;
	private JTable bonusTable;
	private JTextField heightField;
	private JTextField weightField;
	@SuppressWarnings("rawtypes")
	private JComboBox classBox;
	@SuppressWarnings("rawtypes")
	private JComboBox raceBox;
	private int[] charStats = new int[6];
	private JLabel lblError;
	private boolean halfelf = false;

	private boolean done = false;
	private boolean nameDone = false;
	private boolean weightDone = false;
	private boolean weightFormat = false;
	private boolean heightDone = false;
	private boolean heightFormat = false;


	public int[] finalStats = new int[6];
	public String finalName = "";
	public float finalWeight = -1;
	public float finalHeight = -1;
	public Race finalRace = null;
	public Class finalClass = null;
	public boolean cancel = false;
	public boolean rolled = false;
	private JTable tblStats;
	
	private int constitution = 0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				RaceList rlist = new RaceList(new File("race-list.txt"));
				ClassList clist = new ClassList(new File("class-list.txt"));
				try {
					rlist.buildList();
				} catch (IdConflictException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					clist.buildList();
				} catch (IdConflictException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CreationForm dialog = new CreationForm(rlist,clist);
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				System.exit(0);
			}
		});


	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CreationForm(RaceList rl, ClassList cl) {
		setType(Type.POPUP);
		setTitle("Character Creation");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreationForm.class.getResource("/icons/dnd icon1.png")));
		this.rl = rl;
		this.cl = cl;
		setBounds(100, 100, 752, 572);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		lblError = new JLabel("");
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblError, 2, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblError, -10, SpringLayout.SOUTH, contentPanel);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblError);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblName, 10, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, contentPanel);
		contentPanel.add(lblName);

		nameField = new JTextField();
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				nameDone = !(nameField.getText().trim().equalsIgnoreCase(""));
				isDone();
			}
		});
		sl_contentPanel.putConstraint(SpringLayout.NORTH, nameField, -3, SpringLayout.NORTH, lblName);
		sl_contentPanel.putConstraint(SpringLayout.WEST, nameField, 6, SpringLayout.EAST, lblName);
		sl_contentPanel.putConstraint(SpringLayout.EAST, nameField, 166, SpringLayout.EAST, lblName);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(nameField);
		nameField.setColumns(10);
		nameField.setPreferredSize(new Dimension(300,30));

		bonusTable = new JTable();
		bonusTable.setRowSelectionAllowed(false);
		sl_contentPanel.putConstraint(SpringLayout.WEST, bonusTable, 539, SpringLayout.WEST, contentPanel);
		bonusTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		bonusTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
				},
				new String[] {
						"New column", "New column", "New column"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4777683669536303407L;
			boolean[] columnEditables = new boolean[] {
					false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bonusTable.getColumnModel().getColumn(0).setPreferredWidth(95);
		bonusTable.getColumnModel().getColumn(0).setMinWidth(95);
		bonusTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		bonusTable.getColumnModel().getColumn(1).setMaxWidth(35);
		bonusTable.getColumnModel().getColumn(2).setPreferredWidth(35);
		bonusTable.getColumnModel().getColumn(2).setMaxWidth(35);
		bonusTable.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		bonusTable.setRowHeight(30);
		contentPanel.add(bonusTable);
		setStatsNameColumn(bonusTable);

		JLabel lblRace = new JLabel("Race:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblRace, 0, SpringLayout.NORTH, lblName);
		lblRace.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblRace);

		raceBox = new JComboBox(raceListToVector());
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblRace, -53, SpringLayout.WEST, raceBox);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblRace, -6, SpringLayout.WEST, raceBox);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, bonusTable, 16, SpringLayout.SOUTH, raceBox);
		sl_contentPanel.putConstraint(SpringLayout.EAST, raceBox, 153, SpringLayout.WEST, bonusTable);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, raceBox, 2, SpringLayout.NORTH, nameField);
		sl_contentPanel.putConstraint(SpringLayout.WEST, raceBox, 0, SpringLayout.WEST, bonusTable);
		raceBox.setSelectedIndex(0);
		raceBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				setBonuses(rl.get(raceBox.getSelectedIndex()+1));
				if(rl.get(raceBox.getSelectedIndex()+1).getName().equalsIgnoreCase("half-elf") && !halfelf)
				{
					JOptionPane.showMessageDialog(null, "Half-elves get +1 to 2 stats of your choice.");
					HalfElfChoice hec = new HalfElfChoice();
					hec.setVisible(true);
					rl.get(raceBox.getSelectedIndex()+1).setBonuses(hec.getBonus());
					halfelf = true;
				}
				else
					halfelf = false;
				updateHP();
			}
		});
		raceBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(raceBox);

		JLabel lblScores = new JLabel("Ability Scores:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblScores, 4, SpringLayout.NORTH, bonusTable);
		//sl_contentPanel.putConstraint(SpringLayout.EAST, lblStats, -595, SpringLayout.EAST, contentPanel);
		lblScores.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblScores);

		tblScores = new JTable();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, tblScores, 0, SpringLayout.NORTH, bonusTable);
		sl_contentPanel.putConstraint(SpringLayout.WEST, tblScores, 6, SpringLayout.EAST, lblScores);
		//sl_contentPanel.putConstraint(SpringLayout.EAST, statsTable, 136, SpringLayout.EAST, lblStats);
		tblScores.setRowSelectionAllowed(false);
		tblScores.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		tblScores.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblScores.setModel(new DefaultTableModel(
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
			private static final long serialVersionUID = 1114808588180367001L;
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblScores.getColumnModel().getColumn(0).setPreferredWidth(95);
		tblScores.getColumnModel().getColumn(0).setMinWidth(95);
		tblScores.getColumnModel().getColumn(1).setPreferredWidth(35);
		tblScores.getColumnModel().getColumn(1).setMaxWidth(35);
		tblScores.setRowHeight(30);
		contentPanel.add(tblScores);

		JButton btnRoll = new JButton("Roll Stats");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, btnRoll, 6, SpringLayout.SOUTH, tblScores);
		sl_contentPanel.putConstraint(SpringLayout.WEST, btnRoll, 307, SpringLayout.WEST, contentPanel);
		btnRoll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rollStats();
				isDone();
				setBonuses(rl.get(raceBox.getSelectedIndex()+1));
				updateHP();
			}
		});
		btnRoll.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(btnRoll);

		JLabel lblClass = new JLabel("Class:");
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblScores, 0, SpringLayout.EAST, lblClass);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblClass, 0, SpringLayout.NORTH, lblName);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblClass, 36, SpringLayout.EAST, nameField);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblClass, 78, SpringLayout.EAST, nameField);
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblClass);

		classBox = new JComboBox(classListToVector());
		classBox.setSelectedIndex(0);
		classBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				tblStats.getModel().setValueAt(cl.get(classBox.getSelectedIndex()+1).getDie().toString(), 1, 2);
				updateHP();
			}
		});
		sl_contentPanel.putConstraint(SpringLayout.NORTH, classBox, 2, SpringLayout.NORTH, nameField);
		sl_contentPanel.putConstraint(SpringLayout.WEST, classBox, 6, SpringLayout.EAST, lblClass);
		sl_contentPanel.putConstraint(SpringLayout.EAST, classBox, 0, SpringLayout.EAST, tblScores);
		classBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(classBox);

		JLabel lblBonuses = new JLabel("Bonuses:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblBonuses, 4, SpringLayout.NORTH, bonusTable);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblBonuses, 108, SpringLayout.EAST, tblScores);
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
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if(done)
						{
							setData();
							setVisible(false);
						}
						else
						{
							checkError();
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
						cancel = true;
						finalRace = Race.defaultRace();
						finalClass = Class.defaultClass();
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setStatsNameColumn(tblScores);
		setInitialStats(tblScores);

		JLabel lblHeight = new JLabel("Height:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblHeight, 4, SpringLayout.NORTH, bonusTable);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblHeight, 5, SpringLayout.WEST, contentPanel);
		//sl_contentPanel.putConstraint(SpringLayout.EAST, lblHeight, 57, SpringLayout.WEST, contentPanel);
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblHeight);


		DecimalFormat df = new DecimalFormat();
		NumberFormatter ndff = new NumberFormatter(df);
		DefaultFormatterFactory fac = new DefaultFormatterFactory(ndff);
		heightField = new JFormattedTextField(fac);
		sl_contentPanel.putConstraint(SpringLayout.EAST, heightField, 80, SpringLayout.EAST, lblHeight);
		sl_contentPanel.putConstraint(SpringLayout.WEST, heightField, 6, SpringLayout.EAST, lblHeight);
		//sl_contentPanel.putConstraint(SpringLayout.EAST, heightField, -759, SpringLayout.EAST, contentPanel);
		heightField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				//System.out.println("Height field focus lost.");
				heightDone = !heightField.getText().trim().equalsIgnoreCase("");

				if(UF.isInt(heightField.getText().trim()))
					heightField.setText(heightField.getText().trim().concat(".00"));

				//System.out.println(heightField.getText());
				heightFormat = UF.isFloat(heightField.getText().trim());
				isDone();
				repaint();
			}

		});

		heightField.setMinimumSize(new Dimension(6, 30));
		sl_contentPanel.putConstraint(SpringLayout.NORTH, heightField, 1, SpringLayout.NORTH, bonusTable);
		heightField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(heightField);
		heightField.setColumns(10);

		JLabel lblWeight = new JLabel("Weight:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblWeight, 19, SpringLayout.SOUTH, lblHeight);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblWeight, 2, SpringLayout.WEST, contentPanel);
		//sl_contentPanel.putConstraint(SpringLayout.EAST, lblWeight, 57, SpringLayout.WEST, contentPanel);
		lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblWeight);

		weightField = new JFormattedTextField(fac);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, weightField, 8, SpringLayout.SOUTH, heightField);
		sl_contentPanel.putConstraint(SpringLayout.WEST, weightField, 6, SpringLayout.EAST, lblWeight);
		sl_contentPanel.putConstraint(SpringLayout.EAST, weightField, 80, SpringLayout.EAST, lblWeight);
		weightField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				weightDone = !weightField.getText().trim().equalsIgnoreCase("");

				if(UF.isInt(weightField.getText().trim()))
					weightField.setText(weightField.getText().concat(".00"));

				weightFormat = UF.isFloat(weightField.getText().trim());
				isDone();
				//checkError();
				refresh();
			}
		});
		weightField.setMinimumSize(new Dimension(6, 30));
		weightField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(weightField);
		weightField.setColumns(10);
		
		JLabel lblStats = new JLabel("Stats:");
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblStats, 0, SpringLayout.EAST, lblName);
		lblStats.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblStats);
		
		tblStats = new JTable();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblStats, 4, SpringLayout.NORTH, tblStats);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, tblStats, 161, SpringLayout.SOUTH, weightField);
		sl_contentPanel.putConstraint(SpringLayout.WEST, tblStats, 0, SpringLayout.WEST, nameField);
		tblStats.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		tblStats.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblStats.setModel(new DefaultTableModel(
			new Object[][] {
				{"Level", "Hitpoints", "Hit Dice"},
				{null, null, null},
			},
			new String[] {
				"Level", "Hitpoints", "Hit Dice"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8474853470627396222L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblStats.setRowHeight(30);
		contentPanel.add(tblStats);

		//System.out.println(raceBox.getSelectedIndex());
		//rl.get(raceBox.getSelectedIndex()).printRace();
		setBonuses(rl.get(raceBox.getSelectedIndex()+1));
		setInitStats(cl.get(classBox.getSelectedIndex()+1));
		setModal(true);
	}
	private void setInitStats(Class class1) {
		tblStats.getModel().setValueAt(1, 1, 0);
		tblStats.getModel().setValueAt(class1.getDie().getNumSides(), 1, 1);
		tblStats.getModel().setValueAt(class1.getDie().toString(), 1, 2);
	}

	private boolean isDone()
	{
		if(!done)
			done = nameDone && weightDone && weightFormat && heightDone && heightFormat && rolled;

		return done;
	}

	private void checkError() {
		// TODO Auto-generated method stub
		String errorMessage = "";
		if(done)
		{
			lblError.setText("");
			nameField.setBackground(Color.white);
			weightField.setBackground(Color.white);
			heightField.setBackground(Color.white);
		}
		else if(!done)
		{
			errorMessage = errorMessage.concat("Form incomplete. ");
			if(!nameDone)
				nameField.setBackground(UF.lightCoral);
			else
				nameField.setBackground(Color.white);

			if(!weightDone)
				weightField.setBackground(UF.lightCoral);

			if(!heightDone)
				heightField.setBackground(UF.lightCoral);

		}

		if(!weightFormat)
		{
			errorMessage = errorMessage.concat("Weight must be a decimal. ");
			weightField.setBackground(UF.lightCoral);
		}
		else
		{
			weightField.setBackground(Color.white);
		}

		if(!heightFormat)
		{
			errorMessage = errorMessage.concat("Height must be a decimal. ");
			heightField.setBackground(UF.lightCoral);
		}
		else
		{
			heightField.setBackground(Color.white);
		}

		if(!rolled)
		{
			errorMessage = errorMessage.concat("Roll your stats.");
			tblScores.setBorder(new LineBorder(Color.red));
		}
		else
		{
			tblScores.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		}

		lblError.setText(errorMessage);

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
		SelectStats ss = new SelectStats();
		ss.setVisible(true);
		charStats = ss.getStats();
		for(int i = 0; i < 6; i++)
		{
			tblScores.getModel().setValueAt(charStats[i], i, 1);
		}
		rolled = true;
	}
	private void setBonuses(Race r)
	{
		//r.printRace();
		int[] bonuses = r.getBonus();
		for(int i = 0; i < 6; i++)
		{
			bonusTable.getModel().setValueAt(Integer.toString(bonuses[i]), i, 1);
			bonusTable.getModel().setValueAt(Integer.toString(bonuses[i]+charStats[i]), i, 2);
		}
	}

	private void setData()
	{
		finalName = nameField.getText().trim();
		finalHeight = Float.parseFloat(heightField.getText());
		finalWeight = Float.parseFloat(weightField.getText());
		finalRace = rl.get(raceBox.getSelectedIndex()+1);
		finalClass = cl.get(classBox.getSelectedIndex()+1);
		for(int i = 0; i < 6; i++)
		{
			finalStats[i] = Integer.parseInt(bonusTable.getModel().getValueAt(i, 2).toString());
		}
	}
	
	private void refresh()
	{
		setVisible(false);
		setVisible(true);
	}
	
	private void updateHP()
	{
		constitution = Integer.parseInt(bonusTable.getModel().getValueAt(2, 2).toString());
		tblStats.getModel().setValueAt(cl.get(classBox.getSelectedIndex()+1).getDie().getNumSides() + (int)Math.floor((constitution-10)/2.0), 1, 1);
	}
}

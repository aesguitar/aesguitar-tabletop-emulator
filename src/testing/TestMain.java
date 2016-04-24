package testing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.SpringLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;

import main.Character;
import util.UF;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import charcreate.CreateCharacter;

import javax.swing.border.LineBorder;

public class TestMain {

	private Character loadedCh;
	
	private JFrame frmDdSim;
	private JFileChooser fc = new JFileChooser();
	private JTextField nameField;
	private FileFilter cff = new FileFilter() {

		   public String getDescription() {
		       return "Character Files (*.char)";
		   }

		   public boolean accept(File f) {
		       if (f.isDirectory()) {
		           return true;
		       } else {
		           String filename = f.getName().toLowerCase();
		           return filename.endsWith(".char");
		       }
		   }
		};
	private JTextField txtRace;
	private JTextField txtClass;
	private JTable table;
	private JTextField txtHeight;
	private JTextField txtWeight;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestMain window = new TestMain();
					window.frmDdSim.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDdSim = new JFrame();
		frmDdSim.setIconImage(Toolkit.getDefaultToolkit().getImage(TestMain.class.getResource("/icons/dnd icon1.png")));
		frmDdSim.setTitle("D&D Sim");
		frmDdSim.getContentPane().setBackground(new Color(211, 211, 211));
		frmDdSim.setBounds(100, 100, 793, 618);
		frmDdSim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fc.setCurrentDirectory(new File(System.getProperty("user.home") + "\\workspace\\rpg\\"));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		frmDdSim.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnNew.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mnFile.add(mnNew);
		
		JMenuItem mntmCharacter = new JMenuItem("Character");
		mntmCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateCharacter cc = new CreateCharacter();
				try {
					cc.createCharacter();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mnNew.add(mntmCharacter);
		
		JMenu mnOpen = new JMenu("Open");
		mnFile.add(mnOpen);
		
		JMenuItem mntmPlayer = new JMenuItem("Character");
		mntmPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc.setFileFilter(cff);
				int done = fc.showOpenDialog(mntmPlayer);
				File tmp = fc.getSelectedFile();
				if(done == JFileChooser.APPROVE_OPTION)
				{
					loadedCh = new Character(tmp);
					nameField.setText(loadedCh.getName());
					txtRace.setText(loadedCh.getRace().getName());
					txtClass.setText(loadedCh.getCl().getName());
					setStatsTableValues();
					txtHeight.setText(String.format("%.2f%n", loadedCh.getHeight()));
					txtWeight.setText(String.format("%.2f%n", loadedCh.getWeight()));
				}
			}
		});
		mnOpen.add(mntmPlayer);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mnFile.add(mntmSaveAs);
		SpringLayout springLayout = new SpringLayout();
		frmDdSim.getContentPane().setLayout(springLayout);
		
		JLabel lblName = new JLabel("Name:");
		springLayout.putConstraint(SpringLayout.NORTH, lblName, 30, SpringLayout.NORTH, frmDdSim.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, frmDdSim.getContentPane());
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(lblName);
		
		nameField = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, lblName, -6, SpringLayout.WEST, nameField);
		springLayout.putConstraint(SpringLayout.WEST, nameField, 63, SpringLayout.WEST, frmDdSim.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, nameField, 29, SpringLayout.NORTH, frmDdSim.getContentPane());
		nameField.setBorder(new LineBorder(new Color(192, 192, 192)));
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameField.setEditable(false);
		frmDdSim.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblRace = new JLabel("Race:");
		springLayout.putConstraint(SpringLayout.NORTH, lblRace, 20, SpringLayout.SOUTH, lblName);
		springLayout.putConstraint(SpringLayout.WEST, lblRace, 18, SpringLayout.WEST, frmDdSim.getContentPane());
		lblRace.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(lblRace);
		
		txtRace = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, lblRace, -6, SpringLayout.WEST, txtRace);
		springLayout.putConstraint(SpringLayout.NORTH, txtRace, 18, SpringLayout.SOUTH, nameField);
		springLayout.putConstraint(SpringLayout.WEST, txtRace, 63, SpringLayout.WEST, frmDdSim.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtRace, 205, SpringLayout.WEST, frmDdSim.getContentPane());
		txtRace.setBorder(new LineBorder(new Color(192, 192, 192)));
		txtRace.setEditable(false);
		txtRace.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(txtRace);
		txtRace.setColumns(10);
		
		JLabel lblClass = new JLabel("Class:");
		springLayout.putConstraint(SpringLayout.NORTH, lblClass, 20, SpringLayout.SOUTH, lblRace);
		springLayout.putConstraint(SpringLayout.WEST, lblClass, 15, SpringLayout.WEST, frmDdSim.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblClass, 57, SpringLayout.WEST, frmDdSim.getContentPane());
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(lblClass);
		
		txtClass = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtClass, 18, SpringLayout.SOUTH, txtRace);
		springLayout.putConstraint(SpringLayout.WEST, txtClass, 6, SpringLayout.EAST, lblClass);
		txtClass.setBorder(new LineBorder(new Color(192, 192, 192)));
		txtClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtClass.setEditable(false);
		txtClass.setColumns(10);
		frmDdSim.getContentPane().add(txtClass);
		
		JLabel lblStatsl = new JLabel("Base Stats:");
		springLayout.putConstraint(SpringLayout.NORTH, lblStatsl, 19, SpringLayout.SOUTH, txtClass);
		springLayout.putConstraint(SpringLayout.WEST, lblStatsl, 10, SpringLayout.WEST, frmDdSim.getContentPane());
		lblStatsl.setAutoscrolls(true);
		lblStatsl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(lblStatsl);
		
		table = new JTable();
		springLayout.putConstraint(SpringLayout.NORTH, table, 6, SpringLayout.SOUTH, lblStatsl);
		springLayout.putConstraint(SpringLayout.WEST, table, 63, SpringLayout.WEST, frmDdSim.getContentPane());
		table.setBorder(new LineBorder(new Color(192, 192, 192)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Stat Names", "Values"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		table.setRowHeight(25);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		setStatsTableNames();
		
		frmDdSim.getContentPane().add(table);
		
		JLabel lblHeight = new JLabel("Height:");
		springLayout.putConstraint(SpringLayout.WEST, lblHeight, 35, SpringLayout.EAST, nameField);
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(lblHeight);
		
		txtHeight = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtHeight, 29, SpringLayout.NORTH, frmDdSim.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtHeight, 6, SpringLayout.EAST, lblHeight);
		txtHeight.setBorder(new LineBorder(new Color(192, 192, 192)));
		txtHeight.setEditable(false);
		txtHeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(txtHeight);
		txtHeight.setColumns(10);
		
		JLabel lblWeight = new JLabel("Weight:");
		springLayout.putConstraint(SpringLayout.SOUTH, lblHeight, -20, SpringLayout.NORTH, lblWeight);
		springLayout.putConstraint(SpringLayout.NORTH, lblWeight, 70, SpringLayout.NORTH, frmDdSim.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblWeight, 32, SpringLayout.EAST, txtRace);
		springLayout.putConstraint(SpringLayout.EAST, lblWeight, 87, SpringLayout.EAST, txtRace);
		lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmDdSim.getContentPane().add(lblWeight);
		
		txtWeight = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtWeight, 18, SpringLayout.SOUTH, txtHeight);
		springLayout.putConstraint(SpringLayout.WEST, txtWeight, 6, SpringLayout.EAST, lblWeight);
		txtWeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtWeight.setEditable(false);
		txtWeight.setColumns(10);
		txtWeight.setBorder(new LineBorder(new Color(192, 192, 192)));
		frmDdSim.getContentPane().add(txtWeight);
	}
	
	private void setStatsTableNames()
	{
		for(int i = 0; i < 6; i++)
		{
			table.setValueAt(" " + UF.statsList[i], i, 0);
		}
	}
	
	private void setStatsTableValues()
	{
		for(int i = 0; i < 6; i++)
			table.setValueAt(String.format("%d%n", loadedCh.getStats()[i]), i, 1);
	}
}

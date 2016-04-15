package charcreate;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.UF;

import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Toolkit;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class HalfElfChoice extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5961279320192835828L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox comboBox, comboBox_1;
	private int[] bonuses = {0,0,0,0,0,2};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HalfElfChoice dialog = new HalfElfChoice();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * Create the dialog.
	 */
	public HalfElfChoice() {
		setTitle("Half-elf");
		setIconImage(Toolkit.getDefaultToolkit().getImage(HalfElfChoice.class.getResource("/icons/dnd icon1.png")));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);
		setBounds(100, 100, 210, 230);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		JLabel lblNewLabel = new JLabel("Select your bonuses.");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, contentPanel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblNewLabel);
		
		comboBox = new JComboBox(Arrays.copyOf(UF.statsList, UF.statsList.length-1));
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox, 0, SpringLayout.WEST, lblNewLabel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, comboBox, 146, SpringLayout.WEST, lblNewLabel);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox);
		
		comboBox_1 = new JComboBox(Arrays.copyOf(UF.statsList, UF.statsList.length-1));
		comboBox_1.setSelectedIndex(1);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, comboBox_1, 6, SpringLayout.SOUTH, comboBox);
		sl_contentPanel.putConstraint(SpringLayout.WEST, comboBox_1, 0, SpringLayout.WEST, comboBox);
		sl_contentPanel.putConstraint(SpringLayout.EAST, comboBox_1, 146, SpringLayout.WEST, comboBox);
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(comboBox_1);
		
		JLabel lblError = new JLabel("Selections must be unique.");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblError, 0, SpringLayout.WEST, lblNewLabel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblError, -10, SpringLayout.SOUTH, contentPanel);
		contentPanel.add(lblError);
		lblError.setVisible(false);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if(!checkErrors())
						{
							lblError.setVisible(false);
							bonuses[comboBox.getSelectedIndex()] = 1;
							bonuses[comboBox_1.getSelectedIndex()] = 1;
							setVisible(false);
						}
						else
						{
							lblError.setVisible(true);
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
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private boolean checkErrors()
	{
		return comboBox.getSelectedIndex() == comboBox_1.getSelectedIndex();
	}
	
	public int[] getBonus()
	{
		return bonuses;
	}
}

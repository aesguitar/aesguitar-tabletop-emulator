package testing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import charcreate.CreateCharacter;

import org.eclipse.swt.widgets.Menu;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import main.Character;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;

public class TestSWT {
	private DataBindingContext m_bindingContext;

	protected Shell shlDdSim;
	final JFileChooser fc = new JFileChooser();
	private FileDialog fd;
	private FileFilter pfFilter = new FileFilter() {

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
	private File playerFile = null;
	Character ch = null;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Label lblPlayerName;
	private Label lblHeight;
	private Label lblHeightData;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					TestSWT window = new TestSWT();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlDdSim.open();
		shlDdSim.layout();
		while (!shlDdSim.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlDdSim = new Shell();
		shlDdSim.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shlDdSim.setImage(SWTResourceManager.getImage(TestSWT.class, "/icons/dnd icon1.png"));
		shlDdSim.setSize(757, 514);
		shlDdSim.setText("D&D Sim");
		shlDdSim.setLayout(new FormLayout());
		fd = new FileDialog(shlDdSim);

		Menu menu = new Menu(shlDdSim, SWT.BAR);
		shlDdSim.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmNew = new MenuItem(menu_1, SWT.CASCADE);
		mntmNew.setText("New");

		Menu menu_2 = new Menu(mntmNew);
		mntmNew.setMenu(menu_2);

		MenuItem mntmPlayer = new MenuItem(menu_2, SWT.NONE);
		mntmPlayer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CreateCharacter cc = new CreateCharacter();
				try {
					cc.createCharacter();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmPlayer.setText("Player");

		MenuItem mntmLoad = new MenuItem(menu_1, SWT.CASCADE);
		mntmLoad.setText("Load");

		Menu menu_3 = new Menu(mntmLoad);
		mntmLoad.setMenu(menu_3);

		MenuItem mntmPlayer_1 = new MenuItem(menu_3, SWT.NONE);
		
		mntmPlayer_1.setText("Player");
		
		Label lblCharacter = formToolkit.createLabel(shlDdSim, "Character:", SWT.NONE);
		lblCharacter.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblCharacter.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		FormData fd_lblCharacter = new FormData();
		fd_lblCharacter.top = new FormAttachment(0, 10);
		fd_lblCharacter.left = new FormAttachment(0, 10);
		lblCharacter.setLayoutData(fd_lblCharacter);
		
		lblPlayerName = formToolkit.createLabel(shlDdSim, "No character loaded", SWT.NONE);
		lblPlayerName.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblPlayerName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_lblPlayerName = new FormData();
		fd_lblPlayerName.top = new FormAttachment(lblCharacter, 0, SWT.TOP);
		fd_lblPlayerName.right = new FormAttachment(lblCharacter, 149, SWT.RIGHT);
		fd_lblPlayerName.left = new FormAttachment(lblCharacter, 6);
		lblPlayerName.setLayoutData(fd_lblPlayerName);
		
		lblHeight = formToolkit.createLabel(shlDdSim, "Height:",SWT.NONE);
		lblHeight.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblHeight.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		FormData fd_lblHeight = new FormData();
		fd_lblHeight.top = new FormAttachment(lblCharacter, 6);
		fd_lblHeight.right = new FormAttachment(lblCharacter, 0, SWT.RIGHT);
		lblHeight.setLayoutData(fd_lblHeight);
		
		lblHeightData = formToolkit.createLabel(shlDdSim, "", SWT.NONE);
		lblHeightData.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblHeightData.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		FormData fd_lblHeightData = new FormData();
		fd_lblHeightData.right = new FormAttachment(lblPlayerName, 49);
		fd_lblHeightData.bottom = new FormAttachment(lblHeight, 0, SWT.BOTTOM);
		fd_lblHeightData.left = new FormAttachment(lblPlayerName, 0, SWT.LEFT);
		lblHeightData.setLayoutData(fd_lblHeightData);
		//lblHeight.setVisible(false);
		

		mntmPlayer_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fd.setFilterExtensions(new String[] {"*.char"});
				fd.open();
				playerFile = new File(fd.getFileName());
				if(playerFile != null && playerFile.exists())
				{
					ch = new Character(playerFile);
					//ch.printCharacter();
					lblPlayerName.setText(ch.getName());
					//lblHeight.setVisible(true);
					lblHeightData.setText(String.format("%.2f%n", ch.getHeight()));
				}
			}
		});
		m_bindingContext = initDataBindings();
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		return bindingContext;
	}
}

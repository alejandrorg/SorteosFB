package com.alejandrorg.fbsorteos.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.List;

import com.alejandrorg.fbsorteos.logic.Constants;
import com.alejandrorg.fbsorteos.logic.FBSorteos;
import com.alejandrorg.fbsorteos.objects.ObserverMessage;
import com.alejandrorg.fbsorteos.objects.Post;

public class MainGUI implements Observer {

	protected Shell shlSorteadorFacebook;
	private Text TXTPost;
	private Text TXTPostDate;
	private Text TXTNumLikes;
	private Text TXTWinner;
	private FBSorteos fbSorteos;
	private Spinner SPMagicNumber;
	private Button BTStart;
	private List LSTMagicNumberUsers;
	private Post selectedPost;

	private Button BTSearchPost;
	private Map<String, Post> posts;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainGUI window = new MainGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSorteadorFacebook.open();
		shlSorteadorFacebook.layout();
		while (!shlSorteadorFacebook.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSorteadorFacebook = new Shell(SWT.SHELL_TRIM & SWT.CLOSE
				| (~SWT.RESIZE) & SWT.APPLICATION_MODAL);
		shlSorteadorFacebook.setSize(568, 606);
		shlSorteadorFacebook.setText("Sorteos para Facebook");

		Menu menu = new Menu(shlSorteadorFacebook, SWT.BAR);
		shlSorteadorFacebook.setMenuBar(menu);

		MenuItem mntmArchivo_1 = new MenuItem(menu, SWT.CASCADE);
		mntmArchivo_1.setText("Archivo");

		Menu menu_1 = new Menu(mntmArchivo_1);
		mntmArchivo_1.setMenu(menu_1);

		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				openConfiguration();
			}
		});
		mntmNewItem.setText("Configuraci\u00F3n");

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmNewItem_1 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				System.exit(0);
			}
		});
		mntmNewItem_1.setText("Salir");

		Group grpDatosDelPost = new Group(shlSorteadorFacebook, SWT.NONE);
		grpDatosDelPost.setText("Datos del post");
		grpDatosDelPost.setBounds(10, 10, 532, 193);

		Label lblMensaje = new Label(grpDatosDelPost, SWT.NONE);
		lblMensaje.setBounds(10, 28, 55, 15);
		lblMensaje.setText("Post");

		TXTPost = new Text(grpDatosDelPost, SWT.BORDER);
		TXTPost.setEditable(false);
		TXTPost.setBounds(10, 50, 512, 53);

		Label lblNewLabel = new Label(grpDatosDelPost, SWT.NONE);
		lblNewLabel.setBounds(10, 122, 87, 15);
		lblNewLabel.setText("Fecha de post");

		TXTPostDate = new Text(grpDatosDelPost, SWT.BORDER);
		TXTPostDate.setEditable(false);
		TXTPostDate.setBounds(103, 119, 142, 21);

		Label numlikeslb = new Label(grpDatosDelPost, SWT.NONE);
		numlikeslb.setBounds(265, 122, 103, 15);
		numlikeslb.setText("N\u00FAmero de likes:");

		TXTNumLikes = new Text(grpDatosDelPost, SWT.BORDER);
		TXTNumLikes.setEditable(false);
		TXTNumLikes.setBounds(374, 119, 148, 21);
		
		BTSearchPost = new Button(grpDatosDelPost, SWT.NONE);
		BTSearchPost.setBounds(439, 158, 83, 25);
		BTSearchPost.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				selectPost();
			}
		});
		BTSearchPost.setText("Buscar post");

		SPMagicNumber = new Spinner(shlSorteadorFacebook, SWT.BORDER);
		SPMagicNumber.setMaximum(10);
		SPMagicNumber.setMinimum(1);
		SPMagicNumber.setBounds(459, 209, 83, 22);

		Label lblNewLabel_1 = new Label(shlSorteadorFacebook, SWT.NONE);
		lblNewLabel_1.setBounds(348, 212, 96, 15);
		lblNewLabel_1.setText("N\u00FAmero m\u00E1gico");

		BTStart = new Button(shlSorteadorFacebook, SWT.NONE);
		BTStart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				startContext();
			}
		});
		BTStart.setEnabled(false);
		BTStart.setBounds(440, 249, 102, 25);
		BTStart.setText("Comenzar sorteo");

		Group grpResultados = new Group(shlSorteadorFacebook, SWT.NONE);
		grpResultados.setText("Resultados");
		grpResultados.setBounds(10, 280, 532, 260);

		LSTMagicNumberUsers = new List(grpResultados, SWT.BORDER);
		LSTMagicNumberUsers.setEnabled(false);
		LSTMagicNumberUsers.setBounds(10, 62, 228, 188);

		Label lblNewLabel_2 = new Label(grpResultados, SWT.NONE);
		lblNewLabel_2.setBounds(10, 29, 228, 15);
		lblNewLabel_2
				.setText("Aleatorios generados por n\u00FAmero m\u00E1gico");

		TXTWinner = new Text(grpResultados, SWT.BORDER);
		TXTWinner.setEditable(false);
		TXTWinner.setBounds(309, 136, 213, 21);

		Label lblNewLabel_3 = new Label(grpResultados, SWT.NONE);
		lblNewLabel_3.setBounds(309, 115, 55, 15);
		lblNewLabel_3.setText("Ganador");

	}

	protected void selectPost() {
		posts = new HashMap<String, Post>();
		PostSelectorWindow psw = new PostSelectorWindow(
				this.shlSorteadorFacebook, SWT.SHELL_TRIM & SWT.CLOSE
						| (~SWT.RESIZE) & SWT.APPLICATION_MODAL, this, posts);
		psw.open();
		
	}

	protected void startContext() {
		this.LSTMagicNumberUsers.removeAll();
		this.TXTWinner.setText("");
		fbSorteos = new FBSorteos(this, this.SPMagicNumber.getSelection(), this.selectedPost);
		Thread tr = new Thread(fbSorteos);
		tr.start();
	}

	protected void openConfiguration() {
		ConfigurationWindow cw = new ConfigurationWindow(
				this.shlSorteadorFacebook, SWT.SHELL_TRIM & SWT.CLOSE
						| (~SWT.RESIZE) & SWT.APPLICATION_MODAL);
		cw.open();
	}


	public void update(Observable o, Object arg) {
		ObserverMessage om = (ObserverMessage) arg;
		switch (om.getTypeOfMessage()) {
		case Constants.ERROR_MESSAGE:
			Display.getDefault().asyncExec(new Runnable() {
		        public void run() {
					ShowMessage.showMessage("Error obteniendo post",
							"Error al obtener el post: " + om.getMessage(),
							SWT.ICON_ERROR, shlSorteadorFacebook);
		        }
		    });
			break;
		case Constants.ADD_USER:
			Display.getDefault().asyncExec(new Runnable() {
		        public void run() {
		        	LSTMagicNumberUsers.add(om.getMessage());
		        }
		    });
			break;
		case Constants.SET_WINNER:
			Display.getDefault().asyncExec(new Runnable() {
		        public void run() {
		        	TXTWinner.setText(om.getMessage());
		        }
		    });
			break;
		}

	}

	public void setSelectedPost(Post sp) {
		this.selectedPost = sp;
		this.TXTPost.setText(this.selectedPost.getMessage());
		this.TXTPostDate.setText(this.selectedPost.getCreatedTime());
		this.TXTNumLikes.setText(Integer.toString(this.selectedPost.getLikes().size()));
		this.BTStart.setEnabled(true);
	}
}

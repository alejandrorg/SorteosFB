package com.alejandrorg.fbsorteos.gui;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.alejandrorg.fbsorteos.logic.Constants;
import com.alejandrorg.fbsorteos.logic.RetrievePosts;
import com.alejandrorg.fbsorteos.objects.ObserverMessage;
import com.alejandrorg.fbsorteos.objects.Post;

import org.eclipse.swt.widgets.TableColumn;

public class PostSelectorWindow extends Dialog implements Observer {

	protected Object result;
	protected Shell shell;
	private MainGUI mainGUI;
	private Table TBPosts;
	private Text TXTIDPost;
	private Text TXTFechaPost;
	private Text TXTLikes;
	private Text TXTMessage;
	private Button BTSearchPosts;
	private Spinner SPNumberPosts;
	private Button BTSelect;
	private Map<String, Post> posts;
	private TableColumn tblclmnNewColumn;
	private TableColumn tblclmnNewColumn_1;
	private TableColumn tblclmnNewColumn_2;
	private Label LBstatus;
	private Post selectedPost;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PostSelectorWindow(Shell parent, int style, MainGUI mg, Map<String, Post> posts) {
		super(parent, style);
		this.mainGUI = mg;
		this.posts = posts;
		setText("Selector de posts");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(681, 503);
		shell.setText(getText());
		
		Group grpPostsDisponibles = new Group(shell, SWT.NONE);
		grpPostsDisponibles.setText("Posts disponibles");
		grpPostsDisponibles.setBounds(10, 10, 655, 229);
		
		TBPosts = new Table(grpPostsDisponibles, SWT.BORDER | SWT.FULL_SELECTION);
		TBPosts.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				loadPostInfo();
			}
		});
		TBPosts.setBounds(10, 26, 635, 125);
		TBPosts.setHeaderVisible(true);
		TBPosts.setLinesVisible(true);
		
		tblclmnNewColumn = new TableColumn(TBPosts, SWT.CENTER);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("ID Post");
		
		tblclmnNewColumn_1 = new TableColumn(TBPosts, SWT.CENTER);
		tblclmnNewColumn_1.setWidth(436);
		tblclmnNewColumn_1.setText("Mensaje");
		
		tblclmnNewColumn_2 = new TableColumn(TBPosts, SWT.CENTER);
		tblclmnNewColumn_2.setWidth(93);
		tblclmnNewColumn_2.setText("Likes");
		
		SPNumberPosts = new Spinner(grpPostsDisponibles, SWT.BORDER);
		SPNumberPosts.setMinimum(1);
		SPNumberPosts.setSelection(10);
		SPNumberPosts.setBounds(540, 157, 105, 22);
		
		Label lblNewLabel = new Label(grpPostsDisponibles, SWT.NONE);
		lblNewLabel.setBounds(243, 160, 284, 15);
		lblNewLabel.setText("N\u00FAmero de posts a obtener (m\u00E1s a menos reciente)");
		
		BTSearchPosts = new Button(grpPostsDisponibles, SWT.NONE);
		BTSearchPosts.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				searchPosts();
			}
		});
		BTSearchPosts.setBounds(570, 185, 75, 25);
		BTSearchPosts.setText("Buscar");
		
		LBstatus = new Label(grpPostsDisponibles, SWT.NONE);
		LBstatus.setBounds(10, 204, 352, 15);
		
		Group grpDatosDelPost = new Group(shell, SWT.NONE);
		grpDatosDelPost.setText("Datos del post");
		grpDatosDelPost.setBounds(10, 250, 655, 175);
		
		TXTIDPost = new Text(grpDatosDelPost, SWT.BORDER);
		TXTIDPost.setEditable(false);
		TXTIDPost.setBounds(80, 26, 132, 21);
		
		Label lblIdDelPost = new Label(grpDatosDelPost, SWT.NONE);
		lblIdDelPost.setBounds(10, 29, 64, 15);
		lblIdDelPost.setText("ID del post");
		
		Label lblNewLabel_1 = new Label(grpDatosDelPost, SWT.NONE);
		lblNewLabel_1.setBounds(228, 29, 103, 15);
		lblNewLabel_1.setText("Fecha de creaci\u00F3n");
		
		TXTFechaPost = new Text(grpDatosDelPost, SWT.BORDER);
		TXTFechaPost.setEditable(false);
		TXTFechaPost.setBounds(337, 26, 157, 21);
		
		Label lblLikes = new Label(grpDatosDelPost, SWT.NONE);
		lblLikes.setBounds(509, 29, 40, 15);
		lblLikes.setText("Likes");
		
		TXTLikes = new Text(grpDatosDelPost, SWT.BORDER);
		TXTLikes.setEditable(false);
		TXTLikes.setBounds(555, 26, 90, 21);
		
		Label lblMensaje = new Label(grpDatosDelPost, SWT.NONE);
		lblMensaje.setBounds(10, 63, 55, 15);
		lblMensaje.setText("Mensaje");
		
		TXTMessage = new Text(grpDatosDelPost, SWT.BORDER);
		TXTMessage.setEditable(false);
		TXTMessage.setBounds(10, 84, 635, 70);
		
		BTSelect = new Button(shell, SWT.NONE);
		BTSelect.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				selectPost();
			}
		});
		BTSelect.setEnabled(false);
		BTSelect.setBounds(590, 431, 75, 25);
		BTSelect.setText("Seleccionar");

	}

	protected void selectPost() {
		mainGUI.setSelectedPost(selectedPost);
		this.shell.dispose();
	}

	protected void loadPostInfo() {
		if (this.TBPosts.getSelectionIndex() >= 0) {
			this.BTSelect.setEnabled(true);
			selectedPost = getPostByID(this.TBPosts.getItem(this.TBPosts.getSelectionIndex()).getText(0));
			this.TXTFechaPost.setText(selectedPost.getCreatedTime());
			this.TXTIDPost.setText(selectedPost.getID());
			this.TXTLikes.setText(Integer.toString(selectedPost.getLikes().size()));
			this.TXTMessage.setText(selectedPost.getMessage());
		}
		else {
			this.BTSelect.setEnabled(false);
		}
		
	}

	private Post getPostByID(String idPost) {
		return posts.get(idPost);
	}

	protected void searchPosts() {
		RetrievePosts rp;
		try {
			rp = new RetrievePosts(this, posts, this.SPNumberPosts.getSelection());
			Thread tr = new Thread(rp);
			tr.start();
			enableGUI(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Observable o, Object arg) {
		ObserverMessage om = (ObserverMessage) arg;
		switch (om.getTypeOfMessage()) {
		case Constants.POSTS_RETRIEVED:
			Display.getDefault().asyncExec(new Runnable() {
		        public void run() {
		        	enableGUI(true);
		        	LBstatus.setText("");
		        	loadPostsIntoTable();
		        }
		    });
			break;
		case Constants.UPDATE_GUI:
			Display.getDefault().asyncExec(new Runnable() {
		        public void run() {
		        	LBstatus.setText(om.getMessage());
		        }
		    });
			
			break;
		}
	}

	protected void loadPostsIntoTable() {
		Iterator<Entry<String, Post>> it = this.posts.entrySet().iterator();
		while (it.hasNext()) {
			Post p = it.next().getValue();
			TableItem item = new TableItem(this.TBPosts, SWT.NULL);
			item.setText(0, p.getID());
			item.setText(1, p.getMessage());
			item.setText(2, Integer.toString(p.getLikes().size()));
		}
		
	}

	protected void enableGUI(boolean b) {
		TBPosts.setEnabled(b);
		SPNumberPosts.setEnabled(b);
		BTSearchPosts.setEnabled(b);
	}
}

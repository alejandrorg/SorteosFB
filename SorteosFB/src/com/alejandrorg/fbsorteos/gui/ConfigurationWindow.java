package com.alejandrorg.fbsorteos.gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

import com.alejandrorg.fbsorteos.logic.ConfigManager;
import com.alejandrorg.fbsorteos.logic.Constants;
import com.alejandrorg.fbsorteos.logic.StaticUtils;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ConfigurationWindow extends Dialog {

	protected Object result;
	protected Shell shlConfiguration;
	private Text TXTToken;
	private Button BTSave;
	private Button BTSalir;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ConfigurationWindow(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlConfiguration.open();
		shlConfiguration.layout();
		Display display = getParent().getDisplay();
		while (!shlConfiguration.isDisposed()) {
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
		shlConfiguration = new Shell(getParent(), getStyle());
		shlConfiguration.setSize(450, 149);
		shlConfiguration.setText("Configuracion");
		
		Label lblNewLabel = new Label(shlConfiguration, SWT.NONE);
		lblNewLabel.setBounds(10, 21, 185, 15);
		lblNewLabel.setText("Token de acceso de la p\u00E1gina");
		
		TXTToken = new Text(shlConfiguration, SWT.BORDER);
		TXTToken.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				BTSave.setEnabled(!StaticUtils.isEmpty(TXTToken.getText()));
			}
		});
		TXTToken.setBounds(10, 42, 424, 21);
		
		BTSave = new Button(shlConfiguration, SWT.NONE);
		BTSave.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				saveConfig();
			}
		});
		BTSave.setEnabled(false);
		BTSave.setBounds(274, 86, 75, 25);
		BTSave.setText("Guardar");
		
		BTSalir = new Button(shlConfiguration, SWT.NONE);
		BTSalir.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shlConfiguration.dispose();
			}
		});
		BTSalir.setBounds(359, 86, 75, 25);
		BTSalir.setText("Salir");
		loadConfig();
	}

	private void saveConfig() {
		try {
			ConfigManager.setConfig(Constants.ACCESS_TOKEN, TXTToken.getText());
			ShowMessage.showMessage("Configuración guardada", "Configuración guardada correctamente.", SWT.ICON_INFORMATION, shlConfiguration);
		} catch (Exception e) {
			ShowMessage.showMessage("Error guardando configuración", "Error guardando access token: " + e.getMessage(), SWT.ICON_ERROR, shlConfiguration);
		}
	}
	
	private void loadConfig() {
		try {
			String at = ConfigManager.getConfig(Constants.ACCESS_TOKEN);
			TXTToken.setText(at);
		} catch (Exception e) {
			ShowMessage.showMessage("Error cargando configuración", "Error cargando access token: " + e.getMessage(), SWT.ICON_ERROR, shlConfiguration);
		}
		
	}

}

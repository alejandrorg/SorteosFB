package com.alejandrorg.fbsorteos.gui;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Clase para mostrar mensajes tipo JOptionPane.
 * 
 */

public class ShowMessage {

	/**
	 * Muestra un mensaje normal con un boton para aceptar.
	 * 
	 * @param subject
	 *            Recibe el titulo de la ventana.
	 * @param message
	 *            El mensaje
	 * @param type
	 *            El tipo
	 * @param shell
	 *            La shell
	 */
	public static void showMessage(String subject, String message, int type,
			Shell shell) {

		MessageBox msg = new MessageBox(shell, type);
		msg.setText(subject);
		msg.setMessage(message);
		msg.open();
	}

	/**
	 * Método para mostrar un dialogo de "Ok" o "Cancel"
	 * 
	 * @param subject
	 *            Recibe el titulo
	 * @param message
	 *            Mensaje
	 * @param shell
	 *            Shell
	 * @return Devuelve un entero donde se puede ver si pulso Ok o Cancel
	 */
	public static int showConfirmDialog(String subject, String message,
			Shell shell) {
		MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK
				| SWT.CANCEL);
		msg.setText(subject);
		msg.setMessage(message);
		int rc = msg.open();
		return rc;
	}
}

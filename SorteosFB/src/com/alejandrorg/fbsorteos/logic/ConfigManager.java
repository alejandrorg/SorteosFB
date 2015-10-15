package com.alejandrorg.fbsorteos.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Clase para manejar ficheros de configuración.
 * @author alejandro
 *
 */
public class ConfigManager {

	private static Properties config;
	private final static String CFG_FILE = "config/config.cfg";

	/**
	 * Método para crear la instancia.
	 * 
	 * @throws Exception
	 *             Puede lanzar excepción.
	 */
	private static void createInstace() throws Exception {
		config = new Properties();
		config.load(new FileInputStream(CFG_FILE));
	}

	/**
	 * Método para obtener un valor de configuración.
	 * 
	 * @param key
	 *            Recibe la clave.
	 * @return Devuelve el valor.
	 * @throws Exception
	 *             Puede lanzar excepción.
	 */
	public static String getConfig(String key) throws Exception {
		if (config == null) {
			createInstace();
		}
		String ret = config.getProperty(key);
		if (ret == null) {
			ret = "";
		}
		return ret;
	}

	/**
	 * Método para obtener una configuración de un fichero concreto.
	 * 
	 * @param key
	 *            Recibe la configuración.
	 * @param file
	 *            Recibe el fichero.
	 * @return Devuelve el resultado.
	 * @throws Exception
	 *             Puede lanzar excepción.
	 */
	public static String getConfig(String key, File file) throws Exception {
		Properties tmpProp = new Properties();
		FileInputStream fi = new FileInputStream(file);
		tmpProp.load(fi);
		String ret = tmpProp.getProperty(key);
		if (ret == null) {
			ret = "";
		}
		fi.close();
		return ret;
	}

	/**
	 * Método para establecer un valor de configuración.
	 * 
	 * @param key
	 *            Recibe la clave.
	 * @param value
	 *            Recibe el valor.
	 * @throws Exception
	 *             Puede lanzar excepción.
	 */
	public static void setConfig(String key, String value) throws Exception {
		if (config == null) {
			createInstace();
		}
		config.setProperty(key, value);
		config.store(new FileOutputStream(CFG_FILE), "");
	}

	/**
	 * Método para establecer un valor de configuración.
	 * 
	 * @param key
	 *            Recibe la clave.
	 * @param value
	 *            Recibe el valor.
	 * @throws Exception
	 *             Puede lanzar excepción.
	 */
	public static void removeKey(String key) throws Exception {
		if (config == null) {
			createInstace();
		}
		config.remove(key);
		config.store(new FileOutputStream(CFG_FILE), "");
	}
	
	/**
	 * Método para establecer una configuración.
	 * 
	 * @param key
	 *            Clave
	 * @param value
	 *            Valor
	 * @param file
	 *            Fichero
	 * @throws Exception
	 *             Puede lanzar excepción.
	 */
	public static void setConfig(String key, String value, File file)
			throws Exception {
		Properties tmpProp = new Properties();
		FileInputStream fi = new FileInputStream(file);
		tmpProp.load(fi);
		tmpProp.setProperty(key, value);
		FileOutputStream fo = new FileOutputStream(file);
		tmpProp.store(fo, "");
		fi.close();
		fo.close();
	}
}

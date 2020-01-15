package com.abapi.cloud.common.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class PropUtil {
	// private static Map<String, ResourceBundle> resourceBundleMap = new
	// HashMap<String, ResourceBundle>();
	private static Map<String, Properties> PropertiesMap = new HashMap<String, Properties>();

	// 私有构造方法，防止被实例化
	private PropUtil() {
	}

	// // 静态工程方法，创建ResourceBundle
	// private static ResourceBundle getResourceBundle(String name, Locale
	// locale) {
	// if (!resourceBundleMap.containsKey(name)) {
	// synchronized (PropUtil.class) {
	// resourceBundleMap.put(name, ResourceBundle.getBundle(name, locale));
	// }
	// }
	// return resourceBundleMap.get(name);
	// }

	// 静态工程方法，创建ResourceBundle
	private static Properties getProperties(String name) {
		if (!PropertiesMap.containsKey(name)) {
			synchronized (PropUtil.class) {
				Properties properties = new Properties();
				try {
					properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(name));
					PropertiesMap.put(name, properties);
				} catch (IOException ie) {
					ie.printStackTrace();
				} catch (NullPointerException ne) {
					ne.printStackTrace();
				}
			}
		}
		return PropertiesMap.get(name);
	}

	/**
	 * 根据配置ID取得内容(可带不定个数参数)
	 * 
	 * @param messageCode
	 *            消息ID
	 * @param arguments
	 *            消息参数
	 * @return 消息内容
	 */
	public static String getProperty(String name, String messageCode, String... arguments) {
		String message = null;
		// if (name.endsWith(".properties")) {
		Properties p = getProperties(name);
		message = p.getProperty(messageCode);
		// }
		// else {
		// ResourceBundle rb = getResourceBundle(name, null);
		// message = rb.getString(messageCode);
		// }

		if (arguments != null && arguments.length != 0 && message != null) {
			message = MessageFormat.format(message, (Object[]) arguments);
		}
		return message;
	}

	// public static String getProperty(String name, String messageCode, Locale
	// locale, String... arguments) {
	// ResourceBundle rb = getResourceBundle(name, locale);
	// String message = rb.getString(messageCode);
	//
	// if (arguments != null && arguments.length != 0 && message != null) {
	// message = MessageFormat.format(message, (Object[]) arguments);
	// }
	// return message;
	// }
}

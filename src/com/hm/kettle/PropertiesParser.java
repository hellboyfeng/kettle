package com.hm.kettle;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;

/**
 * 属性文件解析
 * @Description
 * @date 2014年10月27日  
 * @author fengping
 */
public class PropertiesParser {

	private Properties props;
	public PropertiesParser(Properties props) {
		this.props = props;
	}
	public String[] getPropertyGroups(String prefix) {
		Enumeration<?> keys = props.propertyNames();
		HashSet<String> groups = new HashSet<String>(10);
		if (!prefix.endsWith(".")) {
			prefix += ".";
		}
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix)) {
				String groupName = key.substring(prefix.length(),
						key.indexOf('.', prefix.length()));
				groups.add(groupName);
			}
		}

		return (String[]) groups.toArray(new String[groups.size()]);
	}

	public Properties getPropertyGroup(String prefix, boolean stripPrefix,
			String[] excludedPrefixes) {
		Enumeration<?> keys = props.propertyNames();
		Properties group = new Properties();
		if (!prefix.endsWith(".")) {
			prefix += ".";
		}
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix)) {

				boolean exclude = false;
				if (excludedPrefixes != null) {
					for (int i = 0; (i < excludedPrefixes.length)
							&& (exclude == false); i++) {
						exclude = key.startsWith(excludedPrefixes[i]);
					}
				}
				if (exclude == false) {
					String value = getStringProperty(key, "");
						group.put(key.substring(prefix.length()), value);
						if (stripPrefix) {
					} else {
						group.put(key, value);
					}
				}
			}
		}
		return group;
	}

	public String getStringProperty(String name, String defaultValue) {
		String val = props.getProperty(name, defaultValue);
		if (val == null) {
			return defaultValue;
		}
		val = val.trim();
		return (val.length() == 0) ? defaultValue : val;
	}
}

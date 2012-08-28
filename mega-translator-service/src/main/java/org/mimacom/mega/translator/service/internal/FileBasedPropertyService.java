package org.mimacom.mega.translator.service.internal;

import org.mimacom.mega.translator.service.PropertyKey;
import org.mimacom.mega.translator.service.PropertyService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class FileBasedPropertyService implements PropertyService {

	private final HashMap<PropertyKey, String> properties = new HashMap<PropertyKey, String>();


	@PostConstruct
	public void loadProperties() {
		// TODO proper handling of properties
		properties.put(PropertyKey.TRANSLATION_FILE_STORE, "C:\\development\\projects\\mega-translator\\store");
	}

	@Override
	public String getProperty(PropertyKey key) {
		String value = properties.get(key);
		if (value == null) {
			throw new NullPointerException("There's no associated value for key: " + key.name());
		}
		return value;
	}
}

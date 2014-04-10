package org.groept.cloudMigration.web.util;

import org.springframework.core.convert.converter.Converter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class KeyToStringConverter implements Converter<Key, String> {

	@Override
	public String convert(Key keyObject) {
		if (keyObject != null) {
	           return KeyFactory.keyToString(keyObject);
	       } else {
	    	   return ("");
	       }
	}
}

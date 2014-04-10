package org.groept.cloudMigration.web.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class StringToKeyConverter implements Converter<String, Key> {

	@Override
	public Key convert(String normalizedKey) {
		if( StringUtils.hasText(normalizedKey)) {
			return(KeyFactory.stringToKey(normalizedKey));
		} else {
			return null;
		}
	}

}

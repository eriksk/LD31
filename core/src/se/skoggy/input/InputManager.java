package se.skoggy.input;

import java.util.HashMap;

public class InputManager {
	private static HashMap<String, KeyCodes> keyMappings = new HashMap<String, KeyCodes>();

	public static final String TYPE_OUYA = "OUYA";
	public static final String TYPE_XBOX_360 = "XBOX360";

	public static KeyCodes getMappingFor(String type){
		if(keyMappings.containsKey(type)){
			return keyMappings.get(type);
		}

		if(type.equals(TYPE_OUYA)){
			keyMappings.put(TYPE_OUYA, new OUYAKeyCodes());
			return getMappingFor(type);
		}else if(type.equals(TYPE_XBOX_360)){
			keyMappings.put(TYPE_XBOX_360, new Xbox360KeyCodes());
			return getMappingFor(type);
		}
		return null;
	}
}

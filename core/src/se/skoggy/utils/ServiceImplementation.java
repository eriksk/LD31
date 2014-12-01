package se.skoggy.utils;

public class ServiceImplementation {

	public Class<?> interfaceType, implementationType;
	public Object instance;
	
	public ServiceImplementation(Class<?> interfaceType, Class<?> implemenationType) {
		this(interfaceType, implemenationType, null);
	}
	
	public ServiceImplementation(Class<?> interfaceType, Class<?> implemenationType, Object instance) {
		this.interfaceType = interfaceType;
		this.implementationType = implemenationType;
		this.instance = instance;
	}
	
}

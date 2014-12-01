package se.skoggy.utils;

import java.util.ArrayList;
import java.util.List;

public class ServiceLocator {

	public static final ServiceLocator context = new ServiceLocator();

	List<ServiceImplementation> services;
	
	public ServiceLocator() {
		services = new ArrayList<ServiceImplementation>();
	}
	
	public void register(Class<?> interfaceClass, Class<?> implementationClass){
		if(interfaceExistsInServices(interfaceClass))
			throw new RuntimeException(interfaceClass.getName() + " is already registered");
		services.add(new ServiceImplementation(interfaceClass, implementationClass));
	}
	
	public void registerSingleton(Class<?> interfaceClass, Object instance){
		if(interfaceExistsInServices(interfaceClass))
			throw new RuntimeException(interfaceClass.getName() + " is already registered");
		
		services.add(new ServiceImplementation(interfaceClass, instance.getClass(), instance));		
	}
	
	public <TInterfaceType> TInterfaceType locate(Class<TInterfaceType> interfaceType){
		if(!interfaceExistsInServices(interfaceType))
			throw new RuntimeException(interfaceType.getName() + " is not registered");
		
		return (TInterfaceType)getService(interfaceType).instance;
	}
	
	private ServiceImplementation getService(Class<?> interfaceType){
		for (ServiceImplementation service : services) {
			if(service.interfaceType == interfaceType)
				return service;
		}
		return null;
	}

	private boolean interfaceExistsInServices(Class<?> interfaceClass) {
		for (ServiceImplementation service : services) {
			if(service.interfaceType == interfaceClass)
				return true;
		}
		return false;
	}
}

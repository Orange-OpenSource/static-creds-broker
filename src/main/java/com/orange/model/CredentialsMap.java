package com.orange.model;

import java.util.*;
import java.util.Map.Entry;

/**
 * map of servicePlan (String) and credentials (Map<String, Object>)
 * servicePlan is the combination identification of service and plan
 * identification could be "id" named in env. variables or name
 */
public class CredentialsMap {
	private Map<List<String>, Credentials> credentialsMap = new HashMap<>();

	/**
	 * 
	 * @param serviceID
	 * @param planID null if the credential is for all plans of the service
	 * @param credentialName
	 * @param credentialValue
	 */
	public void addCredential(String serviceID, String planID, String credentialName, Object credentialValue) {
		Credentials credentialsToAdd = new Credentials();
		credentialsToAdd.put(credentialName, credentialValue);
		addCredentials(serviceID, planID, credentialsToAdd);
	}
	
	/**
	 * 
	 * @param serviceID
	 * @param planID null if the credential is for all plans of the service
	 * @param credentialsToAdd
	 */
	public void addCredentials(String serviceID, String planID, Credentials credentialsToAdd) {
		List<String> servicePlan;
		if (planID == null) {
			servicePlan = Arrays.asList(serviceID);
		}
		else {
			servicePlan = Arrays.asList(serviceID, planID);
		}
		Credentials credentials = credentialsMap.get(servicePlan);
		if (credentials == null) {
			credentials = new Credentials();
		}
		credentials.putAll(credentialsToAdd);
		credentialsMap.put(servicePlan, credentials);
	}

	/**
	 * get all keys which has credentials defined 
	 * @return
	 */
	public Set<Entry<List<String>,Credentials>> getEntrySet(){
		return credentialsMap.entrySet();
	}
	
	public boolean contains(String serviceID, String planID, String credentialName, Object credentialValue){
		List<String> servicePlan = planID == null ? Arrays.asList(serviceID) : Arrays.asList(serviceID, planID);
		Map<String, Object> credentials = credentialsMap.get(servicePlan);
		if (credentials != null && credentials.get(credentialName).equals(credentialValue)) {
			return true;
		}
		else {
			return false;
		}
	}

	public Credentials getCredentialsForPlan(String planId) {
		for (Entry<List<String>,Credentials> entry : getEntrySet()) {
			List<String> servicePlanName = entry.getKey();
			String service_name = servicePlanName.get(0);
			String plan_name = servicePlanName.get(1);
			String plan_guid = UUID.nameUUIDFromBytes(Arrays.asList(service_name, plan_name).toString().getBytes()).toString();
			if (plan_guid.equals(planId)) {
				return entry.getValue();
			}
		}
		return null;
	}
}

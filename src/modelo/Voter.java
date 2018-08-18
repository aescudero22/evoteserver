package modelo;

import org.json.simple.JSONObject;

public class Voter {
	public String name;
	public String surname;
	public String id;
	
	public Voter(String name, String surname, String id) {
		this.name 		= name;
		this.surname 	= surname;
		this.id 			= id;
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		json.put("name"		, name);
		json.put("surname"	, surname);
		json.put("id"		, id);
		
		return json;
	}
}

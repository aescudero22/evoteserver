package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import javax.annotation.Resource;

import org.json.simple.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Candidature {
	public 			int 		id;
	public 			String 	name;
	public 			String 	description;
	public 			String 	agenda;
	public 			String 	image_base64;
	
	private static	int		currentMaxId	=0;			
	
	
	public Candidature(String name, String description, String agenda, String pathToImage) {
		this.id				= currentMaxId;
		currentMaxId++;
		this.name 			= name;
		this.description 	= description;
		this.agenda 			= agenda;
		
		String base64string = "";
		if (!pathToImage.equals("")) {
			try {
				String file = "";
				BufferedReader br = new BufferedReader (new InputStreamReader (getClass().getClassLoader().getResource(pathToImage).openStream()));
				while (br.ready()) {
					file += br.readLine();
				}
				base64string = Base64.encode(file.getBytes());
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.image_base64 	= base64string;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		json.put("Id"			, id);
		json.put("Name"			, name);
		json.put("Description"	, description);
		json.put("Agenda"		, agenda);
		json.put("Image_base64"	, image_base64);
		
		return json;
	}
}

package modelo;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Elections {
	public			int 							id;
	public 			String 						name;
	public 			String 						description;
	public 			ArrayList<Candidature> 		candidates;
	public			HashMap<Integer, Integer>	votes;
	public			ArrayList<Vote>				voteInfo;
	public 			Census				 		census;
	
	private static	int							currentMaxId	=0;			
	
	public Elections(String name, String description) {
		this.id				= currentMaxId;
		currentMaxId++;
		this.name 			= name;
		this.description 	= description;
		this.candidates 		= new ArrayList<Candidature>();
		this.votes			= new HashMap<Integer, Integer>();
		this.voteInfo		= new ArrayList<Vote>();
		this.census			= new Census();
	}
	
	public Elections() {
		this.id				= currentMaxId;
		currentMaxId++;
		this.name 			= "namePlaceholder";
		this.description 	= "descriptionPlaceholder";
		this.candidates 		= new ArrayList<Candidature>();
		this.votes			= new HashMap<Integer, Integer>();
		this.voteInfo		= new ArrayList<Vote>();
		this.census			= new Census();
	}
	
	public void AddCandidature(Candidature c) {
		candidates.add(c);
		votes.put(c.id, 0);
	}
	
	public void AddVoter(Voter v) {
		census.AddVoterToCensus(v);
	}
	
	public void VoteFor (int candidateId) {
		votes.put(candidateId, votes.get(candidateId)+1);
		for (Candidature c : candidates) {
			if (c.id == candidateId) {
				voteInfo.add(new Vote(c.name));
				break;
			}
		}
		
		try {
			BufferedWriter bw = new BufferedWriter (new FileWriter(System.getProperty("user.home") + File.separatorChar + id + ".log", true));
			bw.write(candidateId + " - " + votes.get(candidateId));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR: fallo al abrir el archivo de respaldo.");
		}
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("Id"			, id);
		json.put("Name"			, name);
		json.put("Description"	, description);
		
		return json;
	}
}

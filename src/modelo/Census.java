package modelo;

import java.util.HashMap;

public class Census {
	private HashMap<String, Boolean> census;
	
	public Census () {
		census = new HashMap<String, Boolean>();
	}
	
	public void AddVoterToCensus (Voter v) {
		census.put(v.id, false);
	}
	
	public void MarkAsAlreadyVoted (String id) {
		census.put(id, true);
	}
	
	public boolean HasVoted (String id) {
		return census.get(id);
	}
	
	public boolean ContainsKey (String userId) {
		return census.containsKey(userId);
	}
}

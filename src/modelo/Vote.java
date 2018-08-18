package modelo;

import java.util.Date;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;

public class Vote {
	public String candidateName;
	public String timeStampValue;
	
	private static final SimpleDateFormat df = new SimpleDateFormat("MM.dd.HH.mm.ss");

	public Vote (String candidateName) {
		this.candidateName = candidateName;
		timeStampValue = df.format(new Date());
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("Candidate"		, candidateName);
		json.put("Timestamp"		, timeStampValue);
		
		return json;
	}
}

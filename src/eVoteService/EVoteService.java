package eVoteService;

import java.util.ArrayList;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eVoteSocketServer.SocketServer;
import modelo.*;

@Path ("eVoteService")
@Singleton
public class EVoteService {
	
	@Context
    private ServletContext context;
	
	public EVoteService () {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path ("GetElectionsForUser")
	public String GetElectionsForUser (@QueryParam(value="u") String userId) throws ParseException {
		SocketServer voteServer = (SocketServer)context.getAttribute("voteServer");		
		JSONArray jsonArray = new JSONArray();

		for (Elections e : voteServer.GetElections()) {
			if (e.census.ContainsKey(userId)) {
				jsonArray.add(e.toJSON());
			}
		}
		
		return jsonArray.toJSONString();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path ("GetAllElections")
	public String GetAllElections () throws ParseException {
		SocketServer voteServer = (SocketServer)context.getAttribute("voteServer");		

		JSONArray jsonArray = new JSONArray();

		for (Elections e : voteServer.GetElections()) {
			jsonArray.add(e.toJSON());
		}
		
		return jsonArray.toJSONString();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path ("GetCandidatesForElection")
	public String GetCandidatesForElection (@QueryParam(value="e") int electionId) throws ParseException {
		SocketServer voteServer = (SocketServer)context.getAttribute("voteServer");		

		JSONArray jsonArray = new JSONArray();

		for (Elections e : voteServer.GetElections()) {
			if (e.id == electionId) {
				for (Candidature c : e.candidates) {
					jsonArray.add(c.toJSON());
				}
				break;
			}
		}
		
		return jsonArray.toJSONString();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path ("GetVoteStatistics")
	public String GetVoteStatistics () {
		SocketServer voteServer = (SocketServer)context.getAttribute("voteServer");		

		JSONArray jsonArray = new JSONArray();

		for (Elections e : voteServer.GetElections()) {
			JSONObject jsonElection = new JSONObject();
			JSONArray jsonVoteArray = new JSONArray();
			for (Vote v : e.voteInfo) {
				jsonVoteArray.add(v.toJSON());
			}
			jsonElection.put("Name", e.name);
			jsonElection.put("Votes", jsonVoteArray.toJSONString());
			jsonArray.add(jsonElection.toJSONString());
		}
		
		return jsonArray.toJSONString();
	}
}

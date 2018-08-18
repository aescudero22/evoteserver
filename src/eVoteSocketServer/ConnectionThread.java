package eVoteSocketServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import modelo.Elections;

public class ConnectionThread extends Thread {
	
	private Socket s;
	private List<Elections> elections;
	
	public ConnectionThread (Socket s, List<Elections> elections) {
		this.s = s;
		this.elections = elections;
	}
	
	@Override
	public void run () {
		SSLSession session = ((SSLSocket) s).getSession();
		try {
			OutputStream socketOS = s.getOutputStream();
			InputStream  socketIS = s.getInputStream();

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socketOS));
			BufferedReader br = new BufferedReader(new InputStreamReader (socketIS));

			String msg 		= "";
			String response 	= "";
			
			String peerName	= GetPeerId(session);

			System.out.println("Reading from socket");

			msg = br.readLine();
			System.out.println(msg);
			
			if (msg.contains("AUTH")) {
				response = peerName;
			} else if (msg.contains("VOTE")) {
				response = GetVoteResponse(peerName, msg);
			}
			
			bw.write(response);
			bw.flush();
			bw.close();
			br.close();
			
			s.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			try {s.close();} catch (IOException ex) {};
		}
	}
	
	private String GetPeerId (SSLSession session) throws SSLPeerUnverifiedException {
		String peerName = session.getPeerPrincipal().getName().split("=")[1];
		System.out.println("User authenticated: " + peerName);
		return peerName;
	}
	
	private String GetVoteResponse (String peerId, String msg) {
		String[] params = msg.split(":");
		int electionId 	= -1;
		int candidateId 	= -1;
		String voterName = "";
		Elections chosenElection = null;
		
		if (params.length != 4) {
			System.out.println("ERROR:El formato del voto es incorrecto.");
			return "ERROR:El formato del voto es incorrecto";
		}
		
		try {
			voterName   = params[1];
			electionId 	= Integer.parseInt(params[2]);
			candidateId 	= Integer.parseInt(params[3]);
		} catch (NumberFormatException e) {
			System.out.println("ERROR:El formato del voto es incorrecto.");
			return "ERROR:El formato del voto es incorrecto";
		}
		
		if (voterName.equals("")) {
			voterName = peerId;
		}
		
		for (Elections e : elections) {
			if (e.id == electionId) {
				chosenElection = e;
				break;
			}
		}
		if (chosenElection == null) {
			System.out.println("ERROR:Las elecciones para las que se ha emitido el voto no están disponibles.");
			return "ERROR:Las elecciones para las que se ha emitido el voto no están disponibles";
		}
		
		if (chosenElection.census.ContainsKey(peerId)) {
			synchronized (chosenElection) {
				if (!chosenElection.census.HasVoted(peerId)) {
					chosenElection.census.MarkAsAlreadyVoted(peerId);
					chosenElection.VoteFor(candidateId);
				} else {
					return "ERROR:El votante ya ha votado para estas elecciones";
				}
			}
			return "OK";
		} else {
			System.out.println("ERROR:El censo no contiene a ese votante.");
			return "ERROR:El censo no contiene a ese votante";
		}
	}
}

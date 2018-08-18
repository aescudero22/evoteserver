package eVoteService;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.ws.rs.core.Context;

import eVoteSocketServer.SocketServer;
import modelo.*;

public class EVoteInitializer implements javax.servlet.ServletContextListener{

	public static ArrayList<Elections> elecciones;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
				
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		elecciones = new ArrayList<Elections>();
		SocketServer voteServer = new SocketServer(elecciones);
		arg0.getServletContext().setAttribute("voteServer", voteServer);		
		
		String resPath = arg0.getServletContext().getContextPath() + "/Resources/";
		
		Elections e = new Elections ("Municipales", "Elecciones municipales de Garrido, donde el clan \"Defend Garrido\" buscará prolongar su mandato para los próximos 50 años");
		
		
		e.AddCandidature(new Candidature("Defend Garrido", "La apuesta del pueblo para el pueblo", "www.defendgarrido.es", ""));
		e.AddCandidature(new Candidature("Los pijos de Garrido", "Cuidao", "www.alvarolitri.es", ""));

		
		e.AddVoter(new Voter ("perico", "", "device1"));
		e.AddVoter(new Voter ("perico2", "", "device2"));
				
		elecciones.add(e);
		
		e = new Elections ("Municipales de Garrido (Salamanca)", "Elecciones municipales de Garrido, donde el clan \"Defend Garrido\" buscará prolongar su mandato para los próximos 50 años");
		
		
		e.AddCandidature(new Candidature("Defend Garrido", "La apuesta del pueblo para el pueblo", "www.defendgarrido.es", ""));
		e.AddCandidature(new Candidature("Los pijos de Garrido", "Cuidao", "www.alvarolitri.es", ""));

		
		e.AddVoter(new Voter ("perico", "", "device1"));
		e.AddVoter(new Voter ("perico2", "", "device2"));
		
		elecciones.add(e);
		
		e = new Elections ("Generales2", "Elecciones generales de ESPAÑA.\nCiudadanos está que arde baby, hemos vuelto a la política to crazy. \nAlbert Rivera, el presi sexy, le ama toda Cataluña como a Messi.\nOs manda besis, está muy busy preparando la campaña pa sacarnos de la crisis.");		
		
		e.AddCandidature(new Candidature("PP", "España España España España España España España España España España España España España España España España España va bien España va bien España va bien España va bien España va bien España va bien España va bien", "www.pp.es", ""));
		e.AddCandidature(new Candidature("Ciudadanos", "Cuidao", "www.ciudadanos-cs.org", "Logo_del_pp.png"));

		e.AddVoter(new Voter ("perico2", "", "device1"));
		e.AddVoter(new Voter ("perico3", "", "device3"));
		
		elecciones.add(e);
		
		e = new Elections ("Generales3", "Elecciones generales de ESPAÑA.\nCiudadanos está que arde baby, hemos vuelto a la política to crazy. \nAlbert Rivera, el presi sexy, le ama toda Cataluña como a Messi.\nOs manda besis, está muy busy preparando la campaña pa sacarnos de la crisis.");		
		
		e.AddCandidature(new Candidature("PP", "España va bien", "www.pp.es", ""));
		e.AddCandidature(new Candidature("Ciudadanos", "Cuidao", "www.ciudadanos-cs.org", "Logo_del_pp.png"));

		e.AddVoter(new Voter ("perico2", "", "device1"));
		e.AddVoter(new Voter ("perico3", "", "device3"));
		
		elecciones.add(e);
		
		voteServer.start();
	}

}

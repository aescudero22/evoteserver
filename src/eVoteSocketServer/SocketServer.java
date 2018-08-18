package eVoteSocketServer;
import java.net.*;

import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.SecureRandom;
import java.security.Security;
import java.io.*;
import java.math.BigInteger;

import javax.net.ServerSocketFactory;
import javax.net.ssl.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import modelo.Elections;


public class SocketServer extends Thread {
	ArrayList<Elections> elections;

	public SocketServer(	ArrayList<Elections> elections) {
		this.elections = elections;
	}


	@Override
	public void run () {
		
		//Security.addProvider(new BouncyCastleProvider());

		try {
			
			InputStream certStream = new FileInputStream ("/Users/alberto/Desktop/VotoCerts/CA.jks");
	           
			InputStream keyStream = new FileInputStream ("/Users/alberto/Desktop/VotoCerts/server.p12");
			
			KeyStore trustStore = KeyStore.getInstance("JKS");
			trustStore.load(certStream, ("rootCA123").toCharArray());
			
			KeyStore privateStore = KeyStore.getInstance("PKCS12");
			privateStore.load(keyStream, ("server").toCharArray());
            
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(privateStore, ("server").toCharArray());
			
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		    SSLServerSocketFactory ssf = (SSLServerSocketFactory) sc.getServerSocketFactory();
		    SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(8099);
		    
		    ss.setNeedClientAuth(true);

		    while (true) {
		    		System.out.println("Awaiting connection");
		    		ConnectionThread t = new ConnectionThread (ss.accept(), elections);
		    		t.start();
		    }
	    } catch (Exception e) {
	    		e.printStackTrace();
	    }
	    
//		try
//		{
//			TrustManager[] trustAllCerts = new TrustManager[] {new trustAllManager()};
//			SSLContext context = SSLContext.getInstance("SSL");
//			
//			
//			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//			ks.load(new FileInputStream ("/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home/jre/lib/security/cacerts"), new String ("changeit").toCharArray());
//			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//			kmf.init(ks, new String("").toCharArray());
//			
//			KeyManager[] keyManager = kmf.getKeyManagers();
//			
//			context.init(keyManager, trustAllCerts, null);
//			
//			SSLServerSocketFactory socketFactory = context.getServerSocketFactory();
//			SSLServerSocket socket = (SSLServerSocket) socketFactory.createServerSocket(8099);
//			System.out.println("Servidor a la escucha");
//			while (true) {
//				SSLSocket connectedSocket = (SSLSocket) socket.accept();
//				new PrintWriter (connectedSocket.getOutputStream(), true).println("hola de nuevo");
//				connectedSocket.addHandshakeCompletedListener(this);
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public List<Elections> GetElections () {
		return elections;
	}
}


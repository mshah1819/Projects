package poke.server;

import java.util.ArrayList;

import com.google.protobuf.ByteString;

import domain.Image;
import domain.User;

import poke.resources.Event;
import poke.resources.HibernateUtil;
import poke.server.management.ManagementQueue;
import util.JPAManager;

import eye.Comm.Request;

public class DBInteraction {

	private static DBInteraction searchDBInstance;

	// public static HashMap<String, Boolean> heartBeatLog = new HashMap<String,
	// Boolean>();
	private DBInteraction() {

	}

	public static synchronized DBInteraction getInstance() {
		if (null == searchDBInstance) {
			// System.out.println("Creating a new instance of the HeartBeatLog");
			searchDBInstance = new DBInteraction();
		}
		// System.out.println("Returning instance with hashcode::"+heartBeatLogInstance.hashCode());
		return searchDBInstance;
	}

//	public boolean canProcessRequest(String nodeId) {
//		// System.out.println("NODE.ID::::::::::::"+nodeId);
//		boolean canProcess = false;
//		if (nodeId.equals("1")) {
//			canProcess = false;
//		} else if (nodeId.equals("2")) {
//			canProcess = false;
//		} else if (nodeId.equals("3")) {
//			canProcess = false;
//		} else if (nodeId.equals("4")) {
//			canProcess = true;
//		}
//		return canProcess;
//	}

	public boolean canProcessRequest(Request req) {

		JPAManager mgr = new JPAManager(Server.configPath);
		
		// Add user Request
		if (req.getBody().getOperation().getAddUser().isInitialized()) {
			System.out.println("Request to ADD USER!!!");
			mgr.addUser(req.getBody().getOperation().getAddUser().getUserName());
			return true;
			
			
		// Add image request
		} else if (req.getBody().getOperation().getAddImage().isInitialized()) {
			System.out.println("Request to ADD IMAGE!!!");	
			User user = new User();
			user.setUserId(req.getBody().getOperation().getAddImage().getUser()
					.getUserId());
			System.out.println("ADDING image for user::"+user.getUserId());
			
			mgr.addImage(user, req.getBody().getOperation().getAddImage()
					.getImage().toByteArray());
			return true;
			
			
		// Add image request
		} else if (req.getBody().getOperation().getRetrieveImage().isInitialized()) { 
			System.out.println("Request to RETRIEVE IMAGE!!!");	
			User user = new User();
			user.setUserId(req.getBody().getOperation().getAddImage().getUser()
					.getUserId());
			System.out.println("RETRIEVING images for user::"+user.getUserId());
			
			ArrayList<Image> arrImage = mgr.getImage(user);
			return true;
		} else {
			return true;
		}
		
		
	}

}

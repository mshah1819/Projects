package util;
 
import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtil {
 
    private static final SessionFactory sessionFactory = null;
 
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration cfg = new Configuration();
        	cfg.addAnnotatedClass(domain.User.class);
        	cfg.addAnnotatedClass(domain.Image.class);
        	
        	return cfg.configure(new File(JPAManager.configPath)).buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory()
    { 
    	if(sessionFactory == null)
    	{
    		return buildSessionFactory();
    	} else {
    		return sessionFactory;
    	}
    	
    	
    }
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
 
}
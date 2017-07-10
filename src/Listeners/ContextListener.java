package Listeners;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.api.client.json.JsonFactory;

import database.AllConnections;
import defPackage.MyDrive;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
	
	public static final String CONNECTION_ATTRIBUTE_NAME = "connection";
    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
        AllConnections connection = new AllConnections();
        arg0.getServletContext().setAttribute(CONNECTION_ATTRIBUTE_NAME, connection);
        
        MyDrive drive = null;
		try {
			drive = new MyDrive();
		} catch (IOException e) {
			e.printStackTrace();
		}
        arg0.getServletContext().setAttribute("drive", drive);
    }
}

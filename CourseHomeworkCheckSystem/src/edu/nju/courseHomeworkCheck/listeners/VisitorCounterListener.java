package edu.nju.courseHomeworkCheck.listeners;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class VisitorCounterListener
 *
 */
@WebListener
public class VisitorCounterListener implements ServletContextListener, ServletContextAttributeListener,
			HttpSessionListener{
	ServletContext application;
	int counter;
	int totalCounter;
	int onlineCounter;
//	String counterFilePath = "E:\\work\\git\\courseHomeworkCheckSystem\\CourseHomeworkCheckSystem\\WebContent\\counter.txt";
	String counterFilePath = "E:\\zhangyi\\1zy13\\Git\\courseHomeworkCheckSystem\\CourseHomeworkCheckSystem\\WebContent\\counter.txt";
	/**
     * Default constructor. 
     */
    public VisitorCounterListener() {
    	
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent cse)  { 
    	try {
    		System.out.println("Reading Start");	
    		BufferedReader reader = new BufferedReader(new FileReader(counterFilePath));
    		counter = Integer.parseInt( reader.readLine());
    		totalCounter = Integer.parseInt( reader.readLine());
    		onlineCounter = Integer.parseInt( reader.readLine());
    		reader.close();
//    		System.out.println("Reading " + totalCounter);
    	}
    	catch (Exception e) {
    		System.out.println(e.toString());
    	}
    	application= cse.getServletContext();
    	application.setAttribute("webCounter", Integer.toString(counter));
    	application.setAttribute("totalCounter", Integer.toString(totalCounter));
    	application.setAttribute("onlineCounter", Integer.toString(onlineCounter));
    	application.setAttribute("allvisitIP", new ArrayList<String>());
    	System.out.println("Application initialized");
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0)  { 
    	System.out.println("ServletContextattribute added");
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
    	System.out.println("ServletContextattribute removed");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent scae)  { 
    	System.out.println("Application shut down");
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent scae)  { 
    	System.out.println("ServletContextattribute replaced");
    	writeCounter(scae);
    }

    synchronized void writeCounter(ServletContextAttributeEvent scae) {
    	ServletContext servletContext= scae.getServletContext();
    	counter = Integer.parseInt((String) servletContext.getAttribute("webCounter"));
    	totalCounter = Integer.parseInt((String) servletContext.getAttribute("totalCounter"));
    	onlineCounter = Integer.parseInt((String) servletContext.getAttribute("onlineCounter"));

    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
    		writer.write(Integer.toString(counter)+"\n");
    		writer.write(Integer.toString(totalCounter)+"\n");
    		writer.write(Integer.toString(onlineCounter));
    		writer.close();
    		System.out.println("Writing");
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    }

	public void sessionCreated(HttpSessionEvent arg0) {
		counter++;
		application.setAttribute("webCounter", Integer.toString(counter));
		onlineCounter++;
		application.setAttribute("onlineCounter", Integer.toString(onlineCounter));
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		onlineCounter--;
		application.setAttribute("onlineCounter", Integer.toString(onlineCounter));
	}
}

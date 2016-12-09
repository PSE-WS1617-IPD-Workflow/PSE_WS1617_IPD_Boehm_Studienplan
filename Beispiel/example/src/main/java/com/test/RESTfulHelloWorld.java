package com.test;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.test.model.Book;

@Path("/hello")
public class RESTfulHelloWorld {	
	
	@GET
	@Produces("text/html")
	public Response getStartingPage(
			@DefaultValue("kartoffel") @QueryParam("gemuese") String vegetable,
			@DefaultValue("42") @QueryParam("number") int number) {
		String output = "<h1>Hello World!<h1>" +
				"<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p><br>";
		output += "gemuese="+vegetable+"<br>";
		output += "number="+number+"</p>";
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
        List<Book> resultList = session.createQuery("from Book", Book.class).getResultList();
        session.getTransaction().commit();
        output +="<table border=\"1\">";
        for (Book book : resultList) {
        	output+="<tr><td>"+book.getId()+"</td>";
        	output+="<td>"+book.getTitle()+"</td>";
        	output+="<td>"+book.getAuthor()+"</td>";
        	output+="<td>"+book.getPrice()+"</td>";
        	output+="<td>"+book.getQuantity()+"</td></tr>";
        }
        output +="</table>";
		return Response.status(200).entity(output).build();
	}
}
package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import org.example.model.Audio;

import com.google.gson.Gson;
import com.google.gson.JsonElement;



@WebServlet(name = "audio", value = "audio")
public class AudioServlet extends HttpServlet {
	
	ConcurrentHashMap<String, Audio> audioDb = new ConcurrentHashMap<>();
	
	@Override
	 public void init() throws ServletException {
		Audio audio1 = new Audio("id_1","artist_1","track_title_1","album_title_1",10,2001,101,100);
		Audio audio2 = new Audio("id_2","artist_2","track_title_2","album_title_2",20,2002,102,200);
		Audio audio3 = new Audio("id_3","artist_3","track_title_3","album_title_3",30,2003,103,300);
		Audio audio4 = new Audio("id_4","artist_4","track_title_4","album_title_4",40,2004,104,400);
		Audio audio5 = new Audio("id_5","artist_5","track_title_5","album_title_5",50,2005,105,500);
		Audio audio6 = new Audio("id_6","artist_6","track_title_6","album_title_6",60,2006,106,600);
		 
		audioDb.put("artist_1", audio1);
		audioDb.put("artist_2", audio2);
		audioDb.put("artist_3", audio3);
		audioDb.put("artist_4", audio4);
		audioDb.put("artist_5", audio5);
		audioDb.put("artist_6", audio6);
		 
	 }
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		String artistName = request.getParameter("artistName");
		Audio ai = audioDb.get(artistName);
		
	
	   Gson gson = new Gson();
	   JsonElement element = gson.toJsonTree(audioDb);
	    
		PrintWriter out = response.getWriter();
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    out.println("GET RESPONSE IN JSON - single element: " + gson.toJson(ai));
	    
	   out.println("GET RESPONSE IN JSON - all elements " + element.toString());
	        
	   out.flush();
	
	}
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       
		   	String id = request.getParameter("id");
		    String artistName = request.getParameter("artistName");
		    String trackTitle = request.getParameter("trackTitle");
		    String albumTitle = request.getParameter("albumTitle");
		    String trackNumber = request.getParameter("trackNumber");
		    int intTrackNumber = Integer.parseInt(trackNumber);
		    String year = request.getParameter("year");
		    int intYear = Integer.parseInt(year);
		    String numberOfReviews = request.getParameter("numberOfReviews");
		    int intNumberOfReviews = Integer.parseInt(numberOfReviews);
		    String copiesSold = request.getParameter("copiesSold");
		    int intCopiesSold = Integer.parseInt(copiesSold);
		        
		    Audio ai = new Audio();
		    ai.setId(id);
		    ai.setArtistName(artistName);
		    ai.setTrackTitle(trackTitle);
		    ai.setAlbumTitle(albumTitle);
		    ai.setTrackNumber(intTrackNumber);
		    ai.setYear(intYear);
		    ai.setNumberOfReviews(intNumberOfReviews);
		    ai.setCopiesSold(intCopiesSold);
	        
	        
	        audioDb.put(artistName, ai);
	        response.setStatus(200);
	    	
	    	response.getOutputStream().println("POST RESPONSE: Audio Item of " + artistName + " is added to the database.");
	    }

}

package org.example.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpResponse;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.example.model.Artist;
import java.util.Random;


class WebServletTest {

	//@Test
	void testHelloServletGet() throws Exception {
		
		HttpClient client = new HttpClient();
        client.start();

        ContentResponse res = client.GET("http://localhost:9090/coen6317/HelloServlet");
        
        System.out.println(res.getContentAsString());
        
        client.stop();
		
	}
	
	
	//@Test
	void testBlockingServletGet() throws Exception {
		
		HttpClient client = new HttpClient();
        client.start();

        ContentResponse res = client.GET("http://localhost:9090/coen6317/BlockingServlet");
        
        System.out.println(res.getContentAsString());
        
        client.stop();
		
	}
	
	//@Test
	void testAsyncServletGet() throws Exception {
		
		String url = "http://localhost:9090/coen6317/longtask";
		HttpClient client = new HttpClient();
        client.start();

        ContentResponse response = client.GET(url);

		assertThat(response.getStatus(), equalTo(200));
		
		String responseContent = IOUtils.toString(response.getContent());
		
		 System.out.println(responseContent);
		//assertThat(responseContent, equalTo( "This is some heavy resource that will be served in an async way"));
		
	}

	
	@Test
	void testArtistsGet() throws Exception {
		String url = "http://localhost:9090/coen6317/artists";
		HttpClient client = new HttpClient();
        client.start();

        Request request = client.newRequest(url);
        request.param("id","id200");
        ContentResponse response = request.send();
   

		assertThat(response.getStatus(), equalTo(200));
		
		String responseContent = IOUtils.toString(response.getContent());
		
		 System.out.println(responseContent);
		client.stop();
		
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Test
	void testArtistsPost() throws Exception {
		
		String url = "http://localhost:9090/coen6317/artists";
		HttpClient client = new HttpClient();
        client.start();
        
        Request request = client.POST(url);
        
        request.param("id","id200");
        request.param("name","artist200");
        
        ContentResponse response = request.send();
		String res = new String(response.getContent());
		System.out.println(res);
		client.stop();
	}
	
	
	
	@Test
	void testAudioPost() throws Exception {
		
		String url = "http://localhost:9090/coen6317/audio";
		HttpClient client = new HttpClient();
        client.start();
      
        for (int i = 0; i < 10; i++) {
        	
        	
            Thread thread = new Thread(() -> {
                try {
                	Random random = new Random();
                    int id = random.nextInt(10) + 1;
                	Request request = client.POST(url);
                    
                    request.param("id", "id_"+(id+10));
                    request.param("artistName","artist_"+(id+10));
                    request.param("trackTitle","track_title_"+(id+10));
                    request.param("albumTitle","album_title_"+(id+10));
                    request.param("trackNumber",Integer.toString(id));
                    request.param("year","2100");
                    request.param("numberOfReviews",Integer.toString(id));
                    request.param("copiesSold",Integer.toString(id));
                    
                    
                    ContentResponse response = request.send();
            		String res = new String(response.getContent());
            		System.out.println(res);
            		//client.stop();
                	
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
             
        }     
        
        
	}
	
	

	
	
	@Test
	void testAudioGet() throws Exception {
		String url = "http://localhost:9090/coen6317/audio";
		HttpClient client = new HttpClient();
        client.start();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                	Request request = client.newRequest(url);
                    request.param("artistName","artist_1");
                    ContentResponse response = request.send();
                    assertThat(response.getStatus(), equalTo(200));
                    String responseContent = IOUtils.toString(response.getContent());
                    System.out.println(responseContent);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
             
        }        	
       
	}
	
	
	

	
	
	
}




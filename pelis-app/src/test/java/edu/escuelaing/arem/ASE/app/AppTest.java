package edu.escuelaing.arem.ASE.app;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public void testApiConnection() {
    try {
        String movieInfo = HttpConnection.getMovie("ted");
        assertNotNull(movieInfo);
        
    } catch (IOException e) {
        fail("IOException occurred during API connection");
    }
}
public void testCache() {
    try {
        String movieInfo1 = Cache.inMemory("Titanic");
        assertNotNull(movieInfo1);

        String movieInfo2 = Cache.inMemory("Titanic");
        assertNotNull(movieInfo2);

        
        assertEquals(movieInfo1, movieInfo2);
    } catch (IOException e) {
        fail("IOException occurred during cache test");
    }
}
public void testBuildHtmlFromJson() {
    String jsonExample = "{\"Title\":\"The Shawshank Redemption\",\"Year\":\"1994\",\"Genre\":\"Drama\",\"Director\":\"Frank Darabont\"}";
    String htmlResult = Cache.buildHtmlFromJson(jsonExample);
    
    assertTrue(htmlResult.contains("<strong>Title:</strong> The Shawshank Redemption<br>"));
    assertTrue(htmlResult.contains("<strong>Year:</strong> 1994<br>"));
    assertTrue(htmlResult.contains("<strong>Genre:</strong> Drama<br>"));
    assertTrue(htmlResult.contains("<strong>Director:</strong> Frank Darabont<br>"));
    
}
public void testBuildHtmlFromJson2() {
    String jsonExample = "{\"Title\":\"The Godfather\",\"Year\":\"1972\",\"Genre\":\"Crime, Drama\",\"Director\":\"Francis Ford Coppola\"}";
    String htmlResult = Cache.buildHtmlFromJson(jsonExample);
    
    assertTrue(htmlResult.contains("<strong>Title:</strong> The Godfather<br>"));
    assertTrue(htmlResult.contains("<strong>Year:</strong> 1972<br>"));
    assertTrue(htmlResult.contains("<strong>Genre:</strong> Crime, Drama<br>"));
    assertTrue(htmlResult.contains("<strong>Director:</strong> Francis Ford Coppola<br>"));

}
public void testBuildHtmlFromJson3() {
    String jsonExample = "{\"Title\":\"Pulp Fiction\",\"Year\":\"1994\",\"Genre\":\"Crime, Drama\",\"Director\":\"Quentin Tarantino\",\"Actors\":\"John Travolta, Uma Thurman, Samuel L. Jackson\"}";
    String htmlResult = Cache.buildHtmlFromJson(jsonExample);
    
    assertTrue(htmlResult.contains("<strong>Title:</strong> Pulp Fiction<br>"));
    assertTrue(htmlResult.contains("<strong>Year:</strong> 1994<br>"));
    assertTrue(htmlResult.contains("<strong>Genre:</strong> Crime, Drama<br>"));
    assertTrue(htmlResult.contains("<strong>Director:</strong> Quentin Tarantino<br>"));
    assertTrue(htmlResult.contains("<strong>Actors:</strong> John Travolta, Uma Thurman, Samuel L. Jackson<br>"));
  
}

}

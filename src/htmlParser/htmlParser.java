package htmlParser;

import java.io.*;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.*;

public class htmlParser {
	
	public static String htmlString;
	
	public static void main(String[] args) {
		
		Scanner scanner=new Scanner(System.in);
		
		System.out.print("Please enter the html file name:");
		String filename=scanner.next();
		
		
		htmlParser htmlparser = new htmlParser();
		
		ArrayList<String> captialArray = new ArrayList();
		
		ArrayList<String> stateArray = new ArrayList();
		
		//get the html content from the html file and convert it into string
		
		htmlString = htmlparser.getHtmlContent("src/"+filename);
		
		
		
		
		//get the captial array from the htmlString
		captialArray = htmlparser.getCapital(htmlString);
		
		//get the state array from the htmlString
		stateArray = htmlparser.getState(htmlString);
		
		//generate JSON file and save it to the local repository
		JsonFactory jsonFactory = new JsonFactory();
		try {
			
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(new File("Output/result.json"), JsonEncoding.UTF8);
			
			// start generate JSON 
			jsonGenerator.writeStartObject();
			
			// start the capitals field
			jsonGenerator.writeArrayFieldStart("capitals");
			
			// captialArray should have the same size as stateArray
			int numOfCapital = 0;
			int numOfState = 0;
			for(numOfCapital = 0; numOfCapital < captialArray.size(); numOfCapital++){
				numOfState = numOfCapital;
				jsonGenerator.writeStartObject();
				jsonGenerator.writeStringField("capital", captialArray.get(numOfCapital));
				jsonGenerator.writeStringField("state", stateArray.get(numOfState));
				jsonGenerator.writeEndObject();
			}
			// end the capitals field
			jsonGenerator.writeEndArray();
			
			// start the summary object
			
			jsonGenerator.writeObjectFieldStart("summary");
			jsonGenerator.writeNumberField("numberOfCapitals", captialArray.size());
			
			// end the object
			jsonGenerator.writeEndObject();
			// close the JSON
	        jsonGenerator.close();  
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	// this function is used to get the HTML content from the local repository
	public String getHtmlContent(String filePath) {  
	    String temp;  
	    BufferedReader br;  
	    StringBuffer stringBuffer = new StringBuffer();  
	    try {  
	        br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));  
	        while ((temp = br.readLine()) != null) {  
	        	stringBuffer.append(temp);  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return stringBuffer.toString();  
	}  
	
	public ArrayList<String> getCapital(String htmlString){
		//initiate string array and result
		ArrayList<String> resultArray = new ArrayList();
		String result=new String();
		
		//initiate regular expression
		Pattern pattern_capital = Pattern.compile("capital'>(.+?)<\\/strong>");	
		Matcher matcher_capital = pattern_capital.matcher(htmlString);
		Pattern pattern_space = Pattern.compile("\\w+");	
	    Matcher matcher_space;
	    
		while(matcher_capital.find()) {  
		     result = matcher_capital.group(1);
		     matcher_space = pattern_space.matcher(result);
		     if(matcher_space.find()){
		    	 result = matcher_space.group(0);
		     }
		     System.out.println(result);
		     resultArray.add(result);
		} 
		
		return resultArray;
		
	}
	
	public ArrayList<String> getState(String htmlString){
		//initiate regular expression
		ArrayList<String> resultArray = new ArrayList();
		String result=new String();
		
		Pattern pattern_state = Pattern.compile("state'>(.+?)<\\/span>");	
		Matcher matcher_state = pattern_state.matcher(htmlString);
		Pattern pattern_space = Pattern.compile("\\w+");	
	    Matcher matcher_space;
	    
		while(matcher_state.find()) {  
		     result = matcher_state.group(1);
		     matcher_space = pattern_space.matcher(result);
		     if(matcher_space.find()){
		    	 result = matcher_space.group(0);
		     }
		     System.out.println(result);
		     resultArray.add(result);
		} 
		
		return resultArray;
		
	}
	
}

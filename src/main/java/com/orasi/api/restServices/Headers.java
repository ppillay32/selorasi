package com.orasi.api.restServices;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.orasi.utils.Randomness;
import com.orasi.utils.TestReporter;

public class Headers {
	/**
	 * Used in coordination with RestService.createHeader() <br/>
	 * Enums: <br/>
	 * <b>AUTH</b>:<br/>
	 *          Content-type: application/x-www-form-urlencoded<br/><br/>
	 * <b>BASIC_CONVO</b>:<br/> 
	 * 			Content-type: application/json;charset=utf-8 <br/>
	 * 			Accept: application/json <br/>
	 * 			username: test.user <br/>
	 *     		messageId: Random Alphanumic string <br/>
	 *     		Connection: keep-alive <br/>
	 *     		ConversationId:  Random Alphanumic string  <br/>
	 *     		requestedTimestamp: Current timestamp<br/><br/>
	 * <b>JSON</b>:<br/>
	 * 			Content-type: application/json<br/>    
	 *
	 */
	public static enum HeaderType {
	  	AUTH,
	    BASIC_CONVO,
	    JSON;
	}
	
	  /**
	   * Automatically populates headers based on predefined options from RestService.HeaderType
	   * @param type Uses options from RestService.HeaderType enum
	   */
	  public static Header[] createHeader(HeaderType type)  {
		  Header[] headers = null;
	        switch(type) {		    
		        case AUTH:		        	
        			TestReporter.logTrace("Creating headers for [AUTH]");
		        	headers= new Header[] {
		    	   		     new BasicHeader("Content-type", "application/x-www-form-urlencoded")};
		        	break;
		        case BASIC_CONVO:
        			TestReporter.logDebug("Creating headers for [BASIC_CONVO]");
		        	headers = new Header[] {
		    	   		     new BasicHeader("Content-type", "application/json;charset=utf-8")
		    	   		    ,new BasicHeader("Accept", "application/json")
		    	   		    ,new BasicHeader("username", "test.user")
		    	   		    ,new BasicHeader("messageId", Randomness.generateMessageId())
		    	   		    ,new BasicHeader("Connection", "keep-alive")
		    	   		    ,new BasicHeader("requestedTimestamp", Randomness.generateCurrentXMLDatetime() + ".000-04:00")
		    	   		};
		        	break;   
		        case JSON:
	        			TestReporter.logTrace("Creating headers for [JSON]");
			        	headers = new Header[] {
			    	   		     new BasicHeader("Content-type", "application/json")
			    	   		    
			    	   		};
			        	break;
	            default:
	                break;
	        }
	        
	        //Logging headers
	        String allHeaders = "";
	    	for (Header header : headers){
	    		allHeaders += "[" +header.getName() + ": " + header.getValue()+"] ";
	    	}
			TestReporter.logTrace("Headers added " + allHeaders);
			
			return headers;
	    }
}
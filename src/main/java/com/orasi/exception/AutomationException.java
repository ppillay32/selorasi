package com.orasi.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.orasi.utils.OrasiDriver;

@SuppressWarnings("deprecation")
public class AutomationException extends RuntimeException{

    public static final String SESSION_ID = "Session ID";
    public static final String DRIVER_INFO = "Driver info";
    public WebDriver driver = null;

    private Map<String, String> extraInfo = new HashMap<>();
    private static final long serialVersionUID = -8710980695994382082L;

    public AutomationException(){
	super("Automation Error");
    }

    public AutomationException(String message){
	super("Automation Error: " + message);
    }

    public AutomationException(String message, WebDriver driver){
	super("Automation Error: " + message);
	this.driver = driver;
    }
    public AutomationException(String message, OrasiDriver driver){
	super("Automation Error: " + message);
	this.driver = driver.getWebDriver();
    }

    public AutomationException(String message, Throwable cause){
	super("Automation Error: " + message, cause);
    }
    @Override
    public String getMessage() {
	return createMessage(super.getMessage());
    }

    private String createMessage(String originalMessageString) {
	String supportMessage = getSupportUrl() == null ?
		"" : "For documentation on this error, please visit: " + getSupportUrl() + "\n";

	return (originalMessageString == null ? "" : originalMessageString + "\n")
		+ supportMessage
		+ getBuildInformation() + "\n"
		+ getDriverInfo(driver) + "\n"
		+ getGridInfo(driver) ; 
    }

    public String getSystemInformation() {
	String host = "N/A";
	String ip   = "N/A";

	try{
	    host = InetAddress.getLocalHost().getHostName();
	    ip   = InetAddress.getLocalHost().getHostAddress();
	} catch (UnknownHostException throw_away) {}

	return String.format("System info: host: '%s', ip: '%s', os.name: '%s', os.arch: '%s', os.version: '%s', java.version: '%s'",
		host,
		ip,
		System.getProperty("os.name"),
		System.getProperty("os.arch"),
		System.getProperty("os.version"),
		System.getProperty("java.version"));
    }

    public String getSupportUrl() {
	return null;
    }

    public BuildInfo getBuildInformation() {
	return new BuildInfo();
    }

    public static String getDriverName(StackTraceElement[] stackTraceElements) {
	String driverName = "unknown";
	for (StackTraceElement e : stackTraceElements) {
	    if (e.getClassName().endsWith("Driver")) {
		String[] bits = e.getClassName().split("\\.");
		driverName = bits[bits.length - 1];
	    }
	}

	return driverName;
    }

    public String getDriverInfo(WebDriver driver){
	String info = "N/A";
	String browserName = "N/A";
	String browserVersion= "N/A";
	String osInfo = "N/A";
	String sessionId = "N/A";

	if(driver instanceof OrasiDriver)  driver = ((OrasiDriver)driver).getWebDriver();

	if (driver != null){
	    browserName = browserName(driver);
	    browserVersion = browserVersion(driver);
	    osInfo = platformOS(driver);
	    sessionId = sessionId(driver);
	}
	info =  String.format("Browser info: name: '%s', version: '%s', os.info: '%s', driver session id: '%s'", 
		browserName,
		browserVersion,
		osInfo,
		sessionId).trim();	
	return info;
    }

    public void addInfo(String key, String value) {
	extraInfo.put(key, value);
    }

    public String getAdditionalInformation() {
	if (! extraInfo.containsKey(DRIVER_INFO)) {
	    extraInfo.put(DRIVER_INFO, "driver.version: " + getDriverName(getStackTrace()));
	}

	String result = "";
	for (Map.Entry<String, String> entry : extraInfo.entrySet()) {
	    if (entry.getValue() != null && entry.getValue().startsWith(entry.getKey())) {
		result += "\n" + entry.getValue();
	    } else {
		result += "\n" + entry.getKey() + ": " + entry.getValue();
	    }
	}
	return result;
    }


    private String browserName(WebDriver driver) {
	if(driver instanceof HtmlUnitDriver) return "Html Unit";
	return ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
    }

    private String browserVersion(WebDriver driver) {
	if(driver instanceof HtmlUnitDriver) return "N/A";
	return ((RemoteWebDriver) driver).getCapabilities().getVersion();
    }

    private String sessionId(WebDriver driver) {
	if(driver instanceof HtmlUnitDriver) return "N/A";
	String sessionId = ((RemoteWebDriver) driver).getSessionId() != null ? ((RemoteWebDriver) driver).getSessionId().toString().trim(): "N/A";
	return sessionId;
    }

    private String platformOS(WebDriver driver) {
	if(driver instanceof HtmlUnitDriver) {
	    return "N/A";
	}
	return ((RemoteWebDriver) driver).getCapabilities().getPlatform().name() + " "
	+ ((RemoteWebDriver) driver).getCapabilities().getPlatform().getMajorVersion() + "."
	+ ((RemoteWebDriver) driver).getCapabilities().getPlatform().getMinorVersion();
    }


    public String getGridInfo(WebDriver driver){
	String info = "Selenium Node info: N/A";
	String[] gridNodeInfo =null;

	if (driver != null){
	    gridNodeInfo = getHostNameAndPort(driver);

	    info =  String.format("Selenium Node info: hostname: '%s', port: '%s', session id: '%s'", 
		    gridNodeInfo[0], 
		    gridNodeInfo[1], 
		    gridNodeInfo[2] == "N/A" ? sessionId(driver) : gridNodeInfo[2]).trim();	
	}
	return info;
    }

    private String[] getHostNameAndPort(WebDriver driver) {
	String[] hostAndPort = new String[3];
	String sessionId = "N/A";

	if(driver == null) return new String[]{"N/A", "N/A", "N/A"};
	DefaultHttpClient client = null;
	try {
	    String hostName = (((HttpCommandExecutor)((RemoteWebDriver)driver).getCommandExecutor()).getAddressOfRemoteServer().getHost());
	    int port =4444;
	    HttpHost host = new HttpHost(hostName, port);
	    client = new DefaultHttpClient();
	    sessionId = sessionId(driver);
	    URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session=" + sessionId);
	    BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST", sessionURL.toExternalForm());
	    HttpResponse response = client.execute(host, r);
	    JSONObject object = extractObject(response);
	    URL myURL = new URL(object.getString("proxyId"));
	    if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
		hostAndPort[0] = myURL.getHost();
		hostAndPort[1] = Integer.toString(myURL.getPort());
		hostAndPort[2] = sessionId;
	    }
	} catch (Exception throw_away) { return new String[]{"N/A", "N/A", "N/A"};}
	finally{client.close();}
	return hostAndPort;
    }

    private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
	BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
	StringBuffer s = new StringBuffer();
	String line;
	while ((line = rd.readLine()) != null) {
	    s.append(line);
	}
	rd.close();
	JSONObject objToReturn = new JSONObject(s.toString());
	return objToReturn;
    }
}

package perpetualTokenClient;

public class RequestClass {
	String token;
	String operation;
	String apiUrl;
	String requestXML;
	
	public String getToken() {
		return token;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public String getApiUrl() {
		return apiUrl;
	}
	
	public String getRequestXML() {
		return requestXML;
	}
	
	public void setToken(String token) {
		this.token=token;
	}
	
	public void setOperation(String operation) {
		this.operation=operation;
	}
	
	public void setApiUrl(String apiUrl) {
		this.apiUrl=apiUrl;
	}
	
	public void setRequestXML(String requestXML) {
		this.requestXML=requestXML;
	}
	
	public RequestClass(String token, String operation, String apiUrl, String requestXML) {
		this.token=token;
		this.operation=operation;
		this.apiUrl=apiUrl;
		this.requestXML=requestXML;
	}
	
	public RequestClass() {
		
	}
}

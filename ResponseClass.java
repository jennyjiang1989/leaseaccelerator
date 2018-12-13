package perpetualTokenClient;

public class ResponseClass {
	 String body;
	 Integer statusCode;
	 public String getBody() {
		 return body;
	 }
	 public Integer getStatusCode() {
		 return statusCode;
	 }
	 public void setBody(String body) {
		 this.body=body;
	 }
	 public void setStatusCode(Integer statusCode) {
		 this.statusCode=statusCode;
	 }
	 public ResponseClass(String body, Integer statusCode) {
		 this.body=body;
		 this.statusCode=statusCode;
	 }
	 public ResponseClass() {
		 
	 }
}

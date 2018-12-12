package perpetualTokenClient;

//import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;//newly added
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
 
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.entity.mime.content.ByteArrayBody;//newly added
//import org.json.simple.JSONObject;//newly added

public class perpetualTokenClient 
{
	private static InputStream in;
	   
    public static void main(String[] args)
    {
    	Integer debug = 1;
             
        if (debug == 1)
        {
            System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
            System.setProperty("org.apache.commons.logging.simplelog.showdatetime","true");
            System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http","DEBUG");
            System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire","DEBUG");
        }
        String token = "gVijK1YarW//cwre/uIy4ZS+ghpXja5AivOSDCIvCKdv1ZmtkL71ITrPOSGzPy0IjG+AQS9IQq6txyJ/dah6ND1GEvoCVXxVHgqMwyLVm3TSZ7P9WNdDfDEwPm3GnPdG+l/E3c+3fxyECT6yNlW7lSWpESn3jj4E+UuPlJ97yCI=";
        String operation = "Generate";
        //String xmlRequestFile = "test.xml";
        String apiUrl = "https://beta.leaseaccelerator.com/lease_accelerator/api/LeaseAccelerator/";
        try
        {
            if (token != null)
            {
                if (debug == 1)
                {
                    System.out.println("Token Data  :" + token);
                }
                BasicHttpClientConnectionManager clientConnectionManager = new BasicHttpClientConnectionManager(getRegistry());
                CloseableHttpClient apiClient = HttpClients.custom().setConnectionManager(clientConnectionManager).build();
                HttpPost httppost = new HttpPost(apiUrl + operation);
               
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addPart("token", new StringBody(token, ContentType.DEFAULT_TEXT));
                builder.addPart("requestId", new StringBody("12345", ContentType.DEFAULT_TEXT));
               
                //File requestFile = new File(xmlRequestFile);
                //builder.addPart("file", new FileBody(requestFile.getAbsoluteFile()));
                String requestXML="<APIRequest><Request><RequestId>1111</RequestId></Request><Payload><Report><ReportName>LedgerExport</ReportName><Format>XML</Format><Parameters><Parameter><Name>P_STARTING_FISCALYEAR</Name><Value>-100</Value></Parameter><Parameter><Name>P_PERIODDATE</Name><Value>-10</Value></Parameter><Parameter><Name>P_LOOKFORWARD</Name><Value>1</Value></Parameter><Parameter><Name>P_DETAILLEVEL</Name><Value>SCHEDULE</Value></Parameter><Parameter><Name>P_CALENDARTYPE</Name><Value /></Parameter><Parameter><Name>P_LEDGER</Name><Value>21</Value></Parameter><Parameter><Name>P_ENTITY</Name><Value>ACME MARKETS INC</Value></Parameter><Parameter><Name>P_EXCLUDENEWENTRIES</Name><Value>N</Value></Parameter><Parameter><Name>P_EXCLUDETRANSFERREDENTRIES</Name><Value>N</Value></Parameter><Parameter><Name>P_EXCLUDEPOSTEDENTRIES</Name><Value>N</Value></Parameter></Parameters></Report></Payload></APIRequest>";
                byte[] requestBytes=requestXML.getBytes(StandardCharsets.UTF_8);
                builder.addPart("file",new ByteArrayBody(requestBytes,"test"));
                
                HttpEntity entity = builder.build();
               
                httppost.setEntity(entity);
 
                try
                {
                    System.out.println(httppost);
                    CloseableHttpResponse responseFromPost = apiClient.execute(httppost);
                              
                    if (debug == 1)
                    {
                        System.out.println(responseFromPost.toString());
                    }
                              
                    in = responseFromPost.getEntity().getContent();
                    String body = IOUtils.toString(in, "UTF-8");
                    System.out.println(body);
                }
                catch (ClientProtocolException e)
                {
                    System.out.println(e);
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }
            }  
                
        }
        catch (Exception nsae)
        {
            System.out.println("NoSuchAlgorithmException in InvokeAPIFromFile: " + nsae.getLocalizedMessage());
        }
        finally
        {
            //end
        }
    }
   
    private static Registry<ConnectionSocketFactory> getRegistry() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = SSLContexts.custom().build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[]{"TLSv1.2"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory)
                .build();
    }

}

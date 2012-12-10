package communicators;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Inspired by
 * http://www.xyzws.com/Javafaq/how-to-use-httpurlconnection-post-data
 * -to-web-server/139
 * 
 * Sends HTTP requests of any type, and returns response.
 * 
 * @author Volodymyr Zavidovych
 * 
 */
public class HttpRequest {
    private static final String BAD_URL_MSG = "Bad URL.";
    private static final String BAD_PARAMS_MSG = "Unsupported encoding of parameters.";
    private static final String FAILED_CONNECTION_MSG = "Couldn't establish connection.";

    private String myRequestParams;
    private String myRequestType;
    private URL myURL;
    private HttpURLConnection myConnection;

    /**
     * Constructor for HTTP request sender.
     * 
     * @param url Target URL
     * @param params Request parameters
     * @param requestType Request type (GET, POST, PUT)
     */
    public HttpRequest (String url, HashMap<String, String> params, String requestType) {
        try {
            myURL = new URL(url);
            myRequestParams = encodeParams(params);
            myRequestType = requestType;
        }
        catch (MalformedURLException e) {
            throw new HttpRequestException(BAD_URL_MSG, e.getCause());
        }
    }

    /**
     * Send specified request.
     * 
     * @return Request response
     */
    public String send () {
        try {
            makeConnection();
            sendRequest();
            return readResponse();
        }
        catch (IOException e) {
            throw new HttpRequestException(FAILED_CONNECTION_MSG, e.getCause());
        }
        finally {
            if (myConnection != null) {
                myConnection.disconnect();
            }
        }
    }

    private void makeConnection () throws IOException {
        myConnection = (HttpURLConnection) myURL.openConnection();
        myConnection.setRequestMethod(myRequestType);
        myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        myConnection.setRequestProperty("Content-Length",
                                        "" + Integer.toString(myRequestParams.getBytes().length));
        myConnection.setRequestProperty("Content-Language", "en-US");
        myConnection.setUseCaches(false);
        myConnection.setDoInput(true);
        myConnection.setDoOutput(true);
    }

    private void sendRequest () throws IOException {
        DataOutputStream wr = new DataOutputStream(myConnection.getOutputStream());
        wr.writeBytes(myRequestParams);
        wr.flush();
        wr.close();
    }

    private String readResponse () throws IOException {
        InputStream is = myConnection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }

    private String encodeParams (Map<String, String> params) {
        String result = "";
        Iterator<Entry<String, String>> it = params.entrySet().iterator();
        try {
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                result += pairs.getKey() + "=" + URLEncoder.encode(pairs.getValue(), "UTF-8") + "&";
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new HttpRequestException(BAD_PARAMS_MSG, e.getCause());
        }
        return result;
    }
}

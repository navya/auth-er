package in.pclub.auth_er;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pallav on 1/13/17.
 */

public class Connection {
    protected static int getStatusCode() {
        HttpURLConnection urlConnection = null;
        String str = null;
        int code = 0;

        try {
            URL url = new URL("http://www.example.com/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            code = urlConnection.getResponseCode();
        } catch (Exception e) {
            Log.w("Exception", e.toString());
        } finally {
            urlConnection.disconnect();
        }
        return code;
    }
}

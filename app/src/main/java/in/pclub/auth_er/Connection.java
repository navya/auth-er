package in.pclub.auth_er;

import android.util.Base64;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by pallav on 1/13/17.
 */

public class Connection {

    private static String locationHeader = null;
    public static String username = null;
    public static String password = null;

    protected static int attempt(String _username, String _password) {
        username = _username;
        password = _password;

        HttpURLConnection urlConnection = null;
        String str = null;
        int code = 0;

        try {
            URL url = new URL("http://93.184.216.34/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.connect();

            State state = Connection.getState(urlConnection);

            try {
                if (state == State.IRONPORT) {
                    Connection.handleIronport();
                } else if (state == State.FORTINET) {
                    Connection.handleFortinet();
                } else if (state == State.ERROR) {
                    Log.w("Error", "Failed to log in");
                }
            } catch (Exception e) {
                Log.w("Exception", "Something went wrong. Please report this as a bug");
                Log.w("Exception", e.toString());
            }

        } catch (Exception e) {
            Log.w("Exception", e.toString());
        } finally {
            urlConnection.disconnect();
        }
        return code;
    }

    private static void handleIronport() throws Exception {
        assert (Connection.locationHeader.contains("ironport"));
        Log.w("Debug", "Logging in to IRONPORT");
        HttpsURLConnection connection = (HttpsURLConnection) new URL(Connection.locationHeader).openConnection();
        String credentials = username + ":" + password;
        String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        connection.setRequestProperty("Authorization", "Basic " + credBase64);
        try {
            connection.connect();
        } catch (Exception e) {
            Log.w("Exception", e.toString());
        } finally {
            Log.d("Debug: ", connection.getResponseMessage());
            Log.d("Debug: ", connection.getResponseCode() + "");
            connection.disconnect();
        }
    }

    private static void handleFortinet() {
        assert (Connection.locationHeader.contains("gateway.iitk.ac.in"));
        Log.w("Debug", "Logging in to Fortinet");
    }

    private static State getState(HttpURLConnection urlConnection) {
        int code;
        String location;
        try {
            code = urlConnection.getResponseCode();
            Log.w("Danger", code + "");
            if (code < 300 || code >= 400) {
                return State.LOGGEDIN;
            }

            try {
                location = urlConnection.getHeaderField("Location");
                locationHeader = location;
                if (location.contains("gateway.iitk.ac.in")) {
                    return State.FORTINET;
                } else if (location.contains("ironport")) {
                    return State.IRONPORT;
                }

                // The website may genuinely decide to redirect users
                return State.LOGGEDIN;

            } catch (Exception e) {
                Log.w("Exception", "Could not get location");
                Log.w("Exception", e.toString());
                return State.ERROR;
            }

        } catch (Exception e) {
            Log.w("Exception", e.toString());
            return State.ERROR;
        }
    }

    private enum State {
        LOGGEDIN,
        IRONPORT,
        FORTINET,
        ERROR
    }
}

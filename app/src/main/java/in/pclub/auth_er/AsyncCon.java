package in.pclub.auth_er;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by pallav on 1/13/17.
 */

public class AsyncCon extends AsyncTask<Void, Void, Integer> {
    private View v;

    public AsyncCon(View context) {
        this.v = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return Connection.getStatusCode();
    }

    @Override
    protected void onPostExecute(Integer result) {
        Snackbar.make(v, result + "", Snackbar.LENGTH_LONG).setAction("Action!!", null).show();
    }

}

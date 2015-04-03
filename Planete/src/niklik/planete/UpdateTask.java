package niklik.planete;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.app.AlertDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class UpdateTask extends AsyncTask<Void, Void, Boolean> {

	public static final int VERSION = 3;
	private AlertDialog ad;
	private static final String URL = "http://nikiknez.github.io";
	private static final String VER_FILE = "/planete_version";

	public UpdateTask(AlertDialog ad) {
		this.ad = ad;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
		HttpGet request = new HttpGet(URL + VER_FILE);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			String s = mClient.execute(request, responseHandler);
			Log.i("verzija pronadjena", s);
			int ver = Integer.parseInt(s);
			if (ver > VERSION)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mClient.close();
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean imaNovaVerzija) {
		if (imaNovaVerzija)
			ad.show();
	}
}

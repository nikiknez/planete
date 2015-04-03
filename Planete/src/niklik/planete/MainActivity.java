package niklik.planete;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private Podloga podloga;

	public static final String APP_URL = "http://nikiknez.github.io/Planete.apk";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		podloga = new Podloga(getApplicationContext());
		setContentView(podloga);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		podloga.setDisplayMetrics(metrics);

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("UPDATE");
		adb.setMessage("Nova verzija dostupna. Da li zelite da je preuzmete?");

		adb.setNegativeButton("NE", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		adb.setPositiveButton("DA", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(APP_URL));
				startActivity(browserIntent);
			}
		});
		AlertDialog ad = adb.create();
		new UpdateTask(ad).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_settings:
			podloga.getRender().ukloniSve();
			return true;
		case R.id.speedUp:
			podloga.getRender().speedUp();
			return true;
		case R.id.slowDown:
			podloga.getRender().slowDown();
			return true;
		}
		return false;
	}
}

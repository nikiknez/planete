package niklik.planete;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Slike {

	private static Bitmap[] slike = new Bitmap[13];
	public static final int SUN = 0;
	public static final int EARTH = 1;
	public static final int JUPITER = 2;
	public static final int MARS = 6;
	public static final int NEPTUNE = 9;
	public static final int VENUS = 11;

	public static void init(Resources res) {
		slike[0] = BitmapFactory.decodeResource(res, R.drawable.sun);
		slike[1] = BitmapFactory.decodeResource(res, R.drawable.earth);
		slike[2] = BitmapFactory.decodeResource(res, R.drawable.jupiter);
		slike[3] = BitmapFactory.decodeResource(res, R.drawable.jupiter2);
		slike[4] = BitmapFactory.decodeResource(res, R.drawable.jupiter3);
		slike[5] = BitmapFactory.decodeResource(res, R.drawable.jupiter4);
		slike[6] = BitmapFactory.decodeResource(res, R.drawable.mars);
		slike[7] = BitmapFactory.decodeResource(res, R.drawable.mars4);
		slike[8] = BitmapFactory.decodeResource(res, R.drawable.mars5);
		slike[9] = BitmapFactory.decodeResource(res, R.drawable.neptune);
		slike[10] = BitmapFactory.decodeResource(res, R.drawable.planet5);
		slike[11] = BitmapFactory.decodeResource(res, R.drawable.venus);
		slike[12] = BitmapFactory.decodeResource(res, R.drawable.venus2);
	}
	public static Bitmap get(int i){
		return slike[i];
	}
	public static Bitmap getRandom(){
		return slike[(int) (Math.random()*slike.length)];
	}
}

package niklik.planete;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;

public class Strelica {

	private static Path path = new Path();
	static {
		path.setFillType(FillType.WINDING);
		path.lineTo(0, -1);
		path.lineTo(8, -1);
		path.lineTo(8, -2);
		path.lineTo(10, 0);
		path.lineTo(8, 2);
		path.lineTo(8, 1);
		path.lineTo(0, 1);
		path.close();
	}

	public static void crtaj(Canvas canvas, Paint paint) {
		canvas.scale(0.1f, 0.25f);
		canvas.drawPath(path, paint);
	}
}

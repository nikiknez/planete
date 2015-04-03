package niklik.planete;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SPlaneta extends Planeta {


	public SPlaneta(float m, Vektor pozicija, Vektor brzina, Bitmap image, float scale) {
		super(m, pozicija, brzina, image, scale);
	}

	public void draw(Canvas canvas) {
		crtajSliku(canvas);
	}

	public void protekloVreme(float vreme) {
		ugao += ugaonaBrzina * vreme;
	}
}

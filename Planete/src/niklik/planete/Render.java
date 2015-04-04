package niklik.planete;

import java.util.LinkedList;
import java.util.List;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class Render extends Thread {

	private int w, h;
	private SurfaceHolder sh;
	private Bitmap pozadina;
	private List<Planeta> planete;
	private Planeta novaPlaneta = null;
	private Resources res;
	private Paint paint;
	private long fps = 25;
	private long as = 40; // animation speed [ms]
	private boolean pritisnutEkran = false;
	private long t0;
	private float ukloniPozX, ukloniPozY;
	private float x0;
	private float y0;
	private int minBitmapSize;
	private int maxBitmapSize;
	private float speed = 0.4f;

	public Render(SurfaceHolder sh, Resources res, int w, int h) {
		this.sh = sh;
		this.w = w;
		this.h = h;
		this.res = res;
		minBitmapSize = w / 13;
		maxBitmapSize = w / 6;
		ukloniPozX = w - Podloga.xdpmm * 5;
		ukloniPozY = h - Podloga.ydpmm * 5;
		planete = new LinkedList<Planeta>();
		Bitmap b = BitmapFactory.decodeResource(res, R.drawable.stars);
		pozadina = Bitmap.createScaledBitmap(b, w, h, true);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		initPlanete();
	}

	private void initPlanete() {
		Slike.init(res);
		Vektor p = new Vektor(w / 2, h / 2);
		Vektor b = new Vektor();
		planete.add(new SPlaneta(500, p, b, Slike.get(Slike.SUN), maxBitmapSize));

	}

	public void run() {
		long t0, t;
		crtaj();
		try {
			while (!interrupted()) {
				t0 = System.currentTimeMillis();
				crtaj();
				pomeriPlanete();
				povecajNovuPlanetu();
				t = System.currentTimeMillis() - t0;
				if (t < as) {
					fps = 25;
					sleep(as - t);
				} else
					fps = 1000 / t;
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private synchronized void crtaj() {
		Canvas canvas = sh.lockCanvas();
		if (canvas == null)
			return;
		canvas.drawBitmap(pozadina, 0, 0, null);
		for (Planeta p : planete) {
			p.draw(canvas);
		}
		if (pritisnutEkran) {
			novaPlaneta.draw(canvas);
			crtajProgres(canvas);
		}
		canvas.drawText("FPS: " + fps + " BP: " + planete.size(), 0, h, paint);

		sh.unlockCanvasAndPost(canvas);
	}

	private synchronized void povecajNovuPlanetu() {
		if (!pritisnutEkran)
			return;
		float s = System.currentTimeMillis() - t0;
		float m = s;
		if (m < 500)
			m = 100 + m * 0.8f;
		else
			m = 500;
		if (s > 500)
			s = 500;
		novaPlaneta.setMasa(m);
		novaPlaneta.setScale(minBitmapSize + s / 500
				* (maxBitmapSize - minBitmapSize));
	}

	private void crtajProgres(Canvas canvas) {
		float procenat = (System.currentTimeMillis() - t0) * 1.0f / 460;
		if (procenat > 1)
			procenat = 1;
		canvas.drawRect(0, 0, w * procenat, 5, paint);
	}

	private synchronized void pomeriPlanete() {
		for (Planeta p1 : planete) {
			if (p1 instanceof SPlaneta)
				continue;
			Vektor ubrzanje = new Vektor();
			for (Planeta p2 : planete) {
				if (p1 != p2) {
					float razdaljina2 = p1.razdaljina2(p2);
					ubrzanje.dodaj(p1.pravacKa(p2).proizvod(
							p2.masa() / (razdaljina2 + 50)));
				}
			}
			p1.gUbrzanje(ubrzanje);
		}

		for (Planeta p : planete)
			p.protekloVreme(speed);
	}

	public synchronized void ukloniSve() {
		planete.clear();
		novaPlaneta = null;
	}

	public synchronized void actionDown(float x, float y) {
		if (pritisnutEkran)
			return;
		if (x > ukloniPozX && y > ukloniPozY) {
			ukloniSve();
			return;
		}
		t0 = System.currentTimeMillis();
		x0 = x;
		y0 = y;
		novaPlaneta = new SPlaneta(100, new Vektor(x, y), new Vektor(),
				Slike.getRandom(), minBitmapSize);
		pritisnutEkran = true;
	}

	public void actionMove(float x, float y) {
		if (pritisnutEkran)
			novaPlaneta.setPozicija(x, y);
	}

	public synchronized void actionUp(float x, float y) {
		if (!pritisnutEkran)
			return;
		pritisnutEkran = false;
		if (x > ukloniPozX && y > ukloniPozY) {
			ukloniSve();
			return;
		}
		novaPlaneta.setPozicija(x, y);
		float dt = System.currentTimeMillis() - t0;
		if (dt < 500) {
			float vx = (x - x0) / dt * 30;
			float vy = (y - y0) / dt * 30;
			Vektor b = new Vektor(vx, vy);
			float scale = minBitmapSize + dt / 500
					* (maxBitmapSize - minBitmapSize);
			novaPlaneta = new Planeta(novaPlaneta.masa(),
					novaPlaneta.pozicija(), b, novaPlaneta.getImage(), scale);
			planete.add(novaPlaneta);
		} else {
			novaPlaneta.setScale(maxBitmapSize);
			planete.add(novaPlaneta);
		}
	}

	public void speedUp() {
		speed *= 1.2f;
	}

	public void slowDown() {
		speed *= 0.8f;
	}
}

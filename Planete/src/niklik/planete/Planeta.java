package niklik.planete;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Planeta {
	protected float masa;
	protected Vektor brzina;
	protected Vektor ubrzanje;
	protected Vektor pozicija;
	protected Paint paint;
	protected Bitmap image;
	protected float ugaonaBrzina;
	protected float ugao;
	protected Vektor[] trag;
	protected int head = 0;
	private float scale;

	public Planeta(float m, Vektor pozicija, Vektor brzina, Bitmap image,
			float scale) {
		masa = m;
		this.brzina = brzina;
		this.pozicija = pozicija;
		this.scale = scale;
		ubrzanje = new Vektor(0, 0);
		paint = new Paint();
		paint.setAntiAlias(true);
		trag = new Vektor[20];
		ugaonaBrzina = (float) (Math.random() * 100 - 50);
		ugao = 0;
		this.image = image;
		for (int i = 0; i < trag.length; i++)
			trag[i] = new Vektor(pozicija.x(), pozicija.y());

	}

	public void gUbrzanje(Vektor f) {
		ubrzanje = f;
	}

	public void protekloVreme(float vreme) {
		brzina.dodaj(ubrzanje.proizvod(vreme));
		pozicija.dodaj(brzina.proizvod(vreme));
		ugao += ugaonaBrzina * vreme;
		head = (head + 1) % trag.length;
		trag[head].setxy(pozicija.x(), pozicija.y());
	}

	public float masa() {
		return masa;
	}

	public void setMasa(float m) {
		masa = m;
	}

	public void draw(Canvas canvas) {
		crtajTrag(canvas);
		paint.setColor(Color.RED);
		crtajStrelicu(canvas, ubrzanje);
		crtajSliku(canvas);
	}

	protected void crtajSliku(Canvas canvas) {
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.translate(pozicija.x(), pozicija.y());
		canvas.rotate(ugao);
		canvas.scale(scale / image.getWidth(), scale / image.getHeight());
		canvas.drawBitmap(image, -image.getWidth() / 2, -image.getHeight() / 2,
				null);
		canvas.restore();
	}

	protected void crtajStrelicu(Canvas canvas, Vektor v) {
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		float ugao = (float) Math.toDegrees(Math.atan2(v.y(), v.x()));
		canvas.translate(pozicija.x(), pozicija.y());
		canvas.rotate(ugao);
		canvas.scale(scale / image.getWidth(), scale / image.getHeight());
		canvas.translate(image.getWidth() / 2.2f, 0);
		canvas.scale(image.getWidth() / 2, image.getWidth() / 6);
		Strelica.crtaj(canvas, paint);
		canvas.restore();
	}

	protected void crtajTrag(Canvas canvas) {
		for (int i = 0, c = trag.length * 5 + 20; i < trag.length - 1; i++) {
			paint.setColor(Color.rgb(c, c, c));
			canvas.drawLine(
					(int) trag[(head - i + trag.length) % trag.length].x(),
					(int) trag[(head - i + trag.length) % trag.length].y(),
					(int) trag[(head - i + trag.length - 1) % trag.length].x(),
					(int) trag[(head - i + trag.length - 1) % trag.length].y(),
					paint);
			c -= 5;
		}
	}

	public Vektor getPravac() {
		float x = 0, y = 0;
		for (int i = 0; i < 5; i++) {
			x += trag[(head - i + trag.length) % trag.length].x()
					- trag[(head - i + trag.length - 1) % trag.length].x();
			y += trag[(head - i + trag.length) % trag.length].y()
					- trag[(head - i + trag.length - 1) % trag.length].y();
		}
		return new Vektor(x, y);
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public void setPozicija(float x, float y) {
		pozicija.setxy(x, y);
		head = (head + 1) % trag.length;
		trag[head].setxy(pozicija.x(), pozicija.y());
	}

	public Vektor pravacKa(Planeta p) {
		return p.pozicija.zbir(pozicija.proizvod(-1));
	}

	public float razdaljina(Planeta p) {
		return pozicija.razdaljina(p.pozicija);
	}

	public float razdaljina2(Planeta p) {
		return pozicija.razdaljina2(p.pozicija);
	}

	public void setBrzina(Vektor b) {
		brzina = b;
	}

	public void setScale(float s) {
		scale = s;
	}

	public Vektor pozicija() {
		return pozicija;
	}
}
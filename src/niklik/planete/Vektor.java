package niklik.planete;

public class Vektor {
	private float x, y;

	public Vektor() {
		this(0, 0);
	}

	public Vektor(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setxy(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vektor zbir(Vektor v) {
		return new Vektor(x + v.x, y + v.y);
	}

	public Vektor dodaj(Vektor v) {
		x += v.x;
		y += v.y;
		return this;
	}

	public Vektor proizvod(float f) {
		return new Vektor(x * f, y * f);
	}

	public Vektor mnozi(float f) {
		x *= f;
		y *= f;
		return this;
	}

	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public float razdaljina(Vektor v) {
		return (float) Math.sqrt(razdaljina2(v));
	}

	public float razdaljina2(Vektor v) {
		return (x - v.x) * (x - v.x) + (y - v.y) * (y - v.y);
	}
}
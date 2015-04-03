package niklik.planete;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Podloga extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder sh;
	private Render render;
	public static float xdpmm, ydpmm;

	public Render getRender() {
		return render;
	}

	public Podloga(Context context) {
		super(context);
		sh = getHolder();
		sh.addCallback(this);
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			int pindex = event.getActionIndex();
			float x = event.getX(pindex);
			float y = event.getY(pindex);
			render.actionDown(x, y);
			return true;
		}
		if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
			int pindex = event.getActionIndex();
			float x = event.getX(pindex);
			float y = event.getY(pindex);
			render.actionMove(x, y);
			return true;
		}
		if (event.getActionMasked() == MotionEvent.ACTION_UP) {
			int pindex = event.getActionIndex();
			float x = event.getX(pindex);
			float y = event.getY(pindex);
			render.actionUp(x, y);
			return true;
		}
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		render = new Render(sh, getResources(), getWidth(), getHeight());
		render.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (render != null)
			render.interrupt();
	}

	public void setDisplayMetrics(DisplayMetrics metrics) {
		xdpmm = metrics.xdpi / 25.4f;
		ydpmm = metrics.ydpi / 25.4f;
	}
}

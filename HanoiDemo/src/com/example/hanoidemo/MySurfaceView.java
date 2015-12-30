package com.example.hanoidemo;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	// hanoi total size;
	private int size = 0;
	private SurfaceHolder holder;
	private Stack<Tower> A = new Stack<Tower>();
	private Stack<Tower> B = new Stack<Tower>();
	private Stack<Tower> C = new Stack<Tower>();

	public int getSize() {
		return size;
	}

	Thread runThread;
	ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

	public void setSize(final int size) {
		if (runThread != null) {
			runThread.interrupt();
		}
		this.size = size;
		A = new Stack<Tower>();
		B = new Stack<Tower>();
		C = new Stack<Tower>();
		for (int i = size; i > 0; i--) {
			A.push(new Tower(i - 1, size));
		}
		runThread = new Thread(new Runnable() {

			@Override
			public void run() {
				draw(size, A, B, C);
			}
		});
		runThread.start();
	}

	public MySurfaceView(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
	}

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		holder.addCallback(this);
	}

	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		holder = getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		isRun = true;
		t.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRun = false;
	}

	private boolean isRun = false;
	Canvas canvas;

	private void drawInit() {
		canvas = holder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		p = new Paint();
		p.setColor(Color.RED);
		p.setTextSize(18);
		canvas.drawText("A", 100, 100, p);
		canvas.drawText("B", 100 + offset, 100, p);
		canvas.drawText("C", 100 + offset * 2, 100, p);
		show(canvas);
		holder.unlockCanvasAndPost(canvas);
	}

	Thread t = new Thread() {

		@Override
		public void run() {
			super.run();
			while (isRun) {
				drawInit();
				sleep_(500);
			}
		}

	};

	float offset = 500f;
	float width = 40;
	float height = 30;
	float addWidth = 20, off_x = -20;// 每增加一个，后一个的左偏移
	float init_x = 90, init_y = 180;
	Paint p;

	private void draw(int towerSize, Stack<Tower> A, Stack<Tower> B,
			Stack<Tower> C) {
		doTower(towerSize, A, B, C);
	}

	public void doTower(int towerSize, Stack<Tower> A, Stack<Tower> B,
			Stack<Tower> C) {
		if (runThread.isInterrupted()) {
			return;
		}
		if (towerSize == 1) {
			move(A, C);
			sleep_(1000);
		} else {
			doTower(towerSize - 1, A, C, B);
			move(A, C);
			sleep_(1000);
			doTower(towerSize - 1, B, A, C);
		}
	}

	private void sleep_(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
		}
	}

	public void move(Stack<Tower> A, Stack<Tower> B) {
		Tower pop = A.pop();
		B.push(pop);
	}

	public void show(Canvas canvas) {
		for (int i = 0; i < A.size(); i++) {
			int num = A.get(i).getSize();
			canvas.drawRect(init_x + off_x * num, init_y + height * num, init_x
					+ width + addWidth * num, init_y + height * (num + 1), p);
		}
		for (int i = 0; i < B.size(); i++) {
			int num = B.get(i).getSize();
			canvas.drawRect(init_x + off_x * num + offset, init_y + height
					* num, init_x + width + addWidth * num + offset, init_y
					+ height * (num + 1), p);
		}
		for (int i = 0; i < C.size(); i++) {
			int num = C.get(i).getSize();
			canvas.drawRect(init_x + off_x * num + offset * 2, init_y + height
					* num, init_x + width + addWidth * num + offset * 2, init_y
					+ height * (num + 1), p);
		}
	}
}

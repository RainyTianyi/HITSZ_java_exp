package edu.hitsz.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import edu.hitsz.android.game.Arena;
import edu.hitsz.android.game.Difficulty;
import edu.hitsz.android.game.GameEngine;
import edu.hitsz.android.game.Sprite;
import edu.hitsz.android.game.basic.AbstractFlyingObject;

final class BaseGame extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final ImageManager images;
    private final GameEngine game;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final Paint hud = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final SurfaceHolder holder;
    private boolean foreground;
    private boolean surfaceReady;
    private Thread renderThread;
    private volatile boolean running;
    private volatile Viewport viewport;
    private volatile TouchTarget touchTarget;
    private int pointerId = -1;
    private int backgroundOffset;

    BaseGame(Context context, Difficulty difficulty) {
        super(context);
        images = new ImageManager(getResources(), difficulty);
        game = new GameEngine(difficulty);
        hud.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        hud.setTextSize(22);
        holder = getHolder();
        holder.addCallback(this);
    }

    void resumeGame() {
        foreground = true;
        startThreadIfReady();
    }

    void pauseGame() {
        foreground = false;
        pointerId = -1;
        touchTarget = null;
        stopThread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceReady = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (width > 0 && height > 0) {
            viewport = new Viewport(width, height);
            startThreadIfReady();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceReady = false;
        stopThread();
        viewport = null;
    }

    // 只有 Activity 在前台且 Surface 有效时才启动，生命周期回调都在主线程。
    private void startThreadIfReady() {
        if (foreground && surfaceReady && viewport != null && renderThread == null) {
            running = true;
            renderThread = new Thread(this, "aircraft-game");
            renderThread.start();
        }
    }

    private void stopThread() {
        running = false;
        if (renderThread == null) {
            return;
        }
        renderThread.interrupt();
        boolean interrupted = false;
        // 等旧线程释放画布，避免返回页面时两个线程同时向 Surface 绘制。
        while (renderThread.isAlive()) {
            try {
                renderThread.join();
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        renderThread = null;
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (running && !game.isOver()) {
            long started = SystemClock.uptimeMillis();
            TouchTarget target = touchTarget;
            if (target != null) {
                game.hero().moveTo(target.x, target.y);
            }
            game.step();
            drawFrame();
            long delay = Arena.STEP_MS - (SystemClock.uptimeMillis() - started);
            if (delay > 0 && running) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private void drawFrame() {
        Viewport area = viewport;
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }
        try {
            canvas.drawColor(Color.rgb(16, 25, 35));
            canvas.translate(area.left, area.top);
            canvas.scale(area.scale, area.scale);
            canvas.clipRect(0, 0, Arena.WIDTH, Arena.HEIGHT);
            canvas.drawBitmap(images.background, 0, backgroundOffset - Arena.HEIGHT, paint);
            canvas.drawBitmap(images.background, 0, backgroundOffset, paint);
            backgroundOffset = (backgroundOffset + 1) % Arena.HEIGHT;

            drawAll(canvas, game.enemyBullets());
            drawAll(canvas, game.heroBullets());
            drawAll(canvas, game.enemies());
            drawAll(canvas, game.items());
            if (!game.isOver()) {
                drawObject(canvas, game.hero());
            }
            hud.setColor(0xdf101923);
            canvas.drawRoundRect(12, 12, 190, 78, 12, 12, hud);
            hud.setColor(Color.WHITE);
            canvas.drawText("得分  " + game.score(), 24, 38, hud);
            canvas.drawText("生命  " + game.hero().getHp(), 24, 64, hud);
        } finally {
            // 即使绘制中抛出异常，也要归还从 SurfaceHolder 取得的画布。
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawAll(Canvas canvas, Iterable<? extends AbstractFlyingObject> objects) {
        for (AbstractFlyingObject object : objects) {
            drawObject(canvas, object);
        }
    }

    private void drawObject(Canvas canvas, AbstractFlyingObject object) {
        Sprite sprite = object.getSprite();
        canvas.drawBitmap(images.get(sprite), object.getLocationX() - sprite.width / 2f,
                object.getLocationY() - sprite.height / 2f, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                pointerId = event.getPointerId(0);
                updateTouch(event, 0);
                return true;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(pointerId);
                if (index >= 0) {
                    updateTouch(event, index);
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(event.getActionIndex()) == pointerId) {
                    pointerId = -1;
                    performClick();
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                pointerId = -1;
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void updateTouch(MotionEvent event, int index) {
        Viewport area = viewport;
        if (area != null) {
            // 主线程只投递坐标，逻辑线程在下一帧移动英雄机。
            touchTarget = new TouchTarget((event.getX(index) - area.left) / area.scale,
                    (event.getY(index) - area.top) / area.scale);
        }
    }

    private static final class TouchTarget {
        final float x;
        final float y;

        TouchTarget(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final class Viewport {
        final float scale;
        final float left;
        final float top;

        Viewport(int width, int height) {
            scale = Math.min(width / (float) Arena.WIDTH, height / (float) Arena.HEIGHT);
            left = (width - Arena.WIDTH * scale) / 2;
            top = (height - Arena.HEIGHT * scale) / 2;
        }
    }
}

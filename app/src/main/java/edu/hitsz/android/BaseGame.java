package edu.hitsz.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import edu.hitsz.android.game.Arena;
import edu.hitsz.android.game.Difficulty;
import edu.hitsz.android.game.Sprite;

final class BaseGame extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final ImageManager images;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final SurfaceHolder holder;
    private boolean foreground;
    private boolean surfaceReady;
    private Thread renderThread;
    private volatile boolean running;
    private volatile Viewport viewport;

    BaseGame(Context context, Difficulty difficulty) {
        super(context);
        images = new ImageManager(getResources(), difficulty);
        holder = getHolder();
        holder.addCallback(this);
    }

    void resumeGame() {
        foreground = true;
        startThreadIfReady();
    }

    void pauseGame() {
        foreground = false;
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
        while (running) {
            long started = SystemClock.uptimeMillis();
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
            canvas.drawBitmap(images.background, 0, 0, paint);
            drawSprite(canvas, Sprite.HERO, Arena.WIDTH / 2, Arena.HEIGHT - Sprite.HERO.height);
        } finally {
            // 即使绘制中抛出异常，也要归还从 SurfaceHolder 取得的画布。
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawSprite(Canvas canvas, Sprite sprite, int centerX, int centerY) {
        canvas.drawBitmap(images.get(sprite), centerX - sprite.width / 2f,
                centerY - sprite.height / 2f, paint);
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

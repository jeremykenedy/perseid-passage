package com.jeremykenedy.perseidpassage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Color;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Random;

final class ShowerSceneView extends View {
    private static final long FRAME_DELAY_MS = 33L;
    private static final int MAX_METEORS = 14;
    private static final int[][] METEOR_COLORS = {
            {0xffa9d9ff, 0xfff0f7ff}, {0xffc8a5ff, 0xffffefff}, {0xffffbd77, 0xfffff4d9}
    };
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Random random = new Random();
    private final Star[] stars;
    private final Meteor[] meteors = new Meteor[MAX_METEORS];
    private final ShowerOptions options;
    private Bitmap background;
    private long startedAt;
    private long lastFrameAt;
    private long nextMeteorAt;
    private boolean running;

    ShowerSceneView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        options = ShowerOptions.resolve(preferences.getString("density", "balanced"),
                preferences.getString("motion", "natural"), preferences.getString("rate", "steady"),
                preferences.getString("size", "natural"), preferences.getString("palette", "ice"),
                preferences.getString("brightness", "dusk"), preferences.getBoolean("randomize_all", false),
                new Random(System.currentTimeMillis()));
        stars = new Star[options.stars];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(random.nextFloat(), random.nextFloat(), 0.35f + random.nextFloat() * 1.7f,
                    0.3f + random.nextFloat() * 0.7f, random.nextFloat() * 6.28f,
                    0.8f + random.nextFloat() * 1.8f, random.nextFloat() * 0.6f + 0.2f);
        }
    }

    void start() {
        if (!running) {
            running = true;
            startedAt = SystemClock.uptimeMillis();
            lastFrameAt = startedAt;
            nextMeteorAt = startedAt + 450L;
            postInvalidateOnAnimation();
        }
    }

    void stop() {
        running = false;
        removeCallbacks(invalidator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || getHeight() == 0) return;
        long now = SystemClock.uptimeMillis();
        float time = (now - startedAt) / 1000f;
        float delta = Math.min(0.06f, Math.max(0f, (now - lastFrameAt) / 1000f));
        lastFrameAt = now;
        if (background != null) canvas.drawBitmap(background, 0, 0, null);
        drawStars(canvas, time);
        advanceMeteors(now, delta);
        drawMeteors(canvas);
        if (running) postDelayed(invalidator, FRAME_DELAY_MS);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        if (width <= 0 || height <= 0) return;
        if (background != null) background.recycle();
        background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        int baseTop = options.palette == 1 ? 0xff100d22 : options.palette == 2 ? 0xff21100b : 0xff081323;
        int baseBottom = options.palette == 1 ? 0xff05040d : options.palette == 2 ? 0xff080505 : 0xff030711;
        paint.setShader(new LinearGradient(0, 0, width, height, baseTop, baseBottom, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, width, height, paint);
        paint.setShader(null);
        drawNebula(canvas, width, height);
    }

    private final Runnable invalidator = new Runnable() {
        @Override
        public void run() {
            if (running) invalidate();
        }
    };

    private void drawNebula(Canvas canvas, int width, int height) {
        int color = options.palette == 1 ? 0xff6e4ab3 : options.palette == 2 ? 0xffb45a35 : 0xff27648d;
        drawNebulaCloud(canvas, width * 0.27f, height * 0.34f, width * 0.39f, color, 82);
        drawNebulaCloud(canvas, width * 0.69f, height * 0.56f, width * 0.44f,
                options.palette == 2 ? 0xffa74d68 : 0xff3857a8, 58);
        drawNebulaCloud(canvas, width * 0.49f, height * 0.88f, width * 0.35f, color, 32);
    }

    private void drawNebulaCloud(Canvas canvas, float x, float y, float radius, int color, int alpha) {
        int center = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        int edge = Color.argb(0, Color.red(color), Color.green(color), Color.blue(color));
        paint.setShader(new RadialGradient(x, y, radius, center, edge, Shader.TileMode.CLAMP));
        canvas.drawCircle(x, y, radius, paint);
        paint.setShader(null);
    }

    private void drawStars(Canvas canvas, float time) {
        float height = getHeight();
        float width = getWidth();
        for (Star star : stars) {
            float drift = (time * options.speed * star.depth * 1.8f) % height;
            float x = star.x * width + (float) Math.sin(time * 0.025f + star.phase) * star.depth * 4f;
            float y = (star.y * height + drift) % height;
            float twinkle = 0.56f + 0.44f * (float) Math.sin(time * star.twinkle + star.phase);
            int alpha = Math.min(255, Math.max(0, (int) (star.alpha * twinkle * options.brightness * 220f)));
            paint.setColor(options.palette == 2 && star.warm ? 0xffffd8b0 : 0xffd4e6ff);
            paint.setAlpha(alpha);
            canvas.drawCircle(x, y, star.radius * (0.75f + twinkle * 0.25f), paint);
        }
        paint.setAlpha(255);
    }

    private void advanceMeteors(long now, float delta) {
        for (int i = 0; i < meteors.length; i++) {
            Meteor meteor = meteors[i];
            if (meteor != null) {
                meteor.age += delta * options.speed;
                if (meteor.age >= meteor.lifetime) meteors[i] = null;
            }
        }
        if (now < nextMeteorAt) return;
        int slot = freeMeteorSlot();
        if (slot >= 0) meteors[slot] = new Meteor(random, options.meteorSize);
        double interval = -Math.log(Math.max(0.02, 1.0 - random.nextDouble())) / options.meteorRate;
        nextMeteorAt = now + Math.max(260L, (long) (interval * 1000f));
    }

    private int freeMeteorSlot() {
        for (int i = 0; i < meteors.length; i++) if (meteors[i] == null) return i;
        return -1;
    }

    private void drawMeteors(Canvas canvas) {
        for (Meteor meteor : meteors) {
            if (meteor == null) continue;
            float progress = meteor.age / meteor.lifetime;
            float eased = progress * progress * (3f - 2f * progress);
            float x = (meteor.startX + (meteor.endX - meteor.startX) * eased) * getWidth();
            float y = (meteor.startY + (meteor.endY - meteor.startY) * eased) * getHeight();
            float curve = (float) Math.sin(progress * Math.PI) * meteor.curve;
            x += curve;
            float fade = (float) Math.sin(Math.min(1f, progress * 5f) * Math.PI / 2f)
                    * (1f - progress) * options.brightness;
            int[] colors = METEOR_COLORS[options.palette];
            float trailLength = getWidth() * meteor.tailLength;
            float trailX = x - trailLength;
            float trailY = y - trailLength * 0.57f;
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(colors[0]);
            paint.setAlpha((int) (85f * fade));
            paint.setStrokeWidth(meteor.width * 5.2f);
            canvas.drawLine(trailX, trailY, x, y, paint);
            paint.setAlpha((int) (190f * fade));
            paint.setStrokeWidth(meteor.width * 1.9f);
            canvas.drawLine(trailX, trailY, x, y, paint);
            paint.setColor(colors[1]);
            paint.setAlpha((int) (245f * fade));
            paint.setStrokeWidth(meteor.width * 0.85f);
            canvas.drawLine(trailX, trailY, x, y, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha((int) (180f * fade));
            canvas.drawCircle(x, y, meteor.width * 2.5f, paint);
            paint.setAlpha((int) (255f * fade));
            canvas.drawCircle(x, y, meteor.width * 0.8f, paint);
        }
        paint.setAlpha(255);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        if (background != null) {
            background.recycle();
            background = null;
        }
        super.onDetachedFromWindow();
    }

    private static final class Star {
        final float x, y, radius, alpha, phase, twinkle, depth;
        final boolean warm;

        Star(float x, float y, float radius, float alpha, float phase, float twinkle, float depth) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.alpha = alpha;
            this.phase = phase;
            this.twinkle = twinkle;
            this.depth = depth;
            this.warm = x > 0.8f;
        }
    }

    private static final class Meteor {
        final float startX, startY, endX, endY, curve, width, tailLength, lifetime;
        float age;

        Meteor(Random random, float size) {
            startX = random.nextFloat() * 0.8f;
            startY = random.nextFloat() * 0.62f;
            endX = Math.min(1.12f, startX + 0.35f + random.nextFloat() * 0.28f);
            endY = Math.min(1.1f, startY + 0.28f + random.nextFloat() * 0.3f);
            curve = (random.nextFloat() - 0.5f) * 22f;
            width = (2.7f + random.nextFloat() * 2f) * size;
            tailLength = 0.07f + random.nextFloat() * 0.08f;
            lifetime = 1.15f + random.nextFloat() * 0.9f;
        }
    }
}

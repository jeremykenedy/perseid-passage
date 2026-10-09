package com.jeremykenedy.perseidpassage;

import java.util.Random;

public final class ShowerOptions {
    public final int stars;
    public final float speed;
    public final float meteorRate;
    public final float meteorSize;
    public final int palette;
    public final float brightness;

    private ShowerOptions(int stars, float speed, float meteorRate, float meteorSize,
            int palette, float brightness) {
        this.stars = stars;
        this.speed = speed;
        this.meteorRate = meteorRate;
        this.meteorSize = meteorSize;
        this.palette = palette;
        this.brightness = brightness;
    }

    public static ShowerOptions resolve(String density, String motion, String rate, String size,
            String palette, String brightness, boolean randomizeAll, Random random) {
        return new ShowerOptions(starsFor(choose(density, randomizeAll, random,
                        "balanced", "sparse", "dense", "packed")),
                speedFor(choose(motion, randomizeAll, random, "natural", "slow", "fast")),
                rateFor(choose(rate, randomizeAll, random, "steady", "quiet", "storm")),
                sizeFor(choose(size, randomizeAll, random, "natural", "fine", "bold")),
                paletteFor(choose(palette, randomizeAll, random, "ice", "violet", "amber")),
                brightnessFor(choose(brightness, randomizeAll, random, "dusk", "night", "bright")));
    }

    static String choose(String selected, boolean randomizeAll, Random random, String... values) {
        if (randomizeAll || "random".equals(selected)) return values[random.nextInt(values.length)];
        for (String value : values) if (value.equals(selected)) return selected;
        return values[0];
    }

    static int starsFor(String density) {
        if ("sparse".equals(density)) return 220;
        if ("dense".equals(density)) return 600;
        if ("packed".equals(density)) return 900;
        return 400;
    }

    static float speedFor(String motion) {
        if ("slow".equals(motion)) return 0.65f;
        if ("fast".equals(motion)) return 1.55f;
        return 1f;
    }

    static float rateFor(String rate) {
        if ("quiet".equals(rate)) return 0.3f;
        if ("storm".equals(rate)) return 2.4f;
        return 1.15f;
    }

    static float sizeFor(String size) {
        if ("fine".equals(size)) return 0.72f;
        if ("bold".equals(size)) return 1.55f;
        return 1f;
    }

    static int paletteFor(String palette) {
        if ("violet".equals(palette)) return 1;
        if ("amber".equals(palette)) return 2;
        return 0;
    }

    static float brightnessFor(String brightness) {
        if ("dusk".equals(brightness)) return 0.78f;
        if ("bright".equals(brightness)) return 1.15f;
        return 0.48f;
    }
}

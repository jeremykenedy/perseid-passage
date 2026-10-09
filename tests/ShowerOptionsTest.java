package com.jeremykenedy.perseidpassage;

import java.util.Random;

public final class ShowerOptionsTest {
    public static void main(String[] args) {
        verifyOptionMapping();
        verifyDefaultsAndInvalidValues();
        verifyRandomSelections();
        verifySettingsValidation();
        System.out.println("Perseid Passage settings tests passed.");
    }

    private static void verifyOptionMapping() {
        ShowerOptions options = ShowerOptions.resolve("packed", "fast", "storm", "bold", "amber", "bright", false,
                new Random(0));
        check(options.stars == 900, "packed star density");
        check(options.speed == 1.55f, "fast meteor speed");
        check(options.meteorRate == 2.4f, "storm rate");
        check(options.meteorSize == 1.55f, "bold meteor size");
        check(options.palette == 2, "amber palette");
        check(options.brightness == 1.15f, "bright scene");
    }

    private static void verifyDefaultsAndInvalidValues() {
        ShowerOptions defaults = ShowerOptions.resolve("balanced", "natural", "steady", "natural", "ice", "dusk",
                false, new Random(2));
        check(defaults.stars == 400 && defaults.speed == 1f && defaults.meteorRate == 1.15f
                && defaults.meteorSize == 1f && defaults.palette == 0 && defaults.brightness == 0.78f,
                "documented defaults");
        ShowerOptions fallback = ShowerOptions.resolve("invalid", "invalid", "invalid", "invalid", "invalid",
                "invalid", false, new Random(3));
        check(fallback.stars == 400 && fallback.speed == 1f && fallback.meteorRate == 1.15f
                && fallback.meteorSize == 1f && fallback.palette == 0 && fallback.brightness == 0.78f,
                "invalid values use safe defaults");
    }

    private static void verifyRandomSelections() {
        ShowerOptions random = ShowerOptions.resolve("random", "random", "random", "random", "random", "random",
                false, new FixedRandom(2));
        check(random.stars == 600 && random.speed == 1.55f && random.meteorRate == 2.4f
                && random.meteorSize == 1.55f && random.palette == 2 && random.brightness == 1.15f,
                "individual random choices");
        ShowerOptions all = ShowerOptions.resolve("sparse", "slow", "quiet", "fine", "ice", "night", true,
                new FixedRandom(1));
        check(all.stars == 220 && all.speed == 0.65f && all.meteorRate == 0.3f
                && all.meteorSize == 0.72f && all.palette == 1 && all.brightness == 0.48f,
                "global random chooses each setting");
    }

    private static void verifySettingsValidation() {
        check(!SettingsValues.isSupported(null, "night"), "null key rejected");
        check(!SettingsValues.isSupported("density", null), "null value rejected");
        accepts("density", "sparse", "balanced", "dense", "packed", "random");
        accepts("motion", "slow", "natural", "fast", "random");
        accepts("rate", "quiet", "steady", "storm", "random");
        accepts("size", "fine", "natural", "bold", "random");
        accepts("palette", "ice", "violet", "amber", "random");
        accepts("brightness", "night", "dusk", "bright", "random");
        check(SettingsValues.isSupported("randomize_all", "true"), "randomize enabled");
        check(SettingsValues.isSupported("randomize_all", "false"), "randomize disabled");
        check(!SettingsValues.isSupported("rate", "constant"), "unsupported rate rejected");
        check(!SettingsValues.isSupported("brightness", "day"), "unsupported brightness rejected");
        check(!SettingsValues.isSupported("randomize_all", "yes"), "invalid boolean rejected");
        check(!SettingsValues.isSupported("unknown", "value"), "unknown key rejected");
    }

    private static void accepts(String key, String... values) {
        for (String value : values) check(SettingsValues.isSupported(key, value), key + " accepts " + value);
    }

    private static void check(boolean result, String message) {
        if (!result) throw new AssertionError(message);
    }

    private static final class FixedRandom extends Random {
        private final int value;

        FixedRandom(int value) {
            this.value = value;
        }

        @Override
        public int nextInt(int bound) {
            return Math.min(value, bound - 1);
        }
    }
}

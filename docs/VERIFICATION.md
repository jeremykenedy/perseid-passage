# Device verification

## Tested

| Device | Platform | Display | Result |
| --- | --- | --- | --- |
| Android TV emulator, `sdk_google_atv64_arm64` | Android 12, API 31 | 1920 x 1080 | APK installed, settings activity and live preview opened, continuous animation visible, settings provider queried/updated, DreamService start/stop checked. |

The scene screenshot in `docs/screenshots/perseid-passage-android-tv-1920.png` was captured from the active DreamService after a 40-second settling period with the default settings: Balanced density, Natural speed, Steady rate, Natural size, Ice Blue palette, and Dusk brightness. The capture was made directly with `adb -s emulator-5570 exec-out screencap -p`, without image editing. Two captures three seconds apart differed in 26,019 pixels. The settings screenshot was captured from the running settings activity. D-pad input changed star density from Balanced to Dense, and the settings provider returned the new value. The emulator verifies app rendering and Android interfaces; it does not establish a TV manufacturer's screensaver picker behavior, idle activation, native 4K output, or long-term thermal behavior.

## Not yet tested

| Device family | Status | Request |
| --- | --- | --- |
| Amazon Fire TV / Fire OS | Untested on physical hardware | A volunteer with a Fire TV is needed to install the APK, select the dream, test remote navigation and idle activation, and report the model, Fire OS version, resolution, and results in a GitHub issue. |
| Android TV | Emulator only; no physical model tested | A volunteer with a physical Android TV is needed to test selection, remote controls, idle activation, and sustained playback, then report the model, OS/API, resolution, and results in a GitHub issue. |
| Google TV | Untested on physical hardware | A volunteer with a Google TV is needed to install and select the dream, test remote controls and idle activation, and report the model, OS/API, resolution, and results in a GitHub issue. |

No physical TV was used for this verification. Native 4K output and prevention of platform-level reversion are not claimed.

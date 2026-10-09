# Configuration

Open Perseid Passage from the device's app launcher. Settings take effect when the next preview or screensaver session starts. Each choice offers Random. Randomize All selects new values for every setting each time a session begins.

| Key | Values | Default | Effect |
| --- | --- | --- | --- |
| `density` | `sparse`, `balanced`, `dense`, `packed`, `random` | `balanced` | Changes the count of background stars from 220 to 900. |
| `motion` | `slow`, `natural`, `fast`, `random` | `natural` | Sets star drift and meteor travel speed. |
| `rate` | `quiet`, `steady`, `storm`, `random` | `steady` | Sets the average interval between meteor events. Events can overlap. |
| `size` | `fine`, `natural`, `bold`, `random` | `natural` | Scales meteor trail and head width. |
| `palette` | `ice`, `violet`, `amber`, `random` | `ice` | Changes the space haze, stars, and meteor color. |
| `brightness` | `night`, `dusk`, `bright`, `random` | `dusk` | Controls star and meteor luminance. |
| `randomize_all` | `true`, `false` | `false` | Selects all six options again at each session start. |

## Host settings interface

The app exposes `content://com.jeremykenedy.perseidpassage.settings/schema` and `/settings`. The schema returns `key`, `title`, `type`, `default`, `choices`, and `randomAllowed`. The settings URI returns the active stored values. Hosts may update one setting at a time by writing `key` and `value` to `/settings`. Unsupported keys or values are rejected. Randomized values are resolved when the screensaver view starts and are not written back to preferences.

Example read:

```bash
adb shell content query --uri content://com.jeremykenedy.perseidpassage.settings/schema
```

This interface exposes only the app's own visual options. It does not read device identity, other app data, or system configuration.

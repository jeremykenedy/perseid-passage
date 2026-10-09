# Architecture

Perseid Passage is a small Android application built without runtime dependencies. Its launcher activity exposes remote-friendly settings and a preview. `MeteorDreamService` provides the full-screen Android dream, and `ShowerSceneView` draws the scene with Android Canvas.

A static full-color bitmap holds a gradient and soft nebula fields for the current surface size. The animation loop draws twinkling stars and advances a bounded set of meteor events at about 30 frames per second. `onDreamingStopped` and view detachment stop the loop and release the cached bitmap. No video decoder, background service, wake lock, or network client is used.

`ShowerOptions` resolves persisted selections, including per-setting and global random choices. `SettingsProvider` exposes a versioned, read/write content provider for host interfaces. `SettingsValues` validates every supported key and value before persistence. The provider has no permission grant and exposes only these visual preferences.

The standalone Python installer contacts GitHub only after the user invokes installation from the latest release. It checks the release URLs, limits downloaded bytes, verifies the sibling SHA-256 file, then invokes ADB for the requested serial.

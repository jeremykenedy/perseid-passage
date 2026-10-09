<p align="center">
    <picture>
        <source media="(prefers-color-scheme: dark)" srcset="art/banner-dark.svg">
        <source media="(prefers-color-scheme: light)" srcset="art/banner-light.svg">
        <img src="art/banner-light.svg" alt="Perseid Passage, animated meteor shower without tracking or analytics" width="800">
    </picture>
</p>

<p align="center">A continuous meteor shower screensaver built for Fire TV, Android TV, and Google TV, verified on an Android TV emulator.</p>

<p align="center">
    <a href="https://github.com/jeremykenedy/perseid-passage/releases"><img src="https://img.shields.io/github/v/release/jeremykenedy/perseid-passage?label=latest%20release" alt="Latest release"></a>
    <a href="https://github.com/jeremykenedy/perseid-passage/releases"><img src="https://img.shields.io/github/downloads/jeremykenedy/perseid-passage/total" alt="GitHub release downloads"></a>
    <a href="LICENSE"><img src="https://img.shields.io/badge/License-Apache--2.0-blue.svg" alt="Apache 2.0 license"></a>
    <a href="https://github.com/jeremykenedy"><img src="https://img.shields.io/github/followers/jeremykenedy?label=Follow&style=social" alt="Follow on GitHub"></a>
    <a href="https://github.com/jeremykenedy/perseid-passage" title="Open the repository and click Star"><img src="https://img.shields.io/badge/Star-this%20repo-yellow?logo=github&style=social" alt="Star this repository"></a>
    <a href="https://github.com/sponsors/jeremykenedy"><img src="https://img.shields.io/badge/Sponsor-jeremykenedy-EA4AAA?logo=githubsponsors&logoColor=white" alt="Sponsor"></a>
</p>

Show some love by starring this repository on GitHub.

## Table of contents

- [Privacy](#privacy)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Screenshots](#screenshots)
- [Fire TV Toolkit](#fire-tv-toolkit)
- [Building and testing](#building-and-testing)
- [Documentation](#documentation)
- [Release notes](#release-notes)
- [License](#license)

## Privacy

The app requests no network permission and makes no network requests. It contains no ads, analytics, telemetry, crash reporting, tracking, or reporting code. Settings remain on the device. The optional installer contacts GitHub only when you ask it to download a release and checksum; those requests contain no TV or usage data.

## Features

- Continuously animated meteor trails over a layered deep-space starfield.
- Quiet, steady, and storm meteor frequencies, with fine, natural, and bold meteor sizes.
- Sparse through packed star density, three palettes, three speed settings, and night, dusk, or bright scenes.
- Per-setting Random selections and an optional Randomize All setting for each new session.
- Remote-friendly settings, live preview, and a validated settings provider for host apps.
- Android DreamService pauses its drawing loop when the dream stops.
- Original procedural rendering with no bundled videos, third-party images, or runtime dependencies.

## Requirements

- Android 6.0 (API 23) or newer with DreamService support.
- Android SDK platform 36 and build-tools 36.0.0 to build from source.
- ADB for computer-based installation.

The app ran on an Android TV emulator at API 31 and 1920 x 1080. Physical Fire TV, Android TV, and Google TV device behavior has not been tested. See [device verification](docs/VERIFICATION.md) for the test matrix and volunteer request.

## Installation

Connect the device to the same network as your computer, enable ADB debugging, then run:

```bash
python3 install.py --serial TV_IP:5555
```

The installer downloads the latest signed release from this repository, checks its SHA-256 file, and installs or updates the app. Select **Perseid Passage** in the device's screensaver or ambient display settings. To remove it, run `python3 install.py --serial TV_IP:5555 --uninstall` and confirm the prompt. Local APK and unattended install instructions are in [installation and removal](docs/INSTALLATION.md).

## Configuration

Open **Perseid Passage** from the device launcher. The settings apply when the next preview or screensaver session starts.

| Setting | Options | Default |
| --- | --- | --- |
| Meteor frequency | Quiet, Steady, Storm, Random | Steady |
| Meteor size | Fine, Natural, Bold, Random | Natural |
| Meteor speed | Slow, Natural, Fast, Random | Natural |
| Star density | Sparse, Balanced, Dense, Packed, Random | Balanced |
| Color palette | Ice Blue, Violet, Amber, Random | Ice Blue |
| Scene brightness | Night, Dusk, Bright, Random | Dusk |
| Randomize all settings | Off, On | Off |

Each random option selects a supported value for that setting at the start of a session. The global option chooses all six values at each start. See [configuration and the settings provider](docs/CONFIGURATION.md) for details.

## Screenshots

<p align="center">
    <img src="docs/screenshots/perseid-passage-android-tv-1920.png" alt="Perseid Passage running on an Android TV emulator at 1920 by 1080 with animated meteor trails and a deep space background" width="49%">
    <img src="docs/screenshots/settings-android-tv-1920.png" alt="Perseid Passage remote-friendly settings on an Android TV emulator at 1920 by 1080" width="49%">
</p>

The image was captured from the running app on the Android TV emulator. Device details and limitations are recorded in [verification](docs/VERIFICATION.md).

## Fire TV Toolkit

[Fire TV Toolkit](https://github.com/jeremykenedy/fire-tv-toolkit) provides guided screensaver installation, updates, activation, sleep and screensaver timers, and supported settings protection. Clone and set it up on your computer:

```bash
git clone https://github.com/jeremykenedy/fire-tv-toolkit.git
cd fire-tv-toolkit
node setup.js
```

After Perseid Passage has been added to Toolkit's reviewed catalog, install and activate it with:

```bash
firetv-screensavers --install=perseid-passage --yes
screensaver --set=perseid-passage
```

Update and remove it through Toolkit's `firetv-screensavers` checklist. Use `--uninstall=perseid-passage --force` to remove it without the typed confirmation. Toolkit manages device timers and its own supported settings protections; the screensaver's visual options remain in Perseid Passage. Toolkit's catalog integration is being completed separately.

## Building and testing

```bash
bash build.sh
bash test.sh
```

The build signs a local APK with a project-specific key stored outside the repository under `~/.android/`. Keep the key and its password backed up for future signed upgrades. See [building](docs/BUILDING.md), [testing](docs/TESTING.md), and [architecture](docs/ARCHITECTURE.md).

## Documentation

- [Installation and removal](docs/INSTALLATION.md)
- [Configuration and host settings](docs/CONFIGURATION.md)
- [Building from source](docs/BUILDING.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Testing](docs/TESTING.md)
- [Device verification](docs/VERIFICATION.md)
- [Troubleshooting](docs/TROUBLESHOOTING.md)
- [Release process](docs/RELEASING.md)

## Release notes

- [Version 1.0.0](docs/releases/v1.0.0.md)
- [Changelog](CHANGELOG.md)

## License

Perseid Passage is licensed under the [Apache License, Version 2.0](LICENSE). See [NOTICE](NOTICE) for project notices.

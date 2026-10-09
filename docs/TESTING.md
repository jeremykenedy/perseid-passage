# Testing

Run the host-side option validation tests, installer tests, build, and privacy/doc checks with:

```bash
bash test.sh
bash build.sh
python3 scripts/check-privacy.py
python3 scripts/check-docs.py
bash scripts/check-style.sh
```

The Java tests exercise option mapping, defaults, invalid-value fallback, random selection, and host-provider value validation. Python tests cover installer device selection, prompts, URL and redirect restrictions, release metadata, checksum parsing, size limits, temporary file cleanup, install, and uninstall behavior.

The Android TV emulator check installs the APK, opens the settings activity, reads the settings schema, changes settings through the provider, opens the live preview, and verifies that the DreamService can start and stop. See [device verification](VERIFICATION.md) for the exact emulator and known platform gaps. Physical TVs have not been tested.

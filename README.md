# Forward SMS (Secure Gateway)

This project is an Android app (Kotlin) that forwards incoming SMS and missed calls to a Telegram bot, keeps a local retry queue, scans SMS inbox for recovery, and supports replying from Telegram.

Build:
- Open this folder in Android Studio (Arctic Fox or later).
- Let Gradle sync and install required plugins.
- Grant runtime permissions on first run: RECEIVE_SMS, READ_SMS, SEND_SMS, READ_PHONE_STATE, READ_CALL_LOG, FOREGROUND_SERVICE, INTERNET.

Configuration:
- Open Settings screen and set `Bot Token` and `Chat ID` (saved encrypted with EncryptedSharedPreferences).
- Use Test Connection to verify the bot.

Notes:
- The app only allows network requests to `api.telegram.org`.
- Sensitive data (bot token, chat ID) are stored in `EncryptedSharedPreferences`.
- No analytics, no third-party telemetry.

Security:
- Do not enable unnecessary permissions for testing. The app requires SMS and call permissions to operate.
"# forward-sms" 
.
# MobiKazi

<p align="center">
  <img src="app/src/main/res/drawable/mobikazi_logo_1793691891746.jpg" alt="MobiKazi logo" width="180" />
</p>

<h1 align="center">MobiKazi</h1>

<p align="center">
  A modern Android application for capturing applicant details and collecting structured questionnaire responses.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/Build-Gradle-02303A?logo=gradle&logoColor=white" alt="Gradle" />
  <img src="https://img.shields.io/badge/Min%20Android-7.0%20(API%2024)-34A853?logo=android&logoColor=white" alt="Minimum Android version" />
</p>

---

## Overview

**MobiKazi** is an Android mobile application built with Kotlin and Jetpack Compose. It provides a guided flow for users to sign in, enter job-applicant information, review the submitted details, and complete a questionnaire.

The app is organised around a simple multi-screen experience. Applicant information is passed between screens, while the questionnaire is opened through an explicit Android Activity Intent and returns its results to the main application.

## Core functionality

- **Login screen** — an entry point to the application.
- **Applicant information form** — captures the applicant's name, phone number, address and education details.
- **Submission summary** — displays the applicant information after the form is submitted.
- **Questionnaire** — collects gender, age, English and Kiswahili language selections, and an optional other language.
- **Screen-to-screen data transfer** — passes applicant information to the questionnaire Activity and returns questionnaire responses to the main Activity.
- **Edit and navigation flow** — allows users to return to the applicant form and review or update entered information.
- **Logout** — returns the user to the login screen.

> **Note:** The repository currently implements a client-side, in-app flow. The presence of Firebase and networking libraries in the build configuration does not by itself mean that cloud authentication, remote storage, or a production backend is configured.

## Application flow

```text
          Launch MobiKazi
                 |
                 v
             Login screen
                 |
                 v
       Applicant information form
                 |
           Submit details
                 |
                 v
          Submission summary
                 |
          Open questionnaire
                 |
                 v
        Questionnaire Activity
                 |
        Submit questionnaire
                 |
                 v
       Return to submission summary
                 |
          Edit details / Logout
```

## Technology stack

| Technology | Purpose |
| --- | --- |
| Kotlin | Primary application language |
| Android SDK | Native Android platform |
| Jetpack Compose | Declarative user interface |
| Material 3 | UI components and visual styling |
| Gradle Kotlin DSL | Build configuration and dependency management |
| AndroidX Activity Compose | Activity integration and result handling |
| Kotlin Coroutines | Asynchronous programming support |
| Room | Local database library available in the project dependencies |
| Retrofit and OkHttp | HTTP networking libraries available in the project dependencies |
| Moshi | JSON serialization and parsing |
| Firebase AI | Firebase AI SDK dependency configured in the project |

## Project structure

```text
MobiKazi/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/
│       │   │   ├── MainActivity.kt
│       │   │   ├── QuestionnaireActivity.kt
│       │   │   ├── model/
│       │   │   │   └── AppData.kt
│       │   │   └── ui/
│       │   │       ├── LoginScreen.kt
│       │   │       ├── JobApplicationScreen.kt
│       │   │       ├── QuestionnaireScreen.kt
│       │   │       ├── SubmissionDisplayScreen.kt
│       │   │       └── theme/
│       │   └── res/
│       ├── test/
│       └── androidTest/
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── .env.example
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
└── README.md
```

## Requirements

Before building the project, install:

- [Android Studio](https://developer.android.com/studio)
- Android SDK 36 (as configured by the app)
- JDK 11 or a compatible JDK supported by your installed Android Gradle Plugin
- An Android emulator or a physical Android device running Android 7.0 (API 24) or later

Gradle Wrapper files are included, so you can use the project's wrapper rather than installing Gradle separately.

## Getting started

### 1. Clone the repository

```bash
git clone https://github.com/otienofrank27/MobiKazi.git
cd MobiKazi
```

### 2. Open in Android Studio

1. Launch Android Studio.
2. Select **Open**.
3. Choose the cloned `MobiKazi` directory.
4. Allow Gradle to sync and download the required dependencies.
5. Install any SDK components requested by Android Studio.

### 3. Configure environment variables

The repository includes an `.env.example` template for the Gradle Secrets Plugin.

If you enable functionality that calls the Gemini API, create a local `.env` file in the project root and configure your key:

```env
GEMINI_API_KEY=your_gemini_api_key
```

Get an API key from [Google AI Studio](https://aistudio.google.com/app/apikey).

Keep your real `.env` file and API keys private. Do not commit secrets to GitHub. The example file is a template; it does not contain a working API key.

### 4. Run the application

1. Connect an Android device with USB debugging enabled, or start an emulator.
2. Select the device from Android Studio's device selector.
3. Click **Run** or execute the following command from the project root:

```bash
# Windows
gradlew.bat installDebug

# macOS / Linux
./gradlew installDebug
```

## Build from the command line

Build a debug APK:

```bash
# Windows
gradlew.bat assembleDebug

# macOS / Linux
./gradlew assembleDebug
```

The APK is generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Run unit tests:

```bash
# Windows
gradlew.bat test

# macOS / Linux
./gradlew test
```

Run Android instrumented tests (requires a connected device or running emulator):

```bash
# Windows
gradlew.bat connectedAndroidTest

# macOS / Linux
./gradlew connectedAndroidTest
```

## Configuration notes

- The Android application uses **minSdk 24** and **targetSdk 36**.
- The application module is configured with **versionName 1.0** and **versionCode 1**.
- The app uses a debug signing configuration for local debug builds.
- Release signing is configured to read keystore settings from environment variables. Set `KEYSTORE_PATH`, `STORE_PASSWORD`, and `KEY_PASSWORD` in your secure build environment before creating a release build.
- Never commit upload keystores, signing passwords, API keys, or other credentials.
- If you configure Firebase services, add the appropriate Firebase project configuration for your Android application and follow the official Firebase Android setup instructions.

## Current scope and future improvements

MobiKazi provides the foundation for a mobile applicant-information and questionnaire workflow. Potential next steps include:

- Secure user authentication and account management.
- Persistent applicant profiles and questionnaire submissions.
- Backend/API integration for submitting and retrieving applications.
- Input validation, clearer error states and loading feedback.
- Automated UI and integration test coverage.
- Release signing, app bundle generation and Google Play deployment.

These are roadmap ideas, not claims that the features are already implemented.

## Contributing

Contributions, bug reports and feature suggestions are welcome.

1. Fork the repository.
2. Create a feature branch:

   ```bash
   git checkout -b feature/your-feature
   ```

3. Make focused changes and test them.
4. Commit your work:

   ```bash
   git commit -m "Add: describe your change"
   ```

5. Push your branch and open a Pull Request.

## License

No license file is currently included in this repository. Unless a license is added, the project remains under the default copyright terms of its owner; do not assume it is open source for reuse or redistribution.

## Repository

[**otienofrank27/MobiKazi**](https://github.com/otienofrank27/MobiKazi)

---

<p align="center">
  Built with Kotlin and Jetpack Compose.
</p>

# Secure Auth Key Android Project

## Overview

This project demonstrates an Android Kotlin application that integrates with an API using a **custom encrypted key** generated via a **C-based encryption mechanism**. The app ensures secure communication by encrypting the authorization key before sending API requests.

---

## **Project Structure**

```
SecureApiKey/
├── app/
│   ├── src/main/cpp                    # C code for key generation
│   ├── src/main/java/....     
│   │                ├── data 
│   │                ├── di
│   │                ├── domain
│   │                ├── ui
│   │                ├── utils
│   │                └── ....                    
│   └── ...
├── src/test/kotlin/...                  # JVM unit tests
├── src/androidTest/kotlin/...           # Android instrumented tests
└── README.md
```

---

## **Features**

* Kotlin Android application with API integration.
* Native C code using NDK for encryption/key generation.
* Unit tests for:

    * `NativeAuthKey` key generation (mocked for JVM, real for instrumented).
    * Repository API calls (`AnimeRepositoryImpl`).
* Performance testing for key generation under load.

---

## **Getting Started**

### **Prerequisites**

* Android Studio Bumblebee or later
* Android NDK installed
* Kotlin 1.9+
* Gradle 8.x
* Connected Android device or emulator for instrumented tests

---

### **Build & Run the Project**

1. Clone the repository:

```bash
git clone https://github.com/yourusername/SecureApiKey.git
cd SecureApiKey
```

2. Open in **Android Studio**.

3. Build the project:

    * `Build -> Make Project`

4. Run on an emulator or device:

    * `Run -> Run 'app'`

---

## **C Encryption Mechanism**

* The key generation is implemented in `jni_facade.cpp`:
* **encryptPayload** implements the actual encryption.
* The generated key is returned as a string to Kotlin.
* Kotlin calls `NativeAuthKey.generateAuthKey()` via `external` method.

---

## **API Integration**

* Repository: `AnimeRepositoryImpl`
* API service interface: `ApiService`
* Safe API call wrapper: `safeApiCall { apiService.getAnimeList() }`
* Example of usage:

```kotlin
val animeList = AnimeRepositoryImpl(apiService).getAnimeList()
```

---

## **Unit Tests**

### **Key Generation Tests**

* **JVM Tests:** MockK is used to mock native calls.
* **Instrumented Tests:** Run on device/emulator to test real native library.
* Performance test for load (1000+ calls).

### **Repository Tests**

* Success scenario: API returns a list of anime.
* Empty list scenario.
* API exception handling.
* Null response handling.

**Run tests:**

```bash
# JVM tests
./gradlew test

# Android instrumented tests
./gradlew connectedAndroidTest
```

---

## **How Encryption Works**

1. Kotlin passes a payload string to the C function via JNI.
2. C function encrypts the payload using the encryption mechanism (digital envelope or chosen algorithm).
3. The encrypted string is returned to Kotlin as the `AuthKey`.
4. This key is added to API requests in a custom header:

```kotlin
val authKey = NativeAuthKey.generateAuthKey()
val response = apiService.getAnimeList(authKey)
```

---

## **Notes**

* JVM tests **mock the native library** to allow fast testing without NDK.
* Instrumented tests **call the real native code** on a device/emulator.
* The project follows clean architecture principles with repository, API service, and safe API calls.

---

## **Contact**

For any questions or issues, contact: **Your Name** – `wsayan28@example.com`

---

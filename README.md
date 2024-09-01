# Notification Core

[![](https://jitpack.io/v/freeskyES/notification-core.svg)](https://jitpack.io/#freeskyES/notification-core)

## Overview

`notification-core` is a module designed to efficiently handle notifications in Android applications. It follows Clean Architecture principles, focusing on receiving notifications, processing them in the background using a thread pool, and storing them in a Room database. This ensures a scalable and maintainable codebase, making it easier to manage notifications within your app.

## Features

- **Notification Reception**: Uses `NotificationListenerService` to listen for incoming notifications in real-time.
- **Foreground Service**: Initiates a foreground service to ensure consistent execution of the notification handling process.
- **Thread Pool Management**: Processes notifications concurrently while maintaining the order of operations to ensure no notification is lost.
- **Room Database Integration**: Parses and stores notifications in a Room database for persistent storage, allowing access to notification data even after an app restart.
- **Hilt Dependency Injection**: The module leverages Hilt for dependency injection, streamlining the integration process.

## Getting Started

### Installation

To use the `notification-core` module, add the following dependency to your `build.gradle` file:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.freeskyES:notification-core:1.0.0'
}
```

### Setup

Ensure that your project is configured to use **Hilt** for dependency injection.

### Usage

1. **Utilize NotificationInitializer**

   Use the `NotificationInitializer` interface to initialize and start the notification handling service.

   ```kotlin
   @AndroidEntryPoint
   class MainActivity : AppCompatActivity() {

       @Inject
       lateinit var notificationInitializer: NotificationInitializer

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)

           // Initialize and start the service
           notificationInitializer.initializeAndStartService()
       }
   }
   ```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
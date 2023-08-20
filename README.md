# Pokemon
<p align="left">
"Pokemon is an exciting mobile application suitable for all ages, based on the popular Pokemon franchise. Immerse yourself in a world of adventures and discover everything about Pokemon."
<p align="left">
In the application of saving all data in the database, therefore, there is support for offline mode.

## Features
* 100% Kotlin
* MVVM architecture
* Kotlin Coroutines
* Single activity pattern
* Dependency injection
* Paging 3

# Architecture
The Pokemon app follows the official architecture guidance.

## Tech Stacks
* [Retrofit](http://square.github.io/retrofit/) + [Gson](https://github.com/google/gson) - RESTful API and networking client.
* [Hilt](https://dagger.dev/hilt/) - Dependency injection.
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - A collections of libraries that help you design rebust, testable and maintainable apps.
    * [Room](https://developer.android.com/training/data-storage/room) - Local persistence database.
    * [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) - UI related data holder, lifecycle aware.
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable data holder that notify views when underlying data changes.
    * [Navigation component](https://developer.android.com/guide/navigation) - Fragment routing handler.
* [Coroutine](https://developer.android.com/kotlin/coroutines) Concurrency design pattern for asynchronous programming.
* [Picasso](https://square.github.io/picasso/) - Image loading.

  

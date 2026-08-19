# HabitFlow

[![CI](https://github.com/yakupaluc1/HabitFlow/actions/workflows/ci.yml/badge.svg)](https://github.com/yakupaluc1/HabitFlow/actions/workflows/ci.yml)

An offline-first habit tracking app for Android, built with Jetpack Compose and a clean, layered architecture.

> ⚠️ Work in progress — this project is being built incrementally as a portfolio piece.

## Screenshots

<p align="center">
  <img src="docs/screenshots/habit-list.png" width="300" alt="Habit list screen" />
</p>

## Features

- Create and list habits, persisted locally
- Reactive UI that updates automatically when data changes
- Fully functional offline — no network required

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Architecture:** MVVM with a Data / Domain / UI layering
- **Dependency Injection:** Hilt
- **Persistence:** Room
- **Asynchrony:** Coroutines & Flow

## Architecture

The app is split into three layers with a strict dependency direction (UI → Domain → Data):

- **UI** — Compose screens and ViewModels. Holds screen state as `StateFlow`, exposed to the UI via `collectAsStateWithLifecycle`.
- **Domain** — Pure Kotlin models and a `HabitRepository` interface. Knows nothing about Android or Room, which keeps it easy to test.
- **Data** — Room database, DAO, the repository implementation, and mappers that convert database entities to domain models.

This separation means the UI never touches Room directly; it only depends on the repository interface. Swapping the data source would leave the UI untouched.

## Roadmap

- [x] Mark habits as completed with daily streak tracking
- [x] Swipe to archive / delete
- [ ] Reminders via WorkManager
- [ ] Unit and UI tests
- [x] CI with GitHub Actions

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Run the `app` configuration on an emulator or device (min SDK 24)

## Author

Yakup Aluc
[GitHub](https://github.com/yakupaluc1)

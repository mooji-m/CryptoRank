# CryptoRank 📈

A clean, light-weight, and native Android application built 3 years ago to track real-time cryptocurrency data, live market trends, global crypto news, and price charts. This repository serves as a practical blueprint and an ideal playground for developers wanting to learn, practice, or test REST API integration and asynchronous data handling in Kotlin.

<div align="center">
<p align="center">
  <video src="https://github.com/user-attachments/assets/0990398f-956c-43e5-ba5f-22fe6d2b8ded
" width="300" controls>
    Your browser does not support the video tag.
  </video>
</p>
</div>

---
## ✨ Features

- **Live Cryptocurrency Tracker:** Displays a real-time list of top cryptocurrencies with prices (formatted dynamically), 24-hour price changes (with dynamic green/red indicators), and total market capitalization.
- **Interactive Price Charts:** Integrates custom sparkline charts to visualize historical price movements over various time periods (Hours, 24H, Week, Month, Year).
- **Crypto News Feed:** Fetches and displays the latest and trending worldwide crypto news using high-performance animations.
- **Offline Currency Metadata:** Parses a local asset JSON database mapping additional web links, GitHub repositories, and descriptions for each coin.
- **Pull-to-Refresh:** Implements continuous swipe gestures to dynamically fetch the latest network payload on demand.

---

## 🛠️ Tech Stack & Architecture

- **Language:** 100% Kotlin with optimized Kotlin-Options (`JavaVersion.VERSION_1_8`).
- **UI Framework & Design:** Material Components, XML layouts, ViewBinding (for safe view access), and Custom Alpha Animations.
- **Networking:** `Retrofit 2` paired with `Gson Converter` for structural asynchronous REST API handling.
- **Image Loading:** `Glide` for asynchronous image downloading, caching, and rendering coin logos smoothly.
- **Data Visualization:** `Robinhood Spark` library for lightweight, high-performance rendering of historical crypto graphs.
- **Data Source:** Crypto APIs (Top volumes and historical data endpoints) with customized header credentials.

---

## 📂 Project Structure Snippet (API & Features)

The core business logic is structured into distinct packages separating network architecture and user features:

- **`apiManager/`**: Contains `ApiManager` and `ApiService` which handle Retrofit setup, synchronous/asynchronous enqueue queues, HTTP headers, and continuous callback interfaces.
- **`marketActivity/`**: Features `MarketActivity` and `MarketAdapter`, controlling the data streams, SwipeRefreshLayout listeners, and mapping metadata using high-performance Gson asset deserialization.

---

## 🚀 How to Run

1. Clone this repository:
   ```bash
   git clone [https://github.com/mooji-m/CryptoRank.git](https://github.com/mooji-m/CryptoRank.git)

2.  Open the project inside Android Studio.

3. API Key Setup: Go to CryptoCompare, generate a free API key, and paste it into the API_KEY constant inside ApiService.kt.

4. Sync Gradle files, build, and run the application on your device/emulator.

---
## 📝 Developer Note
This project was developed 3 years ago as part of my evolutionary journey into native Android and API ecosystem integration. While some libraries or target specifications reflect that specific era, it showcases a rock-solid foundation in asynchronous network architecture, UI state rendering, and decoupled logic. Left open-source to archive my software development timeline.

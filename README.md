MrManage - Business Management Android App
MrManage is a lightweight MVP Android application designed to help micro, small, and medium enterprises (MSMEs) manage their business operations more efficiently. Developed in my personal time, this project is the result of my learning journey in mobile app development, and my desire to build something meaningful for the underserved segment of small business owners who still rely on pen and paper for day-to-day operations.

📌 Why MrManage?
A significant percentage of businesses—especially local shops and MSMEs—still maintain records manually. According to a 2022 NASSCOM and Zinnov report, over 90% of Indian MSMEs are not digitally transformed, and rely on offline systems like paper ledgers. (Source)

MrManage aims to solve this by offering:

An offline-capable app using Room (SQLite)

A minimalist, user-friendly interface

A nearly free platform, with no recurring costs

🧩 Architecture & Design
This is a local-first application that uses:

SQLite with Room for local persistence

Java as the core language (quick to build and comfortable for MVP development)

No server or backend infrastructure, keeping costs low

🔄 Future Direction – Cloud-Enabled Backup
I'm exploring the possibility of using Google Drive as a decentralized and secure document store:

Each user connects their Google account

Their data is backed up to a JSON/text file in their personal Drive

This removes the need for a central backend/database

Project Structure:
MrManage/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/edu/northeastern/MrManage/
│   │   │   │   ├── ApplicationInitiate.java
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── Executors/
│   │   │   │   ├── roomApi/
│   │   │   │   │   ├── MrManageDatabase.java
│   │   │   │   │   ├── dao/
│   │   │   │   │   ├── entities/
│   │   │   │   │   ├── repositories/
│   │   │   │   │   └── view_model/
│   │   │   │   ├── utility/
│   │   │   │   │   └── interfaces/
│   │   │   │   └── view/
│   │   │   │       ├── actions/
│   │   │   │       ├── activity/
│   │   │   │       ├── adapters/
│   │   │   │       ├── dialogs/
│   │   │   │       ├── interfaces/
│   │   │   │       └── viewholders/
│   │   ├── res/
│   │   │   ├── color/
│   │   │   ├── drawable/
│   │   │   ├── drawable-v24/
│   │   │   ├── layout/
│   │   │   ├── mipmap-*/ (various densities)
│   │   │   ├── values/
│   │   │   ├── values-night/
│   │   │   └── xml/
│   │   └── test/java/edu/northeastern/MrManage/
│   │       └── ExampleUnitTest.java
├── build.gradle
├── gradle/
│   └── wrapper/
├── generate_tree.py



Benefits:

Eliminates server costs

Provides resilience against app uninstall or device loss

Uses Drive API for seamless sync

⚠️ Challenges Considered
App re-installation causes local SQLite data loss

No centralized DB means cross-device syncing is challenging

App updates and schema migration strategies must be carefully planned

Drive API integration needs authentication, permission handling, and reliability mechanisms

💡 What I’ve Learned
How Room/SQLite can build functional apps even offline

Trade-offs of local vs cloud-first apps

How to build fast by leveraging known technologies

UX considerations for first-time digital users

🔧 Tech Stack
Language: Java

Database: SQLite (Room)

Architecture: MVVM (planned)

Tools: Android Studio, Drive REST API (planned)


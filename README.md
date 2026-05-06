# *Steam PC Builder*

## Description
A JavaFX application that help users find find/design a desktop that perfectly suites all their gaming needs centered around the worlds biggest game provider for PC. Made for even the most tech-illiterate of users who just want to game, even tech-savvy users will find thiier own pace designing desktops part by part.  

## Features
- Search Steam games
- View minimum/recommended specs
- PC build generation
- Hardware compatibility checking
- Steam account integration
- Search autofill
- Firebase database support
- Responsive JavaFX UI

## Tech Stack
- Java
- JavaFX
- FXML
- CSS
- Maven
- Firebase
- Google Cloud Firestore
- Git
- GitHub
- jpackage (for .exe)

## Installation

```bash
Download the SteamBuilder .zip file
Unzip it
Run through Intellij or follow steps below and add the .exe file through steam through 'add non-steam game' at the bottom left of library
```

## Building EXE

```bash
1.  cd "Your javaFx project path"
2.  .\mvnw.cmd clean package dependency:copy-dependencies "-DincludeScope=runtime"
3.  mkdir package-55
4.  copy target\CSC325-Capstone-1.0-SNAPSHOT.jar package-55\
5.  copy target\dependency\*.jar package-55\
6.  jpackage --type app-image --name CSC325-Capstone --runtime-image "Path to your Java 25 from ProgramFiles" --input package-55 --main-jar CSC325-Capstone-1.0-SNAPSHOT.jar --main-class edu.farmingdale.csc325capstone.Main --dest Runner --icon SteamBuilderLogo.ico
```

## Game Search
User selects a game/games making a library that will be used to recommend pre-builts

### Functions
- Creates complete PC builds automatically
- Returns top 3 most relevant recommendations to users library
- Supports multiple performance tiers and budgets
- Provides user with the link to thier recommended pre-built so they can check it out for themselves

<img width="400" height="328" alt="GameSearchAdding" src="https://github.com/user-attachments/assets/18deb5e1-1a8e-496b-9151-fc5d36c1581a" />   <--- Searching through Steam Games

<img width="400" height="329" alt="GameSearchReccomend" src="https://github.com/user-attachments/assets/748fc33f-4b81-4da7-a89d-153b4604b0c3" />  <--- Giving pre-built recommendations

## Questionnaire Mode
USer answers through a set of questions to pin point what pre-built would best suite their needs
- Accurate recommendations to users response
- Returns top 3 most relevant recommendations to user, even more so with the narrow scope of the questions asked
- Supports multiple performance tiers and budgets
- Considers factors like fps, resolution, and storage of pre-built when questioning user

<img width="400" height="329" alt="Questionnaire" src="https://github.com/user-attachments/assets/919e33bf-70b2-4b39-9423-f02abe939397" /> <--- Going through every question presented










## Build Management System
Users can save, organize, and interact thier own built PC's

### Functions
- Stores generated builds
- Displays builds dynamically in lists
- Allows build comparison
- Supports editing and updating builds
- Organizes hardware configurations
- Enables future expansion for exporting/sharing builds




## Authors
- Logan Raycraft

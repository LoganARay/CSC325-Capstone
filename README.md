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
git clone YOUR_REPO_LINK
cd YOUR_PROJECT
mvn clean javafx:run
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

## Screenshots

(Add screenshots here later)

## Authors
- Logan Raycraft

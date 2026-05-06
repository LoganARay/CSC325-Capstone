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

##HomeScreen
Welcoming, calm introduction to Steam Builder and a clear showing what feature it contains
- Has access to every feature and function with just one scene away (Except Register)
- Maintains clear labels and organization
- Made to look professional and mimic Steams style of color scheme and overall feel
- Custom logo made to emulate steams while staying faithful to the purpose of this application

  <img width="1177" height="961" alt="image" src="https://github.com/user-attachments/assets/fc71e0bc-1538-4385-a2c9-d280a0a2d176" /> <--- First thing you see when logging in

##Login/Register
Simple login and register scenes for users to interact with
- Saves users builds to view later
- Only accepts '@gmail' and no email can be used twice
- Requires complex password, 8 characters long, capital letter or number, and must contain special character
- Isnt manditory for most of the feature. following the design of being user friendly

<img width="400" height="329" alt="Login" src="https://github.com/user-attachments/assets/6a333bf9-5d90-4782-8077-8d410924dd14" /> <--- Loging in

<img width="400" height="329" alt="Register" src="https://github.com/user-attachments/assets/14cf8d3d-10c8-4c0c-a3f3-1938e2351e16" /> <--- Registering


  

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


##Sandbox
For the more tech-savvy users, building a desktop part by part
- Holds an large variety of PC parts fomr the last
- Allows user to save builds onto their account 
- Parts are dynamic! If you select one part that incompatable with another that incompatable part wont show up
- Users can have AI analyze their build and give feed back and recommendation
- Contians side bar featuring the selected parts important stats like price

<img width="400" height="333" alt="SandBoxStuuuuf" src="https://github.com/user-attachments/assets/68ab1af0-5479-4b62-a8ff-c0ab2c513cd6" />  <--- Selecting PC parts

<img width="400" height="403" alt="SandBoxAnalyze" src="https://github.com/user-attachments/assets/cdbd5b51-a200-471a-8894-2e2fa424bceb" /> <--- AI Analyze

<img width="400" height="659" alt="SandBoxScrool" src="https://github.com/user-attachments/assets/5c73ad55-a475-477f-ab98-29fb860ea9fd" />  <--- Parts scroll bar











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

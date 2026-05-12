# *Steam Builder*

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
- AI Analyzer for selected builds

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
- gpt-5.4-mini
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

```json
{
  "AppId": "",
  "Minimum": {
    "Graphics": "",
    "Memory": "",
    "Processor": "",
    }
  "Name": "",
  "Recommended": {
    "Graphics": "",
    "Memory": "",
    "Processor": "",
    }
  "Storage": ""
}
```

## Questionnaire Mode
USer answers through a set of questions to pin point what pre-built would best suite their needs
- Accurate recommendations to users response
- Returns top 3 most relevant recommendations to user, even more so with the narrow scope of the questions asked
- Supports multiple performance tiers and budgets
- Considers factors like fps, resolution, and storage of pre-built when questioning user

<img width="400" height="329" alt="Questionnaire" src="https://github.com/user-attachments/assets/919e33bf-70b2-4b39-9423-f02abe939397" /> <--- Going through every question presented

## Parts Database

Database Schema

```json
{
  "id": "",
  "name": "",
  "category": "",
  "brand": "",
  "price": "",
  "link": "",
  "year": "",
  "specs": {
    "type": "",
    "interface": "",
    "capacity": "",
    "price_per_gb": "",
    "color": ""
  }
}
```




## Sandbox

For the more tech-savvy users, building a desktop part by part
- Holds an large variety of PC parts from the list
- Allows user to save builds onto their account 
- Parts are dynamic! If you select one part that are incompatible with another that incompatible part wont show up
- Users can have AI analyze their build and give feed back and recommendation
- Contains side bar featuring the selected parts important stats like price

<img width="400" height="333" alt="SandBoxStuuuuf" src="https://github.com/user-attachments/assets/68ab1af0-5479-4b62-a8ff-c0ab2c513cd6" />  <--- Selecting PC parts

<img width="400" height="403" alt="SandBoxAnalyze" src="https://github.com/user-attachments/assets/cdbd5b51-a200-471a-8894-2e2fa424bceb" /> <--- AI Analyze

<img width="400" height="659" alt="SandBoxScrool" src="https://github.com/user-attachments/assets/5c73ad55-a475-477f-ab98-29fb860ea9fd" />  <--- Parts scroll bar

## Part Class
- Represents a single PC component object within the application
- Stores general part information such as name, brand, category, price, and link
- Maps data retrieved from the Firebase database into Java objects
- Contains a flexible specs field for component-specific attributes
- Used throughout the application for displaying, filtering, and validating parts
- Helps keep the project object-oriented and organized by modeling real-world PC components as Java objects

## PartService Class
- Fetches the actual parts from the Firebase Firestore database and loads them onto part objects based on the category of the parts
- Each document representing one specific part makes updates and retrieval straightforward
- Helps the frontend load dropdown menus efficiently by fetching only the needed collection
- Makes compatibility logic easier to implement, such as matching CPU and motherboard sockets

# 🔧 Compatibility Logic & Live Filtering – Steam Builder

This document explains how the compatibility checker and dynamic dropdown filtering work in the Steam Builder PC‑building sandbox.

---

## 📦 Data Loading

### Lightweight Summary Lists
- At startup, `HelloApplication.start()` loads minimal part info (name, id) from Firestore.
- These are fast to fetch and immediately populate the combo boxes in the sandbox.

### Full Part Maps (Background)
- `loadAllPartsAsync()` runs on a separate thread and fetches **complete** `Part` objects for every category.
- The objects are stored in static `Map<String, Part>` fields (e.g., `cpuPartMap`).
- The sandbox waits for these maps to be ready before enabling filtering.

### Local Part Pools
- `SandboxViewController.initFilterData()` copies the static maps into local lists (`allCpuParts`, `allMoboParts`, …).
- These lists serve as the **source pools** for filtering – all combinations are tested against them.

---

## 🧪 Compatibility Rules (`CompatibilityChecker.java`)

Each static method takes two `Part` objects and returns `true` if they are compatible.

| Connection              | Check                                                   | Missing spec behaviour |
|-------------------------|---------------------------------------------------------|------------------------|
| CPU ↔ Motherboard       | `socket` must match exactly                             | Incompatible (strict)  |
| RAM ↔ Motherboard       | `type` must match `ram_type`; speed checked if max known | Incompatible (strict)  |
| GPU ↔ Case              | GPU `length` ≤ case `max_gpu_length` (or case‑type fallback) | Incompatible (strict)  |
| Motherboard ↔ Case      | `form_factor` must be supported by case's type or explicit list | Incompatible (strict)  |
| PSU ↔ Estimated wattage  | PSU `wattage` ≥ (CPU TDP + GPU TDP + 50W buffer)        | Insufficient           |

All methods return `true` when one of the parts is `null` (no selection).

---

## ⚙️ Filtering Process

### 1. Initialisation
- Combo boxes are filled with lightweight summary lists.
- If full maps are empty, a "Loading parts…" message appears and the UI waits.
- Once all critical maps (CPU, Motherboard, RAM) are non‑empty:
  - `initFilterData()` builds the source pools.
  - Listeners are attached.
  - The first price/wattage update runs.

### 2. Selection Changed (`onSelectionChanged()`)
1. A guard (`filtering` flag) prevents recursive updates.
2. `applyFilters()` runs.
3. `updateTotalPriceAndWattage()` calculates totals, shows alerts, and updates the details panel.

### 3. Applying Filters (`applyFilters()`)
- Retrieves the currently selected `Part` objects from local maps.
- For **each combo box**, calls `filterCombo()` with:
  - The full source pool (`allCpuParts`, etc.).
  - A **predicate** that invokes the relevant `CompatibilityChecker` method.

  Example for CPUs:
  ```java
  cpu -> {
      if (moboSel == null) return true;
      return CompatibilityChecker.isCpuMotherboardCompatible(cpu, moboSel);
  }

## Build Management System
Users can save, organize, and interact thier own built PC's

### Functions
- Stores generated builds
- Displays builds dynamically in lists
- Allows build comparison
- Supports editing and updating builds
- Organizes hardware configurations
- Enables future expansion for exporting/sharing builds


### GUI Logic and CSS
- Most root containers are ArchorPanes or Vbox to allow for flexiblity of element placements
- Nearly all elements are nested into vbox or hbox to make resizing seamless
  - Vgrow or Hgrow is enabled to ensure proper scaling  
- FXML files for saved builds and sandbox function similarly
  - Both utilize combo boxes allowing users to pick a single item from a drop down 
- Sandbox is the most complex:
  - Combo boxes decicated to PC part picking
  - Scroll pane shows details of parts
  - Text area displays AI recommendation text
- CSS includes different classes for each level of text:
  - Title
  - Section header
  - Regular text
- Padding, spacing, and margins all used to create space between elements and containers



## Authors
- Logan Raycraft
- Fransisco Payes
- Junhui Yu
- Hope Jordan

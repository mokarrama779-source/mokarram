# 📝 MyNotes - Android App

Ek simple aur elegant notes app Android ke liye, built with **Kotlin** aur **Jetpack Compose**.

## ✨ Features

- ✅ **Create Notes** - Naye notes create karein title aur content ke saath
- ✅ **Edit Notes** - Existing notes ko edit karein
- ✅ **Delete Notes** - Notes ko delete karein
- ✅ **Search** - Notes mein se title aur content search karein
- ✅ **Dark Mode** - Dark mode toggle kare
- ✅ **Local Storage** - SharedPreferences mein notes save hote hain

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Storage**: SharedPreferences (JSON)
- **Build System**: Gradle
- **Min SDK**: API 24
- **Target SDK**: API 34

## 📋 Requirements

- Android SDK 24 (API Level 24) ya usse zyada
- Android Studio Flamingo ya naya version
- Kotlin 1.9.10 ya usse zyada

## 🚀 Installation & Setup

1. Repository ko clone karein:
```bash
git clone https://github.com/mokarrama779-source/mokarram.git
cd mokarram
```

2. Android Studio mein open karein

3. Gradle sync karein (Android Studio automatically karega)

4. Emulator ya device connect karein

5. App ko run karein (Ctrl+R ya Run button press karein)

## 📱 Project Structure

```
mokarram/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/mynotes/
│   │   │   │   └── MainActivity.kt          (Main app logic)
│   │   │   ├── AndroidManifest.xml          (App configuration)
│   │   │   └── res/                         (Resources)
│   │   └── test/                            (Unit tests)
│   └── build.gradle.kts                     (App dependencies)
├── build.gradle.kts                         (Root gradle config)
├── settings.gradle.kts                      (Gradle settings)
└── README.md                                (This file)
```

## 💡 How to Use

1. **Naya Note Create Karne ke liye**:
   - FloatingActionButton (➕ icon) click karein
   - Title aur content enter karein
   - **Save** button click karein

2. **Note Edit Karne ke liye**:
   - Note card click karein
   - Changes karein
   - **Save** button click karein

3. **Note Delete Karne ke liye**:
   - Note card mein **Delete** icon (🗑️) click karein

4. **Notes Search Karne ke liye**:
   - Search bar mein title ya content type karein
   - Real-time filtering hogi

5. **Dark Mode Toggle Karne ke liye**:
   - Top right corner mein **Moon** icon (🌙) click karein

## 🎨 Main Components

| Component | Description |
|-----------|-------------|
| `MainActivity` | Main activity jo Compose UI ko manage karta hai |
| `Note` | Data class - note ki structure define karta hai |
| `NotesApp()` | Main composable function - UI ka baap |
| `NoteEditor()` | Dialog composable - create/edit ke liye |

## 🔧 Key Functions

```kotlin
// Notes ko save karte hain SharedPreferences mein
saveNotes(context, notes)

// SharedPreferences se notes load karte hain
loadNotes(context): MutableList<Note>

// Main UI render karte hain
@Composable
fun NotesApp(context: Context)

// Note create/edit dialog dikhaate hain
@Composable
fun NoteEditor(...)
```

## 💾 Data Storage

Notes **SharedPreferences** mein JSON format mein save hote hain:

```json
[
  {
    "id": 1234567890,
    "title": "My First Note",
    "text": "This is the content of my note"
  }
]
```

## 🎯 Features Details

### Create/Edit Notes
- Title: Mandatory ✓
- Content: Mandatory ✓
- Auto-save on device ✓

### Search Functionality
- Title search ✓
- Content search ✓
- Case-insensitive ✓
- Real-time ✓

### Dark Mode
- Toggle kare jab marzi ✓
- Full app theme change ✓
- User preference saved ✓

## 🐛 Troubleshooting

**Gradle Sync Error**:
```bash
./gradlew clean
./gradlew build
```

**Emulator Issues**:
- Emulator ko restart karein
- Android Studio mein Tools > Device Manager kholen

**App Crashes**:
- Logcat check karein (Android Studio mein)
- SharedPreferences corruption ke liye app cache clear karein

## 📝 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

Created by **mokarrama779-source**

---

## 🎓 Next Steps

- 🔐 Authentication add kar sakte ho
- ☁️ Cloud sync implement kar sakte ho
- 📁 Categories add kar sakte ho
- 🏷️ Tags feature add kar sakte ho
- 📊 Analytics add kar sakte ho

**Happy Note Taking! 📚✨**

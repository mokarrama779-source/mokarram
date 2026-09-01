package com.example.mynotes

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import org.json.JSONObject

data class Note(
    val id: Long,
    var title: String,
    var text: String
)

class MainActivity : ComponentActivity() {

    private val prefsName = "notes_data"
    private val key = "notes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesApp(this)
        }
    }

    private fun saveNotes(context: Context, notes: List<Note>) {
        val array = JSONArray()

        notes.forEach {
            val obj = JSONObject()
            obj.put("id", it.id)
            obj.put("title", it.title)
            obj.put("text", it.text)
            array.put(obj)
        }

        context.getSharedPreferences(
            prefsName,
            Context.MODE_PRIVATE
        ).edit().putString(key, array.toString()).apply()
    }

    private fun loadNotes(context: Context): MutableList<Note> {

        val result = mutableListOf<Note>()

        val data = context.getSharedPreferences(
            prefsName,
            Context.MODE_PRIVATE
        ).getString(key, null) ?: return result

        val array = JSONArray(data)

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)

            result.add(
                Note(
                    obj.getLong("id"),
                    obj.getString("title"),
                    obj.getString("text")
                )
            )
        }

        return result
    }

    @Composable
    fun NotesApp(context: Context) {

        var notes by remember {
            mutableStateOf(loadNotes(context))
        }

        var search by remember {
            mutableStateOf("")
        }

        var darkMode by remember {
            mutableStateOf(false)
        }

        var showEditor by remember {
            mutableStateOf(false)
        }

        var editingNote by remember {
            mutableStateOf<Note?>(null)
        }

        MaterialTheme(
            colorScheme =
                if (darkMode)
                    darkColorScheme()
                else
                    lightColorScheme()
        ) {

            Scaffold(

                topBar = {

                    TopAppBar(

                        title = {
                            Text("My Notes 📝")
                        },

                        actions = {

                            IconButton(
                                onClick = {
                                    darkMode = !darkMode
                                }
                            ) {
                                Icon(
                                    Icons.Default.DarkMode,
                                    contentDescription = "Dark Mode"
                                )
                            }
                        }
                    )
                },

                floatingActionButton = {

                    FloatingActionButton(
                        onClick = {
                            editingNote = null
                            showEditor = true
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }

            ) { padding ->

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(12.dp)
                ) {

                    OutlinedTextField(

                        value = search,

                        onValueChange = {
                            search = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        placeholder = {
                            Text("Search notes...")
                        },

                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null
                            )
                        },

                        singleLine = true
                    )

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    val filtered = notes.filter {

                        it.title.contains(
                            search,
                            ignoreCase = true
                        ) ||

                        it.text.contains(
                            search,
                            ignoreCase = true
                        )
                    }

                    LazyColumn {

                        items(
                            filtered,
                            key = { it.id }
                        ) { note ->

                            Card(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 5.dp
                                    )
                                    .clickable {

                                        editingNote = note
                                        showEditor = true
                                    }

                            ) {

                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                ) {

                                    Text(
                                        note.title,
                                        style = MaterialTheme
                                            .typography
                                            .titleMedium
                                    )

                                    Spacer(
                                        Modifier.height(5.dp)
                                    )

                                    Text(
                                        note.text,
                                        maxLines = 3
                                    )

                                    Spacer(
                                        Modifier.height(8.dp)
                                    )

                                    Row {

                                        IconButton(

                                            onClick = {

                                                notes.remove(note)

                                                saveNotes(
                                                    context,
                                                    notes
                                                )

                                                notes =
                                                    notes.toMutableList()
                                            }

                                        ) {

                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription =
                                                    "Delete"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (showEditor) {

                NoteEditor(

                    oldNote = editingNote,

                    onClose = {
                        showEditor = false
                    },

                    onSave = { title, text ->

                        if (editingNote == null) {

                            notes.add(
                                Note(
                                    System.currentTimeMillis(),
                                    title,
                                    text
                                )
                            )

                        } else {

                            editingNote!!.title = title
                            editingNote!!.text = text
                        }

                        saveNotes(
                            context,
                            notes
                        )

                        notes =
                            notes.toMutableList()

                        showEditor = false
                    }
                )
            }
        }
    }

    @Composable
    fun NoteEditor(
        oldNote: Note?,
        onClose: () -> Unit,
        onSave: (String, String) -> Unit
    ) {

        var title by remember {
            mutableStateOf(oldNote?.title ?: "")
        }

        var text by remember {
            mutableStateOf(oldNote?.text ?: "")
        }

        AlertDialog(

            onDismissRequest = onClose,

            title = {
                Text(
                    if (oldNote == null)
                        "New Note"
                    else
                        "Edit Note"
                )
            },

            text = {

                Column {

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = {
                            Text("Title")
                        }
                    )

                    Spacer(
                        Modifier.height(10.dp)
                    )

                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        label = {
                            Text("Note")
                        },
                        minLines = 5
                    )
                }
            },

            confirmButton = {

                Button(
                    onClick = {
                        if (title.isNotBlank() ||
                            text.isNotBlank()
                        ) {
                            onSave(title, text)
                        }
                    }
                ) {
                    Text("Save")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = onClose
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

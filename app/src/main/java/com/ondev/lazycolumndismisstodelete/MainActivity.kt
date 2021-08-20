package com.ondev.lazycolumndismisstodelete

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ondev.lazycolumndismisstodelete.ui.theme.LazyColumnDismissToDeleteTheme

class MainActivity : ComponentActivity() {

    val listItems =
        mutableStateListOf<String>("Kotlin",
            "Type Script",
            "Lisp",
            "Fortran",
            "Pascal",
            "Cobol",
            "Java Script",
            "C++",
            "C#",
            "Dart",
            "Java",
            "Others")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnDismissToDeleteTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumnDismissToDeleteTheme {
                        Scaffold(topBar = { AppBar() }) {
                            App(listItems)
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App(listItems: SnapshotStateList<String>) {


    LazyColumn {
        itemsIndexed(
            items = listItems,
            key = { index, item ->
                item.hashCode()
            }
        ) { index, item ->
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        listItems.remove(item)
                    }
                    true
                }
            )

            SwipeToDismiss(
                state = state,
                background = {
                    val color = when (state.dismissDirection) {
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Red
                        null -> Color.Transparent
                    }

                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = androidx.compose.ui.Modifier.align(Alignment.CenterEnd)
                        )
                    }

                },
                dismissContent = {
                    MyCustomItem(text = item)
                },
                directions = setOf(DismissDirection.EndToStart)
            )
            Divider()

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MyCustomItem(text: String) {
    ListItem(
        text = { Text(text = text) },
        overlineText = { Text(text = "Language name") },
        icon = { Icon(imageVector = Icons.Outlined.ThumbUp, contentDescription = null) },
        trailing = {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            )
        },
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    )

}


@Composable
private fun AppBar() {
    TopAppBar(title = { Text(text = "Swipe To Delete") })
}
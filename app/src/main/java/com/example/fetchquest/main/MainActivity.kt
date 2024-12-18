package com.example.fetchquest.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchquest.model.ListItem
import com.example.fetchquest.ui.theme.FetchQuestTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchQuestTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IndeterminateCircularIndicator()
                }
            }
        }
        init()
    }

    private fun init() {
        val listJob = Job()
        val exception = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }
        val coroutineScope = CoroutineScope(listJob + Dispatchers.Main)
        coroutineScope.launch(exception) {
            val itemMap = model.getListItems()
            setContent {
                FetchQuestTheme {
                    Surface(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        ItemListView(itemMap)
                    }
                }
            }
        }
    }

}

@Composable
fun ItemListView(map: HashMap<Int, MutableList<ListItem>>) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        map.keys.forEach { key ->
            Column(
                modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                    Text(
                        "List ID: $key", modifier = Modifier
                            .padding(start = 2.dp)
                            .background(Color.LightGray)
                    )
                LazyColumn {
                    items(map[key]?.toList() ?: listOf()) { listItem ->
                        ItemView(listItem, map[key]?.indexOf(listItem) ?: 0)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemView(item: ListItem, position: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (position % 2 == 0) Color.Cyan else Color.White)
            .padding(all = 8.dp)
    ) {
        Column {
            Text("ID: ${item.id}")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Name: ${item.name}")
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FetchQuestTheme {
        ItemView(ListItem(684, 1, "Item 684"), 0)
    }
}

@Composable
fun IndeterminateCircularIndicator() {

    CircularProgressIndicator(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min),
        color = Color.Cyan,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,

    )
}
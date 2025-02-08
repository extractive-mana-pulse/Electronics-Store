package com.example.e_shop.core.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.e_shop.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var searchHistory = remember { mutableStateListOf<String>() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (active) 0.dp else 16.dp), // Remove padding when active
            query = query,
            onQueryChange = { query = it },
            onSearch = { newQuery ->
                searchHistory.add(newQuery)
                active = false
                query = ""
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                Row {
                    IconButton(onClick = { TODO("Add voice search") }) {
                        Icon(painter = painterResource(R.drawable.mic), contentDescription = "Microphone icon")
                    }
                    if (active) {
                        IconButton(
                            onClick = { if (query.isNotEmpty()) query = "" else active = false }
                        ) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                        }
                    }
                }
            }
        ) {
            if (searchHistory.isEmpty()) {
                Text(
                    text = "No search history",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                searchHistory.take(10).forEach { item ->
                    ListItem(
                        modifier = Modifier.clickable { query = item },
                        headlineContent = { Text(text = item) },
                        leadingContent = {
                            Icon(
                                painter = painterResource(R.drawable.history),
                                contentDescription = "History icon"
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    )
                }
            }
        }
    }
}
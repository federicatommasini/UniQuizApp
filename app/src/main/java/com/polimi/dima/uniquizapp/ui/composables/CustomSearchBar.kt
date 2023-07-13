package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import java.util.ArrayList
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(items : List<Subject>, navController: NavController){
    var text by remember { mutableStateOf("")}
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = { text = it} ,
        onSearch = {active = false},
        active = active,
        onActiveChange = {active = it},
        placeholder = {
            Text(text = "Search a subject")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon", modifier = Modifier.fillMaxWidth(0.1f))
        },
        trailingIcon = {
            if(active){
                Icon(
                    modifier = Modifier.clickable{
                        if(text.isNotEmpty())
                            text = ""
                        else active = false
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "close icon"
                )
            }
        },
        colors  = SearchBarDefaults.colors( customLightGray, Color.Transparent,inputFieldColors())
    ){
        ItemList(text, items, navController)
    }
}
@Composable
fun ItemList(text : String, items : List<Subject>, navController: NavController) {
    var filteredItems: List<Subject>
    LazyColumn(modifier = Modifier.fillMaxWidth(0.9f)) {
        val searchedText = text
        filteredItems = if (searchedText.isEmpty()) {
            items
        } else {
            val resultList = ArrayList<Subject>()
            for (item in items) {
                if (item.name.lowercase(Locale.getDefault()).startsWith(searchedText.lowercase(Locale.getDefault())) //.contains(searchedText.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(item)
                }
            }
            resultList
        }
        items(filteredItems) { filteredItem ->
            ItemListItem(
                ItemText = filteredItem.name,
                onItemClick = {navController.navigate(route = "subject_screen/" + filteredItem.id)                }
            )
        }

    }
}
@Composable
fun ItemListItem(ItemText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(ItemText) })
            .background(customLightGray)
            .height(57.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.fillMaxWidth(0.1f))
        Text(text = ItemText,
            fontSize = 18.sp,
            color = customizedBlue ,
            modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.CenterVertically))
    }
}

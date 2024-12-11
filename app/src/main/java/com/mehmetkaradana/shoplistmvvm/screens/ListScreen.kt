package com.mehmetkaradana.shoplistmvvm.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetkaradana.shoplistmvvm.model.Item


@ExperimentalMaterial3Api
@Composable
fun ItemList(itemList: List<Item>,navController: NavController){

   // ToppBar(title = "Alışveriş Listesi 1")
    Scaffold( /*modifier = Modifier.systemBarsPadding(0.dp),*/topBar = {
        ToppBar("Alışveriş Listesi")
                       }, floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FAB {
               navController.navigate("add_item_screen")
            }
        }, content = {
            LazyColumn (contentPadding = it, modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)){
                items(itemList){
                    ItemRow(item = it,navController)
                }
            }
        }
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToppBar(title :String) {
    TopAppBar(modifier = Modifier
        .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        title = { Text(title)}
    )
}


@Composable
fun ItemRow(item: Item,navController: NavController){
    Column(modifier = Modifier
        .fillMaxWidth()
        .border(2.dp, Color.LightGray)
        .background(color = MaterialTheme.colorScheme.primaryContainer)
        .clickable {
            navController.navigate("detail_screen/${item.id}")
        }) {
       Text(text = item.itemName,
           style = MaterialTheme.typography.displaySmall,
           modifier = Modifier.padding(5.dp),
           fontWeight = FontWeight.Bold
           )
    }
}

@Composable
fun FAB(onClick : () -> Unit ){
    FloatingActionButton(onClick = onClick, containerColor = MaterialTheme.colorScheme.onTertiary) {
        Icon(Icons.Filled.Add, contentDescription = "Floatting Button" )
    }
}


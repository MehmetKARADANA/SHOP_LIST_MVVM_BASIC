package com.mehmetkaradana.shoplistmvvm

import android.os.Bundle
import android.provider.Settings.NameValueTable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mehmetkaradana.shoplistmvvm.screens.AddItemScreen
import com.mehmetkaradana.shoplistmvvm.screens.DetailScreen
import com.mehmetkaradana.shoplistmvvm.screens.ItemList
import com.mehmetkaradana.shoplistmvvm.ui.theme.ShopListMVVMTheme
import com.mehmetkaradana.shoplistmvvm.viewmodel.ItemViewModel

class MainActivity : ComponentActivity() {
                                             //lazy yapar ve android yaşam döngüsüne göre yapılandırır
    private val viewModel  :ItemViewModel by viewModels<ItemViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()

        window.statusBarColor = resources.getColor(android.R.color.black)
        setContent {
            val navController = rememberNavController()
            ShopListMVVMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.padding(it)){
                        NavHost(navController = navController, startDestination = "list_screen") {
                            composable("list_screen") {
                                //işler viewmodelda yapılıyor ben burada gözlemliyorum
                                viewModel.getItemList()
                                val itemList by remember {
                                    viewModel.itemList
                                }
                                ItemList(itemList =itemList ,navController)
                            }

                            composable("add_item_screen") {

                                AddItemScreen { item ->
                                   viewModel.saveItem(item)
                                    navController.navigate("list_screen")
                                }
                            }

                            composable("detail_screen/{itemId}",
                                arguments = listOf(
                                    navArgument("itemId"){
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                val itemIdString = remember {
                                    it.arguments?.getString("itemId")
                                }

                               viewModel.getItem(itemIdString?.toIntOrNull() ?: 1)

                                val selectedItem by remember {
                                    viewModel.selectedItem
                                }
                                DetailScreen(item =selectedItem ) {
                                   viewModel.deleteItem(selectedItem)
                                    navController.navigate("list_screen")
                                }
                            }
                        }
                    }
                    
                }
            }

        }
    }
}



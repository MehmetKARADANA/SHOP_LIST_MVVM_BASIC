package com.mehmetkaradana.shoplistmvvm.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat.Style
import com.mehmetkaradana.shoplistmvvm.R
import com.mehmetkaradana.shoplistmvvm.model.Item

@Composable
fun DetailScreen(item : Item?,deleteFunction: () -> Unit){
   Scaffold(topBar = {ToppBar(title = "Ürün Detay")},modifier = Modifier.fillMaxSize()) {
       Box(modifier =
       Modifier
           .fillMaxSize()
           .padding(it)
           .background(color = MaterialTheme.colorScheme.primaryContainer)
           ,contentAlignment = Alignment.Center){
           Column(horizontalAlignment = Alignment.CenterHorizontally,) {
               Text(text = item?.itemName ?: "", style = MaterialTheme.typography.displayLarge,
                   modifier = Modifier.padding(5.dp),
                   fontWeight = FontWeight.Bold,
                   color = Color.Black,
                   textAlign = TextAlign.Center
               )
               println("[DETAIL_SCREEN] itemImage :"+item?.image.toString())
               val imageBitmap = item?.image?.let {byteArray ->
                   BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)?.asImageBitmap()
               }
               println("[DETAIL_SCREEN] ImageBitmap :"+imageBitmap.toString())
               Image(bitmap =imageBitmap ?: ImageBitmap.imageResource(id = R.drawable.selectimage) ,
                   contentDescription =item?.itemName ?: "", modifier = Modifier
                       .size(300.dp, 200.dp)
                       .padding(20.dp))

               Text(text = item?.storeName ?: "-", style = MaterialTheme.typography.displaySmall,
                   modifier = Modifier.padding(5.dp),
                   fontWeight = FontWeight.Bold,
                   color = Color.Black,
                   textAlign = TextAlign.Center
               )

               Text(text = item?.price.toString() ?: "-", style = MaterialTheme.typography.displaySmall,
                   modifier = Modifier.padding(5.dp),
                   fontWeight = FontWeight.Bold,
                   color = Color.Black,
                   textAlign = TextAlign.Center
               )

               Button(onClick = {
                   deleteFunction()
               }) {
                   Text(text = "Delete")
               }
           }
       }

   }

}





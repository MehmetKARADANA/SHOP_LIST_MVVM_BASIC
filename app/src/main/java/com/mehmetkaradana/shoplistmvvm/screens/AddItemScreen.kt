package com.mehmetkaradana.shoplistmvvm.screens

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.text.style.LineHeightSpan.WithDensity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter

import com.mehmetkaradana.shoplistmvvm.R
import com.mehmetkaradana.shoplistmvvm.model.Item
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.io.ByteArrayOutputStream

@Composable
fun AddItemScreen(saveFunction : (item : Item) -> Unit){
    
    val itemName = remember {
        mutableStateOf("")
    }

    val itemStore = remember {
        mutableStateOf("")
    }

    val itemPrice = remember {
        mutableStateOf("")
    }
//var olmadığı için sorun yaşadım
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current
    
   Scaffold(topBar = { ToppBar(title = "Ürün Ekle")},modifier = Modifier.fillMaxSize()) {
       Box(modifier = Modifier
           .fillMaxSize()
           .padding(it)
           .background(color = MaterialTheme.colorScheme.primaryContainer),
           contentAlignment = Alignment.Center){
           Column (horizontalAlignment = Alignment.CenterHorizontally){
               ImagePicker { uri ->                 //lambda fonksiyonu ile imagePickerda seçilen imagei dscreene aktardım
                   selectedImageUri = uri
               }
               TextField(value = itemName.value, onValueChange ={
                   itemName.value=it
               }, placeholder = {
                   Text(text = "Enter Item Name")
               }, modifier = Modifier.padding(5.dp) )

               TextField(itemStore.value, placeholder = {
                   Text(text = "Enter Item Store")
               } ,onValueChange ={
                   itemStore.value=it
               }, modifier = Modifier.padding(5.dp) )

               //pricei remmeberde int olarak verebilirim dursun şimdilik
               //keyboardtype sorun çıkabarabilir !!!
               TextField(keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),value = itemPrice.value, onValueChange ={
                   itemPrice.value=it
               },
                   placeholder = {
                       Text(text = "Enter Item Price")
                   }, modifier = Modifier.padding(5.dp))

               Button(modifier = Modifier.padding(5.dp),onClick = {
                   val imageByteArray =selectedImageUri?.let {
                       resizeImage(context =context,it, 300,200)
                   } ?: ByteArray(0)

                   val itemToInsert=Item(
                       itemName.value,
                       itemStore.value
                       ,itemPrice.value.toIntOrNull() ?: 0
                       ,imageByteArray
                   )
                   /* println("[ADD_ITEM_SCREEN] imageByteArray $imageByteArray")//[B@44a7e18
                    println("[ADD_ITEM_SCREEN] imageByteArraySize " +imageByteArray.size.toString())//0
                    println("[ADD_ITEM_SCREEN] selectedImageUri : "+selectedImageUri.toString())//null
                    println("[ADD_ITEM_SCREEN] imagebitmap : "+itemToInsert.image.toString())//[B@44a7e18*/
                   saveFunction(itemToInsert)
               }) {
                   Text(text = " Save ")
               }
           }
   }
    }
}

@Composable
fun ImagePicker(onImageSelected :(Uri?) ->Unit){
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    //mainActivityde mainActivitycontext gelir
    val context = LocalContext.current

    val permission = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
          android.Manifest.permission.READ_MEDIA_IMAGES
    }else{
          android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri->
        selectedImageUri=uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {granted ->
        if (granted) {
        galleryLauncher.launch("image/*")
        }else{
            Toast.makeText(context,"Permission Denied!",Toast.LENGTH_LONG).show()
        }
    }
    
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        selectedImageUri?.let {
            Image(painter = rememberAsyncImagePainter(model = it), contentDescription ="selected Image",
                modifier = Modifier
                .size(300.dp, 200.dp))
            onImageSelected(it)
        } ?: Image(painter = painterResource(id = R.drawable.selectimage), contentDescription ="Select Image" ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .size(300.dp, 200.dp)
                .clickable {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        galleryLauncher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permission)
                    }
                })
    }
}

fun resizeImage(context: Context,uri : Uri,maxWitdh : Int,maxHeight :Int) : ByteArray? {
return try {
    val inputStream=context.contentResolver.openInputStream(uri)
    val originalBitmap = BitmapFactory.decodeStream(inputStream)
    
    //scale
    val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()

    var witdh = maxWitdh
    var height = (witdh /ratio).toInt()

    if (height > maxHeight){
        height = maxHeight
        witdh =  (height*ratio).toInt()
    }
    
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap,witdh,height,false)

    val byteArrayOutputStream=ByteArrayOutputStream()

    resizedBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)

    byteArrayOutputStream.toByteArray()
}catch (e : Exception){
    e.printStackTrace()
    null
}
}
/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   AddItemScreen({})

}*/
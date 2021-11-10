package com.example.myapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.myapp.ui.theme.MyAppTheme
import java.io.File


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                PhotoHolder()
            }
        }
    }
}

@Composable
fun PhotoHolder(
    viewModel : ExampleViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val imageUri by viewModel.imageUri.collectAsState()
    val tmpFile = File(context.cacheDir, "profile_temp_file.png")

    val takeImageResult = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
                viewModel.updateUri(Uri.fromFile(tmpFile))
            }
        }

    fun takeImage(){
            takeImageResult.launch(FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", tmpFile))
    }


    Image(modifier = Modifier
        .size(150.dp)
        .clickable(onClick = { takeImage() }),
        painter = rememberImagePainter(
            data = imageUri?: R.drawable.ic_baseline_person_24,
            builder = {
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.ENABLED)
                diskCachePolicy(CachePolicy.READ_ONLY)
            }
        ),
        contentDescription = null,
    )

}


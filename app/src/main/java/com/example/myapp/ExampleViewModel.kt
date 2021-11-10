package com.example.myapp

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor (val app: Application) : AndroidViewModel(app){

    val imageUri = MutableStateFlow<Uri?>(null)

    fun updateUri(input: Uri){
        imageUri.tryEmit(input)
    }
}
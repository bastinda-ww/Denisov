package com.example.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class FilmApiStatus { LOADING, ERROR, DONE}

class DataModel: ViewModel() {

    private val status = MutableLiveData<FilmApiStatus>()
//    private val
}
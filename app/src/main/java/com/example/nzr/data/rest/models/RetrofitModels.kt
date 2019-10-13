package com.example.nzr.data.rest.models

import com.google.gson.annotations.SerializedName


data class board(
     var id :String,
     var name :String,
     var descData :String,
     var closed :Boolean
)
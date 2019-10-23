package com.example.nzr.data.rest.models

import com.google.gson.annotations.SerializedName


data class board(
     var id :String,
     var name :String,
     var desc :String,
     var descData :String,
     var closed :Boolean,
     var idOrganization: String,
     var pinned : Boolean,
     var url : Boolean,
     var shortUrl  :  Boolean
)

data class cardShort(
    var id:String,
    var name:String,
    var vendor:Boolean = true
)


data class cardDetail(
    var id:String,
    var name:String,
    var address:String,
    var pos:Float,
    var url:String,
    var subscribed:Boolean,
    var desc: String

)

data class listsCards(
    var id : String,
    var name:String,
    var cards : MutableList<cardShort>
)

data class yandexBoard(
    var self : String,
    var id : String,
    var version : String,
    var name : String,
    var columns : List<yandexColumn>,
    //filter
    var useRanking : Boolean
)
data class yandexColumn(
    var self : String,
    var id : String,
    var display : String
)

data class yandexQueue(
    var self : String,
    var id : String,
    var key : String,
    var display : String
)

data class genericBoardShort(
    var trelloId : String?,
    var yandexId : String?,
    var name : String
)

data class yandexCard(
    var self : String,
    var id : String,
    var key : String,
    var version : Int,
    var lastCommentUpdatedAt : String,
    var summary : String,
    var queue: yandexQueue,
    var status :status

)
data class status(
    var self:String,
    var id:String,
    var key:String,
    var display: String
)

data class queueShort(
    var id: String,
    var name:String
)
data class requestCreateCardYandexBody(
    var queue: queueShort,
    var summary: String
)

fun yandexToGeneric(board:yandexBoard) : genericBoardShort{
    return genericBoardShort(null,board.id , board.name)
}

fun yandexQueueToGeneric(queue:queueShort):genericBoardShort{
    return genericBoardShort(null,queue.id,queue.name)
}

fun trelloToGeneric(board:board) : genericBoardShort{
    return genericBoardShort(board.id ,null, board.name)
}



package com.example.nzr.data.rest.models


data class Board(
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

data class CardShort(
    var id:String,
    var name:String,
    var vendor:Boolean = true
)


data class CardDetail(
    var id:String,
    var name:String,
    var address:String,
    var pos:Float,
    var url:String,
    var subscribed:Boolean,
    var desc: String

)

data class ListsCards(
    var id : String,
    var name:String,
    var cards : MutableList<CardShort>
)

data class YandexBoard(
    var self : String,
    var id : String,
    var version : String,
    var name : String,
    var columns : List<YandexColumn>,
    //filter
    var useRanking : Boolean
)
data class YandexColumn(
    var self : String,
    var id : String,
    var display : String
)

data class YandexQueue(
    var self : String,
    var id : String,
    var key : String,
    var display : String
)


data class YandexCard(
    var self : String,
    var id : String,
    var key : String,
    var version : Int,
    var lastCommentUpdatedAt : String,
    var summary : String,
    var queue: YandexQueue,
    var Status :Status

)
data class Status(
    var self:String,
    var id:String,
    var key:String,
    var display: String
)

data class QueueCreate(
    var id: String
)

data class QueueShort(
    var id: String,
    var name:String
)
data class RequestCreateCardYandexBody(
    var queue: QueueCreate,
    var summary: String
)

data class TransitionScreen(
    var self:String,
    var id:String
)

data class Transition(
    var self:String,
    var id:String,
    var to:Status,
    var screen:TransitionScreen
)

data class GenericBoardShort(
    var trelloId : String?,
    var yandexId : String?,
    var name : String
)


fun yandexToGeneric(board:YandexBoard) : GenericBoardShort{
    return GenericBoardShort(null,board.id , board.name)
}

fun yandexQueueToGeneric(queue:QueueShort):GenericBoardShort{
    return GenericBoardShort(null,queue.id,queue.name)
}

fun trelloToGeneric(board:Board) : GenericBoardShort{
    return GenericBoardShort(board.id ,null, board.name)
}



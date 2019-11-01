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
    var status :Status

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
    var ids : MutableMap<String,String>,
    var name : String
)
data class GenericCardDetail(
    var id : Pair<String,String>,
    var name : String
)
data class GenericCardShort(
    var id : Pair<String,String>,
    var name : String
)

fun cardToGenericShort(card:CardShort,vendor:String):GenericCardShort{
    return GenericCardShort(Pair(vendor,card.id),card.name)
}
fun cardToGenericDetail(card:CardDetail,vendor:String) : GenericCardDetail{
    return GenericCardDetail(Pair(vendor,card.id),card.name)
}
fun cardToGenericDetail(card:YandexCard,vendor:String) : GenericCardDetail{
    return GenericCardDetail(Pair(vendor,card.id),card.summary)
}

fun yandexToGeneric(board:YandexBoard) : GenericBoardShort{
    var ids = mutableMapOf("yandex" to board.id)
    return GenericBoardShort(ids, board.name)
}

fun yandexQueueToGeneric(queue:QueueShort):GenericBoardShort{
    var ids = mutableMapOf("yandex" to queue.id)
    return GenericBoardShort(ids,queue.name)
}

fun trelloToGeneric(board:Board) : GenericBoardShort{
    var ids = mutableMapOf("trello" to board.id)
    return GenericBoardShort(ids, board.name)
}



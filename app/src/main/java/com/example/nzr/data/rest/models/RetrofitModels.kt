package com.example.nzr.data.rest.models


data class BoardTrello(
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

data class CardShortTrello(
    var id:String,
    var name:String,
    var vendor:Boolean = true
)


data class CardDetailTrello(
    var id:String,
    var name:String,
    var address:String,
    var pos:Float,
    var url:String,
    var subscribed:Boolean,
    var desc: String

)

data class ListsCardsTrello(
    var id : String,
    var name:String,
    var cards : MutableList<CardShortTrello>
)

data class BoardDetailYandex(
    var self : String,
    var id : String,
    var version : String,
    var name : String,
    var columns : List<ColumnYandex>,
    //filter
    var useRanking : Boolean
)

data class ColumnYandex(
    var self : String,
    var id : String,
    var display : String
)

data class QueueYandex(
    var self : String,
    var id : String,
    var key : String,
    var display : String
)


data class CardDetailYandex(
    var self : String,
    var id : String,
    var key : String,
    var version : Int,
    var lastCommentUpdatedAt : String,
    var summary : String,
    var queue: QueueYandex,
    var status :StatusYandex

)
data class StatusYandex(
    var self:String,
    var id:String,
    var key:String,
    var display: String
)

data class QueueCreatitionYandex(
    var id: String
)

data class QueueShortYandex(
    var id: String,
    var name:String
)
data class RequestCreateCardYandexBody(
    var queue: QueueCreatitionYandex,
    var summary: String
)

data class TransitionScreenYandex(
    var self:String,
    var id:String
)

data class TransitionYandex(
    var self:String,
    var id:String,
    var to:StatusYandex,
    var screen:TransitionScreenYandex
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

data class GenericTransition(
    var id:String?,
    var text:String?
)

fun cardToGenericShort(card:CardShortTrello, vendor:String):GenericCardShort{
    return GenericCardShort(Pair(vendor,card.id),card.name)
}
fun cardToGenericDetail(card:CardDetailTrello, vendor:String) : GenericCardDetail{
    return GenericCardDetail(Pair(vendor,card.id),card.name)
}
fun cardToGenericDetail(card:CardDetailYandex, vendor:String) : GenericCardDetail{
    return GenericCardDetail(Pair(vendor,card.id),card.summary)
}

fun yandexToGeneric(board:BoardDetailYandex) : GenericBoardShort{
    var ids = mutableMapOf("yandex" to board.id)
    return GenericBoardShort(ids, board.name)
}

fun yandexQueueToGeneric(queue:QueueShortYandex):GenericBoardShort{
    var ids = mutableMapOf("yandex" to queue.id)
    return GenericBoardShort(ids,queue.name)
}

fun trelloToGeneric(boardTrello:BoardTrello) : GenericBoardShort{
    var ids = mutableMapOf("trello" to boardTrello.id)
    return GenericBoardShort(ids, boardTrello.name)
}



package models

data class ResponseModel (
    var data: List<Data>
)

data class Data(
    var wins : String,
    var cards: List<String>,
    var _links: Links
)

data class Links(
    var houses: List<String>
)
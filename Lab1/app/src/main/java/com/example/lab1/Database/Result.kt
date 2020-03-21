package com.example.lab1.Database

class Result {
    var id : Int = 0
    var name : String = ""
    var question : String = ""
    var answer : String = ""

    constructor(n:String, q: String, an: String) {
        name = n
        question = q
        answer = an
    }

    constructor(_id: Int, n:String, q: String, an: String) {
        id = _id
        name = n
        question = q
        answer = an
    }
}
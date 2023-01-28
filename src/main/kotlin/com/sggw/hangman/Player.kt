package com.sggw.hangman

data class Player(
    val name: String,
    var score: Long = 0,
    var mistakes: Long = 0
)
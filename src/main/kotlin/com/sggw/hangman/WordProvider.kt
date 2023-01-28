package com.sggw.hangman

import kotlin.random.Random


class WordProvider(var currentWord: String,
                   private val words: List<String>) {


    fun chooseNewWord(){
        val randomIndex = Random.nextInt(words.size)
        currentWord = words[randomIndex]
    }





}

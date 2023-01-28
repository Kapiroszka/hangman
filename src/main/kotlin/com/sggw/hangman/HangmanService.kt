package com.sggw.hangman

class HangmanService(
    private val players: MutableMap<String, Player>,
    private var wordProvider: WordProvider,
    private var checkedLetters: MutableList<Char>,
    private var correctLetters: MutableList<Char>
) {

    fun addUserIfNotPresent(playerName: String): Boolean {
        if (!players.containsKey(playerName)) {
            players[playerName] = Player(name = playerName)
            return true
        }
        return false
    }

    fun startNewGame() {
        wordProvider.chooseNewWord()
        checkedLetters.clear()
        correctLetters.clear()
        players.forEach {
            run {
                it.value.mistakes = 0
                it.value.score = 0
            }
        }
    }

    fun removePlayer(playerName: String) {
        players.remove(playerName)
    }

    fun guessLetter(playerName: String, letter: Char): Boolean {
        addUserIfNotPresent(playerName)
        if (checkedLetters.contains(letter)) {
            return true
        }
        checkedLetters.add(letter)
        val occurrencesOfLetterInWord = wordProvider.currentWord.chars().filter { it.toChar() == letter }.count()
        if (occurrencesOfLetterInWord > 0) {
            correctLetters.add(letter)
            players[playerName]!!.score += occurrencesOfLetterInWord
            return true
        } else {
            players[playerName]!!.mistakes += 1
            return false
        }
    }

    fun getPlayerStatistics(playerName: String): Player? {
        return players[playerName]
    }

    fun getCurrentWord(): String {
        return wordProvider.currentWord
    }
}
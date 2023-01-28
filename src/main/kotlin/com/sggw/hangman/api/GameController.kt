package com.sggw.hangman.api

import com.sggw.hangman.HangmanService
import com.sggw.hangman.Player
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event
import java.util.function.BiConsumer


@Api(value = "game", description = "Rest api for conducting game of hangman")
@RequestMapping("/api/hangman")
@RestController
class GameController(private val hangmanService: HangmanService) {


    @PostMapping("/player")
    @ApiOperation("Add new player")
    fun addPlayer(@RequestBody playerName: String): ResponseEntity<Void> {
        return if (hangmanService.addUserIfNotPresent(playerName)) {
            ResponseEntity.unprocessableEntity().build()
        } else {
            ResponseEntity.ok().build()
        }
    }

    @DeleteMapping("/player")
    @ApiOperation("Remove player")
    fun removePlayer(@RequestBody playerName: String) {
        hangmanService.removePlayer(playerName)
    }


    @PostMapping("/word/guess")
    @ApiOperation("Guess letter")
    fun guessLetter(@RequestBody guessRequest: GuessRequest): ResponseEntity<Player> {
        return if (hangmanService.guessLetter(guessRequest.playerName, guessRequest.letter)) {
            ResponseEntity.ok().body(hangmanService.getPlayerStatistics(guessRequest.playerName))
        } else {
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(hangmanService.getPlayerStatistics(guessRequest.playerName))
        }
    }

    @PostMapping("/newGame")
    fun startNewGame() {
        hangmanService.startNewGame()
    }

    @GetMapping("/word")
    @ApiOperation("get current word")
    fun getCurrentWord(): String {
        return hangmanService.getCurrentWord()
    }
}

data class GuessRequest(
    val letter: Char,
    val playerName: String
)

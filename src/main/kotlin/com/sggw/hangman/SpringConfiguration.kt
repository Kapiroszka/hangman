package com.sggw.hangman

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class SpringConfiguration {


    @Bean
    fun hangmanService(wordProvider: WordProvider): HangmanService {
        return HangmanService(
            mutableMapOf(),
            wordProvider,
            mutableListOf(),
            mutableListOf()
        )
    }

    @Bean
    fun wordProvider(): WordProvider {
        return WordProvider(
            "",
            File("src\\main\\resources\\words.txt")
                .useLines  {
                    it.toList()
                }
        )
    }

}
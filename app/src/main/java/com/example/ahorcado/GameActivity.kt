package com.example.ahorcado

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {

    private lateinit var editTextLetter: EditText
    private lateinit var checkButton: Button
    private lateinit var restartButton: Button
    private lateinit var lettersGuessedTextView: TextView

    private val lettersGuessed = mutableListOf<String>()
    private var targetWord: String = "ejemplo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameactivity)
        editTextLetter = findViewById(R.id.editTextLetter)
        checkButton = findViewById(R.id.checkButton)
        restartButton = findViewById(R.id.restartButton)
        lettersGuessedTextView = findViewById(R.id.lettersGuessedTextView)

        checkButton.setOnClickListener {
            val letter = editTextLetter.text.toString()
            if (letter.isNotEmpty()) {
                lettersGuessed.add(letter)
                updateGuessedLetters()
                checkLetter(letter)
            }
        }

        restartButton.setOnClickListener {
            restartGame()
        }
    }

    private fun checkLetter(letter: String) {
        if (targetWord.contains(letter)) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrecto, intenta de nuevo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateGuessedLetters() {
        lettersGuessedTextView.text = "Letras Introducidas: ${lettersGuessed.joinToString(", ")}"
    }

    private fun restartGame() {
        // Reinicia la partida
        lettersGuessed.clear()
        editTextLetter.text.clear()
        updateGuessedLetters()
        Toast.makeText(this, "Juego reiniciado", Toast.LENGTH_SHORT).show()
    }
}

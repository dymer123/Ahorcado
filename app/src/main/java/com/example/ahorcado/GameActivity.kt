package com.example.ahorcado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var editTextLetter: EditText
    private lateinit var checkButton: Button
    private lateinit var restartButton: Button
    private lateinit var lettersGuessedTextView: TextView
    private lateinit var wordProgressTextView: TextView
    private lateinit var imagenAhorcado: ImageView

    private var intentosFallidos = 0
    private lateinit var partida: Partida
    private val letrasIncorrectas: MutableList<Char> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameactivity)

        editTextLetter = findViewById(R.id.editTextLetter)
        checkButton = findViewById(R.id.checkButton)
        restartButton = findViewById(R.id.restartButton)
        lettersGuessedTextView = findViewById(R.id.lettersGuessedTextView)
        wordProgressTextView = findViewById(R.id.wordProgressTextView)
        imagenAhorcado = findViewById(R.id.imagenAhorcado)

        // Obtener la partida actual desde el Singleton
        partida = SingletonPartida.obtenerPartida()

        // Mostrar la palabra inicial en formato de guiones
        actualizarPalabraProgreso()

        // Listener para el botón de comprobar letra
        checkButton.setOnClickListener {
            val letra = editTextLetter.text.toString().singleOrNull()

            if (letra != null) {
                val letraLowerCase = letra.lowercaseChar()

                // Verificar si la letra ya fue intentada (correcta o incorrecta)
                if (letraLowerCase in partida.letrasDescubiertas || letraLowerCase in letrasIncorrectas) {
                    Toast.makeText(this, "Ya intentaste esta letra", Toast.LENGTH_SHORT).show()
                } else {
                    if (partida.verificarLetra(letraLowerCase)) {
                        // Letra correcta
                        Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
                        partida.letrasDescubiertas.add(letraLowerCase)  // Añadir letra correcta a descubiertas
                        actualizarPalabraProgreso() // Actualizar la palabra en pantalla
                    } else {
                        // Letra incorrecta
                        intentosFallidos++
                        letrasIncorrectas.add(letraLowerCase)  // Añadir letra incorrecta
                        actualizarImagenAhorcado(intentosFallidos)
                        actualizarLetrasIncorrectas() // Actualizar la lista de letras incorrectas
                        Toast.makeText(this, "Letra incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Introduce una letra válida", Toast.LENGTH_SHORT).show()
            }

            editTextLetter.text.clear()  // Limpiar el campo después de verificar
        }

        // Listener para el botón de reiniciar partida
        restartButton.setOnClickListener {
            reiniciarPartida()
        }
    }

    // Actualizar la lista de letras incorrectas en pantalla
    private fun actualizarLetrasIncorrectas() {
        lettersGuessedTextView.text = "Letras Incorrectas: " + letrasIncorrectas.joinToString(", ")
    }

    // Cambiar la imagen del ahorcado según los fallos
    private fun actualizarImagenAhorcado(fallos: Int) {
        val resId = when (fallos) {
            1 -> R.drawable.ahorcado1
            2 -> R.drawable.ahorcado2
            3 -> R.drawable.ahorcado3
            4 -> R.drawable.ahorcado4
            5 -> R.drawable.ahorcado5
            else -> R.drawable.ahorcado6
        }
        imagenAhorcado.setImageResource(resId)

        if (fallos >= 6) {
            SingletonPartida.setGano(false) // Guardar estado de pérdida
            mostrarResultado(false) // Mostrar pantalla de derrota
        }
    }

    // Actualizar el progreso de la palabra con las letras adivinadas
    private fun actualizarPalabraProgreso() {
        val progreso = partida.palabra.map { letra ->
            if (letra in partida.letrasDescubiertas) {
                letra  // Si la letra ha sido descubierta, se muestra
            } else {
                '_'  // Si no, se muestra como un guion bajo
            }
        }.joinToString(" ")

        wordProgressTextView.text = "Palabra: $progreso"

        // Verificar si ha ganado
        if (!progreso.contains('_')) {
            SingletonPartida.setGano(true) // Guardar estado de victoria
            mostrarResultado(true) // Mostrar pantalla de victoria
        }
    }

    // Mostrar el resultado del juego (ganado o perdido)
    private fun mostrarResultado(gano: Boolean) {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
        finish() // Cerrar la GameActivity actual
    }

    // Reiniciar partida (volver a la MainActivity)
    private fun reiniciarPartida() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Cerrar la GameActivity actual
    }
}

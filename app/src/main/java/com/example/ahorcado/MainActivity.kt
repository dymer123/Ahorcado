package com.example.ahorcado

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var startGameButton: Button
    private lateinit var editTextPassword: EditText
    private lateinit var editTextText: EditText
    private lateinit var easyButton: Button
    private lateinit var hardButton: Button
    private var dificultadSeleccionada = "Fácil" // Valor por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de vistas
        startGameButton = findViewById(R.id.startGameButton)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextText = findViewById(R.id.editTextText)
        easyButton = findViewById(R.id.easyButton)
        hardButton = findViewById(R.id.hardButton)

        // Listener para seleccionar la dificultad
        easyButton.setOnClickListener {
            dificultadSeleccionada = "Fácil"
            findViewById<TextView>(R.id.textViewPistaLabel).visibility = View.VISIBLE
            findViewById<EditText>(R.id.editTextPista).visibility = View.VISIBLE
            Toast.makeText(this, "Modo Fácil seleccionado", Toast.LENGTH_SHORT).show()
        }

        hardButton.setOnClickListener {
            dificultadSeleccionada = "Difícil"
            findViewById<TextView>(R.id.textViewPistaLabel).visibility = View.GONE
            findViewById<EditText>(R.id.editTextPista).visibility = View.GONE
            Toast.makeText(this, "Modo Difícil seleccionado", Toast.LENGTH_SHORT).show()
        }

        // Listener para el botón "Empezar Partida"
        startGameButton.setOnClickListener {
            val playerName = editTextText.text.toString().trim()
            val word = editTextPassword.text.toString().trim()

            // Comprobar si los campos no están vacíos
            if (playerName.isNotEmpty() && word.isNotEmpty()) {
                // Inicializar una nueva partida en el singleton
                SingletonPartida.iniciarNuevaPartida(playerName, word, dificultadSeleccionada)

                // Cambiar de Activity usando un Intent
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
                finish() // Finaliza la MainActivity
            } else {
                // Mostrar un mensaje de advertencia si los campos están vacíos
                Toast.makeText(this, "Por favor, introduce un nombre y una palabra", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

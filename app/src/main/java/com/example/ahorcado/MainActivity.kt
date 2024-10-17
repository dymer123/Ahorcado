package com.example.ahorcado

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextPalabra: EditText
    private lateinit var editTextPista: EditText // Nuevo campo para pista
    private lateinit var startButton: Button
    private lateinit var easyButton: Button
    private lateinit var hardButton: Button

    // Instancia de la clase Partida
    private lateinit var partida: Partida

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos de la vista
        editTextNombre = findViewById(R.id.editTextText)
        editTextPalabra = findViewById(R.id.editTextPassword)
        editTextPista = findViewById(R.id.editTextPista) // Referencia a la pista
        startButton = findViewById(R.id.startGameButton)
        easyButton = findViewById(R.id.easyButton)
        hardButton = findViewById(R.id.hardButton)

        // Listener para el botón "Fácil"
        easyButton.setOnClickListener {
            editTextPista.visibility = EditText.VISIBLE // Mostrar campo para pista en modo Fácil
            Toast.makeText(this, "Modo Fácil: puedes añadir una pista", Toast.LENGTH_SHORT).show()
        }

        // Listener para el botón "Difícil"
        hardButton.setOnClickListener {
            editTextPista.visibility = EditText.GONE // Ocultar campo de pista en modo Difícil
            Toast.makeText(this, "Modo Difícil seleccionado", Toast.LENGTH_SHORT).show()
        }

        // Listener para el botón "Empezar Juego"
        startButton.setOnClickListener {
            val nombreJugador = editTextNombre.text.toString()
            val palabraSecreta = editTextPalabra.text.toString()
            val pistaLetra = editTextPista.text.toString().firstOrNull()

            if (nombreJugador.isNotEmpty() && palabraSecreta.isNotEmpty()) {
                // Crear una nueva partida con los datos ingresados
                partida = Partida(nombreJugador, palabraSecreta, "Fácil")
                partida.iniciarPartida(nombreJugador, palabraSecreta, "Fácil")

                // Si el modo es fácil y hay una pista válida
                if (pistaLetra != null && pistaLetra in palabraSecreta) {
                    val pistaAgregada = partida.agregarPistaManual(pistaLetra)
                    if (pistaAgregada) {
                        Toast.makeText(this, "Pista agregada: la letra '$pistaLetra'", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "La letra '$pistaLetra' ya está descubierta o no está en la palabra", Toast.LENGTH_SHORT).show()
                    }
                }

                Toast.makeText(this, "Partida iniciada con éxito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, ingrese el nombre y la palabra", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

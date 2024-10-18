package com.example.ahorcado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var restartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultactivity)

        resultTextView = findViewById(R.id.resultTextView)
        restartButton = findViewById(R.id.restartButton)

        // Obtener el estado de ganancia desde el Singleton
        val gano = SingletonPartida.isGano()

        // Mostrar resultado
        resultTextView.text = if (gano) {
            "¡Has ganado!"
        } else {
            "¡Has perdido!"
        }

        // Listener para el botón de reiniciar
        restartButton.setOnClickListener {
            reiniciarPartida()
        }
    }

    // Reiniciar partida (volver a la MainActivity)
    private fun reiniciarPartida() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Cerrar la ResultActivity actual
    }
}

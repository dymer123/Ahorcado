package com.example.ahorcado

class Partida(
    var nombreJugador: String = "",
    var palabra: String = "",
    var dificultad: String = "Fácil"
) {
    var letrasDescubiertas: MutableList<Char> = mutableListOf()
    var pistasRestantes: Int = if (dificultad == "Difícil") 1 else 3

    // Inicializa el juego con los datos del jugador
    fun iniciarPartida(nombre: String, palabraSecreta: String, dificultadSeleccionada: String) {
        nombreJugador = nombre
        palabra = palabraSecreta
        dificultad = dificultadSeleccionada
        pistasRestantes = if (dificultad == "Difícil") 1 else 3
        letrasDescubiertas.clear() // Reiniciar letras descubiertas
    }

    // Dar una pista manualmente: el jugador proporciona una letra como pista
    fun agregarPistaManual(letraPista: Char): Boolean {
        return if (letraPista in palabra && letraPista !in letrasDescubiertas) {
            letrasDescubiertas.add(letraPista)
            true // Pista agregada correctamente
        } else {
            false // Pista inválida (ya descubierta o no está en la palabra)
        }
    }

    // Verificar si una letra está en la palabra
    fun verificarLetra(letra: Char): Boolean {
        return letra in palabra
    }
}

import com.example.ahorcado.Partida


class SingletonPartida private constructor() {
    companion object {
        public lateinit var partida : Partida
        private var gano: Boolean = false // Variable para almacenar si el jugador ha ganado

        @Volatile
        private var instance: SingletonPartida? = null
        fun getInstance(): SingletonPartida {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SingletonPartida()
                    }
                }
            }
            return instance!!
        }
        fun iniciarNuevaPartida(nombreJugador: String, palabra: String, dificultad: String) {
            partida = Partida().apply {
                iniciarPartida(nombreJugador, palabra, dificultad)
            }
            gano = false // Reiniciar el estado de "gano" al comenzar una nueva partida
        }
        fun obtenerPartida(): Partida {
            return partida ?: throw IllegalStateException("La partida no ha sido iniciada")
        }

        fun setGano(gano: Boolean) {
            this.gano = gano
        }

        fun isGano(): Boolean {
            return gano
        }
    }
}




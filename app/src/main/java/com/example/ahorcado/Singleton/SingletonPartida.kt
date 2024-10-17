import com.example.ahorcado.Partida


class SingletonPartida private constructor() {
    companion object {
        public lateinit var partida : Partida

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
    }
}




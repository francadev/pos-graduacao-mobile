package dev.rafaelfranca.marcadortruco

class MarcadorTrucoModel(
    // Entrada
    var nomePlayer1: String = "Jogador 1",
    var nomePlayer2: String = "Jogador 2"
) {
    var pontosPlayer1: Int = 0
        private set
    var pontosPlayer2: Int = 0
        private set
    var vitoriasPlayer1: Int = 0
        private set
    var vitoriasPlayer2: Int = 0
        private set
    var ultimoGanhador: Int = 0
        private set

    val historico = ArrayList<String>()

    fun alterarPontos(player: Int, valor: Int): StatusAlteracaoPontos {
        // Entrada
        val pontosAtuais = if (player == 1) pontosPlayer1 else pontosPlayer2

        // Processamento
        if (valor < 0 && pontosAtuais == 0) {
            // Saída
            return StatusAlteracaoPontos.PONTUACAO_MINIMA_ATINGIDA
        }

        if (player == 1) {
            pontosPlayer1 = (pontosPlayer1 + valor).coerceIn(0, 12)
            if (pontosPlayer1 >= 12) {
                finalizarPartida(1)
                // Saída
                return StatusAlteracaoPontos.PARTIDA_FINALIZADA
            }
        } else {
            pontosPlayer2 = (pontosPlayer2 + valor).coerceIn(0, 12)
            if (pontosPlayer2 >= 12) {
                finalizarPartida(2)
                // Saída
                return StatusAlteracaoPontos.PARTIDA_FINALIZADA
            }
        }
        // Saída
        return StatusAlteracaoPontos.SUCESSO
    }

    private fun finalizarPartida(ganhador: Int) {
        // Processamento
        ultimoGanhador = ganhador
        if (ganhador == 1) {
            vitoriasPlayer1++
        } else {
            vitoriasPlayer2++
        }
        pontosPlayer1 = 0
        pontosPlayer2 = 0
    }

    fun zerarPontos() {
        // Processamento
        pontosPlayer1 = 0
        pontosPlayer2 = 0
        vitoriasPlayer1 = 0
        vitoriasPlayer2 = 0
        ultimoGanhador = 0
    }

    fun zerarHistorico() {
        // Processamento
        historico.clear()
    }

    fun atualizarNomeNoHistorico(nomeAntigo: String, nomeNovo: String) {
        // Processamento
        if (nomeAntigo.isBlank() || nomeAntigo == nomeNovo) return
        for ((i, element) in historico.withIndex()) {
            val item = element
            if (item.contains(nomeAntigo)) {
                historico[i] = item.replace(nomeAntigo, nomeNovo)
            }
        }
    }

    // Saída
    enum class StatusAlteracaoPontos {
        SUCESSO,
        PONTUACAO_MINIMA_ATINGIDA,
        PARTIDA_FINALIZADA
    }
}
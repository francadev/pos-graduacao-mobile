package dev.rafaelfranca.marcadortruco

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MarcadorTrucoTest {

    private lateinit var model: MarcadorTrucoModel

    @Before
    fun setUp() {
        model = MarcadorTrucoModel(nomePlayer1 = "Jogador 1", nomePlayer2 = "Jogador 2")
    }

    @Test
    fun testarEstadoInicialZero() {
        assertEquals(0, model.pontosPlayer1)
        assertEquals(0, model.pontosPlayer2)
        assertEquals(0, model.vitoriasPlayer1)
        assertEquals(0, model.vitoriasPlayer2)
        assertEquals(0, model.ultimoGanhador)
        assertTrue(model.historico.isEmpty())
    }

    @Test
    fun testarAdicaoDePontosPlayer1() {
        val status = model.alterarPontos(player = 1, valor = 3)
        assertEquals(MarcadorTrucoModel.StatusAlteracaoPontos.SUCESSO, status)
        assertEquals(3, model.pontosPlayer1)
        assertEquals(0, model.pontosPlayer2)
    }

    @Test
    fun testarAdicaoDePontosPlayer2() {
        val status = model.alterarPontos(player = 2, valor = 6)
        assertEquals(MarcadorTrucoModel.StatusAlteracaoPontos.SUCESSO, status)
        assertEquals(0, model.pontosPlayer1)
        assertEquals(6, model.pontosPlayer2)
    }

    @Test
    fun testarSubtracaoEmZeroRetornaAviso() {
        val statusP1 = model.alterarPontos(player = 1, valor = -1)
        assertEquals(MarcadorTrucoModel.StatusAlteracaoPontos.PONTUACAO_MINIMA_ATINGIDA, statusP1)
        assertEquals(0, model.pontosPlayer1)

        val statusP2 = model.alterarPontos(player = 2, valor = -1)
        assertEquals(MarcadorTrucoModel.StatusAlteracaoPontos.PONTUACAO_MINIMA_ATINGIDA, statusP2)
        assertEquals(0, model.pontosPlayer2)
    }

    @Test
    fun testarSubtracaoAposAdicao() {
        model.alterarPontos(player = 1, valor = 3)
        val status = model.alterarPontos(player = 1, valor = -1)
        assertEquals(MarcadorTrucoModel.StatusAlteracaoPontos.SUCESSO, status)
        assertEquals(2, model.pontosPlayer1)
    }

    @Test
    fun testarFinalizacaoPartidaPlayer1() {
        val status = model.alterarPontos(player = 1, valor = 12)
        assertEquals(MarcadorTrucoModel.StatusAlteracaoPontos.PARTIDA_FINALIZADA, status)
        assertEquals(0, model.pontosPlayer1)
        assertEquals(1, model.vitoriasPlayer1)
        assertEquals(1, model.ultimoGanhador)
    }

    @Test
    fun testarFinalizacaoPartidaPlayer2() {
        model.alterarPontos(player = 2, valor = 9)
        val status = model.alterarPontos(player = 2, valor = 3)
        assertEquals(MarcadorTrucoModel.StatusAlteracaoPontos.PARTIDA_FINALIZADA, status)
        assertEquals(0, model.pontosPlayer2)
        assertEquals(1, model.vitoriasPlayer2)
        assertEquals(2, model.ultimoGanhador)
    }

    @Test
    fun testarMudancaDeUltimoGanhador() {
        model.alterarPontos(player = 1, valor = 12)
        assertEquals(1, model.ultimoGanhador)

        model.alterarPontos(player = 2, valor = 12)
        assertEquals(2, model.ultimoGanhador)
    }

    @Test
    fun testarZerarPontosEVitorias() {
        model.alterarPontos(player = 1, valor = 12)
        model.alterarPontos(player = 1, valor = 3)

        model.zerarPontos()

        assertEquals(0, model.pontosPlayer1)
        assertEquals(0, model.pontosPlayer2)
        assertEquals(0, model.vitoriasPlayer1)
        assertEquals(0, model.vitoriasPlayer2)
        assertEquals(0, model.ultimoGanhador)
    }

    @Test
    fun testarZerarHistorico() {
        model.historico.add("Item 1")
        model.historico.add("Item 2")

        model.zerarHistorico()

        assertTrue(model.historico.isEmpty())
    }

    @Test
    fun testarAtualizacaoNomeNoHistorico() {
        model.historico.add("Jogador 1 marcou +3 pontos (Placar: 3 x 0)")
        model.historico.add("★ Jogador 1 VENCEU A PARTIDA! ★")

        model.atualizarNomeNoHistorico("Jogador 1", "Nós")

        assertEquals("Nós marcou +3 pontos (Placar: 3 x 0)", model.historico[0])
        assertEquals("★ Nós VENCEU A PARTIDA! ★", model.historico[1])
    }
}
package dev.rafaelfranca.marcadortruco

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @Test
    fun testarContextoPacote() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("dev.rafaelfranca.marcadortruco", appContext.packageName)
    }

    @Test
    fun testarExibicaoComponentesIniciais() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.txtPlayer1)).check(matches(isDisplayed()))
            onView(withId(R.id.txtPlayer2)).check(matches(isDisplayed()))
            onView(withId(R.id.txtNumPontosPlayer1)).check(matches(withText("0")))
            onView(withId(R.id.txtNumPontosPlayer2)).check(matches(withText("0")))
            onView(withId(R.id.btnMais1Player1)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_historico)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testarIncrementoPontosPlayer1() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btnMais1Player1)).perform(click())
            onView(withId(R.id.txtNumPontosPlayer1)).check(matches(withText("1")))

            onView(withId(R.id.btnMais3Player1)).perform(click())
            onView(withId(R.id.txtNumPontosPlayer1)).check(matches(withText("4")))
        }
    }

    @Test
    fun testarNavegacaoEEdicaoNomeJogador() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.ic_edit_player1)).perform(click())

            onView(withId(R.id.edtPlayer1)).check(matches(isDisplayed()))
            onView(withId(R.id.edtPlayer1)).perform(clearText(), typeText("Dupla 1"))
            onView(withId(R.id.btnSalvar)).perform(click())

            onView(withId(R.id.txtPlayer1)).check(matches(withText("Dupla 1")))
        }
    }

    @Test
    fun testarNavegacaoTelaHistoricoEVoltar() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btnMais1Player1)).perform(click())
            onView(withId(R.id.btn_historico)).perform(click())

            onView(withId(R.id.txtTituloHistorico)).check(matches(isDisplayed()))
            onView(withId(R.id.ic_voltar)).perform(click())
            onView(withId(R.id.txtNumPontosPlayer1)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testarDialogZerarPontosConfirmacao() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btnMais1Player1)).perform(click())
            onView(withId(R.id.txtNumPontosPlayer1)).check(matches(withText("1")))

            onView(withId(R.id.btn_zerar_pontos)).perform(click())
            onView(withText(R.string.dialog_sim)).perform(click())

            onView(withId(R.id.txtNumPontosPlayer1)).check(matches(withText("0")))
        }
    }

    @Test
    fun testarVitoriaEContagemComEstrela() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btnMais12Player1)).perform(click())

            onView(withId(R.id.txtNumPontosPlayer1)).check(matches(withText("0")))
            onView(withId(R.id.txtNumPontosPlayer2)).check(matches(withText("0")))

            onView(withId(R.id.txtVitoriasPlayer1)).check(matches(withText("Vitórias: 1 ⭐")))
            onView(withId(R.id.txtVitoriasPlayer2)).check(matches(withText("Vitórias: 0")))
        }
    }

    @Test
    fun testarAlternanciaEstrelaAposVitoriaPlayer2() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btnMais12Player1)).perform(click())
            onView(withId(R.id.txtVitoriasPlayer1)).check(matches(withText("Vitórias: 1 ⭐")))

            onView(withId(R.id.btnMais12Player2)).perform(click())

            onView(withId(R.id.txtVitoriasPlayer1)).check(matches(withText("Vitórias: 1")))
            onView(withId(R.id.txtVitoriasPlayer2)).check(matches(withText("Vitórias: 1 ⭐")))
        }
    }

    private fun pausa(ms: Long = 800) {
        Thread.sleep(ms)
    }

    @Ignore("Roteiro exclusivo para gravação de demonstração (GIF)")
    @Test
    fun gravarDemonstracaoGif() {
        ActivityScenario.launch(MainActivity::class.java).use {
            pausa(1200)

            // ==========================================
            // RODADA 1: Nomes Padrão - Vitória do Jogador 1
            // ==========================================
            onView(withId(R.id.btnMais3Player1)).perform(click())
            pausa(700)
            onView(withId(R.id.btnMais1Player2)).perform(click())
            pausa(700)
            onView(withId(R.id.btnMais9Player1)).perform(click())
            pausa(1200)

            // Exibe o histórico ao final da Rodada 1
            onView(withId(R.id.btn_historico)).perform(click())
            pausa(1800)
            onView(withId(R.id.ic_voltar)).perform(click())
            pausa(1000)

            // Zera os pontos para iniciar nova rodada
            onView(withId(R.id.btn_zerar_pontos)).perform(click())
            pausa(800)
            onView(withText(R.string.dialog_sim)).perform(click())
            pausa(1000)

            // ==========================================
            // RODADA 2: Edita Nomes ("Nós" x "Eles") - Vitória do Jogador 2 ("Eles")
            // ==========================================
            onView(withId(R.id.ic_edit_player1)).perform(click())
            pausa(600)
            onView(withId(R.id.edtPlayer1)).perform(replaceText("Nós"), closeSoftKeyboard())
            pausa(600)
            onView(withId(R.id.btnSalvar)).perform(click())
            pausa(800)

            onView(withId(R.id.ic_edit_player2)).perform(click())
            pausa(600)
            onView(withId(R.id.edtPlayer2)).perform(replaceText("Eles"), closeSoftKeyboard())
            pausa(600)
            onView(withId(R.id.btnSalvar)).perform(click())
            pausa(1000)

            onView(withId(R.id.btnMais6Player2)).perform(click())
            pausa(700)
            onView(withId(R.id.btnMais3Player1)).perform(click())
            pausa(700)
            onView(withId(R.id.btnMais6Player2)).perform(click())
            pausa(1200)

            // Exibe o histórico ao final da Rodada 2
            onView(withId(R.id.btn_historico)).perform(click())
            pausa(1800)
            onView(withId(R.id.ic_voltar)).perform(click())
            pausa(1000)

            // ==========================================
            // RODADA 3: Vitória do Jogador 1 ("Nós") - Estrela ⭐ alterna para "Nós"
            // ==========================================
            onView(withId(R.id.btnMais3Player1)).perform(click())
            pausa(700)
            onView(withId(R.id.btnMais1Player2)).perform(click())
            pausa(700)
            onView(withId(R.id.btnMais9Player1)).perform(click())
            pausa(1200)

            // Exibe o histórico ao final da Rodada 3
            onView(withId(R.id.btn_historico)).perform(click())
            pausa(1800)
            onView(withId(R.id.ic_voltar)).perform(click())
            pausa(1000)

            // ==========================================
            // ENCERRAMENTO: Limpa Histórico e Pontos
            // ==========================================
            onView(withId(R.id.btn_zerar_historico)).perform(click())
            pausa(800)
            onView(withText(R.string.dialog_sim)).perform(click())
            pausa(1000)

            onView(withId(R.id.btn_zerar_pontos)).perform(click())
            pausa(800)
            onView(withText(R.string.dialog_sim)).perform(click())
            pausa(1500)
        }
    }
}
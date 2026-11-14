package dev.rafaelfranca.marcadortruco

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.rafaelfranca.marcadortruco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model = MarcadorTrucoModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Entrada
        model.nomePlayer1 = binding.txtPlayer1.text.toString()
        model.nomePlayer2 = binding.txtPlayer2.text.toString()

        configurarBotoesPontuacao()
        configurarNavegacao()
    }

    // Entrada
    private val launcherEdicaoNomes = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val novoNomeP1 = result.data?.getStringExtra("NOVO_NOME_P1")
            val novoNomeP2 = result.data?.getStringExtra("NOVO_NOME_P2")

            val antigoNomeP1 = binding.txtPlayer1.text.toString()
            val antigoNomeP2 = binding.txtPlayer2.text.toString()

            // Processamento
            if (!novoNomeP1.isNullOrBlank() && novoNomeP1 != antigoNomeP1) {
                model.atualizarNomeNoHistorico(antigoNomeP1, novoNomeP1)
                model.nomePlayer1 = novoNomeP1
                // Saída
                binding.txtPlayer1.text = novoNomeP1
            }

            if (!novoNomeP2.isNullOrBlank() && novoNomeP2 != antigoNomeP2) {
                model.atualizarNomeNoHistorico(antigoNomeP2, novoNomeP2)
                model.nomePlayer2 = novoNomeP2
                // Saída
                binding.txtPlayer2.text = novoNomeP2
            }
        }
    }

    private fun configurarNavegacao() {
        // Entrada
        binding.btnHistorico.setOnClickListener {
            // Saída
            val intent = Intent(this, HistoricoActivity::class.java).apply {
                putStringArrayListExtra("LISTA_HISTORICO", model.historico)
            }
            startActivity(intent)
        }

        binding.btnZerarPontos.setOnClickListener {
            // Saída
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_zerar_pontos_title))
                .setMessage(getString(R.string.dialog_zerar_pontos_msg))
                .setPositiveButton(getString(R.string.dialog_sim)) { _, _ ->
                    // Processamento
                    model.zerarPontos()
                    model.historico.add(0, getString(R.string.historico_placar_zerado))

                    // Saída
                    atualizarPlacarVisual()
                    Toast.makeText(this, getString(R.string.toast_pontuacao_zerada), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.dialog_nao), null)
                .show()
        }

        binding.imgJoker.setOnClickListener {
            // Saída
            Toast.makeText(this, "Desenvolvido com ❤\uFE0F por francadev\nAproveite o jogo!", Toast.LENGTH_SHORT).show()
        }

        binding.btnZerarHistorico.setOnClickListener {
            // Saída
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_zerar_historico_title))
                .setMessage(getString(R.string.dialog_zerar_historico_msg))
                .setPositiveButton(getString(R.string.dialog_sim)) { _, _ ->
                    // Processamento
                    model.zerarHistorico()

                    // Saída
                    Toast.makeText(this, getString(R.string.toast_historico_apagado), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.dialog_nao), null)
                .show()
        }

        binding.icEditPlayer1.setOnClickListener {
            abrirEdicao(focoPlayer = 1)
        }

        binding.icEditPlayer2.setOnClickListener {
            abrirEdicao(focoPlayer = 2)
        }
    }

    private fun abrirEdicao(focoPlayer: Int) {
        // Saída
        val intent = Intent(this, EdicaoNomesActivity::class.java).apply {
            putExtra("FOCO_PLAYER", focoPlayer)
            putExtra("NOME_ATUAL_PLAYER1", binding.txtPlayer1.text.toString())
            putExtra("NOME_ATUAL_PLAYER2", binding.txtPlayer2.text.toString())
        }
        launcherEdicaoNomes.launch(intent)
    }

    private fun configurarBotoesPontuacao() {
        // Entrada
        binding.btnMais1Player1.setOnClickListener { alterarPontos(1, 1) }
        binding.btnMais3Player1.setOnClickListener { alterarPontos(1, 3) }
        binding.btnMais6Player1.setOnClickListener { alterarPontos(1, 6) }
        binding.btnMais9Player1.setOnClickListener { alterarPontos(1, 9) }
        binding.btnMais12Player1.setOnClickListener { alterarPontos(1, 12) }
        binding.btnMenos1Player1.setOnClickListener { alterarPontos(1, -1) }

        binding.btnMais1Player2.setOnClickListener { alterarPontos(2, 1) }
        binding.btnMais3Player2.setOnClickListener { alterarPontos(2, 3) }
        binding.btnMais6Player2.setOnClickListener { alterarPontos(2, 6) }
        binding.btnMais9Player2.setOnClickListener { alterarPontos(2, 9) }
        binding.btnMais12Player2.setOnClickListener { alterarPontos(2, 12) }
        binding.btnMenos1Player2.setOnClickListener { alterarPontos(2, -1) }
    }

    private fun alterarPontos(player: Int, valor: Int) {
        // Entrada
        val nomeP1 = binding.txtPlayer1.text.toString()
        val nomeP2 = binding.txtPlayer2.text.toString()
        model.nomePlayer1 = nomeP1
        model.nomePlayer2 = nomeP2

        // Processamento
        val resultado = model.alterarPontos(player, valor)
        val nomeAtivo = if (player == 1) nomeP1 else nomeP2
        val sinal = if (valor > 0) "+$valor" else "$valor"

        // Saída
        when (resultado) {
            MarcadorTrucoModel.StatusAlteracaoPontos.PONTUACAO_MINIMA_ATINGIDA -> {
                Toast.makeText(this, getString(R.string.toast_pontuacao_minima), Toast.LENGTH_SHORT).show()
            }
            MarcadorTrucoModel.StatusAlteracaoPontos.PARTIDA_FINALIZADA -> {
                model.historico.add(
                    0,
                    getString(R.string.historico_pontos_sem_placar, nomeAtivo, sinal)
                )
                model.historico.add(
                    0,
                    getString(R.string.historico_jogador_venceu, nomeAtivo)
                )
                Toast.makeText(this, getString(R.string.toast_jogador_venceu, nomeAtivo), Toast.LENGTH_SHORT).show()
                atualizarPlacarVisual()
            }
            MarcadorTrucoModel.StatusAlteracaoPontos.SUCESSO -> {
                val p1 = model.pontosPlayer1
                val p2 = model.pontosPlayer2
                model.historico.add(
                    0,
                    getString(R.string.historico_pontos_marcados, nomeAtivo, sinal, p1, p2)
                )
                atualizarPlacarVisual()
            }
        }
    }

    private fun atualizarPlacarVisual() {
        // Saída
        binding.txtNumPontosPlayer1.text = model.pontosPlayer1.toString()
        binding.txtNumPontosPlayer2.text = model.pontosPlayer2.toString()

        val formatP1 = if (model.ultimoGanhador == 1) R.string.vitorias_com_estrela_format else R.string.vitorias_format
        val formatP2 = if (model.ultimoGanhador == 2) R.string.vitorias_com_estrela_format else R.string.vitorias_format

        binding.txtVitoriasPlayer1.text = getString(formatP1, model.vitoriasPlayer1)
        binding.txtVitoriasPlayer2.text = getString(formatP2, model.vitoriasPlayer2)
    }
}
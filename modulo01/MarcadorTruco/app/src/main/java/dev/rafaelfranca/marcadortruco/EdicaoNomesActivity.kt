package dev.rafaelfranca.marcadortruco

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import dev.rafaelfranca.marcadortruco.databinding.ActivityEdicaoNomesBinding

class EdicaoNomesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEdicaoNomesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEdicaoNomesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Entrada
        binding.edtPlayer1.setText(intent.getStringExtra("NOME_ATUAL_PLAYER1") ?: "")
        binding.edtPlayer2.setText(intent.getStringExtra("NOME_ATUAL_PLAYER2") ?: "")
        val playerAlvo = intent.getIntExtra("FOCO_PLAYER", 0)

        binding.icVoltar.setOnClickListener {
            finish()
        }

        configurarAvisoLimite(binding.edtPlayer1)
        configurarAvisoLimite(binding.edtPlayer2)

        // Processamento
        when (playerAlvo) {
            1 -> focarCampo(binding.edtPlayer1)
            2 -> focarCampo(binding.edtPlayer2)
        }

        binding.btnSalvar.setOnClickListener {
            // Processamento
            val novoP1 = binding.edtPlayer1.text.toString().trim()
            val novoP2 = binding.edtPlayer2.text.toString().trim()

            if (novoP1.isEmpty() || novoP2.isEmpty()) {
                // Saída
                Toast.makeText(this, getString(R.string.toast_nome_obrigatorio), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Saída
            val resultadoIntent = Intent().apply {
                putExtra("NOVO_NOME_P1", novoP1)
                putExtra("NOVO_NOME_P2", novoP2)
            }

            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }

    private fun configurarAvisoLimite(editText: TextInputEditText) {
        var avisouLimite = false
        // Entrada
        editText.doOnTextChanged { text, _, _, _ ->
            // Processamento
            if ((text?.length ?: 0) >= 30) {
                if (!avisouLimite) {
                    // Saída
                    Toast.makeText(this, getString(R.string.toast_limite_caracteres), Toast.LENGTH_SHORT).show()
                    avisouLimite = true
                }
            } else {
                avisouLimite = false
            }
        }
    }

    private fun focarCampo(editText: TextInputEditText) {
        // Processamento
        editText.post {
            editText.requestFocus()
            editText.setSelection(editText.text?.length ?: 0)
            val insetsController = WindowCompat.getInsetsController(window, editText)
            insetsController.show(WindowInsetsCompat.Type.ime())
        }
    }
}
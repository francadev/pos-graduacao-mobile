package dev.rafaelfranca.marcadortruco

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.rafaelfranca.marcadortruco.databinding.ActivityHistoricoBinding

class HistoricoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.icVoltar.setOnClickListener {
            finish()
        }

        val listaJogadas = intent.getStringArrayListExtra("LISTA_HISTORICO") ?: arrayListOf()
        val adapter = HistoricoAdapter(listaJogadas)
        binding.rvHistorico.adapter = adapter
    }
}
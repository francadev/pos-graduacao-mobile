package dev.rafaelfranca.calculaimc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var tvResultado: TextView
    private lateinit var tvClassificacao: TextView
    private lateinit var btCalcular: Button
    private lateinit var btLimpar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        tvResultado = findViewById(R.id.tvResultado)
        tvClassificacao = findViewById(R.id.tvClassificacao)
        btCalcular = findViewById(R.id.btCalcular)
        btLimpar = findViewById(R.id.btLimpar)

        btCalcular.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_bt_calcular_clicado), Toast.LENGTH_SHORT).show()
            btCalcularOnClick()
        }

        btLimpar.setOnClickListener {
            btLimparOnClick()
        }

        btCalcular.setOnLongClickListener {
            Toast.makeText(this, getString(R.string.toast_bt_calcular_long_click), Toast.LENGTH_SHORT).show()
            false
        }

        btLimpar.setOnLongClickListener {
            Toast.makeText(this, getString(R.string.toast_bt_limpar_long_click), Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun btCalcularOnClick() {
        val peso = ImcCalculator.parseInputDouble(etPeso.text.toString()) ?: run {
            etPeso.error = getString(R.string.error_peso)
            etPeso.requestFocus()
            return
        }
        val altura = ImcCalculator.parseInputDouble(etAltura.text.toString()) ?: run {
            etAltura.error = getString(R.string.error_altura)
            etAltura.requestFocus()
            return
        }

        val isImperial = Locale.getDefault().language == "en"
        val resultado = ImcCalculator.calcularImc(peso, altura, isImperial)

        if (isImperial) {
            val nf = NumberFormat.getNumberInstance(Locale.US)
            val df = nf as DecimalFormat
            tvResultado.text = df.format(resultado)
        } else {
            tvResultado.text = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", resultado)
        }

        // Torna o resultado visível ao calcular
        tvResultado.visibility = View.VISIBLE
        tvClassificacao.text = getString(ImcCalculator.getClassificacaoResId(resultado))
    }

    private fun btLimparOnClick() {
        etPeso.text.clear()
        etAltura.text.clear()
        tvResultado.text = getString(R.string.resultado_default)
        tvClassificacao.text = getString(R.string.classificacao_default)
        etPeso.requestFocus()
        tvResultado.visibility = View.GONE
    }
}
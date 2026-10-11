package dev.rafaelfranca.calculaimc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var tvResultado: TextView
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
        btCalcular = findViewById(R.id.btCalcular)
        btLimpar = findViewById(R.id.btLimpar)

        btCalcular.setOnClickListener {
            Toast.makeText(this,"Botão 'Calcular' clicado!",Toast.LENGTH_SHORT).show()
            btCalcularOnClick()
        }

        btLimpar.setOnClickListener {
            btLimparOnClick()
        }

        btCalcular.setOnLongClickListener {
            Toast.makeText(this,"Botão calcular IMC de clique longo",Toast.LENGTH_SHORT).show()
            false
        }

        btLimpar.setOnLongClickListener {
            Toast.makeText(this,"Botão limpar de clique longo!",Toast.LENGTH_SHORT).show()
            true
        }
    }  // fim do onCreate()

    private fun btCalcularOnClick() {
        var resultado: Double = 0.0

        val peso: Double = etPeso.text.toString().toDoubleOrNull() ?: run {
            etPeso.error = "Campo peso deve ser informado!"
            etPeso.requestFocus()
            return
        }
        val altura: Double = etAltura.text.toString().toDoubleOrNull() ?: run {
            etAltura.error = "Campo altura deve ser informado!"
            etAltura.requestFocus()
            return
        }

        resultado = peso / altura.pow(2)
        tvResultado.text = "%.2f".format(resultado)
    }
    private fun btLimparOnClick() {
        etPeso.text.clear()
        etAltura.text.clear()
        tvResultado.text = "0.0"
        etPeso.requestFocus()
    }
} // fim do MainActivity
package dev.rafaelfranca.trocatela

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfirmarActivity : AppCompatActivity() {

    private lateinit var tvCod: TextView
    private lateinit var tvQtd: TextView
    private lateinit var tvVaLor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvCod = findViewById(R.id.tvCod)
        tvQtd = findViewById(R.id.tvQtd)
        tvVaLor = findViewById(R.id.tvValor)

        tvCod.text = intent.getStringExtra("cod")
        tvQtd.text = intent.getStringExtra("qtd")
        tvVaLor.text = intent.getStringExtra("valor")
    }

    fun btSmsOnClick(view: View) {}
}
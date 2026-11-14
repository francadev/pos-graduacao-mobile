package dev.rafaelfranca.tiptime

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.rafaelfranca.tiptime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.calculateButton.setOnClickListener { calculateTip() }
        displayTip(0.0)

        if (savedInstanceState != null) {
            binding.tipResult.text = getString(R.string.tip_amount, savedInstanceState.getString("result"))
        } else {
            binding.tipResult.text = getString(R.string.tip_amount, "0.0")
        }
    }

    private fun displayTip(tip: Double) {
        val formattedTip = java.text.NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        val selectedId = binding.tipOption.checkedRadioButtonId
        val roundUp = binding.roundUpSwitch.isChecked

        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPercentage

        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }

    fun showClearHistoryDialog() {
        AlertDialog.Builder(this)
            .setTitle("Limpar")
            .setMessage("Deseja realmente apagar?")
            .setPositiveButton("Sim") { dialog, _ ->
                clearHistory()
                dialog.dismiss()
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun clearHistory() {
        binding.costOfService.setText("")
        binding.tipOption.check(R.id.option_twenty_percent)
        binding.roundUpSwitch.isChecked = true
        displayTip(0.0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("result", binding.tipResult.text.toString())
    }
}
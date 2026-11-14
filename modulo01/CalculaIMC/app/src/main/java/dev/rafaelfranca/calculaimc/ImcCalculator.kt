package dev.rafaelfranca.calculaimc

import kotlin.math.pow

object ImcCalculator {

    fun parseInputDouble(input: String): Double? {
        return input.trim().replace(',', '.').toDoubleOrNull()
    }

    fun calcularImc(peso: Double, altura: Double, isImperial: Boolean = false): Double {
        if (altura <= 0.0) return 0.0
        return if (isImperial) {
            703.0 * (peso / altura.pow(2))
        } else {
            peso / altura.pow(2)
        }
    }

    fun getClassificacaoResId(imc: Double): Int {
        return when {
            imc < 18.5 -> R.string.classificacao_abaixo_peso
            imc < 25.0 -> R.string.classificacao_peso_normal
            imc < 30.0 -> R.string.classificacao_sobrepeso
            imc < 35.0 -> R.string.classificacao_obesidade_1
            imc < 40.0 -> R.string.classificacao_obesidade_2
            else -> R.string.classificacao_obesidade_3
        }
    }
}
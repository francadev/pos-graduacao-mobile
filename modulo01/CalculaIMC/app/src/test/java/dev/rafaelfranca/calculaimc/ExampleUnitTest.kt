package dev.rafaelfranca.calculaimc

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Testes unitários para a lógica de cálculo e classificação do IMC ([ImcCalculator]).
 */
class ExampleUnitTest {

    @Test
    fun parseInputDouble_aceitaPontoEVirgula() {
        assertEquals(70.5, ImcCalculator.parseInputDouble("70,5")!!, 0.001)
        assertEquals(1.75, ImcCalculator.parseInputDouble("1.75")!!, 0.001)
        assertEquals(80.0, ImcCalculator.parseInputDouble(" 80 ")!!, 0.001)
        assertNull(ImcCalculator.parseInputDouble("abc"))
        assertNull(ImcCalculator.parseInputDouble(""))
    }

    @Test
    fun calcularImc_sistemaMetrico_calculaComSucesso() {
        val peso = 70.0
        val altura = 1.75
        val imcEsperado = 22.857142857142858

        val imcCalculado = ImcCalculator.calcularImc(peso, altura, isImperial = false)

        assertEquals(imcEsperado, imcCalculado, 0.0001)
    }

    @Test
    fun calcularImc_sistemaImperial_calculaComSucesso() {
        val peso = 154.0 // libras
        val altura = 68.0 // polegadas
        val imcEsperado = 703.0 * (154.0 / (68.0 * 68.0)) // ~23.41

        val imcCalculado = ImcCalculator.calcularImc(peso, altura, isImperial = true)

        assertEquals(imcEsperado, imcCalculado, 0.0001)
    }

    @Test
    fun calcularImc_alturaZeroOuNegativa_retornaZero() {
        assertEquals(0.0, ImcCalculator.calcularImc(70.0, 0.0), 0.0001)
        assertEquals(0.0, ImcCalculator.calcularImc(70.0, -1.75), 0.0001)
    }

    @Test
    fun getClassificacaoResId_retornaRecursoCorretoParaCadaFaixa() {
        // Abaixo do peso (< 18.5)
        assertEquals(ImcCalculator.getClassificacaoResId(17.0), R.string.classificacao_abaixo_peso)

        // Peso normal (18.5 - 24.9)
        assertEquals(ImcCalculator.getClassificacaoResId(22.0), R.string.classificacao_peso_normal)

        // Sobrepeso (25.0 - 29.9)
        assertEquals(ImcCalculator.getClassificacaoResId(27.5), R.string.classificacao_sobrepeso)

        // Obesidade I (30.0 - 34.9)
        assertEquals(ImcCalculator.getClassificacaoResId(32.0), R.string.classificacao_obesidade_1)

        // Obesidade II (35.0 - 39.9)
        assertEquals(ImcCalculator.getClassificacaoResId(37.0), R.string.classificacao_obesidade_2)

        // Obesidade III (>= 40.0)
        assertEquals(ImcCalculator.getClassificacaoResId(42.0), R.string.classificacao_obesidade_3)
    }
}
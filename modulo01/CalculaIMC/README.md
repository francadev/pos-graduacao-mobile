# Calcula IMC - Android básico (Módulo 01)

Projeto desenvolvido durante a primeira aula prática do Módulo 01 (Android Básico) da especialização com o professor Robison Cris Brito.

---

## Demonstração

<p align="center">
  <img src="assets/CalculaIMCv2.gif" alt="Calcula IMC" width="300" />
</p>

---

## Desenvolvimento do aplicativo

O desenvolvimento prático do projeto foi concentrado na construção da interface moderna no arquivo [activity_main.xml](app/src/main/res/layout/activity_main.xml), na lógica do aplicativo em [MainActivity.kt](app/src/main/java/dev/rafaelfranca/calculaimc/MainActivity.kt) e na regra de negócio em [ImcCalculator.kt](app/src/main/java/dev/rafaelfranca/calculaimc/ImcCalculator.kt).

### Layout da tela (`activity_main.xml`)

* Estruturação da interface moderna utilizando `ConstraintLayout` e cartões arredondados com elevação.
* Uso de componentes estilizados com Material 3 (`MaterialButton`).
* Configuração e estilização dos componentes de interface:
  * `TextView` para os rótulos, títulos e exibição do resultado do IMC.
  * `EditText` configurado com `android:digits="0123456789.,"` para permitir a digitação tanto de vírgula quanto de ponto.
  * Botões interativos para as ações de calcular o IMC e limpar os dados.

### Lógica da aplicação e internacionalização (`MainActivity.kt` e `strings.xml`)

* Conexão dos componentes visuais com o código Kotlin utilizando `findViewById`.
* **Suporte a múltiplos idiomas (Locales):**
  * [values/strings.xml](app/src/main/res/values/strings.xml): Arquivo padrão com textos em inglês (`en`).
  * [values-pt/strings.xml](app/src/main/res/values-pt/strings.xml): Arquivo de recursos com textos em português (`pt-BR`).
* **Adaptação dinâmica por idioma:**
  * Tratamento do cálculo no sistema métrico (kg e metros) para português e no sistema imperial (libras e polegadas) para inglês.
  * Formatação decimal adequada por localização (vírgula em `pt-BR` e ponto em `en`).
* Tratamento de eventos de clique (`setOnClickListener` e `setOnLongClickListener`) e controle de visibilidade da resposta (`View.VISIBLE` e `View.GONE`).
* Validação de campos obrigatórios com mensagens de erro direcionadas via `setError()`, foco automático com `requestFocus()` e alertas visuais com `Toast`.

---

## Testes unitários (`ExampleUnitTest.kt`)

As regras de negócio do cálculo e conversão de dados foram isoladas no objeto [ImcCalculator.kt](app/src/main/java/dev/rafaelfranca/calculaimc/ImcCalculator.kt) e validadas através de testes unitários automatizados em [ExampleUnitTest.kt](app/src/test/java/dev/rafaelfranca/calculaimc/ExampleUnitTest.kt):

* **Conversão de entradas (`parseInputDouble`):** Garante que valores informados com vírgula ou ponto sejam convertidos corretamente para `Double`.
* **Cálculo do IMC (`calcularImc`):** Valida os resultados das fórmulas nos sistemas métrico e imperial.
* **Validação de limites:** Garante tratamento seguro para alturas zeradas ou negativas.
* **Classificação corporal (`getClassificacaoResId`):** Confirma se todas as faixas de IMC (Abaixo do peso, Peso normal, Sobrepeso e Obesidades) retornam as mensagens corretas.

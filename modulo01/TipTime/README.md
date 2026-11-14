# Tip Time - Android básico (Módulo 01)

Projeto desenvolvido durante a primeira e segunda aula prática do Módulo 01 (Android Básico) da especialização com o professor Robison Cris Brito.

---

## Demonstração

<p align="center">
  <img src="assets/TipTime.gif" alt="Tip Time" width="300" />
</p>

---

## Desenvolvimento do aplicativo

O desenvolvimento prático do projeto foi concentrado na construção da interface moderna no arquivo [activity_main.xml](app/src/main/res/layout/activity_main.xml), na lógica do aplicativo em [MainActivity.kt](app/src/main/java/dev/rafaelfranca/tiptime/MainActivity.kt) e nos recursos da aplicação em [strings.xml](app/src/main/res/values/strings.xml) e temas em [themes.xml](app/src/main/res/values/themes.xml).

### Layout da tela (`activity_main.xml`)

* Estruturação da interface responsiva utilizando `ScrollView` envolvendo um `ConstraintLayout` para permitir a rolagem em telas menores.
* Uso de componentes estilizados com Material Design 3:
  * `TextInputLayout` e `TextInputEditText` configurados com `android:inputType="numberDecimal"` para entrada do valor do serviço.
  * `RadioGroup` e `RadioButton` para a seleção da porcentagem da gorjeta (20%, 18% e 15%).
  * `SwitchCompat` (`androidx.appcompat.widget.SwitchCompat`) para alternar a opção de arredondamento da gorjeta.
  * Ícones vetoriais (`VectorDrawable`) adaptáveis ao tema claro/escuro utilizando `android:tint="?attr/colorControlNormal"`.
  * `Button` interativo para ação de calcular e `TextView` para exibição do resultado da gorjeta.

### Lógica da aplicação e temas (`MainActivity.kt`, `themes.xml` e `strings.xml`)

* Conexão dos componentes visuais via **ViewBinding** (`ActivityMainBinding`).
* Suporte ao **Edge-to-Edge** com tratamento de insets das barras do sistema via `WindowInsetsCompat`.
* **Cálculo dinâmico da gorjeta:**
  * Conversão segura do valor informado para `Double` e seleção da porcentagem.
  * Arredondamento opcional do valor final com a função `kotlin.math.ceil`.
  * Formatação monetária automática de acordo com o idioma/locale do dispositivo usando `NumberFormat.getCurrencyInstance()`.
* **Persistência de estado da tela (`onSaveInstanceState`):** Preservação do resultado calculado durante a rotação de tela.
* **Diálogo de confirmação (`AlertDialog`):** Diálogo interativo com botões "Sim" e "Não" para confirmação antes de apagar/limpar o histórico de dados.
* **Suporte a tema claro e escuro (Light / Dark Mode):**
  * Configuração de paleta de cores personalizada em `colors.xml`, `themes.xml` e `values-night/themes.xml`.

---

## Testes instrumentados de UI (`ExampleInstrumentedTest.kt`)

As interações da interface foram validadas através de testes de UI automatizados com **Espresso** em [ExampleInstrumentedTest.kt](app/src/androidTest/java/dev/rafaelfranca/tiptime/ExampleInstrumentedTest.kt):

* **Automação de ações de usuário:** Preenchimento do campo de texto com `typeText`, fechamento do teclado com `closeSoftKeyboard` e acionamento do botão via `click`.
* **Verificação dinâmica do resultado:** Validação do texto final no `TextView` utilizando `matches(withText(containsString(...)))` combinado com a formatação por localização (`NumberFormat.getCurrencyInstance()`), garantindo compatibilidade entre diferentes locales/idiomas.

# Calcula IMC - Android básico (Módulo 01)

Projeto desenvolvido durante a primeira aula prática do Módulo 01 (Android Básico) da especialização com o professor Robison Cris Brito.

---

## Demonstração

<p align="center">
  <img src="assets/Calcula IMC.gif" alt="Calcula IMC" width="300" />
</p>

---

## Desenvolvimento do aplicativo

O desenvolvimento prático do projeto foi concentrado na construção da interface no arquivo [activity_main.xml](app/src/main/res/layout/activity_main.xml) e na lógica do aplicativo em [MainActivity.kt](app/src/main/java/dev/rafaelfranca/calculaimc/MainActivity.kt).

### Layout da tela (`activity_main.xml`)

* Estruturação da interface de usuário utilizando `LinearLayout` com orientações vertical e horizontal.
* Ajustes de dimensões dos elementos com `match_parent` e `wrap_content`.
* Configuração e estilização dos componentes básicos da interface:
  * `TextView` para os rótulos e para a exibição do resultado do IMC.
  * `EditText` para a entrada de dados de peso e altura.
  * `Button` para disparar as ações de calcular e limpar.

### Lógica da aplicação (`MainActivity.kt`)

* Conexão dos componentes visuais com o código Kotlin utilizando `findViewById`.
* Tratamento de eventos de clique com `setOnClickListener` e de clique longo com `setOnLongClickListener`.
* Conversão segura de tipos de dados de entrada com `toDoubleOrNull()`.
* Validação de campos obrigatórios com mensagens de erro via `setError()`, direcionamento do foco com `requestFocus()` e alertas temporários via `Toast`.
* Aplicação de técnicas de depuração no Android Studio com filtros no Logcat, *breakpoints* e inspeção de variáveis no painel Threads & Variables (*Watches* e *Evaluate Expression*).

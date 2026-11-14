# Marcador de Truco - Android básico (Módulo 01)

Aplicativo desenvolvido para a avaliação final do Módulo 01 (Android Básico) da pós-graduação em Desenvolvimento Mobile com o professor Robison Cris Brito.

## Demonstração

<p align="center"><img src="assets/MarcadorTruco.gif" alt="Marcador de Truco" width="300" /></p>

## Desenvolvimento do aplicativo

Principais arquivos do projeto: [activity_main.xml](app/src/main/res/layout/activity_main.xml), [activity_edicao_nomes.xml](app/src/main/res/layout/activity_edicao_nomes.xml), [activity_historico.xml](app/src/main/res/layout/activity_historico.xml), [item_historico.xml](app/src/main/res/layout/item_historico.xml), [MainActivity.kt](app/src/main/java/dev/rafaelfranca/marcadortruco/MainActivity.kt), [EdicaoNomesActivity.kt](app/src/main/java/dev/rafaelfranca/marcadortruco/EdicaoNomesActivity.kt), [HistoricoActivity.kt](app/src/main/java/dev/rafaelfranca/marcadortruco/HistoricoActivity.kt), [HistoricoAdapter.kt](app/src/main/java/dev/rafaelfranca/marcadortruco/HistoricoAdapter.kt), [MarcadorTrucoModel.kt](app/src/main/java/dev/rafaelfranca/marcadortruco/MarcadorTrucoModel.kt), [strings.xml](app/src/main/res/values-pt-rBR/strings.xml) e [themes.xml](app/src/main/res/values/themes.xml).

### Layout da tela

- **Estruturação responsiva:** Uso de `ConstraintLayout` interno a `ScrollView` (`fillViewport="true"`), com divisão de colunas orientada por `Guideline` vertical de 50%.
- **Campos e restrições de entrada:** `TextInputLayout` com estilo `OutlinedBox`, limite de 30 caracteres (`counterMaxLength="30"`), contador visual e modo `clear_text`.
- **Lista de histórico:** `RecyclerView` parametrizado com `LinearLayoutManager` utilizando `item_historico.xml` para exibição sequencial das jogadas.

### Lógica da aplicação e temas

- **Gerenciamento de regras de negócio:** `MarcadorTrucoModel.kt` encapsula a contagem de pontos (0 a 12), trava de pontuação mínima, encerramento de partidas e controle da estrela de último vencedor.
- **Comunicação entre Activities:** Uso de `ActivityResultContracts.StartActivityForResult()` para receber os nomes alterados em `EdicaoNomesActivity.kt` e atualizar o histórico acumulado.
- **Internacionalização e suporte a temas:** Textos desacoplados em `strings.xml` (Inglês padrão e Português `pt-rBR`) e suporte aos modos claro e escuro via `DayNight.NoActionBar`.

## Testes instrumentados de UI ([MainActivityInstrumentedTest.kt](app/src/androidTest/java/dev/rafaelfranca/marcadortruco/MainActivityInstrumentedTest.kt))

- **Validação de interação e estado:** Fluxos automatizados com Espresso e `ActivityScenario` cobrindo adição/subtração de pontos, acionamento de diálogos `AlertDialog` e resete de placar.
- **Integração entre telas:** Testes instrumentados validando a navegação entre a tela principal, formulário de edição de nomes e visualização da lista de histórico.

## Download (APK)

Baixe a versão de testes diretamente pelo arquivo [MarcadorTruco (app-debug.apk)](app/build/outputs/apk/debug/app-debug.apk).
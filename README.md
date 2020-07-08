# EventCheckIn
Teste para a vaga de desenvolvimento Android.

O Objetivo do projeto era criar um aplicativo que consuma uma REST API, exibir a listagem de eventos na tela e acessar os detalhes do mesmo.

# Arquitetura e dependencias

**Arquitetura**

A arquitetura escolhida para o desenvolvimento foi a _Model View ViewModel(MVVM)_. Esta arquitetura foi escolhida para atuar em conjunto com o **DataBinding** facilitanto o código e beneficiando os testes.

Blueprint:
- Archive: Arquivos de ajuda. Onde ficam os arquivos de extension do kotlin(Como formatação de preço, data e etc...)
- Data: Serviços e chamadas de API. Onde ficam o datasource e o network do projeto.
- View: Screens, Adapters e ViewHolders. Apresentam as informações para o usuário.
- ViewModel: Tratam as regras de negócio do projeto.

**Dependencias**
- Projeto:
  - _Kotlin: 1.3.72_ -> Linguagem do projeto
  - _Gradle: 4.0.0_ -> Copilador
  - _Coroutines: 1.3.7_ -> Para tasks async
  - _RecyclerView: 1.2.0-alpha04_ -> Para listas do projeto
  - _LifecycleExtensions: 2.2.0_ -> Para os Providers da ViewModel
  
- Network digest:
  - _Retrofit: 2.8.1_
  - _OkHttp3 LoggingInterceptor: 3.12.12_
  - _Glide: 4.11.0_
 
- Tests:
  - _JUnit 4.13_
  - _Mockito 3.1.0 ~Kotlin: 2.2.0_
  - _OkHttp3 MockWebServer: 3.12.12_
  - _Robolectric: 4.3.1_
  - _Espresso: 3.2.0_


# Layout
O layout foi pensado e desenvolvido em uma plataforma chamada **whimsical**. (Imagem do layout previsto para aplicação)

<img src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/layout.png" alt="" data-canonical-src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/layout.png" height="400" />

# Aplicação
[EventsListActivity:](https://github.com/Wottrich/test-android-sicredi/blob/master/EventCheckIn/app/src/main/java/wottrich/github/io/eventcheckin/view/EventsListActivity.kt)
A listagem de eventos é onde nosso aplicativo começa. Listando os eventos que estão no [endpoint](http://5b840ba5db24a100142dcd8c.mockapi.io/api/events) que foi disponibilizado, na tela nós aprensentamos um item de lista com o resumo do evento, contendo: nome do evento, data evento, descrição e a imagem do evento.

<img src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/event_list.png" alt="" data-canonical-src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/event_list.png" width="200" height="400" />

[EventDetailActivity:](https://github.com/Wottrich/test-android-sicredi/blob/master/EventCheckIn/app/src/main/java/wottrich/github/io/eventcheckin/view/EventDetailActivity.kt)
Na tela de detalhes é apresentado o evento completo onde o usuário poderá ver: todas as informações diponiveis anteriormente, preço, localização, compartilhar e fazer check-in no evento.

<img src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/event_detail.png" alt="" data-canonical-src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/event_detail.png" width="200" height="400" />

[CheckInDialog:](https://github.com/Wottrich/test-android-sicredi/blob/master/EventCheckIn/app/src/main/java/wottrich/github/io/eventcheckin/view/dialog/CheckInDialog.kt)
CheckInDialog é uma dialog que aparece para confirmação quando o usuario quer fazer o check-in no evento selecionado anteriormente. Nela ele precisará informar o seu nome e email para poder continuar.

<img src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/event_confirm_check_in.png" alt="" data-canonical-src="https://github.com/Wottrich/test-android-sicredi/blob/master/archives/event_confirm_check_in.png" width="200" height="400" />

# Testes

### Testes Unitários
_**Coverage Projeto:**_

![Projeto coverage](https://github.com/Wottrich/test-android-sicredi/blob/master/archives/coverage_api_datasourse_viewModel.png)

_**Coverage por camadas:**_

Network - API:

![API coverage](https://github.com/Wottrich/test-android-sicredi/blob/master/archives/coverage_api.png)

Network - DataSouce:

![DataSource coverage](https://github.com/Wottrich/test-android-sicredi/blob/master/archives/coverage_datasource.png)

ViewModel:

![ViewModel coverage](https://github.com/Wottrich/test-android-sicredi/blob/master/archives/coverage_viewmodel.png)

### Testes de Interação

![Projeto interação coverage](https://github.com/Wottrich/test-android-sicredi/blob/master/archives/ui_tests.png)

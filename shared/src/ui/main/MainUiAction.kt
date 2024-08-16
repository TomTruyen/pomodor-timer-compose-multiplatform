package ui.main

sealed class MainUiAction {
    data object StartTimer: MainUiAction()
    data object PauseTimer: MainUiAction()
    data object ResetTimer: MainUiAction()
}

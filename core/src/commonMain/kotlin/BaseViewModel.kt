package core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<UIState, UIAction>(
    initialState: UIState
): ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()
    
    protected fun setState(block: UIState.() -> UIState) {
        _uiState.update(block)
    }
    
    abstract fun onAction(action: UIAction)
}
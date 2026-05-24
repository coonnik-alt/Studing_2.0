package UI.Characters

import Data.CharactersRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class ViewModel( private val repository : CharactersRepository) : ViewModel() {

    val characters = repository.getCharacters()
        .cachedIn(viewModelScope)
}
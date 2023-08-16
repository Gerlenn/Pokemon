package app.pokemon.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pokemon.domain.PokemonInteractor
import app.pokemon.presentation.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonInteractor: PokemonInteractor,
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _selectedPokemonName = MutableLiveData<Int>()
    val selectedPokemonName: LiveData<Int> = _selectedPokemonName

    private val currentPage = MutableLiveData<Int>()
    private val itemsPerPage = 20

    init {
        currentPage.value = 0
    }

    fun loadNextPage() {
        val nextPage = currentPage.value?.plus(1) ?: 1
        viewModelScope.launch {
            val listPokemons = pokemonInteractor.getPokemonList(nextPage - 1, itemsPerPage)
            _pokemonList.value = listPokemons
            currentPage.value = nextPage
        }
    }

    fun setSelectedPokemonName(pokemonId: Int) {
        _selectedPokemonName.value = pokemonId
    }
}
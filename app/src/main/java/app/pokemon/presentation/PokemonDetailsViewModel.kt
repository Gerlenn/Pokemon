package app.pokemon.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pokemon.R
import app.pokemon.domain.PokemonInteractor
import app.pokemon.presentation.model.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonInteractor: PokemonInteractor,
) : ViewModel() {

    private val _pokemonDetails = MutableLiveData<PokemonDetails>()
    val pokemonDetails: LiveData<PokemonDetails> = _pokemonDetails

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    fun getPokemonDetails(pokemonId: Int) {
        viewModelScope.launch {
            val detailsFromDb = pokemonInteractor.getPokemonDetailsFromDatabase(pokemonId)
            val detailsToSet = when {
                detailsFromDb != null -> detailsFromDb
                else -> pokemonInteractor.getPokemonDetails(pokemonId)
            }
            if (detailsToSet != null) {
                _pokemonDetails.value = detailsToSet
            } else {
                _errorMessage.value = R.string.details_not_found
            }
        }
    }
}
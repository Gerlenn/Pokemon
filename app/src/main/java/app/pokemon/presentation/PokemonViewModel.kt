package app.pokemon.presentation

import androidx.lifecycle.ViewModel
import app.pokemon.domain.PokemonInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonInteractor: PokemonInteractor,
) : ViewModel() {

}
package app.pokemon.domain

import javax.inject.Inject

class PokemonInteractor@Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {

}
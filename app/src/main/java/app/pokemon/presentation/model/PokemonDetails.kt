package app.pokemon.presentation.model

data class PokemonDetails(
    val id: Int,
    val name: String,
    val weight: Double,
    val height: Int,
    val types: String,
    val imageUrl: String
)

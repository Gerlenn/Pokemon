package app.pokemon.presentation.model

data class PokemonDetails(
    val name: String,
    val image: String,
    val types: List<String>,
    val weight: Double,
    val height: Double,
)

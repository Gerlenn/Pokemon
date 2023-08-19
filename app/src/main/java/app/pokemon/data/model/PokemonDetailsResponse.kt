package app.pokemon.data.model

data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val weight: Double,
    val height: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites,
)

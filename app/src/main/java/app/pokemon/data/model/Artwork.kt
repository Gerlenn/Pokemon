package app.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class Artwork(
    @SerializedName("official-artwork")
    val official_artwork: PokemonImage,
)

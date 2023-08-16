package app.pokemon.data.service

import app.pokemon.data.model.PokemonDetailsResponse
import app.pokemon.data.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("offset") offset: Int, @Query("limit") limit: Int): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): Response<PokemonDetailsResponse>
}
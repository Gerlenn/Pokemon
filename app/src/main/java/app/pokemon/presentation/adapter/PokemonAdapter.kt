package app.pokemon.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.pokemon.databinding.PokemonItemBinding
import app.pokemon.presentation.adapter.listener.PokemonListener
import app.pokemon.presentation.model.Pokemon

class PokemonAdapter(
    private var pokemonListener: PokemonListener,
) : RecyclerView.Adapter<PokemonViewHolder>() {

    private var pokemonList = mutableListOf<Pokemon>()

    fun submitList(listPokemon: List<Pokemon>) {
        val currentSize = pokemonList.size
        pokemonList.addAll(listPokemon)
        notifyItemRangeInserted(currentSize, listPokemon.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val viewBinding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(viewBinding, pokemonListener)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
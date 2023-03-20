package com.example.pokemonandroidapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GridViewPokemonsAdapter extends BaseAdapter {
    private Context context;
    private List<Pokemon> pokemons;

    public GridViewPokemonsAdapter(Context context, List<Pokemon> pokemons) {
        this.context = context;
        this.pokemons = pokemons;
    }

    @Override
    public int getCount() {
        return pokemons.size();
    }

    @Override
    public Object getItem(int i) {
        return pokemons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.grid_item_pokemon, null);
        }
        ImageView imageView = view.findViewById(R.id.imageViewPokemon);
        TextView textView = view.findViewById(R.id.textViewPokemon);
        Pokemon pokemon = pokemons.get(i);
        LinearLayout linearLayout = view.findViewById(R.id.gridItemPokemon);
        linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, PokemonActivity.class);
            intent.putExtra("pokemon", pokemon);
            context.startActivity(intent);
        });
        Glide.with(imageView.getContext())
                .load(pokemon.getImageUrl())
                .into(imageView);
        textView.setText(pokemon.getName());
        return view;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}

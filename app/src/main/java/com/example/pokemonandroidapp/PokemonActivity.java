package com.example.pokemonandroidapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");
        ImageView imageView = findViewById(R.id.pokemonActivityImageView);
        Glide.with(imageView.getContext())
                .load(pokemon.getImageUrl())
                .into(imageView);
        ((TextView) findViewById(R.id.pokemonActivityTextViewName)).setText(pokemon.getName());
        ((TextView) findViewById(R.id.pokemonActivityTextViewWeight)).setText(pokemon.getWeight() + " KG");
        ((TextView) findViewById(R.id.pokemonActivityTextViewHeight)).setText(pokemon.getHeight() + " M");
        ((ProgressBar) findViewById(R.id.progressBarHp)).setProgress(pokemon.getHp());
        ((ProgressBar) findViewById(R.id.progressBarAttack)).setProgress(pokemon.getAttack());
        ((ProgressBar) findViewById(R.id.progressBarDefense)).setProgress(pokemon.getDefense());
        ((ProgressBar) findViewById(R.id.progressBarSpeed)).setProgress(pokemon.getSpeed());
        ((ProgressBar) findViewById(R.id.progressBarExperience)).setProgress(pokemon.getExperience());
    }
}
package com.example.pokemonandroidapp;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private GridViewPokemonsAdapter pokemonsAdapter;
    private String previousUrl;
    private String nextUrl;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        requestQueue = Volley.newRequestQueue(this);
        //
        findViewById(R.id.buttonPrevious).setOnClickListener(view -> {
            if (Objects.equals(previousUrl, "null")) {
                Toast.makeText(this, getResources().getString(R.string.nothing_to_show), Toast.LENGTH_SHORT).show();
                return;
            }
            fillPokemonsAdapterFromAPI(previousUrl);
        });
        findViewById(R.id.buttonNext).setOnClickListener(view -> {
            if (Objects.equals(nextUrl, "null")) {
                Toast.makeText(this, getResources().getString(R.string.nothing_to_show), Toast.LENGTH_SHORT).show();
                return;
            }
            fillPokemonsAdapterFromAPI(nextUrl);
        });
        GridView gridView = findViewById(R.id.gridViewPokemons);
        pokemonsAdapter = new GridViewPokemonsAdapter(this, new ArrayList<>());
        gridView.setAdapter(pokemonsAdapter);
        nextUrl = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=10";
        fillPokemonsAdapterFromAPI(nextUrl);
    }

    private void fillPokemonsAdapterFromAPI(String sourcedUrl) {
        requestQueue.add(new StringRequest(Request.Method.GET, sourcedUrl, responsePokemons -> {
            try {
                JSONObject jsonResponsePokemons = new JSONObject(responsePokemons);
                previousUrl = jsonResponsePokemons.getString("previous");
                nextUrl = jsonResponsePokemons.getString("next");
                JSONArray pokemonJSONArray = jsonResponsePokemons.getJSONArray("results");
                pokemonsAdapter.getPokemons().clear();
                for (int i = 0; i < pokemonJSONArray.length(); i++) {
                    JSONObject pokemonObject = pokemonJSONArray.getJSONObject(i);
                    String name = pokemonObject.getString("name");
                    String url = pokemonObject.getString("url");

                    requestQueue.add(new StringRequest(Request.Method.GET, url, responsePokemon -> {
                        try {
                            JSONObject jsonResponsePokemon = new JSONObject(responsePokemon);
                            String imageUrl = jsonResponsePokemon
                                    .getJSONObject("sprites")
                                    .getJSONObject("other")
                                    .getJSONObject("official-artwork")
                                    .getString("front_default");

                            float weight = Float.parseFloat(jsonResponsePokemon.getString("weight"));
                            float height = Float.parseFloat(jsonResponsePokemon.getString("height"));
                            int experience = Integer.parseInt(jsonResponsePokemon.getString("base_experience"));
                            JSONArray statsJSONArray = jsonResponsePokemon.getJSONArray("stats");
                            int hp = 0;
                            int attack = 0;
                            int defense = 0;
                            int speed = 0;
                            for (int j = 0; j < statsJSONArray.length(); j++) {
                                JSONObject statObject = statsJSONArray.getJSONObject(j);
                                switch (statObject.getJSONObject("stat").getString("name")) {
                                    case "hp":
                                        hp = Integer.parseInt(statObject.getString("base_stat"));
                                        break;
                                    case "attack":
                                        attack = Integer.parseInt(statObject.getString("base_stat"));
                                        break;
                                    case "defense":
                                        defense = Integer.parseInt(statObject.getString("base_stat"));
                                        break;
                                    case "speed":
                                        speed = Integer.parseInt(statObject.getString("base_stat"));
                                        break;
                                }
                            }
                            pokemonsAdapter.getPokemons().add(new Pokemon(name, imageUrl, weight, height, hp, attack, defense, speed, experience));
                            pokemonsAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, error -> Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()));
    }
}
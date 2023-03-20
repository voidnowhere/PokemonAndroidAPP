package com.example.pokemonandroidapp;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private GridViewPokemonsAdapter pokemonsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        GridView gridView = findViewById(R.id.gridViewPokemons);
        pokemonsAdapter = new GridViewPokemonsAdapter(this, new ArrayList<>());
        gridView.setAdapter(pokemonsAdapter);
        fillPokemonsAdapterFromAPI();
    }

    private void fillPokemonsAdapterFromAPI() {
        new Thread(() -> {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://pokeapi.co/api/v2/pokemon/?limit=25")
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                String responseData = response.body().string();

                JSONObject jsonResponse = new JSONObject(responseData);
                JSONArray pokemonJSONArray = jsonResponse.getJSONArray("results");

                for (int i = 0; i < pokemonJSONArray.length(); i++) {
                    JSONObject pokemonObject = pokemonJSONArray.getJSONObject(i);
                    String name = pokemonObject.getString("name");
                    String url = pokemonObject.getString("url");

                    request = new Request.Builder().url(url).build();
                    response = httpClient.newCall(request).execute();
                    responseData = response.body().string();

                    jsonResponse = new JSONObject(responseData);
                    String imageUrl = jsonResponse
                            .getJSONObject("sprites")
                            .getJSONObject("other")
                            .getJSONObject("official-artwork")
                            .getString("front_default");

                    float weight = Float.parseFloat(jsonResponse.getString("weight"));
                    float height = Float.parseFloat(jsonResponse.getString("height"));
                    int experience = Integer.parseInt(jsonResponse.getString("base_experience"));
                    JSONArray statsJSONArray = jsonResponse.getJSONArray("stats");
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
                    runOnUiThread(() -> {
                        pokemonsAdapter.notifyDataSetChanged();
                    });
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
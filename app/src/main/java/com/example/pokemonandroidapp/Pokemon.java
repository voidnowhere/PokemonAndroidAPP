package com.example.pokemonandroidapp;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private String name;
    private String imageUrl;
    private float weight;
    private float height;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private int experience;

    public Pokemon(String name, String imageUrl, float weight, float height, int hp, int attack, int defense, int speed, int experience) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.height = height;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getExperience() {
        return experience;
    }
}

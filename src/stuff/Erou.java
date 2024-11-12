package stuff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public class Erou {
    private int mana;
    private int health;
    private String description;
    private String colors[];
    private String name;
    @JsonIgnore
    private int ability;//1-4 fiecare abilitate pe rand
    public void setAbility(Erou hero){
        if(hero.name.equals("Lord Royce"))
            hero.ability = 1;
        else if(hero.name.equals("Empress Thorina"))
            hero.ability = 2;
        else if(hero.name.equals("King Mudface"))
            hero.ability = 3;
        else if(hero.name.equals("General Kocioraw"))
            hero.ability = 4;
    }
    public Erou(CardInput hero){
        this.health = 30;
        this.mana = hero.getMana();
        this.description = hero.getDescription();
        this.colors = hero.getColors().toArray(new String[0]);
        this.name = hero.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public int getAbility() {
        return ability;
    }

    public String[] getColors() {
        return colors;
    }

    public String getDescription() {
        return description;
    }

    public int getMana() {
        return mana;
    }

    public int getHealth() {
        return health;
    }
}

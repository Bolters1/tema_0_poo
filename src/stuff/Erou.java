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
    @JsonIgnore
    private int attacked_this_round = 0;
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

    public void setAttacked_this_round(int attacked_this_round) {
        this.attacked_this_round = attacked_this_round;
    }

    public int getAttacked_this_round() {
        return attacked_this_round;
    }

    public int useHeroAbility(Erou hero, Player attacker, Player attacked, int row, GameState game){
        abilities ability = new abilities();
        if(hero.getMana() > attacker.getMana())
            return 1; // not enough mana
        else if(hero.getAttacked_this_round() == 1)
            return 2; // already attacked
        else if(hero.getAbility() == 1 || hero.getAbility() == 2){
            if ((game.getPlayerTurn() == 2 && (row == 0 || row == 1)) || (game.getPlayerTurn() == 1 && (row == 2 || row == 3)))
                return 3; // eroare 3 Attacked card does not belong to the enemy
        }
        else if(hero.getAbility() == 3 || hero.getAbility() == 4){
            if ((game.getPlayerTurn() == 2 && (row == 2 || row == 3)) || (game.getPlayerTurn() == 1 && (row == 0 || row == 1)))
                return 4; // eroare 5 Attacked card does not belong to the ally
        }
        if(hero.getAbility() == 1){
            if(row == 0 || row == 3)
                ability.Sub_Zero(attacked.getBackRow());
            else
                ability.Sub_Zero(attacked.getFrontRow());
            attacker.setMana(attacker.getMana() - hero.getMana());
            hero.attacked_this_round = 1;
        }
        else if(hero.getAbility() == 2){
            if(row == 0 || row == 3)
                ability.Low_Blow(attacked.getBackRow(), 0, attacked);
            else
                ability.Low_Blow(attacked.getFrontRow(), 1, attacked);
            attacker.setMana(attacker.getMana() - hero.getMana());
            hero.attacked_this_round = 1;
        }
        else if(hero.getAbility() == 3){
            if(row == 0 || row == 3)
               ability.earth_born(attacker.getBackRow());
            else
                ability.earth_born(attacker.getFrontRow());
            attacker.setMana(attacker.getMana() - hero.getMana());
            hero.attacked_this_round = 1;
        }
        else if(hero.getAbility() == 4){
            if(row == 0 || row == 3)
                ability.blood_thirst(attacker.getBackRow());
            else
                ability.blood_thirst(attacker.getFrontRow());
            attacker.setMana(attacker.getMana() - hero.getMana());
            hero.attacked_this_round = 1;
        }
        return 0;
    }
}

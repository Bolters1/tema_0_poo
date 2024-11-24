package stuff;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Minion {
    private int mana;
    private int health;
    private int attackDamage;
    private String description;
    private String[] colors;
    private String name;
    @JsonIgnore
    private int attacked_this_turn = 0; //0 = poate ataca, 1=nu poate ataca
    @JsonIgnore
    private int position; // 1 pt randul din fata 0 pt randul din spate
    @JsonIgnore
    private int ability; // 0 fata abilitati si 1-4 abilitatile pe rand
    @JsonIgnore
    private int frozen; // 0 == unfrozen si 1 == frozen
    @JsonIgnore
    private int is_tank = 0;
    public void assign_position(Minion minion){
        if(minion.name.equals("Sentinel") || minion.name.equals("Berserker"))
            minion.position = 0;
        else if(minion.name.equals("Goliath") || minion.name.equals("Warden")) {
            minion.position = 1;
            is_tank = 1;
        }
        else if(minion.name.equals("Miraj")){
            minion.position = 1;
            minion.ability = 1;
        }
        else if(minion.name.equals("The Ripper")){
            minion.position = 1;
            minion.ability = 2;
        }
        else if(minion.name.equals("The Cursed One")){
            minion.position = 0;
            minion.ability = 3;
        }
        else if(minion.name.equals("Disciple")){
            minion.position = 0;
            minion.ability = 4;
        }
    }

    public int getMana() {
        return mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public String getDescription() {
        return description;
    }

    public String[] getColors() {
        return colors;
    }

    public String getName() {
        return name;
    }

    public int getAbility() {
        return ability;
    }

    public int getPosition() {
        return position;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }

    public int getFrozen() {
        return frozen;
    }

    public void setAttacked_this_turn(int attacked_this_turn) {
        this.attacked_this_turn = attacked_this_turn;
    }

    public int getAttacked_this_turn() {
        return attacked_this_turn;
    }

    public int getIs_tank() {
        return is_tank;
    }
    public int check_is_tank(ArrayList<Minion> row){
        for(int i = 0; i < row.size(); i++){
            if(row.get(i).is_tank == 1)
                return 1;
        }
        return 0;
    }
}

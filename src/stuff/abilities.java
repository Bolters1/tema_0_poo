package stuff;


import java.awt.*;
import java.util.ArrayList;

public class abilities {
    public int useAbility(Minion attacker,Minion attacked, Player attacked_player, int x, int y, GameState game) {
        if (attacker.getAbility() == 1 || attacker.getAbility() == 2 || attacker.getAbility() == 3) {
            if ((game.getPlayerTurn() == 2 && (x == 0 || x == 1)) || (game.getPlayerTurn() == 1 && (x == 2 || x == 3)))
                return 1; // eroare 1 Attacked card does not belong to the enemy
            else if (attacker.getAttacked_this_turn() == 1)
                return 2; //eroare 2 Attacker card has already used their ability
            else if (attacker.getFrozen() == 1)
                return 3; //eroare 3 Attacker card is frozen
            else if (attacked_player.getNrOfCardsFront() > 0 && attacked.getPosition() == 0 && attacked.check_is_tank(attacked_player.getFrontRow()) == 1) {
                return 4; //eroare 4 Attacked card is not of type 'Tank’.
            }
            else if(attacked_player.getNrOfCardsFront() > 0 && attacked.getPosition() == 1 && attacked.check_is_tank(attacked_player.getFrontRow()) == 1 && attacked.getIs_tank() == 0)
                return 4;
        }
        else if(attacker.getAbility() == 4){
            if ((game.getPlayerTurn() == 2 && (x == 2 || x == 3)) || (game.getPlayerTurn() == 1 && (x == 0 || x == 1)))
                return 5; // eroare 5 Attacked card does not belong to the ally
        }
        if(attacker.getAbility() == 1){
            Skyjack(attacker, attacked);
            attacker.setAttacked_this_turn(1);
        }
        else if(attacker.getAbility() == 2){
            Weak_Knees(attacked);
            attacker.setAttacked_this_turn(1);
        }
        else if(attacker.getAbility() == 3){
            if(attacked.getAttackDamage() == 0){
                if(attacked.getPosition() == 0) {
                    attacked_player.getBackRow().remove(y);
                    attacked_player.setNrOfCardsBack(attacked_player.getNrOfCardsBack() - 1);
                }
                else if(attacked.getPosition() == 1) {
                    attacked_player.getFrontRow().remove(y);
                    attacked_player.setNrOfCardsFront(attacked_player.getNrOfCardsFront() - 1);
                }
            }
            else
                Shapeshift(attacked);
            attacker.setAttacked_this_turn(1);
        }
        else if(attacker.getAbility() == 4){
            Gods_plan(attacked);
            attacker.setAttacked_this_turn(1);
        }
        return 0;
    }
    void Weak_Knees(Minion minion){
        if(minion.getAttackDamage() - 2 > 0)
            minion.setAttackDamage(minion.getAttackDamage() - 2);
        else
            minion.setAttackDamage(0);
    }
    void Skyjack(Minion Miraj, Minion opponent){
        int aux = Miraj.getHealth();
        Miraj.setHealth(opponent.getHealth());
        opponent.setHealth(aux);
    }
    void Shapeshift(Minion minion){
        int aux = minion.getHealth();
        minion.setHealth(minion.getAttackDamage());
        minion.setAttackDamage(aux);
    }
    void Gods_plan(Minion minion){
        minion.setHealth(minion.getHealth() + 2);
    }
    void Sub_Zero(ArrayList<Minion> row){
        for(int i = 0; i < row.size(); i++)
            row.get(i).setFrozen(1);
    }
    void Low_Blow(ArrayList<Minion> row, int type_of_row, Player attacked){
        int maxHp = 0, maxIndex = 0;
        for(int i = 0; i < row.size(); i++) {
            if (row.get(i).getHealth() > maxHp) {
                maxHp = row.get(i).getHealth();
                maxIndex = i;
            }
        }
        if(row.size() != 0) {
            row.remove(maxIndex);
            if (type_of_row == 0)
                attacked.setNrOfCardsBack(attacked.getNrOfCardsBack() - 1);
            else
                attacked.setNrOfCardsFront(attacked.getNrOfCardsFront() - 1);
        }
    }
    void earth_born(ArrayList<Minion> row){
        for(int i = 0; i < row.size(); i++)
            row.get(i).setHealth(row.get(i).getHealth() + 1);
    }
    void blood_thirst(ArrayList<Minion> row){
        for(int i = 0; i < row.size(); i++)
            row.get(i).setAttackDamage(row.get(i).getAttackDamage() + 1);
    }
}

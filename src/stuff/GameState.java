package stuff;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameState {
    private int playerTurn;
    private int overallTurn;
    private int mana_to_receive = 1;
    //private int gamesPlayed;
    public GameState(int playerTurn){
        this.playerTurn = playerTurn;
        overallTurn = 1;
    }

    public int getOverallTurn() {
        return overallTurn;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setOverallTurn(int overallTurn) {
        this.overallTurn = overallTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int getMana_to_receive() {
        return mana_to_receive;
    }

    public void setMana_to_receive(int mana_to_receive) {
        if(mana_to_receive >= 10)
            this.mana_to_receive = 10;
        else
            this.mana_to_receive = mana_to_receive;
    }
    public int Attack(Minion attacker, Minion attacked, Player attacked_player, int x, int y, GameState game){
        // x = x attacked y = y attacked
        if((game.getPlayerTurn() == 2 && (x == 0 || x == 1)) || (game.getPlayerTurn() == 1 && (x == 2 || x== 3)))
            return 1; // eroare 1 Attacked card does not belong to the enemy
        else if(attacker.getAttacked_this_turn() == 1)
            return 2; //eroare 2 Attacker card has already attacked this turn.
        else if(attacker.getFrozen() == 1)
            return 3; //eroare 3 Attacker card is frozen
        else if(attacked_player.getNrOfCardsFront() > 0 && attacked.getPosition() == 1 &&  attacked.check_is_tank(attacked_player.getFrontRow()) == 1 && attacked.getIs_tank() == 0){
            return 4; //eroare 4 Attacked card is not of type 'Tank’.
        }
        attacked.setHealth(attacked.getHealth() - attacker.getAttackDamage());
        attacker.setAttacked_this_turn(1);
        if(attacked.getHealth() <= 0){
            if(attacked.getPosition() == 0) {
                attacked_player.getBackRow().remove(y);
                attacked_player.setNrOfCardsBack(attacked_player.getNrOfCardsBack() - 1);
            }
            else if(attacked.getPosition() == 1) {
                attacked_player.getFrontRow().remove(y);
                attacked_player.setNrOfCardsFront(attacked_player.getNrOfCardsFront() - 1);
            }
        }
        return 0;
    }
    public void resetAttack(Player player1, Player player2){
        for(int i = 0; i < player1.getNrOfCardsFront(); i++){
            player1.getFrontRow().get(i).setAttacked_this_turn(0);
        }
        for(int i = 0; i < player1.getNrOfCardsBack(); i++){
            player1.getBackRow().get(i).setAttacked_this_turn(0);
        }
        for(int i = 0; i < player2.getNrOfCardsFront(); i++){
            player2.getFrontRow().get(i).setAttacked_this_turn(0);
        }
        for(int i = 0; i < player2.getNrOfCardsBack(); i++){
            player2.getBackRow().get(i).setAttacked_this_turn(0);
        }
    }
    public void resetFrozen_1(Player player1){
        for(int i = 0; i < player1.getFrontRow().size(); i++){
            player1.getFrontRow().get(i).setFrozen(0);
        }
        for(int i = 0; i < player1.getBackRow().size(); i++){
            player1.getBackRow().get(i).setFrozen(0);
        }
    }
    public void resetFrozen_2( Player player2){
        for(int i = 0; i < player2.getFrontRow().size(); i++){
            player2.getFrontRow().get(i).setFrozen(0);
        }
        for(int i = 0; i < player2.getBackRow().size(); i++){
            player2.getBackRow().get(i).setFrozen(0);
        }
    }
    public Minion getCardAtThisPosition(Player player1, Player player2, int x, int y){
        if(x == 0){
            if(y >= 0 && player2.getNrOfCardsBack() > y)
                return player2.getBackRow().get(y);
            else
                return null;
        }
        else if(x == 1){
            if(y >= 0 && player2.getNrOfCardsFront() > y)
                return player2.getFrontRow().get(y);
            else
                return null;
        }
        else if(x == 3){
            if(y >= 0 && player1.getNrOfCardsBack() > y)
                return player1.getBackRow().get(y);
            else
                return null;
        }
        else if(x == 2){
            if(y >= 0 && player1.getNrOfCardsFront() > y)
                return player1.getFrontRow().get(y);
            else
                return null;
        }
        else
            return null;
    }
    public int attackHero(Minion attacker, Erou hero, Player attacked_player){
        if(attacker.getAttacked_this_turn() == 1)
            return 2; //eroare 2 Attacker card has already attacked this turn.
        else if(attacker.getFrozen() == 1)
            return 3; //eroare 3 Attacker card is frozen
        else if(attacked_player.getNrOfCardsFront() > 0 &&  attacker.check_is_tank(attacked_player.getFrontRow()) == 1){
            return 4; //eroare 4 Attacked card is not of type 'Tank’.
        }
        hero.setHealth(hero.getHealth() - attacker.getAttackDamage());
        attacker.setAttacked_this_turn(1);
        return 0;
    }
    public void shuffle(int seed, ArrayList<Minion> deck){
        Collections.shuffle(deck, new Random(seed));
    }

}

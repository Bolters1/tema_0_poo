package stuff;

import java.util.ArrayList;

public class Player {
    int wins;
    private final ArrayList<Minion> backRow = new ArrayList<Minion>();
    private final ArrayList<Minion> frontRow = new ArrayList<Minion>();
    private int nrOfCardsFront = 0;
    private int nrOfCardsBack = 0;
    private int NrOfCardsInHand = 0;
    private final ArrayList<Minion> CardsInHand = new ArrayList<Minion>();
    private int player_turn_check = 0;
    private int mana;

    public ArrayList<Minion> getBackRow() {
        return backRow;
    }

    public Player(Minion card, Deck deck) {
        NrOfCardsInHand = 1;
        CardsInHand.add(card);
        deck.getDeck().remove(0);
        deck.setNrOfCardsInDeck(deck.getNrOfCardsInDeck() - 1);
        mana = 1;
    }


    public ArrayList<Minion> getFrontRow() {
        return frontRow;
    }

    public void addToBackRow(ArrayList<Minion> backRow, Minion card) {
        backRow.add(card);
        nrOfCardsBack++;
        NrOfCardsInHand--;
        mana = mana - card.getMana();
    }

    public void addToFrontRow(ArrayList<Minion> frontRow, Minion card) {
        frontRow.add(card);
        nrOfCardsFront++;
        NrOfCardsInHand--;
        mana = mana - card.getMana();
    }

    public void addCardToHand(Minion card, Deck deck) {
        CardsInHand.add(card);
        deck.getDeck().remove(0);
        NrOfCardsInHand++;
        deck.setNrOfCardsInDeck(deck.getNrOfCardsInDeck() - 1);
    }

    public void removeCard(ArrayList<Minion> hand, int i) {
        hand.remove(i);
        NrOfCardsInHand--;
    }

    public Minion getCardFromHand(int index) {
        return CardsInHand.get(index);
    }
    public Minion getCardFromCoordonates(Player player1, Player player2, int x, int y){
        if(x == 0){
            return player2.getBackRow().get(y);
        }
        else if(x == 1)
            return player2.getFrontRow().get(y);
        else if(x == 2)
            return player1.getFrontRow().get(y);
        else {
            return player1.getBackRow().get(y);
        }
    }

    public int getNrOfCardsBack() {
        return nrOfCardsBack;
    }

    public int getNrOfCardsFront() {
        return nrOfCardsFront;
    }

    public ArrayList<Minion> getCardsInHand() {
        return CardsInHand;
    }

    public void setPlayer_turn_check(int player_turn_check) {
        this.player_turn_check = player_turn_check;
    }

    public int getPlayer_turn_check() {
        return player_turn_check;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getNrOfCardsInHand() {
        return NrOfCardsInHand;
    }

    public void setNrOfCardsBack(int nrOfCardsBack) {
        this.nrOfCardsBack = nrOfCardsBack;
    }

    public void setNrOfCardsFront(int nrOfCardsFront) {
        this.nrOfCardsFront = nrOfCardsFront;
    }

    public int getWins() {
        return wins;
    }
    public ArrayList<Minion> getFrozenCards(ArrayList<Minion> row){
        ArrayList<Minion> frozen = new ArrayList<Minion>();
        for(int i = 0; i < row.size(); i++){
            if(row.get(0).getFrozen() == 1)
                frozen.add(row.get(i));
        }
        return frozen;
    }
}

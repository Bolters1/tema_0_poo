package stuff;
import fileio.CardInput;
import java.util.ArrayList;
public class Deck {
    private ArrayList<Minion> CardsInDeck = new ArrayList<Minion>();
    private int NrOfCardsInDeck;

    public Deck (ArrayList<CardInput> cards) {
        //Deck deck;
        NrOfCardsInDeck = cards.size();

        for (int i = 0; i < cards.size(); i++) {
            Minion minion = new Minion();
            minion.setHealth(cards.get(i).getHealth());
            minion.setAttackDamage(cards.get(i).getAttackDamage());
            minion.setMana(cards.get(i).getMana());
            minion.setDescription(cards.get(i).getDescription());
            minion.setColors(cards.get(i).getColors().toArray(new String[0]));
            minion.setName(cards.get(i).getName());
            this.CardsInDeck.add(minion);
        }
        //return deck;
    }

    public void setNrOfCardsInDeck(int nrOfCardsInDeck) {
        NrOfCardsInDeck = nrOfCardsInDeck;
    }

    public int getNrOfCardsInDeck() {
        return NrOfCardsInDeck;
    }

    public ArrayList<Minion> getDeck() {
        return CardsInDeck;
    }
}

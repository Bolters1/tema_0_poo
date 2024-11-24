package stuff;

public class GameInfo {
    private int nrOfWins1, NrOfWins2, nrOfGamesPlayed;

    public int getNrOfGamesPlayed() {
        return nrOfGamesPlayed;
    }

    public int getNrOfWins1() {
        return nrOfWins1;
    }

    public void setNrOfGamesPlayed(int nrOfGamesPlayed) {
        this.nrOfGamesPlayed = nrOfGamesPlayed;
    }

    public void setNrOfWins1(int nrOfWins) {
        this.nrOfWins1 = nrOfWins;
    }
    public void gameStart(){
        this.nrOfGamesPlayed++;
    }

    public int getNrOfWins2() {
        return NrOfWins2;
    }

    public void setNrOfWins2(int nrOfWins2) {
        NrOfWins2 = nrOfWins2;
    }
}

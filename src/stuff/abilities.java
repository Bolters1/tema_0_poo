package stuff;


public class abilities {
    void Weak_Knees(Minion minion){
        if(minion.getHealth() - 2 > 0)
            minion.setHealth(minion.getHealth() - 2);
        else
            minion.setHealth(0);
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
    void Sub_Zero(Minion minion[], int n){
        for(int i = 0; i < n; i++)
            minion[i].setFrozen(1);
    }
    //void Low_Blow()
}

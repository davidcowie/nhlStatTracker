import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by David on 12/11/2016.
 */
public class Player  {

    private String name;
    private int totalPoints;
    private int totalGoals;
    private int totalAssists;
    private ArrayList<GameStat> stats; // just have stats basically be an array of 10 items
    private GameStat[] last10;
    private int numStats = 0;
    private Queue<GameStat> last5Queue;

    private double pointsPerGame;

    private boolean active; // essentially in lineup or not
    private boolean statAdded;

    public Player(String name) {
        this.name = name;
        stats = new ArrayList<>();
        totalPoints = 0;
        totalGoals =0;
        totalAssists=0;
        pointsPerGame = 0;
        last10 = new GameStat[10];
        for (int i = 0; i < last10.length; i++) {
            last10[i] = new GameStat(0,0);
        }
        last5Queue = new LinkedList<GameStat>();
        active = true;
        statAdded = false;
    }

    public void addStat(GameStat gs){
        stats.add(gs);
        totalPoints += gs.getPoints();
        totalGoals+= gs.getGoals();
        totalAssists += gs.getAssists();
        numStats++;
        statAdded = true;
        addSomeStat(gs);

        if(last5Queue.size() >=5){
            last5Queue.remove();
        }
        last5Queue.add(gs);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isStatAdded() {
        return statAdded;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getTotalGoals() {
        return totalGoals;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public double getPointsPerGame(){
        return (double)(totalPoints)/numStats;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setTotalGoals(int totalGoals) {
        this.totalGoals = totalGoals;
    }

    public void setTotalAssists(int totalAssists) {
        this.totalAssists = totalAssists;
    }

    public void setLast10(GameStat[] last10) {
        this.last10 = last10;
    }

    public void setNumStats(int numStats) { // same as games played
        this.numStats = numStats;
    }
    public void increaseGame(){
        this.numStats++;
    }

    // made public cause just call this when reading in the last 10 games
    public void addSomeStat(GameStat gs) {
     /*   if(numStats >=10){
            //need to remove last10[0] and shift everything down
            for (int i = 0; i < last10.length - 1; i++) {
                last10[i] = last10[i+1];
            }
            last10[last10.length-1] = gs;
            numStats++;
        }else{
            last10[numStats] = gs;
            numStats++;
        }
*/
        for (int i = 0; i < last10.length - 1; i++) {
            last10[i] = last10[i+1];
        }
        last10[last10.length-1] = gs;
    }

    public String getFileInfo(){
        String words =  name + " " + active + " " + numStats + " " + totalGoals + " "+ totalAssists + " ";
        for (int i = 0; i < last10.length; i++) {
            words += last10[i].getGoals() + " " + last10[i].getAssists() + " ";
        }
        return words;
    }
    public String getLast10GameTotals(){
        int last10G = getLast10GoalTotal();
        int last10A = getLast10AssistTotal();
        return "Goals: " + last10G+ ", Assists: " + last10A + " Points: " + (last10A + last10G);
    }

    public int getLast10PointTotals(){
        return getLast10AssistTotal() + getLast10GoalTotal();
    }

    public int getLast10GoalTotal(){
        int total = 0;
        for (int i = 0; i < last10.length; i++) {
            total += last10[i].getGoals();
        }
        return total;
    }
    public int getLast10AssistTotal(){
        int total = 0;
        for (int i = 0; i < last10.length; i++) {
            total += last10[i].getAssists();
        }
        return total;
    }


    public int getGamesPlayed(){
        return numStats;
    }

    public int getPointTotals(){
       /* int total =0;
        for (int i = 0; i < stats.size(); i++) {
            total += stats.get(i).getPoints();
        }
        return total;*/
        return totalPoints;
    }

    public int getLastFiveGamePoints(){
        int total =0;
        if(stats.size() >5) {
            for (int i = stats.size() - 1; i > stats.size() - 6; i--) {
                total += stats.get(i).getPoints();
            }
        }else{
            for (int i = 0; i < stats.size(); i++) {
                total += stats.get(i).getPoints();
            }
            System.out.println("Not played 5 games");
        }
        return total;
    }
    public int getLastFiveGameGoals(){
        int total =0;
        if(stats.size() >5) {
            for (int i = stats.size() - 1; i > stats.size() - 6; i--) {
                total += stats.get(i).getGoals();
            }
        }else{
            for (int i = 0; i < stats.size(); i++) {
                total += stats.get(i).getGoals();
            }
            System.out.println("Not played 5 games");
        }
        return total;
    }
    public int getLastFiveGameAssists(){
        int total =0;
        if(stats.size() >5) {
            for (int i = stats.size() - 1; i > stats.size() - 6; i--) {
                total += stats.get(i).getAssists();
            }
        }else{
            for (int i = 0; i < stats.size(); i++) {
                total += stats.get(i).getAssists();
            }
            System.out.println("Not played 5 games");
        }
        return total;
    }

    public String toString(){

        return "" + name+ ", total points: " + totalPoints + " , last 5 total: " + getLastFiveGamePoints();
    }
}

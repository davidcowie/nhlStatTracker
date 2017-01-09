import java.io.Serializable;

/**
 * Created by David on 12/11/2016.
 */
public class GameStat implements Serializable {

    private int goals;
    private int assists;
    private int points;

    public GameStat(int g, int a){
        this.goals = g;
        this.assists = a;
        this.points = goals+assists;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public int getPoints() {
        return points;
    }


    public String toString(){
        return "Goals: " + goals +", assists: " + assists + ", Points: " + points;
    }


    // this was a test for the formatting of the output file in main.java
    public static void main(String[] arg){
        String a = "zetterburg";
        String b = "danced";
        String c = "bob";
        String apples1 = "ryan: 9, 3, 0, 3, 0.3333333333333333, 3, 0, 3, 0.3";
        String apples = "zetterberg: 9, 7, 2, 9, 1.0, 7, 2, 9, 0.9";
        System.out.printf("%1$33s Last 10 game stats %n", "|");
        System.out.printf("%1$-10s: %2$3d, %3$3d,%4$3d,%5$3d,%6$.2f,%7$3d,%8$3d,%9$3d,%10$.2f %n", "ryan", 9,3,0,3, 0.3333333333333333,3,0,3,0.3 );
        System.out.printf("%1$-10s: %2$3d, %3$3d,%4$3d,%5$3d,%6$.2f,%7$3d,%8$3d,%9$3d,%10$.2f %n", "zetterberg", 9,7,2,9, 1.0,7,2,9,0.9 );
        System.out.printf("%1$-10s danced %n %hi10s", a);
        System.out.printf("%10s danced","c");
    }
}

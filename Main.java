import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by David on 12/11/2016.
 */
public class Main {

    // maybe a hashmap with the player name and then the class so string and player
    static HashMap<String, Player> players;
    static ArrayList<String> names;
    public static void main(String[] args){
        players = new HashMap<>();
        names = new ArrayList<>();
        //read in the saved players object
        // but for first time will need an init
        /*FileInputStream fis = null;
        ObjectInputStream objectInput = null;
        try {
            fis = new FileInputStream("players.data");
            objectInput = new ObjectInputStream(fis);
            log("did this one");
            Object obj = objectInput.readObject();
            log("did this one2");
            players = (HashMap<String, Player>) obj;
            log("did this one3");
            objectInput.close();
            fis.close();

        }catch (Exception e){e.printStackTrace();
            log("there was an error");
            System.exit(0);
        }
        readInPlayerNames("playerNames.txt");*/

        // could have a scanner ask if have things to add
        // then ask for there name
        // then goals and then assists
        // then create that gamestat and add it to that player
        //then ask if there are anymore stats to add

        readInFile("playerData.txt");

        Scanner scanner = new Scanner(System.in);

        //might want to add if there was a game or if just internal update of stats.
        // in the something to add, if the answer is n then it will give all players a game and 0 stats

        boolean stillAdding = true;
        while(stillAdding){
            log("is there something to add?(y/n)");
            String ans = scanner.next();
            if(ans.equals("n")){
                stillAdding = false;
                for (int i = 0; i < names.size(); i++) {
                    if(players.get(names.get(i)).isActive() && !players.get(names.get(i)).isStatAdded()){
                        players.get(names.get(i)).addStat(new GameStat(0,0));
                    }
                }
                break;
            }
            log("What is the players name?");
            String name = scanner.next();
            log("How many goals?");
            int goals = scanner.nextInt();
            log("How many assists?");
            int assists = scanner.nextInt();
            GameStat gameStat = new GameStat(goals,assists);
            if(!players.containsKey(name)){
                Player player = new Player(name);
                player.addStat(gameStat);
                players.put(name, player);
                names.add(name);
            }else{
                players.get(name).addStat(gameStat);
            }
            log("hi");

        }

     

        while(true){
            log("would you like to activate or inactivate any player?(y/n)?");
            String ans = scanner.next();
            if(ans.equals("n")){
                break;
            }
            log("enter players name");
            String nm = scanner.next();
            log("activate or inactivate?");
            String ac = scanner.next();
            if(ac.equals("activate")) {
                players.get(nm).setActive(true);
            }else{
                players.get(nm).setActive(false);
            }

        }
        while(true){
            log("would you like to get stats for anyone?(y/n)");
            String ans = scanner.next();
            if(ans.equals("n")){
                break;
            }
            log("which player?");
            String p = scanner.next();
            while(!players.containsKey(p)){
                log("there is no player with that name. Please try again");
                p = scanner.next();
            }
            Player pl = players.get(p);
            log("Last 10 game stats: " + pl.getLast10GameTotals());
            log(p +"'s points per game: " + pl.getPointsPerGame());
        }


       /* for (int i = 0; i < names.size(); i++) {
            log("name size:" + names.size());
            log("the name in question : "+ names.get(i));
            log("players.size: :" + players.size());
            log(players.get(names.get(i)).toString());
        }*/

        writeToFile("playerData.txt");
        writeToStatFile();
/*
        FileOutputStream fos= null;
        ObjectOutputStream objectOutput = null;
        try {
            fos = new FileOutputStream("players.data");
            objectOutput = new ObjectOutputStream(fos);
            objectOutput.writeObject(players);
            objectOutput.close();
            fos.close();
            // write the names

        }catch (Exception e){}

        writePlayerNames("playerNames.txt");*/






    }

    public static void readInFile(String filename){
        // file in form: name gamesplayed totalgoals totalassists followed by doublets of the last 10 game stats
        // ex: toews 12 8 7 1 0 0 1 2 2 3 1 0 2 0 0 0 0 0 0 0 0 0 0
        Scanner scan = null;

        try{
            scan = new Scanner(new File(filename));
        }catch (Exception e){return;}

        while(scan.hasNextLine()){
            String line = scan.nextLine();
            Scanner scanner = new Scanner(line);
            String name = scanner.next();
            boolean active = scanner.nextBoolean();
            int gamesPlayed = scanner.nextInt();
            int goalTotal = scanner.nextInt();
            int assistTotal = scanner.nextInt();
            Player player = new Player(name);
            player.setTotalAssists(assistTotal);
            player.setTotalGoals(goalTotal);
            player.setTotalPoints(goalTotal+assistTotal);
            player.setNumStats(gamesPlayed);
            player.setActive(active);
            GameStat[] gameStats = new GameStat[10];
            while(scanner.hasNextInt()){
                int gameGoals = scanner.nextInt();
                int gameAssists = scanner.nextInt();
                GameStat gs = new GameStat(gameGoals,gameAssists);
                player.addSomeStat(gs);
            }
            players.put(name,player);
            names.add(name);
        }
        scan.close();
    }

    public static void writeToFile(String filename){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileOutputStream(filename));
        }catch (Exception e){}

        for (int i = 0; i < names.size(); i++) {
            pw.println(players.get(names.get(i)).getFileInfo());
        }
        pw.close();
    }

    public static void writeToStatFile(){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileOutputStream("playerStats.txt"));
        }catch (Exception e){}

        //pw.println("name: gp, total goals, total assists, points, points per game, last10 G, last10 A, last10P, last10 ppg");
        pw.printf("%1$34s Last 10 games %n", "|");
        pw.printf("%1$-10s: %2$3s|%3$3s|%4$3s|%5$3s|  %6$3s|%7$3s|%8$3s|%9$3s|%10$3s %n", "name", "GP","G","A","PTS","PPG","G","A","P","PPG");
        for (int i = 0; i < names.size(); i++) {
            String nm = names.get(i);
            Player p = players.get(nm);
            String line = names.get(i) + ": " + p.getGamesPlayed() + ", " + p.getTotalGoals() + ", " + p.getTotalAssists() + ", " + p.getTotalPoints() + ", " + p.getPointsPerGame() + ", "
                    + p.getLast10GoalTotal() + ", " + p.getLast10AssistTotal() + ", " + p.getLast10PointTotals() + ", " + (double)( p.getLast10PointTotals()/10.0) ;
            //pw.println(line);
            pw.printf("%1$-10s: %2$3d|%3$3d|%4$3d|%5$3d| %6$.2f|%7$3d|%8$3d|%9$3d| %10$.2f %n", nm,p.getGamesPlayed(),p.getTotalGoals(),p.getTotalAssists(),p.getTotalPoints(),p.getPointsPerGame(),
                    p.getLast10GoalTotal(),p.getLast10AssistTotal(),p.getLast10PointTotals(),p.getLast10PointTotals()/10.0);
        }
        pw.close();
    }

    public static void readInPlayerNames(String filename){
        Scanner scan = null;

        try{
            scan = new Scanner(new File(filename));
        }catch (Exception e){return;}

        while(scan.hasNextLine()){
            names.add(scan.nextLine());
        }
        scan.close();
    }

    public static void writePlayerNames(String filename){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileOutputStream(filename));
        }catch (Exception e){}

        for (int i = 0; i < names.size(); i++) {
            pw.println(names.get(i));
        }
        pw.close();
    }

    public static void log(String s){
        System.out.println(s);
    }
}

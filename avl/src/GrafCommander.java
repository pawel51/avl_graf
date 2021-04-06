import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GrafCommander {
    Graf graf;
    public GrafCommander(Graf graf) {
        this.graf = graf;
    }
    public void readCommands(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String ct;
        while(scanner.hasNext()){
            ct = scanner.next().toUpperCase();
            if(ct.equals("DP"))
                this.insertion(scanner);
            else if(ct.equals("UP"))
                this.deletion(scanner);
            else if(ct.equals("NP"))
                this.fastestPath(scanner);
            else if(ct.equals("ND"))
                this.earliestDate(scanner);
        }
        scanner.close();
    }

    private void earliestDate(Scanner scanner) {
        Date eaDate = graf.firstConnDateLessThanLimit(scanner.nextInt(),
                scanner.nextInt(),
                scanner.nextInt());
        if(eaDate==null)
            System.out.println("NIGDY");
        else
            System.out.println(eaDate.year+"-"+eaDate.month+"-"+eaDate.day);
    }

    private void fastestPath(Scanner scanner){
        int fastestRoute = graf.findFastestRoute(scanner.nextInt(),
                scanner.nextInt(), false);
        if(fastestRoute==graf.INFINITY)
            System.out.println("NIE");
        else
            System.out.println(fastestRoute);
    }

    private void deletion(Scanner scanner) {
        graf.delete(this.createDate(scanner.next()),
                                    scanner.nextInt(),
                                    scanner.nextInt());
    }

    private void insertion(Scanner scanner){
        String data="00-00-0000";
        int id1=0, id2=0, p=1, dl=1;
        if(scanner.hasNext())
            data = scanner.next();
        if(scanner.hasNextInt())
            id1 = scanner.nextInt();
        if(scanner.hasNextInt())
            id2 = scanner.nextInt();
        if(scanner.hasNextInt())
            p = scanner.nextInt();
        if(scanner.hasNextInt())
            dl = scanner.nextInt();
        graf.insert(this.createDate(data), id1, id2, p, dl);
    }

    private Date createDate(String dataStr) {
        char[] data = dataStr.toCharArray();
        char[] rok = new char[4];
        char[] miesiac = new char[2];
        char[] dzien = new char[2];

        System.arraycopy(data, 0, rok, 0, 4);
        System.arraycopy(data, 5, miesiac, 0, 2);
        System.arraycopy(data, 8, dzien, 0, 2);

        String rokstr = new String(rok);
        String miesiacstr = new String(miesiac);
        String daystr = new String(dzien);
        int year = Integer.parseInt(rokstr);
        int month = Integer.parseInt(miesiacstr);
        int day = Integer.parseInt(daystr);

        return new Date(year, month, day);
    }

}

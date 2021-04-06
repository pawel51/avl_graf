import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Commander {
    AvlTree tree;
    Commander(AvlTree t){
        tree = t;
    }

    public void readCommands(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String ct;
        while(scanner.hasNext()){
            ct = scanner.next();
            if(ct.toUpperCase().equals("DP"))
                this.insertion(scanner);
            else if(ct.toUpperCase().equals("WY")){
                try {
                    this.wypisanie();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(ct.toUpperCase().equals("UP"))
                this.usuwanie(scanner);
            else if(ct.toUpperCase().equals("WP"))
                this.wyszukiwanie(scanner);
            else if(ct.toUpperCase().equals("LP"))
                this.znajdzpomiedzy(scanner);
        }

    }

    private void znajdzpomiedzy(Scanner scanner) {
        Klucz k1 = new Klucz(scanner.next(), -1, -1);
        Klucz k2 = new Klucz(scanner.next(), 10001 , 10001);
        System.out.println(tree.findBetween(tree.root, k1.data, k2.data));
    }


    private void wyszukiwanie(Scanner scanner) {
        Klucz k = new Klucz(
                scanner.next(),
                scanner.nextInt(),
                scanner.nextInt()
        );
        if(tree.compareKeys(tree.search(k).klucz, k) == 0)
            System.out.println("TAK ");
        else
            System.out.println("NIE ");

    }

    private void insertion(Scanner scanner){
        String data="00-00-0000";
        int id1=0, id2=0, p=1, dl=0;
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

        tree.insert(new Wezel(data, id1, id2, p, dl));

    }


    private void usuwanie(Scanner scanner) {
        Klucz k = new Klucz(
                scanner.next(),
                scanner.nextInt(),
                scanner.nextInt()
        );
        Wezel w = tree.delete(k);
    }


    public void wypisanie() throws InterruptedException {
        int licznik=0;
        if(tree.root == null){
            //System.out.println("Tree is empty");
            return;
        }
        LinkedBlockingQueue<Wezel> kolejka = new LinkedBlockingQueue<>();
        Wezel temp = tree.root;
        kolejka.put(temp);
        while(!kolejka.isEmpty()){
            temp = kolejka.poll();
            licznik++;
            System.out.println(temp.klucz.data.day+
                    "-"+ temp.klucz.data.month+
                    "-"+ temp.klucz.data.year);
            if(temp.lsyn != null){
                kolejka.put(temp.lsyn);
                System.out.println(temp.lsyn.klucz.data.year);
            }

            else
                System.out.println("nullLEFT");
            if(temp.psyn != null){
                kolejka.put(temp.psyn);
                System.out.println(temp.psyn.klucz.data.year);
            }

            else
                System.out.println("nullRIGHT");
        }
        System.out.println("Liczba elementów drzewa: "+licznik);
    }
}

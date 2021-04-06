import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {


        AvlTree tree = new AvlTree();
        Commander com = new Commander(tree);
       com.readCommands("src\\testyDrzewa\\projekt2_inX.txt");






        Graf g = new Graf();
        GrafCommander gcom = new GrafCommander(g);
        //gcom.readCommands("src\\testyGrafy\\projekt2_in8.txt");












    }
}

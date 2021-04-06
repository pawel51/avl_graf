public class Wezel {
    Wezel lsyn;
    Wezel psyn;
    Wezel ojciec;
    int bf;         //balance factor
    Klucz klucz;
    int lep;        //liczba elementow poddrzewa

    int predkosc;
    int droga;
    Wezel(String dataStr, int id1, int id2, int v, int s ){
        lsyn=null;
        psyn=null;
        ojciec=null;
        bf=0;
        predkosc = v;
        droga = s;
        klucz = new Klucz(dataStr, id1, id2);
        lep = 1;
    }





}

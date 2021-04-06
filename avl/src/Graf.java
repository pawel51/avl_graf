import java.util.*;


public class Graf {
    final int MAX_SIZE = 10001;
    final int INFINITY = 1000000; //minutes
    byte[] v; //węzły 1-10000
    int n;    //liczba węzłów
    ArrayList<Integer>[][] s; //macierz sąsiedztwa 10000-10000
    ArrayList<Date>[][] date; //macierz czasów dodania połączeń
    Date lastFastestRouteDate;
    int lastFastestRoudeId1;
    int lastFastestRoudeId2;

    public Graf() {
        v = new byte[MAX_SIZE];
        s = new ArrayList[MAX_SIZE][MAX_SIZE];
        date = new ArrayList[MAX_SIZE][MAX_SIZE];

        for (int i = 1; i < 10001; i++) {
            v[i] = 0;
            for (int j = 0; j < 10001; j++) {
                s[i][j] = null;
                date[i][j] = null;
            }
        }
    }

    public void insert(Date dat, int id1, int id2, int predkosc, int droga) {
        if (id1 == id2 || id1 < 0 || id1 > 10000 || id2 < 0 || id2 > 10000)     //warunki poprawności działania algorytmu
            return;

        if (v[id1] == 0) {
            n++;
            v[id1] = 1;
        }
        if (v[id2] == 0) {
            n++;
            v[id2] = 1;
        }

        int temp;
        if (id1 > id2) {          //korzystam tylko z połowy tablicy, więc przyjmuję że id2 >= id1
            temp = id1;
            id1 = id2;
            id2 = temp;
        }
        int czas = droga * 60 / predkosc;

        if (s[id1][id2] == null)
            s[id1][id2] = new ArrayList<>();
        s[id1][id2].add(czas);

        if (date[id1][id2] == null)
            date[id1][id2] = new ArrayList<>();
        date[id1][id2].add(dat);


    }

    public void delete(Date dat, int id1, int id2) {
        int pos;
        int temp;
        if (id1 > id2) {          //korzystam tylko z połowy tablicy, więc przyjmuję że id2 >= id1
            temp = id1;
            id1 = id2;
            id2 = temp;
        }

        if (s[id1][id2] == null) {
            //System.out.println("Cant delete nonexisting conn");
            return;
        }

        if (v[id1] == 1 && v[id2] == 1) {
            pos = date[id1][id2].indexOf(dat);
            if (pos == -1) {
                //System.out.println("Cant delete nonexisting conn");
                return;
            }
            date[id1][id2].remove(pos);
            s[id1][id2].remove(pos);
            if (s[id1][id2].isEmpty())
                s[id1][id2] = null;
            if (date[id1][id2].isEmpty())
                date[id1][id2] = null;
        }
        if (s[id1][id2] == null)           //sprawdzenie czy po usunięciu połączenia węzły są wciąż połączne O(n)
            tryToRemove(id1, id2);


    }

    private void tryToRemove(int id1, int id2) {
        boolean id1stillConnected = false;
        boolean id2stillConnected = false;
        for (int i = 0; i < MAX_SIZE; i++)
            if (s[id1][i] != null) {
                id1stillConnected = true;
                break;
            }
        for (int i = 0; i < MAX_SIZE; i++)
            if (s[i][id2] != null) {
                id2stillConnected = true;
                break;
            }
        if (!id1stillConnected) {
            n--;
            v[id1] = 0;
        }
        if (!id2stillConnected) {
            n--;
            v[id2] = 0;
        }
    }

    public int findFastestRoute(int index1, int index2, boolean b) {

        if (v[index1] == 0 || v[index2] == 0) {      //sprawdza czy zadane stacje istnieją
            return INFINITY;
        }

        /*
        d[] - wartości odległości do węzłów, p[] - adresy poprzedników, h[] - kopiec, hp[] - adresy w kopcu
         */
        int[] d, p, h, hp;
        /*
        hlen - wielkość kopca, left - lewy potomek parenta, right - prawy potomek parenta
        dmin - mniejsza wartość z potomków, pmin - adres mniejszego z potomków, u - najmniejsza odległość z nie przetworzonych
        temp - zm. pomocnicza, wagauis - waga trasy z x do y
         */
        int hlen, parent, left, right, dmin, pmin, child, temp, u, waga_u_is;

        byte[] odw;
        d = new int[n];
        p = new int[n];
        h = new int[n];
        hp = new int[n];
        odw = new byte[n];
        for (int i = 0; i < n; i++) {
            d[i] = INFINITY;
            p[i] = -1;
            odw[i] = 0;
            h[i] = hp[i] = i;
        }

        int id1, id2;
        id1 = compressID(index1);   // indeks od 1 - 10000, zapisywany jako któryś z kolei O(n)
        id2 = compressID(index2);

        hlen = n;
        d[id1] = 0;

        temp = h[0];                //zamiana h[0] z h[id1] najmniejsza odległość do samego siebie
        h[0] = h[id1];
        h[id1] = temp;
        hp[id1] = 0;
        hp[0] = id1;

        for (int i = 0; i < n; i++) {
            u = h[0];                   //zapamiętuję najmniejszy element kopca
            // Operacje na kopcu
            // Usunięcie najmniejszego elementu + heapify
            hlen--;
            h[0] = h[hlen];             // W korzeniu umieszczamy ostatni element
            hp[h[0]] = parent = 0;      // Zapamiętujemy pozycję elementu w kopcu
            while (true) {                // W pętli idziemy w dół kopca, przywracając go
                left = parent * 2 + 1;      // poczycja lewego potomka
                right = left + 1;       // pozycja prawego potomka
                if (left >= hlen)        // jeżeli lewy potomek poza kopcem
                    break;
                dmin = d[h[left]];      //wyznaczenie mniejszego z potomków
                pmin = left;
                if ((right < hlen) && (dmin > d[h[right]])) {
                    dmin = d[h[right]];
                    pmin = right;
                }
                if (d[h[parent]] <= dmin)    //jeśli własność kopca jest zachowana
                    break;                  //czyli mniejszy z potomków jest większy od rodzica

                temp = h[parent];
                h[parent] = h[pmin];
                h[pmin] = temp;             //zamiana wartości parenta z mniejszym dzieckiem
                hp[h[parent]] = parent;
                hp[h[pmin]] = pmin;         //zmiana adresu
                parent = pmin;              //przejscie na niższy poziom kopca
            }

            odw[u] = 1;             //najmniejszy wierzchołek oznaczam jako przetworzony

            int gid1 = decompressID(u); //gid1 to id węzła w tablicy n=10000, z dziurami
            //u to id węzła w tablicach skompresowanych d[], odw[]
            int is = 0;              //is to id potencjalnych sąsiadów u
            //Modyfikuję wszystkich sąsiadów u, dijkstra
            for (int gid2 = 0; gid2 < MAX_SIZE && is != n - 1; gid2++) {
                if (this.isConnBetween(gid1, gid2)) {
                    is = compressID(gid2);
                    if (odw[is] == 0) {
                        waga_u_is = waga(gid1, gid2);
                        if (gid2 != index2)
                            waga_u_is += 5;
                        if (d[is] > d[u] + waga_u_is) {
                            d[is] = d[u] + waga_u_is;
                            p[is] = u;

                            // Operacje na kopcu
                            // Po zmianie d[is] odtwarzam wartość kopca idąc w górę
                            for (child = hp[is]; child != 0; child = parent) {
                                parent = child / 2;
                                if (d[h[parent]] <= d[h[child]])
                                    break;
                                temp = h[parent];
                                h[parent] = h[child];
                                h[child] = temp;
                                hp[h[parent]] = parent;
                                hp[h[child]] = child;
                            }
                        }
                    }

                }
            }
        }
        if (b && d[id2] < INFINITY)       //określenie daty powstania połączenia tylko w przypadku gdy metoda wywoływana z komendy 'ND' i znaleziono jakieś połączenie
            findLastFastestRouteDate(p, id1, id2);
        return d[id2];
    }



    private boolean isConnBetween(int gid1, int gid2) {
        return s[gid1][gid2] != null || s[gid2][gid1] != null;  // jeżeli istnieje połączenie zwraca true
    }

    // Zwraca najmniejszą wagę połączenia pomiędzy id1 i id2
    private int waga(int id1, int id2) {
        int temp;
        if (id1 > id2) {          //korzystam tylko z połowy tablicy, więc przyjmuję że id2 >= id1
            temp = id1;
            id1 = id2;
            id2 = temp;
        }


        Integer min = s[id1][id2].get(0);
        for (Integer i : s[id1][id2]) {
            if (i.compareTo(min) < 0)
                min = i;
        }
        return min;
    }

    // Zwraca id k-tego węzła
    private int compressID(int gid) {
        int i = 0, j = 0;
        while (i <= gid) {
            if (v[i] == 1)
                j++;
            i++;
        }
        return j - 1;
    }

    //Zwraca którym z kolei jest węzeł o idn
    private int decompressID(int u) {
        int i = -1, j = 0;
        while (j != u + 1) {
            i++;
            if (v[i] == 1)
                j++;
        }
        return i;
    }







    private void findLastFastestRouteDate(int[] p, int id1, int id2) {
        int dcp, dcid2, temp;
        int minConnLength;      // najszybszy czas połączenia ( bezpośredni )
        Date lastDate = new Date(0, 0, 0);  // data utworzenia trasy ( dijkstra )
        int index;
        int minid1 = 0;
        int minid2 = 0;
        while (true) {
            dcp = decompressID(p[id2]);     //odczytuję ostanie id i zamieniam na id globalnę by móc stosować do tablicy s[][]
            dcid2 = decompressID(id2);
            if(dcp > dcid2){                //sprawdzenie czy dcid2 jest większe od dcp
                temp = dcp;
                dcp = dcid2;
                dcid2 = temp;
            }
            minConnLength = waga(dcp, dcid2);                       // odczytuję wagę połączenia
            index = s[ dcp ][ dcid2 ].indexOf(minConnLength);       // jeżeli istnieje więcej bezpośrednich połączeń między stacjami to biorę te najszybsze
            if (Date.compare(date[ dcp ][ dcid2 ].get(index), lastDate) > 0) {  // jeżeli data dodania połączenia jest większa od daty utworzenia trasy między stacjami
                lastDate = date[ dcp ][ dcid2 ].get(index);                     // to uaktualniam datę utworzenia trasy
                minid1 = dcp;                                                   // oraz zapamiętuję pomiędzy którymi stacjami
                minid2 = dcid2;
            }
            id2 = p[id2];
            if (p[id2] == -1)       //p[id1] jest równe -1 wszystkie połączenia na trasie zostały sprawdzone
                break;
        }
        lastFastestRouteDate = lastDate;        // uaktualniane po każdym wywołaniu
        lastFastestRoudeId1 = minid1;           // alternatywnie można zwracać obiekt typu ConnectionNode
        lastFastestRoudeId2 = minid2;
    }
    public Date firstConnDateLessThanLimit(int id1, int id2, int limit) {
        int fastestRoute = findFastestRoute(id1, id2, true);
        Date lfrd = lastFastestRouteDate;
        int lfrid1; //= lastFastestRouteId1 (cost of travel to id);
        int lfrid2; //= lastFastestRouteId2;

        while (fastestRoute < limit) {
            lfrd = lastFastestRouteDate;
            lfrid1 = lastFastestRoudeId1;
            lfrid2 = lastFastestRoudeId2;
            this.delete(lfrd, lfrid1, lfrid2);
            fastestRoute = findFastestRoute(id1, id2, true);              //znalezienie obecnie najszybszego połączenia
        }
        return lfrd;
    }
}


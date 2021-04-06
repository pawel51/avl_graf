
public class AvlTree {
    Wezel root;
    AvlTree(){
        root=null;
    }



    private void zmienLicznoscLL(Wezel A, Wezel B) {
        int lepLewyA=0;
        int lepLewyB=0;
        int lepPrawyB=0;
        if(A.lsyn != null)
            lepLewyA = A.lsyn.lep;          //zmiana liczności poddrzew spowodowanej obrotem
        if(B.lsyn != null)
            lepLewyB = B.lsyn.lep;
        if(B.psyn != null)
            lepPrawyB = B.psyn.lep;
        A.lep = lepLewyA + lepLewyB + 1;
        B.lep = lepLewyA + lepLewyB + lepPrawyB + 2;

    }
    private void zmienLicznoscRR(Wezel A, Wezel B){
        int lepPrawyA=0;
        int lepLewyB=0;
        int lepPrawyB=0;
        if(A.psyn != null)
            lepPrawyA = A.psyn.lep;          //zmiana liczności poddrzew spowodowanej obrotem
        if(B.lsyn != null)
            lepLewyB = B.lsyn.lep;
        if(B.psyn != null)
            lepPrawyB = B.psyn.lep;
        A.lep = lepPrawyA + lepPrawyB + 1;
        B.lep = lepPrawyA + lepPrawyB + lepLewyB + 2;
    }
    private void zmienLicznoscRL(Wezel A, Wezel B, Wezel C) {
        int lepLewyA=0;
        int lepPrawyB=0;
        int lepLewyC=0;
        int lepPrawyC=0;
        if(A.lsyn != null)
            lepLewyA = A.lsyn.lep;
        if(B.psyn != null)
            lepPrawyB = B.psyn.lep;
        if(C.lsyn != null)
            lepLewyC = C.lsyn.lep;
        if(C.psyn != null)
            lepPrawyC = C.psyn.lep;
        A.lep = lepLewyA + lepLewyC + 1;
        B.lep = lepPrawyC + lepPrawyB + 1;
        C.lep = A.lep + B.lep + 1;

    }
    private void zmienLicznoscLR(Wezel A, Wezel B, Wezel C) {
        int lepPrawyA=0;
        int lepLewyB=0;
        int lepLewyC=0;
        int lepPrawyC=0;
        if(A.psyn != null)
            lepPrawyA = A.psyn.lep;
        if(B.lsyn != null)
            lepLewyB = B.lsyn.lep;
        if(C.lsyn != null)
            lepLewyC = C.lsyn.lep;
        if(C.psyn != null)
            lepPrawyC = C.psyn.lep;
        A.lep = lepPrawyA + lepPrawyC + 1;
        B.lep = lepLewyC + lepLewyB + 1;
        C.lep = A.lep + B.lep + 1;

    }

    public void LL(Wezel A){
        //System.out.println("LL");
        Wezel B = A.psyn;                   //W B umieszczamy adres prawego syna węzła A
        Wezel tempOjciecA = A.ojciec;       //w tempOjciecA umieszczamy ojca A
        zmienLicznoscLL(A, B);
        A.psyn = B.lsyn;                    //prawym synem A staje się lewy syn B
        if(B.lsyn != null)                  //jeżeli lewy syn B istnieje to jego ojcem jest A
            B.lsyn.ojciec = A;
        B.lsyn = A;                         //lewym synem B staje się A
        B.ojciec = tempOjciecA;             //ojcem B staje się pierwotny ojciec A
        A.ojciec = B;                       //A zmienia ojca na B
        if(tempOjciecA == null) {           //sprawdzamy czy węzeł A był korzeniem
            root = B;                       //jeśli był to zmieniamy korzeń na B
        }else{                              //jeśli nie był to uaktualniamy ojca A
            if(tempOjciecA.lsyn == A)       //prawego albo lewego syna ojca A
                tempOjciecA.lsyn = B;
            if(tempOjciecA.psyn == A)
                tempOjciecA.psyn = B;
        }
        if(B.bf == -1){                     //A.bf   -2 || -2   --->  0 || -1
            A.bf=0;                         //B.bf   -1 ||  0   --->  0 ||  1
            B.bf=0;
        }else if(B.bf == 0){
            A.bf=-1;
            B.bf=1;
        }
        else
            System.out.println("LL bf wrong read");

    }

    public void RR(Wezel A){
        //System.out.println("RR");
        Wezel B = A.lsyn;                   //w B umieszczamy adres lewego syna
        Wezel tempOjciecA = A.ojciec;       //w tempOciecA umieszczamy ojca A
        zmienLicznoscRR(A, B);
        A.lsyn = B.psyn;                    //lewy syn A staje się prawy syn B
        if(B.psyn != null)                  //jeżeli B miał prawego syna
            B.psyn.ojciec = A;              //to jego ojcem staje się A
        B.psyn = A;                         //prawym synem B staje się A
        B.ojciec = tempOjciecA;             //ojcem B staje się ojcie A
        A.ojciec = B;                       //ojcem A staje się B
        if(tempOjciecA == null)             //jeżeli A był rootem
            root = B;                       //to teraz rootem jest B
        else{                               //jeżeli nie to uaktualniamy ojca A
            if(tempOjciecA.lsyn == A)       //lewego
                tempOjciecA.lsyn = B;
            if(tempOjciecA.psyn == A)       //albo prawego syna
                tempOjciecA.psyn = B;
        }
        if(B.bf == 1){
            A.bf=0;                         //A.bf   2 || 2   --->  0 || 1
            B.bf=0;                         //B.bf   1 || 0   --->  0 || -1
        }else if(B.bf == 0){
            A.bf=1;
            B.bf=-1;
        }
        else
            System.out.println("RR wrong read");
    }
    public void LR(Wezel A){
        //System.out.println("LR");

        Wezel B = A.lsyn;                   //ustawienie B i C
        Wezel C = B.psyn;
        Wezel tempOjciecA = A.ojciec;       //zapamiętanie ojca A
        zmienLicznoscLR(A, B, C);
        B.psyn = C.lsyn;                    //prawym synem B staje się lewy syn C
        if(B.psyn != null)                  //Jeżeli prawy syn B (lewy syn C) istnieje to
            B.psyn.ojciec = B;              //B staje się jego ojcem
        A.lsyn = C.psyn;                    //lewy syn A staje się prawym synem C
        if(A.lsyn != null)                  //Jeżeli lewy syn A (prawy syn C) istnieje to
            A.lsyn.ojciec = A;              //A staje się jego ojcem
        C.psyn = A;                         //prawym synem C staje się A
        C.lsyn = B;                         //lewym synem C staje się B
        A.ojciec = C;                       //ojcem A staje się C
        B.ojciec = C;                       //ojcem B staje się C
        C.ojciec = tempOjciecA;             //ojcem C staje się ojciec A
        if(tempOjciecA == null)             //Jeżeli A był rootem to
            root = C;                       //rootem jest teraz C
        else{                               //Jeżeli nie był to
            if(tempOjciecA.lsyn == A)       //Sprawdzamy czy A był lewym czy prawym synem
                tempOjciecA.lsyn = C;       //uaktualniamy pierwotnego ojca A
            if(tempOjciecA.psyn == A)
                tempOjciecA.psyn = C;
        }
        if(C.bf == 1)       //A.bf  2 | 2 | 2
            A.bf = -1;      //B.bf -1 |-1 |-1
        else                //C.bf -1 | 0 | 1
            A.bf = 0;       //---po rotacji---
        if(C.bf == -1)      //A.bf  0 | 0 | -1
            B.bf = 1;       //B.bf  1 | 0 | 0
        else                //C.bf  0 | 0 | 0
            B.bf = 0;
        C.bf = 0;
    }

    public void RL(Wezel A){
        //System.out.println("RL");
        Wezel B = A.psyn;                   //Ustawienie B i C
        Wezel C = B.lsyn;
        Wezel tempOjciecA = A.ojciec;       //zapamiętanie ojca A
        zmienLicznoscRL(A, B, C);
        B.lsyn = C.psyn;                    //lewym synem B staje się prawy syn C
        if(B.lsyn != null)                  //Jeżeli lewy syn B(prawy syn C) istnieje
            B.lsyn.ojciec = B;              //to jego ijcem staje sięB
        A.psyn = C.lsyn;                    //prawym synem A staje się lewy syn C
        if(A.psyn != null)                  //Jeżeli prawy syn B(lewy syn C) istnieje
            A.psyn.ojciec = A;              //to jego ojcem staje się A
        C.lsyn = A;                         //lewym synem C staje się A
        C.psyn = B;                         //prawym synem C staje się B
        A.ojciec = C;                       //ojcem A staje się C
        B.ojciec = C;                       //ojcem B staje się C
        C.ojciec = tempOjciecA;             //ojcem C staje się pierwotny ojciec A
        if(tempOjciecA == null)             //Jeżeli A był rootem
            root = C;                       //rootem staje się C
        else{
            if(tempOjciecA.lsyn == A)       //Jeżeli nie był
                tempOjciecA.lsyn = C;       //uaktualniamy lewego albo prawego
            if(tempOjciecA.psyn == A)       //syna pierwotnego ojca A
                tempOjciecA.psyn = C;
        }
        if(C.bf == -1)                      //A.bf -2 |-2 |-2
            A.bf = 1;                       //B.bf  1 | 1 | 1
        else                                //C.bf -1 | 0 | 1
            A.bf = 0;                  //---po rotacji
        if(C.bf == 1)                       //A.bf  1 | 0 | 0
            B.bf = -1;                      //B.bf  0 | 0 |-1
        else                                //C.bf  0 | 0 | 0
            B.bf = 0;
        C.bf = 0;
    }



    public Wezel search(Klucz klucz){
        if(root == null)
            return null;
        Wezel troot = root;
        int c;
        while (true) {                                     //Póki nie znajdziemy miejsca na nowy węzęł w
            c = compareKeys(troot.klucz, klucz);
            if (c==1) {  //troot.klucz > w.klucz -->  1    //Jeżeli szukany jest mniejszy od troot
                if(troot.lsyn != null)
                    troot = troot.lsyn;
                else {
                    return troot;                           //Jeżeli nie znaleziono to zwraca potencjalnego ojca
                }
            }
            else if(c == -1){                               //Jeżeli szukany jest większy od troot
                if(troot.psyn != null)
                    troot = troot.psyn;
                else {                                      //Jeżeli nie znaleziono to zwraca potencjalnego ojca
                    return troot;
                }
            }
            else {                                          //Jeżeli klucze są równe to zwraca co znaleziono
                return troot;
            }
        }
    }

    public void insert(Wezel w) {

        Wezel troot = root;                              //tymczasowy troot by nie zmieniać roota
        if (troot == null) {                              //Jeżeli drzewo jest puste
            root = w;
            return;
        }


        Wezel potencjalnyOjciec = search(w.klucz);
        if(potencjalnyOjciec==null)
            return;
        int c = compareKeys(potencjalnyOjciec.klucz, w.klucz);
        if(c == 0){                         //wezel juz istnieje
            return;
        }
        else if(c == 1){                    //Nowy węzeł jest mniejszy od ojca
            w.ojciec = potencjalnyOjciec;
            troot = potencjalnyOjciec;
            troot.lsyn = w;
        }
        else if(c == -1){
            w.ojciec = potencjalnyOjciec;  //Nowy wezel jest większy od ojca
            troot = potencjalnyOjciec;
            troot.psyn = w;
        }

        if(troot.bf != 0) {                 //jeżeli ojciec miejsca wstawienia jest różny od zera
            troot.bf=0;                     //to wyważamy sąsiednią gałąź dodając element
            while(troot != null){           //zwiększamy lep ojca i każdego przodka ojca
                troot.lep++;
                troot = troot.ojciec;
            }
            return;
        }

        Wezel tojciec=troot;         //określenie tymczasowego ojca i tymczasowego syna
        Wezel tsyn=w;
        while(tojciec != null) {            //Przypadek gdy bf ojca == 0,
            if(tojciec.bf != 0){
                break;
            }
            if(tojciec.lsyn == tsyn)        //przyszliśmy z lewej zmieniamy na 1
                tojciec.bf = 1;
            else
                tojciec.bf = -1;            //przyszliśmy z prawej zmieniamy na -1
            tojciec.lep += 1;               //zwiększamy liczbę elementów poddrzewa ojca
            tsyn = tojciec;                 //idziemy w górę
            tojciec = tojciec.ojciec;
        }

        if(tojciec == null)                 //sprawdzamy czy nie wyszliśmy korzeniem
            return;
        Wezel tojciec2 = tojciec;           //Zaktualizowanie liczby elementów poddrzewa
        while(tojciec2 != null){
            tojciec2.lep++;
            tojciec2 = tojciec2.ojciec;
        }

        if(tojciec.bf == 1) {               //lewe podrzewo ojca jest cięższe
            if(tojciec.psyn == tsyn) {      //przyszliśmy z prawej
                tojciec.bf = 0;
                return;
            }
            if(tojciec.lsyn == tsyn){       //przyszliśmy z lewej
                if(tsyn.bf == 1) {
                    RR(tojciec);            //lewe poddrzewo syna jest cięższe
                }

                else if(tsyn.bf == -1) {     //prawe poddrzewo syna jest cięższe
                    LR(tojciec);
                }
            }
        }
        else if(tojciec.bf == -1){          //prawe poddrzewo ojca jest cięższe
            if(tojciec.lsyn == tsyn){       //przyszliśmy z lewej
                tojciec.bf = 0;
                return;
            }
            if(tojciec.psyn == tsyn){       //przyszliśmy z prawej
                if(tsyn.bf == -1)           //prawe poddrzewo syna jest cięższe
                    LL(tojciec);
                else if(tsyn.bf == 1)       //lewe poddrzewo syna jest cięższe
                    RL(tojciec);
            }
        }

    }

    public int compareKeys(Klucz key1, Klucz key2){

        if(Date.compare(key1.data, key2.data) == 1)
            return 1;
        else if(Date.compare(key1.data, key2.data) == 0)
            if(key1.id_1 > key2.id_1)
                return 1;
            else if(key1.id_1 == key2.id_1)
                if(key1.id_2 > key2.id_2)
                    return 1;
                else if(key1.id_2 == key2.id_2)
                    return 0;
                else
                    return -1;
            else
                return -1;
        else
            return -1;
    }




    private Wezel pred(Wezel x) {
        Wezel r;
        if (x != null) {
            if (x.lsyn != null) {         //idziemy raz w lewo i cały czas w prawo aż do liścia
                x = x.lsyn;
                while (x.psyn != null)
                    x = x.psyn;
            } else
                do {
                    r = x;
                    x = x.ojciec;
                } while (x != null && x.psyn != r);   //idziemy w górę drzewa, aż r będzie lewym synem x
        }
        return x;
    }

    public Wezel delete(Klucz k) {
        Wezel x = search(k);            //potencjalny wezel do usuniecia
        if(x == null)                       //drzewo jest puste
            return null;
        int c = compareKeys(x.klucz, k);      //sprawdzamy czy element istnieje

        if(c != 0)
            return null;

        Wezel t=null,y=null,z=null;             //y jest ojcem z i t
        boolean nest;                           //wskaźnik zagnieżdżenia
        if(x.lsyn != null && x.psyn != null){   //Jeżeli x ma dwóch synów to usuwamy
            y = delete(pred(x).klucz);          //poprzednik i wstawiamy go w miejsce x, y = poprzednik x
            nest = false;                       //żeby nie wyważać wielokrotnie
        }
        else{
            if(x.lsyn != null){                 //x ma jednego lub zero synów
                y = x.lsyn;                     //y staje się lewym synem x
                x.lsyn = null;                  //na pewno jest to jedyny syn x
            }
            else if(x.psyn != null){
                y = x.psyn;
                x.psyn = null;
            }
            x.bf = 0;
            nest = true;                        //trzeba wyważyć
        }
        if(y != null) {                         //Jeżeli y istnieje to ojcem y
            y.ojciec = x.ojciec;                //staje się ojciec x(usuwam referencje do x)

            y.lsyn = x.lsyn;
            if (y.lsyn != null)
                y.lsyn.ojciec = y;

            y.psyn = x.psyn;
            if(y.psyn != null)                  //zmiana referencji u synów x
                y.psyn.ojciec = y;

            y.bf = x.bf;
            y.lep = x.lep;
        }

        if(x.ojciec != null){                   //zmiana referencji u ojca x
            if(x.ojciec.lsyn == x)
                x.ojciec.lsyn = y;
            else
                x.ojciec.psyn = y;
        }
        else
            root = y;                           //usuwany element był rootem
        //wyważanie
        if(nest) {                               //sprawdzenie zagnieżdżenia
            z = y;                              //ustawienie y jako ojca z
            y = x.ojciec;
            while (y != null) {                   //idziemy w górę drzewa aż do korzenia
                y.lep -= 1;                       //odejmujemy 1 w każdym węźle idąc od y do korzenia
                if (y.bf == 0) {                  //PRZYPADEK 1
                    if (y.lsyn == z)             //y.bf = 0, czyli węzeł y był w stanie równowagi
                        y.bf = -1;              //przed usunięciem węzła x w jednym z jego poddrzew
                    else
                        y.bf = 1;
                    y = y.ojciec;
                    break;
                } else {

                    if ((y.bf == 1 && y.lsyn == z) || (y.bf == -1 && y.psyn == z)) {
                        y.bf = 0;               //PRZYPADEK 2
                        z = y;                  //y.bf ≠ 0 i skrócone zostało cięższe poddrzewo.
                        y = y.ojciec;           //W takim przypadku y.bf = 0
                    }                           //idziemy w górę
                    else {                              //PRZYPADEK 3a)
                        if (y.lsyn == z)
                            t = y.psyn;             //ustawienie t jako brata z
                        else
                            t = y.lsyn;
                        if (t.bf == 0) {                //współczynnik t.bf syna y w cięższym poddrzewie jest równy 0
                            if (y.bf == 1)
                                RR(y);
                            else
                                LL(y);
                            y = y.ojciec;
                            break;
                        } else if (t.bf == y.bf) {      //PRZYPADEK 3b)
                            if (y.bf == 1)             //Współczynnik y.bf jest taki sam jak współczynnik t.bf
                                RR(y);
                            else
                                LL(y);
                            y = t.ojciec;               //rotacja zmniejsza wysokość poddrzewa
                            z = t;
                        } else {                        //Przypadek 3c)
                            if (y.bf == 1) {              //Współczynniki y.bf i t.bf są przeciwne
                                LR(y);
                            }
                            else if(y.bf == -1)
                                RL(y);
                            z = y.ojciec;               //rotacja zmniejsza wysokość poddrzewa
                            y = z.ojciec;
                        }
                    }
                }
            }
            while(y != null){
                y.lep--;
                y = y.ojciec;
            }
        }
        return x;
    }

    public int countBetween(Klucz p, Klucz q){ // p to np 1000-01-01 -1 -1 ,a q 2000-01-01 10001 10001
        int r1 = ranga(p);
        int r2 = ranga(q);
        return r1 - r2;
    }
    private int ranga(Klucz x){
        int ranga = 1;
        Wezel troot = root;
        int c;
        while(troot != null){
            c = compareKeys(x, troot.klucz);
            if(c == -1){
                if(troot.psyn != null){
                    ranga = ranga + troot.psyn.lep + 1;     //Jeżeli x<akt.liczba to uaktualnij rangę i przejdź na lewo
                }
                else
                    ranga++;
                troot = troot.lsyn;
            }
            else if(c == 1){
                troot = troot.psyn;                         //Jeżeli x>akt.liczba to przejdz na prawo
            }
            else{
                if(troot.psyn != null)
                    ranga = ranga + troot.psyn.lep;         //Jeżeli x==akt.liczba to zwróć rangę
                return ranga;
            }
        }
        return ranga;
    }

    int licznik;
    public void countElements(Wezel x){
        if(x != null){
            licznik ++;
            countElements(x.psyn);
            countElements(x.lsyn);

        }
    }
    // zwraca liczbę węzłów pomiędzy datami d1 i d2
    // złożoność od logn do n
    public int findBetween(Wezel x, Date d1, Date d2){
        int counter=0;
        if(x != null){
            int c1 = Date.compare(x.klucz.data, d1);
            int c2 = Date.compare(x.klucz.data, d2);

            if(c1 == -1)                                            // jeżeli d1 jest późniejsze od węzła
                counter += findBetween(x.psyn, d1, d2);               // musimy policzyć węzły pomiędzy d1 i d2 w prawym poddrzewie
            else if((c1 == 0 || c1 == 1) && (c2 == 0 || c2 == -1)){ // jeżeli d1 jest wcześniejsze lub równe węzłowi x  i  d2 jest późniejsze lub równe węzłowi x
                counter++;                                            // zwiększamy counter o jeden
                counter += findBetween(x.lsyn, d1, d2);               // wywołujemy rekurencyjnie wyszukiwanie w lewym i prawym poddrzewie
                counter += findBetween(x.psyn, d1, d2);
            }
            else if(c2 == 1)                                        // jeżeli d2 jest wcześniejsze od x
                counter += findBetween(x.lsyn, d1, d2);             // to zliczamy węzły w lewym poddrzewie
        }
        return counter;
    }
}























import java.util.Calendar;
import java.util.GregorianCalendar;

public class Klucz {
    Date data;
    int id_1;
    int id_2;

    public Klucz(String dataStr, int id_1, int id_2) {
        this.data = setDate(dataStr);
        this.id_1 = id_1;
        this.id_2 = id_2;
    }

    private Date setDate(String dataStr) {
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


public class Date{
    int year;
    int month;
    int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    // zwraca 1, gdy o1 > o2
    public static int compare(Date o1, Date o2) {
        int y1 = o1.year;
        int y2 = o2.year;
        int m1 = o1.month;
        int m2 = o2.month;
        int d1 = o1.day;
        int d2 = o2.day;

        if(y1 > y2)
            return 1;
        else if(y1 == y2)
            if(m1 > m2)
                return 1;
            else if(m1 == m2)
                if(d1 > d2)
                    return 1;
                else if(d1 == d2)
                    return 0;
                else return -1;
            else
                return -1;
        else
            return -1;
    }


}

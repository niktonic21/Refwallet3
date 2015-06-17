package sk.upjs.ics.refwallet;

import android.provider.BaseColumns;

/**
 * Created by Maťo21 on 9. 6. 2015.
 */
public interface Provider {
    public interface Zapas extends BaseColumns {
        public static final String TABLE_NAME = "zapas";

        public static final String CISLO_ZAP = "cislozap";

        public static final String LIGA = "liga";

        public static final String TIMESTAMP = "timestamp";//to je datum

        public static final String STADION = "stadion";
        public static final String DOMACI = "domaci";
        public static final String HOSTIA = "hostia";
        public static final String HR1 = "hr1";
        public static final String HR2 = "hr2";
        public static final String CR1 = "cr1";
        public static final String CR2 = "cr2";
        public static final String INSTRUKTOR = "instruktor";
        public static final String VIDEO = "video";
        public static final String PRICHOD = "prichod";
        public static final String ODCHOD = "odchod";
        public static final String Z_MESTA = "zmesta";
        public static final String DO_MESTA = "domesta";
        public static final String PAUSAL = "pausal";
        public static final String STRAVNE = "stravne";
        public static final String CESTOVNE = "cestovne";
        public static final String SPOLU = "spolu";


    }
}

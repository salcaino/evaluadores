package naranjalab.common.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public final class Utility {

    private Utility() {
    }
    public static final String dateFormatString = "dd/MM/YYYY";
    public static final Path currentLocation = Paths.get(System.getProperty("user.dir"));
    public static final Path libLocation = currentLocation.resolve("lib");
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final Path pathRespuestasIC = libLocation.resolve("respuestasic.txt");
    public static final String sdfFormatMiliseconds = "yyyymmddssSSSS";
    
    public static String generarId(){
        SimpleDateFormat sdf = new SimpleDateFormat(sdfFormatMiliseconds);
        return sdf.format(new Date());
    }
    public static Integer getHashCode(String... target){
        if(target == null || target.length == 0) return null;
        int finalhash = 0;
        for (String string : target) {
            int hash = 5;
            hash = 83 * hash + Objects.hashCode(string);
            finalhash += hash;
        }
        return finalhash;
    }
}

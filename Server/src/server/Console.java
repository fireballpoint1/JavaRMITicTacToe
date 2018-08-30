package server;

import java.text.*;
import java.util.*;

public class Console {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    
    public static void print(String info) {
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] " + info);
    }
}
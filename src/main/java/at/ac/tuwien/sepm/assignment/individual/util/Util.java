package at.ac.tuwien.sepm.assignment.individual.util;

public class Util {

    //Str str.isEmpty(); str method aber es funktiniert nicht immer richtig
    public static boolean isEmpty(String str){
        if(str == null) return true;
        return str.isEmpty();

    }

    // noch ein boolean wert brauche ich auch

    public static boolean isEmpty(Double dbl){
        if(dbl == null) return true;
        return dbl == 0.0;
    }

}

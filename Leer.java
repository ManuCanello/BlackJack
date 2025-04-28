import java.util.Scanner;

public class Leer{

    public static String leeString(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static int leeInt(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static Double leerDouble(){
        Scanner sc = new Scanner(System.in);
        return sc.nextDouble();
    }

}
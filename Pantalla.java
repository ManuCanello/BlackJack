import java.io.IOException;

public class Pantalla{

    public static void borrarPantalla(){
        System.out.print("\033[H\033[2J");
    }

    public static void esperarTecla(){
        try {
            System.in.read();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
    }

    public static void esperar(int ms){
        try {
            Thread.sleep(ms);  
        } catch (InterruptedException e) {
            
        }
    }
}
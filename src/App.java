import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class App {
    private ArrayList<Carta> mazo = new ArrayList<>();
    

    public static void main(String[] args) {
        new App();
    }
    
    public App(){
        juego();
    }

    private void juego(){
        crearMazo();
        mezclar();

        Jugador p1 = new Jugador("manu");
        

        for(int i=0;i<3;i++){
            imprimirCarta(0);
            darCartaJugador(p1,getMazo().get(0));
            getMazo().remove(0);
        }
        

        System.out.println(masDe21(p1.getCartasEnMano()));
        System.out.println("Total:"+sumaValores(p1.getCartasEnMano()));

    }
    
    private void addCarta(Carta c){
        this.mazo.add(c);
    }
    
    private void darCartaJugador(Jugador j,Carta c){
        j.setCartasEnMano(c);
    }

    private int sumaValores(ArrayList<Carta> lista){
        
        int total=0;
        for(Carta c : lista){
            total += c.getValor();
        }

        return total;
    }

    private void crearMazo(){
        String[] tipo={"♠",("\u001B[31m"+"♥"+"\u001B[0m"),"♣",("\u001B[31m"+"♦"+"\u001B[0m")};
        
        for(int t=0;t<4;t++){
            for(int i=1; i<=12;i++){
                if(i==1)
                    addCarta(new Carta("A",11,tipo[t]));
                else{
                    if(i<10)
                        addCarta(new Carta(String.valueOf(i),i,tipo[t]));
                    else{
                        if(i==10)
                            addCarta(new Carta("J",10,tipo[t]));
                        if(i==11)
                            addCarta(new Carta("Q",10,tipo[t]));
                        if(i==12)
                            addCarta(new Carta("K",10,tipo[t]));
                    }
                }
                
            }
        }
    }
    
    private void mezclar(){
        Collections.shuffle(mazo);
    }
    
    private boolean masDe21(ArrayList<Carta> lista){
        for(Carta c : lista ){
            if(c.getValor()==11 && sumaValores(lista)>21)
                c.cambiarValorDeA();
            }
        
        return sumaValores(lista) > 21;
        

        
    }
    //hacer devuelta para que imprima todas las cartas una al lado de otra
    private void imprimirCarta(int p){
        System.out.println("┌─────────┐\n│"+mazo.get(p).getNumero()+"        │\n│         │\n│    "+mazo.get(p).getTipo()+"    │\n│         │\n│        "+mazo.get(p).getNumero()+"│\n└─────────┘");
    }

    

    private ArrayList<Carta> getMazo() {
        return mazo;
    }

    

}

class Carta{

    private String numero;
    private int valor;
    private String tipo;

    public Carta(String n, int v, String t){
        setNumero(n);
        setValor(v);
        setTipo(t);
    }

    private void setNumero(String numero) {
        this.numero = numero;
    }

    private void setValor(int valor) {
        this.valor = valor;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public int getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void cambiarValorDeA(){
        setValor(1);
    }


}

class Jugador{

    private String nombre;
    private ArrayList<Carta> cartasEnMano = new ArrayList<>();

    public Jugador(String n){
        setNombre(n);
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Carta> getCartasEnMano() {
        return cartasEnMano;
    }

    public void setCartasEnMano(Carta c) {
        this.cartasEnMano.add(c);
    }


}

class Leer{

    public static String leeString(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static int leeInt(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

}
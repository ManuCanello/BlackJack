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

        Jugador p1 = new Jugador("manu",1000.0);
        

        for(int i=0;i<2;i++){
            darCartaJugador(p1,getMazo().get(0));
            getMazo().remove(0);
        }
        
        imprimirCarta(p1.getCartasEnMano());

        
        System.out.println("\nTotal:"+sumaValores(p1.getCartasEnMano()));

    }
    
    private ArrayList<Carta> getMazo() {
        return mazo;
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
    
    private void imprimirCarta(ArrayList<Carta> cartas){
        int s = cartas.size();
        
        for(int i=0;i<s;i++){
            System.out.print("┌─────────┐");
        }
        
        System.out.println();

        for(Carta c : cartas){
            System.out.print("│"+c.getNumero()+"        │");
        }
        
        System.out.println();

        for(int i=0;i<s;i++){
            System.out.print("│         │");
        }

        System.out.println();

        for(Carta c : cartas){
            System.out.print("│    "+c.getTipo()+"    │");
        }

        System.out.println();
        
        for(int i=0;i<s;i++){
            System.out.print("│         │");
        }
        
        System.out.println();

        for(Carta c : cartas){
            System.out.print("│        "+c.getNumero()+"|");
        }

        System.out.println();
        
        for(int i=0;i<s;i++){
            System.out.print("└─────────┘");
        }
        
    }

    private void imprimirCarta(ArrayList<Carta> carta, int p){
        System.out.println("┌─────────┐\n│"+carta.get(p).getNumero()+"        │\n│         │\n│    "+carta.get(p).getTipo()+"    │\n│         │\n│        "+carta.get(p).getNumero()+"│\n└─────────┘");

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

class Dealer{

    private String nombre;
    private ArrayList<Carta> cartasEnMano = new ArrayList<>();

    public Dealer(String n){
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

class Jugador extends Dealer{
    
    private double plata;
    
    public Jugador(String n,Double p) {
        super(n);
        setPlata(p);
    }

    private void setPlata(double plata){
        this.plata = plata;
    }

    public double getPlata(){
        return this.plata;
    }
    
    public void cambiarPlata(double p){
        if(p>0)
            this.plata += p;
        else   
            this.plata -= p;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class App{
    
    private ArrayList<Carta> mazo = new ArrayList<>();
    
    public static void main(String[] args) {
        App game = new App();
        game.juego();
    }
    
    
    private void juego(){
    //Falta pedir nombre de jugador, que se reinicie cartas en mano, cuando volver a mezclar(y agregar cartas que no esten (if carta a agreagar != 4 cartas actuales, agregar), y que el juego
    // se juege hasta que no haya plata o no quiera mas jugar. 
        crearMazo();
        mezclar();
        Jugador p1 = new Jugador("KNE",1000.0);
        Dealer dealer = new Dealer("DEALER");
        
        repartirCartas(dealer,p1);
        juegoJugador(dealer,p1,0);
    
    }
    
    private ArrayList<Carta> getMazo() {
        return mazo;
    }

    private void addCarta(Carta c){
        this.mazo.add(c);
    }
    
    private void darCarta(Jugador j,Carta c){
        j.setCartasEnMano(c);
        getMazo().remove(0);
    }

    private void darCarta(Dealer j,Carta c){
        j.setCartasEnMano(c);
        getMazo().remove(0);
    }

    private void darCartaDividir(Jugador j,Carta c){
        j.setDividir(c);
        getMazo().remove(0);
    }

    private int sumaValores(ArrayList<Carta> lista){
        
        int total=0;
        for(Carta c : lista){
            total += c.getValor();
        }

        return total;
    }

    private void crearMazo(){
        String[] tipo={"$",("\u001B[31m"+"@"+"\u001B[0m"),"&",("\u001B[31m"+"^"+"\u001B[0m")};
        
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
    
    private void comprobarValorA(ArrayList<Carta> lista){
        for(Carta c : lista ){
            if(c.getValor()==11 && sumaValores(lista)>21)
                c.cambiarValorDeA();
            }
    }

    private void repartirCartas(Dealer dealer,Jugador jugador ){
        for(int i=0;i<4;i++){
            if(i%2==0){
                darCarta(jugador, getMazo().get(0));
                comprobarValorA(jugador.getCartasEnMano());
                secuenciaRepartir(dealer, jugador, i);
            }else{
                darCarta(dealer, getMazo().get(0));
                secuenciaRepartir(dealer, jugador, i);
            }

        }
    }
    
    private void mostrarCartas(Dealer dealer, Jugador jugador,int t){
        if(t==0)
            imprimirCartaAtras(dealer);
        else{
            imprimirCarta(dealer.getCartasEnMano());
            System.out.println("\n"+dealer.getNombre()+":"+sumaValores(dealer.getCartasEnMano()));
        }
        
        if(jugador.getDividir().isEmpty()){
            imprimirCarta(jugador.getCartasEnMano());
            System.out.println("\n"+jugador.getNombre()+":"+sumaValores(jugador.getCartasEnMano()));
            
        }else{
            imprimirCarta(jugador);
            System.out.println("\nJuego 1:"+sumaValores(jugador.getCartasEnMano())+espaciosPuntos(jugador.getCartasEnMano())+"Juego 2:"+sumaValores(jugador.getDividir()));
        }
    }

    private void juegoJugador(Dealer dealer,Jugador jugador,int t){
        boolean cont = false;
      
        if(t==0)
            comprobarValorA(jugador.getCartasEnMano());
        else
            comprobarValorA(jugador.getDividir());
        
        if(sumaValores(jugador.getCartasEnMano()) == 21 && t==0 || sumaValores(jugador.getDividir()) == 21 && t==1){
            mostrarCartas(dealer, jugador, 0);
            System.out.println("BlackJack");
            cont = true;
            Pantalla.esperarTecla();
            Pantalla.borrarPantalla();
        }
        
        while(!cont){ 
                
                mostrarCartas(dealer, jugador,0);
                if(!jugador.getDividir().isEmpty()){
                    System.out.println("\nJuego "+(t+1));
                }
                    
                System.out.print("1)Pedir carta 2)Plantarse");
                
                if(jugador.getCartasEnMano().size()==2 && t==0 || jugador.getDividir().size()==2 && t==1)
                    System.out.print(" 3)Doblar");
                
                if(jugador.getDividir().isEmpty() && jugador.getCartasEnMano().get(0).getNumero().equals(jugador.getCartasEnMano().get(1).getNumero()))
                        System.out.print(" 4)Dividir");
                
                System.out.println("\n");
                
                switch (Leer.leeInt()){
                    case 1:
                        if(t==0)
                            darCarta(jugador, getMazo().get(0));
                        else
                            darCartaDividir(jugador, getMazo().get(0));
                        break;
                    
                    case 2:
                        cont = true;
                        break;
                    case 3:
                        if(jugador.getCartasEnMano().size() ==2 && t==0){
                            darCarta(jugador, getMazo().get(0));
                            mostrarCartas(dealer, jugador,0);
                            cont = true;
                            break;
                        }
                        
                        if(jugador.getDividir().size()==2 && t==1){
                            darCartaDividir(jugador, getMazo().get(0));
                            mostrarCartas(dealer, jugador,0);
                            cont = true;
                            break;
                        }

                        break;
                            
                    case 4:
                        if(jugador.getDividir().isEmpty() && jugador.getCartasEnMano().get(0).getNumero().equals(jugador.getCartasEnMano().get(1).getNumero())){
                            Pantalla.borrarPantalla();
                            juegoDividir(dealer,jugador);
                            cont = true;
                            break;
                        }
                    
                    default:
                        System.out.println("intente devuelta");
                        Pantalla.esperarTecla();
                        Pantalla.borrarPantalla();
                        break;
                }
                
                if(t==0)
                    comprobarValorA(jugador.getCartasEnMano());
                else
                    comprobarValorA(jugador.getDividir());
                
                Pantalla.borrarPantalla();
                
                if(t==0){
                    if(sumaValores(jugador.getCartasEnMano())>21)
                        cont = true;
                }else{
                    if(sumaValores(jugador.getDividir())>21)
                        cont = true;
                } 
                    
        }
        
        if(jugador.getDividir().isEmpty() || t==1){
            juegoDealer(dealer,jugador);
        }
    }

    private void juegoDealer(Dealer dealer, Jugador jugador){
        
        mostrarCartas(dealer, jugador, 0);
        Pantalla.esperar(1000);
        Pantalla.borrarPantalla();
        comprobarValorA(dealer.getCartasEnMano());
        
        mostrarCartas(dealer, jugador, 1);
        
        if(sumaValores(dealer.getCartasEnMano())<17){
            do { 
                Pantalla.esperar(1000);
                Pantalla.borrarPantalla();
                darCarta(dealer, getMazo().get(0));
                comprobarValorA(dealer.getCartasEnMano());
                mostrarCartas(dealer, jugador, 1);
            } while (sumaValores(dealer.getCartasEnMano())<17);
        }
        
        
        Pantalla.borrarPantalla();
        mostrarCartas(dealer, jugador, 1);

        if(jugador.getDividir().isEmpty())
            ganador(dealer,jugador);
        
    }

    private void juegoDividir(Dealer dealer, Jugador jugador){
        if(jugador.getCartasEnMano().get(0).getValor()==1)
            jugador.getCartasEnMano().get(0).cambiarValorDeA11();
        
        darCartaDividir(jugador, jugador.getCartasEnMano().get(1));
        jugador.getCartasEnMano().remove(1);
        
        darCarta(jugador, getMazo().get(0));
        darCartaDividir(jugador, getMazo().get(0));
        
        juegoJugador(dealer, jugador, 0);
        juegoJugador(dealer, jugador, 1);
        ganadorDividir(dealer, jugador);
        
    }
    
    private void ganador(Dealer dealer, Jugador jugador){
    
        if(sumaValores(dealer.getCartasEnMano()) > 21 && sumaValores(jugador.getCartasEnMano()) > 21)
            System.out.println("Ambos se pasaron");
        
        if(sumaValores(dealer.getCartasEnMano()) <= 21 && sumaValores(jugador.getCartasEnMano()) > 21)
            System.out.println(dealer.getNombre()+" es el ganador");
        
        if(sumaValores(dealer.getCartasEnMano()) > 21 && sumaValores(jugador.getCartasEnMano()) <= 21)
            System.out.println(jugador.getNombre()+" es el ganador");

        if(sumaValores(dealer.getCartasEnMano()) <= 21 && sumaValores(jugador.getCartasEnMano()) <= 21){
            if(sumaValores(dealer.getCartasEnMano()) == sumaValores(jugador.getCartasEnMano()))
                System.out.println("Empate");
            else{
                if(sumaValores(dealer.getCartasEnMano()) > sumaValores(jugador.getCartasEnMano()))
                    System.out.println(dealer.getNombre()+" es el ganador");
            else
                    System.out.println(jugador.getNombre()+" es el ganador");
            }
            
        }      
    }

    private void ganadorDividir(Dealer dealer, Jugador jugador){
        //juego 1
        
        if(sumaValores(dealer.getCartasEnMano()) > 21 && sumaValores(jugador.getCartasEnMano()) > 21)
            System.out.println("Juego 1:Ambos se pasaron");
        
        if(sumaValores(dealer.getCartasEnMano()) <= 21 && sumaValores(jugador.getCartasEnMano()) > 21)
            System.out.println("Juego 1:"+dealer.getNombre()+" es el ganador");
        
        if(sumaValores(dealer.getCartasEnMano()) > 21 && sumaValores(jugador.getCartasEnMano()) <= 21)
            System.out.println("Juego 1:"+jugador.getNombre()+" es el ganador");

        if(sumaValores(dealer.getCartasEnMano()) <= 21 && sumaValores(jugador.getCartasEnMano()) <= 21){
            if(sumaValores(dealer.getCartasEnMano()) == sumaValores(jugador.getCartasEnMano()))
                System.out.println("Juego 1:Empate");
            else{
                if(sumaValores(dealer.getCartasEnMano()) > sumaValores(jugador.getCartasEnMano()))
                    System.out.println("Juego 1:"+dealer.getNombre()+" es el ganador");
            else
                    System.out.println("Juego 1:"+jugador.getNombre()+" es el ganador");
            }
            
        }
        //juego 2
        if(sumaValores(dealer.getCartasEnMano()) > 21 && sumaValores(jugador.getDividir()) > 21)
            System.out.println("Juego 2:Ambos se pasaron");
        
        if(sumaValores(dealer.getCartasEnMano()) <= 21 && sumaValores(jugador.getDividir()) > 21)
            System.out.println("Juego 2:"+dealer.getNombre()+" es el ganador");
        
        if(sumaValores(dealer.getCartasEnMano()) > 21 && sumaValores(jugador.getDividir()) <= 21)
            System.out.println("Juego 2:"+jugador.getNombre()+" es el ganador");

        if(sumaValores(dealer.getCartasEnMano()) <= 21 && sumaValores(jugador.getDividir()) <= 21){
            if(sumaValores(dealer.getCartasEnMano()) == sumaValores(jugador.getDividir()))
                System.out.println("Juego 2:Empate");
            else{
                if(sumaValores(dealer.getCartasEnMano()) > sumaValores(jugador.getDividir()))
                    System.out.println("Juego 2:"+dealer.getNombre()+" es el ganador");
            else
                    System.out.println("Juego 2:"+jugador.getNombre()+" es el ganador");
            }
            
        }  

        Pantalla.esperarTecla();
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


    private void imprimirCarta(Jugador jugador){
        int s = jugador.getCartasEnMano().size(); 
        int d = jugador.getDividir().size();
        
        for(int i=0;i<s;i++){
            System.out.print("┌─────────┐");
        }
        
        System.out.print("         ");
        
        for(int i=0;i<d;i++){
            System.out.print("┌─────────┐");
        }

        System.out.println();

        for(Carta c : jugador.getCartasEnMano()){
            System.out.print("│"+c.getNumero()+"        │");
        }
        
        System.out.print("         ");

        for(Carta c : jugador.getDividir()){
            System.out.print("│"+c.getNumero()+"        │");
        }

        System.out.println();

        for(int i=0;i<s;i++){
            System.out.print("│         │");
        }

        System.out.print("         ");

        for(int i=0;i<d;i++){
            System.out.print("│         │");
        }

        System.out.println();

        for(Carta c : jugador.getCartasEnMano()){
            System.out.print("│    "+c.getTipo()+"    │");
        }

        System.out.print("         ");

        for(Carta c : jugador.getDividir()){
            System.out.print("│    "+c.getTipo()+"    │");
        }

        System.out.println();
        
        for(int i=0;i<s;i++){
            System.out.print("│         │");
        }
        
        System.out.print("         ");
       
        for(int i=0;i<d;i++){
            System.out.print("│         │");
        }
        
        System.out.println();

        for(Carta c : jugador.getCartasEnMano()){
            System.out.print("│        "+c.getNumero()+"|");
        }
        
        System.out.print("         ");

        for(Carta c : jugador.getDividir()){
            System.out.print("│        "+c.getNumero()+"|");
        }
        
        System.out.println();
        
        for(int i=0;i<s;i++){
            System.out.print("└─────────┘");
        }
        
        System.out.print("         ");

        for(int i=0;i<d;i++){
            System.out.print("└─────────┘");
        }

    }
    
    private void imprimirCartaAtras(Dealer dealer){
        System.out.print("┌─────────┐┌─────────┐\n|"+dealer.getCartasEnMano().get(0).getNumero()+"        ││░░░░░░░░░|\n│         ││░░░░░░░░░|\n│    "+dealer.getCartasEnMano().get(0).getTipo()+"    ││░░░░░░░░░|\n│         ││░░░░░░░░░|\n│        "+dealer.getCartasEnMano().get(0).getNumero()+"|│░░░░░░░░░|\n└─────────┘└─────────┘\n");
        System.out.println(dealer.getNombre()+":"+dealer.getCartasEnMano().get(0).getValor());
    }

    private String espaciosPuntos(ArrayList<Carta> cartas){
        String espacios = "";
        int s = cartas.size();
        
        for(int i=0;i<s;i++){
            espacios = espacios + "           ";
        }

        
        return espacios;
    }

    private void secuenciaRepartir(Dealer dealer, Jugador jugador, int i){
        if(i==0){
            System.out.print("\n\n\n\n\n\n\n\n");
            imprimirCarta(jugador.getCartasEnMano());
            System.out.println("\n"+jugador.getNombre()+":"+sumaValores(jugador.getCartasEnMano()));
            Pantalla.esperar(1000);
            Pantalla.borrarPantalla();
        }
        
        if(i==1){
            imprimirCarta(dealer.getCartasEnMano());
            System.out.println("\n"+dealer.getNombre()+":"+dealer.getCartasEnMano().get(0).getValor());
            imprimirCarta(jugador.getCartasEnMano());
            System.out.println("\n"+jugador.getNombre()+":"+sumaValores(jugador.getCartasEnMano()));
            Pantalla.esperar(1000);
            Pantalla.borrarPantalla();
        }
        
        if(i==2){
            imprimirCarta(dealer.getCartasEnMano());
            System.out.println("\n"+dealer.getNombre()+":"+dealer.getCartasEnMano().get(0).getValor());
            imprimirCarta(jugador.getCartasEnMano());
            System.out.println("\n"+jugador.getNombre()+":"+sumaValores(jugador.getCartasEnMano()));
            Pantalla.esperar(1000);
            Pantalla.borrarPantalla();
        }
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

    public void cambiarValorDeA11(){
        setValor(11);
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
    private ArrayList<Carta> dividir= new ArrayList<>();
    
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

    public ArrayList<Carta> getDividir() {
        return dividir;
    }
    
    public void cambiarPlata(double p){
        if(p>0)
            this.plata += p;
        else   
            this.plata -= p;
    }

    public void setDividir(Carta c) {
        this.dividir.add(c);
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

class Pantalla{

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

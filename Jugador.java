import java.util.ArrayList;

public class Jugador extends Dealer{
    
    private double plata;
    private double apuesta;
    private final ArrayList<Carta> dividir= new ArrayList<>();
    
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
        this.plata += p;
    }

    public void setDividir(Carta c) {
        this.dividir.add(c);
    }

    public double getApuesta() {
        return apuesta;
    }

    public void setApuesta(Double a) {
        this.apuesta=a;
    }

    
    
}

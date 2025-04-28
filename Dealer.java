import java.util.ArrayList;

public class Dealer{

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
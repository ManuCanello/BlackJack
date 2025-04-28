public class Carta{

    private String numero;
    private int valor;
    private String tipo;
    private String direccion;

    public Carta(String n, int v, String t,String d){
        setNumero(n);
        setValor(v);
        setTipo(t);
        setDireccion(d);
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}

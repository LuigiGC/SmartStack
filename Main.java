public class Main {
    public static void main(String[] args) {
        Armazem estacionamento = new Armazem(30, 5);
        Interface menu = new Interface(estacionamento);
        menu.iniciar();
    }
}
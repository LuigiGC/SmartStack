public class Main {
    public static void main(String[] args) {
        // 3 blocos (A, B, C), 10 corredores por bloco, 5 vagas por corredor
        Armazem estacionamento = new Armazem(3, 10, 5);
        Interface menu = new Interface(estacionamento);
        menu.iniciar();
    }
}
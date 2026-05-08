public class Pilha {
    private int capacidade;
    private int topo;
    private String[] elementos;

    public Pilha(int capacidade) {
        this.capacidade = capacidade;
        this.topo = -1;
        this.elementos = new String[capacidade];
    }

    public boolean estaVazia() {
        return topo == -1;
    }

    public boolean estaCheia() {
        return topo == capacidade - 1;
    }

    public int tamanho() {
        return topo + 1;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void empilhar(String elemento) {
        if (estaCheia()) {
            throw new IllegalStateException("Pilha cheia! Não é possível empilhar.");
        }
        topo++;
        elementos[topo] = elemento;
    }

    public String desempilhar() {
        if (estaVazia()) {
            throw new IllegalStateException("Pilha vazia! Não é possível desempilhar.");
        }
        String elemento = elementos[topo];
        elementos[topo] = null;
        topo--;
        return elemento;
    }

    public String topo() {
        if (estaVazia()) {
            return null;
        }
        return elementos[topo];
    }

    public String[] elementos() {
        String[] snapshot = new String[tamanho()];
        for (int i = 0; i < snapshot.length; i++) {
            snapshot[i] = elementos[i];
        }
        return snapshot;
    }
}

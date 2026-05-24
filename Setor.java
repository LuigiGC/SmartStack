public class Setor {
    private String id;
    private Pilha vagas;
    public Setor(String id, int capacidade) {
        this.id = id;
        this.vagas = new Pilha(capacidade);
    }
    public String getId() {
        return id;
    }
    public Pilha getPilha() {
        return vagas;
    }
}
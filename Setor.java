public class Setor {
    private String corredor;
    private int prateleira;
    private String produto;

    public Setor(String corredor, int prateleira) {
        this.corredor = corredor;
        this.prateleira = prateleira;
        this.produto = "Disponível"; 
    }

    public String getCoordenadas() {
        return "Corredor " + corredor + ", Prateleira " + prateleira + ", Produto: " + produto;
    }

    public String getCorredor() {
        return corredor;
    }

    public String getProduto() {
        return produto;
    }

    public boolean estaDisponivel() {
        return "Disponível".equals(produto);
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }
}
public class Main {
    public static void main(String[] args) {
        Armazem meuEstoque = new Armazem(2, 2);
        Setor[][] mapa = meuEstoque.getSetores();
        
        mapa[0][0].setProduto("Caixa de parafusos");
        mapa[3][1].setProduto("Ferramentas");

        System.out.println(meuEstoque.getEstrutura());


        System.out.println();
        System.out.println("Corredor A tem prateleira disponível? "
            + meuEstoque.corredorTemPrateleiraDisponivel("A"));
        System.out.println("Corredor A está lotado? "
            + meuEstoque.corredorEstaLotado("A"));
        System.out.println("Corredor B tem prateleira disponível? "
            + meuEstoque.corredorTemPrateleiraDisponivel("B"));
    }
}
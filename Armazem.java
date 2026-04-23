public class Armazem {
    private Setor[][] setores;
    private int linhas;
    private int colunas;

    public Armazem(int quantidadeCorredores, int prateleirasPorCorredor) {
        this.linhas = quantidadeCorredores;
        this.colunas = prateleirasPorCorredor;
        this.setores = new Setor[linhas][colunas];
        
        inicializarArmazem();
    }

    private void inicializarArmazem() {
        for (int i = 0; i < linhas; i++) {
            String nomeCorredor;

            if (i < 26) {
                nomeCorredor = String.valueOf((char) ('A' + i));
            } else {
                char primeiraLetra = (char) ('A' + (i / 26) - 1);
                char segundaLetra = (char) ('A' + (i % 26));
                nomeCorredor = "" + primeiraLetra + segundaLetra;
            }

            for (int j = 0; j < colunas; j++) {
                setores[i][j] = new Setor(nomeCorredor, j + 1);
            }
        }
    }

    private int buscarIndiceCorredor(String corredor) {
        if (corredor == null || corredor.isBlank()) {
            throw new IllegalArgumentException("Nome do corredor inválido.");
        }

        String alvo = corredor.trim().toUpperCase();

        for (int i = 0; i < linhas; i++) {
            if (setores[i][0].getCorredor().equals(alvo)) {
                return i;
            }
        }

        throw new IllegalArgumentException("Corredor " + alvo + " não encontrado.");
    }

    public boolean corredorTemPrateleiraDisponivel(String corredor) {
        int indiceCorredor = buscarIndiceCorredor(corredor);

        for (int j = 0; j < colunas; j++) {
            if (setores[indiceCorredor][j].estaDisponivel()) {
                return true;
            }
        }

        return false;
    }

    public boolean corredorEstaLotado(String corredor) {
        return !corredorTemPrateleiraDisponivel(corredor);
    }

    public String getEstrutura() {
        StringBuilder estrutura = new StringBuilder();
        estrutura.append("--- Estrutura do Armazém Criada ---\n");

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                estrutura.append("[")
                    .append(setores[i][j].getCoordenadas())
                    .append("] ");
            }
            estrutura.append("\n");
        }

        return estrutura.toString();
    }

    public Setor[][] getSetores() {
        return setores;
    }
}
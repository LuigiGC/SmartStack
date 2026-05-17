public class Armazem {
    private Pilha[] corredores;
    private int quantidadeCorredores;
    private int capacidadePorCorredor;
    private int pausaMs;

    public Armazem(int quantidadeCorredores, int capacidadePorCorredor) {
        this.quantidadeCorredores = quantidadeCorredores;
        this.capacidadePorCorredor = capacidadePorCorredor;
        this.pausaMs = 2000;
        this.corredores = new Pilha[quantidadeCorredores];

        for (int i = 0; i < quantidadeCorredores; i++) {
            corredores[i] = new Pilha(capacidadePorCorredor);
        }
    }

    private int buscarIndiceCorredor(String corredor) {
        if (corredor == null || corredor.isBlank()) {
            throw new IllegalArgumentException("Nome do corredor inválido.");
        }

        String alvo = corredor.trim().toUpperCase();
        for (int i = 0; i < quantidadeCorredores; i++) {
            if (getNomeCorredor(i).equals(alvo)) {
                return i;
            }
        }

        throw new IllegalArgumentException("Corredor " + alvo + " não encontrado.");
    }

    private String getNomeCorredor(int indice) {
        if (indice < 26) {
            return String.valueOf((char) ('A' + indice));
        }

        char primeiraLetra = (char) ('A' + (indice / 26) - 1);
        char segundaLetra = (char) ('A' + (indice % 26));
        return "" + primeiraLetra + segundaLetra;
    }

    private void pausaCurta() {
        try {
            Thread.sleep(pausaMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean corredorTemVaga(String corredor) {
        int indice = buscarIndiceCorredor(corredor);
        return !corredores[indice].estaCheia();
    }

    public boolean corredorEstaLotado(String corredor) {
        return !corredorTemVaga(corredor);
    }

    public boolean placaJaExiste(String placa) {
        if (placa == null || placa.isBlank()) {
            return false;
        }

        String alvo = placa.trim().toUpperCase();
        for (int i = 0; i < quantidadeCorredores; i++) {
            String[] elementos = corredores[i].elementos();
            for (String elemento : elementos) {
                if (alvo.equals(elemento)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String adicionarCarro(String corredor, String placa) {
        if (placa == null || placa.isBlank()) {
            return "Placa inválida.";
        }

        String placaNormalizada = placa.trim().toUpperCase();
        if (placaJaExiste(placaNormalizada)) {
            return "Placa já cadastrada no estacionamento.";
        }

        int indice = buscarIndiceCorredor(corredor);
        Pilha pilha = corredores[indice];
        if (pilha.estaCheia()) {
            return "Corredor " + getNomeCorredor(indice) + " está lotado.";
        }

        pilha.empilhar(placaNormalizada);
        return "Carro " + placaNormalizada + " estacionado no corredor " + getNomeCorredor(indice) + ".";
    }

    public String removerCarro(String corredor, String placa) {
        if (placa == null || placa.isBlank()) {
            return "Placa inválida.";
        }

        int indice = buscarIndiceCorredor(corredor);
        Pilha principal = corredores[indice];

        if (principal.estaVazia()) {
            return "Corredor " + getNomeCorredor(indice) + " está vazio.";
        }

        String placaNormalizada = placa.trim().toUpperCase();
        Pilha auxiliar = new Pilha(capacidadePorCorredor);
        boolean encontrado = false;

        while (!principal.estaVazia()) {
            String atual = principal.desempilhar();
            System.out.println("Removendo carro " + atual + "...");
            pausaCurta();

            if (atual.equals(placaNormalizada)) {
                encontrado = true;
                break;
            }

            auxiliar.empilhar(atual);
        }

        while (!auxiliar.estaVazia()) {
            String atual = auxiliar.desempilhar();
            System.out.println("Recolocando carro " + atual + "...");
            pausaCurta();
            principal.empilhar(atual);
        }

        if (!encontrado) {
            return "Placa " + placaNormalizada + " não encontrada no corredor " + getNomeCorredor(indice) + ".";
        }

        return "Carro " + placaNormalizada + " removido do corredor " + getNomeCorredor(indice) + ".";
    }

    public String buscarCarro(String placa) {
        if (placa == null || placa.isBlank()) {
            return "Placa inválida.";
        }

        String alvo = placa.trim().toUpperCase();
        for (int i = 0; i < quantidadeCorredores; i++) {
            String[] elementos = corredores[i].elementos();
            for (int j = 0; j < elementos.length; j++) {
                if (alvo.equals(elementos[j])) {
                    int posicao = j + 1;
                    int nivelDoTopo = elementos.length - j;
                    return "Placa " + alvo + " está no corredor " + getNomeCorredor(i)
                        + " (posição " + posicao + " da base, " + nivelDoTopo + " do topo).";
                }
            }
        }

        return "Placa " + alvo + " não encontrada.";
    }

    public String getStatusCorredores() {
        StringBuilder builder = new StringBuilder();
        builder.append("--- Status dos Corredores ---\n");

        for (int i = 0; i < quantidadeCorredores; i++) {
            Pilha pilha = corredores[i];
            builder.append("Corredor ")
                .append(getNomeCorredor(i))
                .append(" - ")
                .append(pilha.tamanho())
                .append("/")
                .append(pilha.getCapacidade())
                .append(pilha.estaCheia() ? " (Lotado)" : " (Disponível)")
                .append("\n");
        }

        return builder.toString();
    }

    public String getCarrosNoCorredor(String corredor) {
        int indice = buscarIndiceCorredor(corredor);
        Pilha pilha = corredores[indice];
        String[] elementos = pilha.elementos();

        if (elementos.length == 0) {
            return "Corredor " + getNomeCorredor(indice) + " está vazio.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Carros no corredor ")
            .append(getNomeCorredor(indice))
            .append(" (base -> topo):\n");

        for (int i = 0; i < elementos.length; i++) {
            builder.append(i + 1)
                .append(" - ")
                .append(elementos[i])
                .append("\n");
        }

        return builder.toString();
    }

    public String getEstatisticasGerais() {
        int totalVagas = quantidadeCorredores * capacidadePorCorredor;
        int vagasUtilizadas = 0;
        for (int i = 0; i < quantidadeCorredores; i++) {
            vagasUtilizadas += corredores[i].tamanho();
        }
        double porcentagem = 0.0;
        if (totalVagas > 0) {
            porcentagem = ((double) vagasUtilizadas / totalVagas) * 100.0;
        }
        int vagasDisponiveis = totalVagas - vagasUtilizadas;
        
        return String.format("\n--- Vagas ---\nTotal de Vagas: %d\nVagas Utilizadas: %d\nVagas Disponíveis: %d\nPercentual de Lotação: %.2f%%", 
                             totalVagas, vagasUtilizadas, vagasDisponiveis, porcentagem);
    }
}
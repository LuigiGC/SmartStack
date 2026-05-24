public class Armazem {
    private Setor[][] mapa;
    private int quantidadeBlocos;
    private int corredoresPorBloco;
    private int capacidadePorCorredor;
    private int pausaMs;

    public Armazem(int quantidadeBlocos, int corredoresPorBloco, int capacidadePorCorredor) {
        this.quantidadeBlocos = quantidadeBlocos;
        this.corredoresPorBloco = corredoresPorBloco;
        this.capacidadePorCorredor = capacidadePorCorredor;
        this.pausaMs = 2000;
        this.mapa = new Setor[quantidadeBlocos][corredoresPorBloco];

        for (int i = 0; i < quantidadeBlocos; i++) {
            for (int j = 0; j < corredoresPorBloco; j++) {
                String idSetor = getNomeBloco(i) + (j + 1);
                mapa[i][j] = new Setor(idSetor, capacidadePorCorredor);
            }
        }
    }

    private String getNomeBloco(int indice) {
        if (indice < 26) {
            return String.valueOf((char) ('A' + indice));
        }
        char primeiraLetra = (char) ('A' + (indice / 26) - 1);
        char segundaLetra = (char) ('A' + (indice % 26));
        return "" + primeiraLetra + segundaLetra;
    }

    private Setor buscarSetor(String setorId) {
        if (setorId == null || setorId.isBlank()) {
            throw new IllegalArgumentException("Identificador de setor inválido.");
        }

        String alvo = setorId.trim().toUpperCase();
        for (int i = 0; i < quantidadeBlocos; i++) {
            for (int j = 0; j < corredoresPorBloco; j++) {
                if (mapa[i][j].getId().equals(alvo)) {
                    return mapa[i][j];
                }
            }
        }

        throw new IllegalArgumentException("Setor " + alvo + " não encontrado.");
    }

    private void pausaCurta() {
        try {
            Thread.sleep(pausaMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean setorTemVaga(String setorId) {
        Setor setor = buscarSetor(setorId);
        return !setor.getPilha().estaCheia();
    }

    public boolean placaJaExiste(String placa) {
        if (placa == null || placa.isBlank()) {
            return false;
        }

        String alvo = placa.trim().toUpperCase();
        for (int i = 0; i < quantidadeBlocos; i++) {
            for (int j = 0; j < corredoresPorBloco; j++) {
                String[] elementos = mapa[i][j].getPilha().elementos();
                for (String elemento : elementos) {
                    if (alvo.equals(elemento)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String adicionarCarro(String setorId, String placa) {
        if (placa == null || placa.isBlank()) {
            return "Placa inválida.";
        }

        String placaNormalizada = placa.trim().toUpperCase();
        if (placaJaExiste(placaNormalizada)) {
            return "Placa já cadastrada no estacionamento.";
        }

        Setor setor;
        try {
            setor = buscarSetor(setorId);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        
        Pilha pilha = setor.getPilha();
        if (pilha.estaCheia()) {
            return "Setor " + setor.getId() + " está lotado.";
        }

        pilha.empilhar(placaNormalizada);
        return "Carro " + placaNormalizada + " estacionado no setor " + setor.getId() + ".";
    }

    public String removerCarro(String placa) {
        if (placa == null || placa.isBlank()) {
            return "Placa inválida.";
        }

        String placaNormalizada = placa.trim().toUpperCase();
        Setor setorEncontrado = null;

        
        for (int i = 0; i < quantidadeBlocos; i++) {
            for (int j = 0; j < corredoresPorBloco; j++) {
                String[] elementos = mapa[i][j].getPilha().elementos();
                for (String elemento : elementos) {
                    if (placaNormalizada.equals(elemento)) {
                        setorEncontrado = mapa[i][j];
                        break;
                    }
                }
                if (setorEncontrado != null) break;
            }
            if (setorEncontrado != null) break;
        }

        if (setorEncontrado == null) {
            return "Placa " + placaNormalizada + " não encontrada no estacionamento.";
        }
        
        Pilha principal = setorEncontrado.getPilha();

        System.out.println("Carro encontrado no setor " + setorEncontrado.getId() + ". Iniciando manobra de remoção...");
        
        Pilha auxiliar = new Pilha(capacidadePorCorredor);
        boolean encontrado = false;

        while (!principal.estaVazia()) {
            String atual = principal.desempilhar();
            System.out.println("Movendo carro " + atual + "...");
            pausaCurta();

            if (atual.equals(placaNormalizada)) {
                encontrado = true;
                break;
            }

            auxiliar.empilhar(atual);
        }

        while (!auxiliar.estaVazia()) {
            String atual = auxiliar.desempilhar();
            System.out.println("Devolvendo carro " + atual + " para a vaga...");
            pausaCurta();
            principal.empilhar(atual);
        }

        return "Carro " + placaNormalizada + " removido com sucesso do setor " + setorEncontrado.getId() + ".";
    }

    public String buscarCarro(String placa) {
        if (placa == null || placa.isBlank()) {
            return "Placa inválida.";
        }

        String alvo = placa.trim().toUpperCase();
        for (int i = 0; i < quantidadeBlocos; i++) {
            for (int j = 0; j < corredoresPorBloco; j++) {
                String[] elementos = mapa[i][j].getPilha().elementos();
                for (int k = 0; k < elementos.length; k++) {
                    if (alvo.equals(elementos[k])) {
                        int posicao = k + 1;
                        int nivelDoTopo = elementos.length - k;
                        return "Placa " + alvo + " está no setor " + mapa[i][j].getId() + ".";
                    }
                }
            }
        }

        return "Placa " + alvo + " não encontrada.";
    }

    public String getStatusCorredores() {
        StringBuilder builder = new StringBuilder();
        builder.append("--- Status dos Setores ---\n");

        for (int i = 0; i < quantidadeBlocos; i++) {
            for (int j = 0; j < corredoresPorBloco; j++) {
                Setor setor = mapa[i][j];
                Pilha pilha = setor.getPilha();
                builder.append("Setor ")
                    .append(setor.getId())
                    .append(" - ")
                    .append(pilha.tamanho())
                    .append("/")
                    .append(pilha.getCapacidade())
                    .append(pilha.estaCheia() ? " (Lotado)" : " (Disponível)")
                    .append("\n");
            }
        }

        return builder.toString();
    }

    public String getStatusBlocos() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n--- Blocos Disponíveis ---\n");
        for (int i = 0; i < quantidadeBlocos; i++) {
            int vagasUtilizadas = 0;
            int totalVagas = corredoresPorBloco * capacidadePorCorredor;
            for (int j = 0; j < corredoresPorBloco; j++) {
                vagasUtilizadas += mapa[i][j].getPilha().tamanho();
            }
            double ocupacao = totalVagas > 0 ? ((double) vagasUtilizadas / totalVagas) * 100.0 : 0.0;
            builder.append("Bloco ").append(getNomeBloco(i))
                   .append(String.format(" - Ocupação: %.1f%%\n", ocupacao));
        }
        return builder.toString();
    }

    public String getStatusSetoresDoBloco(String nomeBloco) {
        if (nomeBloco == null || nomeBloco.isBlank()) {
            return "Nome do bloco inválido.";
        }
        
        String alvo = nomeBloco.trim().toUpperCase();
        int indiceBloco = -1;
        for (int i = 0; i < quantidadeBlocos; i++) {
            if (getNomeBloco(i).equals(alvo)) {
                indiceBloco = i;
                break;
            }
        }

        if (indiceBloco == -1) {
            return "Bloco " + alvo + " não encontrado.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("\n--- Corredores do Bloco ").append(getNomeBloco(indiceBloco)).append(" ---\n");
        for (int j = 0; j < corredoresPorBloco; j++) {
            Setor setor = mapa[indiceBloco][j];
            Pilha pilha = setor.getPilha();
            builder.append(setor.getId())
                   .append(" - ")
                   .append(pilha.tamanho())
                   .append("/")
                   .append(pilha.getCapacidade())
                   .append(pilha.estaCheia() ? " (Lotado)" : " (Disponível)")
                   .append("\n");
        }
        return builder.toString();
    }

    public String getCarrosNoCorredor(String setorId) {
        Setor setor = buscarSetor(setorId);
        Pilha pilha = setor.getPilha();
        String[] elementos = pilha.elementos();

        if (elementos.length == 0) {
            return "Setor " + setor.getId() + " está vazio.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Carros no setor ")
            .append(setor.getId())
            .append(":\n");

        for (int i = 0; i < elementos.length; i++) {
            builder.append(i + 1)
                .append(" - ")
                .append(elementos[i])
                .append("\n");
        }

        return builder.toString();
    }

    public String getEstatisticasGerais() {
        int totalVagas = quantidadeBlocos * corredoresPorBloco * capacidadePorCorredor;
        int vagasUtilizadas = 0;
        
        StringBuilder setoresLotados = new StringBuilder();
        setoresLotados.append("\nSetores acima de 80% da capacidade:\n");
        boolean temSetorLotado = false;

        for (int i = 0; i < quantidadeBlocos; i++) {
            for (int j = 0; j < corredoresPorBloco; j++) {
                Pilha pilha = mapa[i][j].getPilha();
                int tamanho = pilha.tamanho();
                vagasUtilizadas += tamanho;
                
                double ocupacaoAtual = ((double) tamanho / pilha.getCapacidade()) * 100.0;
                if (ocupacaoAtual > 80.0) {
                    temSetorLotado = true;
                    setoresLotados.append(" - Setor ").append(mapa[i][j].getId())
                                  .append(String.format(" (%.1f%% ocupado)\n", ocupacaoAtual));
                }
            }
        }
        
        if (!temSetorLotado) {
            setoresLotados.append(" - Nenhum setor com ocupação acima de 80%.\n");
        }

        double porcentagem = 0.0;
        if (totalVagas > 0) {
            porcentagem = ((double) vagasUtilizadas / totalVagas) * 100.0;
        }
        int vagasDisponiveis = totalVagas - vagasUtilizadas;
        
        return String.format("\n--- Vagas ---\nTotal de Vagas: %d\nVagas Utilizadas: %d\nVagas Disponíveis: %d\nLotação Geral: %.2f%%\n", 
                             totalVagas, vagasUtilizadas, vagasDisponiveis, porcentagem) + setoresLotados.toString();
    }
}
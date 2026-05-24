public class Interface {
	private final Armazem estacionamento;
	private final java.util.Scanner scanner;

	public Interface(Armazem estacionamento) {
		this.estacionamento = estacionamento;
		this.scanner = new java.util.Scanner(System.in);
	}

	public void iniciar() {
		boolean executando = true;

		while (executando) {
			System.out.println();
			System.out.println("=== Bem vindo ao SmartStack! Escolha uma opção a baixo! ===");
			System.out.println("1 - Adicionar carro");
			System.out.println("2 - Remover carro");
			System.out.println("3 - Buscar carro por placa");
			System.out.println("4 - Mostrar status dos setores");
			System.out.println("0 - Sair");
			System.out.print("Escolha uma opcao: ");

			String opcao = scanner.nextLine().trim();

			switch (opcao) {
				case "1":
					adicionarCarro();
					break;
				case "2":
					removerCarro();
					break;
				case "3":
					buscarCarro();
					break;
				case "4":
					mostrarStatus();
					break;
				case "0":
					System.out.println("Obrigado por usar o estacionamento. Ate logo!");
					executando = false;
					break;
				default:
					System.out.println("Opcao invalida.");
					break;
			}
		}

		scanner.close();
	}

	private void adicionarCarro() {
		System.out.print("Digite a placa: ");
		String placaAdicionar = scanner.nextLine();
		
		System.out.println(estacionamento.getStatusBlocos());
		System.out.print("Digite o bloco desejado (ex: A, B, C): ");
		String bloco = scanner.nextLine();
		
		String statusCorredores = estacionamento.getStatusSetoresDoBloco(bloco);
		if (statusCorredores.contains("não encontrado") || statusCorredores.contains("inválido")) {
			System.out.println(statusCorredores);
			return;
		}
		
		System.out.println(statusCorredores);
		System.out.print("Digite o setor escolhido (ex: " + bloco.trim().toUpperCase() + "1): ");
		String corredorAdicionar = scanner.nextLine();
		System.out.println(estacionamento.adicionarCarro(corredorAdicionar, placaAdicionar));
	}

	private void removerCarro() {
		System.out.print("Digite a placa a remover: ");
		String placaRemover = scanner.nextLine();
		System.out.println(estacionamento.removerCarro(placaRemover));
	}

	private void buscarCarro() {
		System.out.print("Digite a placa para buscar: ");
		String placaBuscar = scanner.nextLine();
		System.out.println(estacionamento.buscarCarro(placaBuscar));
	}

	private void mostrarStatus() {
		System.out.println(estacionamento.getStatusCorredores());
		System.out.println(estacionamento.getEstatisticasGerais());
		System.out.print("\nDeseja ver os carros de um setor especifico? (S/N): ");
		String resposta = scanner.nextLine().trim();
		if (resposta.equalsIgnoreCase("S")) {
			System.out.print("Digite o setor (ex: A1): ");
			String corredorDetalhe = scanner.nextLine();
			try {
				System.out.println(estacionamento.getCarrosNoCorredor(corredorDetalhe));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

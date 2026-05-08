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
			System.out.println("=== Bem vindo ao estacionamento! Escolha uma opção a baixo! ===");
			System.out.println("1 - Adicionar carro");
			System.out.println("2 - Remover carro");
			System.out.println("3 - Buscar carro por placa");
			System.out.println("4 - Mostrar status dos corredores");
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
		System.out.println(estacionamento.getStatusCorredores());
		System.out.print("Digite a placa: ");
		String placaAdicionar = scanner.nextLine();
		System.out.print("Digite o corredor: ");
		String corredorAdicionar = scanner.nextLine();
		System.out.println(estacionamento.adicionarCarro(corredorAdicionar, placaAdicionar));
	}

	private void removerCarro() {
		System.out.print("Digite a placa a remover: ");
		String placaRemover = scanner.nextLine();
		System.out.print("Digite o corredor: ");
		String corredorRemover = scanner.nextLine();
		System.out.println(estacionamento.removerCarro(corredorRemover, placaRemover));
	}

	private void buscarCarro() {
		System.out.print("Digite a placa para buscar: ");
		String placaBuscar = scanner.nextLine();
		System.out.println(estacionamento.buscarCarro(placaBuscar));
	}

	private void mostrarStatus() {
		System.out.println(estacionamento.getStatusCorredores());
		System.out.print("Deseja ver os carros de um corredor? (S/N): ");
		String resposta = scanner.nextLine().trim();
		if (resposta.equalsIgnoreCase("S")) {
			System.out.print("Digite o corredor: ");
			String corredorDetalhe = scanner.nextLine();
			try {
				System.out.println(estacionamento.getCarrosNoCorredor(corredorDetalhe));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

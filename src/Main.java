import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // Objeto Scanner para ler da entrada padrão
		long tin, tconc, tout, tini, tfin;

		tini = System.currentTimeMillis();
		sc.nextInt(); // Ignora dimensão N do mapa
		sc.nextInt(); // Ignora dimensão M do mapa

		int nclient = sc.nextInt(); // Recebe o número de clientes

		Central central = new Central(nclient); // Inicializa o monitor
		List<Thread> clients_taxis = new ArrayList<Thread>(); // Aloca lista para guardar todas as threads

		int x0, y0, x, y; // Coordenadas (x0, y0) de origem e (x, y) de destino de cada cliente
		Client client;

		for (int i = 0; i < nclient; i++) {
			// Leitura de (x0, y0) e (x, y) de cada cliente
			x0 = sc.nextInt();
			y0 = sc.nextInt();
			x = sc.nextInt();
			y = sc.nextInt();

			client = new Client(x0, y0, x, y, central); // Inicialização de um novo cliente
			clients_taxis.add(new Thread(client)); // Criação de uma nova thread a partir do objeto 'client'
		}

		int ntaxi = sc.nextInt(); // Recebe o número de táxis
		Taxi taxi;
		for (int i = 0; i < ntaxi; i++) {
			// Leitura da posição inicial (x0, y0) de cada táxi
			x0 = sc.nextInt();
			y0 = sc.nextInt();

			taxi = new Taxi(x0, y0, central); // Inicialização de um novo táxi
			clients_taxis.add(new Thread(taxi)); // Criação de uma nova thread a partir do objeto 'taxi'
		}

		sc.close(); // Destrói o objeto Scanner (não há mais nada a ser lido da entrada)

		Collections.reverse(clients_taxis); // Inverte a lista de threads

		tfin = System.currentTimeMillis();

		tin = tfin - tini;

		tini = System.currentTimeMillis();
		// Lança todas as threads criadas
		for(Thread t: clients_taxis) {
			t.start();
		}

		// Aguarda o término das threads
		for(Thread t: clients_taxis) {
			try {
				t.join();
			} catch(InterruptedException e) {
				System.err.println("--Erro no método join()");
			}
		}

		tfin = System.currentTimeMillis();
		tconc = tfin - tini;

		tini = System.currentTimeMillis();
		// Imprime o relatório (saída) mantido pelo objeto 'central'
		central.printReport();
		tfin = System.currentTimeMillis();

		tout = tfin - tini;
		System.out.println("Tempo de execução da leitura de dados da entrada: " + tin + " ms");
		System.out.println("Tempo de execução do processamento concorrente: " + tconc + " ms");
		System.out.println("Tempo de execução da impressão da saída: " + tout + " ms");
		System.out.println("Tempo de execução total: " + (tin + tconc + tout) + " ms");
	}
}

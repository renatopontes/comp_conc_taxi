public class Client implements Runnable{
	private static int idcount = 0; // Variável que gera id's
	private int id; // Identificação do objeto
	private Coord origin, dest; // Coordenadas de origem e destino
	private Central central; // Referência para a instância de Central

	// Construtor
	// (x0, y0) é a posição inicial do objeto
	// (x, y) é a posição de destino do objeto
	// _central é a referência para a instância de Central
	public Client(int x0, int y0, int x, int y, Central _central) {
		id = ++idcount;
		origin = new Coord(x0, y0);
		dest = new Coord(x, y);
		central = _central;
	}

	// Método executado por cada thread Client
	public void run() {
		// Cada cliente faz um pedido, é atendido e termina
		central.makeRequest(this);
	}

	// Getter: acessa 'origin'
	public Coord getOrigin() {
		return origin;
	}

	// Getter: acessa 'dest'
	public Coord getDest() {
		return dest;
	}

	// Getter: acessa 'id'
	public int getId() {
		return id;
	}

}

import java.util.ArrayList;
import java.util.List;

public class Central {
    private int nclient; // Número total de clientes ainda não atendidos
    private int clientlog; // Número de clientes que iniciaram um pedido, mas não foram associados a um táxi
    private List<Taxi> ltaxis; // Lista de táxis disponíveis
    private Coord[] finalcoord;

    // Construtor
    // _nclient é o número de clientes lidos na entrada
    public Central(int _nclient) {
        nclient = _nclient; // Inicializa o número de clientes a serem atendidos
        ltaxis = new ArrayList<Taxi>(100); // Aloca memória para a lista de táxis disponíveis
        finalcoord = new Coord[100]; // Aloca memória para o relatório de posições finais dos táxis
    }

    // Calcula a menor distância Manhattan entre 'client' e os táxis disponíveis (objetos Taxi em 'ltaxis')
    // Retorna o táxi disponível que tem a menor distância Manhattan até 'client'
    private Taxi chooseTaxi(Client client) {
        Taxi chosenTaxi = null; // Táxi escolhido
        int dist; // Váriavel auxiliar para guardar distâncias
        double mindist = Double.POSITIVE_INFINITY; // Distância mínima, inicializada com +∞

        // Para cada táxi disponível
        for(Taxi t: ltaxis) {
            // Calculo a distância entre 't' e 'client'
            dist = client.getOrigin().distanceTo(t.getCoord());

            // Se for a menor até agora, atualizo o táxi escolhido e a menor distância
            if (dist < mindist) {
                mindist = dist;
                chosenTaxi = t;
            }
        }

        // Retornamos o táxi escolhido
        return chosenTaxi;
    }

    // Método usado pelos objetos Client para fazer pedido
    // client é o objeto Client que fez o pedido
    public synchronized void makeRequest(Client client) {
        clientlog++;

        System.out.println("Cliente #" + client.getId() + " pediu um táxi. Total de " + clientlog + " " + (clientlog != 1 ? "clientes" : "cliente") + " aguardando atendimento");
        try {
            while(ltaxis.isEmpty()){ // Se não há táxis disponíveis, o cliente deve esperar
                System.out.println("Cliente #" + client.getId() + " esperando disponibilidade de táxis...");
                wait();
            }

            Taxi taxi = chooseTaxi(client); // Retorna táxi disponível mais próximo do cliente
            ltaxis.remove(taxi); // 'taxi' é retirado da lista de disponíveis

            taxi.setCurrentclient(client); // As informações do cliente são passadas para o táxi escolhido
            notifyAll(); // notificar taxis de que um deles foi escolhido

            nclient--; // A central não precisa mais lidar com esse cliente
            clientlog--;
            System.out.println("O cliente #" + client.getId() + " será atendido pelo táxi #" + taxi.getId() +
                    "\n" + ltaxis.size() + " " + (ltaxis.size() != 1 ? "táxis disponíveis" : "táxi disponível") + " e " + clientlog + " " + (clientlog != 1 ? "clientes" : "cliente") + " aguardando atendimento");

        } catch (InterruptedException e) {
            System.err.println("--Erro no método makeRequest()");
        }
    }

    // Método utilizado pelos objetos Taxi para informar a Central que estão disponíveis.
    // 'taxi' é o objeto Taxi que informou que está disponível
    public synchronized void getRequest(Taxi taxi) {
        try {
            // Um táxi disponível não deve ter nenhum cliente associado a ele
            taxi.setCurrentclient(null);

            // Se todos os clientes já foram atendidos, não escolhemos um cliente para este táxi
            if (nclient == 0) return;

            // Acordar clientes que tentaram pedir taxi quando não existiam táxis disponíveis
            if (ltaxis.isEmpty())
                // Se a lista de táxis disponiveis está vazia, todos os táxis foram escolhidos e acordados
                // Logo, a fila do wait() só pode ter clientes nesse momento. Só um cliente poderá ser atendido
                notify();

            ltaxis.add(taxi); // Adicionamos este táxi a lista 'ltaxis'

            System.out.println("Táxi #" + taxi.getId() + " está disponível. Total de " + ltaxis.size() + " " + (ltaxis.size() != 1 ? "táxis disponíveis" : "táxi disponível") + ".");

            // O táxi espera a Central escolher um cliente
            while(taxi.getCurrentclient() == null){
                wait();

                // Se não há mais clientes para atender, retorno mesmo
                // que não tenha sido associado a um cliente. (Aqui, o táxi
                // pode ter sido associado ao último cliente)
                if (nclient == 0) return;
            }

            return;
        } catch (InterruptedException e) {
            System.err.println("--Erro no método getRequest()");
        }
    }

    // Método utilizado pelos objetos Taxi para relatar suas posições finais,
    // quando não há mais clientes para atender.
    // 'coord' é a coordenada final do táxi que chamou o método.
    public synchronized void report(int id, Coord coord) {
        // Adicionamos 'coord' a lista de posições finais, na posição id - 1.
        finalcoord[id - 1] = coord;
    }

    // Método auxiliar utilizado pela classe Principal para imprimir
    // o relatório (saída do programa) depois que todas as threads terminaram.
    public void printReport() {
    	// Cria o relatório
    	StringBuilder report = new StringBuilder("\n");

    	// Preenche relatório linha a linha
        for(Coord c: finalcoord) {
        	if (c == null) break;
        	report.append(c.getX() + " " + c.getY() + "\n");
        }

        // Garante uma linha em branco entre a saída e os tempos de execução
        report.append("\n");

        // Imprime a saída do programa
        System.out.print(report);
    }
}
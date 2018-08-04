public class Taxi implements Runnable {
    private static int idcount = 0; // Variável que gera id's
    private int id; // Identificação do objeto
    private Coord coord; // Coordenadas atuais
    private Central central; // Referência para a instância de Central
    // Informações do cliente que será atendido. Se esta variável é null,
    // não há cliente para ser atendido
    private Client currentclient;

    // Construtor
    // (x0, y0) é a posição inicial do objeto
    // _central é a referência para a instância de Central
    public Taxi(int x0, int y0, Central _central) {
        id = ++idcount;
        coord = new Coord(x0, y0);
        central = _central;
    }

    // Simula tempo de viagem até o cliente e do cliente até seu destino
    private void travel() {
        try {
            Thread.sleep((coord.distanceTo(currentclient.getOrigin())));
            System.out.println("Táxi #" + id + " alcançou o cliente #" + currentclient.getId());

            Thread.sleep(currentclient.getOrigin().distanceTo(currentclient.getDest()));
            System.out.println("Taxi #" + id + " chegou ao destino " +  currentclient.getDest() + " do cliente #" + currentclient.getId());
        } catch (InterruptedException e) {
            System.err.println("--Erro no método sleep()");
        }

        // Minha posição depois da viagem é a posição de destino do passageiro
        coord.set(currentclient.getDest());
    }

    // Método executado por cada thread Taxi
    public void run() {
        // Cada táxi atende pedidos até que todos os clientes tenham sido atendidos
        while(true) {
            // Avisa a 'central' que este táxi está disponível
            central.getRequest(this);

            // Só terei retornado de getRequest() sem cliente se todos os clientes foram atendidos.
            if (currentclient == null) {
                System.out.println("Táxi #" + id + " terminou o expediente na posição " + coord);
                // Relato minha posição a 'central'
                central.report(id, coord);
                // A thread sai do loop e termina
                break;
            }

            // Se tenho um cliente para atender, faço a viagem
            travel();
        }
    }

    // Setter: modifica 'currentclient'
    public void setCurrentclient(Client currentclient) {
        this.currentclient = currentclient;
    }

    // Getter: acessa 'currentclient'
    public Client getCurrentclient() {
        return currentclient;
    }

    //Getter: acessa 'coord'
    public Coord getCoord() {
        return coord;
    }

    // Getter: acessa 'id'
    public int getId() {
        return id;
    }

}
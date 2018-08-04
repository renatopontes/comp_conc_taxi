
// Representa pontos (x,y) de um plano cartesiano
public class Coord {
    private int x, y;

    // Construtor
    // _x e _y são as coordenadas do ponto (_x,_y)
    public Coord(int _x, int _y) {
        x = _x;
        y = _y;
    }

    // Calcula a distância Manhattan deste ponto até outro ponto 'c2'
    public int distanceTo(Coord c2) {
        // A distância entre (a,b) e (c,d) é: |c-a| + |d-c|
        return Math.abs(c2.x - this.x) + Math.abs(c2.y - this.y);
    }

    // Atualiza as coordenadas deste objeto para as coordenadas do objeto 'dest'
    public void set(Coord dest) {
        x = dest.x;
        y = dest.y;
    }

    // Retorna uma representação do objeto para ser usada pelos métodos
    // que escrevem na saída padrão
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Getter: acessa 'x'
    public int getX() {
        return x;
    }

    // Getter: acessa 'y'
    public int getY() {
        return y;
    }
}

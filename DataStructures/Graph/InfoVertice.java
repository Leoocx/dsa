class InfoVertice<T> {
    Cor cor;
    int distancia;     // Usado no algoritmo de BFS
    Vertice<T> predecessor;
    int tempoDescoberta; // Usado no algoritmo de DFS
    int tempoFinalizacao;

    InfoVertice() {
        this.cor = Cor.BRANCO;
        this.distancia = Integer.MAX_VALUE;
        this.predecessor = null;
        this.tempoDescoberta = 0;
        this.tempoFinalizacao = 0;
    }
}
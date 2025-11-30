/**
     * Classe auxiliar para a fila de prioridade do Prim
     */
    class VerticeChave<T> {
        private Vertice<T> vertice;
        private double chave;

        public VerticeChave(Vertice<T> vertice, double chave) {
            this.vertice = vertice;
            this.chave = chave;
        }

        public Vertice<T> getVertice() { return vertice; }
        public double getChave() { return chave; }
    }

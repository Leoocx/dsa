import java.util.ArrayList;

class Vertice<T> {
    private T dado;
    private ArrayList<Aresta<T>> arestas = new ArrayList<>();

    public T getDado() { return dado; }
    public void setDado(T dado) { this.dado = dado; }

    public ArrayList<Aresta<T>> getArestas() { return arestas; }
}
class Aresta<T> {
    private String descricao;
    private Vertice<T> verticeInicio;
    private Vertice<T> verticeFim;

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Vertice<T> getVerticeInicio() { return verticeInicio; }
    public void setVerticeInicio(Vertice<T> verticeInicio) { this.verticeInicio = verticeInicio; }

    public Vertice<T> getVerticeFim() { return verticeFim; }
    public void setVerticeFim(Vertice<T> verticeFim) { this.verticeFim = verticeFim; }
}

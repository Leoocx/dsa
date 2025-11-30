
import java.util.*;
import java.util.stream.Collectors;

class GrafoDirigido<T> {
    ArrayList<Vertice<T>> vertices;
    ArrayList<Aresta<T>> arestas;

    public GrafoDirigido() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }
//==================================================================================================

    public void inserirVertice(T dado) {
        Vertice<T> v = new Vertice<>();
        v.setDado(dado);
        this.vertices.add(v);
    }
//==================================================================================================

    private Vertice<T> getVertice(T dado) {
        for (Vertice<T> v : vertices) {
            if (v.getDado().equals(dado)) {
                return v;
            }
        }
        return null;
    }
//==================================================================================================

    public void inserirAresta(T u, T v) {
        Vertice<T> verticeU = getVertice(u);
        Vertice<T> verticeV = getVertice(v);

        if (verticeU == null) {
            inserirVertice(u);
            verticeU = getVertice(u);
        }
        if (verticeV == null) {
            inserirVertice(v);
            verticeV = getVertice(v);
        }

        Aresta<T> a = new Aresta<>();
        a.setDescricao("e" + arestas.size());
        a.setVerticeInicio(verticeU);
        a.setVerticeFim(verticeV);
        this.arestas.add(a);
    }

//==================================================================================================

    public void removerV(T dado) {
        Vertice<T> verticeParaRemover = getVertice(dado);
        if (verticeParaRemover == null) {
            System.out.println("Vértice não encontrado.");
            return;
        }

        // Remove todas as arestas incidentes ao vértice
        arestas.removeIf(aresta ->
            aresta.getVerticeInicio().equals(verticeParaRemover) ||
            aresta.getVerticeFim().equals(verticeParaRemover)
        );

        // Remove o vértice
        vertices.remove(verticeParaRemover);
        System.out.println("Vértice " + dado + " e suas arestas incidentes foram removidos.");
    }
//==================================================================================================

    public void removerA(String descricaoAresta) {
        Aresta<T> arestaParaRemover = null;
        for (Aresta<T> a : arestas) {
            if (a.getDescricao().equals(descricaoAresta)) {
                arestaParaRemover = a;
                break;
            }
        }
        if (arestaParaRemover != null) {
            arestas.remove(arestaParaRemover);
            System.out.println("Aresta " + descricaoAresta + " removida.");
        } else {
            System.out.println("Aresta não encontrada.");
        }
    }
//==================================================================================================

    public void buscarVertice(T vertice) {
        boolean pertence = getVertice(vertice) != null;
        System.out.println("Pertence? :" + pertence);
    }
//==================================================================================================

    public void vertices() {
        for (Vertice<T> v : vertices) {
            System.out.println("Vertice: " + v.getDado());
        }
    }
//==================================================================================================

    public void arestas() {
        for (Aresta<T> a : arestas) {
            System.out.println("Aresta: " + a.getDescricao() + 
                " (" + a.getVerticeInicio().getDado() + " -> " + a.getVerticeFim().getDado() + ")");
        }
    }
//==================================================================================================

    public void verticesA(String nomeAresta) {
        for (Aresta<T> e : arestas) {
            if (e.getDescricao().equals(nomeAresta)) {
                System.out.println("Aresta " + e.getDescricao() + 
                    ": " + e.getVerticeInicio().getDado() + " -> " + e.getVerticeFim().getDado());
            }
        }
    }
//==================================================================================================

    public int getOrdem() {
        return vertices.size();
    }
//==================================================================================================

    public int getTamanho() {
        return arestas.size();
    }
//==================================================================================================

    public Aresta<T> getA(T u, T v) {
        Vertice<T> verticeU = getVertice(u);
        Vertice<T> verticeV = getVertice(v);

        if (verticeU == null || verticeV == null) {
            return null;
        }

        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().equals(verticeU) && a.getVerticeFim().equals(verticeV)) {
                return a;
            }
        }
        return null;
    }
//==================================================================================================

    public int grauE(T dado) {
        Vertice<T> v = getVertice(dado);
        if (v == null) {
            return 0;
        }
        int grau = 0;
        for (Aresta<T> a : arestas) {
            if (a.getVerticeFim().equals(v)) {
                grau++;
            }
        }
        return grau;
    }
//==================================================================================================

    public int grauS(T dado) {
        Vertice<T> v = getVertice(dado);
        if (v == null) {
            return 0;
        }
        int grau = 0;
        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().equals(v)) {
                grau++;
            }
        }
        return grau;
    }
//==================================================================================================

    public int grau(T dado) {
        return grauE(dado) + grauS(dado);
    }
//==================================================================================================

    public T oposto(T dadoVertice, String descricaoAresta) {
        Vertice<T> v = getVertice(dadoVertice);
        Aresta<T> e = null;
        for (Aresta<T> a : arestas) {
            if (a.getDescricao().equals(descricaoAresta)) {
                e = a;
                break;
            }
        }

        if (v == null || e == null) {
            return null;
        }

        if (e.getVerticeInicio().equals(v)) {
            return e.getVerticeFim().getDado();
        } else if (e.getVerticeFim().equals(v)) {
            return e.getVerticeInicio().getDado();
        } else {
            return null; // O vértice não pertence à aresta
        }
    }
//==================================================================================================

    public List<Aresta<T>> arestasE(T dado) {
        Vertice<T> v = getVertice(dado);
        List<Aresta<T>> arestasDeEntrada = new ArrayList<>();
        if (v == null) {
            return arestasDeEntrada;
        }
        for (Aresta<T> a : arestas) {
            if (a.getVerticeFim().equals(v)) {
                arestasDeEntrada.add(a);
            }
        }
        return arestasDeEntrada;
    }
//==================================================================================================

    public List<Aresta<T>> arestasS(T dado) {
        Vertice<T> v = getVertice(dado);
        List<Aresta<T>> arestasDeSaida = new ArrayList<>();
        if (v == null) {
            return arestasDeSaida;
        }
        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().equals(v)) {
                arestasDeSaida.add(a);
            }
        }
        return arestasDeSaida;
    }
//==================================================================================================
    
    public ArrayList<Vertice<T>> adj(T dado) {
        Vertice<T> v = getVertice(dado);
        ArrayList<Vertice<T>> adjacentes = new ArrayList<>();

        if (v == null) return adjacentes;

        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().equals(v)) {
                adjacentes.add(a.getVerticeFim()); // só segue a direção
            }
        }
        return adjacentes;
    }

//==================================================================================================
    public void bfs(T origem) {
    Vertice<T> s = getVertice(origem);
    if (s == null) {
        System.out.println("Vértice de origem não encontrado!");
        return;
    }

    Map<Vertice<T>, InfoVertice<T>> info = new HashMap<>();
    for (Vertice<T> v : vertices) {
        info.put(v, new InfoVertice<>());
    }

    InfoVertice<T> infoS = info.get(s);
    infoS.cor = Cor.CINZA;
    infoS.distancia = 0;
    infoS.predecessor = null;

    Queue<Vertice<T>> fila = new LinkedList<>();
    fila.add(s);

    while (!fila.isEmpty()) {
        Vertice<T> u = fila.poll();

        for (Vertice<T> v : adj(u.getDado())) {
            InfoVertice<T> infoV = info.get(v);
            if (infoV.cor == Cor.BRANCO) {
                infoV.cor = Cor.CINZA;
                infoV.distancia = info.get(u).distancia + 1;
                infoV.predecessor = u;
                fila.add(v);
            }
        }

        info.get(u).cor = Cor.PRETO;
    }

    exibirTabela(info);
}
//==================================================================================================

private int tempo; // contador global do dfs

public void dfs() {
    Map<Vertice<T>, InfoVertice<T>> info = new HashMap<>();
    for (Vertice<T> v : vertices) {
        info.put(v, new InfoVertice<>());
    }
    tempo = 0;

    for (Vertice<T> u : vertices) {
        if (info.get(u).cor == Cor.BRANCO) {
            dfsVisita(u, info);
        }
    }

    exibirTabela(info);
}

private void dfsVisita(Vertice<T> u, Map<Vertice<T>, InfoVertice<T>> info) {
    tempo++;
    InfoVertice<T> iu = info.get(u);
    iu.cor = Cor.CINZA;
    iu.tempoDescoberta = tempo;

    for (Vertice<T> v : adj(u.getDado())) {
        if (info.get(v).cor == Cor.BRANCO) {
            info.get(v).predecessor = u;
            dfsVisita(v, info);
        }
    }

    iu.cor = Cor.PRETO;
    tempo++;
    iu.tempoFinalizacao = tempo;
}
//==================================================================================================

    public void exibirGrafo() {
        for (Vertice<T> v : vertices) {
            System.out.print(v.getDado() + " -> ");
            Set<T> vizinhos = new HashSet<>();
            for (Aresta<T> a : arestas) {
                if (a.getVerticeInicio().equals(v)) {
                    vizinhos.add(a.getVerticeFim().getDado());
                }
            }
            System.out.println(String.join(", ", vizinhos.stream().map(Object::toString).toList()));
        }
    }
//==================================================================================================

    private void exibirTabela(Map<Vertice<T>, InfoVertice<T>> info) {
    System.out.println("| Vértice | Cor    | Distância | Predecessor | Descoberta | Finalização |");
    System.out.println("-------------------------------------------------------------------------");
    for (Vertice<T> v : vertices) {
        InfoVertice<T> i = info.get(v);
        String pred = (i.predecessor == null) ? "-" : i.predecessor.getDado().toString();
        String dist = (i.distancia == Integer.MAX_VALUE) ? "∞" : String.valueOf(i.distancia);
        System.out.printf("| %-7s | %-6s | %-9s | %-11s | %-9d | %-11d |\n",
                v.getDado(), i.cor, dist, pred, i.tempoDescoberta, i.tempoFinalizacao);
    }
}
//==================================================================================================
    public void imprimirCaminho(T origem, T destino, Map<Vertice<T>, InfoVertice<T>> info) {
        Vertice<T> s = getVertice(origem);
        Vertice<T> v = getVertice(destino);
        if (s == null || v == null) {
            System.out.println("Vértice inválido!");
            return;
        }

        if (v.equals(s)) {
            System.out.print(s.getDado());
        } else if (info.get(v).predecessor == null) {
            System.out.println("Não existe caminho de " + origem + " para " + destino);
        } else {
            imprimirCaminho(origem, info.get(v).predecessor.getDado(), info);
            System.out.print(" -> " + v.getDado());
        }
    }


    public void inserirArestaComPeso(T u, T v, double peso) {
        Vertice<T> verticeU = getVertice(u);
        Vertice<T> verticeV = getVertice(v);

        if (verticeU == null) {
            inserirVertice(u);
            verticeU = getVertice(u);
        }
        if (verticeV == null) {
            inserirVertice(v);
            verticeV = getVertice(v);
        }

        // Remove aresta existente na mesma direção
        removerArestaDirecionada(u, v);

        // Aresta u -> v com peso (APENAS UMA DIREÇÃO)
        ArestaComPeso<T> a = new ArestaComPeso<>();
        a.setDescricao("e" + arestas.size());
        a.setVerticeInicio(verticeU);
        a.setVerticeFim(verticeV);
        a.setPeso(peso);
        this.arestas.add(a);
    }

    private void removerArestaDirecionada(T u, T v) {
        arestas.removeIf(a -> 
            a.getVerticeInicio().getDado().equals(u) && 
            a.getVerticeFim().getDado().equals(v)
        );
    }

    // Método para obter peso entre dois vértices (APENAS NA DIREÇÃO u->v)
    public double getPeso(T u, T v) {
        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().getDado().equals(u) && 
                a.getVerticeFim().getDado().equals(v)) {
                if (a instanceof ArestaComPeso) {
                    return ((ArestaComPeso<T>) a).getPeso();
                }
            }
        }
        return Double.POSITIVE_INFINITY; // Não existe aresta nessa direção
    }

    // Método para obter predecessores (importante para grafos dirigidos)
    public ArrayList<Vertice<T>> predecessores(T dado) {
        Vertice<T> v = getVertice(dado);
        ArrayList<Vertice<T>> pred = new ArrayList<>();

        if (v == null) return pred;

        for (Aresta<T> a : arestas) {
            if (a.getVerticeFim().equals(v)) {
                pred.add(a.getVerticeInicio());
            }
        }
        return pred;
    }


    public Map<T, Double> dijkstra(T origem) {
    Vertice<T> verticeOrigem = getVertice(origem);
    if (verticeOrigem == null) {
        System.out.println("Vértice de origem não encontrado!");
        return new HashMap<>();
    }

    // Estruturas para armazenar distâncias e predecessores
    Map<T, Double> distancias = new HashMap<>();
    Map<T, T> predecessores = new HashMap<>();
    PriorityQueue<VerticeDistancia<T>> filaPrioridade = new PriorityQueue<>(
        Comparator.comparingDouble(VerticeDistancia::getDistancia)
    );

    // Inicialização
    for (Vertice<T> v : vertices) {
        T dado = v.getDado();
        if (dado.equals(origem)) {
            distancias.put(dado, 0.0);
        } else {
            distancias.put(dado, Double.POSITIVE_INFINITY);
        }
        predecessores.put(dado, null);
        filaPrioridade.add(new VerticeDistancia<>(v, distancias.get(dado)));
    }

    System.out.println("=== EXECUÇÃO DO ALGORITMO DE DIJKSTRA (DIRIGIDO) ===");
    System.out.println("Vértice de origem: " + origem);

    while (!filaPrioridade.isEmpty()) {
        VerticeDistancia<T> current = filaPrioridade.poll();
        T u = current.getVertice().getDado();
        double distU = current.getDistancia();

        // Se a distância é infinita, não há como alcançar outros vértices
        if (distU == Double.POSITIVE_INFINITY) {
            break;
        }

        System.out.println("\nProcessando vértice: " + u + " (distância: " + distU + ")");

        // Para cada VIZINHO DE SAÍDA de u (APENAS NA DIREÇÃO DAS ARESTAS)
        for (Vertice<T> vizinho : adj(u)) {
            T v = vizinho.getDado();
            double pesoUV = getPeso(u, v); // Peso apenas na direção u->v
            double distAlternativa = distU + pesoUV;

            System.out.println("  Vizinho de saída: " + v + ", peso: " + pesoUV + 
                             ", distância alternativa: " + distAlternativa);

            if (distAlternativa < distancias.get(v)) {
                distancias.put(v, distAlternativa);
                predecessores.put(v, u);
                
                // Atualiza a fila de prioridade
                filaPrioridade.removeIf(vd -> vd.getVertice().getDado().equals(v));
                filaPrioridade.add(new VerticeDistancia<>(vizinho, distAlternativa));
                
                System.out.println("  ✓ Atualizada distância para " + v + ": " + distAlternativa);
            }
        }
    }

    exibirResultadosDijkstra(origem, distancias, predecessores);
    return distancias;
}

// Classe auxiliar para a fila de prioridade do Dijkstra
class VerticeDistancia<T> {
    private Vertice<T> vertice;
    private double distancia;

    public VerticeDistancia(Vertice<T> vertice, double distancia) {
        this.vertice = vertice;
        this.distancia = distancia;
    }

    public Vertice<T> getVertice() { return vertice; }
    public double getDistancia() { return distancia; }
}

private void exibirResultadosDijkstra(T origem, Map<T, Double> distancias, Map<T, T> predecessores) {
    System.out.println("\n=== RESULTADOS DO DIJKSTRA (DIRIGIDO) ===");
    System.out.println("Vértice de origem: " + origem);
    System.out.println("\n| Vértice | Distância | Predecessor | Caminho | Acessível |");
    System.out.println("|---------|-----------|-------------|---------|-----------|");

    for (Vertice<T> v : vertices) {
        T dado = v.getDado();
        double dist = distancias.get(dado);
        T pred = predecessores.get(dado);
        String distStr = (dist == Double.POSITIVE_INFINITY) ? "∞" : String.valueOf(dist);
        String predStr = (pred == null) ? "-" : pred.toString();
        String caminho = reconstruirCaminho(origem, dado, predecessores);
        String acessivel = (dist < Double.POSITIVE_INFINITY) ? "✓" : "✗";

        System.out.printf("| %-7s | %-9s | %-11s | %-7s | %-9s |\n", 
                         dado, distStr, predStr, caminho, acessivel);
    }
}

private String reconstruirCaminho(T origem, T destino, Map<T, T> predecessores) {
    if (predecessores.get(destino) == null && !origem.equals(destino)) {
        return "Inacessível";
    }

    LinkedList<T> caminho = new LinkedList<>();
    T current = destino;

    while (current != null) {
        caminho.addFirst(current);
        current = predecessores.get(current);
    }

    return caminho.stream()
                  .map(Object::toString)
                  .collect(Collectors.joining(" → "));
}


//==================================================================================================
// ALGORITMO DE FLOYD-WARSHALL PARA GRAFO DIRIGIDO
//==================================================================================================

public Map<T, Map<T, Double>> floydWarshall() {
    int n = vertices.size();
    
    // Mapeia vértices para índices
    Map<T, Integer> indiceVertice = new HashMap<>();
    Map<Integer, T> verticeIndice = new HashMap<>();
    
    for (int i = 0; i < n; i++) {
        T dado = vertices.get(i).getDado();
        indiceVertice.put(dado, i);
        verticeIndice.put(i, dado);
    }

    // Inicializa matriz de distâncias (ASSIMÉTRICA para grafos dirigidos)
    double[][] dist = new double[n][n];
    int[][] next = new int[n][n];

    // Inicialização
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (i == j) {
                dist[i][j] = 0;
            } else {
                T u = verticeIndice.get(i);
                T v = verticeIndice.get(j);
                double peso = getPeso(u, v); // Apenas na direção u->v
                dist[i][j] = peso;
                
                if (peso < Double.POSITIVE_INFINITY) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
    }

    System.out.println("=== EXECUÇÃO DO ALGORITMO FLOYD-WARSHALL (DIRIGIDO) ===");
    exibirMatriz(dist, verticeIndice, "Matriz inicial D(0):");

    // Algoritmo principal (MESMO que para não dirigido, mas a matriz é assimétrica)
    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][k] + dist[k][j] < dist[i][j]) {
                    dist[i][j] = dist[i][k] + dist[k][j];
                    next[i][j] = next[i][k];
                }
            }
        }
        exibirMatriz(dist, verticeIndice, "Matriz D(" + (k + 1) + "):");
    }

    // Verifica ciclos negativos
    boolean temCicloNegativo = false;
    for (int i = 0; i < n; i++) {
        if (dist[i][i] < 0) {
            temCicloNegativo = true;
            System.out.println("AVISO: Ciclo negativo detectado no vértice " + verticeIndice.get(i));
        }
    }

    return exibirResultadosFloydWarshall(dist, indiceVertice, verticeIndice, next, temCicloNegativo);
}

private void exibirMatriz(double[][] dist, Map<Integer, T> verticeIndice, String titulo) {
    System.out.println("\n" + titulo);
    int n = dist.length;
    
    System.out.print("     ");
    for (int i = 0; i < n; i++) {
        System.out.printf("%-6s", verticeIndice.get(i));
    }
    System.out.println();
    
    for (int i = 0; i < n; i++) {
        System.out.printf("%-4s ", verticeIndice.get(i));
        for (int j = 0; j < n; j++) {
            if (dist[i][j] == Double.POSITIVE_INFINITY) {
                System.out.print("∞     ");
            } else {
                System.out.printf("%-5.1f ", dist[i][j]);
            }
        }
        System.out.println();
    }
}

private Map<T, Map<T, Double>> exibirResultadosFloydWarshall(double[][] dist, 
                                                           Map<T, Integer> indiceVertice,
                                                           Map<Integer, T> verticeIndice,
                                                           int[][] next,
                                                           boolean temCicloNegativo) {
    
    Map<T, Map<T, Double>> resultados = new HashMap<>();
    int n = dist.length;

    System.out.println("\n=== RESULTADOS DO FLOYD-WARSHALL (DIRIGIDO) ===");
    
    if (temCicloNegativo) {
        System.out.println("⚠️  AVISO: O grafo contém ciclos negativos!");
    }
    
    for (int i = 0; i < n; i++) {
        T origem = verticeIndice.get(i);
        Map<T, Double> distancias = new HashMap<>();
        
        System.out.println("\nDistâncias a partir de " + origem + ":");
        System.out.println("| Destino | Distância | Caminho | Acessível |");
        System.out.println("|---------|-----------|---------|-----------|");
        
        for (int j = 0; j < n; j++) {
            T destino = verticeIndice.get(j);
            double distancia = dist[i][j];
            distancias.put(destino, distancia);
            
            String distStr = (distancia == Double.POSITIVE_INFINITY) ? "∞" : String.valueOf(distancia);
            String caminho = reconstruirCaminhoFloyd(i, j, next, verticeIndice);
            String acessivel = (distancia < Double.POSITIVE_INFINITY) ? "✓" : "✗";
            
            System.out.printf("| %-7s | %-9s | %-7s | %-9s |\n", 
                            destino, distStr, caminho, acessivel);
        }
        
        resultados.put(origem, distancias);
    }
    
    return resultados;
}

private String reconstruirCaminhoFloyd(int i, int j, int[][] next, Map<Integer, T> verticeIndice) {
    if (next[i][j] == -1) {
        return "Inacessível";
    }
    
    LinkedList<T> caminho = new LinkedList<>();
    caminho.add(verticeIndice.get(i));
    
    while (i != j) {
        i = next[i][j];
        caminho.add(verticeIndice.get(i));
    }
    
    return caminho.stream()
                  .map(Object::toString)
                  .collect(Collectors.joining(" → "));
    }


    public void compararAlgoritmos(T origem) {
    System.out.println("=== COMPARAÇÃO DIJKSTRA vs FLOYD-WARSHALL (DIRIGIDO) ===");
    System.out.println("Vértice de origem: " + origem);
    
    long startTime, endTime;
    
    // Dijkstra
    startTime = System.nanoTime();
    Map<T, Double> resultadoDijkstra = dijkstra(origem);
    endTime = System.nanoTime();
    long tempoDijkstra = endTime - startTime;
    
    // Floyd-Warshall
    startTime = System.nanoTime();
    Map<T, Map<T, Double>> resultadoFloyd = floydWarshall();
    endTime = System.nanoTime();
    long tempoFloyd = endTime - startTime;
    
    System.out.println("\n=== RESUMO DA COMPARAÇÃO ===");
    System.out.println("Tempo de execução Dijkstra: " + tempoDijkstra / 1000000.0 + " ms");
    System.out.println("Tempo de execução Floyd-Warshall: " + tempoFloyd / 1000000.0 + " ms");
    
    // Comparação de resultados
    System.out.println("\n=== VERIFICAÇÃO DE CONSISTÊNCIA ===");
    boolean consistente = true;
    Map<T, Double> distanciasFloyd = resultadoFloyd.get(origem);
    
    for (T vertice : vertices.stream().map(Vertice::getDado).collect(Collectors.toList())) {
        double distDijkstra = resultadoDijkstra.get(vertice);
        double distFloyd = distanciasFloyd.get(vertice);
        
        boolean iguais = Math.abs(distDijkstra - distFloyd) < 0.0001 || 
                        (Double.isInfinite(distDijkstra) && Double.isInfinite(distFloyd));
        
        System.out.printf("Vértice %s: Dijkstra=%.1f, Floyd=%.1f - %s\n",
                         vertice, distDijkstra, distFloyd,
                         iguais ? " CONSISTENTE" : " INCONSISTENTE");
        
        if (!iguais) {
            consistente = false;
        }
    }
    
    System.out.println("\nResultado da verificação: " + 
                      (consistente ? " ALGORITMOS CONSISTENTES" : " INCONSISTÊNCIA DETECTADA"));
    }


    //==================================================================================================
// ALGORITMO DE PRIM PARA GRAFO DIRIGIDO
//==================================================================================================

/**
 * Algoritmo de Prim modificado para grafo dirigido
 * Encontra uma arborescência de custo mínimo a partir de um vértice raiz
 */
public List<ArestaComPeso<T>> prim(T verticeInicial) {
    Vertice<T> inicio = getVertice(verticeInicial);
    if (inicio == null) {
        System.out.println("Vértice inicial não encontrado!");
        return new ArrayList<>();
    }

    System.out.println("=== ALGORITMO DE PRIM (DIRIGIDO) ===");
    System.out.println("Vértice raiz: " + verticeInicial);

    // Estruturas para o algoritmo
    Map<T, Double> chave = new HashMap<>(); // Custo mínimo para alcançar o vértice
    Map<T, T> predecessor = new HashMap<>(); // Vértice predecessor na arborescência
    Map<T, Boolean> naArvore = new HashMap<>(); // Se o vértice está na arborescência
    PriorityQueue<VerticeChave<T>> filaPrioridade = new PriorityQueue<>(
        Comparator.comparingDouble(VerticeChave::getChave)
    );

    // Inicialização
    for (Vertice<T> v : vertices) {
        T dado = v.getDado();
        chave.put(dado, Double.MAX_VALUE);
        predecessor.put(dado, null);
        naArvore.put(dado, false);
    }

    // Inicializa o vértice raiz
    chave.put(verticeInicial, 0.0);
    filaPrioridade.add(new VerticeChave<>(inicio, 0.0));

    int passo = 1;
    while (!filaPrioridade.isEmpty()) {
        VerticeChave<T> current = filaPrioridade.poll();
        T u = current.getVertice().getDado();
        
        if (naArvore.get(u)) continue;
        
        naArvore.put(u, true);
        
        System.out.println("\nPasso " + passo++ + ": Adicionando vértice " + u + 
                         " (custo: " + chave.get(u) + ")");

        // Para cada VIZINHO DE SAÍDA de u (direção das arestas)
        for (Vertice<T> vizinho : adj(u)) {
            T v = vizinho.getDado();
            double pesoUV = getPeso(u, v);

            // Em grafo dirigido, só consideramos arestas de saída
            if (!naArvore.get(v) && pesoUV < chave.get(v)) {
                chave.put(v, pesoUV);
                predecessor.put(v, u);
                filaPrioridade.add(new VerticeChave<>(vizinho, pesoUV));
                
                System.out.println("  Atualizando vértice " + v + 
                                 " - novo custo: " + pesoUV + 
                                 ", predecessor: " + u);
            }
        }
    }

    return construirArborescenciaPrim(predecessor, chave, verticeInicial);
}


private List<ArestaComPeso<T>> construirArborescenciaPrim(Map<T, T> predecessor, 
                                                         Map<T, Double> chave, 
                                                         T raiz) {
    List<ArestaComPeso<T>> arborescencia = new ArrayList<>();
    double custoTotal = 0.0;
    int verticesAlcancaveis = 0;

    System.out.println("\n=== CONSTRUINDO ARBORESCÊNCIA DO PRIM ===");
    
    for (T vertice : predecessor.keySet()) {
        T pred = predecessor.get(vertice);
        if (pred != null) {
            double peso = chave.get(vertice);
            ArestaComPeso<T> aresta = criarArestaArborescencia(pred, vertice, peso);
            arborescencia.add(aresta);
            custoTotal += peso;
            verticesAlcancaveis++;
            
            System.out.println("Aresta na arborescência: " + pred + " -> " + 
                             vertice + " (peso: " + peso + ")");
        }
    }

    exibirResultadoPrim(arborescencia, custoTotal, verticesAlcancaveis, raiz);
    return arborescencia;
}

private void exibirResultadoPrim(List<ArestaComPeso<T>> arborescencia, double custoTotal, 
                               int verticesAlcancaveis, T raiz) {
    System.out.println("\n=== RESULTADO DO ALGORITMO DE PRIM (DIRIGIDO) ===");
    System.out.println("Vértice raiz: " + raiz);
    System.out.println("Custo total da arborescência: " + custoTotal);
    System.out.println("Número de arestas na arborescência: " + arborescencia.size());
    System.out.println("Vértices alcançáveis a partir da raiz: " + verticesAlcancaveis + "/" + vertices.size());
    
    if (arborescencia.size() == verticesAlcancaveis) {
        System.out.println("✓ Arborescência válida");
    } else {
        System.out.println("⚠ Arborescência parcial - alguns vértices não são alcançáveis");
    }
    
    System.out.println("\nArestas da Arborescência de Custo Mínimo:");
    for (ArestaComPeso<T> aresta : arborescencia) {
        System.out.println("  " + aresta.getVerticeInicio().getDado() + " -> " + 
                          aresta.getVerticeFim().getDado() + " (peso: " + aresta.getPeso() + ")");
    }
    
    // Exibir como árvore dirigida
    System.out.println("\nEstrutura da Arborescência (a partir de " + raiz + "):");
    exibirComoArvoreDirigida(arborescencia, raiz);
}


//==================================================================================================
// ALGORITMO DE KRUSKAL PARA GRAFO DIRIGIDO
//==================================================================================================

/**
 * Algoritmo de Kruskal modificado para grafo dirigido
 * Encontra uma floresta de arborescências de custo mínimo
 */
public List<ArestaComPeso<T>> kruskal() {
    System.out.println("=== ALGORITMO DE KRUSKAL (DIRIGIDO) ===");

    // Em grafo dirigido, consideramos TODAS as arestas (não removemos duplicatas)
    List<ArestaComPeso<T>> todasArestas = obterTodasArestasComPeso();
    
    // Ordena arestas por peso
    todasArestas.sort(Comparator.comparingDouble(ArestaComPeso::getPeso));

    System.out.println("Arestas ordenadas por peso:");
    for (ArestaComPeso<T> aresta : todasArestas) {
        System.out.println("  " + aresta.getVerticeInicio().getDado() + " -> " + 
                          aresta.getVerticeFim().getDado() + " (peso: " + aresta.getPeso() + ")");
    }

    // Usa a classe DisjointSet fornecida
    DisjointSet<T> disjointSet = new DisjointSet<>();
    
    // Inicializa conjuntos para todos os vértices
    for (Vertice<T> v : vertices) {
        disjointSet.makeSet(v.getDado());
    }

    List<ArestaComPeso<T>> floresta = new ArrayList<>();
    double custoTotal = 0.0;
    int passo = 1;

    System.out.println("\nProcessando arestas:");
    for (ArestaComPeso<T> aresta : todasArestas) {
        T u = aresta.getVerticeInicio().getDado();
        T v = aresta.getVerticeFim().getDado();
        double peso = aresta.getPeso();

        T raizU = disjointSet.findSet(u);
        T raizV = disjointSet.findSet(v);

        System.out.println("\nPasso " + passo++ + ": Aresta " + u + " -> " + v + " (peso: " + peso + ")");
        System.out.println("  Conjunto de " + u + ": " + raizU);
        System.out.println("  Conjunto de " + v + ": " + raizV);

        // Em grafo dirigido, a aresta u->v só pode ser adicionada se não formar ciclo
        // E se v não tiver outro predecessor na floresta atual
        if (!raizU.equals(raizV) && !temPredecessorNaFloresta(floresta, v)) {
            floresta.add(aresta);
            custoTotal += peso;
            disjointSet.union(u, v);
            System.out.println("  ✓ ADICIONADA - une conjuntos " + raizU + " e " + raizV);
        } else {
            System.out.println("  ✗ REJEITADA - formaria ciclo ou v já tem predecessor");
        }
    }

    exibirResultadoKruskal(floresta, custoTotal);
    return floresta;
}

/**
 * Obtém todas as arestas com peso (não remove duplicatas em grafo dirigido)
 */
private List<ArestaComPeso<T>> obterTodasArestasComPeso() {
    List<ArestaComPeso<T>> arestasComPeso = new ArrayList<>();

    for (Aresta<T> a : arestas) {
        if (a instanceof ArestaComPeso) {
            arestasComPeso.add((ArestaComPeso<T>) a);
        }
    }
    return arestasComPeso;
}

/**
 * Verifica se um vértice já tem predecessor na floresta atual
 * Em uma arborescência, cada vértice (exceto raízes) tem exatamente um predecessor
 */
private boolean temPredecessorNaFloresta(List<ArestaComPeso<T>> floresta, T vertice) {
    for (ArestaComPeso<T> aresta : floresta) {
        if (aresta.getVerticeFim().getDado().equals(vertice)) {
            return true;
        }
    }
    return false;
}

private void exibirResultadoKruskal(List<ArestaComPeso<T>> floresta, double custoTotal) {
    System.out.println("\n=== RESULTADO DO ALGORITMO DE KRUSKAL (DIRIGIDO) ===");
    System.out.println("Custo total da floresta: " + custoTotal);
    System.out.println("Número de arestas na floresta: " + floresta.size());
    System.out.println("Número de vértices: " + vertices.size());
    
    // Encontra as raízes das arborescências
    Set<T> raizes = encontrarRaizes(floresta);
    System.out.println("Número de arborescências (componentes): " + raizes.size());
    
    System.out.println("\nArestas da Floresta de Arborescências de Custo Mínimo:");
    for (ArestaComPeso<T> aresta : floresta) {
        System.out.println("  " + aresta.getVerticeInicio().getDado() + " -> " + 
                          aresta.getVerticeFim().getDado() + " (peso: " + aresta.getPeso() + ")");
    }
    
    // Exibir cada arborescência separadamente
    System.out.println("\nEstrutura da Floresta:");
    exibirFloresta(floresta, raizes);
}


//==================================================================================================
// MÉTODOS AUXILIARES PARA GRAFOS DIRIGIDOS
//==================================================================================================

private ArestaComPeso<T> criarArestaArborescencia(T u, T v, double peso) {
    ArestaComPeso<T> aresta = new ArestaComPeso<>();
    aresta.setVerticeInicio(getVertice(u));
    aresta.setVerticeFim(getVertice(v));
    aresta.setPeso(peso);
    aresta.setDescricao(u + "->" + v + "(Arborescencia)");
    return aresta;
}

/**
 * Encontra as raízes das arborescências na floresta
 * Raiz = vértice sem arestas de entrada na floresta
 */
private Set<T> encontrarRaizes(List<ArestaComPeso<T>> floresta) {
    Set<T> todosVertices = new HashSet<>();
    Set<T> verticesComEntrada = new HashSet<>();
    
    // Coleta todos os vértices e os que têm arestas de entrada
    for (ArestaComPeso<T> aresta : floresta) {
        T inicio = aresta.getVerticeInicio().getDado();
        T fim = aresta.getVerticeFim().getDado();
        todosVertices.add(inicio);
        todosVertices.add(fim);
        verticesComEntrada.add(fim);
    }
    
    // Raízes são vértices que não têm arestas de entrada
    Set<T> raizes = new HashSet<>(todosVertices);
    raizes.removeAll(verticesComEntrada);
    
    return raizes;
}

/**
 * Exibe a floresta como conjunto de arborescências
 */
private void exibirFloresta(List<ArestaComPeso<T>> floresta, Set<T> raizes) {
    if (floresta.isEmpty()) {
        System.out.println("Floresta vazia");
        return;
    }

    // Constrói a estrutura da floresta
    Map<T, List<T>> arvores = new HashMap<>();
    for (ArestaComPeso<T> aresta : floresta) {
        T pai = aresta.getVerticeInicio().getDado();
        T filho = aresta.getVerticeFim().getDado();
        arvores.computeIfAbsent(pai, k -> new ArrayList<>()).add(filho);
    }

    int componente = 1;
    for (T raiz : raizes) {
        System.out.println("Arborescência " + componente++ + " (Raiz: " + raiz + "):");
        exibirArvoreDirigidaRecursivo(arvores, raiz, 0);
        System.out.println();
    }
}

/**
 * Exibe árvore dirigida recursivamente
 */
private void exibirArvoreDirigidaRecursivo(Map<T, List<T>> arvores, T vertice, int nivel) {
    String indentacao = "  ".repeat(nivel);
    System.out.println(indentacao + "└── " + vertice);
    
    List<T> filhos = arvores.get(vertice);
    if (filhos != null) {
        for (T filho : filhos) {
            exibirArvoreDirigidaRecursivo(arvores, filho, nivel + 1);
        }
    }
}

/**
 * Exibe uma única arborescência a partir de uma raiz
 */
private void exibirComoArvoreDirigida(List<ArestaComPeso<T>> arborescencia, T raiz) {
    Map<T, List<T>> arvore = new HashMap<>();
    
    // Constrói a árvore a partir das arestas
    for (ArestaComPeso<T> aresta : arborescencia) {
        T pai = aresta.getVerticeInicio().getDado();
        T filho = aresta.getVerticeFim().getDado();
        arvore.computeIfAbsent(pai, k -> new ArrayList<>()).add(filho);
    }

    exibirArvoreDirigidaRecursivo(arvore, raiz, 0);
}

//==================================================================================================
// COMPARAÇÃO PRIM vs KRUSKAL PARA GRAFOS DIRIGIDOS
//==================================================================================================

public void compararAGMs(T verticeInicialPrim) {
    System.out.println("=" .repeat(70));
    System.out.println("COMPARAÇÃO PRIM vs KRUSKAL (GRAFO DIRIGIDO)");
    System.out.println("=" .repeat(70));
    
    long startTime, endTime;
    
    // Algoritmo de Prim
    System.out.println("\n" + "=" .repeat(30) + " PRIM " + "=" .repeat(30));
    startTime = System.nanoTime();
    List<ArestaComPeso<T>> resultadoPrim = prim(verticeInicialPrim);
    endTime = System.nanoTime();
    long tempoPrim = endTime - startTime;
    
    // Algoritmo de Kruskal
    System.out.println("\n" + "=" .repeat(30) + " KRUSKAL " + "=" .repeat(28));
    startTime = System.nanoTime();
    List<ArestaComPeso<T>> resultadoKruskal = kruskal();
    endTime = System.nanoTime();
    long tempoKruskal = endTime - startTime;
    
    // Comparação
    System.out.println("\n" + "=" .repeat(30) + " COMPARAÇÃO " + "=" .repeat(27));
    System.out.println("Tempo de execução Prim: " + tempoPrim / 1000000.0 + " ms");
    System.out.println("Tempo de execução Kruskal: " + tempoKruskal / 1000000.0 + " ms");
    
    double custoPrim = calcularCustoAGM(resultadoPrim);
    double custoKruskal = calcularCustoAGM(resultadoKruskal);
    
    System.out.println("Custo Prim (a partir de " + verticeInicialPrim + "): " + custoPrim);
    System.out.println("Custo Kruskal (floresta): " + custoKruskal);
    
    System.out.println("Número de arestas Prim: " + resultadoPrim.size());
    System.out.println("Número de arestas Kruskal: " + resultadoKruskal.size());
    
    // Em grafos dirigidos, os resultados podem ser diferentes
    // Prim encontra uma arborescência a partir de uma raiz
    // Kruskal encontra uma floresta de arborescências
    System.out.println("\nObservação: Em grafos dirigidos:");
    System.out.println("  - Prim encontra UMA arborescência a partir de uma raiz específica");
    System.out.println("  - Kruskal encontra UMA FLORESTA de arborescências");
    System.out.println("  - Os resultados podem ser diferentes mas ambos são válidos");
}

private double calcularCustoAGM(List<ArestaComPeso<T>> agm) {
    return agm.stream().mapToDouble(ArestaComPeso::getPeso).sum();
}


}
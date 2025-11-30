import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação de um Grafo Não Dirigido com algoritmos clássicos de teoria dos grafos
 * Inclui: DFS, BFS, Dijkstra, Floyd-Warshall, Prim, Kruskal, verificação Euleriana
 */
class GrafoNaoDirigido<T> {
    // Estruturas de dados principais do grafo
    ArrayList<Vertice<T>> vertices;  // Lista de vértices
    ArrayList<Aresta<T>> arestas;    // Lista de arestas

    // Construtor - inicializa as listas vazias
    public GrafoNaoDirigido() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    //==================================================================================================
    // OPERAÇÕES BÁSICAS DO GRAFO
    //==================================================================================================

    /**
     * Insere um novo vértice no grafo
     * @param dado O dado a ser armazenado no vértice
     */
    public void inserirVertice(T dado) {
        Vertice<T> v = new Vertice<>();
        v.setDado(dado);
        this.vertices.add(v);
    }

    /**
     * Busca um vértice pelo seu dado (método privado auxiliar)
     * @param dado O dado a ser buscado
     * @return O vértice encontrado ou null se não existir
     */
    private Vertice<T> getVertice(T dado) {
        for (Vertice<T> v : vertices) {
            if (v.getDado().equals(dado)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Insere uma aresta não dirigida entre dois vértices
     * Cria os vértices se não existirem
     * Evita duplicação de arestas
     */
    public void inserirAresta(T u, T v) {
        Vertice<T> verticeU = getVertice(u);
        Vertice<T> verticeV = getVertice(v);

        // Cria vértices se não existirem
        if (verticeU == null) {
            inserirVertice(u);
            verticeU = getVertice(u);
        }
        if (verticeV == null) {
            inserirVertice(v);
            verticeV = getVertice(v);
        }

        // Verifica se a aresta já existe para evitar duplicação
        if (getA(u, v) != null) {
            return; // Aresta já existe
        }

        // Aresta u -> v (direção original)
        Aresta<T> a1 = new Aresta<>();
        a1.setDescricao("e" + (arestas.size() + 1));
        a1.setVerticeInicio(verticeU);
        a1.setVerticeFim(verticeV);
        this.arestas.add(a1);

        // Aresta v -> u (espelhada, para ser realmente não dirigido)
        Aresta<T> a2 = new Aresta<>();
        a2.setDescricao("e" + (arestas.size() + 1));
        a2.setVerticeInicio(verticeV);
        a2.setVerticeFim(verticeU);
        this.arestas.add(a2);
    }

    /**
     * Verifica se um vértice pertence ao grafo
     */
    public void buscarVertice(T vertice) {
        boolean pertence = getVertice(vertice) != null;
        System.out.println("Pertence? :" + pertence);
    }

    /**
     * Exibe todos os vértices do grafo
     */
    public void vertices() {
        for (Vertice<T> v : vertices) {
            System.out.println("Vertice: " + v.getDado());
        }
    }

    /**
     * Exibe todas as arestas do grafo (evitando duplicação na visualização)
     */
    public void arestas() {
        // Mostra apenas uma direção para evitar duplicação na visualização
        Set<String> arestasMostradas = new HashSet<>();
        for (Aresta<T> a : arestas) {
            String chave = gerarChaveAresta(a.getVerticeInicio().getDado(), a.getVerticeFim().getDado());
            if (arestasMostradas.add(chave)) {
                System.out.println("Aresta: " + a.getDescricao() +
                    " (" + a.getVerticeInicio().getDado() + " - " + a.getVerticeFim().getDado() + ")");
            }
        }
    }

    /**
     * Gera chave única para aresta independente da direção
     */
    private String gerarChaveAresta(T u, T v) {
        // Ordena os vértices para garantir chave única independente da direção
        return u.toString().compareTo(v.toString()) < 0 ? u + "-" + v : v + "-" + u;
    }

    /**
     * Remove um vértice e todas as arestas incidentes a ele
     */
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

    /**
     * Obtém a aresta entre dois vértices (se existir)
     */
    public Aresta<T> getA(T u, T v) {
        Vertice<T> verticeU = getVertice(u);
        Vertice<T> verticeV = getVertice(v);

        if (verticeU == null || verticeV == null) {
            return null;
        }

        // Busca aresta em ambas as direções (grafo não dirigido)
        for (Aresta<T> a : arestas) {
            if ((a.getVerticeInicio().equals(verticeU) && a.getVerticeFim().equals(verticeV)) ||
                (a.getVerticeInicio().equals(verticeV) && a.getVerticeFim().equals(verticeU))) {
                return a;
            }
        }
        return null;
    }

    /**
     * Calcula o grau de um vértice (número de arestas incidentes)
     * Método à prova de duplicação - conta arestas únicas
     */
    public int grau(T dado) {
        Vertice<T> v = getVertice(dado);
        if (v == null) {
            return 0;
        }
        
        // Usa Set para evitar contar arestas duplicadas
        Set<String> arestasUnicas = new HashSet<>();
        
        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().equals(v) || a.getVerticeFim().equals(v)) {
                // Gera chave única para a aresta
                T u = a.getVerticeInicio().getDado();
                T w = a.getVerticeFim().getDado();
                String chave = (u.toString().compareTo(w.toString()) < 0) ? 
                    u + "-" + w : w + "-" + u;
                arestasUnicas.add(chave);
            }
        }
        
        return arestasUnicas.size();
    }

    /**
     * Encontra o vértice oposto em uma aresta específica
     */
    public T oposto(T dadoVertice, String descricaoAresta) {
        Vertice<T> v = getVertice(dadoVertice);
        Aresta<T> e = null;
        
        // Encontra a aresta pela descrição
        for (Aresta<T> a : arestas) {
            if (a.getDescricao().equals(descricaoAresta)) {
                e = a;
                break;
            }
        }

        if (v == null || e == null) {
            return null;
        }

        // Retorna o vértice oposto
        if (e.getVerticeInicio().equals(v)) {
            return e.getVerticeFim().getDado();
        } else if (e.getVerticeFim().equals(v)) {
            return e.getVerticeInicio().getDado();
        } else {
            return null; // O vértice não pertence à aresta
        }
    }

    /**
     * Retorna todas as arestas incidentes a um vértice
     */
    public List<Aresta<T>> arestasIncidentes(T dado) {
        Vertice<T> v = getVertice(dado);
        List<Aresta<T>> arestasIncidentes = new ArrayList<>();
        if (v == null) {
            return arestasIncidentes;
        }
        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().equals(v) || a.getVerticeFim().equals(v)) {
                arestasIncidentes.add(a);
            }
        }
        return arestasIncidentes;
    }

    /**
     * Remove uma aresta pelo seu identificador
     */
    public void removerA(String descricaoAresta) {
        // Remove ambas as direções da aresta
        List<Aresta<T>> arestasParaRemover = new ArrayList<>();
        for (Aresta<T> a : arestas) {
            if (a.getDescricao().equals(descricaoAresta)) {
                arestasParaRemover.add(a);
            }
        }
        arestas.removeAll(arestasParaRemover);
        System.out.println("Aresta " + descricaoAresta + " removida.");
    }

    /**
     * Retorna a ordem do grafo (número de vértices)
     */
    public int getOrdem() {
        return vertices.size();
    }

    /**
     * Retorna o tamanho do grafo (número de arestas únicas)
     */
    public int getTamanho() {
        // Em grafo não dirigido, cada aresta é armazenada duas vezes
        return arestas.size() / 2;
    }

    /**
     * Retorna lista de vértices adjacentes a um vértice
     */
    public ArrayList<Vertice<T>> adj(T dado) {
        Vertice<T> v = getVertice(dado);
        ArrayList<Vertice<T>> adjacentes = new ArrayList<>();

        if (v == null) return adjacentes;

        // Adiciona todos os vértices conectados por arestas
        for (Aresta<T> a : arestas) {
            if (a.getVerticeInicio().equals(v)) {
                adjacentes.add(a.getVerticeFim());
            } else if (a.getVerticeFim().equals(v)) {
                adjacentes.add(a.getVerticeInicio());
            }
        }
        return adjacentes;
    }

    //==================================================================================================
    // ALGORITMOS DE BUSCA
    //==================================================================================================

    private int tempo; // contador global para DFS

    /**
     * Busca em Profundidade (DFS) - percorre todo o grafo
     */
    public void dfs() {
        Map<Vertice<T>, InfoVertice<T>> info = new HashMap<>();
        // Inicializa todos os vértices como não visitados
        for (Vertice<T> v : vertices) {
            info.put(v, new InfoVertice<>());
        }
        tempo = 0;

        // Visita cada vértice não visitado (para grafos não conexos)
        for (Vertice<T> u : vertices) {
            if (info.get(u).cor == Cor.BRANCO) {
                dfsVisita(u, info);
            }
        }

        exibirTabela(info);
    }

    /**
     * Método recursivo auxiliar para DFS
     */
    private void dfsVisita(Vertice<T> u, Map<Vertice<T>, InfoVertice<T>> info) {
        tempo++;
        InfoVertice<T> iu = info.get(u);
        iu.cor = Cor.CINZA; // Marcando como visitado
        iu.tempoDescoberta = tempo;

        // Visita recursivamente todos os vizinhos não visitados
        for (Vertice<T> v : adj(u.getDado())) {
            if (info.get(v).cor == Cor.BRANCO) {
                info.get(v).predecessor = u;
                dfsVisita(v, info);
            }
        }

        iu.cor = Cor.PRETO; // Finalizado
        tempo++;
        iu.tempoFinalizacao = tempo;
    }

    /**
     * Busca em Largura (BFS) - a partir de um vértice origem
     */
    public void bfs(T origem) {
        Vertice<T> s = getVertice(origem);
        if (s == null) {
            System.out.println("Vértice de origem não encontrado!");
            return;
        }

        Map<Vertice<T>, InfoVertice<T>> info = new HashMap<>();
        for (Vertice<T> v : vertices) {
            InfoVertice<T> infoV = new InfoVertice<>();
            infoV.distancia = Integer.MAX_VALUE; // Inicializa com "infinito"
            info.put(v, infoV);
        }

        // Configura vértice origem
        InfoVertice<T> infoS = info.get(s);
        infoS.cor = Cor.CINZA;
        infoS.distancia = 0;
        infoS.predecessor = null;

        Queue<Vertice<T>> fila = new LinkedList<>();
        fila.add(s);

        while (!fila.isEmpty()) {
            Vertice<T> u = fila.poll();
            InfoVertice<T> infoU = info.get(u);

            // Processa todos os vizinhos
            for (Vertice<T> v : adj(u.getDado())) {
                InfoVertice<T> infoV = info.get(v);
                if (infoV.cor == Cor.BRANCO) {
                    infoV.cor = Cor.CINZA;
                    infoV.distancia = infoU.distancia + 1;
                    infoV.predecessor = u;
                    fila.add(v);
                }
            }

            infoU.cor = Cor.PRETO;
        }
        exibirTabela(info);
    }

    /**
     * Exibe o grafo como lista de adjacência
     */
    public void exibirGrafo() {
        for (Vertice<T> v : vertices) {
            System.out.print(v.getDado() + " -> ");
            Set<T> vizinhos = new HashSet<>();
            for (Aresta<T> a : arestas) {
                if (a.getVerticeInicio().equals(v)) {
                    vizinhos.add(a.getVerticeFim().getDado());
                }
                if (a.getVerticeFim().equals(v)) {
                    vizinhos.add(a.getVerticeInicio().getDado());
                }
            }
            System.out.println(String.join(", ", vizinhos.stream().map(Object::toString).toList()));
        }
    }

    /**
     * Exibe tabela com informações dos vértices após busca
     */
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

    /**
     * Imprime o caminho entre dois vértices usando BFS
     */
    public void imprimirCaminho(T origem, T destino) {
        Vertice<T> s = getVertice(origem);
        Vertice<T> v = getVertice(destino);
        if (s == null || v == null) {
            System.out.println("Vértice inválido!");
            return;
        }

        // Executa BFS para obter informações dos predecessores
        Map<Vertice<T>, InfoVertice<T>> info = new HashMap<>();
        for (Vertice<T> vert : vertices) {
            info.put(vert, new InfoVertice<>());
        }

        Queue<Vertice<T>> fila = new LinkedList<>();
        info.get(s).cor = Cor.CINZA;
        info.get(s).distancia = 0;
        fila.add(s);

        while (!fila.isEmpty()) {
            Vertice<T> u = fila.poll();
            for (Vertice<T> w : adj(u.getDado())) {
                if (info.get(w).cor == Cor.BRANCO) {
                    info.get(w).cor = Cor.CINZA;
                    info.get(w).distancia = info.get(u).distancia + 1;
                    info.get(w).predecessor = u;
                    fila.add(w);
                }
            }
            info.get(u).cor = Cor.PRETO;
        }

        // Imprime o caminho recursivamente
        imprimirCaminhoRecursivo(s, v, info);
        System.out.println();
    }

    /**
     * Método recursivo para imprimir caminho
     */
    private void imprimirCaminhoRecursivo(Vertice<T> s, Vertice<T> v, Map<Vertice<T>, InfoVertice<T>> info) {
        if (v.equals(s)) {
            System.out.print(s.getDado());
        } else if (info.get(v).predecessor == null) {
            System.out.print("Não existe caminho de " + s.getDado() + " para " + v.getDado());
        } else {
            imprimirCaminhoRecursivo(s, info.get(v).predecessor, info);
            System.out.print(" -> " + v.getDado());
        }
    }

    //==================================================================================================
    // CONECTIVIDADE E COMPONENTES
    //==================================================================================================

    /**
     * Encontra todas as componentes conexas do grafo usando DFS
     */
    public Map<Integer, List<T>> componentesConexasDFS() {
        Map<Vertice<T>, InfoVertice<T>> info = new HashMap<>();
        Map<Vertice<T>, Integer> componenteMap = new HashMap<>();
        
        for (Vertice<T> v : vertices) { 
            info.put(v, new InfoVertice<>());
        }
        tempo = 0;
        int componenteID = 0;

        // Para cada vértice não visitado, inicia uma nova componente
        for (Vertice<T> u : vertices) {
            if (info.get(u).cor == Cor.BRANCO) {
                componenteID++;
                dfsVisitaConexidade(u, info, componenteID, componenteMap);
            }
        }

        // Processa os resultados agrupando por componente
        Map<Integer, List<T>> resultado = new HashMap<>();
        for (Map.Entry<Vertice<T>, Integer> entry : componenteMap.entrySet()) {
            int id = entry.getValue();
            T dadoVertice = entry.getKey().getDado();
            resultado.computeIfAbsent(id, k -> new ArrayList<>()).add(dadoVertice);
        }
        
        exibirResultadoConexidade("Busca em Profundidade (DFS)", resultado);
        return resultado;
    }

    /**
     * DFS modificado para identificar componentes conexas
     */
    private void dfsVisitaConexidade(Vertice<T> u, Map<Vertice<T>, InfoVertice<T>> info, 
                                     int componenteID, Map<Vertice<T>, Integer> componenteMap) {
        
        tempo++;
        InfoVertice<T> iu = info.get(u);
        iu.cor = Cor.CINZA;
        iu.tempoDescoberta = tempo;
        
        // Associa vértice à componente atual
        componenteMap.put(u, componenteID);
        
        for (Vertice<T> v : adj(u.getDado())) {
            if (info.get(v).cor == Cor.BRANCO) {
                info.get(v).predecessor = u;
                dfsVisitaConexidade(v, info, componenteID, componenteMap);
            }
        }

        iu.cor = Cor.PRETO;
        tempo++;
        iu.tempoFinalizacao = tempo;
    }

    /**
     * Verifica se o grafo é conexo
     */
    public boolean isConexo() {
        if (vertices.isEmpty()) return true; // Grafo vazio é considerado conexo
        
        Map<Integer, List<T>> componentes = componentesConexasDFS();
        return componentes.size() == 1;
    }

    //==================================================================================================
    // VERIFICAÇÃO EULERIANA
    //==================================================================================================

    /**
     * Verifica se o grafo é Euleriano (tem ciclo Euleriano)
     * Critérios: todos vértices com grau par E grafo conexo E tem arestas
     */
    public boolean isEuleriano() {
        // Verificação básica
        if (vertices.isEmpty() || getTamanho() == 0) {
            return false;
        }

        System.out.println("\n=== VERIFICAÇÃO EULERIANA CORRETA ===");
        
        // Verifica graus ímpares
        boolean todosPares = true;
        for (Vertice<T> v : vertices) {
            int grauAtual = grau(v.getDado());
            boolean ehPar = (grauAtual % 2 == 0);
            System.out.println("Vértice " + v.getDado() + ": grau = " + grauAtual + 
                          " (" + (ehPar ? "PAR" : "ÍMPAR") + ")");
            
            if (!ehPar) {
                todosPares = false;
            }
        }

        // Verifica conexidade
        boolean conexo = isConexo();
        System.out.println("Grafo conexo: " + conexo);
        System.out.println("Todas arestas: " + getTamanho());

        // Critério: TODOS graus pares E grafo conexo E tem arestas
        boolean resultado = todosPares && conexo;
        System.out.println("É Euleriano: " + resultado);
        
        return resultado;
    }

    /**
     * Encontra ciclo Euleriano usando algoritmo de Hierholzer
     */
    public void encontrarCicloEuleriano() {
        System.out.println("\n*** Verificação do Ciclo Euleriano (Hierholzer) ***");

        // Verifica se é Euleriano primeiro
        if (!isEuleriano()) {
            System.out.println("O grafo NÃO é Euleriano.");
            
            // Diagnóstico detalhado
            System.out.println("Motivo(s):");
            
            // Verifica graus ímpares
            List<T> verticesGrauImpar = vertices.stream()
                    .filter(v -> grau(v.getDado()) % 2 != 0)
                    .map(Vertice::getDado)
                    .collect(Collectors.toList());
            
            if (!verticesGrauImpar.isEmpty()) {
                System.out.println("  - Vértices com grau ímpar: " + verticesGrauImpar);
            }
            
            // Verifica conexidade
            if (!isConexo()) {
                System.out.println("  - O grafo não é conexo");
                Map<Integer, List<T>> componentes = componentesConexasDFS();
                System.out.println("  - Número de componentes: " + componentes.size());
            }
            
            if (getTamanho() == 0) {
                System.out.println("  - O grafo não tem arestas");
            }
            
            return;
        }

        System.out.println("O grafo É Euleriano.");

        // Caso trivial: grafo sem arestas
        if (getTamanho() == 0) {
            System.out.println("Ciclo trivial (grafo sem arestas)");
            return;
        }

        // Encontra um vértice com grau > 0 para iniciar
        Vertice<T> inicio = vertices.stream()
                .filter(v -> grau(v.getDado()) > 0)
                .findFirst()
                .orElse(null);

        if (inicio == null) {
            System.out.println("Erro: não foi possível encontrar vértice inicial.");
            return;
        }

        // Cria uma cópia da estrutura de adjacência para manipulação
        Map<T, LinkedList<T>> adjacencia = new HashMap<>();
        
        // Inicializa as listas de adjacência
        for (Vertice<T> v : vertices) {
            adjacencia.put(v.getDado(), new LinkedList<>());
        }

        // Preenche a adjacência (cada aresta aparece uma vez)
        Set<String> arestasProcessadas = new HashSet<>();
        for (Aresta<T> a : arestas) {
            T u = a.getVerticeInicio().getDado();
            T v = a.getVerticeFim().getDado();
            String chave = gerarChaveAresta(u, v);
            
            if (arestasProcessadas.add(chave)) {
                adjacencia.get(u).add(v);
                adjacencia.get(v).add(u);
            }
        }

        System.out.println("Estrutura de adjacência para Hierholzer:");
        for (Map.Entry<T, LinkedList<T>> entry : adjacencia.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        //================= ALGORITMO DE HIERHOLZER ======================
        Stack<T> pilhaAtual = new Stack<>();
        LinkedList<T> ciclo = new LinkedList<>();
        
        // Começa com um vértice arbitrário
        T corrente = inicio.getDado();
        pilhaAtual.push(corrente);

        System.out.println("\nExecutando Hierholzer:");
        int passo = 1;
        
        while (!pilhaAtual.isEmpty()) {
            T topo = pilhaAtual.peek();
            
            if (!adjacencia.get(topo).isEmpty()) {
                // Há arestas saindo do vértice atual
                T proximo = adjacencia.get(topo).removeFirst();
                
                // Remove a aresta bidirecionalmente
                adjacencia.get(proximo).remove(topo);
                
                pilhaAtual.push(proximo);
                
                System.out.println("Passo " + passo++ + ": De " + topo + " para " + proximo + 
                                 " (Pilha: " + pilhaAtual + ")");
            } else {
                // Não há mais arestas saindo, adiciona ao ciclo
                ciclo.addFirst(pilhaAtual.pop());
                System.out.println("Passo " + passo++ + ": Backtrack -> Ciclo: " + ciclo + 
                                 " (Pilha: " + pilhaAtual + ")");
            }
        }

        // Verifica se usou todas as arestas
        boolean todasArestasUsadas = adjacencia.values().stream()
                .allMatch(lista -> lista.isEmpty());
        
        System.out.println("\n=== RESULTADO FINAL ===");
        if (todasArestasUsadas) {
            System.out.println(" Ciclo Euleriano válido encontrado!");
            System.out.println("Ciclo Euleriano: " + ciclo);
            System.out.println("Comprimento do ciclo: " + (ciclo.size() - 1) + " arestas");
            System.out.println("Número de vértices no ciclo: " + ciclo.size());
            
            // Verificação adicional
            if (ciclo.getFirst().equals(ciclo.getLast())) {
                System.out.println(" Ciclo fechado (início = fim)");
            } else {
                System.out.println("AVISO: Ciclo não está fechado!");
            }
        } else {
            System.out.println("ERRO: Nem todas as arestas foram usadas!");
            System.out.println("Ciclo parcial: " + ciclo);
        }
        
        System.out.println("---------------------------------------------------------------");
    }

    /**
     * Diagnóstico detalhado das propriedades Eulerianas do grafo
     */
    public void diagnosticoEulerianoDetalhado() {
        System.out.println("\n=== DIAGNÓSTICO EULERIANO DETALHADO ===");
        
        System.out.println("Informações do grafo:");
        System.out.println("  Ordem (vértices): " + getOrdem());
        System.out.println("  Tamanho (arestas): " + getTamanho());
        System.out.println("  É conexo? " + isConexo());
        
        System.out.println("\nGraus dos vértices:");
        int verticesGrauImpar = 0;
        for (Vertice<T> v : vertices) {
            int grauAtual = grau(v.getDado());
            String paridade = (grauAtual % 2 == 0) ? "PAR" : "ÍMPAR";
            System.out.println("  " + v.getDado() + ": grau " + grauAtual + " (" + paridade + ")");
            
            if (grauAtual % 2 != 0) {
                verticesGrauImpar++;
            }
        }
        
        System.out.println("\nResumo:");
        System.out.println("  Total de vértices com grau ímpar: " + verticesGrauImpar);
        System.out.println("  É Euleriano? " + isEuleriano());
        
        // Critério correto
        boolean criterioGraus = (verticesGrauImpar == 0);
        boolean criterioConexo = isConexo();
        boolean criterioArestas = (getTamanho() > 0);
        
        System.out.println("\nCritérios atendidos:");
        System.out.println("   Todos vértices com grau par: " + criterioGraus);
        System.out.println("   Grafo conexo: " + criterioConexo);
        System.out.println("   Tem arestas: " + criterioArestas);
        
        if (criterioGraus && criterioConexo && criterioArestas) {
            System.out.println("   TODOS os critérios atendidos - Grafo Euleriano!");
        } else {
            System.out.println(" Alguns critérios não atendidos - Grafo NÃO Euleriano");
        }
    }

    /**
     * Exibe resultado da análise de conexidade
     */
    private void exibirResultadoConexidade(String algoritmo, Map<?, List<T>> resultado) {
        System.out.println("\n*** Resultado do Algoritmo: " + algoritmo + " ***");    
        int numComponentes = resultado.size();

        if (numComponentes <= 1 && !vertices.isEmpty()) {
            System.out.println("O grafo É conexo. (1 Componente)");
        } else if (vertices.isEmpty()) {
            System.out.println("O grafo está vazio. (0 Componentes)");
        } else {
            System.out.println("O grafo NÃO é conexo. (" + numComponentes + " Componentes)");
        }

        System.out.println("Vértices pertencentes a cada componente:");
        int i = 1;
        for (List<T> componentes : resultado.values()) {
            System.out.printf("  [Componente %d]: %s\n", 
                i++, 
                componentes.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    //==================================================================================================
    // MÉTODOS PARA GRAFOS COM PESO
    //==================================================================================================

    /**
     * Insere aresta com peso entre dois vértices
     */
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

        // Remove arestas existentes entre u e v
        removerArestaEntre(u, v);

        // Aresta u -> v com peso
        ArestaComPeso<T> a1 = new ArestaComPeso<>();
        a1.setDescricao("e" + (arestas.size() + 1));
        a1.setVerticeInicio(verticeU);
        a1.setVerticeFim(verticeV);
        a1.setPeso(peso);
        this.arestas.add(a1);

        // Aresta v -> u com peso (espelhada)
        ArestaComPeso<T> a2 = new ArestaComPeso<>();
        a2.setDescricao("e" + (arestas.size() + 1));
        a2.setVerticeInicio(verticeV);
        a2.setVerticeFim(verticeU);
        a2.setPeso(peso);
        this.arestas.add(a2);
    }

    /**
     * Remove arestas entre dois vértices
     */
    private void removerArestaEntre(T u, T v) {
        arestas.removeIf(a -> 
            (a.getVerticeInicio().getDado().equals(u) && a.getVerticeFim().getDado().equals(v)) ||
            (a.getVerticeInicio().getDado().equals(v) && a.getVerticeFim().getDado().equals(u))
        );
    }

    /**
     * Obtém o peso da aresta entre dois vértices
     */
    public double getPeso(T u, T v) {
        for (Aresta<T> a : arestas) {
            if ((a.getVerticeInicio().getDado().equals(u) && a.getVerticeFim().getDado().equals(v)) ||
                (a.getVerticeInicio().getDado().equals(v) && a.getVerticeFim().getDado().equals(u))) {
                if (a instanceof ArestaComPeso) {
                    return ((ArestaComPeso<T>) a).getPeso();
                }
            }
        }
        return Double.POSITIVE_INFINITY; // Não existe aresta
    }

    //==================================================================================================
    // ALGORITMO DE DIJKSTRA - CAMINHOS MÍNIMOS
    //==================================================================================================

    /**
     * Algoritmo de Dijkstra para caminhos mínimos a partir de uma fonte
     * Funciona apenas com pesos não negativos
     */
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

        System.out.println("=== EXECUÇÃO DO ALGORITMO DE DIJKSTRA ===");
        System.out.println("Vértice de origem: " + origem);

        while (!filaPrioridade.isEmpty()) {
            VerticeDistancia<T> current = filaPrioridade.poll();
            T u = current.getVertice().getDado();
            double distU = current.getDistancia();

            System.out.println("\nProcessando vértice: " + u + " (distância: " + distU + ")");

            // Relaxamento das arestas
            for (Vertice<T> vizinho : adj(u)) {
                T v = vizinho.getDado();
                double pesoUV = getPeso(u, v);
                double distAlternativa = distU + pesoUV;

                System.out.println("  Vizinho: " + v + ", peso: " + pesoUV + 
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

    /**
     * Classe auxiliar para a fila de prioridade do Dijkstra
     */
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

    /**
     * Exibe resultados do algoritmo de Dijkstra
     */
    private void exibirResultadosDijkstra(T origem, Map<T, Double> distancias, Map<T, T> predecessores) {
        System.out.println("\n=== RESULTADOS DO DIJKSTRA ===");
        System.out.println("Vértice de origem: " + origem);
        System.out.println("\n| Vértice | Distância | Predecessor | Caminho |");
        System.out.println("|---------|-----------|-------------|---------|");

        for (Vertice<T> v : vertices) {
            T dado = v.getDado();
            double dist = distancias.get(dado);
            T pred = predecessores.get(dado);
            String distStr = (dist == Double.POSITIVE_INFINITY) ? "∞" : String.valueOf(dist);
            String predStr = (pred == null) ? "-" : pred.toString();
            String caminho = reconstruirCaminho(origem, dado, predecessores);

            System.out.printf("| %-7s | %-9s | %-11s | %-7s |\n", 
                             dado, distStr, predStr, caminho);
        }
    }

    /**
     * Reconstrói o caminho mínimo entre origem e destino
     */
    private String reconstruirCaminho(T origem, T destino, Map<T, T> predecessores) {
        if (predecessores.get(destino) == null && !origem.equals(destino)) {
            return "Sem caminho";
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
    // ALGORITMO DE FLOYD-WARSHALL - TODOS OS PARES DE CAMINHOS MÍNIMOS
    //==================================================================================================

    /**
     * Algoritmo de Floyd-Warshall para todos os pares de caminhos mínimos
     * Permite pesos negativos, mas não ciclos negativos
     */
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

        // Inicializa matriz de distâncias
        double[][] dist = new double[n][n];
        int[][] next = new int[n][n]; // Para reconstruir caminhos

        // Inicialização da matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    T u = verticeIndice.get(i);
                    T v = verticeIndice.get(j);
                    double peso = getPeso(u, v);
                    dist[i][j] = peso;
                    
                    if (peso < Double.POSITIVE_INFINITY) {
                        next[i][j] = j;
                    } else {
                        next[i][j] = -1;
                    }
                }
            }
        }

        System.out.println("=== EXECUÇÃO DO ALGORITMO FLOYD-WARSHALL ===");
        exibirMatriz(dist, verticeIndice, "Matriz inicial D(0):");

        // Algoritmo principal - programação dinâmica
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
        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) {
                System.out.println("AVISO: Ciclo negativo detectado!");
                break;
            }
        }

        return exibirResultadosFloydWarshall(dist, indiceVertice, verticeIndice, next);
    }

    /**
     * Exibe matriz de distâncias
     */
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

    /**
     * Exibe resultados do Floyd-Warshall
     */
    private Map<T, Map<T, Double>> exibirResultadosFloydWarshall(double[][] dist, 
                                                               Map<T, Integer> indiceVertice,
                                                               Map<Integer, T> verticeIndice,
                                                               int[][] next) {
        
        Map<T, Map<T, Double>> resultados = new HashMap<>();
        int n = dist.length;

        System.out.println("\n=== RESULTADOS DO FLOYD-WARSHALL ===");
        
        for (int i = 0; i < n; i++) {
            T origem = verticeIndice.get(i);
            Map<T, Double> distancias = new HashMap<>();
            
            System.out.println("\nDistâncias a partir de " + origem + ":");
            System.out.println("| Destino | Distância | Caminho |");
            System.out.println("|---------|-----------|---------|");
            
            for (int j = 0; j < n; j++) {
                T destino = verticeIndice.get(j);
                double distancia = dist[i][j];
                distancias.put(destino, distancia);
                
                String distStr = (distancia == Double.POSITIVE_INFINITY) ? "∞" : String.valueOf(distancia);
                String caminho = reconstruirCaminhoFloyd(i, j, next, verticeIndice);
                
                System.out.printf("| %-7s | %-9s | %-7s |\n", destino, distStr, caminho);
            }
            
            resultados.put(origem, distancias);
        }
        
        return resultados;
    }

    /**
     * Reconstrói caminho mínimo no Floyd-Warshall
     */
    private String reconstruirCaminhoFloyd(int i, int j, int[][] next, Map<Integer, T> verticeIndice) {
        if (next[i][j] == -1) {
            return "Sem caminho";
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

    //==================================================================================================
    // COMPARAÇÃO DIJKSTRA vs FLOYD-WARSHALL
    //==================================================================================================

    /**
     * Compara os algoritmos de Dijkstra e Floyd-Warshall
     */
    public void compararAlgoritmos(T origem) {
        System.out.println("=== COMPARAÇÃO DIJKSTRA vs FLOYD-WARSHALL ===");
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
                             iguais ? "✓ CONSISTENTE" : "✗ INCONSISTENTE");
            
            if (!iguais) {
                consistente = false;
            }
        }
        
        System.out.println("\nResultado da verificação: " + 
                          (consistente ? " ALGORITMOS CONSISTENTES" : " INCONSISTÊNCIA DETECTADA"));
    }

    //==================================================================================================
    // ALGORITMO DE PRIM - ÁRVORE GERADORA MÍNIMA
    //==================================================================================================

    /**
     * Algoritmo de Prim para Árvore Geradora Mínima (AGM)
     * Baseado em vértices, usa vetor de predecessores
     */
    public List<ArestaComPeso<T>> prim(T verticeInicial) {
        Vertice<T> inicio = getVertice(verticeInicial);
        if (inicio == null) {
            System.out.println("Vértice inicial não encontrado!");
            return new ArrayList<>();
        }

        System.out.println("=== ALGORITMO DE PRIM ===");
        System.out.println("Vértice inicial: " + verticeInicial);

        // Estruturas para o algoritmo
        Map<T, Double> chave = new HashMap<>(); // Custo mínimo para conectar ao vértice
        Map<T, T> predecessor = new HashMap<>(); // Vértice predecessor na AGM
        Map<T, Boolean> naAGM = new HashMap<>(); // Se o vértice está na AGM
        PriorityQueue<VerticeChave<T>> filaPrioridade = new PriorityQueue<>(
            Comparator.comparingDouble(VerticeChave::getChave)
        );

        // Inicialização
        for (Vertice<T> v : vertices) {
            T dado = v.getDado();
            chave.put(dado, Double.MAX_VALUE);
            predecessor.put(dado, null);
            naAGM.put(dado, false);
        }

        // Inicializa o vértice de início
        chave.put(verticeInicial, 0.0);
        filaPrioridade.add(new VerticeChave<>(inicio, 0.0));

        int passo = 1;
        while (!filaPrioridade.isEmpty()) {
            VerticeChave<T> current = filaPrioridade.poll();
            T u = current.getVertice().getDado();
            
            if (naAGM.get(u)) continue;
            
            naAGM.put(u, true);
            
            System.out.println("\nPasso " + passo++ + ": Adicionando vértice " + u + 
                             " (custo: " + chave.get(u) + ")");

            // Para cada vizinho de u
            for (Vertice<T> vizinho : adj(u)) {
                T v = vizinho.getDado();
                double pesoUV = getPeso(u, v);

                if (!naAGM.get(v) && pesoUV < chave.get(v)) {
                    chave.put(v, pesoUV);
                    predecessor.put(v, u);
                    filaPrioridade.add(new VerticeChave<>(vizinho, pesoUV));
                    
                    System.out.println("  Atualizando vértice " + v + 
                                     " - novo custo: " + pesoUV + 
                                     ", predecessor: " + u);
                }
            }
        }

        return construirAGMPrim(predecessor, chave);
    }

    
    /**
     * Constrói a AGM a partir dos predecessores
     */
    private List<ArestaComPeso<T>> construirAGMPrim(Map<T, T> predecessor, Map<T, Double> chave) {
        List<ArestaComPeso<T>> agm = new ArrayList<>();
        double custoTotal = 0.0;

        System.out.println("\n=== CONSTRUINDO AGM DO PRIM ===");
        
        for (T vertice : predecessor.keySet()) {
            T pred = predecessor.get(vertice);
            if (pred != null) {
                double peso = chave.get(vertice);
                ArestaComPeso<T> aresta = criarArestaAGM(pred, vertice, peso);
                agm.add(aresta);
                custoTotal += peso;
                
                System.out.println("Aresta na AGM: " + pred + " - " + vertice + " (peso: " + peso + ")");
            }
        }

        exibirResultadoPrim(agm, custoTotal);
        return agm;
    }

    /**
     * Exibe resultados do algoritmo de Prim
     */
    private void exibirResultadoPrim(List<ArestaComPeso<T>> agm, double custoTotal) {
        System.out.println("\n=== RESULTADO DO ALGORITMO DE PRIM ===");
        System.out.println("Custo total da AGM: " + custoTotal);
        System.out.println("Número de arestas na AGM: " + agm.size());
        System.out.println("Número de vértices: " + vertices.size());
        
        if (agm.size() == vertices.size() - 1) {
            System.out.println("✓ AGM válida (n-1 arestas)");
        } else {
            System.out.println("✗ AGM inválida - grafo pode não ser conexo");
        }
        
        System.out.println("\nArestas da Árvore Geradora Mínima:");
        for (ArestaComPeso<T> aresta : agm) {
            System.out.println("  " + aresta.getVerticeInicio().getDado() + " - " + 
                              aresta.getVerticeFim().getDado() + " (peso: " + aresta.getPeso() + ")");
        }
        
        // Exibir como árvore
        System.out.println("\nEstrutura da Árvore:");
        exibirComoArvore(agm);
    }

    //==================================================================================================
    // ALGORITMO DE KRUSKAL - ÁRVORE GERADORA MÍNIMA
    //==================================================================================================

    /**
     * Algoritmo de Kruskal para Árvore Geradora Mínima (AGM)
     * Baseado em arestas, usa estrutura Union-Find
     */
    //==================================================================================================
// ALGORITMO DE KRUSKAL (COM DISJOINT SET)
//==================================================================================================

public List<ArestaComPeso<T>> kruskal() {
    System.out.println("=== ALGORITMO DE KRUSKAL ===");

    // Lista de todas as arestas únicas (sem duplicatas)
    List<ArestaComPeso<T>> arestasUnicas = obterArestasUnicasComPeso();
    
    // Ordena arestas por peso
    arestasUnicas.sort(Comparator.comparingDouble(ArestaComPeso::getPeso));

    System.out.println("Arestas ordenadas por peso:");
    for (ArestaComPeso<T> aresta : arestasUnicas) {
        System.out.println("  " + aresta.getVerticeInicio().getDado() + " - " + 
                          aresta.getVerticeFim().getDado() + " (peso: " + aresta.getPeso() + ")");
    }

    // Usa a classe DisjointSet em vez da implementação manual
    DisjointSet<T> disjointSet = new DisjointSet<>();
    
    // Inicializa conjuntos para todos os vértices
    for (Vertice<T> v : vertices) {
        disjointSet.makeSet(v.getDado());
    }

    List<ArestaComPeso<T>> agm = new ArrayList<>();
    double custoTotal = 0.0;
    int passo = 1;

    System.out.println("\nProcessando arestas:");
    for (ArestaComPeso<T> aresta : arestasUnicas) {
        T u = aresta.getVerticeInicio().getDado();
        T v = aresta.getVerticeFim().getDado();
        double peso = aresta.getPeso();

        T raizU = disjointSet.findSet(u);
        T raizV = disjointSet.findSet(v);

        System.out.println("\nPasso " + passo++ + ": Aresta " + u + " - " + v + " (peso: " + peso + ")");
        System.out.println("  Conjunto de " + u + ": " + raizU);
        System.out.println("  Conjunto de " + v + ": " + raizV);

        if (!raizU.equals(raizV)) {
            agm.add(aresta);
            custoTotal += peso;
            disjointSet.union(u, v);
            System.out.println("  ADICIONADA - une conjuntos " + raizU + " e " + raizV);
        } else {
            System.out.println("  REJEITADA - formaria ciclo");
        }
    }

    exibirResultadoKruskal(agm, custoTotal);
    return agm;
}

    /**
     * Obtém arestas únicas (sem duplicatas)
     */
    private List<ArestaComPeso<T>> obterArestasUnicasComPeso() {
        List<ArestaComPeso<T>> arestasUnicas = new ArrayList<>();
        Set<String> arestasProcessadas = new HashSet<>();

        for (Aresta<T> a : arestas) {
            if (a instanceof ArestaComPeso) {
                ArestaComPeso<T> arestaPeso = (ArestaComPeso<T>) a;
                T u = arestaPeso.getVerticeInicio().getDado();
                T v = arestaPeso.getVerticeFim().getDado();
                String chave = gerarChaveAresta(u, v);

                if (arestasProcessadas.add(chave)) {
                    arestasUnicas.add(arestaPeso);
                }
            }
        }
        return arestasUnicas;
    }

    /**
     * Exibe resultados do algoritmo de Kruskal
     */
    private void exibirResultadoKruskal(List<ArestaComPeso<T>> agm, double custoTotal) {
        System.out.println("\n=== RESULTADO DO ALGORITMO DE KRUSKAL ===");
        System.out.println("Custo total da AGM: " + custoTotal);
        System.out.println("Número de arestas na AGM: " + agm.size());
        System.out.println("Número de vértices: " + vertices.size());
        
        if (agm.size() == vertices.size() - 1) {
            System.out.println(" AGM válida (n-1 arestas)");
        } else {
            System.out.println(" AGM inválida - grafo pode não ser conexo");
        }
        
        System.out.println("\nArestas da Árvore Geradora Mínima:");
        for (ArestaComPeso<T> aresta : agm) {
            System.out.println("  " + aresta.getVerticeInicio().getDado() + " - " + 
                              aresta.getVerticeFim().getDado() + " (peso: " + aresta.getPeso() + ")");
        }
        
        // Exibir como árvore
        System.out.println("\nEstrutura da Árvore:");
        exibirComoArvore(agm);
    }

    //==================================================================================================
    // MÉTODOS AUXILIARES PARA AGM
    //==================================================================================================

    /**
     * Cria aresta para representação da AGM
     */
    private ArestaComPeso<T> criarArestaAGM(T u, T v, double peso) {
        ArestaComPeso<T> aresta = new ArestaComPeso<>();
        aresta.setVerticeInicio(getVertice(u));
        aresta.setVerticeFim(getVertice(v));
        aresta.setPeso(peso);
        aresta.setDescricao(u + "-" + v + "(AGM)");
        return aresta;
    }

    /**
     * Exibe a AGM como árvore hierárquica
     */
    private void exibirComoArvore(List<ArestaComPeso<T>> agm) {
        if (agm.isEmpty()) {
            System.out.println("Árvore vazia");
            return;
        }

        // Encontra a raiz (vértice sem arestas de entrada na representação direcionada)
        Map<T, List<T>> arvore = new HashMap<>();
        Set<T> todosVertices = new HashSet<>();
        
        // Constrói a árvore como grafo direcionado a partir das arestas
        for (ArestaComPeso<T> aresta : agm) {
            T pai = aresta.getVerticeInicio().getDado();
            T filho = aresta.getVerticeFim().getDado();
            
            arvore.computeIfAbsent(pai, k -> new ArrayList<>()).add(filho);
            todosVertices.add(pai);
            todosVertices.add(filho);
        }

        // Encontra a raiz (vértice que não aparece como filho)
        T raiz = null;
        Set<T> filhos = new HashSet<>();
        for (List<T> listaFilhos : arvore.values()) {
            filhos.addAll(listaFilhos);
        }
        
        for (T vertice : todosVertices) {
            if (!filhos.contains(vertice)) {
                raiz = vertice;
                break;
            }
        }

        if (raiz == null && !todosVertices.isEmpty()) {
            raiz = todosVertices.iterator().next();
        }

        if (raiz != null) {
            System.out.println("Raiz: " + raiz);
            exibirArvoreRecursivo(arvore, raiz, 0);
        } else {
            System.out.println("Não foi possível determinar a raiz da árvore");
        }
    }

    /**
     * Exibe árvore recursivamente com indentação
     */
    private void exibirArvoreRecursivo(Map<T, List<T>> arvore, T vertice, int nivel) {
        String indentacao = "  ".repeat(nivel);
        System.out.println(indentacao + "└── " + vertice);
        
        List<T> filhos = arvore.get(vertice);
        if (filhos != null) {
            for (T filho : filhos) {
                exibirArvoreRecursivo(arvore, filho, nivel + 1);
            }
        }
    }

    //==================================================================================================
    // COMPARAÇÃO PRIM vs KRUSKAL
    //==================================================================================================

    /**
     * Compara os algoritmos de Prim e Kruskal
     */
    public void compararAGMs(T verticeInicialPrim) {
        System.out.println("=" .repeat(70));
        System.out.println("COMPARAÇÃO PRIM vs KRUSKAL");
        System.out.println("=" .repeat(70));
        
        long startTime, endTime;
        
        // Algoritmo de Prim
        System.out.println("\n" + "=" .repeat(30) + " PRIM " + "=" .repeat(30));
        startTime = System.nanoTime();
        List<ArestaComPeso<T>> agmPrim = prim(verticeInicialPrim);
        endTime = System.nanoTime();
        long tempoPrim = endTime - startTime;
        
        // Algoritmo de Kruskal
        System.out.println("\n" + "=" .repeat(30) + " KRUSKAL " + "=" .repeat(28));
        startTime = System.nanoTime();
        List<ArestaComPeso<T>> agmKruskal = kruskal();
        endTime = System.nanoTime();
        long tempoKruskal = endTime - startTime;
        
        // Comparação
        System.out.println("\n" + "=" .repeat(30) + " COMPARAÇÃO " + "=" .repeat(27));
        System.out.println("Tempo de execução Prim: " + tempoPrim / 1000000.0 + " ms");
        System.out.println("Tempo de execução Kruskal: " + tempoKruskal / 1000000.0 + " ms");
        
        double custoPrim = calcularCustoAGM(agmPrim);
        double custoKruskal = calcularCustoAGM(agmKruskal);
        
        System.out.println("Custo Prim: " + custoPrim);
        System.out.println("Custo Kruskal: " + custoKruskal);
        
        if (Math.abs(custoPrim - custoKruskal) < 0.0001) {
            System.out.println(" CUSTOS IGUAIS - Ambos os algoritmos encontraram a AGM ótima");
        } else {
            System.out.println(" CUSTOS DIFERENTES - Possível erro na implementação");
        }
        
        System.out.println("Número de arestas Prim: " + agmPrim.size());
        System.out.println("Número de arestas Kruskal: " + agmKruskal.size());
        
        // Verifica se as AGMs são iguais (mesmo conjunto de arestas)
        if (saoAGMsIguais(agmPrim, agmKruskal)) {
            System.out.println("✓ AGMs IDÊNTICAS - Mesmo conjunto de arestas");
        } else {
            System.out.println("⚠ AGMs DIFERENTES - Conjuntos de arestas diferentes mas custo igual");
            System.out.println("  (Pode haver múltiplas AGMs com mesmo custo)");
        }
    }

    /**
     * Calcula custo total de uma AGM
     */
    private double calcularCustoAGM(List<ArestaComPeso<T>> agm) {
        return agm.stream().mapToDouble(ArestaComPeso::getPeso).sum();
    }

    /**
     * Verifica se duas AGMs são iguais
     */
    private boolean saoAGMsIguais(List<ArestaComPeso<T>> agm1, List<ArestaComPeso<T>> agm2) {
        if (agm1.size() != agm2.size()) return false;
        
        Set<String> arestas1 = new HashSet<>();
        for (ArestaComPeso<T> a : agm1) {
            arestas1.add(gerarChaveAresta(a.getVerticeInicio().getDado(), a.getVerticeFim().getDado()));
        }
        
        Set<String> arestas2 = new HashSet<>();
        for (ArestaComPeso<T> a : agm2) {
            arestas2.add(gerarChaveAresta(a.getVerticeInicio().getDado(), a.getVerticeFim().getDado()));
        }
        
        return arestas1.equals(arestas2);
    }

}
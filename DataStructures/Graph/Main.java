import java.util.*;

/**
 * SISTEMA INTERATIVO PARA MANIPULAÇÃO DE GRAFOS
 * INTERFACE DE USUÁRIO COM MENU PARA OPERAÇÕES EM GRAFOS DIRIGIDOS E NÃO DIRIGIDOS
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static GrafoNaoDirigido<String> grafoNaoDirigido = null;
    private static GrafoDirigido<String> grafoDirigido = null;
    private static boolean grafoNaoDirigidoCriado = false;
    private static boolean grafoDirigidoCriado = false;

    public static void main(String[] args) {
        menuPrincipal();
    }

    /**
     * LIMPA O TERMINAL 
     */
    private static void limparTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // FALLBACK: IMPRIME VÁRIAS LINHAS EM BRANCO
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    private static void pausar() {
        System.out.print("\nPRESSIONE ENTER PARA CONTINUAR...");
        scanner.nextLine();
    }

    /**
     * MENU PRINCIPAL DO SISTEMA
     */
    private static void menuPrincipal() {
        while (true) {
            limparTerminal();
            System.out.println("=======================================================");
            System.out.println("                   MENU PRINCIPAL                   ");
            System.out.println("=======================================================");
            
            // EXIBIR STATUS DOS GRAFOS
            System.out.println("\nSTATUS DOS GRAFOS:");
            if (grafoDirigidoCriado) {
                System.out.println("   [DIRIGIDO]    Vértices: " + grafoDirigido.getOrdem() + 
                                " | Arestas: " + grafoDirigido.getTamanho());
            } else {
                System.out.println("   [DIRIGIDO]    Não criado");
            }
            
            if (grafoNaoDirigidoCriado) {
                System.out.println("   [NAO DIRIGIDO] Vértices: " + grafoNaoDirigido.getOrdem() + 
                                " | Arestas: " + grafoNaoDirigido.getTamanho());
            } else {
                System.out.println("   [NAO DIRIGIDO] Não criado");
            }

            System.out.println("\nOPERACOES BASICAS:");
            System.out.println("[1] CRIAR NOVO GRAFO");
            
            if (grafoDirigidoCriado || grafoNaoDirigidoCriado) {
                System.out.println("\nOPERACOES AVANCADAS:");
                System.out.println("[2] OPERACOES BASICAS DO GRAFO");
                System.out.println("[3] ALGORITMOS DE BUSCA");
                System.out.println("[4] ALGORITMOS DE CAMINHOS MINIMOS");
                System.out.println("[5] ALGORITMOS DE ARVORES GERADORAS MINIMAS");
                System.out.println("[6] ALGORITMOS DE CONECTIVIDADE E EULERIANOS");
                System.out.println("[7] EXIBIR INFORMACOES DO GRAFO");
                System.out.println("[8] LIMPAR GRAFOS");
            }
            System.out.println("\n[0] SAIR");

            System.out.print("\nESCOLHA UMA OPCAO: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    menuCriarGrafo();
                    break;
                case "2":
                    if (grafoDirigidoCriado || grafoNaoDirigidoCriado) menuOperacoesBasicas();
                    else opcaoInvalida();
                    break;
                case "3":
                    if (grafoDirigidoCriado || grafoNaoDirigidoCriado) menuBusca();
                    else opcaoInvalida();
                    break;
                case "4":
                    if (grafoDirigidoCriado || grafoNaoDirigidoCriado) menuCaminhosMinimos();
                    else opcaoInvalida();
                    break;
                case "5":
                    if (grafoDirigidoCriado || grafoNaoDirigidoCriado) menuAGM();
                    else opcaoInvalida();
                    break;
                case "6":
                    if (grafoDirigidoCriado || grafoNaoDirigidoCriado) menuConectividade();
                    else opcaoInvalida();
                    break;
                case "7":
                    if (grafoDirigidoCriado || grafoNaoDirigidoCriado) exibirInformacoesGrafo();
                    else opcaoInvalida();
                    break;
                case "8":
                    if (grafoDirigidoCriado || grafoNaoDirigidoCriado) limparGrafos();
                    else opcaoInvalida();
                    break;
                case "0":
                    System.out.println("\nOBRIGADO POR USAR O SISTEMA! ATE LOGO!");
                    return;
                default:
                    opcaoInvalida();
            }
        }
    }

    /**
     * MENU PARA CRIACAO DE GRAFO
     */
    private static void menuCriarGrafo() {
        while (true) {
            limparTerminal();
            System.out.println("=======================================================");
            System.out.println("                  CRIAR NOVO GRAFO                   ");
            System.out.println("=======================================================");
            
            System.out.println("\nESCOLHA O TIPO DE GRAFO:");
            System.out.println("[1] GRAFO DIRIGIDO (ARESTAS COM DIRECAO)");
            System.out.println("[2] GRAFO NAO DIRIGIDO (ARESTAS BIDIRECIONAIS)");
            System.out.println("[3] VOLTAR AO MENU PRINCIPAL");

            System.out.print("\nESCOLHA: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    grafoDirigido = new GrafoDirigido<>();
                    grafoDirigidoCriado = true;
                    System.out.println("\nGRAFO DIRIGIDO CRIADO COM SUCESSO!");
                    pausar();
                    return;
                case "2":
                    grafoNaoDirigido = new GrafoNaoDirigido<>();
                    grafoNaoDirigidoCriado = true;
                    System.out.println("\nGRAFO NAO DIRIGIDO CRIADO COM SUCESSO!");
                    pausar();
                    return;
                case "3":
                    return;
                default:
                    System.out.println("OPCAO INVALIDA!");
                    pausar();
            }
        }
    }

    /**
     * MENU DE OPERACOES BASICAS DO GRAFO
     */
    private static void menuOperacoesBasicas() {
        while (true) {
            limparTerminal();
            System.out.println("=======================================================");
            System.out.println("               OPERACOES BASICAS                  ");
            System.out.println("=======================================================");
            
            System.out.println("\nOPERACOES DISPONIVEIS:");
            System.out.println("[1]  INSERIR VERTICE");
            System.out.println("[2]  INSERIR ARESTA");
            System.out.println("[3]  INSERIR ARESTA COM PESO");
            System.out.println("[4]  REMOVER VERTICE");
            System.out.println("[5]  REMOVER ARESTA");
            System.out.println("[6]  BUSCAR VERTICE");
            System.out.println("[7]  LISTAR VERTICES");
            System.out.println("[8]  LISTAR ARESTAS");
            System.out.println("[9]  CALCULAR GRAU DE VERTICE");
            System.out.println("[10] VOLTAR AO MENU PRINCIPAL");

            System.out.print("\nESCOLHA UMA OPCAO: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    inserirVertice();
                    break;
                case "2":
                    inserirAresta(false);
                    break;
                case "3":
                    inserirAresta(true);
                    break;
                case "4":
                    removerVertice();
                    break;
                case "5":
                    removerAresta();
                    break;
                case "6":
                    buscarVertice();
                    break;
                case "7":
                    listarVertices();
                    break;
                case "8":
                    listarArestas();
                    break;
                case "9":
                    calcularGrau();
                    break;
                case "10":
                    return;
                default:
                    opcaoInvalida();
            }
        }
    }

    /**
     * MENU DE ALGORITMOS DE BUSCA
     */
    private static void menuBusca() {
        while (true) {
            limparTerminal();
            System.out.println("=======================================================");
            System.out.println("                ALGORITMOS DE BUSCA               ");
            System.out.println("=======================================================");
            
            System.out.println("\nALGORITMOS DISPONIVEIS:");
            System.out.println("[1] BUSCA EM PROFUNDIDADE (DFS) - TODO O GRAFO");
            System.out.println("[2] BUSCA EM LARGURA (BFS) - A PARTIR DE VERTICE");
            System.out.println("[3] ENCONTRAR CAMINHO ENTRE DOIS VERTICES");
            System.out.println("[4] VOLTAR AO MENU PRINCIPAL");

            System.out.print("\nESCOLHA UMA OPCAO: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    executarDFS();
                    break;
                case "2":
                    executarBFS();
                    break;
                case "3":
                    encontrarCaminho();
                    break;
                case "4":
                    return;
                default:
                    opcaoInvalida();
            }
        }
    }

    /**
     * MENU DE ALGORITMOS DE CAMINHOS MINIMOS
     */
    private static void menuCaminhosMinimos() {
        while (true) {
            limparTerminal();
            System.out.println("=======================================================");
            System.out.println("            CAMINHOS MINIMOS                    ");
            System.out.println("=======================================================");
            
            System.out.println("\nALGORITMOS DISPONIVEIS:");
            System.out.println("[1] DIJKSTRA - CAMINHOS A PARTIR DE UMA FONTE");
            System.out.println("[2] FLOYD-WARSHALL - TODOS OS PARES DE CAMINHOS");
            System.out.println("[3] COMPARAR DIJKSTRA VS FLOYD-WARSHALL");
            System.out.println("[4] VOLTAR AO MENU PRINCIPAL");

            System.out.print("\nESCOLHA UMA OPCAO: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    executarDijkstra();
                    break;
                case "2":
                    executarFloydWarshall();
                    break;
                case "3":
                    compararCaminhosMinimos();
                    break;
                case "4":
                    return;
                default:
                    opcaoInvalida();
            }
        }
    }

    /**
     * MENU DE ALGORITMOS DE ARVORES GERADORAS MINIMAS
     */
    private static void menuAGM() {
        while (true) {
            limparTerminal();
            System.out.println("=======================================================");
            System.out.println("         ARVORES GERADORAS MINIMAS              ");
            System.out.println("=======================================================");
            
            System.out.println("\nALGORITMOS DISPONIVEIS:");
            System.out.println("[1] PRIM - BASEADO EM VERTICES");
            System.out.println("[2] KRUSKAL - BASEADO EM ARESTAS");
            System.out.println("[3] COMPARAR PRIM VS KRUSKAL");
            System.out.println("[4] VOLTAR AO MENU PRINCIPAL");

            System.out.print("\nESCOLHA UMA OPCAO: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    executarPrim();
                    break;
                case "2":
                    executarKruskal();
                    break;
                case "3":
                    compararAGMs();
                    break;
                case "4":
                    return;
                default:
                    opcaoInvalida();
            }
        }
    }

    /**
     * MENU DE CONECTIVIDADE E ALGORITMOS EULERIANOS
     */
    private static void menuConectividade() {
        while (true) {
            limparTerminal();
            System.out.println("=======================================================");
            System.out.println("         CONECTIVIDADE E EULERIANOS             ");
            System.out.println("=======================================================");
            
            System.out.println("\nOPERACOES DISPONIVEIS:");
            if (grafoNaoDirigidoCriado) {
                System.out.println("[1] COMPONENTES CONEXAS (DFS)");
                System.out.println("[2] VERIFICAR SE GRAFO E CONEXO");
                System.out.println("[3] VERIFICAR SE E EULERIANO");
                System.out.println("[4] DIAGNOSTICO EULERIANO DETALHADO");
                System.out.println("[5] ENCONTRAR CICLO EULERIANO (HIERHOLZER)");
            }
            System.out.println("[6] VOLTAR AO MENU PRINCIPAL");

            System.out.print("\nESCOLHA UMA OPCAO: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    if (grafoNaoDirigidoCriado) componentesConexas();
                    else opcaoInvalida();
                    break;
                case "2":
                    if (grafoNaoDirigidoCriado) verificarConexo();
                    else opcaoInvalida();
                    break;
                case "3":
                    if (grafoNaoDirigidoCriado) verificarEuleriano();
                    else opcaoInvalida();
                    break;
                case "4":
                    if (grafoNaoDirigidoCriado) diagnosticoEuleriano();
                    else opcaoInvalida();
                    break;
                case "5":
                    if (grafoNaoDirigidoCriado) encontrarCicloEuleriano();
                    else opcaoInvalida();
                    break;
                case "6":
                    return;
                default:
                    opcaoInvalida();
            }
        }
    }

    // ===================================================================
    // IMPLEMENTACAO DOS METODOS DE OPERACOES
    // ===================================================================

    private static void selecionarGrafoParaOperacao(Runnable operacaoDirigido, Runnable operacaoNaoDirigido) {
        if (grafoDirigidoCriado && grafoNaoDirigidoCriado) {
            System.out.println("\nSELECIONE O GRAFO PARA OPERACAO:");
            System.out.println("[1] GRAFO DIRIGIDO");
            System.out.println("[2] GRAFO NAO DIRIGIDO");
            System.out.print("ESCOLHA: ");
            String escolha = scanner.nextLine();
            
            if (escolha.equals("1")) {
                operacaoDirigido.run();
            } else if (escolha.equals("2")) {
                operacaoNaoDirigido.run();
            } else {
                System.out.println("OPCAO INVALIDA!");
            }
        } else if (grafoDirigidoCriado) {
            operacaoDirigido.run();
        } else if (grafoNaoDirigidoCriado) {
            operacaoNaoDirigido.run();
        }
    }

    private static void inserirVertice() {
        System.out.print("\nDIGITE O NOME DO VERTICE: ");
        String vertice = scanner.nextLine();
        
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.inserirVertice(vertice),
            () -> grafoNaoDirigido.inserirVertice(vertice)
        );
        System.out.println("VERTICE '" + vertice + "' INSERIDO COM SUCESSO!");
        pausar();
    }

    private static void inserirAresta(boolean comPeso) {
        System.out.print("\nDIGITE O VERTICE DE ORIGEM: ");
        String origem = scanner.nextLine();
        System.out.print("DIGITE O VERTICE DE DESTINO: ");
        String destino = scanner.nextLine();
        
        if (comPeso) {
            System.out.print("DIGITE O PESO DA ARESTA: ");
            try {
                double peso = Double.parseDouble(scanner.nextLine());
                selecionarGrafoParaOperacao(
                    () -> grafoDirigido.inserirArestaComPeso(origem, destino, peso),
                    () -> grafoNaoDirigido.inserirArestaComPeso(origem, destino, peso)
                );
                System.out.println("ARESTA COM PESO " + peso + " INSERIDA: " + origem + " -> " + destino);
            } catch (NumberFormatException e) {
                System.out.println("PESO INVALIDO! USE NUMEROS (EX: 2.5)");
            }
        } else {
            selecionarGrafoParaOperacao(
                () -> grafoDirigido.inserirAresta(origem, destino),
                () -> grafoNaoDirigido.inserirAresta(origem, destino)
            );
            System.out.println("ARESTA INSERIDA: " + origem + " -> " + destino);
        }
        pausar();
    }

    private static void removerVertice() {
        System.out.print("\nDIGITE O VERTICE A SER REMOVIDO: ");
        String vertice = scanner.nextLine();
        
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.removerV(vertice),
            () -> grafoNaoDirigido.removerV(vertice)
        );
        pausar();
    }

    private static void removerAresta() {
        System.out.print("\nDIGITE A DESCRICAO DA ARESTA A SER REMOVIDA: ");
        String aresta = scanner.nextLine();
        
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.removerA(aresta),
            () -> grafoNaoDirigido.removerA(aresta)
        );
        pausar();
    }

    private static void buscarVertice() {
        System.out.print("\nDIGITE O VERTICE A SER BUSCADO: ");
        String vertice = scanner.nextLine();
        
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.buscarVertice(vertice),
            () -> grafoNaoDirigido.buscarVertice(vertice)
        );
        pausar();
    }

    private static void listarVertices() {
        System.out.println("\nVERTICES DO GRAFO:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.vertices(),
            () -> grafoNaoDirigido.vertices()
        );
        pausar();
    }

    private static void listarArestas() {
        System.out.println("\nARESTAS DO GRAFO:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.arestas(),
            () -> grafoNaoDirigido.arestas()
        );
        pausar();
    }

    private static void calcularGrau() {
        System.out.print("\nDIGITE O VERTICE PARA CALCULAR O GRAU: ");
        String vertice = scanner.nextLine();
        
        selecionarGrafoParaOperacao(
            () -> {
                int grauEntrada = grafoDirigido.grauE(vertice);
                int grauSaida = grafoDirigido.grauS(vertice);
                int grauTotal = grafoDirigido.grau(vertice);
                System.out.println("GRAU DE ENTRADA: " + grauEntrada);
                System.out.println("GRAU DE SAIDA: " + grauSaida);
                System.out.println("GRAU TOTAL: " + grauTotal);
            },
            () -> {
                int grau = grafoNaoDirigido.grau(vertice);
                System.out.println("GRAU DO VERTICE: " + grau);
            }
        );
        pausar();
    }

    private static void executarDFS() {
        System.out.println("\nEXECUTANDO BUSCA EM PROFUNDIDADE (DFS):");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.dfs(),
            () -> grafoNaoDirigido.dfs()
        );
        pausar();
    }

    private static void executarBFS() {
        System.out.print("\nDIGITE O VERTICE DE ORIGEM PARA BFS: ");
        String origem = scanner.nextLine();
        
        System.out.println("\nEXECUTANDO BUSCA EM LARGURA (BFS):");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.bfs(origem),
            () -> grafoNaoDirigido.bfs(origem)
        );
        pausar();
    }

    private static void encontrarCaminho() {
        System.out.print("\nDIGITE O VERTICE DE ORIGEM: ");
        String origem = scanner.nextLine();
        System.out.print("DIGITE O VERTICE DE DESTINO: ");
        String destino = scanner.nextLine();
        
        System.out.println("\nCAMINHO ENTRE " + origem + " E " + destino + ":");
        selecionarGrafoParaOperacao(
            () -> {
                // PARA GRAFO DIRIGIDO, PRECISAMOS DE UMA IMPLEMENTACAO ESPECIFICA
                System.out.println("FUNCIONALIDADE ESPECIFICA PARA GRAFO DIRIGIDO EM DESENVOLVIMENTO");
            },
            () -> grafoNaoDirigido.imprimirCaminho(origem, destino)
        );
        pausar();
    }

    private static void executarDijkstra() {
        System.out.print("\nDIGITE O VERTICE DE ORIGEM PARA DIJKSTRA: ");
        String origem = scanner.nextLine();
        
        System.out.println("\nEXECUTANDO ALGORITMO DE DIJKSTRA:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.dijkstra(origem),
            () -> grafoNaoDirigido.dijkstra(origem)
        );
        pausar();
    }

    private static void executarFloydWarshall() {
        System.out.println("\nEXECUTANDO ALGORITMO DE FLOYD-WARSHALL:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.floydWarshall(),
            () -> grafoNaoDirigido.floydWarshall()
        );
        pausar();
    }

    private static void compararCaminhosMinimos() {
        System.out.print("\nDIGITE O VERTICE DE ORIGEM PARA COMPARACAO: ");
        String origem = scanner.nextLine();
        
        System.out.println("\nCOMPARANDO DIJKSTRA VS FLOYD-WARSHALL:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.compararAlgoritmos(origem),
            () -> grafoNaoDirigido.compararAlgoritmos(origem)
        );
        pausar();
    }

    private static void executarPrim() {
        System.out.print("\nDIGITE O VERTICE INICIAL PARA PRIM: ");
        String verticeInicial = scanner.nextLine();
        
        System.out.println("\nEXECUTANDO ALGORITMO DE PRIM:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.prim(verticeInicial),
            () -> grafoNaoDirigido.prim(verticeInicial)
        );
        pausar();
    }

    private static void executarKruskal() {
        System.out.println("\nEXECUTANDO ALGORITMO DE KRUSKAL:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.kruskal(),
            () -> grafoNaoDirigido.kruskal()
        );
        pausar();
    }

    private static void compararAGMs() {
        System.out.print("\nDIGITE O VERTICE INICIAL PARA PRIM (KRUSKAL NAO PRECISA): ");
        String verticeInicial = scanner.nextLine();
        
        System.out.println("\nCOMPARANDO PRIM VS KRUSKAL:");
        selecionarGrafoParaOperacao(
            () -> grafoDirigido.compararAGMs(verticeInicial),
            () -> grafoNaoDirigido.compararAGMs(verticeInicial)
        );
        pausar();
    }

    private static void componentesConexas() {
        System.out.println("\nCOMPONENTES CONEXAS (DFS):");
        grafoNaoDirigido.componentesConexasDFS();
        pausar();
    }

    private static void verificarConexo() {
        System.out.println("\nVERIFICANDO CONEXIDADE:");
        boolean conexo = grafoNaoDirigido.isConexo();
        System.out.println("O GRAFO " + (conexo ? "E" : "NAO E") + " CONEXO");
        pausar();
    }

    private static void verificarEuleriano() {
        System.out.println("\nVERIFICANDO SE E EULERIANO:");
        boolean euleriano = grafoNaoDirigido.isEuleriano();
        System.out.println("O GRAFO " + (euleriano ? "E" : "NAO E") + " EULERIANO");
        pausar();
    }

    private static void diagnosticoEuleriano() {
        System.out.println("\nDIAGNOSTICO EULERIANO DETALHADO:");
        grafoNaoDirigido.diagnosticoEulerianoDetalhado();
        pausar();
    }

    private static void encontrarCicloEuleriano() {
        System.out.println("\nBUSCANDO CICLO EULERIANO (HIERHOLZER):");
        grafoNaoDirigido.encontrarCicloEuleriano();
        pausar();
    }

    private static void exibirInformacoesGrafo() {
        limparTerminal();
        System.out.println("=======================================================");
        System.out.println("              INFORMACOES DO GRAFO                ");
        System.out.println("=======================================================");
        
        if (grafoDirigidoCriado) {
            System.out.println("\nGRAFO DIRIGIDO:");
            System.out.println("ORDEM (VERTICES): " + grafoDirigido.getOrdem());
            System.out.println("TAMANHO (ARESTAS): " + grafoDirigido.getTamanho());
            System.out.println("\nLISTA DE ADJACENCIA:");
            grafoDirigido.exibirGrafo();
        }
        
        if (grafoNaoDirigidoCriado) {
            System.out.println("\nGRAFO NAO DIRIGIDO:");
            System.out.println("ORDEM (VERTICES): " + grafoNaoDirigido.getOrdem());
            System.out.println("TAMANHO (ARESTAS): " + grafoNaoDirigido.getTamanho());
            System.out.println("\nLISTA DE ADJACENCIA:");
            grafoNaoDirigido.exibirGrafo();
        }
        pausar();
    }

    private static void limparGrafos() {
        System.out.print("\nTEM CERTEZA QUE DESEJA LIMPAR TODOS OS GRAFOS? (S/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            grafoDirigido = null;
            grafoNaoDirigido = null;
            grafoDirigidoCriado = false;
            grafoNaoDirigidoCriado = false;
            System.out.println("GRAFOS LIMPOS COM SUCESSO!");
        } else {
            System.out.println("OPERACAO CANCELADA.");
        }
        pausar();
    }

    private static void opcaoInvalida() {
        System.out.println("OPCAO INVALIDA! TENTE NOVAMENTE.");
        pausar();
    }
}
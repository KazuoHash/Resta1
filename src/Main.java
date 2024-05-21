import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    private final char[][] tabuleiro =
            {
                    {' ', ' ', 'o', 'o', 'o', ' ', ' '},
                    {' ', ' ', 'o', 'o', 'o', ' ', ' '},
                    {'o', 'o', 'o', 'o', 'o', 'o', 'o'},
                    {'o', 'o', 'o', '.', 'o', 'o', 'o'},
                    {'o', 'o', 'o', 'o', 'o', 'o', 'o'},
                    {' ', ' ', 'o', 'o', 'o', ' ', ' '},
                    {' ', ' ', 'o', 'o', 'o', ' ', ' '}
            };

    private final int height = tabuleiro.length;
    private final int length = tabuleiro[height - 1].length;
    private int pecas = 32;
    private int movDesfeito = 0;
    private final String[] Direcoes = {"Cima", "Direita", "Baixo", "Esquerda"};
    private final List<String[]> movimentos = new ArrayList<>();

    private void run() {

        if (!solucao())
            System.out.println("\nSolucao não encontrada!!");
    }

    private boolean solucao() {

        if (pecas == 1 && tabuleiro[3][3] == 'o') {
            System.out.println("\nSolucao encontrada!");
            System.out.println("\nQuantidade de Movimentos Desfeitos: " + movDesfeito);
            System.out.println( "\nSequencia de Movimentos para a solucao:" );
            printMovimentos();
            return true;
        } else {
            for (int i = 0; i < length; i++)
                for (int j = 0; j < height; j++)
                    for (String direcoe : Direcoes) {
                        int[][] move = determinaMovimento(j, i, direcoe);
                        if (movimentoLegal(move)) {
                            fazMovimento(move);

                            System.out.println("Processando, Aguarde!!");
                            printTabuleiro();
                            pausaMeioSegundo();

                            if (solucao())
                                return true;
                            System.out.println("Voltando Movimento!");
                            movDesfeito++;
                            voltar(move);
                            printTabuleiro();
                            pausaMeioSegundo();

                        }
                    }

            return false;
        }
    }

    private int[][] determinaMovimento(int linha, int coluna, String direcao) {
        int[][] move = new int[3][2];
        move[0][0] = linha;
        move[0][1] = coluna;

        switch (direcao) {

            case "Cima":
                move[1][0] = linha + 1;
                move[1][1] = coluna;
                move[2][0] = linha + 2;
                move[2][1] = coluna;
                break;

            case "Esquerda":
                move[1][0] = linha;
                move[1][1] = coluna - 1;
                move[2][0] = linha;
                move[2][1] = coluna - 2;
                break;

            case "Direita":
                move[1][0] = linha;
                move[1][1] = coluna + 1;
                move[2][0] = linha;
                move[2][1] = coluna + 2;
                break;

            case "Baixo":
                move[1][0] = linha - 1;
                move[1][1] = coluna;
                move[2][0] = linha - 2;
                move[2][1] = coluna;
                break;

            default:

                break;
        }

        return move;
    }

    private void fazMovimento(int[][] move) {
        tabuleiro[move[0][0]][move[0][1]] = '.';
        tabuleiro[move[1][0]][move[1][1]] = '.';
        tabuleiro[move[2][0]][move[2][1]] = 'o';

        pecas--;

        String[] movimento = {
                coordenadas(move[0]),
                coordenadas(move[1]),
                coordenadas(move[2])
        };
        movimentos.add(movimento);
    }

    private void voltar(int[][] move) {
        tabuleiro[move[0][0]][move[0][1]] = 'o';
        tabuleiro[move[1][0]][move[1][1]] = 'o';
        tabuleiro[move[2][0]][move[2][1]] = '.';

        pecas++;

        if (!movimentos.isEmpty()) {
            movimentos.removeLast();
        }
    }

    private boolean movimentoLegal(int[][] move) {
        if (move[2][0] >= 7 || move[2][1] >= 7 || move[2][0] < 0 || move[2][1] < 0)
            return false;

        return (tabuleiro[move[0][0]][move[0][1]] == 'o') &&
                (tabuleiro[move[1][0]][move[1][1]] == 'o') &&
                (tabuleiro[move[2][0]][move[2][1]] == '.');
    }

    private void printTabuleiro() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();
    }

    private String coordenadas(int[] coordenadas) {
        int linha = coordenadas[0];
        int coluna = coordenadas[1];

        char letraColuna = (char) ('A' + coluna);
        int numeroLinha = linha + 1;

        return "(" + letraColuna + numeroLinha + ")";
    }

    private void printMovimentos() {
        for (String[] movimento : movimentos) {
            String posicaoPecaMovida = movimento[0];
            String pecaRetirada = movimento[1];
            String novaPosicaoPecaMovida = movimento[2];
            System.out.println( posicaoPecaMovida + pecaRetirada + novaPosicaoPecaMovida);
        }
    }
    private void pausaMeioSegundo() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

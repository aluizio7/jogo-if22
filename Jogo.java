import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Jogo {
    private static final int MAX_JOGADORES = 11;
    private List<Jogador> jogadores;
    private Dado dado1;
    private Dado dado2;
    private Scanner scanner;
    private String arquivoResultados = "resultados.txt";

    public Jogo() {
        jogadores = new ArrayList<>();
        dado1 = new Dado();
        dado2 = new Dado();
        scanner = new Scanner(System.in);
    }

    public void inserirJogadores(String nome) {
        if (jogadores.size() < MAX_JOGADORES) {
            jogadores.add(new Jogador(nome));
        } else {
            System.out.println("Número máximo de jogadores atingido.");
        }
    }

    public void escolherValor() {
        Set<Integer> apostas = new HashSet<>();
        for (Jogador jogador : jogadores) {
            int aposta;
            while (true) {
                System.out.print("Jogador " + jogador.getNome() + ", insira sua aposta (entre 2 e 12, valor único): ");
                aposta = scanner.nextInt();
                if (aposta < 2 || aposta > 12) {
                    System.out.println("A aposta deve estar entre 2 e 12.");
                } else if (apostas.contains(aposta)) {
                    System.out.println("Aposta já escolhida por outro jogador. Escolha outro valor.");
                } else {
                    apostas.add(aposta);
                    break;
                }
            }
            jogador.setAposta(aposta);
        }
    }

    public void lançarDados() {
        int resultadoDado1 = dado1.rolar();
        int resultadoDado2 = dado2.rolar();
        int soma = resultadoDado1 + resultadoDado2;

        apresentarResultado(resultadoDado1, resultadoDado2, soma);
    }

    public void apresentarResultado(int resultadoDado1, int resultadoDado2, int soma) {
        System.out.println("Resultado dos dados: " + resultadoDado1 + " + " + resultadoDado2 + " = " + soma);

        Jogador vencedor = null;
        for (Jogador jogador : jogadores) {
            if (jogador.getAposta() == soma) {
                vencedor = jogador;
                break;
            }
        }

        if (vencedor != null) {
            System.out.println("O jogador " + vencedor.getNome() + " venceu com a aposta " + vencedor.getAposta() + "!");
            salvarResultado(vencedor.getNome());
        } else {
            System.out.println("Nenhum jogador acertou. O computador venceu.");
            salvarResultado("Computador");
        }
    }

    private void salvarResultado(String vencedor) {
        try (FileWriter writer = new FileWriter(arquivoResultados, true)) {
            writer.write(vencedor + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar resultado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Insira o número de jogadores (máximo de 11): ");
        int numeroDeJogadores = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha restante

        if (numeroDeJogadores > MAX_JOGADORES) {
            System.out.println("Número de jogadores excede o máximo permitido (11).");
            return;
        }

        for (int i = 1; i <= numeroDeJogadores; i++) {
            System.out.print("Insira o nome do jogador " + i + ": ");
            String nome = scanner.nextLine();
            jogo.inserirJogadores(nome);
        }

        jogo.escolherValor();
        jogo.lançarDados();
    }
}

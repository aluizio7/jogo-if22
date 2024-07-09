public class Jogador {
    private String nome;
    private int aposta;
    
    public Jogador(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getAposta() {
        return aposta;
    }
    
    public void setAposta(int aposta) {
        this.aposta = aposta;
    }
}

package controldespesas.domain;

public class Categoria {
    private int id;
    private String nome;
    private String dataremovido;
    private boolean apagado;
    private Usuario user;
    
    public Categoria() {
    }

    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataremovido() {
        return dataremovido;
    }

    public void setDataremovido(String dataremovido) {
        this.dataremovido = dataremovido;
    }

    
    
    public boolean isApagado() {
        return apagado;
    }

    public void setApagado(boolean apagado) {
        this.apagado = apagado;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    
    
    @Override
    public String toString() {
        return this.nome;
    }
    
    
}

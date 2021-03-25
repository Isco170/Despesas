package controldespesas.domain;

public class Usuario {
    private int id;
    private String nome;
    private String apelido;
    private String usuario;
    private String senha;
    private boolean trabalha;
    private String email;
    private int telefone;

    public Usuario(String nome, String apelido, String usuario, String senha, int telefone, String email) {
        this.nome = nome;
        this.apelido = apelido;
        this.usuario = usuario;
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
    }

    public Usuario() {
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

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isTrabalha() {
        return trabalha;
    }

    public void setTrabalha(boolean trabalha) {
        this.trabalha = trabalha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + ", apelido=" + apelido + ", usuario=" + usuario + ", senha=" + senha + ", trabalha=" + trabalha + ", email=" + email + ", telefone=" + telefone + '}';
    }
    
    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    
}

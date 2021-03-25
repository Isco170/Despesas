package controldespesas.domain;

public class Despesa {

    private int id;
    private String nome;
    private String categoria;
    private String descricao;
    private float valor;
    private String data;
    private String dataremovido;
    private boolean apagado;
    private Usuario usuario;

    public Despesa() {
        this.nome = "Nome";
        this.categoria = "Categoria";
        this.descricao = "Descricao";
        this.valor = 0;

    }

    public Despesa(String categoria, float valor) {
        this.categoria = categoria;
        this.valor = valor;
    }

    public Despesa(String nome, String categoria, String descricao, float valor, String data) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isApagado() {
        return apagado;
    }

    public void setApagado(boolean apagado) {
        this.apagado = apagado;
    }

    public String getDataremovido() {
        return dataremovido;
    }

    public void setDataremovido(String dataremovido) {
        this.dataremovido = dataremovido;
    }

    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

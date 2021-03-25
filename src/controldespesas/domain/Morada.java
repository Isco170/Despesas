package controldespesas.domain;

public class Morada {

    private String idMorada;
    private String avenida;
    private String bairro;
    private String rua;
    private int quarteirao;
    private int nrCasa;
    private int chave;
    private Usuario user;

    public Morada(String avenida, String bairro, String rua, int quarteirao, int nrCasa) {
        
        this.avenida = avenida;
        this.bairro = bairro;
        this.rua = rua;
        this.quarteirao = quarteirao;
        this.nrCasa = nrCasa;
    }

    public Morada() {
    }

    
    public String getIdMorada() {
        return idMorada;
    }

    public void setIdMorada(String idMorada) {
        this.idMorada = idMorada;
    }

    
    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(int quarteirao) {
        this.quarteirao = quarteirao;
    }

    public int getNrCasa() {
        return nrCasa;
    }

    public void setNrCasa(int nrCasa) {
        this.nrCasa = nrCasa;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }
    
    

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}

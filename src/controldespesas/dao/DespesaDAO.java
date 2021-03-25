package controldespesas.dao;

import controldespesas.domain.Despesa;
import controldespesas.domain.Usuario;
import controldespesas.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DespesaDAO {

    public void salvarDespesa(Despesa despesa) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into despesa ");
        sql.append("(despnome, despcategoria, despdescriao, despvalor, despdata, userid) ");
        sql.append("values (?,?,?,?,?,?) ");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, despesa.getNome());
        comando.setString(2, despesa.getCategoria());
        comando.setString(3, despesa.getDescricao());
        comando.setFloat(4, despesa.getValor());
        comando.setString(5, despesa.getData());
        comando.setInt(6, despesa.getUsuario().getId());

        comando.executeUpdate();
        
    }

    public ObservableList<Despesa> listarDespesa(Usuario usuario) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT des.despid, des.despnome, des.despdescriao, des.despcategoria, des.despvalor, des.despdata, des.apagado, us.id, us.usernome ");
        sql.append("from despesa des ");
        sql.append("INNER join usuario us on us.id = des.userId ");
        sql.append("where des.apagado = false and des.userid = ? and des.limpado = false");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, usuario.getId());

        ResultSet resultado = comando.executeQuery();

        ObservableList<Despesa> despesa = FXCollections.observableArrayList();
        while (resultado.next()) {

            Usuario user = new Usuario();
            user.setId(resultado.getInt("us.id"));
            user.setNome(resultado.getString("us.usernome"));

            Despesa des = new Despesa();
            des.setId(resultado.getInt("des.despid"));
            des.setNome(resultado.getString("des.despnome"));
            des.setCategoria(resultado.getString("des.despcategoria"));
            des.setDescricao(resultado.getString("des.despdescriao"));
            des.setValor(resultado.getFloat("des.despvalor"));
            des.setData(resultado.getString("des.despdata"));
            des.setApagado(resultado.getBoolean("des.apagado"));

            des.setUsuario(user);

            despesa.add(des);
        }
        return despesa;
    }
    public ObservableList<Despesa> listarLixeira(Usuario usuario) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("select des.*, us.* ");
        sql.append("from despesa des ");
        sql.append("inner join usuario us on us.id = des.userId ");
        sql.append("where des.apagado = true and des.userid = ? and des.limpado = false ");
        
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, usuario.getId());
        ResultSet resultado = comando.executeQuery();
        ObservableList<Despesa> despesas = FXCollections.observableArrayList();
        while(resultado.next()){
            Usuario user = new Usuario();
            user.setId(resultado.getInt("us.id"));
            user.setNome(resultado.getString("us.usernome"));
            
            Despesa despesa = new Despesa();
            despesa.setId(resultado.getInt("des.despid"));
            despesa.setNome(resultado.getString("des.despnome"));
            despesa.setCategoria(resultado.getString("des.despcategoria"));
            despesa.setDescricao(resultado.getString("des.despdescriao"));
            despesa.setValor(resultado.getFloat("des.despvalor"));
            despesa.setData(resultado.getString("des.despdata"));
            despesa.setApagado(resultado.getBoolean("des.apagado"));
            despesa.setDataremovido(resultado.getString("des.dataremovido"));

            despesa.setUsuario(user);
            despesas.add(despesa);
        }
        return despesas;
    }
    public ObservableList<Despesa> filtrarDespesas(String filtro, Usuario usuario) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("select des.*, us.* ");
        sql.append("from despesa des ");
        sql.append("inner join usuario us on us.id = des.userId ");
        sql.append("where des.apagado = false and des.userid = ? and des.limpado = false and des.despcategoria = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
     
        comando.setInt(1, usuario.getId());
        comando.setString(2, filtro);
        
        ResultSet resultado = comando.executeQuery();
        ObservableList<Despesa> despesas = FXCollections.observableArrayList();
     
        while(resultado.next()){
            Usuario user = new Usuario();
            user.setId(resultado.getInt("us.id"));
            user.setNome(resultado.getString("us.usernome"));
            
            Despesa despesa = new Despesa();
            despesa.setId(resultado.getInt("des.despid"));
            despesa.setNome(resultado.getString("des.despnome"));
            despesa.setCategoria(resultado.getString("des.despcategoria"));
            despesa.setDescricao(resultado.getString("des.despdescriao"));
            despesa.setValor(resultado.getFloat("des.despvalor"));
            despesa.setData(resultado.getString("des.despdata"));
            despesa.setApagado(resultado.getBoolean("des.apagado"));
            despesa.setDataremovido(resultado.getString("des.dataremovido"));

            despesa.setUsuario(user);
            despesas.add(despesa);
        }
        return despesas;
    }
    
    public ObservableList<Despesa> filtrarValor(String valor1, String valor2, Usuario usuario) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("select des.*, us.* ");
        sql.append("from despesa des ");
        sql.append("inner join usuario us on us.id = des.userId ");
        sql.append("where des.apagado = false and des.userid = ? and des.limpado = false and des.despvalor between ? and ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
     
        comando.setInt(1, usuario.getId());
        comando.setString(2, valor1);
        comando.setString(3, valor2);
        
        ResultSet resultado = comando.executeQuery();
        ObservableList<Despesa> despesas = FXCollections.observableArrayList();
     
        while(resultado.next()){
            Usuario user = new Usuario();
            user.setId(resultado.getInt("us.id"));
            user.setNome(resultado.getString("us.usernome"));
            
            Despesa despesa = new Despesa();
            despesa.setId(resultado.getInt("des.despid"));
            despesa.setNome(resultado.getString("des.despnome"));
            despesa.setCategoria(resultado.getString("des.despcategoria"));
            despesa.setDescricao(resultado.getString("des.despdescriao"));
            despesa.setValor(resultado.getFloat("des.despvalor"));
            despesa.setData(resultado.getString("des.despdata"));
            despesa.setApagado(resultado.getBoolean("des.apagado"));
            despesa.setDataremovido(resultado.getString("des.dataremovido"));

            despesa.setUsuario(user);
            despesas.add(despesa);
        }
        return despesas;
    }
    
    public void excluirDespesa(Despesa despesa) throws SQLException {
        StringBuilder sql = new StringBuilder();
        //sql.append("delete from despesa ");
        //sql.append("where despid = ? ");
        sql.append("update despesa ");
        sql.append("set apagado = true ");
        sql.append("where despid = ? ");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, despesa.getId());
        comando.executeUpdate();
    }

    public void editarDespesa(Despesa despesa) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update despesa ");
        sql.append("set despnome = ?, despcategoria = ?, despdescriao = ? ");
        sql.append("where despid = ? ");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, despesa.getNome());
        comando.setString(2, despesa.getCategoria());
        comando.setString(3, despesa.getDescricao());
        comando.setInt(4, despesa.getId());

        comando.executeUpdate();
    }

    public void atribuirCategoria(String outra, String deletada) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update despesa ");
        sql.append("set despcategoria = ? ");
        sql.append("where despcategoria = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, outra);
       comando.setString(2, deletada);
        comando.executeUpdate();
    }
    
    public void dataRemovido(String data, Despesa despesa) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update despesa ");
        sql.append("set dataremovido = ? ");
        sql.append("where despid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, data);
        comando.setInt(2, despesa.getId());
        comando.executeUpdate();
    }
    public void recuperarDespesa(Despesa despesa) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update despesa ");
        sql.append("set apagado = false ");
        sql.append("where despid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, despesa.getId());
        comando.executeUpdate();
    }
    public void limparDespesa(Despesa despesa) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update despesa ");
        sql.append("set limpado = true ");
        sql.append("where despid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, despesa.getId());
        comando.executeUpdate();
    }
}

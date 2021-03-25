package controldespesas.dao;

import controldespesas.domain.Categoria;
import controldespesas.domain.Usuario;
import controldespesas.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriaDAO {

    public void salvarCategoria(Categoria categoria) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into categoria ");
        sql.append("(catenome, userid) ");
        sql.append("values (?, ?) ");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, categoria.getNome());
        comando.setInt(2, categoria.getUser().getId());

        comando.executeUpdate();
    }

    public void excluirCategoria(Categoria categoria) throws SQLException {
        StringBuilder sql = new StringBuilder();
        //sql.append("delete from categoria ");
        //sql.append("where cateid = ? ");
        sql.append("update categoria ");
        sql.append("set apagado = true, usada = false ");
        sql.append("where cateid = ? ");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, categoria.getId());
        comando.executeUpdate();

    }
    public void categoriaUsada(String ca, Usuario usuario) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update categoria ");
        sql.append("set usada = true ");
        sql.append("where catenome = ? and userid = ? ");
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, ca);
        comando.setInt(2, usuario.getId());
        comando.executeUpdate();
    }

    public ObservableList<Categoria> listarCategoria(Usuario user) throws SQLException {
        StringBuilder sql = new StringBuilder();
        //sql.append("select * from categoria ");
        //sql.append("where apagado = false ");
        sql.append("SELECT cate.cateid, cate.catenome, cate.apagado, cate.userid, us.id, us.usernome ");
        sql.append("from categoria cate ");
        sql.append("INNER join usuario us on us.id = cate.userid ");
        sql.append("where cate.userid = ? and cate.apagado = false and cate.limpado = false ");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, user.getId());
        ResultSet resultado = comando.executeQuery();

        ObservableList<Categoria> listaCategoria = FXCollections.observableArrayList();
        while (resultado.next()) {
            Categoria categoria = new Categoria();
            categoria.setId(resultado.getInt("cateid"));
            categoria.setNome(resultado.getString("catenome"));
            categoria.setApagado(resultado.getBoolean("apagado"));
            categoria.setUser(user);

            listaCategoria.add(categoria);
        }
        return listaCategoria;
    }
    
    public ObservableList<Categoria> listarUsadas(Usuario user) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cate.cateid, cate.catenome, cate.apagado, cate.userid, us.id, us.usernome ");
        sql.append("from categoria cate ");
        sql.append("INNER join usuario us on us.id = cate.userid ");
        sql.append("where cate.userid = ? and cate.apagado = false and cate.limpado = false and usada = true");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, user.getId());
        ResultSet resultado = comando.executeQuery();

        ObservableList<Categoria> listaCategoria = FXCollections.observableArrayList();
        while (resultado.next()) {
            Categoria categoria = new Categoria();
            categoria.setId(resultado.getInt("cateid"));
            categoria.setNome(resultado.getString("catenome"));
            categoria.setApagado(resultado.getBoolean("apagado"));
            categoria.setUser(user);

            listaCategoria.add(categoria);
        }
        return listaCategoria;
    }
    public ObservableList<Categoria> listarLixeira(Usuario user) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select cat.*, us.* ");
        sql.append("from categoria cat ");
        sql.append("inner join usuario us on us.id = cat.userid ");
        sql.append("where cat.userid = ? and cat.apagado = true and cat.limpado = false ");

        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, user.getId());

        ResultSet resultado = comando.executeQuery();

        ObservableList<Categoria> categorias = FXCollections.observableArrayList();
        while (resultado.next()) {
            Categoria categoria = new Categoria();
            
            categoria.setId(resultado.getInt("cateid"));
            categoria.setNome(resultado.getString("catenome"));
            categoria.setApagado(resultado.getBoolean("apagado"));
            categoria.setDataremovido(resultado.getString("dataremovido"));
            categoria.setUser(user);
            
            categorias.add(categoria);
        }
        return categorias;
    }

    public void dataRemovido(String data, Categoria categ) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update categoria ");
        sql.append("set dataremovido = ? ");
        sql.append("where cateid = ? ");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, data);
        comando.setInt(2, categ.getId());
        comando.executeUpdate();
    }
    public void recuperarCategoria(Categoria categoria) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update categoria ");
        sql.append("set apagado = false ");
        sql.append("where cateid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, categoria.getId());
        comando.executeUpdate();
    }
    
    public void limparCategoria(Categoria categoria) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update categoria ");
        sql.append("set limpado = true ");
        sql.append("where cateid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, categoria.getId());
        comando.executeUpdate();
    }
    
    public boolean verificarCategoria(String nome) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("select catenome ");
        sql.append("from categoria ");
        sql.append("where catenome = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, nome);
        
        boolean tem = false;
        ResultSet resultado = comando.executeQuery();
        while(resultado.next()){
            tem = true;
        }
        return tem;
    }
}

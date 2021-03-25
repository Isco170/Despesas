package controldespesas.dao;

import controldespesas.domain.Usuario;
import controldespesas.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImagemDAO {

    public static void salvarIamgem(int user) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into imagem ");
        sql.append("(imagemnome, userid) ");
        sql.append("values (?, ?) ");

        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, "C:\\Users\\The FAM\\Documents\\NetBeansProjects\\ControlDespesas\\src\\controldespesas\\view\\img\\user.png");
        comando.setInt(2, user);
        comando.executeUpdate();
    }

    public void atualizarImagem(String caminho, Usuario user) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update imagem ");
        sql.append("set imagemnome = ? ");
        sql.append("where userid = ? ");

        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, caminho);
        comando.setInt(2, user.getId());
        comando.executeUpdate();

    }

    public String listarImagem(Usuario user) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select imagemnome ");
        sql.append("from imagem ");
        sql.append("where userid = ? ");

        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, user.getId());

        ResultSet resultado = comando.executeQuery();
        String nome = "";
        
        while (resultado.next()) {
            nome = resultado.getString("imagemnome");
        }
        return nome;
    }
}

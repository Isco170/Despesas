package controldespesas.dao;

import controldespesas.domain.Empresa;
import controldespesas.domain.Morada;
import controldespesas.domain.Usuario;
import controldespesas.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    ResultSet resultado, resultadoSet;
    public static int chave = 0;
    EmpresaDAO empDao = new EmpresaDAO();
    MoradaDAO morDao = new MoradaDAO();

    public void salvarAmbos(Usuario usuario, Morada morada) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO usuario ");
        sql.append("(usernome, userapelido, usertelefone, useremail, userid, usersenha ) ");
        sql.append("VALUES (?,?,?,?,?, ? )");

        Connection conexao = ConexaoFactory.conectar();
        conexao.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        PreparedStatement comando = conexao.prepareStatement(sql.toString(),
                PreparedStatement.RETURN_GENERATED_KEYS);
        comando.setString(1, usuario.getNome());
        comando.setString(2, usuario.getApelido());
        comando.setInt(3, usuario.getTelefone());
        comando.setString(4, usuario.getEmail());
        comando.setString(5, usuario.getUsuario());
        comando.setString(6, usuario.getSenha());

        comando.execute();
        ResultSet rs = comando.getGeneratedKeys();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1);
        }

        ImagemDAO.salvarIamgem(id);
        morDao.salvarMorada(morada, id);
        System.out.println("SALVOU AMBOS");

    }

    public void salvarTodos(Usuario usuario, Morada morada, Empresa empresa) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO usuario ");
        sql.append("(usernome, userapelido, usertelefone, useremail, userid, usersenha) ");
        sql.append("VALUES (?,?,?,?,?,? )");

        Connection conexao = ConexaoFactory.conectar();
        conexao.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        PreparedStatement comando = conexao.prepareStatement(sql.toString(),
                PreparedStatement.RETURN_GENERATED_KEYS);
        comando.setString(1, usuario.getNome());
        comando.setString(2, usuario.getApelido());
        comando.setInt(3, usuario.getTelefone());
        comando.setString(4, usuario.getEmail());
        comando.setString(5, usuario.getUsuario());
        comando.setString(6, usuario.getSenha());
        comando.execute();

        ResultSet rs = comando.getGeneratedKeys();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1);
        }

        ImagemDAO.salvarIamgem(id);

        morDao.salvarMorada(morada, id);
        empDao.salvarEmpresa(empresa, id);

    }

    public void salvarUsuario(Usuario usuario) throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO usuario ");
        sql.append("(usernome, userapelido, usertelefone, useremail, userid, usersenha ) ");
        sql.append("VALUES (?,?,?,?,?,?)");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString(),
                PreparedStatement.RETURN_GENERATED_KEYS);
        comando.setString(1, usuario.getNome());
        comando.setString(2, usuario.getApelido());
        comando.setInt(3, usuario.getTelefone());
        comando.setString(4, usuario.getEmail());
        comando.setString(5, usuario.getUsuario());
        comando.setString(6, usuario.getSenha());

        comando.executeUpdate();
        ResultSet cha = comando.getGeneratedKeys();
        while (cha.next()) {
            System.out.println("Chave => " + cha.getInt(1));
            chave = cha.getInt(1);
        }

        ImagemDAO.salvarIamgem(chave);
    }

    public void excluirUsuario(Usuario usuario) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from usuario ");
        sql.append("where id = ? ");

        Connection conectar = ConexaoFactory.conectar();

        PreparedStatement comando = conectar.prepareStatement(sql.toString());
        comando.setInt(1, usuario.getId());
        comando.executeUpdate();
    }

    public void editarUsuario(Usuario user) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update usuario ");
        sql.append("set usernome = ?, userapelido = ?, usertelefone = ?, useremail = ?, userid = ?, usersenha = ? ");
        sql.append("where id = ? ");

        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, user.getNome());
        comando.setString(2, user.getApelido());
        comando.setInt(3, user.getTelefone());
        comando.setString(4, user.getEmail());
        comando.setString(5, user.getUsuario());
        comando.setString(6, user.getSenha());
        comando.setInt(7, user.getId());
        comando.executeUpdate();

    }

    public Usuario login(String user, String senha) throws SQLException {
        boolean check = false;
        Usuario usuario = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select * from usuario ");
        sql.append("where userid = ? and usersenha = ?");

        Connection conexao = ConexaoFactory.conectar();

        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, user);
        comando.setString(2, senha);

        resultado = comando.executeQuery();

        if (resultado.next()) {
            usuario = new Usuario();
            usuario.setId(resultado.getInt("id"));
            usuario.setNome(resultado.getString("usernome"));
            usuario.setApelido(resultado.getString("userapelido"));
            usuario.setTelefone(resultado.getInt("usertelefone"));
            usuario.setEmail(resultado.getString("useremail"));
            usuario.setUsuario(resultado.getString("userid"));
            usuario.setSenha(resultado.getString("usersenha"));
        }
        return usuario;
    }
    public boolean verificarUser(String string) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("select userid ");
        sql.append("from usuario ");
        sql.append("where userid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, string);
        
        ResultSet resultado = comando.executeQuery();
        boolean nao = false;
        while(resultado.next()){
            nao = true;
        }
        return nao;
    }
}

package controldespesas.dao;

import controldespesas.domain.Empresa;
import controldespesas.domain.Usuario;
import controldespesas.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpresaDAO {

    public void salvarEmpresa(Empresa empresa, int id) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into empresa ");
        sql.append("(empnome, empend, empmail, emptele, userid) ");
        sql.append("values (?,?,?,?,?) ");

        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        //comando = conexao.prepareStatement(sql3.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
        comando.setString(1, empresa.getNome());
        comando.setString(2, empresa.getEndereco());
        comando.setString(3, empresa.getEmail());
        comando.setInt(4, empresa.getTelefone());
        comando.setInt(5, id);

        comando.execute();
    }
    
    public Empresa listarEmpresa(Usuario usuario) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("select * from empresa ");
        sql.append("where userid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, usuario.getId());
        
        ResultSet resultado = comando.executeQuery();
        
        Empresa empresa = new Empresa();
        while(resultado.next()){
            empresa.setId(resultado.getInt("id"));
            empresa.setNome(resultado.getString("empnome"));
            empresa.setEmail(resultado.getString("empmail"));
            empresa.setEndereco(resultado.getString("empend"));
            empresa.setTelefone(resultado.getInt("emptele"));
        }
        return empresa;
    }
    public void editarEmpresa(Empresa empresa, Usuario user) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update empresa ");
        sql.append("set empnome = ?, empend = ?, empmail = ?, emptele = ? ");
        sql.append("where userid = ?");
        
        Connection conexao = ConexaoFactory.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, empresa.getNome());
        comando.setString(2, empresa.getEndereco());
        comando.setString(3, empresa.getEmail());
        comando.setInt(4, empresa.getTelefone());
        comando.setInt(5, user.getId());
        comando.executeUpdate();
    }
}

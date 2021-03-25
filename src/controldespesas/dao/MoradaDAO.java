
package controldespesas.dao;

import controldespesas.domain.Morada;
import controldespesas.domain.Usuario;
import controldespesas.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoradaDAO {
   
    public void salvarMorada(Morada morada, int id) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("insert into morada ");
        sql.append("(moradaav, moradabr, moradaqrt, moradarua, moradacasa, userId) ");
        sql.append("values (?,?,?,?,?,?) ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, morada.getAvenida());
        comando.setString(2, morada.getBairro());
        comando.setInt(3, morada.getQuarteirao());
        comando.setString(4, morada.getRua());
        comando.setInt(5, morada.getNrCasa());
        comando.setInt(6, id);
        
        comando.executeUpdate();
    }
    
    public Morada listarMorada(Usuario usuario) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT mor.*, us.usernome " );
        sql.append("from morada mor ");
        sql.append("INNER join usuario us on us.id = mor.userid ");
        sql.append("where mor.userid = ?");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, usuario.getId());
        
        ResultSet resultado = comando.executeQuery();
        
        Morada des = new Morada();
        Usuario user = new Usuario();
        
        while(resultado.next()){
            //user.setId(resultado.getInt("us.id"));
            user.setNome(resultado.getString("us.usernome"));            
            
            des.setIdMorada(resultado.getString("mor.moradaid"));
            des.setAvenida(resultado.getString("mor.moradaav"));
            des.setBairro(resultado.getString("mor.moradabr"));
            des.setRua(resultado.getString("mor.moradarua"));
            des.setQuarteirao(resultado.getInt("mor.moradaqrt"));
            des.setNrCasa(resultado.getInt("mor.moradacasa"));
            
            des.setUser(user);
            
        }
        return des;
    }
    public void editarMorada(Morada mor, Usuario user) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update morada ");
        sql.append("set moradaav = ?, moradabr = ?, moradarua = ?, moradaqrt = ?, moradacasa = ? ");
        sql.append("where userid = ? ");
        
        Connection conexao = ConexaoFactory.conectar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, mor.getAvenida());
        comando.setString(2, mor.getBairro());
        comando.setString(3, mor.getRua());
        comando.setInt(4, mor.getQuarteirao());
        comando.setInt(5, mor.getNrCasa());
        comando.setInt(6, user.getId());
        comando.executeUpdate();
    }
}

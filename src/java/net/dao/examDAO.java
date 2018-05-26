package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.typeuser;
import net.model.user;

public class userDAO {
    
    ConnectionDB cn;

    public userDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }
    
    public boolean insert(user model) {
        String sql = "call SVURS_RegisterUsr(?,?,?,?,?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getFirstname());
            ps.setString(2, model.getPsurname());
            ps.setString(3, model.getMsurname());
            ps.setString(4, model.getEmail());
            ps.setString(5, model.getPhone());
            ps.setString(6, model.getPassword());
            ps.setInt(7, model.getId_tipo());
            ps.setInt(8, model.getId_status());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean delete(int id){
        String sql = "UPDATE ae_usuarios SET ID_ESTATUS = 0 WHERE ID_USUARIO=?";     
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean update(user model) {
        String sql = "UPDATE ae_usuarios SET " +
                    "NOMBRE=?, APE_PATERNO=?, APE_MATERNO=?, CORREO=?, TELEFONO=?,ID_TIPOUSUARIO=? " +
                    "WHERE ID_USUARIO=?";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getFirstname());
            ps.setString(2, model.getPsurname());
            ps.setString(3, model.getMsurname());
            ps.setString(4, model.getEmail());
            ps.setString(5, model.getPhone());
            ps.setInt(6, model.getId_tipo());
            ps.setInt(7, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean validate(String user, String pass) {
        String sql = "call SVURS_ValidateUsr(?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public List<user> getAll() {
        ArrayList<user> lista = new ArrayList<user>();
        String sql = "SELECT u.ID_USUARIO, u.NOMBRE, u.APE_PATERNO,u.APE_MATERNO, u.CORREO, u.TELEFONO, t.ID_TIPOUSUARIO, e.ID_ESTATUS " +
                    "FROM ae_usuarios u INNER JOIN ae_tipousuario t ON u.ID_TIPOUSUARIO = t.ID_TIPOUSUARIO " +
                    "INNER JOIN ae_estatus e ON u.ID_ESTATUS = e.ID_ESTATUS WHERE e.DESCRIPCION = 'Activo'";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                user sc = new user(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getInt(7),rs.getInt(8));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }
    
    //Tipos de usuario
    
    public boolean insertType(typeuser model) {
        String id = "SELECT MAX(ID_TIPOUSUARIO) + 1 from ae_tipousuario";    
        String sql = "INSERT INTO ae_tipousuario(ID_TIPOUSUARIO, DESCRIPCION) VALUES(?,?)";    
        int idType = 0;
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                idType = rs.getInt(1);
            }
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, idType);
            ps.setString(2, model.getDescription());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean deleteType(int id){
        String sql = "DELETE FROM ae_tipousuario WHERE ID_TIPOUSUARIO=?";     
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean updateType(typeuser model) {
        String sql = "UPDATE ae_tipousuario SET DESCRIPCION = ? WHERE ID_TIPOUSUARIO=?";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getDescription());
            ps.setInt(2, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.typeExam;
import net.model.exam;

public class examDAO {
    
    ConnectionDB cn;

    public examDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }
    
    //Funciones CRUD basic information
    public boolean insert(exam model) {
        String id = "SELECT MAX(ID_CREAEXA) + 1 from ae_creaexa";    
        String sql = "INSERT INTO ae_creaexa " +
                "(ID_CREAEXA, DESCRIPCION, NUM_PREGUNTAS, FECHA_CREACION, FECHA_BAJA, CAL_MAX, ID_USUARIO, ID_ESTATUS, ID_TIPOEXA, ID_MATERIA) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";    
        int idExam = 0;
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                idExam = rs.getInt(1);
            }
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, idExam);
            ps.setString(2, model.getDescription());
            ps.setInt(3, model.getQuestions());
            ps.setString(4, model.getCreatDate());
            ps.setString(5, model.getDischargeDate());
            ps.setFloat(6, model.getNote());
            ps.setInt(7, model.getId_user());
            ps.setInt(8, model.getId_status());
            ps.setInt(9, model.getId_typeExa());
            ps.setString(10, model.getId_subject());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean delete(int id){
        String sql = "UPDATE ae_creaexa SET ID_ESTATUS = 0, FECHA_BAJA = NOW() WHERE ID_CREAEXA=?"; 
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean update(exam model) {
        String sql = "UPDATE ae_creaexa SET " +
                    "DESCRIPCION=?, CAL_MAX=?, ID_TIPOEXA=?, ID_MATERIA=? " +
                    "WHERE ID_CREAEXA=?";    
                
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getDescription());
            ps.setFloat(2, model.getNote());
            ps.setInt(3, model.getId_typeExa());
            ps.setString(4, model.getId_subject());
            ps.setInt(5, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public List<exam> getAll() {
        ArrayList<exam> lista = new ArrayList<exam>();
        String sql = "SELECT ex.ID_CREAEXA, ex.DESCRIPCION, ex.NUM_PREGUNTAS, ex.FECHA_CREACION, ex.FECHA_BAJA, " +
                "ex.CAL_MAX, ex.ID_USUARIO, ex.ID_ESTATUS, ex.ID_TIPOEXA, ex.ID_MATERIA FROM ae_creaexa ex;";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                exam sc = new exam(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getFloat(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }
    
    //Funtions CRUD Types of the exams
    public boolean insertType(typeExam model) {
        String id = "SELECT MAX(ID_TIPOEXA) + 1 from ae_tipoexa";    
        String sql = "INSERT INTO ae_tipoexa(ID_TIPOEXA, DESCRIPCION) VALUES(?,?)";    
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
        String sql = "DELETE FROM ae_tipoexa WHERE ID_TIPOEXA=?";     
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean updateType(typeExam model) {
        String sql = "UPDATE ae_tipoexa SET DESCRIPCION = ? WHERE ID_TIPOEXA=?";
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

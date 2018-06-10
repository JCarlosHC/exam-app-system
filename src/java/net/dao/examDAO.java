package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.answersPerQuestion;
import net.model.typeExam;
import net.model.exam;
import net.model.examforview;
import net.model.questionPerExam;

public class examDAO {
    
    ConnectionDB cn;

    public examDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }
    
    //Funciones CRUD basic information
    public boolean insertOrUpdate(exam model) {
        String sql = "call SVURS_CRUDBasicInformation(?,?,?,?,?,?,?,?,?,?,?,?)";
        int id = 0;
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getDescription());
            ps.setInt(3, model.getQuestions());
            ps.setString(4, model.getCreatDate());
            ps.setString(5, model.getDischargeDate());
            ps.setFloat(6, model.getNote());
            ps.setInt(7, model.getId_user());
            ps.setInt(8, model.getId_status());
            ps.setInt(9, model.getId_typeExa());
            ps.setString(10, model.getId_subject());
            ps.setString(11, model.getTitle());
            ps.setString(12, model.getImage());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean updateImage(exam model) {
        String sql = "update ae_creaexa set image = ? where id_creaexa = ?";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getImage());
            ps.setInt(2, model.getId());
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
        String sql = "SELECT ex.ID_CREAEXA, ex.TITLE, ex.DESCRIPCION, ex.NUM_PREGUNTAS, ex.FECHA_CREACION, ex.FECHA_BAJA, " +
                "ex.CAL_MAX, ex.ID_USUARIO, ex.ID_ESTATUS, ex.ID_TIPOEXA, ex.ID_MATERIA, ex.IMAGE FROM ae_creaexa ex;";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                exam sc = new exam(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6),
                        rs.getFloat(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11), rs.getString(12));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }
    public List<examforview> getMyExams(int iduser) {
        ArrayList<examforview> lista = new ArrayList<examforview>();
        String sql = "SELECT ex.ID_CREAEXA, ex.TITLE, ex.IMAGE, ex.DESCRIPCION, IFNULL(SEC_TO_TIME(averageTime(ex.ID_CREAEXA)),0) as AVERAGETIME," +
                "IFNULL(averageMark(ex.ID_CREAEXA),0) as AVERAGEMARK, totalStudents(ex.ID_CREAEXA) as TOTALSTUDENTS, ex.CAL_MAX " +
                "FROM ae_creaexa ex INNER JOIN ae_usuarios us ON ex.ID_USUARIO = us.ID_USUARIO WHERE ex.ID_USUARIO=?;";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1,iduser);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                examforview sc = new examforview(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                        rs.getString(5), rs.getFloat(6), rs.getInt(7), rs.getFloat(8));
                lista.add(sc);
            }

            } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }
    public exam getExam(int id){
        String sql = "SELECT ex.ID_CREAEXA, ex.TITLE, ex.DESCRIPCION, ex.NUM_PREGUNTAS, ex.FECHA_CREACION, ex.FECHA_BAJA, " +
                "ex.CAL_MAX, ex.ID_USUARIO, ex.ID_ESTATUS, ex.ID_TIPOEXA, ex.ID_MATERIA, ex.IMAGE FROM ae_creaexa ex WHERE ex.ID_CREAEXA=?";
        exam model = new exam();
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                model = new exam(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6),
                        rs.getFloat(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11), rs.getString(12));
            }

        } catch (SQLException e) {
            model = null;
        }
        
        return model;
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
    
    //Funcions CRUD preguntas
    public List<questionPerExam> getQuestions(int id){
        ArrayList<questionPerExam> lista = new ArrayList<>();
        String sql = "select p.* from ae_cexapreg ce " +
                    "inner join ae_creaexa ac on ce.ID_CREAEXA = ac.ID_CREAEXA " +
                    "inner join ae_preguntas p on p.ID_PREGUNTA = ce.ID_PREGUNTA " +
                    "where ce.ID_CREAEXA =? and p.ID_ESTATUS = 1";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                questionPerExam model = new questionPerExam(
                                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                        rs.getInt(6), rs.getInt(7));
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }
    public questionPerExam getQuestion(int id){
        questionPerExam model = null;
        
        String sql = "select * from ae_preguntas " +
                    "where ID_PREGUNTA =?";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                model = new questionPerExam(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                        rs.getString(5),rs.getInt(6), rs.getInt(7));
            }
            List<answersPerQuestion> answers = this.getAnswers(id);
            model.setAnswers(answers);
            
        } catch (SQLException e) {
            model = null;
        }
        return model;
    }
    public boolean insertOrUpdateQuestion(questionPerExam model, int idexam) {
        String sql = "call SVURS_CRUDQuestion(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getQuestion());
            ps.setString(3, model.getCreatDate());
            ps.setString(4, model.getUnitTemary());
            ps.setString(5, model.getDischargeDate());
            ps.setInt(6, model.getIdUser());
            ps.setInt(7, model.getIdStatus());
            ps.setInt(8, idexam);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean deleteQuestion(int id){
        String sql = "call SVURS_CRUDQuestion(?,?,?,?,?,?,?,?)";
        questionPerExam model = this.getQuestion(id);
        model.setIdStatus(0);
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getQuestion());
            ps.setString(3, model.getCreatDate());
            ps.setString(4, model.getUnitTemary());
            ps.setString(5, model.getDischargeDate());
            ps.setInt(6, model.getIdUser());
            ps.setInt(7, model.getIdStatus());
            ps.setInt(8, 0);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }        
    }
    
    //Funciones CRUD respuestas
    public List<answersPerQuestion> getAnswers(int id){
        ArrayList<answersPerQuestion> lista = new ArrayList<>();
        String sql = "select*from ae_respuestas where ID_PREGUNTA =? AND ID_ESTATUSRESP IN(0,1)";
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                answersPerQuestion model = new answersPerQuestion(rs.getInt("ID_RESPUESTA"), rs.getInt("ID_PREGUNTA"), 
                        rs.getString("RESPUESTA"), rs.getInt("ID_ESTATUSRESP"), false);
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }
    public answersPerQuestion getAnswer(int id){
        answersPerQuestion model = null;
        String sql = "select * from ae_respuestas " +
                    "where ID_RESPUESTA =?";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                model = new answersPerQuestion(rs.getInt("ID_RESPUESTA"),rs.getInt("ID_PREGUNTA"), 
                                            rs.getString("RESPUESTA"), rs.getInt("ID_ESTATUSRESP"), false);
            }

        } catch (SQLException e) {
            model = null;
        }
        return model;
    }
    public boolean insertOrUpdateAnswer(answersPerQuestion model) {
        String sql = "call SVURS_CRUDAnswer(?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getAnswer());
            ps.setInt(3, model.getStatus());
            ps.setInt(4, model.getIdQuestion());
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean changeAnswer(answersPerQuestion model) {
        String sql = "update ae_respuestas set ID_ESTATUSRESP = ? where ID_PREGUNTA = ? and ID_RESPUESTA = ?;";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getStatus());
            ps.setInt(2, model.getIdQuestion());
            ps.setInt(3, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean deleteAnswer(int id){
        String sql = "call SVURS_CRUDAnswer(?,?,?,?)";
        answersPerQuestion model = this.getAnswer(id);
        
        if(model.getStatus() == 0){
            model.setStatus(2);
        }else{
            model.setStatus(3);
        }
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getAnswer());
            ps.setInt(3, model.getStatus());
            ps.setInt(4, model.getIdQuestion());
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }        
    }
}

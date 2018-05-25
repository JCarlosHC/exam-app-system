package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import net.model.typeuser;

public class catalogs {

    ConnectionDB cn;
    
    public catalogs(ConnectionDB _cn) {
        this.cn = _cn;
    }
    
    public ArrayList<typeuser> getListTypeUsers(){
        String sql = "select * from ae_tipousuario";   
        ArrayList<typeuser> lista = new ArrayList<typeuser>();
        
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                typeuser a = new typeuser();
                a.setId(rs.getInt(1));
                a.setDescription(rs.getString(2));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            return null;
        }
        
        return lista;
    } 
}

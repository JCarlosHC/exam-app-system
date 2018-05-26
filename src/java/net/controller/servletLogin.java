package net.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.dao.ConnectionDB;
import net.dao.userDAO;
import net.model.user;

public class servletLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession sesion = request.getSession();
        
        if(action.equals("out")){
            sesion.invalidate();
            response.sendRedirect("login.jsp");
        }else{
           
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sesion = request.getSession();
        RequestDispatcher rd;
        String usu = request.getParameter("email");
        String pass = request.getParameter("password");
        String action = request.getParameter("action");
        String firstname = request.getParameter("firstname");
        String psurname = request.getParameter("psurname");
        String msurname = request.getParameter("msurname");
        
        ConnectionDB cn = new ConnectionDB();
        userDAO usrDao = new userDAO(cn);
        user usr = new user();
        
        if(action.equals("login")){
            usr = usrDao.validate(usu, pass);
            
            if(usr != null && sesion.getAttribute("userId") == null){
                sesion.setAttribute("IdtableUser", usr.getId());
                sesion.setAttribute("userId", usr.getEmail());
                sesion.setAttribute("userName", usr.getFirstname());
                sesion.setAttribute("userEmail", usr.getEmail());
                sesion.setAttribute("userType", usr.getId_tipo());
            }
            response.sendRedirect("index.jsp");
            
        }else if(action.equals("register")){
            usr.setFirstname(firstname);
            usr.setPsurname(psurname);
            usr.setMsurname(msurname);
            usr.setEmail(usu);
            usr.setPhone(null);
            usr.setPassword(pass);
            usr.setId_tipo(2); //Se crea por default con el tipo de profesor
            usr.setId_status(1); //Se crea por default activo
            
            if(!usrDao.insert(usr)){
                request.setAttribute("msg", "Ocurrio un error al registrar el usuario");
            }
            rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
        
        cn.disconnect();
        
        
    }

}

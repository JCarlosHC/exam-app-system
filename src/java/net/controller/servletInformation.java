package net.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.dao.ConnectionDB;
import net.dao.userDAO;
import net.model.student;
import net.model.user;

public class servletInformation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String msgresult = request.getParameter("msg");
        String success = request.getParameter("success");
        
        String msg = "", url = "changePassword.jsp";
        RequestDispatcher rd;

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }
        
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("userEmail");
        String userId = (String) request.getSession().getAttribute("userId");
        int userType = (int) request.getSession().getAttribute("userType");
        String passActual = request.getParameter("passActual");
        String confirmnewPassword = request.getParameter("ConfirmnewPassword");
        String newPassword = request.getParameter("newPassword");

        String url = "", msg = "";
        ConnectionDB cn = new ConnectionDB();
        userDAO usrDao = new userDAO(cn);

        if (action.equals("updatePassword")) {
            if (confirmnewPassword.equals(newPassword)) {
                if (userType == 4) {
                    student stu = usrDao.validateStudent(userId, passActual);
                    if (stu != null) {
                        if (usrDao.updatePasswordStudent(newPassword, userId)) {
                            url = "loginstudent.jsp";
                        } else {
                            msg = "Ocurrio un error, no se pudo actualizar la contraseña";
                            url = "servletInformation?success=false&msg=" + msg;
                        }
                    } else {
                        msg = "Ocurrio un error, sus credenciales no son validas";
                        url = "servletInformation?success=false&msg=" + msg;
                    }
                } else {
                    user usr = usrDao.validate(userEmail, passActual);
                    if (usr != null) {
                        if(usrDao.updatePasswordUser(newPassword, userEmail)){
                            url = "loginuser.jsp";
                        } else {
                            msg = "Ocurrio un error, no se pudo actualizar la contraseña";
                            url = "servletInformation?success=false&msg=" + msg;
                        }
                    } else {
                        msg = "Ocurrio un error, sus credenciales no son validas";
                        url = "servletInformation?success=false&msg=" + msg;
                    }
                }
            } else {
                msg = "Ocurrio un error, las contraseñas no coinciden";
                url = "servletInformation?success=false&msg=" + msg;
            }
        }

        cn.disconnect();
        response.sendRedirect(url);

    }
}

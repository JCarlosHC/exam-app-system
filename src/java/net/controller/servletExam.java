package net.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.dao.ConnectionDB;
import net.dao.catalogs;
import net.dao.examDAO;
import net.model.typeExam;
import net.model.exam;

public class servletExam extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String idType = request.getParameter("idType");
        String msgresult = request.getParameter("msg");
        String success = request.getParameter("success");

        String url = "", msg;
        RequestDispatcher rd;
        ConnectionDB cn = new ConnectionDB();
        examDAO exDao = new examDAO(cn);

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }
        switch (action) {
            case "toList":
                List<exam> lista = exDao.getAll();
                request.setAttribute("lista", lista);
                url = "exams.jsp";
                break;
            case "delete":
                if (exDao.delete(Integer.parseInt(id))) {
                    msg = "Se eliminó correctamente el examen";
                    url = "servletExam?action=toList&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el examen no fue eliminado";
                    url = "servletExam?action=toList&success=false&msg=" + msg;
                }   
                break;
            case "deleteType":
                if (exDao.deleteType(Integer.parseInt(idType))) {
                    msg = "Se eliminó correctamente el tipo";
                    url = "servletExam?action=typeExams&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue eliminado";
                    url = "servletExam?action=typeExams&success=false&msg=" + msg;
                }   
                break;
            case "typeExams":
                catalogs catalog = new catalogs(cn);
                List<typeExam> listaType = catalog.getListTypeExams();
                request.setAttribute("listaType", listaType);
                url = "typeExams.jsp";
                break;
            default:
                break;
        }
        cn.disconnect();
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String descriptionE = request.getParameter("descriptionExa");
        String note = request.getParameter("note");
        String typeExam = request.getParameter("typeExam");
        String subject = request.getParameter("subject");
        int user = (int) request.getSession().getAttribute("IdtableUser");
        //Tipos de usuario
        String idType = request.getParameter("idType");
        String description = request.getParameter("description");
        typeExam type = new typeExam();
        
        String url = "", msg = "";
        ConnectionDB cn = new ConnectionDB();
        examDAO exDao = new examDAO(cn);
        exam model = new exam();

        switch (action) {
            case "insert":
                Date d = new Date(); 
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                
                model.setTitle(title);
                model.setDescription(descriptionE);
                model.setQuestions(0);
                model.setCreatDate(formater.format(d));
                model.setDischargeDate(null);
                model.setNote(Float.parseFloat(note));
                model.setId_user(user);
                model.setId_status(1);
                model.setId_typeExa(Integer.parseInt(typeExam));
                model.setId_subject(subject);
                
                if (exDao.insert(model)) {
                    msg = "Se guardo correctamente el examen";
                    url = "servletExam?action=toList&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el examen no fue guardado";
                    url = "servletExam?action=toList&success=false&msg=" + msg;
                }
                break;
            case "insertType":
                type.setDescription(description);
                
                if (exDao.insertType(type)) {
                    msg = "Se guardo correctamente el tipo";
                    url = "servletExam?action=typeExams&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue guardado";
                    url = "servletExam?action=typeExams&success=false&msg=" + msg;
                }
                break;
            case "updateType":
                type.setId(Integer.parseInt(idType));
                type.setDescription(description);
                
                if (exDao.updateType(type)) {
                    msg = "Se modificó correctamente el tipo";
                    url = "servletExam?action=typeExams&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue modificado";
                    url = "servletExam?action=typeExams&success=false&msg=" + msg;
                }
                break;
            case "update":
//                model.setId(Integer.parseInt(id));
//                model.setFirstname(firstname);
//                model.setPsurname(pname);
//                model.setMsurname(mname);
//                model.setEmail(email);
//                model.setPhone(phone);
//                model.setId_tipo(Integer.parseInt(typeuser));
//
//                if (usrDao.update(model)) {
//                    msg = "Se modifico correctamente el usuario";
//                    url = "servletABCUsers?action=toList&success=true&msg=" + msg;
//                } else {
//                    msg = "Ocurrio un error, el usuario no fue modificado";
//                    url = "servletABCUsers?action=toList&success=false&msg=" + msg;
//                }
                break;

            default:
                break;
        }

        cn.disconnect();
        response.sendRedirect(url);

    }

}

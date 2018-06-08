package net.controller;

import com.json.JSONArray;
import com.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.dao.ConnectionDB;
import net.dao.examDAO;
import net.model.questionPerExam;

public class servletQuestion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        JSONObject objJson = new JSONObject();
        PrintWriter out = response.getWriter();
        
        int user = (int) request.getSession().getAttribute("IdtableUser");
        String action = request.getParameter("action");
        String examId = request.getParameter("examId");
        String questionId = request.getParameter("questionId");
        String question = request.getParameter("question");
        String unitTemary = request.getParameter("unitTemary");

        ConnectionDB cn = new ConnectionDB();
        questionPerExam model = new questionPerExam();
        examDAO exDao = new examDAO(cn);

        if (action.equals("getQuestions")) {
            JSONArray questions = new JSONArray(exDao.getQuestions(Integer.parseInt(examId)));
            objJson.put("questions", questions);
            out.print(objJson);
            out.flush();
        } else if (action.equals("saveQuestion")) {
            Date d = new Date();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            model.setId(Integer.parseInt(questionId));
            model.setQuestion(question);
            model.setCreatDate(formater.format(d));
            model.setUnitTemary(unitTemary);
            model.setDischargeDate(null);
            model.setIdUser(user);
            model.setIdStatus(1);
            
            if (!exDao.insertOrUpdateQuestion(model, Integer.parseInt(examId))) {
                objJson.put("id", model.getId());
                objJson.put("success", false);
                objJson.put("msg", "Ocurrio un error, la pregunta no fue guardada");
            }
            out.print(objJson);
            out.flush();
        }
    }

}

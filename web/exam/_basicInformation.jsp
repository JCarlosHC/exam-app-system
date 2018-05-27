<%@page import="net.model.subjects"%>
<%@page import="net.model.typeExam"%>
<%@page import="net.dao.catalogs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.dao.ConnectionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h3>Basic information</h3>

<form action="servletExam" method="post">
    <input type="hidden" name="id" value="">
    <div class="form-group row">
        <label for="title" class="col-sm-3 col-form-label">Title</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="title" name="title" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="descriptionExa" class="col-sm-3 col-form-label">Description</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="descriptionExa" name="descriptionExa" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="note" class="col-sm-3 col-form-label">Maximum note</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="note" name="note" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="typeExam" class="col-sm-3 col-form-label">Type exam</label>
        <div class="col-sm-9">
            <select name="typeExam" class="form-control">
                <%
                    ConnectionDB cn = new ConnectionDB();
                    catalogs catalog = new catalogs(cn);
                    ArrayList<typeExam> listTypeUser = catalog.getListTypeExams();

                    for (int i = 0; i < listTypeUser.size(); i++) {
                        typeExam c = listTypeUser.get(i);
                %>
                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                <%
                    }
                %>        
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label for="subject" class="col-sm-3 col-form-label">Subject</label>
        <div class="col-sm-9">
            <select name="subject" class="form-control">
                <%
                    ArrayList<subjects> listsubjects = catalog.getListSubjects();

                    for (int i = 0; i < listsubjects.size(); i++) {
                        subjects c = listsubjects.get(i);
                %>
                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                <%
                    }
                    cn.disconnect();
                %>        
            </select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-md-10">
            <%  String msg = (String) request.getAttribute("msg");
                String success = (String) request.getAttribute("success");
                String classAlert, title, style;

                if (success == null) {
                    classAlert = "alert alert-danger alert-dismissible";
                    title = "Warning!";
                    style = "display:none";
                } else if (success.equals("true")) {
                    classAlert = "alert alert-success alert-dismissible";
                    title = "Success!";
                    style = "display:block";
                } else {
                    classAlert = "alert alert-danger alert-dismissible";
                    title = "Warning!";
                    style = "display:block";

                }
            %>
            <div class="<%=classAlert%>" style="<%=style%>">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong><%=title%> </strong>${msg}
            </div>
        </div>
        <div class="col-md-2">
            <input type="submit" class="btn btn-primary" name="btnNew" id="btnNew" value="Save changes">
        </div>
        <input type="hidden" name="action" value="saveInformationBasic"> <!-- this is insert -->
    </div>
</form>

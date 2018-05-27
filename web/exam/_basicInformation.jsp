<%@page import="net.model.exam"%>
<%@page import="net.model.subjects"%>
<%@page import="net.model.typeExam"%>
<%@page import="net.dao.catalogs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.dao.ConnectionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h3>Basic information</h3>

<form action="servletExam" method="post">
    <%
        exam model = (exam) request.getAttribute("myExam");
    %>
    <input type="hidden" name="id" value="<%=model.getId()%>">
    <div class="form-group row">
        <label for="title" class="col-sm-3 col-form-label">Title</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="title" name="title" value="<%=model.getTitle()%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="descriptionExa" class="col-sm-3 col-form-label">Description</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="descriptionExa" name="descriptionExa" value="<%=model.getDescription()%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="note" class="col-sm-3 col-form-label">Maximum note</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="note" name="note" value="<%=model.getNote()%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="typeExam" class="col-sm-3 col-form-label">Type exam</label>
        <div class="col-sm-9">
            <select name="typeExam" class="form-control">
                <%
                    ConnectionDB cn = new ConnectionDB();
                    catalogs catalog = new catalogs(cn);
                    ArrayList<typeExam> listTypeExam = catalog.getListTypeExams();

                    for (int i = 0; i < listTypeExam.size(); i++) {
                        typeExam c = listTypeExam.get(i);
                        if(model.getId_typeExa() == c.getId()) {
                        %>
                        <option value="<%=c.getId()%>" selected="true"><%=c.getDescription()%></option>
                        <%
                                }else{
                        %>
                        <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                        <%
                                }
                            }
                        %>        
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label for="subject" class="col-sm-3 col-form-label">Subject</label>
        <div class="col-sm-9">
            <select name="subject" class="form-control"">
                <%
                    ArrayList<subjects> listsubjects = catalog.getListSubjects();

                    for (int i = 0; i < listsubjects.size(); i++) {
                        subjects c = listsubjects.get(i);
                        if(c.getId().equals(model.getId_subject())){
                %>
                <option value="<%=c.getId()%>" selected="true"><%=c.getDescription()%></option>
                <%
                        }else{
                %>
                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                <%
                        }
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

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row">
    <div class="col-sm-6">
        <div class="thumbnail">
            <img id="courseImage" src="~/@Model.Image1" alt="@Model.Name" />
        </div>
    </div>
    <div class="col-sm-6">
        <form action="servletExam" method="post">
            <div class="alert-container"></div>
            <input type="hidden" name="id" value="@Model.Id"/>
            <div class="form-group">
                <label>Image <span class="required">*</span></label>
                <input type="file" name="file" class="form-control-file" />
                <span class="help-block">Only valid images are accepted in jpg, gif, png format and that does not exceed 900 x 473 pixels.</span>
            </div>

            <div class="text-right">
                <input type="submit" class="btn btn-primary" name="btnImage" id="btnImage" value="Save" data-endrequest="refreshImage">
            </div>
        </form>
    </div>
</div>
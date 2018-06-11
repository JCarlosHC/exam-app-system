$(document).ready(function () {

    //Plan de estudio
    $('#editPlan').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('idplan');
        var recipient1 = button.data('description');
        var recipient2 = button.data('startdate');
        var recipient3 = button.data('enddate');
        // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.

        var modal = $(this);
        modal.find('.modal-body #idPlan').val(recipient0);
        modal.find('.modal-body #description').val(recipient1);
        modal.find('.modal-body #cStartDate').val(recipient2);
        modal.find('.modal-body #cEndDate').val(recipient3);
    });

    // Schools
    $('#editSchool').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('id');
        var recipient1 = button.data('description');
        var recipient2 = button.data('acronym');
        // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.

        var modal = $(this);
        modal.find('.modal-body #idSchool').val(recipient0);
        modal.find('.modal-body #description').val(recipient1);
        modal.find('.modal-body #acronym').val(recipient2);
    });
    
    // Career
    $('#editCareer').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('id');
        var recipient1 = button.data('description');
        // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.

        var modal = $(this);
        modal.find('.modal-body #idCareer').val(recipient0);
        modal.find('.modal-body #description').val(recipient1);
    });
    
    // Subject
    $('#editSubject').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('id');
        var recipient1 = button.data('description');
        // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.

        var modal = $(this);
        modal.find('.modal-body #idSubject').val(recipient0);
        modal.find('.modal-body #description').val(recipient1);
    });
    
    // User
    $('#editUser').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('iduser');
        var recipient1 = button.data('firstname');
        var recipient2 = button.data('pname');
        var recipient3 = button.data('mname');
        var recipient4 = button.data('email');
        var recipient5 = button.data('phone');
        var recipient6 = button.data('typeuser');
        var modal = $(this);
        modal.find('.modal-body #iduser').val(recipient0);
        modal.find('.modal-body #firstname').val(recipient1);
        modal.find('.modal-body #psurname').val(recipient2);
        modal.find('.modal-body #msurname').val(recipient3);
        modal.find('.modal-body #email').val(recipient4);
        modal.find('.modal-body #phone').val(recipient5);
        modal.find('.modal-body #typeuser').val(recipient6);
    });
    
    // Type user
    $('#editType').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('id');
        var recipient1 = button.data('description');
        
        var modal = $(this);
        modal.find('.modal-body #idType').val(recipient0);
        modal.find('.modal-body #description').val(recipient1);
    });
    
    // Type exam
    $('#editTypeExam').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('id');
        var recipient1 = button.data('description');
        
        var modal = $(this);
        modal.find('.modal-body #idType').val(recipient0);
        modal.find('.modal-body #description').val(recipient1);
    });
    
    // User
    $('#editStudent').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var recipient0 = button.data('id');
        var recipient1 = button.data('firstname');
        var recipient2 = button.data('pname');
        var recipient3 = button.data('mname');
        var recipient4 = button.data('email');
        var recipient5 = button.data('phone');
        var recipient6 = button.data('idplan');
        var recipient7 = button.data('idschool');
        var recipient8 = button.data('idcareer');
        
        
        var modal = $(this);
        modal.find('.modal-body #id').val(recipient0);
        modal.find('.modal-body #firstname').val(recipient1);
        modal.find('.modal-body #psurname').val(recipient2);
        modal.find('.modal-body #msurname').val(recipient3);
        modal.find('.modal-body #email').val(recipient4);
        modal.find('.modal-body #phone').val(recipient5);
        modal.find('.modal-body #planest').val(recipient6);
        modal.find('.modal-body #escuela').val(recipient7);
        modal.find('.modal-body #carrera').val(recipient8);
    });
    
    
});


	
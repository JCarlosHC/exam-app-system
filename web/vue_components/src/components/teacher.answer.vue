<template>
    <div>
        <loader v-if="loading" height="200"></loader>
        <!-- Lessons List -->
        <ul v-if="!loading" class="list-group">
            <!-- Listado de las lecciones creadas -->
            <li v-for="item in answers" class="list-group-item">
                <div class="input-group">
                    <input type="text" class="form-control" :value="item.answer" readonly>
                    <span class="input-group-btn">
                    <button @click="remove(item.id)" class="btn btn-danger" type="button" title="Delete">
                        <i class="fa fa-trash"></i>
                    </button>
                    <span class="input-group-btn">
                        <button v-if="item.status == 0" @click="changeStatus(item.id, item.status)" class="btn btn-danger" type="button" title="change status">
                            <i class="fa fa-times-circle bg-danger"></i>
                        </button>
                        <button v-else @click="changeStatus(item.id, item.status)" class="btn btn-success" type="button" title="Change status">
                            <i class="fa fa-check-circle bg-success"></i>
                        </button>
                    </span>
                </div>
            </li>
             <!-- Nuevas Respuestas -->
            <li class="list-group-item">
                <div v-if="newEntry.Error.length > 0" class="alert alert-danger">{{ newEntry.Error }}</div>
                <div class="input-group">
                    <input v-model="newEntry.Answer" type="text" class="form-control" placeholder="New Answer">
                    <span class="input-group-btn">
                        <button @click="insert" class="btn btn-default" type="button" title="Register">
                            <i class="fa fa-plus"></i>
                        </button>
                    </span>
                </div>
            </li>
        </ul>

    </div>
</template>
<script>

import loader from "./global.loader.vue";
export default {
 name: "answerquestion",
  components: {
    loader
  },
  props: {
    answers: {
      type: Object,
      requide: true
    },
    questionId: {
      type: Number,
      requide: true
    }
  },
  data() {
    return {
      loading: false,
      newEntry: {
        Answer: "",
        Error: ""
      },
      entry: {
        Id: 0,
        Answer: "",
        IdStatus: 0,
        Error: ""
      }
    };
  },
  mounted() {
    
  },
  updated() {
    // Desde aqui podemos ejecutar plugins de jQuery
  },
  computed: {},
  methods: {
    changeStatus(id, status) {
      var self = this;
      self.loading = true;
      var pstatus;
      if(status == 0){
          pstatus = 1;
      } else {
          pstatus = 0
      }
      $.post(
        "servletQuestion",
        {
          questionId: self.questionId,
          answerId: id,
          action: "changeAnswer",
          status: pstatus
        },
        function(r) {
          self.loading = false;

          if (r.msg != null) {
            // Si hay error mostramos mensaje
            self.newEntry.Error = r.Message;
          } else {
            // En caso de éxito limpiamos todo
            self.newEntry.Answer = "";
            self.newEntry.Error = "";
            self.answers = r.answers;
          }
        },
        "json"
      );
    },
    insert() {
      let self = this;
      self.loading = true;

      $.post(
        "servletQuestion",
        {
          questionId: self.questionId,
          answerId: 0,
          answer: self.newEntry.Answer,
          action: "saveAnswer"
        },
        function(r) {
          self.loading = false;

          if (r.msg != null) {
            // Si hay error mostramos mensaje
            self.newEntry.Error = r.Message;
          } else {
            // En caso de éxito limpiamos todo
            self.newEntry.Answer = "";
            self.newEntry.Error = "";
            self.answers = r.answers;
          }
        },
        "json"
      );
    },
    remove(id) {
      if (!confirm("Are you sure you are doing this action?")) {
        return;
      }

      let self = this;
      self.loading = true;

      $.post(
        "/instructor/deleteLesson",
        {
          id: id
        },
        function(r) {
          self.loading = false;
          self.all();
        },
        "json"
      );
    }
  }
};
</script>


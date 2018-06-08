<template>
<div>
  <loader v-if="loading" height="200"></loader>
  <!-- Lessons List -->
  <ul v-if="!loading" class="list-group">
    <!-- Listado de las lecciones creadas -->
    <li v-for="item in questions" class="list-group-item">
        <div class="input-group">
            <input type="text" class="form-control" :value="item.question" readonly>
            <span class="input-group-btn">
            <button @click="remove(item.Id)" class="btn btn-danger" type="button" title="Delete">
                <i class="fa fa-trash"></i>
            </button>
            <button @click="get(item.Id)" type="button" data-toggle="modal" data-target="#question-edit" class="btn btn-default" title="Edit">
                <i class="fa fa-edit"></i>
            </button>
            </span>
        </div>
    </li>

    <!-- Nuevas lecciones -->
    <li class="list-group-item">
        <div v-if="newEntry.Error.length > 0" class="alert alert-danger">{{ newEntry.Error }}</div>
        <div class="input-group">
            <input v-model="newEntry.Question" type="text" class="form-control" placeholder="New Question">
            <span class="input-group-btn">
                <button @click="insert" class="btn btn-default" type="button" title="Register">
                    <i class="fa fa-plus"></i>
                </button>
            </span>
        </div>
    </li>
  </ul>

  <!-- Modal -->
  <div class="modal fade" id="question-edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog modal-lg" role="document">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title" id="myModalLabel">Lección #1</h4>
              </div>
              <div class="modal-body">
                <div v-if="entry.Error.length > 0" class="alert alert-danger">{{ entry.Error }}</div>
                <loader v-if="loadingEdit" height="200"></loader>
                <div v-if="!loadingEdit">
                  <div class="form-group">
                      <label>Name <span class="text-danger">*</span></label>
                      <input v-model="entry.Name" type="text" name="Name" class="form-control" value="Lesson #1" />
                  </div>

                  <!-- Un pequeño hack para resolver esto -->
                  <div class="form-group">
                      <label>Content <span class="required">*</span></label>
                      <textarea id="wysiwyg" name="Content" class="form-control">{{ entry.Content }}</textarea>
                      <input id="wysiwygHidden" type="hidden" v-model="entry.Content" />
                  </div>

                  <div class="form-group">
                      <label>Video <small>[Opcional]</small></label>
                      <input v-model="entry.Video" type="text" name="Video" class="form-control" />
                      <small>Enter the code of your video</small>
                  </div>

                  <div class="text-right">
                      <button @click="update" type="button" class="btn btn-default">
                          Save
                      </button>
                  </div>
                </div>
              </div>
          </div>
      </div>
  </div>
</div>
</template>

<script>
import loader from "./global.loader.vue";

export default {
  name: "teacherquestion",
  components: {
    loader
  },
  props: {
    id: {
      type: Number,
      requide: true
    }
  },
  data() {
    return {
      loading: false,
      loadingEdit: false,
      newEntry: {
        Question: "",
        Error: ""
      },
      entry: {
        Id: 0,
        Name: "",
        Content: "",
        Video: "",
        Order: 0,
        Error: ""
      },
      questions: []
    };
  },
  mounted() {
    this.all();
  },
  updated() {
    // Desde aqui podemos ejecutar plugins de jQuery
    var self = this;

    $("#wysiwyg").trumbowyg();

    // Pequeño hack para setear el valor manualmente
    $("#wysiwyg").on("tbwblur", function() {
      self.entry.Content = $(this).val();
    });
  },
  computed: {},
  methods: {
    all() {
      let self = this;
      self.loading = true;

      $.post(
        "servletQuestion",
        {
          examId: self.id,
          action: "getQuestions"
        },
        function(r) {
          self.questions = r.questions;
          self.loading = false;
        },
        "json"
      );
    },
    get(id) {
      var self = this;
      self.loadingEdit = true;

      $.post(
        "servletQuestion",
        {
          id: id,
          action: "getQuestion"
        },
        function(r) {
          self.entry.Id = r.Id;
          self.entry.Name = r.Name;
          self.entry.Content = r.Content;
          self.entry.Video = r.Video;
          self.entry.Error = "";

          self.loadingEdit = false;
        },
        "json"
      );
    },
    update() {
      let self = this;
      self.loadingEdit = true;

      $.post(
        "/instructor/updateLesson",
        self.entry,
        function(r) {
          self.loadingEdit = false;

          if (!r.Response) {
            // Si hay error mostramos mensaje
            self.entry.Error = r.Message;
          } else {
            self.entry.Error = "";
            self.all();
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
          examId: self.id,
          questionId: 0,
          question: self.newEntry.Question,
          action: "saveQuestion"
        },
        function(r) {
          self.loading = false;

          if (r.msg != null) {
            // Si hay error mostramos mensaje
            self.newEntry.Error = r.Message;
          } else {
            // En caso de éxito limpiamos todo
            self.newEntry.Question = "";
            self.newEntry.Error = "";

            self.all();
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
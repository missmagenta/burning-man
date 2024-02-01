<template>
  <div>
    <img v-if="main_inf" id="img" src="../assets/arts.jpg" alt="Image"/>
    <div class="overlay-user">
      <div v-if="main_inf == true" class="title">
        <h2 class="h2">
          Зарегистрируйте свой арт объект
        </h2>
        <div v-if="main_inf == true" id="div-inline">
          <ArgsApplyForm
              v-model:p_art_object="p_art_object"
              v-model:p_description="p_description"
              v-model:p_material="p_material"
              v-model:p_location="p_location"
              v-model:p_time="p_time"
              v-model:p_light="p_light"
              v-model:p_link="p_link"
          />
          <div v-if="main_inf == true" class="card flex justify-content-center" id="div-inline">
            <ButtonBlock v-bind:buttons="buttons"
                         v-on:goBack="goBack"
                         v-on:submit="submit"
                         v-on:watchApplications="watchApplications"/>
          </div>
        </div>
      </div>
    </div>
    <div v-if="check == true" class="card flex justify-content-center" id="div-inline">
      <ButtonBlock v-bind:buttons="application_buttons"
                   v-on:goBackToMainInf="goBackToMainInf"/>
    </div>
    <div v-if="check == true" class="card-reg">
      <h3 class="table-heading" style="text-align: center"> Список ваших заявок </h3>
      <DataTable :value="applic_data" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]"
                 tableStyle="min-width: 90rem">
        <Column v-for="col of applic_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                style="width: 20%; font-size: 18px;"></Column>
      </DataTable>
    </div>

  </div>
</template>

<script>
import ArgsApplyForm from "@/components/pcomponents/blocks/ArgsApplyForm";
import ButtonBlock from "@/components/pcomponents/blocks/ButtonBlock";
import {mapState} from "vuex";
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';

export default {
  components: {
    ArgsApplyForm,
    ButtonBlock,
    DataTable,
    Column
  },
  name: "ParticipantApplication",

  data() {
    return {
      data: null,
      applic_data: null,

      main_inf: true,
      check: false,

      buttons: [
        {msg: 'назад', command: 'goBack'},
        {msg: 'подтвердить', command: 'submit'},
        {msg: 'смотреть мои заявки', command: 'watchApplications'}
      ],

      application_buttons: [
        {msg: 'назад', command: 'goBackToMainInf'},
      ],

      applic_columns: [
        {field: 'eventName', header: 'Event'},
        {field: 'name', header: 'Name'},
        {field: 'description', header: 'Description'},
        {field: 'approximateInstallationTime', header: 'Installation Time'},
        {field: 'lightingEquipment', header: 'Light'},
        {field: 'material', header: 'Material'},
        {field: 'desiredLocation', header: 'Desired Location'},
        {field: 'applicationDate', header: 'Application Date'},

      ],

      p_art_object: "",
      p_description: "",
      p_time: "",
      p_link: "",
      p_location: "",
      p_material: "",
      p_light: ""
    }
  },

  computed: mapState({
    cur_applic_data: state => state.event.applications_table_data
  }),

  methods: {
    handleClose() {
      localStorage.removeItem("token");
    },
    goBack() {
      this.$router.push({name: 'reg-form-page'});
    },
    goBackToMainInf() {
      this.main_inf = true;
      this.check = false;
    },
    submit() {
      if (this.check_apply()) {
        let artObject = this.p_art_object;
        let description = this.p_description;
        let time = this.p_time;
        let location = this.p_location;
        let material = this.p_material;
        let light = this.p_light;
        let link = this.p_link;
        this.$store.dispatch('APPLY_FOR_ART_COMPETITION', {
          artObject,
          description,
          location,
          time,
          material,
          light,
          link
        })
            .then((resp) => {
                  console.log(resp);
                  this.check = true;
                  this.main_inf = false;
                  this.$store.dispatch('GET_MY_APPLICATIONS')
                      .then(() => {
                        this.applic_data = this.cur_applic_data;
                        console.log("mapping your applics");
                        console.log(this.cur_applic_data);
                        console.log(this.applic_data);
                      });
                },
                err => this.showError(err));
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }
    },

    watchApplications() {
      this.check = true;
      this.main_inf = false;
      this.$store.dispatch('GET_MY_APPLICATIONS')
          .then(() => {
            this.applic_data = this.cur_applic_data;
            console.log("mapping your applics");
            console.log(this.cur_applic_data);
            console.log(this.applic_data);
          });
    },
    getStringDate(date) {
      let str_date = date.getFullYear() + "-";
      if (date.getMonth() < 10) {
        str_date = str_date + "0" + (date.getMonth() + 1) + "-";
      } else {
        str_date = str_date + (date.getMonth() + 1) + "-";
      }
      if (date.getDate() < 10) {
        str_date = str_date + "0" + date.getDate();
      } else {
        str_date = str_date + date.getDate();
      }
      return str_date;
    },
    check_apply() {
      return this.p_art_object != null && this.p_art_object != undefined && this.p_description != null && this.p_description != undefined
          && this.p_location != null && this.p_location != undefined && this.p_material != null && this.p_material != undefined
    },

    showErrorFromFront(text) {
      this.$notify({
        group: "error",
        title: 'Ошибка',
        text: text,
        type: 'error'
      });
    },
    showError(err) {
      console.log(err);
      this.$notify({
        group: "error",
        title: 'Ошибка',
        text: err.message,
        type: 'error'
      });
    }
  },

}
</script>


<style scoped>

.overlay-user {
  top: 0;
  left: 0;
  margin: 100px 0px 0px 180px;
  width: 100%;
  height: 100%;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: center;
  color: rgb(26, 36, 201); /* Optional: set the text color to be visible on the image */
}

</style>
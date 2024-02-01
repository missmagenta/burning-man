<template>
  <div id="main-div">
    <Header/>
    <div class="main-background">
      <img id="img" src="../assets/burning.jpg" alt="Image"/>
      <div class="overlay-user">
        <div class="table-name">
          Burning Main TimeLine History:
        </div>
        <div class="card">
          <DataTable v-model:selection="selectedEvent" :value="event_content" selectionMode="single" dataKey="id"
                     tableStyle="min-width: 50rem" @rowSelect="showDialog" @rowUnselect="hideDialog">
            <Column v-for="col of columns" :key="col.field" :field="col.field" sortable :header="col.header"
                    style="width: 20%"></Column>
          </DataTable>

          <Dialog :visible="displayDialog" :style="{ width: '55rem' }" @hide="hideDialog"
                  modal :breakpoints="{ '1199px': '75vw', '575px': '90vw' }">
            <div class="dialog-content">
              <div class="event-info"><strong>Событие</strong>: {{ selectedEvent.name }}</div>
              <div class="event-info"><strong>Место</strong>: {{ selectedEvent.location }}</div>
              <div class="event-info"><strong>Координаты</strong>: {{ selectedEvent.coordinates }}</div>
              <div class="event-info"><strong>Количество человек</strong>: {{ selectedEvent.capacity }}</div>
              <div class="event-info"><strong>Дата начала</strong>: {{ selectedEvent.startDate }}</div>
              <div class="event-info"><strong>Дата окончания</strong>: {{ selectedEvent.endDate }}</div>
              <div class="event-info"><strong>Статус</strong>: {{ selectedEvent.eventStatus }}</div>
              <div class="event-info"><strong>Главная тема фестиваля</strong>: {{ selectedEvent.mainThemeName }}</div>
              <div class="event-info"><strong>Описание темы</strong>: {{ selectedEvent.mainThemeDescription }}</div>
            </div>
            <Button label="Закрыть" icon="pi pi-fw pi-times" class="p-button-secondary" @click="hideDialog"/>
          </Dialog>

          <div class="background" style="text-align: center">

            <div style="margin-bottom: 20px; font-size: 18px;">Выберите любое число:</div>
            <Dropdown v-model="record" :options="sortedRecords" filter optionLabel="id" class="w-full md:w-14rem"
                      style="margin: auto"/>
          </div>

          <div class="background" style="text-align: center; margin-top: 20px;">
            <ButtonBlock v-bind:buttons="buttons" v-on:goBack="goBack" v-on:register="register"/>
          </div>

        </div>
      </div>
      <Footer/>
    </div>
  </div>
</template>

<script>

import Header from "@/components/pcomponents/blocks/Header";
import Footer from "@/components/pcomponents/blocks/Footer";
import {mapState} from 'vuex';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Dropdown from "primevue/dropdown";
import Dialog from 'primevue/dialog';
import Button from 'primevue/button';
import ButtonBlock from "@/components/pcomponents/blocks/ButtonBlock";

export default {
  components: {
    ButtonBlock,
    Footer,
    Header,
    DataTable,
    Column,
    Dropdown,
    Dialog,
    Button
  },
  name: 'UserPage',
  data() {
    return {

      buttons: [
        {msg: 'назад', command: 'goBack'},
        {msg: 'регистрация на предстоящее мероприятие', command: 'register'}
      ],


      columns: [
        {field: 'name', header: 'Name'},
      ],

      record: null,
      event_content: [],
      records: [],

      displayDialog: false,
      selectedEvent: null
    }
  },
  computed: mapState({
    sortedRecords() {
      return [...this.records].sort((a, b) => a.id - b.id);
    },

    event_archive_data: state => state.event.event_archive_data,
    data: state => state.event.event_content,
  }),
  watch: {
    record(val) {
      console.log("value");
      console.log(val);
      this.$store.dispatch('SELECT_EVENT', val)
          .then(() => {
            this.$store.dispatch('GET_EVENT_CONTENT')

                .then(() => {
                  this.event_content = this.data;
                  console.log("data");
                  console.log(this.data)
                })
          });

    }
  },
  methods: {

    handleClose() {
      localStorage.removeItem("token");
    },
    goBack() {
      this.$router.push({name: 'auth-page'});
    },
    register() {
      this.$router.push({name: 'reg-form-page'});
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
    },
    showDialog(event) {
      this.selectedEvent = event.data;
      this.displayDialog = true;
    },

    hideDialog() {
      this.displayDialog = false;
    }
  },
  created() {
    this.$store.dispatch('GET_ALL_ARCHIVE_RECORDS')
        .then(() => {
          this.records = this.event_archive_data;
          console.log("mapping archive [] in user")
          console.log(this.event_archive_data)
        });
    this.$store.dispatch('GET_EVENT_CONTENT')
        .then(() => this.event_content = this.data);
  }
}
</script>

<style>
#img {
  object-fit: contain;
  object-position: center;
  width: 100%;
  height: 100%;
  display: block;
  margin: 0 auto;
}

.main-background {
  position: relative;
}

.table-name {
  text-align: center;
  color: #3139a6;
  font-size: 30px;
  font-weight: bold;
  padding: 2%;
  margin-top: -200px;
}

.overlay-user {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: rgb(26, 36, 201); /* Optional: set the text color to be visible on the image */
}

.dialog-content {
  font-size: 18px;
  padding: 40px;
}

.event-info {
  margin-bottom: 8px; /* Adjust the margin as needed for spacing between elements */
}

</style>
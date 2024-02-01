<template>
  <div id="main-div">
    <Header/>
    <div class="main-background">
      <div class="background">
        <h2 v-if="CUR_OME !== null" class="h2">
          Вы руководите предстоящим мероприятием
        </h2>
        <div v-if="CUR_OME == null" class="title">
          Вы не руководите предстоящим мероприятием, поэтому можете только смотреть инфу,
          но не назначать организаторов на внутренние процессы
        </div>

        <div v-if="CUR_OME !== null" class="card-reg">
          <DataTable v-model:selection="selectedFutureEvent" :value="future_events"
                     selectionMode="single" dataKey="id" tableStyle="min-width: 10rem">
            <Column v-for="col of future_columns" :key="col.field" :field="col.field" sortable
                    :header="col.header" style="width: 14%"></Column>
          </DataTable>
        </div>

        <div class="card flex justify-content-center">
          <div v-if="CUR_OME !== null"> Вы можете поменять статус мероприятия, выбрав статус из списка и нажав на нужный
            ряд в табличке:
          </div>
          <Dropdown v-if="CUR_OME !== null" v-model="eStatus" :options="status_data" filter optionLabel="msg"
                    class="w-full md:w-14rem"/>
        </div>

        <div>
          <ButtonsBlock v-if="CUR_OME !== null" v-bind:buttons="cur_ome_buttons"
                        v-on:goToAssign="goToAssign"
                        v-on:updateStatus="updateStatus"/>
        </div>


      </div>
      <h2 class="h2">
        Архив мероприятий Burning Man
      </h2>
    </div>
    <div v-if="check_part == false" class="card-reg">
      <DataTable v-model:selection="selectedEvent" :value="archive_records" paginator :rows="15"
                 :rowsPerPageOptions="[5, 10, 20, 50]"
                 selectionMode="single" dataKey="id" tableStyle="min-width: 10rem">
        <Column v-for="col of columns" :key="col.field" :field="col.field" sortable
                :header="col.header" style="width: 14%"></Column>
      </DataTable>
    </div>

    <div>
      <ButtonsBlock v-bind:buttons="start_buttons"
                    v-on:goBackToMain="goBackToMain"
                    v-on:seeParticipants="seeParticipants"
                    v-on:seeAllRegProcesses="seeAllRegProcesses"
                    v-on:seeAllSalesProcesses="seeAllSalesProcesses"
                    v-on:goBack="goBack"/>
    </div>


    <div class="table-heading" v-if="check_part == true">
      <h3>Список участников</h3>
      <div v-if="check_part == true" class="card-reg">
        <DataTable :value="participants" paginator :rows="10" :rowsPerPageOptions="[5, 10, 20, 50]"
                   tableStyle="min-width: 50rem">
          <Column v-for="col of participants_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 20%"></Column>
        </DataTable>
      </div>
    </div>

    <div class="table-heading" v-if="reg_wave == true">
      <h3>Список регистрационных волн</h3>
      <div v-if="reg_wave == true" class="card-reg">
        <DataTable :value="reg_processes" paginator :rows="10" :rowsPerPageOptions="[5, 10, 20, 50]"
                   tableStyle="min-width: 50rem">
          <Column v-for="col of reg_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 20%"></Column>
        </DataTable>
      </div>
    </div>

    <div class="table-heading" v-if="sales_wave == true">
      <h3>Список продаж</h3>
      <div v-if="sales_wave == true" class="card-reg">
        <DataTable :value="sales_processes" paginator :rows="10" :rowsPerPageOptions="[5, 10, 20, 50]"
                   tableStyle="min-width: 50rem">
          <Column v-for="col of sales_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 20%"></Column>
        </DataTable>
      </div>
    </div>

  </div>
  <Footer/>
</template>

<script>
import Header from "@/components/pcomponents/blocks/Header";
import ButtonsBlock from "@/components/pcomponents/blocks/ButtonBlock";
import Footer from "@/components/pcomponents/blocks/Footer";
import {mapState} from 'vuex';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Dropdown from 'primevue/dropdown';

export default {
  components: {
    Footer,
    Header,
    ButtonsBlock,
    DataTable,
    Column,
    Dropdown
  },
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Main',
  data() {
    return {

      check_part: false,
      reg_wave: false,
      sales_wave: false,

      participants: [],

      selectedEvent: null,
      selectedFutureEvent: null,
      eStatus: null,

      reg_processes: [],
      sales_processes: [],

      start_buttons: [
        {msg: 'выйти', command: 'goBackToMain'},
        {msg: 'посмотреть все рег процессы', command: 'seeAllRegProcesses'},
        {msg: 'посмотреть все процессы продаж', command: 'seeAllSalesProcesses'},
        {msg: 'посмотреть всех участников', command: 'seeParticipants'},
        {msg: 'назад', command: 'goBack'},
      ],
      cur_ome_buttons: [
        {msg: 'перейти к назначению организаторов на мероприятие', command: 'goToAssign'},
        {msg: 'обновить статус вашего мероприятия', command: 'updateStatus'}
      ],
      new_bm_event: false,
      main_info: true,
      p_bm_event: null,
      p_organizer: null,
      update_info: null,
      columns: [
        {field: 'event.name', header: 'Event'},
        {field: 'event.location', header: 'Location'},
        {field: 'mainTheme.name', header: 'Main Theme'},
        {field: 'event.startDate', header: 'Start Date'},
        {field: 'event.endDate', header: 'End Date'},
        {field: 'event.capacity', header: 'Capacity'},
        {field: 'event.eventStatus', header: 'Status'}
      ],

      future_columns: [
        {field: 'name', header: 'Event'},
        {field: 'location', header: 'Location'},
        {field: 'startDate', header: 'Start Date'},
        {field: 'endDate', header: 'End Date'},
        {field: 'capacity', header: 'Capacity'},
        {field: 'eventStatus', header: 'Status'}
      ],

      reg_columns: [
        {field: 'eventName', header: 'Фестиваль'},
        {field: 'ticket', header: 'Тип билета'},
        {field: 'price', header: 'Цена'},
        {field: 'startDate', header: 'Дата открытия регистрации'},
        {field: 'endDate', header: 'Дата закрытия регистрации'},
      ],

      sales_columns: [
        {field: 'eventName', header: 'Фестиваль'},
        {field: 'ticket', header: 'Тип билета'},
        {field: 'ticketsAvailable', header: 'Количество доступных билетов всего'},
        {field: 'price', header: 'Цена'},
        {field: 'startDateTime', header: 'Дата открытия продаж'},
        {field: 'endDateTime', header: 'Дата закрытия продаж'},
      ],

      participants_columns: [
        {field: 'eventName', header: 'Фестиваль'},
        {field: 'personName', header: 'Имя'},
        {field: 'personSurname', header: 'Фамилия'},
        {field: 'purchaseDateTime', header: 'Дата покупки'},
        {field: 'ticketsAmountPurchased', header: 'Tickets Purchased'},
        {field: 'personEmail', header: 'Email'},
        {field: 'personPhone', header: 'Телефон'},
      ],

      archive_records: [],
      future_events: [],

      status_data: [
        {msg: "Canceled", code: "Canceled"},
        {msg: "In progress", code: "In_progress"},
        {msg: "Stopped", code: "Stopped"},
        {msg: "Completed", code: "Completed"},
        {msg: "Scheduled", code: "Scheduled"}]
    }
  },

  created() {
    this.$store.dispatch('GET_ALL_ARCHIVE_RECORDS')
        .then(() => {
          this.archive_records = this.event_archive_data;
          console.log("mapping archive [] in user")
          console.log(this.event_archive_data)
        });
    this.$store.dispatch('GET_ALL_FUTURE_EVENTS')
        .then(() => {
              console.log("events");
              this.future_events = this.fut_bm_event;
              console.log(this.future_events);
            },
            err => this.showError(err));
  },
  computed: mapState({
    event_archive_data: state => state.event.event_archive_data,
    fut_bm_event: state => state.event.future_events_data,

    cur_reg_table_data: state => state.event.event_reg_table_data,
    cur_sales_table_data: state => state.event.event_sales_table_data,

    cur_part_pur_data: state => state.event.queue_purchase_for_sales_manager,

    CUR_OME() {
      console.log("cur ome");
      console.log(this.$store.getters.CUR_OME);
      return this.$store.getters.CUR_OME;
    }
  }),
  methods: {
    handleClose() {
      localStorage.removeItem("token");
    },
    goBackToMain() {
      this.$router.push({name: 'auth-page'});
    },
    goBack() {
      // localStorage.removeItem("token");
      this.check_part = false;
    },
    startNew() {
      this.new_bm_event = true;
      this.main_info = false;
    },
    seeAllRegProcesses() {
      if (this.selectedEvent) {
        let eventId = this.selectedEvent.id;
        console.log(eventId);
        this.$store.dispatch('GET_ALL_REG_PROCESSES', {eventId})
            .then((resp) => {
                  console.log(resp);
                  // this.main_info = false;
                  this.reg_wave = true;
                  this.sales_wave = false;
                  this.reg_processes = this.cur_reg_table_data;
                  console.log(this.cur_reg_table_data);
                  console.log(this.reg_processes);

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },
    seeAllSalesProcesses() {
      if (this.selectedEvent) {
        let eventId = this.selectedEvent.id;
        console.log(eventId);
        this.$store.dispatch('GET_ALL_SALES_PROCESSES', {eventId})
            .then((resp) => {
                  console.log(resp);
                  // this.main_info = false;
                  this.sales_wave = true;
                  this.reg_wave = false;
                  this.sales_processes = this.cur_sales_table_data;
                  console.log(this.cur_sales_table_data);
                  console.log(this.sales_processes);

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },
    seeParticipants() {
      if (this.selectedEvent) {
        let eventId = this.selectedEvent.id;
        console.log(eventId);
        this.$store.dispatch('SEE_PARTICIPANTS_EVENT', {eventId})
            .then((resp) => {
                  console.log(resp);
                  this.check_part = true;
                  this.reg_wave = false;
                  this.sales_wave = false;
                  this.participants = this.cur_part_pur_data;
                  console.log(this.cur_part_pur_data);
                  console.log(this.participants);

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },

    goToAssign() {
      this.$router.push({name: 'assign-page'});
    },
    updateStatus() {
      if (this.selectedFutureEvent) {
        const eventId = this.selectedFutureEvent.id;
        const status = this.eStatus.code;
        console.log(eventId, status);
        this.$store.dispatch('UPDATE_EVENT_STATUS', {eventId, status})
            .then(resp => {
              console.log(resp);
              this.$store.dispatch('GET_ALL_FUTURE_EVENTS')
                  .then(() => {
                        console.log("events");
                        this.future_events = this.fut_bm_event;
                        console.log(this.future_events);
                      },
                      err => this.showError(err));
            })

      }
    },

    check_new_bm_event() {
      return this.p_bm_event != null && this.p_organizer != undefined && this.p_bm_event != null && this.p_organizer != undefined;
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
      this.$notify({
        group: "error",
        title: 'Ошибка',
        text: err.message,
        type: 'error'
      });
    },
    showInfo(msg) {
      this.$notify({
        group: "info",
        title: 'Внимание!',
        text: msg,
        type: 'info'
      });
    }
  },

}
</script>
<style>
.p-dropdown-label {
  width: 100%;
}

.background {
  margin: 0 auto;
  padding: 5px 5px 5px 5px;
  width: 60%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.title {
  text-align: center;
}


.table-name {
  text-align: center;
  color: #3139a6;
  font-size: 30px;
  font-weight: bold;
  padding: 2%;
  margin-top: -200px;
}

#main-div {
  min-width: 100%;
  box-sizing: border-box;
  min-height: calc(100vh - 50px);
  position: relative;
  padding-bottom: 80px;
}

#div-inline {
  width: 100%;
}

.div-block {
  justify-content: center;
  display: flex;
  margin: 0 auto;
}

.simple-text {
  padding: 0% 0% 0% 1%;
  font-size: 14px;
}
</style>
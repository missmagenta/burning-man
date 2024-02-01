<template>
  <div id="main-div">
    <Header />
    <div class="main-background">
      <div v-if="main_inf" class="table-name">
        Идут продажи билетов на фестиваль определенного типа
      </div>
      <div v-if="new_sales" class="table-name">
        Запуск нового процесса продажи билетов
      </div>
      <div id="div-inline">
        <ArgsBlockSales v-if="new_sales"
                         v-model:p_registration="p_registration"
                         v-model:p_start_date_time="p_start_date_time"
                         v-model:p_end_date_time="p_end_date_time"
                         v-model:p_tickets_available="p_tickets_available" />
        <div v-if="is_main_org && main_inf">
          <ButtonBlock v-bind:buttons="buttons_for_main_org" v-on:goBack="goBack" v-on:newSales="newSales" v-on:finishSales="finishSales" />
        </div>
        <div v-if="is_sales_manager && main_inf">
          <ButtonBlock v-bind:buttons="buttons_for_sales_manager" v-on:goBack="goBack" v-on:newSales="newSales" v-on:finishSales="finishSales" />
        </div>
        <div v-if="new_sales">
          <ButtonBlock v-bind:buttons="buttons_for_new_rec" v-on:goBackToMain="goBackToMain" v-on:createNewSales="createNewSales" />
        </div>
      </div>
      <div class="table-name">
        Список процессов продаж билетов:
      </div>
    </div>
    <div class="card">
      <DataTable :value="data" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]" tableStyle="min-width: 50rem">
        <Column v-for="col of columns" :key="col.field" :field="col.field" sortable :header="col.header" style="width: 20%"></Column>
      </DataTable>
    </div>
  </div>
  <Footer />
</template>

<script>
import Header from "@/components/pcomponents/blocks/Header";
import ButtonBlock from "@/components/pcomponents/blocks/ButtonBlock";
import ArgsBlockSales from "@/components/pcomponents/blocks/ArgsBlockSales";
import Footer from "@/components/pcomponents/blocks/Footer";
import { mapState } from 'vuex';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';

export default {
  components: {
    Footer,
    Header,
    ButtonBlock,
    DataTable,
    ArgsBlockSales,
    Column,
  },
  name: 'Proccessing_sales',
  data() {
    return {
      data: [],
      main_inf: true,
      new_sales: false,
      buttons_for_main_org: [
        { msg: 'назад', command: 'goBack' },
        { msg: 'новый процесс продаж', command: 'newSales' },
        { msg: 'закончить данный процесс продаж', command: 'finishSales' },
      ],
      buttons_for_sales_manager: [
        { msg: 'назад', command: 'goBack' },
        { msg: 'новый процесс продаж', command: 'newSales' },
        { msg: 'закончить данный процесс продаж', command: 'finishSales' },
      ],
      buttons_for_new_rec: [
        { msg: 'назад', command: 'goBackToMain' },
        { msg: 'создать', command: 'createNewSales' },
      ],
      is_main_org: (localStorage.getItem("role") == '0'),
      is_sales_manager: (localStorage.getItem("role") == '1'),
      columns: [
        { field: 'registrationId', header: 'Тип билета' },
        { field: 'startDateTime', header: 'Дата начала' },
        { field: 'endDateTime', header: 'Дата окончания' },
        { field: 'ticketsAvailable', header: 'Количество доступных билетов' },

      ],
      p_registration: null,
      p_start_date_time: null,
      p_end_date_time: "",
      p_tickets_available: "",
    }
  },
  computed: mapState({
    cur_data: state => state.event.event_sales_table_data
  }),
  methods: {
    handleClose() {
      localStorage.removeItem("token");
    },
    goBack() {
      if (localStorage.getItem("role") == 0) {
        this.$router.push({ name: 'main-org-page' });
      } else if (localStorage.getItem("role") == 1) {
        this.$router.push({ name: 'processing-ticket-reg-page' });
      } else {
        this.$router.push({ name: 'auth-page' });
      }
    },
    newSales() {
      this.main_inf = false;
      this.new_sales = true;
    },
    goBackToMain() {
      this.main_inf = true;
      this.new_sales = false;
    },
    finishSales() {
      this.$store.dispatch('FINISH_SALES_PROCESS')
          .then(() => this.$router.push({ name: 'proccessing-ticket-sales-page' }));
    },
    createNewSales() {
      if (this.check_new_sales()) {
        let registration = this.p_registration.id;
        let startDateTime = this.getStringDate(this.p_start_date_time);
        let endDateTime = this.getStringDate(this.p_end_date_time);
        let ticketsAvailable = this.p_tickets_available;
        console.log(registration, startDateTime, endDateTime, ticketsAvailable);
        this.$store.dispatch('ADD_SALES_RECORD', { registration , startDateTime, endDateTime, ticketsAvailable })
            .then((resp) => {
                  console.log(resp);
                  this.main_inf = true;
                  this.new_sales = false;
                  this.$store.dispatch('GET_ALL_SALES_RECORDS')
                      .then(() => this.data = this.cur_data);

                },
                err => this.showError(err));
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }
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
    check_new_sales() {
      return this.p_registration != null && this.p_registration != undefined && this.p_start_date_time != null && this.p_start_date_time != undefined &&
          this.p_end_date_time != "" && this.p_cur_date_time != undefined && this.p_cur_date_time != null && this.p_tickets_available != "";
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
  created() {
    this.$store.dispatch('GET_ALL_SALES_PROCESSES_ORG')
        .then(() => this.data = this.cur_data);
    console.log("i am data");
    console.log(this.data);
  },
  // getPurchasesBySalesType(regId) {
  //   this.$store.dispatch('GET_PURCHASES_BY_SALES_TYPE', regId)
  //       .then((response) => {
  //         this.data = response.data;
  //       })
  //       .catch((err) => {
  //         console.log(err);
  //       });
  // }
}
</script>
<style>

</style>
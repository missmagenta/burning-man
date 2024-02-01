<template>
  <div id="main-div">
    <Footer/>
    <Header/>
    <div class="title">
      <h2 v-if="CUR_OME !== null && sales == true" class="h2">
        Вы являетесь организатором предстоящего события<br>
        Организуйте свои продажи билетов
      </h2>
      <h2 v-if="CUR_OME !== null && main" class="h2">
        Вы являетесь организатором предстоящего события<br>
        Создание новой регистрационной волны
      </h2>

      <h2 v-if="CUR_OME == null && main" class="h2">
        Вы не являетесь организатором предстоящего события<br>
        Посмотрите ваш опыт организации в прошлом
      </h2>

      <h2 v-if="CUR_OME == null && sales == true" class="h2">
        Вы не являетесь организатором предстоящего события<br>
        Посмотрите ваш опыт организации в прошлом
      </h2>

      <div id="div-inline">
        <ArgsBlockReg v-if="main && CUR_OME !== null && sales == false"
                      v-model:p_ticket="p_ticket"
                      v-model:p_price="p_price"/>
      </div>

      <div v-if="main && CUR_OME == null">
        <ButtonBlock v-bind:buttons="buttons_for_sales_manager"
                     v-on:goBack="goBack"
                     v-on:goToSales="goToSales"
                     v-on:watch="watch"/>
      </div>
      <div v-if="main && CUR_OME !== null">
        <ButtonBlock v-bind:buttons="buttons_for_new_ticket_reg"
                     v-on:goBackToAuth="goBackToAuth"
                     v-on:createNewTicketReg="createNewTicketReg"
                     v-on:finish="finish"
                     v-on:goToSales="goToSales"
                     v-on:watch="watch"
        />
      </div>

      <h3 v-if="main || sales == true" class="table-heading">
        Список регистрационных волн под вашим руководством:
      </h3>
    </div>
    <div v-if="main || sales == true" class="card-reg">
      <DataTable v-model:selection="selectedReg" :value="data" paginator :rows="10" :rowsPerPageOptions="[5, 10, 20, 50]"
                 selectionMode="single" dataKey="id"
                 tableStyle="min-width: 50rem">
        <Column v-for="col of columns" :key="col.field" :field="col.field" sortable :header="col.header"
                style="width: 20%"></Column>
      </DataTable>
      <ArgsBlockSales v-if="sales == true && CUR_OME !== null" v-model:p_tickets_amount="p_tickets_amount"/>
    </div>

    <div v-if="check == true">
      <ButtonBlock v-bind:buttons="buttons_for_registered_users" v-on:goBackToReg="goBackToReg"/>
    </div>
    <div v-if="check_part == true">
      <ButtonBlock v-bind:buttons="buttons_for_participants" v-on:goBackToReg="goBackToReg"/>
    </div>

    <div v-if="sales == true && CUR_OME !== null">
      <ButtonBlock v-bind:buttons="buttons_for_sales_start"
                   v-on:goBackToReg="goBackToReg"
                   v-on:createNewSales="createNewSales"
                   v-on:finishSales="finishSales"
                   v-on:watchParticipants="watchParticipants"
      />
    </div>
    <div v-if="sales == true && CUR_OME == null">
      <ButtonBlock v-bind:buttons="buttons_for_sales_watch"
                   v-on:goBackToReg="goBackToReg"
                   v-on:watchParticipants="watchParticipants"/>
    </div>

    <h3 v-if="!main && sales == true" class="table-heading">
      Список ваших процессов продаж
    </h3>
    <div v-if="sales == true" class="card-reg">
      <DataTable v-model:selection="selectedSale" :value="sales_data" paginator :rows="5"
                 :rowsPerPageOptions="[5, 10, 20, 50]"
                 selectionMode="single" dataKey="id" tableStyle="min-width: 50rem">
        <Column v-for="col of sales_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                style="width: 20%"></Column>
      </DataTable>
    </div>


    <div class="table-heading" v-if="check == true">
      <h3>Список зарегистрированных людей</h3>
      <div v-if="check == true" class="card-reg">
        <DataTable :value="registeredUsers" paginator :rows="10" :rowsPerPageOptions="[5, 10, 20, 50]"
                   tableStyle="min-width: 50rem">
          <Column v-for="col of user_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 20%"></Column>
        </DataTable>
      </div>
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

  </div>

</template>

<script>
import Header from "@/components/pcomponents/blocks/Header";
import ArgsBlockReg from "@/components/pcomponents/blocks/ArgsBlockReg";
import ButtonBlock from "@/components/pcomponents/blocks/ButtonBlock";
// import Footer from "@/components/pcomponents/blocks/Footer";
import {mapState} from 'vuex';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ArgsBlockSales from "@/components/pcomponents/blocks/ArgsBlockSales";

export default {
  components: {
    ArgsBlockSales,
    // Footer,
    Header,
    ButtonBlock,
    ArgsBlockReg,
    DataTable,
    Column,
  },
  name: 'ProccessingTicketReg',
  data() {
    return {
      main: true,
      sales: false,
      check: false,
      check_part: false,

      data: null,
      sales_data: null,

      selectedReg: null,
      selectedSale: null,

      registeredUsers: null,
      participants: null,


      buttons_for_registered_users: [
        {msg: 'назад', command: 'goBackToReg'},
      ],

      buttons_for_participants: [
        {msg: 'назад', command: 'goBackToReg'},
      ],

      buttons_for_main_org: [
        {msg: 'назад', command: 'goBack'},
        {msg: 'запустить регистрацию', command: 'newTicketReg'},
        {msg: 'закончить регистрацию', command: 'finish'},
      ],

      buttons_for_sales_manager: [
        {msg: 'назад', command: 'goBack'},
        {msg: 'перейти на страницу менеджмента продажами', command: 'goToSales'},
        {msg: 'посмотреть список зареганных людей', command: 'watch'},
      ],
      buttons_for_new_ticket_reg: [
        {msg: 'назад', command: 'goBackToAuth'},
        {msg: 'опубликовать волну', command: 'CreateNewTicketReg'},
        {msg: 'закончить волну', command: 'finish'},
        {msg: 'перейти на страницу менеджмента продажами', command: 'goToSales'},
        {msg: 'посмотреть список зареганных людей', command: 'watch'},
      ],

      buttons_for_sales_start: [
        {msg: 'опубликовать продажи', command: 'createNewSales'},
        {msg: 'закончить продажи', command: 'finishSales'},
        {msg: 'назад', command: 'goBackToReg'},
        {msg: 'смотреть участников, которые успешно купили билеты', command: 'watchParticipants'}
      ],
      buttons_for_sales_watch: [
        {msg: 'назад', command: 'goBackToReg'},
        {msg: 'смотреть участников, которые успешно купили билеты', command: 'watchParticipants'}
      ],


      columns: [
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

      user_columns: [
        {field: 'eventName', header: 'Фестиваль'},
        {field: 'personName', header: 'Имя'},
        {field: 'personSurname', header: 'Фамилия'},
        {field: 'registrationDateTime', header: 'Дата регистрации'},
        {field: 'ticketName', header: 'Тип билета'},
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

      p_bm_event: null,
      p_ticket: null,
      p_price: "",
      p_start_date_time: "",
      p_end_date_time: "",

      p_tickets_amount: ""
    }
  },
  computed: mapState({
    cur_process_data: state => state.event.event_reg_table_data,

    cur_part_reg_data: state => state.event.queue_reg_for_sales_manager,
    cur_part_pur_data: state => state.event.queue_purchase_for_sales_manager,

    cur_sales_process_data: state => state.event.event_sales_table_data,


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
    goBack() {
      this.$router.push({name: 'auth-page'});
    },

    goBackToAuth() {
      this.$router.push({name: 'auth-page'});
    },
    finish() {
      if (this.selectedReg) {
        let regId = this.selectedReg.id;
        console.log(regId);
        this.$store.dispatch('FINISH_REG_PROCESS', {regId})
            .then((resp) => {
                  console.log(resp);
                  // this.main = true;
                  // this.new_ticket_reg = false;
                  this.$store.dispatch('GET_ALL_REG_PROCESSES_ORG')
                      .then(() => this.data = this.cur_process_data);

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },
    watch() {
      if (this.selectedReg) {
        this.main = false;
        this.sales = false;
        this.check = true;
        let regId = this.selectedReg.id;
        console.log(regId);
        this.$store.dispatch('SEE_REGISTERED_PEOPLE', {regId})
            .then((resp) => {
                  console.log(resp);
                  console.log("resp is above");
                  this.registeredUsers = this.cur_part_reg_data;
                  console.log(this.cur_part_reg_data);
                  console.log(this.registeredUsers);

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },
    watchParticipants() {
      if (this.selectedSale) {
        this.main = false;
        this.sales = false;
        this.check = false;
        this.check_part = true;
        let salesId = this.selectedSale.id;
        console.log(salesId);
        this.$store.dispatch('SEE_PARTICIPANTS', {salesId})
            .then((resp) => {
                  console.log(resp);
                  this.check_part = true;
                  this.participants = this.cur_part_pur_data;
                  console.log(this.cur_part_pur_data);
                  console.log(this.participants);

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },

    createNewTicketReg() {
      if (this.check_new_ticket_reg()) {
        let ticket_type = this.p_ticket.id;
        let price = this.p_price;
        console.log("start new reg");
        console.log(ticket_type, price);
        this.$store.dispatch('PUBLISH_TICKET_REG', {ticket_type, price})
            .then((resp) => {
                  console.log(resp);
                  // this.main = true;
                  // this.new_ticket_reg = false;
                  this.$store.dispatch('GET_ALL_REG_PROCESSES_ORG')
                      .then(() => this.data = this.cur_process_data);

                },
                err => this.showError(err));
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }
    },
    createNewSales() {
      if (this.selectedReg && this.p_tickets_amount != null &&
          this.p_tickets_amount != undefined && this.p_tickets_amount !== "") {
        let regId = this.selectedReg.id;
        let ticketsAvailable = this.p_tickets_amount;
        console.log("start new sales");
        console.log(regId, ticketsAvailable);
        this.$store.dispatch('PUBLISH_TICKET_SALES', {regId, ticketsAvailable})
            .then((resp) => {
                  console.log(resp);
                  // this.main = true;
                  // this.new_ticket_reg = false;
                  this.$store.dispatch('GET_ALL_SALES_PROCESSES_ORG')
                      .then(() => this.sales_data = this.cur_sales_process_data);

                },
                err => this.showError(err));
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }
    },
    finishSales() {
      if (this.selectedSale) {
        let salesId = this.selectedSale.id;
        console.log(salesId);
        this.$store.dispatch('FINISH_SALES_PROCESS', {salesId})
            .then((resp) => {
                  console.log(resp);
                  // this.main = true;
                  // this.new_ticket_reg = false;
                  this.$store.dispatch('GET_ALL_SALES_PROCESSES_ORG')
                      .then(() => this.sales_data = this.cur_sales_process_data);

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },
    goToSales() {
      this.sales = true;
      this.main = false;
      this.check = false;
    },
    goBackToReg() {
      this.sales = false;
      this.main = true;
      this.check_part = false;
      this.check = false;
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

    check_new_ticket_reg() {
      console.log("ticket");
      console.log(this.p_ticket);
      console.log("price");
      console.log(this.p_price);
      return this.p_ticket != null && this.p_ticket != undefined && this.p_price != null && this.p_price != undefined && this.p_price != '';
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
    this.$store.dispatch('GET_ALL_REG_PROCESSES_ORG')
        .then(() => {
          this.data = this.cur_process_data;
          console.log("hey");
          console.log(this.cur_process_data);
          console.log(this.data);
        });
    this.$store.dispatch('GET_ALL_SALES_PROCESSES_ORG')
        .then(() => {
          this.sales_data = this.cur_sales_process_data;
          console.log("orgs have sales");
          console.log(this.cur_sales_process_data);
          console.log(this.sales_data);
        });

  }
}
</script>
<style>

.table-heading {
  text-align: center;
  color: #120850;
  margin-bottom: 20px;
}

</style>
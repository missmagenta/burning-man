<template>
  <div id="main-div">
    <Header/>
    <div class="main-backgroundd">
      <!--      <img id="img" src="../assets/burning.jpg" alt="Image"/>-->
      <!--      <div class="overlay">-->
      <h2 v-if="main_inf" class="h2">
        Открыта регистрация на билеты на фестиваль
      </h2>

      <div v-if="main_inf" class="card-reg">
        <h3 class="table-heading"> Список регистраций на Burning Man 2024 </h3>
        <h3 class="table-heading">Чтобы заристрироваться кликните на нужный ряд в табличке и нажмите на кнопку </h3>
        <DataTable v-model:selection="selectedReg" :value="reg_data"
                   selectionMode="single" dataKey="id" tableStyle="min-width: 10rem">
          <Column v-for="col of reg_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 10% "></Column>
        </DataTable>
      </div>
      <div style="margin-top: 50px;">

        <div v-if="main_inf" class="reg-buttons">
          <ButtonsBlock v-bind:buttons="buttons"
                        v-on:goBack="goBack"
                        v-on:newReg="newReg"
                        v-on:buyTickets="buyTickets"/>
        </div>

        <div v-if="main_inf && is_participant" class="buttons">
          <ButtonsBlock v-bind:buttons="special_buttons"
                        v-on:myTickets="myTickets"/>
        </div>


      </div>
      <div v-if="purchase == true" class="card-reg">
        <h3 class="table-heading"> Список продаж на Burning Man 2024 </h3>
        <DataTable v-model:selection="selectedSale" :value="sales_data"
                   selectionMode="single" dataKey="id" tableStyle="min-width: 50rem">
          <Column v-for="col of sales_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 20%; font-size: 17px;"></Column>
        </DataTable>
      </div>
      <div id="div-inline">
        <ArgsBuyForm v-if="purchase == true"
                     v-model:p_ticket_amount="p_ticket_amount"
                     v-model:p_passport="p_passport"/>

      </div>
      <div v-if="main_inf" class="card-reg-person">
        <h3 class="table-heading"> Список ваших регистраций </h3>
        <DataTable :value="data" paginator :rows="10" :rowsPerPageOptions="[5, 10, 20, 50]"
                   tableStyle="min-width: 10rem">
          <Column v-for="col of columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 20%;font-size: 17px;"></Column>
        </DataTable>
      </div>
      <div v-if="purchase == true" class="card-reg-person">
        <h3 class="table-heading"> Список ваших регистраций </h3>
        <DataTable v-model:selection="selectedYourReg" :value="data" paginator :rows="5"
                   :rowsPerPageOptions="[5, 10, 20, 50]"
                   selectionMode="single" dataKey="id" tableStyle="min-width: 10rem">
          <Column v-for="col of columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 30%; font-size: 17px;"></Column>
        </DataTable>
      </div>

      <div v-if="purchase == true">
        <ButtonsBlock v-bind:buttons="buttons_for_purchase"
                      v-on:goBackToMain="goBackToMain"
                      v-on:submitPurchase="submitPurchase"/>

      </div>

      <div v-if="!main_inf && !purchase && is_participant">
        <ButtonsBlock v-bind:buttons="buttons_for_apply"
                      v-on:goBackToMain="goBackToMain"
                      v-on:apply="apply"/>

      </div>

      <div v-if="purchase == true || is_participant && !main_inf" class="card-reg">
        <h3 class="table-heading"> Список ваших билетов. До встречи на Burning Man </h3>
        <DataTable :value="my_tickets_data" paginator :rows="15" :rowsPerPageOptions="[5, 10, 20, 50]"
                   selectionMode="single" dataKey="id" tableStyle="min-width: 90rem">
          <Column v-for="col of my_tickets_columns" :key="col.field" :field="col.field" sortable :header="col.header"
                  style="width: 20%; font-size: 18px;"></Column>
        </DataTable>
      </div>
      <!--      </div>-->
    </div>
  </div>
  <Footer/>
</template>

<script>
import Header from "@/components/pcomponents/blocks/Header";
import ButtonsBlock from "@/components/pcomponents/blocks/ButtonBlock";
import ArgsBuyForm from "@/components/pcomponents/blocks/ArgsBuyForm";
import Footer from "@/components/pcomponents/blocks/Footer";
import {mapState} from 'vuex';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';


export default {
  components: {
    Footer,
    Header,
    ButtonsBlock,
    DataTable,
    ArgsBuyForm,
    Column,
  },
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Register',
  data() {
    return {
      data: null,
      reg_data: null,
      sales_data: null,

      my_tickets_data: [],

      selectedReg: null,
      selectedSale: null,
      selectedYourReg: null,

      purchase: false,
      main_inf: true,
      participate: false,
      is_participant: (localStorage.getItem("role") == '4'),

      buttons: [
        {msg: 'назад', command: 'goBack'},
        {msg: 'зарегистрироваться', command: 'newReg'},
        {msg: 'купить билет', command: 'buyTickets'}
      ],

      buttons_for_purchase: [
        {msg: 'назад', command: 'goBackToMain'},
        {msg: 'подтвердить', command: 'submitPurchase'},
      ],

      special_buttons: [
        {msg: 'посмотреть мои билеты', command: 'myTickets'}
      ],

      buttons_for_apply: [
        {msg: 'назад', command: 'goBackToMain'},
        {msg: 'подать заявку на арт объект', command: 'apply'}
      ],

      columns: [
        {field: 'eventName', header: 'Event'},
        {field: 'ticketName', header: 'Ticket Type'},
        {field: 'registrationDateTime', header: 'Registration Time'},
        {field: 'price', header: 'Price $'},
        {field: 'ticketDescription', header: 'Ticket Description'},
        {field: 'personName', header: 'Your name'},
        {field: 'personSurname', header: 'Your surname'},
        {field: 'personEmail', header: 'Your email'},
      ],

      reg_columns: [
        {field: 'eventName', header: 'Event'},
        {field: 'ticket', header: 'Ticket Type'},
        {field: 'price', header: 'Price'},
        {field: 'startDate', header: 'Start Date'},
        {field: 'endDate', header: 'End Date'}
      ],
      sales_columns: [
        {field: 'eventName', header: 'Event'},
        {field: 'ticket', header: 'Ticket Type'},
        {field: 'ticketsAvailable', header: 'Tickets Amount Available'},
        {field: 'price', header: 'price'},
        {field: 'startDateTime', header: 'startDate'},
        {field: 'endDateTime', header: 'endDate'}
      ],

      my_tickets_columns: [
        {field: 'eventName', header: 'Event'},
        {field: 'purchaseDateTime', header: 'Purchase Date'},
        {field: 'ticketName', header: 'Ticket'},
        {field: 'price', header: 'price'},
        {field: 'ticketsAmountPurchased', header: 'Tickets amount'},
      ],

      p_ticket_amount: null,
      p_passport: ""
    }
  },
  computed: mapState({
    cur_data: state => state.event.queue_reg_for_user,
    cur_part_pur_data: state => state.event.queue_purchase_for_user,

    cur_reg_table_data: state => state.event.event_reg_table_data,
    cur_sales_table_data: state => state.event.event_sales_table_data
  }),
  methods: {
    handleClose() {
      localStorage.removeItem("token");
    },
    goBack() {
      this.$router.push({name: 'user-page'});
    },
    newReg() {
      if (this.selectedReg) {
        let regId = this.selectedReg.id;
        console.log(regId);
        this.$store.dispatch('REGISTER', {regId})
            .then((resp) => {
                  console.log(resp);
                  this.$store.dispatch('GET_MY_REGISTRATIONS')
                      .then(() => {
                        this.data = this.cur_data;
                        console.log("mapping part reg data in part ticket reg");
                        console.log(this.cur_data);
                        console.log(this.data);
                      });

                },
                err => this.showError(err));
      } else {
        console.error("No selected data");
      }
    },
    buyTickets() {
      this.purchase = true;
      this.main_inf = false;
    },

    myTickets() {
      this.main_inf = false;
      this.purchase = false;
      this.$store.dispatch('GET_MY_TICKETS')
          .then(() => {
            this.my_tickets_data = this.cur_part_pur_data;
            console.log("my tickets are");
            console.log(this.cur_part_pur_data);
            console.log(this.my_tickets_data);
          });
    },

    goBackToMain() {
      this.main_inf = true;
      this.purchase = false;
    },

    submitPurchase() {
      if (this.selectedYourReg && this.selectedSale && this.check_your_purchase()) {
        let salesId = this.selectedSale.id;
        let partRegId = this.selectedYourReg.id;
        let ticketAmount = this.p_ticket_amount.code;
        let passport = this.p_passport;
        console.log(ticketAmount, passport);
        this.$store.dispatch('BUY', {salesId, partRegId, ticketAmount, passport})
            .then((resp) => {
                  console.log(resp);
                  this.main_inf = true;
                  this.$store.dispatch('GET_MY_TICKETS')
                      .then(() => {
                        this.my_tickets_data = this.cur_part_pur_data;
                        console.log("my tickets are");
                        console.log(this.my_tickets_data);
                      });

                },
                err => this.showError(err));
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }
    },
    apply() {
      this.$router.push({name: 'apply-page'});
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

    check_your_purchase() {
      return this.p_ticket_amount != null && this.p_ticket_amount != undefined &&
          this.p_passport !== ""
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
    this.$store.dispatch('GET_MY_REGISTRATIONS')
        .then(() => {
          this.data = this.cur_data;
          console.log("mapping part reg data in part ticket reg");
          console.log(this.cur_data);
          console.log(this.data);
        });
    this.$store.dispatch('GET_ALL_REG_PROCESSES_FUTURE')
        .then(() => {
          this.reg_data = this.cur_reg_table_data;
          console.log(this.cur_reg_table_data);
          console.log(this.reg_data);

        });
    this.$store.dispatch('GET_ALL_SALES_PROCESSES_FUTURE')
        .then(() => {
          this.sales_data = this.cur_sales_table_data;
          console.log(this.cur_sales_table_data);
          console.log(this.sales_data);

        });
    this.$store.dispatch('GET_MY_TICKETS')
        .then(() => {
          this.my_tickets_data = this.cur_part_pur_data;
          console.log("my tickets are");
          console.log(this.cur_part_pur_data);
          console.log(this.my_tickets_data);
        });
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

.overlay {
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
  color: rgb(255, 255, 255); /* Optional: set the text color to be visible on the image */
}

.card-reg {
  width: 80%;
  height: 150%;
  margin: 0px auto 0 auto;
  padding: 30px;
}

.card-reg-person {
  width: 95%;
  height: 150%;
  margin: 10px auto 0 auto;
  padding: 10px;
}

.table-heading {
  text-align: center;
  color: #120850;
  margin-top: 10px;
  margin-bottom: 15px; /* Регулируйте значение в соответствии с вашими предпочтениями */
}

.h2 {
  text-align: center;
  color: #02064f;
  margin-top: 10px;
}

.reg-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: -70px;
  align-content: center;
  text-align: center;
}

#div-inline {
  color: #2d257a;
}


</style>
<template>
  <div id="main-div">
    <Header />
    <div class="main-background">
      <div v-if="main_inf" class="table-name">
        Открыта продажа билетов на фестиваль
      </div>
      <div v-if="buy" class="table-name">
        Купить билет на фестиваль
      </div>
      <div id="div-inline">
        <ArgsBuyForm v-if="buy"
                     v-model:p_ticket="p_ticket"
                     v-model:p_ticket_amount="p_tickets_amount"
                     v-model:p_passport="p_passport"/>
        <div v-if="main_inf">
          <ButtonsBlock v-bind:buttons="buttons"
                        v-on:goBack="goBack"
                        v-on:buy="buyTicket"
                        v-on:apply="apply"/>
        </div>
        <div v-if="buy">
          <ButtonsBlock v-bind:buttons="buttons_for_purchase" v-on:goBackToMain="goBackToMain" v-on:submit="submit" />
        </div>
      </div>
      <div class="table-name">
        Список ваших билетов:
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
import ButtonsBlock from "@/components/pcomponents/blocks/ButtonBlock";
import ArgsBuyForm from "@/components/pcomponents/blocks/ArgsBuyForm";
import Footer from "@/components/pcomponents/blocks/Footer";
import { mapState } from 'vuex';
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
  name: 'Buy',
  data() {
    return {
      data: null,
      main_inf: true,
      buy: false,
      buttons: [
        { msg: 'назад', command: 'goBack' },
        { msg: 'купить', command: 'buyTicket' },
        { msg: 'подать заявку на арт объект', command: 'apply'}
      ],
      buttons_for_purchase: [
        { msg: 'назад', command: 'goBackToMain' },
        { msg: 'подтвердить', command: 'submit' },
      ],

      columns: [
        // { field: 'id', header: 'id'},
        { field: 'ticketName', header: 'ticket' },
        { field: 'price', header: 'price' },
        { field: 'ticketsAmountPurchased', header: 'amount'},
        { field: 'purchaseDateTime', header: 'time' },
        { field: 'event', header: 'event' },
      ],

      p_ticket: null,
      p_tickets_amount: null,
      p_passport: "",
    }
  },
  computed: mapState({
    cur_data: state => state.event.queue_purchase_for_user
  }),
  methods: {
    handleClose() {
      localStorage.removeItem("token");
    },
    goBack() {
      this.$router.push({name: 'reg-form-page'});
    },
    buyTicket() {
      this.main_inf = false;
      this.buy = true;
    },
    apply() {
      this.$router.push({name: 'apply-page'});
    },
    goBackToMain() {
      this.main_inf = true;
      this.buy = false;
    },
    submit() {
      if (this.check_buy()) {
        let ticket = this.p_ticket.id;
        let amount = this.p_tickets_amount;
        let passport = this.p_passport;
        console.log(ticket);
        this.$store.dispatch('BUY', { ticket, amount, passport })
            .then((resp) => {
                  console.log(resp);
                  this.main_inf = true;
                  this.buy = false;
                  this.$store.dispatch('GET_MY_TICKETS')
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
    check_buy() {
      return this.p_ticket != null && this.p_ticket != undefined && this.p_ticket_amount != null && this.p_ticket_amount != undefined
          && this.p_passport != null && this.p_passport != undefined
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
    this.$store.dispatch('GET_MY_TICKETS')
        .then(() => this.data = this.cur_data);
  }
}
</script>
<style>

</style>
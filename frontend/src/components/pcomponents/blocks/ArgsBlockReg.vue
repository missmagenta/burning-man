<template>
  <div>
    <div class="background justify-content-center" >
      <div class="card flex justify-content-center" id="account">
        <div>Установите тип билетов:</div>
        <Dropdown v-model="ticket" :options="tickets" filter optionLabel="name" class="w-full md:w-14rem"/>
      </div>

    </div>
    <div class="card flex justify-content-center" id="account">
    <div>Установите цену:</div>
    <InputText type="text" v-model="price"/>
  </div>

  </div>
</template>

<script>
import {mapState} from 'vuex';
import Dropdown from 'primevue/dropdown';
import InputText from 'primevue/inputtext';

export default {
  name: "ArgsBlockReg",
  components: {
    Dropdown,
    InputText
  },
  props: ['p_ticket', 'p_price'],
  emits: ['update:p_ticket', 'update:p_price'],
  data() {
    return {
      ticket: null,
      tickets: [],
      price: "",
    }
  },
  watch: {
    ticket(val) {
      this.$emit('update:p_ticket', val);
    },
    price(val) {
      this.$emit('update:p_price', val);
    },
  },
  created() {
    this.$store.dispatch('GET_ALL_TICKETS')
        .then(() => {
              console.log("tickets");
              this.tickets = this.tickets_data;
              console.log(this.tickets);
            },
            err => this.showError(err));
  },
  computed: mapState({
    tickets_data: state => state.event.tickets_data,
  }),

}

</script>

<style scoped>
#account {
  min-height: 60%;
  /*margin: 3%;*/
}
</style>
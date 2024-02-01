<template>
  <div>
    <div className="background">
      <div className="card flex justify-content-center">
        <div>Выберите количество билетов:</div>
        <Dropdown v-model="ticket_amount" :options="tickets_amount_data" filter optionLabel="code" class="w-full md:w-14rem"/>
      </div>
      <div className="card flex justify-content-center">
      <div>Введите паспортные данные:</div>
        <InputText type="text" v-model="passport" />
      </div>

    </div>

  </div>
</template>


<script>
import InputText from 'primevue/inputtext';
import Dropdown from 'primevue/dropdown';

export default {
  name: "ArgsBuyForm",
  components: {
    Dropdown,
    InputText
  },
  props: ['p_ticket_amount', 'p_passport'],
  emits: ['update:p_ticket_amount', 'update:p_passport'],
  data() {
    return {
      ticket_amount: null,
      tickets_amount_data: [{ msg: "1", code: "1" }, { msg: "2", code: "2" }],

      passport: ""
    }
  },
  watch: {
    ticket_amount(val) {
      this.$emit('update:p_ticket_amount', val);
    },
    passport(val) {
      this.$emit('update:p_passport', val);
    },
  },

}
</script>

<style scoped>
* {
  padding: 0% 0% 1% 1%;
}
</style>
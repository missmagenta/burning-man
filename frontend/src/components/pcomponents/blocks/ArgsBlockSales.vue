<template>
  <div>
    <div class="background justify-content-center" id="account">

    </div>
    <div class="card flex justify-content-center">
      <div>Установите количество билетов:</div>
      <InputText type="text" v-model="tickets_amount"/>
    </div>

  </div>
</template>

<script>
import InputText from 'primevue/inputtext';

export default {
  name: "ArgsBlockSales",
  components: {
    InputText
  },
  props: ['p_tickets_amount'],
  emits: ['update:p_tickets_amount'],
  data() {
    return {
      tickets_amount: "",
    }
  },
  watch: {
    tickets_amount(val) {
      this.$emit('update:p_tickets_amount', val);
    },
  },

}

</script>

<style scoped>
#account {
  min-height: 60%;
  margin: 3%;
}
</style>
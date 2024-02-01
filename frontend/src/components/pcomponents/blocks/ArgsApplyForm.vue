<template>
  <div>
    <div class="background">
      <div class="card flex justify-content-center">
        <div>Название арт объекта:</div>
        <InputText type="text" v-model="art_object" />
      </div>
      <div class="card flex justify-content-center">
        <div>Опишите ваш арт объект (размеры, назначение и тп):</div>
        <InputText type="text" v-model="description" />
      </div>
      <div class="card flex justify-content-start">
        <div>Опишите используемые вами материалы для крафта:</div>
        <InputText type="text" v-model="material" />
      </div>
      <div class="card flex justify-content-start">
        <div>Какое у вас есть световое оборудование для арт объекта, чтобы его было видно в ночи:</div>
        <InputText type="text" v-model="light" />
      </div>
      <div class="card flex justify-content-start">
        <div>Желаемая локация для него:</div>
        <InputText type="text" v-model="location" />
      </div>
      <div class="card flex justify-content-start">
        <div>Приблизительное время, требуемое для его воздвижения в пустыни (в часах) (напишите одно число):</div>
        <InputText type="text" v-model="time" />
      </div>
      <div class="card flex justify-content-start">
        <div>Ссылка на 3D модель арт объекта:</div>
        <InputText type="text" v-model="link" />
      </div>
    </div>

  </div>
</template>


<script>
import InputText from 'primevue/inputtext';
export default {
  name: "ArgsApplyForm",
  components: {
    InputText,
  },
  props: ['p_art_object', 'p_description', 'p_material', 'p_light', 'p_location', 'p_time', 'p_link'],
  emits: ['update:p_art_object', 'update:p_description', 'update:p_material', 'update:p_light',
    'update:p_location', 'update:p_time', 'update:p_link'],
  data() {
    return {
      art_object: "",
      description: "",
      material: "",
      light: "",
      location: "",
      time: "",
      link: ""
    }
  },
  watch: {
    art_object(val) {
      this.$emit('update:p_art_object', val);
    },
    description(val) {
      this.$emit('update:p_description', val);
    },
    material(val) {
      this.$emit('update:p_material', val);
    },
    light(val) {
      this.$emit('update:p_light', val);
    },
    location(val) {
      this.$emit('update:p_location', val)
    },
    time(val) {
      this.$emit('update:p_time', val);
    },
    link(val) {
      this.$emit('update:p_link', val)
    },
  },

}
</script>

<style scoped>

.background {
  align-content: center;
  text-align: left;
  margin-top: 40px;
  width: 50%;
}

.card {
  margin-top: 5px;
  width: 90%;
}

* {
  padding: 0% 0% 1% 1%;
}
</style>
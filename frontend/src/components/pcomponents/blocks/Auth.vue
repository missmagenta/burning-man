<template>
  <div class="background justify-content-center align-items-center" id="account">
    <div class="title">
      <h2 v-if="!new_acc">Вход в аккаунт</h2>
      <h2 v-if="new_acc">Создание аккаунта</h2>
    </div>
    <div>
      <div class="card flex justify-content-center">

        <div v-if="new_acc">Введите имя:</div>
        <InputText v-if="new_acc" type="text" v-model="name" />
        <div></div>

        <div v-if="new_acc">Введите фамилию:</div>
        <InputText v-if="new_acc" type="text" v-model="surname" />
      </div>

      <div class="card flex justify-content-center">
        <div>Логин:</div>
        <InputText type="text" v-model="login" />
        <div></div>
        <div>Пароль:</div>
        <InputText type="password" v-model="password" />
      </div>

      <div class="card flex justify-content-center">

        <div v-if="new_acc">Введите почту:</div>
        <InputText v-if="new_acc" type="text" v-model="email" />
        <div></div>
      </div>
      <div class="card flex justify-content-center">
        <div v-if="new_acc">Введите номер телефона (вместе с +):</div>
        <InputText v-if="new_acc" type="text" v-model="phoneNumber" />
      </div>

      <div class="card flex justify-content-center">
        <div v-if="new_acc">Выберите пол:</div>
        <Dropdown v-if="new_acc" v-model="gender" :options="gender_data" filter optionLabel="msg" class="w-full md:w-14rem" />
      </div>

      <div class="card flex justify-content-center">
        <div v-if="new_acc">Дата рождения:</div>
        <Calendar v-if="new_acc" v-model="birthday" dateFormat="yy-mm-dd" />
      </div>


    </div>

    <div class="card flex justify-content-center" id="div-inline">
      <ButtonsBlock v-if="!new_acc" v-bind:buttons="buttons" v-on:logIn="logIn" v-on:createUser="createUser" />
      <ButtonsBlock v-if="new_acc" v-bind:buttons="buttons_for_new_user" v-on:goBack="goBack" v-on:addUser="addUser" />
    </div>
  </div>
</template>

<script>
import ButtonsBlock from "@/components/pcomponents/blocks/ButtonBlock";
import Dropdown from 'primevue/dropdown';
import Calendar from 'primevue/calendar';
import InputText from 'primevue/inputtext';

export default {
  name: "AccountBlock",
  components: {
    Dropdown,
    ButtonsBlock,
    Calendar,
    InputText,

  },
  data() {
    return {
      buttons: [
        { msg: "Login", command: "logIn" },
        { msg: "Create", command: "createUser" }
      ],
      buttons_for_new_user: [
        { msg: "Back", command: "goBack" },
        { msg: "Create account", command: "addUser" }
      ],
      new_acc: false,
      name: "",
      surname: "",
      login: "",
      password: "",
      gender: null,
      birthday: "",
      email: "",
      phoneNumber: "",
      gender_data: [{ msg: "Male", code: "Male" }, { msg: "Female", code: "Female" }, {msg: "Non-binary", code: "Non_binary"}],
    }
  },

  methods: {
    createUser() {
      this.new_acc = true;
    },

    goBack() {
      this.new_acc = false;
    },

    addUser() {
      if (this.check_params_for_new_user()) {
        const name = this.name;
        const surname = this.surname;
        const birthDate = this.getStringDate(this.birthday);
        const personGender = this.gender.code;
        const email = this.email;
        const phoneNumber = this.phoneNumber;

        const username = this.login;
        const password = this.password;

        this.$store.dispatch('CREATE_NEW_ACCOUNT', { name, surname, email, phoneNumber, personGender, birthDate, username, password })
            .then(resp => {
                  console.log(resp);
                  this.$router.push({ name: 'user-page' });

                },
                err => (this.showError(err)));
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }

    },

    getStringDate(date) {
      let str_date = date.getFullYear() + "-";
      if (date.getMonth() < 9) {
        console.log(" i will kill yoi");
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

    logIn() {
      if (this.check_params_for_user()) {
        const username = this.login;
        const password = this.password;
        this.$store.dispatch('LOG_IN_ACCOUNT', { username, password })
            .then(resp => {
                  console.log(resp);
                  this.$router.push({ name: 'user-page' });
                },
                err => {
                  this.showError(err);
                });
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }

    },

    check_params_for_new_user() {
      return this.name != "" && this.surname != "" && this.birthday != "" && this.birthday != null &&
          this.gender != null && this.gender != undefined && this.login != "" && this.password != "" &&
          this.email != "" && this.phoneNumber != "";
    },


    check_params_for_user() {
      return this.login != "" && this.password != "";
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
  }
}
</script>

<style scoped>

.background {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 100px;
}

#account {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60%;
  /*margin: 3%;*/
  text-align: center;
  margin-left: 285px;
}

#account div {
  padding: 1%;
}

.title {
  padding: 5%;
  text-align: center;
}
</style>
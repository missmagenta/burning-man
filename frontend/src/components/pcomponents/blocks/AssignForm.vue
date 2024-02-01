<template>
  <div class="background justify-content-center">
    <div class="title">
      <h2 v-if="check == false" class="h2"> ASSIGN </h2>
    </div>

    <div class="card flex justify-content-center">
      <div v-if="check == false"> Выберите мероприятие:</div>
      <Dropdown v-if="check == false" v-model="bm_event" :options="events" filter optionLabel="name"
                class="w-full md:w-14rem"/>
      <div></div>
      <div v-if="check == false"> Выберите организатора:</div>
      <Dropdown v-if="check == false" v-model="org" :options="sortedOrgs" filter optionLabel="person.name"
                class="w-full md:w-14rem"/>
    </div>

    <div class="card flex justify-content-center" id="div-inline">
      <ButtonsBlock v-if="check == false" v-bind:buttons="buttons"
                    v-on:goBack="goBack"
                    v-on:assign="assign"
                    v-on:seeOme="seeOme"/>
      <ButtonsBlock v-if="check == true" v-bind:buttons="buttons_for_return" v-on:returnBack="returnBack"/>
    </div>
  </div>
  <div v-if="check == false" class="card-reg-person">
    <DataTable v-model:selection="selectedData" :value="orgs" paginator :rows="20"
               :rowsPerPageOptions="[5, 10, 20, 50]" selectionMode="single" dataKey="id"
               tableStyle="min-width: 10rem">
      <Column v-for="col of columns" :key="col.field" :field="col.field" sortable :header="col.header"
              style="width: 14%"></Column>
    </DataTable>
  </div>
  <div v-if="check == true" class="table-heading">
    Список назначенных организаторов:
  </div>
  <div v-if="check == true" class="card-reg-person">
    <DataTable :value="ome" paginator :rows="10" :rowsPerPageOptions="[5, 10, 20, 50]" tableStyle="width: 90rem">
      <Column v-for="col of columns_for_check" :key="col.field" :field="col.field" sortable :header="col.header"
              style="min-width: 60%"></Column>
    </DataTable>
  </div>


</template>

<script>
import ButtonsBlock from "@/components/pcomponents/blocks/ButtonBlock";
import Dropdown from 'primevue/dropdown';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import {mapState} from 'vuex';

export default {
  name: "AssignForm",
  components: {
    Dropdown,
    ButtonsBlock,
    DataTable,
    Column
  },
  data() {
    return {
      check: false,

      buttons: [
        {msg: "назад", command: "goBack"},
        {msg: "назначить", command: "assign"},
        {msg: "посмотреть назначенных организаторов", command: "seeOme"},

      ],
      buttons_for_return: [
        {msg: "вернуться назад", command: "returnBack"}
      ],

      columns: [
        {field: 'id', header: 'ID'},
        {field: 'role.organizerRole', header: 'organizerRole'},

        {field: 'person.name', header: 'name'},
        {field: 'person.surname', header: 'surname'},

        {field: 'person.birthDate', header: 'birth date'},

        {field: 'person.email', header: 'email'},
        {field: 'person.contactNumber', header: 'number'},
        {field: 'person.deathDate', header: 'death date'},
      ],

      columns_for_check: [
        {field: 'id', header: 'ID'},
        {field: 'event.name', header: 'Event'},
        {field: 'organizer.role.organizerRole', header: 'organizerRole'},
        {field: 'organizer.role.description', header: 'JD'},
        {field: 'organizer.person.name', header: 'name'},
        {field: 'organizer.person.surname', header: 'surname'},
        {field: 'organizer.person.email', header: 'email'},
        {field: 'organizer.person.contactNumber', header: 'number'},
        // {field: 'organizer.person.birthDate', header: 'birth date'},
        // {field: 'organizer.person.deathDate', header: 'death date'}
      ],
      selectedData: null,

      bm_event: null,
      org: null,

      orgs: [],
      events: [],

      ome: null
    }
  },
  methods: {
    goBack() {
      this.$router.push({name: 'main-org-page'});
    },
    assign() {
      if (this.check_params_for_assign()) {
        const eventId = this.bm_event.id;
        const organizerId = this.org.id;

        this.$store.dispatch('ASSIGN_ORGANIZER_TO_EVENT', {eventId, organizerId})
            .then(resp => {
                  console.log(resp);
                  this.check = true;
                  this.$store.dispatch('GET_OME')
                      .then(() => this.ome = this.ome_data);

                },
                err => (this.showError(err)));
      } else {
        this.showErrorFromFront("Необходимо заполнить все поля!");
      }

    },
    seeOme() {
      this.check = true;
    },
    returnBack() {
      this.check = false;
    },
    check_params_for_assign() {
      return this.bm_event != null && this.bm_event != undefined && this.org != null && this.org != undefined;
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
  computed: mapState({
    bm_event_data: state => state.event.future_events_data,
    org_data: state => state.event.alive_orgs_data,

    sortedOrgs() {
      return [...this.orgs].sort((a, b) => a.id - b.id);
    },

    ome_data: state => state.event.ome_data
  }),

  created() {
    this.$store.dispatch('GET_ALL_FUTURE_EVENTS')
        .then(() => {
              console.log("events");
              this.events = this.bm_event_data;
              console.log(this.events);
            },
            err => this.showError(err));
    this.$store.dispatch('GET_ALL_ALIVE_ORGS')
        .then(() => {
          this.orgs = this.org_data
          console.log("orgs");
          console.log(this.orgs);
          console.log(this.org_data);
        });
    this.$store.dispatch('GET_OME')
        .then(() => this.ome = this.ome_data);
  },

}
</script>

<style scoped>

</style>
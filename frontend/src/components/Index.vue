<template>

  <div class="main-background" id="main-div">
    <Header/>
    <div class="div-block">
      <div class="centered-content">
        <TextBlock v-if="info" v-on:click="start($event)" button_msg="Начать">
          <p>Burning Man Project</p>
        </TextBlock>
        <AuthBlock v-if="account"/>
        <img id="img" src="../assets/logobm.png" alt="Image"/>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<script>
import Header from "@/components/pcomponents/blocks/Header";
import TextBlock from "@/components/pcomponents/blocks/TextBlock";
import AuthBlock from "@/components/pcomponents/blocks/Auth";
import Footer from "@/components/pcomponents/blocks/Footer";

export default {
  components: {
    Footer,
    Header,
    TextBlock,
    AuthBlock,
  },
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Index",
  data() {
    return {
      account: false,
      info: true,
    }
  },
  beforeMount() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');

  },


  methods: {
    start() {
      this.account = true
      this.info = false
    },
  }
}
</script>

<style scoped>
.div-block {
  min-height: 60%;
  margin: 3%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.centered-content {
  text-align: center;
}

.div-block div {
  padding: 1%;
}

.centered-content p {
  white-space: nowrap;
  margin-top: 50px;
  font-size: larger;
  font-weight: bold;
  margin-bottom: 50px;
}

#img {
  object-fit: contain;
  object-position: center;
  width: 20%;
  height: 20%;
  display: block;
  margin-top: 10px;
}


</style>
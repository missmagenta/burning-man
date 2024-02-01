<template>
  <footer>
    <div><p>Курсовой проект по предмету <br/> "Информационные системы и базы данных"</p></div>
    <nav>
      <Link href="https://itmo.ru/" name="Университет ИТМО"/>
    </nav>
  </footer>
</template>

<script>
import Link from "@/components/pcomponents/interactiveElements/Link";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Footer",
  components: {Link}
}
</script>

<style scoped>
footer {
  height: 50px;
  font-size: 12px;
  display: table;
  padding: 1em 3% 1% 3%;
  background: #2d257a;
  color: #ffffff;
  width: 100%;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  alignment: left;
}

div p {
  color: #ffffff;
  font-size: 14px;
}

footer nav {
  display: table-cell;
  vertical-align: middle;
  text-align: right;
  color: #ffffff;
}

footer nav a {
  margin: 0 0 5% 5%;
  font-size: larger;
}

</style>
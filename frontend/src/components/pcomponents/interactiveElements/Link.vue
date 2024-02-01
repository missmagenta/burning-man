<template>
  <a :href=href>{{ name }}</a>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Link",
  props: ['href', 'name'],
}
</script>

<style scoped>
a {
  text-decoration: none
}

a:link {
  color: #e1d9d9;
}

a:visited {
  color: #ababab;
}

a:hover {
  color: #fdfdfd;
}

a:active {
  color: #bdb8b8;
}
</style>
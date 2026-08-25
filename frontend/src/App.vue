<script setup lang="ts">
import { ref } from 'vue'

const status = ref('MVP foundation ready')
</script>

<template>
  <main class="home-page">
    <h1>ProjectLearn AI</h1>
    <p>AI-powered project-based learning for developers.</p>
    <span>{{ status }}</span>
  </main>
</template>

<style scoped>
.home-page {
  max-width: 720px;
  margin: 0 auto;
  padding: 4rem 1.5rem;
  font-family: system-ui, sans-serif;
}

h1 {
  margin-bottom: 0.75rem;
}

p {
  color: #4b5563;
}

span {
  display: inline-block;
  margin-top: 1rem;
  padding: 0.5rem 0.75rem;
  border-radius: 0.375rem;
  background: #ecfdf5;
  color: #047857;
}
</style>

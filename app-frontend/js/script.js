const API_URL = "http://localhost:8080/api";

function getUsuarioActual() {
  return JSON.parse(localStorage.getItem("usuarioActual"));
}

function checkAuth() {
  const logged = localStorage.getItem("logged") === "true";
  if (!logged) window.location.href = "login.html";
}

function logout() {
  localStorage.removeItem("logged");
  localStorage.removeItem("usuarioActual");
  localStorage.removeItem("tareaActiva");
  window.location.href = "../index.html";
}

async function login() {
  const email    = document.getElementById("loginEmail").value.trim();
  const password = document.getElementById("loginPassword").value;

  try {
    const res = await fetch(`${API_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    if (!res.ok) {
      const errorEl = document.getElementById("errorLoginPassword");
      const inputEl = document.getElementById("loginPassword");
      errorEl.textContent = "Email o contraseña incorrectos";
      errorEl.classList.add("visible");
      inputEl.classList.add("is-invalid");
      inputEl.classList.remove("is-valid");
      return;
    }

    const usuario = await res.json();
    localStorage.setItem("logged", "true");
    localStorage.setItem("usuarioActual", JSON.stringify(usuario));
    window.location.href = "dashboard.html";

  } catch (e) {
    alert("No se pudo conectar con el servidor. Verifica que el backend esté corriendo.");
  }
}

async function register() {
  const nombre   = document.getElementById("regNombre").value.trim();
  const email    = document.getElementById("regEmail").value.trim();
  const password = document.getElementById("regPassword").value;

  try {
    const res = await fetch(`${API_URL}/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nombre, email, password })
    });

    if (res.status === 409) {
      const errorEl = document.getElementById("errorEmail");
      const inputEl = document.getElementById("regEmail");
      errorEl.textContent = "Ya existe una cuenta con ese email";
      errorEl.classList.add("visible");
      inputEl.classList.add("is-invalid");
      inputEl.classList.remove("is-valid");
      return;
    }

    const usuario = await res.json();
    localStorage.setItem("logged", "true");
    localStorage.setItem("usuarioActual", JSON.stringify(usuario));
    window.location.href = "dashboard.html";

  } catch (e) {
    alert("No se pudo conectar con el servidor. Verifica que el backend esté corriendo.");
  }
}

async function cargarTareas() {
  const usuario = getUsuarioActual();
  if (!usuario) return [];
  try {
    const res = await fetch(`${API_URL}/tareas/usuario/${usuario.id}`);
    return await res.json();
  } catch (e) {
    return [];
  }
}

async function agregarTarea() {
  const input       = document.getElementById("inputTarea");
  const inputTiempo = document.getElementById("inputTiempo");
  const texto       = input.value.trim();
  const tiempo      = parseInt(inputTiempo.value);
  const usuario     = getUsuarioActual();

  if (!texto) { alert("Escribe una tarea"); return; }

  try {
    const res = await fetch(`${API_URL}/tareas`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nombre: texto, tiempo, done: false, usuarioId: usuario.id })
    });

    if (res.ok) {
      input.value = "";
      inputTiempo.value = 25;
      const modalEl = document.getElementById("modalTarea");
      if (modalEl) {
        const modal = bootstrap.Modal.getInstance(modalEl);
        if (modal) modal.hide();
      }
      if (typeof renderTareas === "function") renderTareas();
    }
  } catch (e) {
    alert("No se pudo conectar con el servidor.");
  }
}

async function eliminarTarea(id) {
  try {
    const res = await fetch(`${API_URL}/tareas/${id}`, { method: "DELETE" });
    if (res.ok && typeof renderTareas === "function") renderTareas();
  } catch (e) {
    alert("No se pudo conectar con el servidor.");
  }
}

async function toggleTareaAPI(id, done, nombre, tiempo) {
  const usuario = getUsuarioActual();
  try {
    await fetch(`${API_URL}/tareas/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, nombre, tiempo, done, usuarioId: usuario.id })
    });
  } catch (e) {
    console.error("Error al actualizar tarea");
  }
}

async function cambiarTiempo(id, nuevoTiempo, nombre, done) {
  const usuario = getUsuarioActual();
  try {
    await fetch(`${API_URL}/tareas/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, nombre, tiempo: parseInt(nuevoTiempo), done, usuarioId: usuario.id })
    });
  } catch (e) {
    console.error("Error al actualizar tiempo");
  }
}

function iniciarTarea(id, tiempo, nombre) {
  localStorage.setItem("tareaActiva", JSON.stringify({ id, tiempo, nombre }));
  window.location.href = "timer.html";
}

function setupFeatureCards() {
  const cards = document.querySelectorAll(".feature-card");
  cards.forEach(card => {
    card.addEventListener("click", () => {
      const isLogged = localStorage.getItem("logged") === "true";
      window.location.href = isLogged ? "./pages/dashboard.html" : "./pages/demo.html";
    });
  });
}

document.addEventListener("DOMContentLoaded", () => {
  setupFeatureCards();
});
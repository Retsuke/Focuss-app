function agregarTarea() {
  const input = document.getElementById("inputTarea");
  const inputTiempo = document.getElementById("inputTiempo");

  const texto = input.value.trim();
  const tiempo = inputTiempo.value;

  if (texto === "") {
    alert("Escribe una tarea");
    return;
  }

  let tareas = JSON.parse(localStorage.getItem("tareas")) || [];

  tareas.push({
    nombre: texto,
    tiempo: tiempo
  });

  localStorage.setItem("tareas", JSON.stringify(tareas));

  input.value = "";
  inputTiempo.value = 25;

  const modalEl = document.getElementById('modalTarea');
  const modal = bootstrap.Modal.getInstance(modalEl);

  if (modal) modal.hide();

  mostrarTareas();
}

function mostrarTareas() {
  const lista = document.getElementById("listaTareas");
  if (!lista) return;

  lista.innerHTML = "";

  let tareas = JSON.parse(localStorage.getItem("tareas")) || [];

  tareas.forEach((t, index) => {
    const li = document.createElement("li");
    li.className = "list-group-item d-flex justify-content-between align-items-center";

    li.innerHTML = `
      <span>
        <input type="checkbox" class="form-check-input me-2">
        ${t.nombre} - ${t.tiempo} min
      </span>

      <button class="btn btn-danger btn-sm" onclick="eliminarTarea(${index})">
        <i class="bi bi-trash"></i>
      </button>
    `;

    lista.appendChild(li);
  });
}

function mostrarPomodoro() {
  const lista = document.getElementById("listaPomodoro");
  if (!lista) return;

  lista.innerHTML = "";

  let tareas = JSON.parse(localStorage.getItem("tareas")) || [];

  tareas.forEach((t, index) => {
    const li = document.createElement("li");
    li.className = "list-group-item d-flex justify-content-between align-items-center";

    li.innerHTML = `
      <span>${t.nombre}</span>

      <input type="number"
        value="${t.tiempo}"
        style="width:70px;"
        onchange="cambiarTiempo(${index}, this.value)"
        class="form-control form-control-sm">
    `;

    lista.appendChild(li);
  });
}

function cambiarTiempo(index, nuevoTiempo) {
  let tareas = JSON.parse(localStorage.getItem("tareas")) || [];

  tareas[index].tiempo = nuevoTiempo;

  localStorage.setItem("tareas", JSON.stringify(tareas));
}

function eliminarTarea(index) {
  let tareas = JSON.parse(localStorage.getItem("tareas")) || [];

  tareas.splice(index, 1);

  localStorage.setItem("tareas", JSON.stringify(tareas));

  mostrarTareas();
}

function login() {
  localStorage.setItem("logged", "true");
  window.location.href = "dashboard.html";
}

function register() {
  localStorage.setItem("logged", "true");
  window.location.href = "dashboard.html";
}

function logout() {
  localStorage.removeItem("logged");
  window.location.href = "../index.html";
}

function checkAuth() {
  const isLogged = localStorage.getItem("logged");

  const dashboardLink = document.getElementById("dashboardLink");

  if (dashboardLink && !isLogged) {
    dashboardLink.style.display = "none";
  }
}

function setupFeatureCards() {
  const cards = document.querySelectorAll(".feature-card");

  cards.forEach(card => {
    card.addEventListener("click", () => {
      const isLogged = localStorage.getItem("logged") === "true";

      if (isLogged) {
        window.location.href = "./pages/dashboard.html";
      } else {
        window.location.href = "./pages/demo.html";
      }
    });
  });
}

document.addEventListener("DOMContentLoaded", () => {
  mostrarTareas();
  mostrarPomodoro();
  setupFeatureCards();
});
const btnToggleSidebar = document.getElementById('btnToggleSidebar');
const sidebar = document.getElementById('sidebar');

btnToggleSidebar.addEventListener('click', () => {
    sidebar.classList.toggle('collapsed');
});

function abrirModalEditar(button) {
    const modal = document.getElementById('modalAgregarUsuario');
    const form = modal.querySelector('form');
    const btnSubmit = form.querySelector('button[type="submit"]');

    // Cambiar action para actualizar
    form.action = '/admin/dashboard/usuarios/actualizar';

    // Cambiar texto del botón
    btnSubmit.textContent = 'Actualizar';

    // Rellenar campos con datos del botón
    form.querySelector('input[name="id"]').value = button.getAttribute('data-id');
    form.querySelector('input[name="nombre"]').value = button.getAttribute('data-nombre');
    form.querySelector('input[name="apellido"]').value = button.getAttribute('data-apellido');
    form.querySelector('input[name="correo"]').value = button.getAttribute('data-correo');
    form.querySelector('input[name="direccion"]').value = button.getAttribute('data-direccion');
    form.querySelector('select[name="rol"]').value = button.getAttribute('data-rol');
    form.querySelector('input[name="telefono"]').value = button.getAttribute('data-telefono');
    form.querySelector('input[name="clave"]').value = button.getAttribute('data-clave');

    // Cambiar título del modal
    modal.querySelector('.modal-title').textContent = 'Editar Usuario';
}


function abrirModalAgregarUsuario() {
    const modal = document.getElementById('modalAgregarUsuario');
    const form = modal.querySelector('form');
    const btnSubmit = form.querySelector('button[type="submit"]');

    // Limpiar valores del formulario
    form.reset();

    // Cambiar action para registrar (agregar)
    form.action = '/admin/dashboard/usuarios/registrar';

    // Cambiar texto del botón
    btnSubmit.textContent = 'Guardar';
    btnSubmit.classList.remove('btn-warning');
    btnSubmit.classList.add('btn-primary');

    // Cambiar título del modal
    modal.querySelector('.modal-title').textContent = 'Agregar Usuario';

    // Ocultar botón eliminar si existe
    const btnEliminar = modal.querySelector('#btnEliminar');
    if (btnEliminar) {
        btnEliminar.classList.add('d-none');
    }
}

function abrirModalAgregarProducto() {
    const modal = document.getElementById('modalProducto');
    const form = modal.querySelector('form');
    const btnSubmit = form.querySelector('button[type="submit"]');

    form.reset();

    form.action = '/admin/dashboard/productos/registrar';

    btnSubmit.textContent = 'Guardar';
    btnSubmit.classList.remove('btn-warning');
    btnSubmit.classList.add('btn-primary');

    modal.querySelector('.modal-title').textContent = 'Agregar Producto';
}

function abrirModalEditarProducto(button) {
    const modal = document.getElementById('modalProducto');
    const form = modal.querySelector('form');
    const btnSubmit = form.querySelector('button[type="submit"]');

    // Cambiar action para actualizar
    form.action = '/admin/dashboard/productos/actualizar';

    // Cambiar texto del botón
    btnSubmit.textContent = 'Actualizar';

    // Rellenar campos con datos del botón
    form.querySelector('input[name="id"]').value = button.getAttribute('data-id');
    form.querySelector('select[name="categoria"]').value = button.getAttribute('data-categoria-id'); // <- Aquí selecciona la categoría por ID
    form.querySelector('input[name="descripcion"]').value = button.getAttribute('data-descripcion');
    form.querySelector('input[name="nombre"]').value = button.getAttribute('data-nombre');
    form.querySelector('input[name="precio"]').value = button.getAttribute('data-precio');
    form.querySelector('input[name="stock"]').value = button.getAttribute('data-stock');

    // Cambiar título del modal (opcional)
    modal.querySelector('.modal-title').textContent = 'Editar Producto';
}

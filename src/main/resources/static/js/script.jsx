function togglePassword() {
    const passwordInput = document.getElementById("password");
    const toggleBtn = document.getElementById("toggleBtn");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        toggleBtn.textContent = "Ocultar";
    } else {
        passwordInput.type = "password";
        toggleBtn.textContent = "Ver";
    }
}
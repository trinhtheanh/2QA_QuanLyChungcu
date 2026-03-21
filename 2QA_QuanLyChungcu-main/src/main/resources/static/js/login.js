document.addEventListener("DOMContentLoaded", function () {
    // Toggle hiển thị/ẩn mật khẩu cho nhiều input
    document.querySelectorAll(".toggle-password").forEach(function (toggleBtn) {
        const targetSelector = toggleBtn.getAttribute("data-target");
        const input = document.querySelector(targetSelector);

        if (!input) return;

        toggleBtn.addEventListener("click", function () {
            if (input.type === "password") {
                input.type = "text";
                toggleBtn.textContent = "Ẩn";
            } else {
                input.type = "password";
                toggleBtn.textContent = "Hiện";
            }
        });
    });

    // Chuyển tab Đăng nhập / Đăng ký
    const tabButtons = document.querySelectorAll(".tab-button");
    const panels = document.querySelectorAll(".form-panel");

    tabButtons.forEach(function (btn) {
        btn.addEventListener("click", function () {
            const target = btn.getAttribute("data-target");

            // Active button
            tabButtons.forEach(b => b.classList.remove("active"));
            btn.classList.add("active");

            // Active panel
            panels.forEach(p => p.classList.remove("active"));
            const panel = document.querySelector(target);
            if (panel) {
                panel.classList.add("active");
            }
        });
    });
});
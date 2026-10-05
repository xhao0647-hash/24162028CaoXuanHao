<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title"/> | BaiTap JPA</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <sitemesh:write property="head"/>
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="<c:url value='/home'/>"><i class="bi bi-database-fill"></i> BaiTap JPA</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="<c:url value='/home'/>">Trang chủ</a></li>
                <c:if test="${not empty sessionScope.account}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/categories'/>">Quản lý Category</a></li>
                </c:if>
            </ul>
            <ul class="navbar-nav align-items-lg-center">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle d-flex align-items-center gap-2" href="#" data-bs-toggle="dropdown">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.account.images}">
                                        <img src="<c:url value='/image'><c:param name='fname' value='${sessionScope.account.images}'/></c:url>"
                                             class="rounded-circle border border-light" width="30" height="30" style="object-fit:cover" alt="avatar">
                                    </c:when>
                                    <c:otherwise><i class="bi bi-person-circle fs-5"></i></c:otherwise>
                                </c:choose>
                                <c:out value="${empty sessionScope.account.fullname ? sessionScope.account.username : sessionScope.account.fullname}"/>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="<c:url value='/profile'/>"><i class="bi bi-person"></i> Hồ sơ cá nhân</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="<c:url value='/logout'/>"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a class="nav-link" href="<c:url value='/login'/>">Đăng nhập</a></li>
                        <li class="nav-item"><a class="nav-link" href="<c:url value='/register'/>">Đăng ký</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<main class="container py-4 flex-grow-1">
    <sitemesh:write property="body"/>
</main>

<footer class="bg-dark text-light text-center py-3 mt-auto">
    <small>&copy; 2026 BaiTap02 JPA - Sitemesh 3 + Bootstrap 5</small>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Validation phía client cho mọi form có class "needs-validation"
    document.querySelectorAll('form.needs-validation').forEach(function (form) {
        form.addEventListener('submit', function (e) {
            if (!form.checkValidity()) {
                e.preventDefault();
                e.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });

    // Kiểm tra file ảnh (loại + dung lượng) và xem trước: <input type="file" data-max-mb="5" data-preview="#id">
    document.querySelectorAll('input[type=file][data-max-mb]').forEach(function (input) {
        input.addEventListener('change', function () {
            var f = input.files[0];
            input.setCustomValidity('');
            if (f) {
                if (!/^image\/(jpeg|png|gif|webp)$/.test(f.type)) {
                    input.setCustomValidity('Chỉ chấp nhận ảnh jpg, png, gif, webp');
                } else if (f.size > input.dataset.maxMb * 1024 * 1024) {
                    input.setCustomValidity('Ảnh vượt quá ' + input.dataset.maxMb + 'MB');
                }
                var fb = input.parentElement.querySelector('.invalid-feedback');
                if (fb && input.validationMessage) fb.textContent = input.validationMessage;
                var preview = input.dataset.preview && document.querySelector(input.dataset.preview);
                if (preview && input.validity.valid) { preview.src = URL.createObjectURL(f); preview.classList.remove('d-none'); }
            }
            input.classList.toggle('is-invalid', !input.checkValidity());
        });
    });
</script>
</body>
</html>

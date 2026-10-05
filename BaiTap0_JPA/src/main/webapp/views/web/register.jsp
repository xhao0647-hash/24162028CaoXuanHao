<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h3 class="card-title text-center mb-4">Đăng ký tài khoản</h3>

                <form action="<c:url value='/register'/>" method="post" class="needs-validation" novalidate>
                    <div class="mb-3">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control ${not empty errors.username ? 'is-invalid' : ''}" id="username" name="username"
                               value="<c:out value='${form.username}'/>" required pattern="[A-Za-z0-9_]{4,30}">
                        <div class="invalid-feedback"><c:out value="${empty errors.username ? 'Tên đăng nhập 4-30 ký tự, chỉ gồm chữ, số và dấu _' : errors.username}"/></div>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control ${not empty errors.email ? 'is-invalid' : ''}" id="email" name="email"
                               value="<c:out value='${form.email}'/>" required maxlength="100">
                        <div class="invalid-feedback"><c:out value="${empty errors.email ? 'Email không hợp lệ' : errors.email}"/></div>
                    </div>
                    <div class="mb-3">
                        <label for="fullname" class="form-label">Họ và tên</label>
                        <input type="text" class="form-control ${not empty errors.fullname ? 'is-invalid' : ''}" id="fullname" name="fullname"
                               value="<c:out value='${form.fullname}'/>" required minlength="2" maxlength="100">
                        <div class="invalid-feedback"><c:out value="${empty errors.fullname ? 'Họ tên từ 2 đến 100 ký tự' : errors.fullname}"/></div>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input type="tel" class="form-control ${not empty errors.phone ? 'is-invalid' : ''}" id="phone" name="phone"
                               value="<c:out value='${form.phone}'/>" required pattern="(0|\+84)[0-9]{9}">
                        <div class="invalid-feedback"><c:out value="${empty errors.phone ? 'Số điện thoại không hợp lệ (VD: 0912345678)' : errors.phone}"/></div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="password" class="form-label">Mật khẩu</label>
                            <input type="password" class="form-control ${not empty errors.password ? 'is-invalid' : ''}" id="password" name="password"
                                   required minlength="6" maxlength="50">
                            <div class="invalid-feedback"><c:out value="${empty errors.password ? 'Mật khẩu tối thiểu 6 ký tự' : errors.password}"/></div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="confirm" class="form-label">Nhập lại mật khẩu</label>
                            <input type="password" class="form-control ${not empty errors.confirm ? 'is-invalid' : ''}" id="confirm" name="confirm" required>
                            <div class="invalid-feedback"><c:out value="${empty errors.confirm ? 'Mật khẩu nhập lại không khớp' : errors.confirm}"/></div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
                </form>
                <p class="text-center mt-3 mb-0">Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a></p>
            </div>
        </div>
    </div>
</div>
<script>
    (function () {
        var pw = document.getElementById('password'), cf = document.getElementById('confirm');
        function check() { cf.setCustomValidity(cf.value && cf.value !== pw.value ? 'Mật khẩu nhập lại không khớp' : ''); }
        pw.addEventListener('input', check);
        cf.addEventListener('input', check);
    })();
</script>
</body>
</html>

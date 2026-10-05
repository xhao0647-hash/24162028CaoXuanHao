<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h3 class="card-title text-center mb-4">Đăng nhập</h3>

                <c:if test="${param.registered == '1'}">
                    <div class="alert alert-success">Đăng ký thành công, hãy đăng nhập.</div>
                </c:if>
                <c:if test="${not empty errors.global}">
                    <div class="alert alert-danger"><c:out value="${errors.global}"/></div>
                </c:if>

                <form action="<c:url value='/login'/>" method="post" class="needs-validation" novalidate>
                    <div class="mb-3">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control ${not empty errors.username ? 'is-invalid' : ''}"
                               id="username" name="username" value="<c:out value='${form.username}'/>" required>
                        <div class="invalid-feedback"><c:out value="${empty errors.username ? 'Vui lòng nhập tên đăng nhập' : errors.username}"/></div>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control ${not empty errors.password ? 'is-invalid' : ''}"
                               id="password" name="password" required>
                        <div class="invalid-feedback"><c:out value="${empty errors.password ? 'Vui lòng nhập mật khẩu' : errors.password}"/></div>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                </form>
                <p class="text-center mt-3 mb-0">Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký</a></p>
            </div>
        </div>
    </div>
</div>
</body>
</html>

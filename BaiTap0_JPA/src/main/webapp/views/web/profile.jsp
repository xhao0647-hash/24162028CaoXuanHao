<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hồ sơ cá nhân</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h3 class="card-title text-center mb-4">Hồ sơ cá nhân</h3>

                <c:if test="${param.success == '1'}">
                    <div class="alert alert-success">Cập nhật hồ sơ thành công.</div>
                </c:if>

                <form action="<c:url value='/profile/update'/>" method="post" enctype="multipart/form-data"
                      class="needs-validation" novalidate>

                    <div class="text-center mb-3">
                        <c:url var="avatarUrl" value="/image"><c:param name="fname" value="${user.images}"/></c:url>
                        <img id="avatarPreview" src="${avatarUrl}" alt="Avatar" width="150" height="150"
                             class="rounded-circle border shadow-sm" style="object-fit:cover">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" value="<c:out value='${user.username}'/>" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="text" class="form-control" value="<c:out value='${user.email}'/>" disabled>
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
                    <div class="mb-3">
                        <label for="images1" class="form-label">Ảnh đại diện (jpg, png, gif, webp - tối đa 5MB)</label>
                        <input type="file" class="form-control ${not empty errors.images1 ? 'is-invalid' : ''}" id="images1" name="images1"
                               accept="image/jpeg,image/png,image/gif,image/webp" data-max-mb="5" data-preview="#avatarPreview">
                        <div class="invalid-feedback"><c:out value="${errors.images1}"/></div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100"><i class="bi bi-save"></i> Cập nhật</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

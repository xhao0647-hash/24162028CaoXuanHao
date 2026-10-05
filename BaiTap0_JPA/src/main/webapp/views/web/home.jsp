<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ</title>
</head>
<body>
<div class="p-5 mb-4 bg-white rounded-3 shadow-sm">
    <h1 class="display-6 fw-bold">Bài tập JPA + Sitemesh 3</h1>
    <c:choose>
        <c:when test="${not empty sessionScope.account}">
            <p class="lead">Xin chào, <strong><c:out value="${empty sessionScope.account.fullname ? sessionScope.account.username : sessionScope.account.fullname}"/></strong>!</p>
            <a class="btn btn-primary me-2" href="<c:url value='/admin/categories'/>"><i class="bi bi-list-ul"></i> Quản lý Category</a>
            <a class="btn btn-outline-primary" href="<c:url value='/profile'/>"><i class="bi bi-person"></i> Hồ sơ cá nhân</a>
        </c:when>
        <c:otherwise>
            <p class="lead">Vui lòng đăng nhập để sử dụng các chức năng quản lý.</p>
            <a class="btn btn-primary me-2" href="<c:url value='/login'/>">Đăng nhập</a>
            <a class="btn btn-outline-primary" href="<c:url value='/register'/>">Đăng ký</a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>

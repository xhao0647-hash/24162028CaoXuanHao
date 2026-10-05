<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách Category</title>
</head>
<body>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h2 class="mb-0">Quản lý Category</h2>
    <a class="btn btn-primary" href="<c:url value='/admin/category/add'/>"><i class="bi bi-plus-lg"></i> Thêm Category</a>
</div>

<div class="card shadow-sm">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
            <tr>
                <th style="width:60px">STT</th>
                <th>Hình ảnh</th>
                <th>Tên Category</th>
                <th>Trạng thái</th>
                <th style="width:160px">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listcate}" var="cate" varStatus="stt">
                <c:choose>
                    <c:when test="${fn:startsWith(cate.images, 'http')}">
                        <c:set var="imgUrl" value="${cate.images}"/>
                    </c:when>
                    <c:otherwise>
                        <c:url var="imgUrl" value="/image"><c:param name="fname" value="${cate.images}"/></c:url>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td>${stt.index + 1}</td>
                    <td><img src="${imgUrl}" alt="image" width="100" height="75" class="rounded border" style="object-fit:cover"></td>
                    <td><c:out value="${cate.categoryname}"/></td>
                    <td>
                        <c:if test="${cate.status == 1}"><span class="badge text-bg-success">Hoạt động</span></c:if>
                        <c:if test="${cate.status != 1}"><span class="badge text-bg-secondary">Khóa</span></c:if>
                    </td>
                    <td>
                        <a class="btn btn-sm btn-outline-primary" href="<c:url value='/admin/category/edit'><c:param name='id' value='${cate.categoryid}'/></c:url>"><i class="bi bi-pencil"></i> Sửa</a>
                        <a class="btn btn-sm btn-outline-danger" href="<c:url value='/admin/category/delete'><c:param name='id' value='${cate.categoryid}'/></c:url>"
                           onclick="return confirm('Bạn chắc chắn muốn xóa category này?');"><i class="bi bi-trash"></i> Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty listcate}">
                <tr><td colspan="5" class="text-center text-muted py-4">Chưa có category nào.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

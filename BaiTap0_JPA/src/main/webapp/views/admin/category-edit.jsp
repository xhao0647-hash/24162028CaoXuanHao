<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cập nhật Category</title>
</head>
<body>
<c:choose>
    <c:when test="${fn:startsWith(cate.images, 'http')}">
        <c:set var="imgUrl" value="${cate.images}"/>
    </c:when>
    <c:otherwise>
        <c:url var="imgUrl" value="/image"><c:param name="fname" value="${cate.images}"/></c:url>
    </c:otherwise>
</c:choose>
<div class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h3 class="card-title text-center mb-4">Chỉnh sửa Category</h3>

                <form action="<c:url value='/admin/category/update'/>" method="post" enctype="multipart/form-data"
                      class="needs-validation" novalidate>
                    <input type="hidden" name="categoryid" value="${cate.categoryid}">

                    <div class="mb-3">
                        <label for="categoryname" class="form-label">Tên Category</label>
                        <input type="text" class="form-control ${not empty errors.categoryname ? 'is-invalid' : ''}" id="categoryname"
                               name="categoryname" value="<c:out value='${form.categoryname}'/>" required minlength="2" maxlength="100">
                        <div class="invalid-feedback"><c:out value="${empty errors.categoryname ? 'Tên category từ 2 đến 100 ký tự' : errors.categoryname}"/></div>
                    </div>
                    <div class="mb-3">
                        <label for="images" class="form-label">Link ảnh (tùy chọn)</label>
                        <input type="url" class="form-control ${not empty errors.images ? 'is-invalid' : ''}" id="images" name="images"
                               value="<c:out value='${form.images}'/>" maxlength="255" placeholder="https://...">
                        <div class="invalid-feedback"><c:out value="${empty errors.images ? 'Link ảnh phải bắt đầu bằng http:// hoặc https://' : errors.images}"/></div>
                    </div>
                    <div class="mb-3">
                        <label for="images1" class="form-label">Upload ảnh mới (chọn nếu muốn đổi ảnh, tối đa 5MB)</label>
                        <input type="file" class="form-control ${not empty errors.images1 ? 'is-invalid' : ''}" id="images1" name="images1"
                               accept="image/jpeg,image/png,image/gif,image/webp" data-max-mb="5" data-preview="#preview">
                        <div class="invalid-feedback"><c:out value="${errors.images1}"/></div>
                        <img id="preview" class="mt-2 rounded border" alt="Ảnh hiện tại" width="200" style="object-fit:cover" src="${imgUrl}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label d-block">Trạng thái</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="ston" value="1" ${form.status == '1' ? 'checked' : ''}>
                            <label class="form-check-label" for="ston">Hoạt động</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="stoff" value="0" ${form.status != '1' ? 'checked' : ''}>
                            <label class="form-check-label" for="stoff">Khóa</label>
                        </div>
                        <c:if test="${not empty errors.status}"><div class="text-danger small"><c:out value="${errors.status}"/></div></c:if>
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-save"></i> Cập nhật</button>
                        <a class="btn btn-outline-secondary" href="<c:url value='/admin/categories'/>">Quay lại</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

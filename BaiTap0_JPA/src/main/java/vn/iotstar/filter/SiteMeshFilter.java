package vn.iotstar.filter;

import jakarta.servlet.annotation.WebFilter;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * Cấu hình Sitemesh 3: 01 decorator Bootstrap dùng chung cho toàn bộ trang.
 */
@WebFilter("/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/WEB-INF/decorators/main.jsp")
               .addExcludedPath("/image")
               .addExcludedPath("/assets/*");
    }
}

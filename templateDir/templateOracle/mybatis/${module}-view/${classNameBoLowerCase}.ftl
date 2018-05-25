<#macro mapperEl_include value>${r'<#include "'}${value}${r'">'}</#macro>
<#assign classNameAllLower = table.classNameBo?lower_case>

<!DOCTYPE html>
<html lang="en">
<head>
<@mapperEl_include "head.html"/>
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<@mapperEl_include "nav_bar.ftl"/>
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <!-- #section:basics/sidebar -->
<@mapperEl_include "sidebar.ftl"/>

    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <!-- #section:basics/content.breadcrumbs -->
    <@mapperEl_include "breadcrumbs.ftl"/>

        <!-- /section:basics/content.breadcrumbs -->

        <@mapperEl_include "/module/custome/${classNameAllLower}_page_grid.ftl"/>

    </div><!-- /.main-content -->

<@mapperEl_include "footer.html"/>
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
<@mapperEl_include "/module/custome/script/${classNameAllLower}_biz_script.ftl"/>
</body>
</html>

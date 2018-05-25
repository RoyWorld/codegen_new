<!-- 左菜单 -->
<ul class="nav nav-list">
    <!-- dyn item start -->
<#assign pr = null />
<#list resources as resource>
    <#assign pr = "pr" />
    <#if parentTitle= resource.resource.name>
    <li class="hsub open active">
    <#else>
    <li class="hsub">
    </#if>
    <a href="#" class="dropdown-toggle">
        <i class="menu-icon fa fa-list"></i>
        <span class="menu-text"> ${resource.resource.name} </span>

        <b class="arrow fa fa-angle-down"></b>
    </a>

    <b class="arrow"></b>

    <#if resource.list?exists>
        <ul class="submenu">
            <#list resource.list  as subresources>
                <#if mainObj= subresources.resource.bizModelName >

                <li class="active">

                <#else>
                <li class="">
                </#if>
                <a href="${subresources.resource.url}">
                    <i class="menu-icon fa fa-caret-right"></i>
                ${subresources.resource.name}
                </a>

                <b class="arrow"></b>
            </li>

            </#list>
        </ul>
    </#if>
</li>

</#list>
    <!-- dyn item end -->
</ul><!-- /.nav-list -->
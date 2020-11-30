package com.apeter0.store.category.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class CategoryApiRoutes {

    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/categories";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";

    public static final String ROOT = BaseApiRoutes.V1 + "/categories";
    public static final String BY_ID = ROOT + "/{id}";
}

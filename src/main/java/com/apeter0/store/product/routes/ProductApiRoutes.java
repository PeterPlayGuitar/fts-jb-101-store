package com.apeter0.store.product.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class ProductApiRoutes {

    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/products";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";

    public static final String ROOT = BaseApiRoutes.V1 + "/products";
    public static final String BY_ID = ROOT + "/{id}";
}

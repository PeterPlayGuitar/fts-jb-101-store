package com.apeter0.store.order.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class OrderApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/orders";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/orders";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";
}

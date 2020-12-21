package com.apeter0.store.cart.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class CartApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/carts";
    public static final String ADD_PRODUCT = ROOT + "/{productId}";
    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/carts";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";
}

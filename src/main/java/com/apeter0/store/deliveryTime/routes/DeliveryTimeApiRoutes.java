package com.apeter0.store.deliveryTime.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class DeliveryTimeApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/delivery_times";
    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/delivery_times";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";
}

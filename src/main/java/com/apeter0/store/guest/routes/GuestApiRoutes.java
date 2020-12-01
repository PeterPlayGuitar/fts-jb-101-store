package com.apeter0.store.guest.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class GuestApiRoutes {
    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/guests";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";

    public static final String ROOT = BaseApiRoutes.V1 + "/guests";
}

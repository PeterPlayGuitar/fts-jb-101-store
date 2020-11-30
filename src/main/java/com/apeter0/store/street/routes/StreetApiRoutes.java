package com.apeter0.store.street.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class StreetApiRoutes {
    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/streets";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";

    public static final String ROOT = BaseApiRoutes.V1 + "/streets";
    public static final String BY_ID = ROOT + "/{id}";
}

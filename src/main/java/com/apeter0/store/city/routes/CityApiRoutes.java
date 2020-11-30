package com.apeter0.store.city.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class CityApiRoutes {
    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/cities";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";
}

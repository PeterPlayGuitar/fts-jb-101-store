package com.apeter0.store.photo.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class PhotoApiRoutes {
    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/photos";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";

    public static final String ROOT = BaseApiRoutes.V1 + "/photos";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String DOWNLOAD = "/photos/{id}";
}

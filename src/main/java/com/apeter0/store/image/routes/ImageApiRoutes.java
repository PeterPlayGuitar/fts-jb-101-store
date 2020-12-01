package com.apeter0.store.image.routes;

import com.apeter0.store.base.routes.BaseApiRoutes;

public class ImageApiRoutes {
    public static final String ADMIN_ROOT = BaseApiRoutes.V1 + "/admin/images";
    public static final String ADMIN_BY_ID = ADMIN_ROOT + "/{id}";

    public static final String ROOT = BaseApiRoutes.V1 + "/images";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String DOWNLOAD = "/images/{id}";
}

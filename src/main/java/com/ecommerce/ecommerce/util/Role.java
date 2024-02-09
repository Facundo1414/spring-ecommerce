package com.ecommerce.ecommerce.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    USER(Arrays.asList(Permission.SAVE_ONE_PRODUCT)),

    ADMIN(Arrays.asList(Permission.READ_ALL_PRODUCTS, Permission.SAVE_ONE_PRODUCT));

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}

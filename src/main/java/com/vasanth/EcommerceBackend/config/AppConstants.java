package com.vasanth.EcommerceBackend.config;

public class AppConstants {

    public static final String SORT_DIR = "asc";
    public static final String SORT_PRODUCTS_BY = "productId";
    public static final String SORT_CATEGORY_BY = "categoryId";
    public static final String SORT_ORDER_BY = "orderId";
    public static final String SORT_USER_BY = "userId";
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "2";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String[] PUBLIC_URLS = {"/api/login/**","/api/register/**"};
    public static final String[] ADMIN_URLS = {"/api/admin/**"};
    public static final String[] USER_URLS = {"/api/public/**"};
    public static final long JWT_VALIDITY = 1000 * 60 * 30;


}

package com.su.ac.th.project.grader.constant;

public class HttpConstant {

    public static class Status {
        public static final int SUCCESS = 200;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int INTERNAL_SERVER_ERROR = 500;
    }

    public static class Message {
        public static final String SUCCESS = "OK";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String FORBIDDEN = "Forbidden";
        public static final String NOT_FOUND = "Not Found";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }



}

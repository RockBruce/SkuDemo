package cn.edsmall.network.errorhandler;

import retrofit2.HttpException;

public class ExceptionHandle {
    public static Throwable handleException(Throwable throwable) {
        HttpException httpException= (HttpException) throwable;
        return throwable;
    }
}

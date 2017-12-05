package com.hy.mvp.http.subscribers;

/**
 * 作者：hanyin on 2017/8/9 13:25
 * 邮箱：hanyinit@163.com
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}

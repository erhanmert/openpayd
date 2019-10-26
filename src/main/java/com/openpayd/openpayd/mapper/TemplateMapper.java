package com.openpayd.openpayd.mapper;

public interface TemplateMapper<T, X> {
    X toDto(T t);

    T toEntity(X x);
}

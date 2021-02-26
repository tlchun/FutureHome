package com.goldze.base.lib.sdk;


import java.io.Serializable;

public class ArgsBean<T> implements Serializable {
    private Class<T> clazz;
    private Class<?>[] argsTypes;
    private Object[] args;
    private Object hBaseInterface;

    public ArgsBean() {
    }

    public ArgsBean(Object hBaseInterface) {
        this.hBaseInterface = hBaseInterface;
    }

    public ArgsBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ArgsBean clasz(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ArgsBean args(Object... initargs) {
        args = initargs;
        return this;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class<?>[] getArgsTypes() {
        return argsTypes;
    }

    public ArgsBean argsTypes(Class<?>... argsTypes) {
        this.argsTypes = argsTypes;
        return this;
    }

    public Object gethBaseInterface() {
        return hBaseInterface;
    }


    public Object getObject(){
        return hBaseInterface;
    }
    public void sethBaseInterface(Object hBaseInterface) {
        this.hBaseInterface = hBaseInterface;
    }
}

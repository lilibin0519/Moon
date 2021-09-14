package com.libin.library.util;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.libin.library.activity.BaseAppCompatActivity;
import com.libin.library.adapter.BaseAdapterViewListener;
import com.libin.library.fragment.BaseFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class ViewBindingUtil {

    private static final Class<?>[] baseClasses = {BaseAppCompatActivity.class, BaseFragment.class, BaseAdapterViewListener.class};

    public static <VB extends ViewBinding> VB inflateWithGeneric(BaseAppCompatActivity<? extends ViewBinding> appCompatActivity, LayoutInflater inflater){
        return withGenericBindingClass(appCompatActivity, inflater);
    }

    public static <VB extends ViewBinding> VB inflateWithGeneric(BaseFragment<? extends ViewBinding> baseFragment, LayoutInflater inflater, ViewGroup container, boolean attachToParent){
        return withGenericBindingClass(baseFragment, inflater, container, attachToParent);
    }

    public static <VB extends ViewBinding> VB inflateWithGeneric(BaseAdapterViewListener<?, VB> viewListener, LayoutInflater inflater, ViewGroup container, boolean attachToParent){
        return withGenericBindingClass(viewListener, inflater, container, attachToParent);
    }

    private static boolean isBaseClass(Object o){
        for (Class<?> clazz : baseClasses) {
            if (clazz.isAssignableFrom(o.getClass())) {
                return true;
            }
        }
        return false;
    }

    private static Class<?> getClass(Object o) {
        if (!isBaseClass(o)) {
            return null;
        }

        TypeVariable<?>[] typeArr = o.getClass().getTypeParameters();
        Class<?> bindingClass = null;
        if (typeArr.length > 0) {
            for (TypeVariable<?> typeVariable : typeArr) {
                bindingClass = getClassByTypes(typeVariable.getBounds());
                if (null != bindingClass) {
                    break;
                }
            }
        }
        if (null == bindingClass) {
            Type type = o.getClass().getGenericSuperclass();
            Class<?> clazz = o.getClass().getSuperclass();
            if (null != type) {
                while (null == bindingClass) {
                    if (type instanceof Class) {
                        if (((Class<?>) type).getName().equals(Fragment.class.getName()) || ((Class<?>) type).getName().equals(AppCompatActivity.class.getName())) {
                            break;
                        }
                    } else if (type instanceof ParameterizedType) {
                        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                        bindingClass = getClassByTypes(types);
                    }
                    if (null == bindingClass) {
                        if (null == clazz) {
                            break;
                        }
                        type = clazz.getGenericSuperclass();
                        clazz = clazz.getSuperclass();
                    }
                }
            }
        }
        return bindingClass;
    }

    public static Class<?> getClassByTypes(Type[] types){
        if (types.length > 0) {
            for (Type mType : types) {
                if (mType instanceof Class<?>) {
                    Class<?> temp = (Class<?>) mType;
                    if (ViewBinding.class.isAssignableFrom(temp)) {
                        return temp;
                    }
                }
            }
        }
        return null;
    }

    private static <VB extends ViewBinding> VB withGenericBindingClass(BaseAppCompatActivity<? extends ViewBinding> o, LayoutInflater inflater){
        Class<?> bindingClass = getClass(o);
        if (null == bindingClass) {
            return null;
        }
        Method method;
        VB vb = null;
        try {
            method = bindingClass.getMethod("inflate", LayoutInflater.class);
            vb = (VB) method.invoke(null, inflater);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return vb;
    }

    private static <VB extends ViewBinding> VB withGenericBindingClass(Object o, LayoutInflater inflater, ViewGroup container, boolean attachToParent){
        Class<?> bindingClass = getClass(o);
        if (null == bindingClass) {
            return null;
        }
        Method method;
        VB vb = null;
        try {
            method = bindingClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            vb = (VB) method.invoke(null, inflater, container, attachToParent);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return vb;
    }
}

package com.zx.yuren.jdk.reflection.aopframework;

import java.lang.reflect.Method;

public interface Advice {
	void beforeMethod(Method method);
	void afterMethod(Method method);
}

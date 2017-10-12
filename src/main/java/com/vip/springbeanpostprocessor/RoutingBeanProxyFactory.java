package com.vip.springbeanpostprocessor;

import java.lang.reflect.Method;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.StringUtils;

public class RoutingBeanProxyFactory {

	public static Object createProxy(Class<?> targetClass, Map<String, ?> beans) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setInterfaces(targetClass);
		proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(targetClass,beans));
		return proxyFactory.getProxy();
	}
	
	static class  VersionRoutingMethodInterceptor implements MethodInterceptor{
		private String classSwitch;
		private Object beanOfSwitchOn;
		private Object beanOfSwitchOff;
		
		
		public VersionRoutingMethodInterceptor(Class<?> targetClass, Map<String, ?> beans) {
			String interfaceName = StringUtils.uncapitalize(targetClass.getSimpleName());
			if (targetClass.isAnnotationPresent(RoutingSwitch.class)) {
				classSwitch = targetClass.getAnnotation(RoutingSwitch.class).value();
			}
			
			beanOfSwitchOn = beans.get(this.buildBeanName(interfaceName, true));
			beanOfSwitchOff = beans.get(this.buildBeanName(interfaceName, false));
		}
		
		private String buildBeanName(String interfaceName, boolean isSwitchOn) {
			return interfaceName + "Impl" + (isSwitchOn?"V2":"V1");
		}


		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			Method method = invocation.getMethod();
			String switchName = this.classSwitch;
			if (method.isAnnotationPresent(RoutingSwitch.class)) {
				switchName = method.getAnnotation(RoutingSwitch.class).value();
			}
			
			if (StringUtils.isEmpty(switchName)) {
				throw new IllegalStateException("Routing switch value is blank, method: " + method.getName());
			}
			
			return invocation.getMethod().invoke(getTargetBean(switchName),invocation.getArguments());
		}

		private Object getTargetBean(String switchName) {
			boolean switchOn;
			if ("A".equals(switchName)) {
				switchOn = false;
			} else if ("B".equals(switchName)) {
				switchOn = true;
			} else {
				switchOn = true;
			}
			
			return switchOn? beanOfSwitchOn: beanOfSwitchOff;
		}

	
	}

}

package com.ivangavlik.http.microkernel;

import com.ivangavlik.http.microkernel.reflection.*;
import com.ivangavlik.http.microkernel.reflection.process.StartUp;

import java.lang.annotation.Annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class Microkernel {
    private static HashMap<String, Object> instances = new HashMap<>();

    /**
     * 1. init all dependencies
     * 2. constructs of all plugin classes
     * 3. init extension points
     * 4. run start method
     *
     * TODO cover the case were start is called before extended
     */
    public Microkernel(final String[] dependencyClasses, final String[] pluginClasses) {
        // load dependencies
        StartUp.loadDependencies(dependencyClasses)
                .stream()
                .forEach(pair -> {
                    if (instances.get(pair.getName()) == null) {
                        instances.put(pair.getName(), pair.getInstance());
                    }
                });

        // run plugin constructor
        StartUp.loadPlugins(pluginClasses)
                .stream()
                .filter(pluginClass -> instances.get(pluginClass.getName()) == null)
                .map(pluginClass -> runConstructor(pluginClass))
                .forEach(instance -> instances.put(instance.getClass().getName(), instance));

        instances.values().stream()
                .forEach(instances -> initExtensionPoint(instances));

        instances.values().stream()
                .filter(el -> el.getClass().getDeclaredAnnotation(Plugin.class) != null)
                .forEach(in -> runPluginStart(in));

        List<Object> actionHandlers = instances.values().stream()
                .filter(el -> el.getClass().getDeclaredAnnotation(Plugin.class) != null)
                .filter(el -> Arrays.stream(el.getClass().getDeclaredMethods())
                                .anyMatch(method ->  method.isAnnotationPresent(HandleAction.class)))
                .collect(Collectors.toList());

        ACTION.pluginActionList.addAll(actionHandlers);
    }
    private Object runConstructor(Class pluginClass) {
        for (int i = 0; i < pluginClass.getDeclaredConstructors().length; i++) {
            Constructor con = pluginClass.getDeclaredConstructors()[i];

            Object[] params = Arrays.stream(con.getParameters())
                    .filter(el -> el.isAnnotationPresent(Dependency.class))
                    .map(el ->  instances.get(el.getAnnotation(Dependency.class).name()))
                    .toArray(Object[]::new);
            try {
                return con.newInstance(params);
            } catch (Exception exception) {
               throw new RuntimeException(exception);
            }
        }
        try {
            // if no declared use default constructor
            return pluginClass.getConstructors()[0].newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void runPluginStart(Object instance) {
        Arrays.stream(instance.getClass().getDeclaredMethods())
                .map(method -> new Object() {
                        Method classMethod = method;
                        Annotation annotation = method.getAnnotation(Start.class);
                    }
                )
                .filter(el -> el.annotation != null)
                .map(el -> el.classMethod)
                .findFirst()
                .ifPresent(el -> {
                    try {
                        el.invoke(instance); // TODO method params
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    private Object initExtensionPoint(Object instance) {
        Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(el -> el.getAnnotation(ExtensionPoint.class) != null)
                .map(el -> {
                    // get classes that implements this extension points class
                    List<Class> allImpl = this.instances.values()
                            .stream()
                            .map(in -> in.getClass())
                            .map(in -> new Object() {
                                    Class classInfo = in;
                                    List interfaces = Arrays.stream(in.getInterfaces()).collect(Collectors.toList());
                                }
                            )
                            // contains because typeName includes List<Type>
                          .filter(in -> in.interfaces.stream().anyMatch(i -> el.getGenericType().getTypeName().contains(((Class) i).getName())))
                          .map(in -> in.classInfo)
                          .collect(Collectors.toList());

                    // for each class create instance and add it to instances
                    List<Object> values = allImpl
                            .stream()
                            .map(item -> {
                                Object value = instances.get(item.getName());
                                // find class that implements specific extension point create it and assign
                                // it to this instance
                                if (value != null) {
                                    return value;
                                }
                                Object instanceNew = runConstructor(item);
                                instances.put(instanceNew.getClass().getName(), instanceNew);
                                return instanceNew;

                    })
                    .collect(Collectors.toList());

                    // set up instance extension value
                    try {
                        el.setAccessible(true);
                        el.set(instance, values);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return el;
                })
                .collect(Collectors.toList());

        return instance;
    }
    public static PluginActionManager ACTION = new PluginActionManager();
    public static class PluginActionManager {
        private List<Object> pluginActionList = new ArrayList<>();
        public void emit(Object payload) {

            pluginActionList.stream()
                    .map(el -> el.getClass())
                    .forEach(el -> {
                        Arrays.stream(el.getMethods())
                                .filter(method -> method.getDeclaredAnnotation(HandleAction.class) != null)
                                .forEach(method -> {
                                    try {
                                        method.invoke(instances.get(el.getName()), payload);
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    } catch (InvocationTargetException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                    });
        }

    }

}

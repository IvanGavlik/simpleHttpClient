package com.ivangavlik.http.poc5.reflection.process;

import com.ivangavlik.http.poc5.reflection.DependencyFactory;
import com.ivangavlik.http.poc5.reflection.DependencyInstance;
import com.ivangavlik.http.poc5.reflection.Plugin;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;


public class StartUp {

    public static List<StartUp.Pair> loadDependencies(String[] dependencyClasses) {
        return Arrays.stream(dependencyClasses)
                .map(el -> toDependencyFactory(el))
                .filter(dependencyClass -> dependencyClass.isPresent())
                .flatMap(pluginClass -> toDependencyInstance(pluginClass.get()).stream())
                .collect(Collectors.toList());
    }

    private static List<StartUp.Pair> toDependencyInstance(Class dependencyClass) {
        final Optional<Object> instance = getInstance(dependencyClass);
        if (!instance.isPresent()) {
            return new ArrayList<>();
        }
        return Arrays.stream(dependencyClass.getDeclaredMethods())
                .filter(el -> el.isAnnotationPresent(DependencyInstance.class))
                .map(el -> {
                    try {
                        StartUp.Pair pair = new StartUp.Pair(el.getName(), el.invoke(instance.get()));
                        return Optional.of(pair);
                    } catch (Exception ex) {
                        return Optional.empty();
                    }
                })
                .filter(el -> el.isPresent())
                .map(el ->  (StartUp.Pair) el.get())
                .collect(Collectors.toList());
    }

    private static Optional<Object> getInstance(Class dependencyClass) {
        try {
           return Optional.of(dependencyClass.getConstructors()[0].newInstance());
        } catch (Exception e) {
           e.printStackTrace();
           return Optional.empty();
        }
    }

    private static Optional<Class<DependencyFactory>> toDependencyFactory(String name) {
        try {
            Class candidateClass = Class.forName(name);
            Annotation annotation = candidateClass.getDeclaredAnnotation(DependencyFactory.class);
            if (annotation != null) {
                return Optional.of(candidateClass);
            } else {
                return Optional.empty();
            }
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
    public static List<Class<Plugin>> loadPlugins(String[] pluginClasses) {
        return Arrays.stream(pluginClasses)
                .map(el -> toPlugin(el))
                .filter(pluginClass -> pluginClass.isPresent())
                .map(pluginClass -> pluginClass.get())
                .collect(Collectors.toList());
    }

    private static Optional<Class<Plugin>> toPlugin(String name) {
        try {
            Class candidateClass = Class.forName(name);
            Annotation annotation = candidateClass.getDeclaredAnnotation(Plugin.class);
            if (annotation != null) {
                return Optional.of(candidateClass);
            } else {
                return Optional.empty();
            }
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static class Pair {
        private String name;
        private Object instance;

        public Pair(String name, Object instance) {
            this.name = name;
            this.instance = instance;
        }
        public String getName() {
            return name;
        }
        public Object getInstance() {
            return instance;
        }
    }
}

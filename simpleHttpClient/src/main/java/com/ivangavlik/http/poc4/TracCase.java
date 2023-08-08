package com.ivangavlik.http.poc4;

import java.util.ArrayList;
import java.util.List;

public class TracCase {
    public static void main(String[] args) {

        ComponentManager componentManager = new ComponentManager();
//        componentManager.add();
    }
}

class ComponentManager {
    List<Component> componentList = new ArrayList<>();

    public void add(Component component) {
        this.componentList.add(component);
    }
    public void init() {
        componentList.forEach(el -> el.init());
    }

}
abstract class Component {
    public abstract void init();
}

interface ExtensionPoint {}

class MyComponent extends Component {
    @Override
    public void init() {
        System.out.println("My component init");
    }

    public void print() {
        MyExtensionPoint myExtensionPoint = new MyExtensionPointImpl();
        myExtensionPoint.print();
    }
}

interface MyExtensionPoint extends ExtensionPoint {
    void print();
}

class MyExtensionPointImpl implements MyExtensionPoint {
    @Override
    public void print() {
        System.out.println("I am printing from extension point");
    }
}

class MyComponent2 extends Component implements MyExtensionPoint {
    @Override
    public void init() {
        System.out.println("init my component 2");
    }

    @Override
    public void print() {

    }
}

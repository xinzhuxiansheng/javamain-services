package com.javamain.java8.javastream.customfilter.test01;

import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author yzhou
 * @date 2023/2/2
 */
public class CustomFilter {
    private static List<Person> persons = new ArrayList<>();

    public static List<Person> initPersons() {
        Person p1 = new Person("yzhou", 20, "BJ");
        Person p2 = new Person("wuzong", 21, "HEBEI");
        Person p3 = new Person("90", 22, "HUNAN");
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        return persons;
    }

    public static void main(String[] args) {
        initPersons();

        // 通过FunctionalInterface 自定义过滤
        println(doFileterNameIsYzhou());
    }


    public static List<Person> doFileterNameIsYzhou() {
        return filterNameIsYzhou(person -> {
            return StringUtils.isNotBlank(person.getName())
                    && person.getName().equals("yzhou");
        });
    }


    public static List<Person> filterNameIsYzhou(Filter<Person> nameFileter) {
        List<Person> filterList = new ArrayList<>();
        if (!persons.isEmpty()) {
            //persons.stream().filter(nameFileter).collect(Collectors.toList());
            Iterator it = persons.iterator();
            while (it.hasNext()) {
                Person p = (Person) it.next();
                if (nameFileter.accept(p)) {
                    filterList.add(p);
                }
            }
        }
        return filterList;
    }

    public static void println(List<Person> persons) {
        persons.forEach(person -> {
            System.out.println(String.format("name: %s, age: %s, address: %s", person.getName(),
                    person.getAge(), person.getAddress()));
        });
    }
}

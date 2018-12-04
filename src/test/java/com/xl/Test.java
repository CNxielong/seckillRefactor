package com.xl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 测试
 * @Auther: X-Dragon
 * @Date: 2018/12/4 11:21
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args){
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2));
        add(list);
        for (Integer i: list) {
            System.out.println(i);
        }

        Integer[] nums = {1,2,3};
        List<Integer> list1 = Arrays.asList(nums);
        list1.add(4);
        System.out.println(list1.size());
    }

    private static void add(List<Integer> list) {
        list.add(3);
    }

}

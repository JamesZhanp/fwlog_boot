package com.fwlog.james.utils;

import java.util.ArrayList;

/**
 * Created by 16255 on 2017/7/12.
 * 求均值，方差和保留两位小数
 * @author jamesZhan
 **/
public class VarianceUtil {
    public static double getAverage(ArrayList array){
        int sum = 0;
        for(int i = 0 ; i < array.size() ; i++){
            sum += Integer.parseInt(String.valueOf(array.get(i)));
        }
        return (sum * 1.00 / array.size());
    }

    public static double getVariance(ArrayList array){
        double variance = 0;
        for(int i = 0 ; i < array.size() ; i++){
            variance += Math.pow((Integer.parseInt(String.valueOf(array.get(i))) - getAverage(array)),2);
        }
        variance = variance * 1.00 / array.size();
        return variance;
    }

    //四舍五入保留两位小数
    public static double keepTwoDecimalPlaces(double number){
        String line = String.format("%.3f",number);
        double d = Double.parseDouble(line);
        return d;
    }
    public static void main(String[] args){
        ArrayList array = new ArrayList();
        array.add(2);
        array.add(1);
        array.add(4);
        array.add(5);
        System.out.println(getVariance(array));
    }
}

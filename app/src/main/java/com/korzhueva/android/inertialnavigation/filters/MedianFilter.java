package com.korzhueva.android.inertialnavigation.filters;

public class MedianFilter implements FilterInterface {
    private int window;
    private double[] values;
    private int count = 0;

    // Инициализация класса
    public MedianFilter(int window) {
        this.window = window;
        this.values = new double[window];
    }

    // Обновление состояния фильтра
    public double update(double current) {
        if (count >= window)
            count = 2;

        values[count] = current;
        count++;

        if (count == window) {
            double middle = getMiddle(values);
            for (int i = 0; i < values.length - 1; i++)
                values[i] = values[i + 1];

            return middle;
        } else
            return current;
    }

    // Сброс состояния фильтра
    private double getMiddle(double[] values) {
        double middle;

        if ((values[0] <= values[1]) && (values[0] <= values[2])) {
            middle = Math.min(values[1], values[2]);
        } else {
            if ((values[1] <= values[0]) && (values[1] <= values[2])) {
                middle = Math.min(values[0], values[2]);
            } else {
                middle = Math.min(values[0], values[1]);
            }
        }
        return middle;
    }

    public void reset() {
        window = 0;
        values = new double[3];
        count = 0;
    }
}
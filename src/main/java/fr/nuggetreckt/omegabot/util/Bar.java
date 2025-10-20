package fr.nuggetreckt.omegabot.util;

public class Bar {

    private final char[] progressBar;
    private long maxValue;
    private final int size;
    private int percentage;
    private int i;

    public Bar(int size, long max) {
        this.maxValue = max;
        this.size = size;
        this.i = 1;
        progressBar = new char[size + 1];

        for (int i = 0; i < size; i++) {
            progressBar[i] = ' ';
        }
    }

    public void update() {
        double ratio = ((double) i / maxValue);
        int pos = (int) Math.round(ratio * size);
        percentage = (int) Math.round(ratio * 100);

        for (int j = 0; j < size; j++) {
            progressBar[j] = ' ';
        }
        for (int j = 0; j < pos; j++) {
            progressBar[j] = '|';
        }
        i++;
    }

    public void display() {
        String bar = String.copyValueOf(progressBar);
        String color = AnsiUtil.GREEN;

        if (percentage > 0 && percentage < 15) {
            color = AnsiUtil.PURPLE;
        } else if (percentage >= 15 && percentage < 35) {
            color = AnsiUtil.RED;
        } else if (percentage >= 35 && percentage < 75) {
            color = AnsiUtil.YELLOW;
        }
        System.out.print("\r" + AnsiUtil.RESET + "[" + AnsiUtil.GREEN + bar + AnsiUtil.RESET + "] " + color + percentage + "%" + AnsiUtil.RESET);
    }

    public void setMaxValue(long value) {
        this.maxValue = value;
    }
}

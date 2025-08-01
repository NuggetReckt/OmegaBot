package fr.nuggetreckt.omegabot.util;

public class Bar {

    private final char[] progressBar;
    private final int maxValue;
    private final int size;
    private int percentage;

    public Bar(int size, int max) {
        this.maxValue = max;
        this.size = size;
        progressBar = new char[size + 1];

        for (int i = 0; i < size; i++) {
            progressBar[i] = ' ';
        }
    }

    public void update(int i) {
        double ratio = ((double) i / maxValue);
        int pos = (int) Math.round(ratio * size);
        percentage = (int) Math.round(ratio * 100);

        for (int j = 0; j < size; j++) {
            progressBar[j] = ' ';
        }
        for (int j = 0; j < pos; j++) {
            progressBar[j] = '|';
        }
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
}

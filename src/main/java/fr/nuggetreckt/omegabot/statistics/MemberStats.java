package fr.nuggetreckt.omegabot.statistics;

public class MemberStats {

    public long counted;
    public long magicNumberCount;
    public long hundredsCount;
    public long thousandsCount;

    public MemberStats(long counted, long magicNumberCount, long hundredsCount, long thousandsCount) {
        this.counted = counted;
        this.magicNumberCount = magicNumberCount;
        this.hundredsCount = hundredsCount;
        this.thousandsCount = thousandsCount;
    }

    public MemberStats() {
        this.counted = 0;
        this.magicNumberCount = 0;
        this.hundredsCount = 0;
        this.thousandsCount = 0;
    }

    public long getScore() {
        long score = counted;

        score += magicNumberCount * 4;
        score += hundredsCount * 2;
        score += thousandsCount * 3;
        return score;
    }
}

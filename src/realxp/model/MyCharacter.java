package realxp.model;

public class MyCharacter {
    private int level;
    private int exp;

    // (1) 기본 생성자
    public MyCharacter() {
        this.level = 0;
        this.exp = 0;
    }

    // (2) level, exp 받는 생성자
    public MyCharacter(int level, int exp) {
        this.level = level;
        this.exp = exp;
    }

    private int getRequiredExp() {
        return level * 100;
    }

    public void gainExp(int amount) {
        exp += amount;
        while (exp >= getRequiredExp()) {
            exp -= getRequiredExp();
            level++;
            System.out.println("레벨업! 현재 레벨: " + level);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextLevelExp() {
        return getRequiredExp();
    }
    
    public int getReaminExp() {
    	return getRequiredExp() - exp;
    }
}
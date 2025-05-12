package AgeOfConquest.core;

import AgeOfConquest.ui.Gui;

public enum Building {
    WOODENHOUSE('O', "🏠", "Wooden House", new int[]{200, 0, 0, 0, 0}),
    STONEHOUSE('N', "🏡", "Stone House", new int[]{200, 100, 50, 0, 0}),
    WELL('W', "💧", "Well", new int[]{50, 100, 0, 0, 0}),
    MARKETPLACE('M', "🏪", "Marketplace", new int[]{150, 50, 0, 10, 0}),
    LUMBERCAMP('L', "🪓", "Lumber Camp", new int[]{400, 0, 0, 0, 0}),
    FARM('F', "🌾", "Farm", new int[]{300, 100, 0, 0, 20}),
    HUNTINGLODGE('H', "🏹", "Hunting Lodge", new int[]{250, 0, 0, 0, 0}),
    IRONMINE('R', "⛏️", "Iron Mine", new int[]{150, 50, 0, 0, 0}),
    GOLDMINE('D', "💰", "Gold Mine", new int[]{200, 50, 0, 50, 0}),
    STONEMINE('S', "🪨", "Stone Mine", new int[]{200, 0, 0, 0, 0});

    private final char mapChar;
    private final String emoji;
    private final String name;
    private final int[] costs;

    Building(char mapChar, String emoji, String name, int[] costs) {
        this.mapChar = mapChar;
        this.emoji = emoji;
        this.name = name;
        this.costs = costs;
    }

    public String getDisplayString() {
        StringBuilder costStr = new StringBuilder();
        if (costs[0] > 0) costStr.append(costs[0]).append(" Wood ");
        if (costs[1] > 0) costStr.append(costs[1]).append(" Stone ");
        if (costs[2] > 0) costStr.append(costs[2]).append(" Iron ");
        if (costs[3] > 0) costStr.append(costs[3]).append(" Gold ");
        if (costs[4] > 0) costStr.append(costs[4]).append(" Food ");

        return "<html>" + emoji + " " + name + "<br>" + costStr.toString().trim() + "</html>";
    }



    public int[] getCosts() { return costs; }
    public String getEmoji() { return emoji; }
    public char getMapChar() { return mapChar; }

}

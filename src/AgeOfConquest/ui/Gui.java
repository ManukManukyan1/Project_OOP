package AgeOfConquest.ui;

import AgeOfConquest.core.Map;
import AgeOfConquest.core.Building;
import AgeOfConquest.core.Citizen;
import AgeOfConquest.exceptions.MalformedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui extends JFrame {
    private enum Resource {
        TREE("🌳"),
        STONE("🪨"),
        IRON("⛏"),
        GOLD("\uD83E\uDDC8"),
        EMPTY("");

        private String icon;
        Resource(String icon) {
            this.icon = icon;
        }
        public String getIcon() { return icon; }
    }


    private static final int MAP_SIZE = 16;
    private Map gameMap;
    private Citizen citizen = new Citizen();
    private Resource[][] map;
    private JLabel[][] tiles;
    private JPanel resourcePanel;
    private JButton nextTurnButton;
    private JPanel buildingPanel;
    private int wood;
    private int stone;
    private int iron;
    private int gold;
    private int food;
    private JPanel citizenPanel = new JPanel();

    public Gui() {
        gameMap = new Map();
        gameMap.generateMap();

        initializeMap();

        initializeUI();

        SwingUtilities.invokeLater(() -> {
            updateResourceDisplay();
            updateCitizenDisplay();
            updateMapTiles();
        });
    }


    private void initializeMap() {
        map = new Resource[MAP_SIZE][MAP_SIZE];
        for (int row = 0; row < MAP_SIZE; row++) {
            for (int col = 0; col < MAP_SIZE; col++) {
                map[row][col] = Resource.EMPTY;
            }
        }

        for (int row = 0; row < MAP_SIZE; row++) {
            for (int col = 0; col < MAP_SIZE; col++) {
                char cell = gameMap.getMapCell(row, col);
                switch (cell) {
                    case 'T': map[row][col] = Resource.TREE; break;
                    case 'S': map[row][col] = Resource.STONE; break;
                    case 'I': map[row][col] = Resource.IRON; break;
                    case 'G': map[row][col] = Resource.GOLD; break;
                    default: map[row][col] = Resource.EMPTY;
                }
            }
        }
    }

    private void initializeUI() {
        setTitle("Age of Conquest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        citizenPanel = new JPanel(new GridLayout(1, 2));
        citizenPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(citizenPanel, BorderLayout.NORTH);


        JPanel mapPanel = new JPanel(new GridLayout(MAP_SIZE, MAP_SIZE));
        tiles = new JLabel[MAP_SIZE][MAP_SIZE];

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                JLabel tile = new JLabel(map[i][j].getIcon(), SwingConstants.CENTER);
                tile.setOpaque(true);
                tile.setBackground(getResourceColor(map[i][j]));
                tile.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
                tile.setPreferredSize(new Dimension(50, 50));
                tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final int row = i, col = j;
                tile.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        handleTileClick(row, col);
                    }
                });

                tiles[i][j] = tile;
                mapPanel.add(tile);
            }
        }

        bottomPanel = new JPanel(new BorderLayout());

        JPanel citizenPanel = new JPanel();
        updateCitizenDisplay(citizenPanel);
        bottomPanel.add(citizenPanel, BorderLayout.NORTH);

        citizenPanel = new JPanel(new GridLayout(1, 2));
        citizenPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        bottomPanel.add(citizenPanel, BorderLayout.NORTH);


        resourcePanel = new JPanel(new GridLayout(2, 3, 5, 5));
        resourcePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(resourcePanel, BorderLayout.CENTER);

        updateResourceDisplay();
        bottomPanel.add(resourcePanel, BorderLayout.CENTER);

        nextTurnButton = new JButton("Next Turn");
        JPanel finalCitizenPanel = citizenPanel;
        nextTurnButton.addActionListener(e -> {
            gameMap.nextTurn();
            updateResourceDisplay();
            updateCitizenDisplay(finalCitizenPanel);
            updateMapTiles();
        });
        bottomPanel.add(nextTurnButton, BorderLayout.SOUTH);


        add(mapPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        updateResourceDisplay();
        updateAllDisplays();


        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
    }

    private void handleTileClick(int row, int col) {
        if (row == MAP_SIZE ) {
            return;
        }

        Resource resource = map[row][col];
        if (resource != Resource.EMPTY) {
            String[] options = {"Gather Resource", "Cancel"};
            int choice = JOptionPane.showOptionDialog(this,
                    "What would you like to do with this " + resource.name() + "?",
                    "Resource Action",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 0) {
                gatherResource(row, col);
            }
        } else {
            showBuildingOptions(row, col);
        }
    }

    private void gatherResource(int row, int col) {
        int idle = gameMap.getIdle();

        if (idle <= 0) {
            System.err.println("No idle citizens available!");
            return;
        }

        Integer[] options = new Integer[Math.min(5, idle)];
        for (int i = 0; i < options.length; i++) {
            options[i] = i + 1;
        }

        Integer choice = (Integer)JOptionPane.showInputDialog(
                this,
                "Assign workers (Available: " + idle + ")",
                "Gather Resource",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice != null) {
            try {
                gameMap.gainResource(choice, row, col);
                citizen.gain(choice);
                updateAllDisplays();
            } catch (MalformedException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private void updateCitizenDisplay() {
        int idle = gameMap.getCitizen().getIdle();
        int working = gameMap.getCitizen().getWorking();

        JPanel citizenPanel = (JPanel) nextTurnButton.getParent().getComponent(0);
        citizenPanel.removeAll();

        JLabel idleLabel = new JLabel("Idle: " + idle);
        JLabel workingLabel = new JLabel("Working: " + working);

        citizenPanel.setLayout(new GridLayout(1, 2));
        citizenPanel.add(idleLabel);
        citizenPanel.add(workingLabel);

        citizenPanel.revalidate();
        citizenPanel.repaint();
    }


    private void showBuildingOptions(int row, int col) {
        JDialog dialog = new JDialog(this, "Construct Building", true);
        dialog.setLayout(new BorderLayout());
        dialog.setPreferredSize(new Dimension(300, 400));

        JPanel buildingPanel = new JPanel();
        buildingPanel.setLayout(new BoxLayout(buildingPanel, BoxLayout.Y_AXIS));
        buildingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Building building : Building.values()) {
            JButton btn = new JButton();
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(280, 60));

            String html = String.format("<html><center><b>%s</b><br>" +
                            "<font color='#FF9900'>Cost: %s</font><br>" +
                            "<font color='#66CC66'>Effect: %s</font></center></html>",
                    building.name(),
                    getCostString(building),
                    getEffectDescription(building));

            btn.setText(html);
            btn.setEnabled(canAfford(building));

            btn.addActionListener(e -> {
                try {
                    gameMap.build(building, row, col);
                    citizen.addCitizens(gameMap.getIdleBuild());
                    updateAllDisplays();
                    dialog.dispose();
                } catch (MalformedException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Cannot build: " + ex.getMessage(),
                            "Construction Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            buildingPanel.add(btn);
            buildingPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private String getCostString(Building building) {
        StringBuilder sb = new StringBuilder();
        int[] costs = building.getCosts();
        if (costs[0] > 0) sb.append(costs[0]).append(" Wood ");
        if (costs[1] > 0) sb.append(costs[1]).append(" Stone ");
        if (costs[2] > 0) sb.append(costs[2]).append(" Iron ");
        if (costs[3] > 0) sb.append(costs[3]).append(" Gold ");
        if (costs[4] > 0) sb.append(costs[4]).append(" Food ");
        return sb.toString().trim();
    }

    private String getEffectDescription(Building building) {
        return switch (building) {
            case WOODENHOUSE -> "+3 Population";
            case STONEHOUSE -> "+5 Population";
            case WELL -> "+10% Food Production";
            case MARKETPLACE -> "+15% Gold Production";
            case LUMBERCAMP -> "+20% Wood Production";
            case FARM -> "+2 Food/Turn, +10% Food";
            case HUNTINGLODGE -> "+10% Food Production";
            case IRONMINE -> "+15% Iron Production";
            case GOLDMINE -> "+10% Gold Production";
            case STONEMINE -> "+20% Stone Production";
        };
    }



    private boolean canAfford(Building building) {
        int[] costs = building.getCosts();
        return gameMap.getWood() >= costs[0] &&
                gameMap.getStone() >= costs[1] &&
                gameMap.getIron() >= costs[2] &&
                gameMap.getGold() >= costs[3] &&
                gameMap.getFood() >= costs[4];
    }



    private void updateCitizenDisplay(JPanel panel) {
        panel.removeAll();
        panel.setLayout(new GridLayout(1, 2));

        JLabel idleLabel = new JLabel("Idle Citizens: " + citizen.getIdle());
        JLabel workingLabel = new JLabel("Working Citizens: " + citizen.getWorking());

        panel.add(idleLabel);
        panel.add(workingLabel);

        panel.revalidate();
        panel.repaint();
    }

    private void updateResourceDisplay() {
        resourcePanel.removeAll();
        resourcePanel.setLayout(new GridLayout(1, 6)); // One row, 6 columns

        resourcePanel.add(createResourceLabel("🍎: " + gameMap.getFood()));
        resourcePanel.add(createResourceLabel("🪵: " + gameMap.getWood()));
        resourcePanel.add(createResourceLabel("🪨: " + gameMap.getStone()));
        resourcePanel.add(createResourceLabel("⛏️: " + gameMap.getIron()));
        resourcePanel.add(createResourceLabel("💰: " + gameMap.getGold()));
        resourcePanel.add(createResourceLabel("👥: " + gameMap.getCitizen().getIdle() + "/" +
                gameMap.getCitizen().getpop()));

        resourcePanel.revalidate();
        resourcePanel.repaint();
    }

    private JLabel createResourceLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        return label;
    }



    private void updateMapTiles() {
        if (gameMap == null) return;

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                char cell = gameMap.getMapCell(i, j);
            }
        }
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                char cell = gameMap.getMapCell(i, j);
                Resource resource = Resource.EMPTY;
                String icon = "";

                switch (cell) {
                    case 'T': resource = Resource.TREE; break;
                    case 'S': resource = Resource.STONE; break;
                    case 'I': resource = Resource.IRON; break;
                    case 'G': resource = Resource.GOLD; break;
                    case 'O': icon = Building.WOODENHOUSE.getEmoji(); break;  // Wooden House
                    case 'N': icon = Building.STONEHOUSE.getEmoji(); break;   // Stone House
                    case 'W': icon = Building.WELL.getEmoji(); break;         // Well
                    case 'M': icon = Building.MARKETPLACE.getEmoji(); break;  // Marketplace
                    case 'L': icon = Building.LUMBERCAMP.getEmoji(); break;   // Lumber Camp
                    case 'F': icon = Building.FARM.getEmoji(); break;         // Farm
                    case 'H': icon = Building.HUNTINGLODGE.getEmoji(); break; // Hunting Lodge
                    case 'A': icon = Building.STONEMINE.getEmoji(); break;    // Warehouse
                    case 'R': icon = Building.IRONMINE.getEmoji(); break;     // Iron Mine
                    case 'D': icon = Building.GOLDMINE.getEmoji(); break;    // Gold Mine
                }

                if (!icon.isEmpty()) {
                    tiles[i][j].setText(icon);
                    tiles[i][j].setBackground(getResourceColor(Resource.EMPTY));
                } else {
                    tiles[i][j].setText(resource.getIcon());
                    tiles[i][j].setBackground(getResourceColor(resource));
                }
            }
        }
    }

    private Color getResourceColor(Resource resource) {
        return switch (resource) {
            case TREE -> new Color(34, 139, 34);
            case STONE -> new Color(66, 66, 66, 209);
            case IRON -> new Color(192, 192, 192);
            case GOLD -> new Color(255, 215, 0);
            case EMPTY -> new Color(34, 139, 34);
        };
    }



    private void updateAllDisplays() {
        int totalCitizens = citizen.getpop();
        int workingCitizens = citizen.getWorking(); // Implement this method in Map.java
        int idleCitizens = citizen.getIdle();

        System.out.println("DISPLAY UPDATE - Total: " + totalCitizens
                + " | Working: " + workingCitizens
                + " | Idle: " + idleCitizens);

        citizenPanel.removeAll();
        citizenPanel.add(new JLabel("Idle: " + idleCitizens));
        citizenPanel.add(new JLabel("Working: " + workingCitizens));
        citizenPanel.revalidate();
        citizenPanel.repaint();

        resourcePanel.removeAll();
        resourcePanel.add(new JLabel("🍎 Food: " + gameMap.getFood()));
        resourcePanel.add(new JLabel("🪵 Wood: " + gameMap.getWood()));
        resourcePanel.add(new JLabel("🪨 Stone: " + gameMap.getStone()));
        resourcePanel.add(new JLabel("⛏️ Iron: " + gameMap.getIron()));
        resourcePanel.add(new JLabel("💰 Gold: " + gameMap.getGold()));
        resourcePanel.revalidate();
        resourcePanel.repaint();

        updateMapTiles();
        updateCitizenDisplay();

        revalidate();
        repaint();
    }
}
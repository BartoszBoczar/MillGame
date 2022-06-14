package sample.Game;

import javafx.scene.layout.GridPane;

public class Board extends GridPane {
    public static final int X_LENGTH = 7;
    public static final int Y_LENGTH = 7;
    public static final int HOW_MANY_NODES = 24;
    public static final int BUTTON_PADDING = 20;
    public static final int BUTTON_H_GAP = 34;
    public static final int BUTTON_V_GAP = 34;

    private Node[] boardOfNodes = new Node[X_LENGTH * Y_LENGTH];
    private Node[] nodes = new Node[HOW_MANY_NODES];
    private SimplifiedNode[]  simplifiedNodes = new SimplifiedNode[nodes.length];
    private int[] realIndexes = new int[X_LENGTH * Y_LENGTH];
    private Line[] lines;
    private SimplifiedLine[] simplifiedLines;
    private Node currentSourceNode = null;


    public Board() {
        super();

        initializeRealIndexes();
        initializeNodes();
        setNodesAround();
        initializeLines();

    }

    public void initializeNodes() {
        boardOfNodes[0] = new Node(0, Pawn.EMPTY);
        boardOfNodes[1] = new Node(1, Pawn.EMPTY);
        boardOfNodes[2] = new Node(2, Pawn.EMPTY);
        boardOfNodes[9] = new Node(9, Pawn.EMPTY);
        boardOfNodes[14] = new Node(14, Pawn.EMPTY);
        boardOfNodes[21] = new Node(21, Pawn.EMPTY);
        boardOfNodes[22] = new Node(22, Pawn.EMPTY);
        boardOfNodes[23] = new Node(23, Pawn.EMPTY);

        // adjacent boardOfNodes for middle box
        boardOfNodes[3] = new Node(3, Pawn.EMPTY);
        boardOfNodes[4] = new Node(4, Pawn.EMPTY);
        boardOfNodes[5] = new Node(5, Pawn.EMPTY);
        boardOfNodes[10] = new Node(10, Pawn.EMPTY);
        boardOfNodes[13] = new Node(13, Pawn.EMPTY);
        boardOfNodes[18] = new Node(18, Pawn.EMPTY);
        boardOfNodes[19] = new Node(19, Pawn.EMPTY);
        boardOfNodes[20] = new Node(20, Pawn.EMPTY);

        // adjacent boardOfNodes for inner box
        boardOfNodes[6] = new Node(6, Pawn.EMPTY);
        boardOfNodes[7] = new Node(7, Pawn.EMPTY);
        boardOfNodes[8] = new Node(8, Pawn.EMPTY);
        boardOfNodes[11] = new Node(11, Pawn.EMPTY);
        boardOfNodes[12] = new Node(12, Pawn.EMPTY);
        boardOfNodes[15] = new Node(15, Pawn.EMPTY);
        boardOfNodes[16] = new Node(16, Pawn.EMPTY);
        boardOfNodes[17] = new Node(17, Pawn.EMPTY);

        int i = 0;
        for(Node node : boardOfNodes) {
            if(node != null) {
                nodes[i] = node;
                simplifiedNodes[i] = new SimplifiedNode(nodes[i]);
                nodes[i].setSimplifiedSelf(simplifiedNodes[i]);
                i++;
            }
        }
        int a = 0;
    }

    public void setNodesAround() {
        // adjacent boardOfNodes for outer box
        boardOfNodes[0].setNodesAroundIndexes(new int[]{1, 9});
        boardOfNodes[1].setNodesAroundIndexes(new int[]{0, 2, 4});
        boardOfNodes[2].setNodesAroundIndexes(new int[]{1, 14});
        boardOfNodes[9].setNodesAroundIndexes(new int[]{0, 10, 21});
        boardOfNodes[14].setNodesAroundIndexes(new int[]{2, 13, 23});
        boardOfNodes[21].setNodesAroundIndexes(new int[]{9, 22});
        boardOfNodes[22].setNodesAroundIndexes(new int[]{19, 21, 23});
        boardOfNodes[23].setNodesAroundIndexes(new int[]{14, 22});

        // adjacent boardOfNodes for middle box
        boardOfNodes[3].setNodesAroundIndexes(new int[]{4, 10});
        boardOfNodes[4].setNodesAroundIndexes(new int[]{1, 3, 5, 7});
        boardOfNodes[5].setNodesAroundIndexes(new int[]{4, 13});
        boardOfNodes[10].setNodesAroundIndexes(new int[]{3, 9, 11, 18});
        boardOfNodes[13].setNodesAroundIndexes(new int[]{5, 12, 14, 20});
        boardOfNodes[18].setNodesAroundIndexes(new int[]{10, 19});
        boardOfNodes[19].setNodesAroundIndexes(new int[]{16, 18, 20, 22});
        boardOfNodes[20].setNodesAroundIndexes(new int[]{13, 19});

        // adjacent boardOfNodes for inner box
        boardOfNodes[6].setNodesAroundIndexes(new int[]{7, 11});
        boardOfNodes[7].setNodesAroundIndexes(new int[]{4, 6, 8});
        boardOfNodes[8].setNodesAroundIndexes(new int[]{7, 12});
        boardOfNodes[11].setNodesAroundIndexes(new int[]{6, 10, 15});
        boardOfNodes[12].setNodesAroundIndexes(new int[]{8, 13, 17});
        boardOfNodes[15].setNodesAroundIndexes(new int[]{11, 16});
        boardOfNodes[16].setNodesAroundIndexes(new int[]{15, 17, 19});
        boardOfNodes[17].setNodesAroundIndexes(new int[]{12, 16});

        for(Node node : nodes) {
            node.getSimplifiedSelf().setNodesAroundIndexes(node.getNodesAroundIndexes());
        }
    }

    private void initializeLines() {
        lines = new Line[]{
                // mill combinations for the outer box
                new Line(boardOfNodes[0], boardOfNodes[1], boardOfNodes[2]),
                new Line(boardOfNodes[0], boardOfNodes[9], boardOfNodes[21]),
                new Line(boardOfNodes[2], boardOfNodes[14], boardOfNodes[23]),
                new Line(boardOfNodes[21], boardOfNodes[22], boardOfNodes[23]),

                // mill combinations for the middle box
                new Line(boardOfNodes[3], boardOfNodes[4], boardOfNodes[5]),
                new Line(boardOfNodes[3], boardOfNodes[10], boardOfNodes[18]),
                new Line(boardOfNodes[5], boardOfNodes[13], boardOfNodes[20]),
                new Line(boardOfNodes[18], boardOfNodes[19], boardOfNodes[20]),

                // mill combinations for the inner box
                new Line(boardOfNodes[6], boardOfNodes[7], boardOfNodes[8]),
                new Line(boardOfNodes[6], boardOfNodes[11], boardOfNodes[15]),
                new Line(boardOfNodes[8], boardOfNodes[12], boardOfNodes[17]),
                new Line(boardOfNodes[15], boardOfNodes[16], boardOfNodes[17]),

                // mill combinations for the various lines connecting the boxes
                new Line(boardOfNodes[1], boardOfNodes[4], boardOfNodes[7]),
                new Line(boardOfNodes[9], boardOfNodes[10], boardOfNodes[11]),
                new Line(boardOfNodes[12], boardOfNodes[13], boardOfNodes[14]),
                new Line(boardOfNodes[16], boardOfNodes[19], boardOfNodes[22])
        };
        simplifiedLines = new SimplifiedLine[lines.length];
        for(int i = 0; i < lines.length; i++) {
            simplifiedLines[i] = new SimplifiedLine(lines[i]);
            lines[i].setSimplifiedSelf(simplifiedLines[i]);
        }
    }

    public Node getNode(int x, int y) {
        return boardOfNodes[x * X_LENGTH + y];
    }

    public Node getNode(int index) {
        return boardOfNodes[realIndexes[index]];
    }

    private void initializeRealIndexes() {
        realIndexes[0] = 0;
        realIndexes[1] = 3;
        realIndexes[2] = 6;
        realIndexes[3] = 8;
        realIndexes[4] = 10;
        realIndexes[5] = 12;
        realIndexes[6] = 16;
        realIndexes[7] = 17;
        realIndexes[8] = 18;
        realIndexes[9] = 21;
        realIndexes[10] = 22;
        realIndexes[11] = 23;
        realIndexes[12] = 25;
        realIndexes[13] = 26;
        realIndexes[14] = 27;
        realIndexes[15] = 30;
        realIndexes[16] = 31;
        realIndexes[17] = 32;
        realIndexes[18] = 36;
        realIndexes[19] = 38;
        realIndexes[20] = 40;
        realIndexes[21] = 42;
        realIndexes[22] = 45;
        realIndexes[23] = 48;

    }

    public int realIndex(int index) {
        return realIndexes[index];
    }

    public Node[] getBoardOfNodes() {
        return boardOfNodes;
    }

    public void setBoardOfNodes(Node[] boardOfNodes) {
        this.boardOfNodes = boardOfNodes;
    }

    public int[] getRealIndexes() {
        return realIndexes;
    }

    public void setRealIndexes(int[] realIndexes) {
        this.realIndexes = realIndexes;
    }

    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }

    public Node getCurrentSourceNode() {
        return currentSourceNode;
    }

    public void setCurrentSourceNode(Node currentSourceNode) {
        this.currentSourceNode = currentSourceNode;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public SimplifiedNode[] getSimplifiedNodes() {
        return simplifiedNodes;
    }

    public SimplifiedLine[] getSimplifiedLines() {
        return simplifiedLines;
    }
}

package sample.Game;

public class SimplifiedNode {
    private int index;
    private int[] nodesAroundIndexes;
    private Pawn pawnPlaced;
    private int lastSourceIndex;

    public SimplifiedNode(Node node) {
        index = node.getIndex();
        nodesAroundIndexes = node.getNodesAroundIndexes();
        pawnPlaced = node.getPawnPlaced();
        if(node.getLastSource() == null) {
            lastSourceIndex = -1;
        } else {
            lastSourceIndex = node.getLastSource().getIndex();
        }
    }

    public int getIndex() {
        return index;
    }

    public int[] getNodesAroundIndexes() {
        return nodesAroundIndexes;
    }

    public void setNodesAroundIndexes(int[] nodesAroundIndexes) {
        this.nodesAroundIndexes = nodesAroundIndexes;
    }

    public Pawn getPawnPlaced() {
        return pawnPlaced;
    }

    public void setPawnPlaced(Pawn pawnPlaced) {
        this.pawnPlaced = pawnPlaced;
    }

    public int getLastSourceIndex() {
        return lastSourceIndex;
    }

    public void setLastSourceIndex(int lastSourceIndex) {
        this.lastSourceIndex = lastSourceIndex;
    }
}

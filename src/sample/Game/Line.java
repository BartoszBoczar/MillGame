package sample.Game;

public class Line {
    private Node[] nodes;
    private boolean millUsed = true;
    private SimplifiedLine simplifiedSelf;

    public Line(Node[] nodes) {
        this.nodes = nodes;
    }

    public Line(Node firstNode, Node secondNode, Node thirdNode) {
        nodes = new Node[] {
                firstNode, secondNode, thirdNode
        };
    }

    public boolean hasMill() {
        return checkIfHasMill();
    }
    public boolean hasMill(Player player) {
        return checkIfHasMill() && nodes[0].getPawnPlaced() == player.getColor();
    }

    public boolean checkIfHasMill() {
        boolean result = false;
        if(nodes[0].getPawnPlaced() != Pawn.EMPTY) {
            if(nodes[0].getPawnPlaced() == nodes[1].getPawnPlaced() && nodes[1].getPawnPlaced() == nodes[2].getPawnPlaced()) {
                result = true;
            }
        }
        return result;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public boolean isMillUsed() {
        return millUsed;
    }

    public void setMillUsed(boolean millUsed) {
        this.millUsed = millUsed;
        simplifiedSelf.setMillUsed(millUsed);
    }

    public void resetIfNoMill() {
        if(!checkIfHasMill()) {
            millUsed = false;
        }
        simplifiedSelf.resetIfNoMill();
    }

    public void setSimplifiedSelf(SimplifiedLine simplifiedSelf) {
        this.simplifiedSelf = simplifiedSelf;
    }

    public SimplifiedLine getSimplifiedSelf() {
        return simplifiedSelf;
    }
}

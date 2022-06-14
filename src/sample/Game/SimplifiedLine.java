package sample.Game;

public class SimplifiedLine {
    private SimplifiedNode[] nodes;
    private boolean millUsed = true;

    public SimplifiedLine(Line line) {
        nodes = new SimplifiedNode[line.getNodes().length];
        for(int i = 0; i < line.getNodes().length; i++) {
            nodes[i] = line.getNodes()[i].getSimplifiedSelf();
        }
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

    public SimplifiedNode[] getNodes() {
        return nodes;
    }

    public boolean isMillUsed() {
        return millUsed;
    }

    public void setMillUsed(boolean millUsed) {
        this.millUsed = millUsed;
    }

    //Return true if reset was needed
    public boolean resetIfNoMill() {
        if(!checkIfHasMill() && millUsed) {
            millUsed = false;
            return true;
        }
        return false;
    }

    public boolean hasNode(SimplifiedNode nodeToCheck) {
        for(SimplifiedNode node : nodes) {
            if(nodeToCheck.getIndex() == node.getIndex()) {
                return true;
            }
        }
        return false;
    }
}

package sample.Game;

public class Move {
    private Node nodeSource;
    private Node nodeDestination;
    private Pawn pawnToPlace;

    public Move(Node nodeSource, Node nodeDestination, Pawn pawnToPlace) {
        this.nodeSource = nodeSource;
        this.nodeDestination = nodeDestination;
        this.pawnToPlace = pawnToPlace;
    }

    public Node getNodeSource() {
        return nodeSource;
    }

    public void setNodeSource(Node nodeSource) {
        this.nodeSource = nodeSource;
    }

    public Node getNodeDestination() {
        return nodeDestination;
    }

    public void setNodeDestination(Node nodeDestination) {
        this.nodeDestination = nodeDestination;
    }

    public Pawn getPawnToPlace() {
        return pawnToPlace;
    }

    public void setPawnToPlace(Pawn pawnToPlace) {
        this.pawnToPlace = pawnToPlace;
    }
}

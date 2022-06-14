package sample.Game;


import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Collection;

public class Node extends Button {
    private int index;
    private Pawn pawnPlaced;
    private int[] nodesAroundIndexes;
    private Circle circle;
    private Node lastSource;
    private SimplifiedNode simplifiedSelf;

    public Node(int index, Pawn pawnPlaced, int[] nodesAroundIndexes) {
        super();
        this.index = index;
        this.pawnPlaced = pawnPlaced;
        this.nodesAroundIndexes = nodesAroundIndexes;
    }

    public Node(int index, Pawn pawnPlaced) {
        super();
        this.index = index;
        this.pawnPlaced = pawnPlaced;
    }

    public int getIndex() {
        return index;
    }

    public Pawn getPawnPlaced() {
        return pawnPlaced;
    }

    public void setPawnPlaced(Pawn newPawnPlaced) {
        this.pawnPlaced = newPawnPlaced;
        simplifiedSelf.setPawnPlaced(pawnPlaced);
        if(newPawnPlaced == Pawn.EMPTY) {
            circle.setFill(Color.GRAY);
        } else if(newPawnPlaced == Pawn.WHITE) {
            circle.setFill(Color.WHITE);
        } else {
            circle.setFill(Color.BLACK);
        }
    }

    public int[] getNodesAroundIndexes() {
        return nodesAroundIndexes;
    }

    public void setNodesAroundIndexes(int[] nodesAroundIndexes) {
        this.nodesAroundIndexes = nodesAroundIndexes;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public boolean isNodeAround(Node node) {
        for(int i = 0; i < nodesAroundIndexes.length; i++) {
            if(node.index == nodesAroundIndexes[i]) {
                return true;
            }
        }
        return false;
    }

    public Node getLastSource() {
        return lastSource;
    }

    public void setLastSource(Node lastSource) {
        this.lastSource = lastSource;
        if(lastSource == null) {
            simplifiedSelf.setLastSourceIndex(-1);
        } else {
            simplifiedSelf.setLastSourceIndex(lastSource.getIndex());
        }
    }

    public SimplifiedNode getSimplifiedSelf() {
        return simplifiedSelf;
    }

    public void setSimplifiedSelf(SimplifiedNode simplifiedSelf) {
        this.simplifiedSelf = simplifiedSelf;
    }
}

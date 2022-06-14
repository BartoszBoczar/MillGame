package sample.Game;

public class ComputerChoice {
    private SimplifiedNode source;
    private SimplifiedNode destination;
    private Pawn pawnToPlace;
    private Pawn originalDestinationPawn;

    public ComputerChoice(SimplifiedNode source, SimplifiedNode destination, Pawn pawnToPlace, Pawn originalDestinationPawn) {
        this.source = source;
        this.destination = destination;
        this.pawnToPlace = pawnToPlace;
        this.originalDestinationPawn =originalDestinationPawn;
    }

    public SimplifiedNode getSource() {
        return source;
    }

    public void setSource(SimplifiedNode source) {
        this.source = source;
    }

    public SimplifiedNode getDestination() {
        return destination;
    }

    public void setDestination(SimplifiedNode destination) {
        this.destination = destination;
    }

    public Pawn getPawnToPlace() {
        return pawnToPlace;
    }

    public void setPawnToPlace(Pawn pawnToPlace) {
        this.pawnToPlace = pawnToPlace;
    }

    public Pawn getOriginalDestinationPawn() {
        return originalDestinationPawn;
    }

    public void setOriginalDestinationPawn(Pawn originalDestinationPawn) {
        this.originalDestinationPawn = originalDestinationPawn;
    }
}

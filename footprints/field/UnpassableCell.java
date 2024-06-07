package footprints.field;

import footprints.Player;

public class UnpassableCell extends AbstractCell {

    @Override
    public boolean hasPlayer() {
        return false;
    }

    @Override
    public boolean setPlayer(Player newPlayer) {
        return false;
    }

    @Override
    public Player extractPlayer() {
        return null;
    }

    @Override
    public Player getPlayer() {
        return null;
    }
}

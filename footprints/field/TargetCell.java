package footprints.field;

import footprints.Player;
import footprints.events.TargetCellActionEvent;
import footprints.events.TargetCellActionListener;

import java.util.ArrayList;
import java.util.List;

public class TargetCell extends AbstractCell {
    private final List<TargetCellActionListener> _listeners = new ArrayList<>();

    @Override
    public boolean setPlayer(Player newPlayer) {
        if (!super.setPlayer(newPlayer)) {
            return false;
        }

        firePlayerAtTargetCell(newPlayer);
        return true;
    }

    public void addTargetActionListener(TargetCellActionListener listener) {
        _listeners.add(listener);
    }

    public void removeTargetActionListener(TargetCellActionListener listener) {
        _listeners.remove(listener);
    }

    private void firePlayerAtTargetCell(Player player) {
        for (TargetCellActionListener listener: _listeners) {
            TargetCellActionEvent event = new TargetCellActionEvent(listener);
            event.setPlayer(player);
            listener.playerAtTargetCell(event);
        }
    }
}

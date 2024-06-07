package footprints;

import footprints.criterion.Criterion;
import footprints.events.*;
import footprints.field.Field;
import footprints.seeder.Seeder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Criterion _winCriterion;
    private final Criterion _loseCriterion;
    private final Field _field;
    private final GameObserver _gameObserver;
    private final List<GameActionListener> _listeners;
    private GameState _currentState;

    public Game(@NotNull Criterion winCriterion,
                @NotNull Criterion loseCriterion,
                @NotNull Seeder fieldSeeder) {
        _winCriterion = winCriterion;
        _loseCriterion = loseCriterion;
        _field = fieldSeeder.getField();
        _gameObserver = new GameObserver();
        _listeners = new ArrayList<>();
        _currentState = GameState.GAME_IS_ON;

        _field.getPlayer().addPlayerActionListener(_gameObserver);
        _field.getTargetCell().addTargetActionListener(_gameObserver);
    }

    public Player getPlayer() {
        return _field.getPlayer();
    }

    public GameState getGameState() {
        if (_loseCriterion.isSatisfied()) {
            return GameState.GAME_IS_LOST;
        }
        else if (_winCriterion.isSatisfied()) {
            return GameState.GAME_IS_WON;
        }
        else {
            return GameState.GAME_IS_ON;
        }
    }

    private void setGameState(GameState state) {
        if (state != _currentState) {
            _currentState = state;
            fireGameStateChanged();
        }
    }

    private String userMessage() {
        if (_loseCriterion.isSatisfied()) {
            return _loseCriterion.message();
        }
        else if (_winCriterion.isSatisfied()) {
            return _winCriterion.message();
        }

        return null;
    }

    private void fireGameStateChanged() {
        for (GameActionListener listener : _listeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setState(_currentState);
            event.setUserMessage(userMessage());
            listener.gameStatusChanged(event);
        }
    }

    public void addGameActionListener(GameActionListener listener) {
        _listeners.add(listener);
    }

    public void removeGameActionListener(GameActionListener listener) {
        _listeners.remove(listener);
    }

    private class GameObserver implements PlayerActionListener, TargetCellActionListener {

        @Override
        public void playerMoved(@NotNull PlayerActionEvent event) {
            setGameState(getGameState());
        }

        @Override
        public void playerGotKey(@NotNull PlayerActionEvent event) {
            setGameState(getGameState());
        }

        @Override
        public void playerAtTargetCell(@NotNull TargetCellActionEvent event) {
            setGameState(getGameState());
        }
    }
}

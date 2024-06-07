package footprints;

import footprints.criterion.*;
import footprints.field.*;
import footprints.items.Key;
import footprints.seeder.DifferentKeysSeeder;
import footprints.seeder.Seeder;
import footprints.seeder.SimpleSeeder;
import footprints.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {
        private Game _game;
        private Field _field;
        private Criterion _winCriterion;
        private Criterion _loseCriterion;

        public GamePanel() throws HeadlessException {
            gameInit();

            setVisible(true);

            JPanel content = (JPanel) this.getContentPane();
            FieldWidget fieldWidget = new FieldWidget(_field, new WidgetFactory());
            content.add(fieldWidget);
            setPreferredSize(fieldWidget.getSize());
            pack();

            setResizable(false);
            setFocusable(true);
            addKeyListener(new KeyController());
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JOptionPane.showMessageDialog(null,
                    "Чтобы победить: " + _winCriterion.message());
        }
//Новый порядок создания критериев
        private void gameInit() {
            // KeysCollectedCriterion allKeys = new KeysCollectedCriterion();
            PlayerOnTargetCellCriterion playerOnTargetCell = new PlayerOnTargetCellCriterion();
            CannotMoveAnywhereCriterion cannotMove = new CannotMoveAnywhereCriterion();
            SpecifiedKeysMustStayCriterion keysMustStay = new SpecifiedKeysMustStayCriterion();

            DifferentKeysSeeder seeder = new DifferentKeysSeeder();
            _field = seeder.getField();

            playerOnTargetCell.setPlayer(seeder.getPlayer());
            cannotMove.setPlayer(seeder.getPlayer());
            List<Key> keysOnField = seeder.getKeys();
            Key firstKey = keysOnField.get(0);
            Key secondKey = keysOnField.get(1);
            // Нужно, чтобы на поле остались красный и синий ключи (см. DifferentKeysSeeder)
            keysMustStay.setKeys(firstKey, secondKey);
            keysMustStay.setField(seeder.getField());
            keysMustStay.setPlayer(seeder.getPlayer());

            // Для проигрыша создаем дополнительный критерий, который проверяет,
            // что заданные ключи не были взяты
            KeyMustBeTakenCriterion redKeyTakenCriterion = new KeyMustBeTakenCriterion();
            redKeyTakenCriterion.setPlayer(seeder.getPlayer());
            redKeyTakenCriterion.setKey(firstKey);
            KeyMustBeTakenCriterion blueKeyTakenCriterion = new KeyMustBeTakenCriterion();
            blueKeyTakenCriterion.setPlayer(seeder.getPlayer());
            blueKeyTakenCriterion.setKey(secondKey);
            Criterion takeWrongKeysCriterion = new OrCriterion(redKeyTakenCriterion, blueKeyTakenCriterion);

            _winCriterion = new AndCriterion(keysMustStay, playerOnTargetCell);
            _loseCriterion = new OrCriterion(cannotMove, takeWrongKeysCriterion);

            _game = new Game(_winCriterion, _loseCriterion, seeder);
        }

        private class KeyController implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                if (code == KeyEvent.VK_Q) { // Вверх влево
                    _field.getPlayer().move(Direction.NORTHWEST);
                } else if (code == KeyEvent.VK_W) { // Вверх
                    _field.getPlayer().move(Direction.NORTH);
                } else if (code == KeyEvent.VK_E) { // Вверх вправо
                    _field.getPlayer().move(Direction.NORTHEAST);
                } else if (code == KeyEvent.VK_A) { // Вниз влево
                    _field.getPlayer().move(Direction.SOUTHWEST);
                } else if (code == KeyEvent.VK_S) { // Вниз
                    _field.getPlayer().move(Direction.SOUTH);
                } else if (code == KeyEvent.VK_D) { // Вниз вправо
                    _field.getPlayer().move(Direction.SOUTHEAST);
                }

                repaint();

                if (_game.getGameState() == GameState.GAME_IS_WON || _game.getGameState() == GameState.GAME_IS_LOST) {
                    JOptionPane.showMessageDialog(null,
                            _game.getGameState() == GameState.GAME_IS_WON ? "Победа" : "Поражение");
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        }
    }
}
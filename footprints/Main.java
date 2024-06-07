package footprints;

import footprints.criterion.*;
import footprints.field.*;
import footprints.seeder.Seeder;
import footprints.seeder.SimpleSeeder;
import footprints.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

        private void gameInit() {
            KeysCollectedCriterion allKeys = new KeysCollectedCriterion();
            PlayerOnTargetCellCriterion playerOnTargetCell = new PlayerOnTargetCellCriterion();
            CannotMoveAnywhereCriterion cannotMove = new CannotMoveAnywhereCriterion();

            _winCriterion = new AndCriterion(allKeys, playerOnTargetCell);
            _loseCriterion = cannotMove;

            Seeder seeder = new SimpleSeeder();
            _game = new Game(_winCriterion, _loseCriterion, seeder);
            _field = seeder.getField();

            allKeys.setPlayer(_game.getPlayer());
            allKeys.setKeysCount(seeder.getField().getRemainingKeys().size());
            playerOnTargetCell.setPlayer(_game.getPlayer());
            cannotMove.setPlayer(_game.getPlayer());
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
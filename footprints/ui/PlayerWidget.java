package footprints.ui;

import footprints.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerWidget extends CellItemWidget {

    private final Color _playerColor;

    public PlayerWidget(Color playerColor) {
        _playerColor = playerColor;
    }

    public Color getPlayerColor() {
        return _playerColor;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getPlayerImageFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected Dimension getSize() {
        return new Dimension(40, 40);
    }

    private File getPlayerImageFile() {
        return new File("src\\player.png");
    }

}

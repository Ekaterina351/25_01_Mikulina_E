package footprints.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class KeyWidget extends CellItemWidget {

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getKeyImageFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected Dimension getSize() {
        return new Dimension(40, 40);
    }

    protected File getKeyImageFile() {
        return new File("src\\key.png");
    }

}

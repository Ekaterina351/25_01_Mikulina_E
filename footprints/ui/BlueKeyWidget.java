package footprints.ui;

import java.io.File;

public class BlueKeyWidget extends KeyWidget {

    @Override
    protected File getKeyImageFile() {
        return new File("src\\blue_key.png");
    }
}

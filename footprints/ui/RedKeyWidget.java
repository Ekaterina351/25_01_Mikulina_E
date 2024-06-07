package footprints.ui;

import java.io.File;

public class RedKeyWidget extends KeyWidget {

    @Override
    protected File getKeyImageFile() {
        return new File("src\\red_key.png");
    }
}

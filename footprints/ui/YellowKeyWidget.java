package footprints.ui;

import java.io.File;

public class YellowKeyWidget extends KeyWidget {

    @Override
    protected File getKeyImageFile() {
        return new File("src\\yellow_key.png");
    }
}

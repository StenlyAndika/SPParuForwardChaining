package styles;

import javafx.scene.control.TextField;

/**
 * Created by pedro_000 on 12/5/13.
 */
public class UIMetroTextFieldSkin extends UITextFieldWithButtonSkin{
    public UIMetroTextFieldSkin(TextField textField) {
        super(textField);
    }

    @Override
    protected void rightButtonPressed()
    {
        getSkinnable().setText("");
    }

}
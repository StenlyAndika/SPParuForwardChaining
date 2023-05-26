package styles;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by pedro_000 on 12/15/13.
 */
public class UIMetroPasswordFieldSkin extends UITextFieldWithButtonSkin{
    private boolean shouldMaskText = true;

    public UIMetroPasswordFieldSkin(TextField textField) {
        super(textField);
    }

    @Override
    protected void rightButtonPressed() {
        TextField tField = getSkinnable();
        shouldMaskText = false;
        textField.setText(textField.getText());
        shouldMaskText = true;
    }

    @Override
    protected  void rightButtonReleased()
    {
        TextField tField = getSkinnable();
        textField.setText(textField.getText());
        textField.end();
    }

    @Override protected String maskText(String txt) {
        if (getSkinnable() instanceof PasswordField && shouldMaskText) {
            int n = txt.length();
            StringBuilder passwordBuilder = new StringBuilder(n);
            for (int i = 0; i < n; i++) {
                passwordBuilder.append(BULLET);
            }

            return passwordBuilder.toString();
        } else {
            return txt;
        }
    }
}

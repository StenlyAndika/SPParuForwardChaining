package styles;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

@SuppressWarnings("deprecation")
public class AnimFadeInLeftTransition extends AnimconfigAnimasi {
    /**
     * Create new FadeInLeftTransition
     * 
     * @param node The node to affect
     */
    public AnimFadeInLeftTransition(final Node node) {
        super(
            node,
            TimelineBuilder.create()
                .keyFrames(
                    new KeyFrame(Duration.millis(0),    
                        new KeyValue(node.opacityProperty(), 0, WEB_EASE),
                        new KeyValue(node.translateXProperty(), -20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(1200),    
                        new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(node.translateXProperty(), 0, WEB_EASE)
                    )
                )
                .build()
            );
        setCycleDuration(Duration.seconds(2));
        setDelay(Duration.seconds(0));
    }
}

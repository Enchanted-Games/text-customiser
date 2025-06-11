package games.enchanted.eg_text_customiser.common.duck;

import games.enchanted.eg_text_customiser.common.fake_style.SignTextData;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;

public interface StyleAdditions {
    Style eg_text_customiser$withSignTextData(SignTextData signTextData);
    void eg_text_customiser$setSignTextData(SignTextData signTextData);
    @Nullable SignTextData eg_text_customiser$getSignTextData();
}

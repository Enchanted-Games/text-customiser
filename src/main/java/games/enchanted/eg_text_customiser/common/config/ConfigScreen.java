package games.enchanted.eg_text_customiser.common.config;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class ConfigScreen extends Screen {
    private static final Component TITLE = Component.translatableWithFallback("gui.eg_text_customiser.config.title", "Text Customiser Config").withStyle(Style.EMPTY.withBold(true));

    private final HeaderAndFooterLayout headerAndFooterLayout = new HeaderAndFooterLayout(this);
    private final LinearLayout contentsFlow = LinearLayout.vertical().spacing(8);
    private final Screen parent;

    protected ConfigScreen(Screen parent) {
        super(TITLE);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        this.headerAndFooterLayout.addTitleHeader(TITLE, this.font);
        this.headerAndFooterLayout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (widget) -> this.onClose()).width(200).build());


        this.headerAndFooterLayout.visitWidgets(this::addRenderableWidget);
        this.contentsFlow.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.headerAndFooterLayout.arrangeElements();

        this.contentsFlow.setY(64);
        this.contentsFlow.setX((this.width / 2) - (contentsFlow.getWidth() / 2));
        this.contentsFlow.arrangeElements();
    }

    @Override
    public void onClose() {
        super.onClose();
        assert this.minecraft != null;
        this.minecraft.setScreen(parent);
    }

    public static Screen createConfigScreen(Screen parent) {
        return new ConfigScreen(parent);
    }
}

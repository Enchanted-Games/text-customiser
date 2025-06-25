package games.enchanted.eg_text_customiser.common.config;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.ModConstants;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.net.URI;

public class ConfigScreen extends Screen {
    private static final Component TITLE = Component.translatableWithFallback("gui.eg_text_customiser.config.title", "Text Customiser Config").withStyle(Style.EMPTY.withBold(true));
    private static final String TOGGLE_DEBUG_LOGS_KEY = "gui.eg_text_customiser.button.debug_logs";
    private static final Component DEBUG_LOGS_TOGGLED_ON = Component.translatable(TOGGLE_DEBUG_LOGS_KEY, CommonComponents.OPTION_ON);
    private static final Component DEBUG_LOGS_TOGGLED_OFF = Component.translatable(TOGGLE_DEBUG_LOGS_KEY, CommonComponents.OPTION_OFF);
    private static final String WIKI_LINK_KEY = "gui.eg_text_customiser.button.wiki_link";
    private static final Component WIKI_LINK = Component.translatableWithFallback(WIKI_LINK_KEY, "Read the Wiki");

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
        this.contentsFlow.setY(64);

        addConfigOptions();

        this.headerAndFooterLayout.visitWidgets(this::addRenderableWidget);
        this.contentsFlow.visitWidgets(this::addRenderableWidget);

        this.repositionElements();
    }

    protected void addConfigOptions() {
        contentsFlow.addChild(
            Button.builder(getToggledComponent(DEBUG_LOGS_TOGGLED_ON, DEBUG_LOGS_TOGGLED_OFF, ConfigValues.TEXT_DEBUG_LOGS), (widget) -> {
                widget.setMessage(getToggledComponent(DEBUG_LOGS_TOGGLED_ON, DEBUG_LOGS_TOGGLED_OFF, !ConfigValues.TEXT_DEBUG_LOGS));
                ConfigValues.TEXT_DEBUG_LOGS = !ConfigValues.TEXT_DEBUG_LOGS;
                Logging.info("Text style logging turned {}", ConfigValues.TEXT_DEBUG_LOGS ? "on" : "off");
            })
            .tooltip(Tooltip.create(Component.translatable(TOGGLE_DEBUG_LOGS_KEY + ".tooltip")))
            .bounds(this.width / 2 - (Button.BIG_WIDTH / 2), this.height / 2 - 20 - (Button.DEFAULT_HEIGHT + 4), Button.BIG_WIDTH, Button.DEFAULT_HEIGHT)
            .build()
        );

        contentsFlow.addChild(
            Button.builder(WIKI_LINK, ConfirmLinkScreen.confirmLink(this, URI.create(ModConstants.WIKI_LINK)))
            .tooltip(Tooltip.create(Component.translatable(WIKI_LINK_KEY + ".tooltip")))
            .bounds(this.width / 2 - (Button.BIG_WIDTH / 2), this.height / 2 - 20, Button.BIG_WIDTH, Button.DEFAULT_HEIGHT)
            .build()
        );

        this.repositionElements();
    }

    public static Component getToggledComponent(Component first, Component second, boolean toggle) {
        return toggle ? first : second;
    }

    @Override
    protected void repositionElements() {
        this.headerAndFooterLayout.arrangeElements();

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

package me.desht.modularrouters.client.gui.widgets.textfield;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.TextComponent;

public class TextFieldWidgetMR extends EditBox {
    private final TextFieldManager manager;
    private final int ordinal;  // order in which this field appears in the textfield manager

    public TextFieldWidgetMR(TextFieldManager manager, Font fontrendererObj, int x, int y, int width, int height) {
        super(fontrendererObj, x, y, width, height, TextComponent.EMPTY);

        this.manager = manager;
        this.ordinal = manager.addTextField(this);
    }

    @Override
    public void setFocused(boolean isFocusedIn) {
        super.setFocused(isFocusedIn);
        manager.onTextFieldFocusChange(ordinal, isFocusedIn);
    }

    /**
     * Convenience method
     */
    public void useGuiTextBackground() {
        setTextColor(0xffffffff);
        setTextColorUneditable(0xffffffff);
        setBordered(false);
    }

    public void onMouseWheel(int direction) {}

    /**
     * Get the order in which this field appears in the text field manager.
     *
     * @return the order
     */
    public int getOrdinal() {
        return ordinal;
    }
}

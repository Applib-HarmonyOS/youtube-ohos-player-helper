package com.jaedongchicken.ytplayer_sample;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

public class Util {

    public static ToastDialog createToast(Context context, String str) {
        Text text = new Text(context);
        text.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        text.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        text.setTextSize(48);
        text.setText(str);
        text.setMultipleLine(true);
        text.setTextAlignment(TextAlignment.CENTER);
        ShapeElement shapeElement = (ShapeElement) buildDrawableByColor(Color.WHITE.getValue());
        text.setBackground(shapeElement);
        DirectionalLayout directionalLayout = new DirectionalLayout(context);
        directionalLayout.setBackground(shapeElement);
        DirectionalLayout.LayoutConfig layoutConfig = new DirectionalLayout.LayoutConfig
                (DirectionalLayout.LayoutConfig.MATCH_PARENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT);
        layoutConfig.setMarginBottom(100);
        directionalLayout.setLayoutConfig(layoutConfig);
        directionalLayout.setPadding(20, 30, 20, 30);
        directionalLayout.addComponent(text);
        ToastDialog toastDialog = new ToastDialog(context);
        toastDialog.setComponent(directionalLayout);
        toastDialog.setAlignment(LayoutAlignment.BOTTOM).setTransparent(true);
        return toastDialog;
    }

    public static Element buildDrawableByColor(int color) {
        ShapeElement drawable = new ShapeElement();
        drawable.setShape(ShapeElement.RECTANGLE);
        drawable.setRgbColor(RgbColor.fromArgbInt(color));
        return drawable;
    }
}

package dev.ecr.actionfinder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

@Mod(modid = "actionfinder", useMetadata=true)
public class ActionFinder {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void newChatMessage(ClientChatReceivedEvent event) {
        this.checkComponents(event.message);
    }

    @SubscribeEvent
    public void newGuiOpened(GuiOpenEvent event) {
        if(event.gui instanceof GuiScreenBook) {
            final Gson gson = new Gson();
            final GuiScreenBook bookGui = (GuiScreenBook) event.gui;
            try {
                Field field = GuiScreenBook.class.getDeclaredField("bookPages");
                field.setAccessible(true);
                NBTTagList pages = (NBTTagList) field.get(bookGui);

                for (int i = 0; i < pages.tagCount(); i++) {
                    String pageText = pages.getStringTagAt(i);
                    JsonElement[] lines = gson.fromJson(pageText, JsonElement[].class);
                    for(JsonElement line : lines) {
                        if(line.isJsonObject()) {
                            IChatComponent lineComponent = IChatComponent.Serializer.jsonToComponent(gson.toJson(line));
                            this.checkComponents(lineComponent);
                        }
                    }

                }
            } catch(NoSuchFieldException | IllegalAccessException e) {
                System.out.println("ERROR: Failed to get components from book");
                e.printStackTrace();
            }
        }
    }

    private void checkComponents(IChatComponent root) {
        for(IChatComponent component : root) {
            ChatStyle style = component.getChatStyle();
            ClickEvent clickEvent = style.getChatClickEvent();
            if(clickEvent != null) {
                this.clickEventDetected(clickEvent);
            }
        }
    }

    private void clickEventDetected(ClickEvent event) {
        System.out.println("Click event detected");
        System.out.println(event.getAction().getCanonicalName());
        System.out.println(event.getValue());
    }
}

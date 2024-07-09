/*
 * Copyright 2024 NotRyken
 * SPDX-License-Identifier: Apache-2.0
 */

package dev.terminalmc.clientsort.screen;

import dev.terminalmc.clientsort.config.Config;
import dev.terminalmc.clientsort.inventory.sort.SortMode;
import dev.terminalmc.clientsort.util.CreativeSearchOrder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Optional;

import static dev.terminalmc.clientsort.util.mod.Localization.localized;

public class ClothConfigScreenProvider {
    /**
     * Builds and returns a Cloth Config options screen.
     * @param parent the current screen.
     * @return a new options {@link Screen}.
     * @throws NoClassDefFoundError if the Cloth Config API mod is not
     * available.
     */
    static Screen getConfigScreen(Screen parent) {
        Config.Options options = Config.get().options;

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(localized("screen", "options"))
                .setSavingRunnable(Config::getAndSave);

        ConfigEntryBuilder eb = builder.entryBuilder();


        ConfigCategory general = builder.getOrCreateCategory(localized("option", "general"));

        general.addEntry(eb.startIntField(localized("option", "interaction_rate_server"),
                        options.interactionRateServer)
                .setTooltip(localized("option", "interaction_rate.tooltip"))
                .setErrorSupplier(val -> {
                    if (val < 1) return Optional.of(
                            localized("option", "error.low"));
                    else if (val > 100) return Optional.of(
                            localized("option", "error.high"));
                    else return Optional.empty();
                })
                .setDefaultValue(Config.Options.defaultInteractionRateServer)
                .setSaveConsumer(val -> options.interactionRateServer = val)
                .build());

        general.addEntry(eb.startIntField(localized("option", "interaction_rate_client"),
                        options.interactionRateClient)
                .setTooltip(localized("option", "interaction_rate.tooltip"))
                .setErrorSupplier(val -> {
                    if (val < 1) return Optional.of(
                            localized("option", "error.low"));
                    else if (val > 100) return Optional.of(
                            localized("option", "error.high"));
                    else return Optional.empty();
                })
                .setDefaultValue(Config.Options.defaultInteractionRateClient)
                .setSaveConsumer(val -> options.interactionRateClient = val)
                .build());

        general.addEntry(eb.startEnumSelector(localized("option", "hotbar_mode"),
                        Config.Options.HotbarMode.class, options.hotbarMode)
                .setEnumNameProvider(val -> localized("hotbar_mode",
                        ((Config.Options.HotbarMode)val).lowerName()))
                .setTooltipSupplier(val -> Optional.of(new Component[]{
                        localized("hotbar_mode", val.lowerName() + ".tooltip")
                }))
                .setDefaultValue(Config.Options.defaultHotbarMode)
                .setSaveConsumer(val -> options.hotbarMode = val)
                .build());

        ConfigCategory sort = builder.getOrCreateCategory(localized("option", "sorting"));

        sort.addEntry(eb.startSelector(localized("option", "sort_mode"),
                        SortMode.SORT_MODES.keySet().toArray(), options.sortModeStr)
                .setNameProvider(val -> localized("sort_order", (String) val))
                .setDefaultValue(Config.Options.defaultSortMode)
                .setSaveConsumer(val -> options.sortModeStr = (String) val)
                .build());

        sort.addEntry(eb.startSelector(localized("option", "shift_sort_mode"),
                        SortMode.SORT_MODES.keySet().toArray(), options.shiftSortModeStr)
                .setNameProvider(val -> localized("sort_order", (String) val))
                .setDefaultValue(Config.Options.defaultShiftSortMode)
                .setSaveConsumer(val -> options.shiftSortModeStr = (String) val)
                .build());

        sort.addEntry(eb.startSelector(localized("option", "ctrl_sort_mode"),
                        SortMode.SORT_MODES.keySet().toArray(), options.ctrlSortModeStr)
                .setNameProvider(val -> localized("sort_order", (String) val))
                .setDefaultValue(Config.Options.defaultCtrlSortMode)
                .setSaveConsumer(val -> options.ctrlSortModeStr = (String) val)
                .build());

        sort.addEntry(eb.startSelector(localized("option", "alt_sort_mode"),
                        SortMode.SORT_MODES.keySet().toArray(), options.altSortModeStr)
                .setNameProvider(val -> localized("sort_order", (String) val))
                .setDefaultValue(Config.Options.defaultAltSortMode)
                .setSaveConsumer(val -> options.altSortModeStr = (String) val)
                .build());

        sort.addEntry(eb.startBooleanToggle(localized("option", "optimized_creative_sorting"),
                        options.optimizedCreativeSorting)
                .setDefaultValue(Config.Options.defaultOptimizedCreativeSorting)
                .setSaveConsumer(val -> {
                    options.optimizedCreativeSorting = val;
                    if (val) CreativeSearchOrder.tryRefreshItemSearchPositionLookup();
                })
                .build());

        return builder.build();
    }
}

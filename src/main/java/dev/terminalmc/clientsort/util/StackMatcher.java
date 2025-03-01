/*
 * Copyright 2020-2022 Siphalor
 * Copyright 2024 NotRyken
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package dev.terminalmc.clientsort.util;

import com.google.common.base.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: This appears to work but needs more testing.
public class StackMatcher {
	private final @NotNull Item item;
	private final @Nullable CompoundTag tag;

	private StackMatcher(@NotNull Item item, @Nullable CompoundTag tag) {
		this.item = item;
		this.tag = tag;
	}

	public static StackMatcher ignoreNbt(@NotNull ItemStack stack) {
		return new StackMatcher(stack.getItem(), null);
	}

	public static StackMatcher of(@NotNull ItemStack stack) {
		return new StackMatcher(stack.getItem(), stack.getTag());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StackMatcher matcher) {
			return ItemStack.isSameItemSameTags(item.getDefaultInstance(), matcher.item.getDefaultInstance());
		}
		else if (obj instanceof ItemStack stack) {
			return ItemStack.isSameItem(item.getDefaultInstance(), stack);
		}
		else if (obj instanceof Item objItem) {
			return item == objItem;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(item, tag);
	}
}

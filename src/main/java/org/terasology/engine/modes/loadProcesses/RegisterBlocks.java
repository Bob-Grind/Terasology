/*
 * Copyright 2013 Moving Blocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.engine.modes.loadProcesses;

import org.terasology.engine.CoreRegistry;
import org.terasology.engine.modes.LoadProcess;
import org.terasology.network.NetworkSystem;
import org.terasology.world.WorldInfo;
import org.terasology.world.block.family.BlockFamilyFactoryRegistry;
import org.terasology.world.block.management.BlockManager;
import org.terasology.world.block.management.BlockManagerImpl;

/**
 * @author Immortius
 */
public class RegisterBlocks implements LoadProcess {

    private WorldInfo worldInfo;

    public RegisterBlocks(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
    }

    @Override
    public String getMessage() {
        return "Registering Blocks...";
    }

    @Override
    public boolean step() {
        NetworkSystem networkSystem = CoreRegistry.get(NetworkSystem.class);
        BlockManagerImpl blockManager;
        if (networkSystem.getMode().isAuthority()) {
            blockManager = new BlockManagerImpl(worldInfo.getRegisteredBlockFamilies(), worldInfo.getBlockIdMap(), true, CoreRegistry.get(BlockFamilyFactoryRegistry.class));
            blockManager.subscribe(CoreRegistry.get(NetworkSystem.class));
        } else {
            blockManager = new BlockManagerImpl(worldInfo.getRegisteredBlockFamilies(), worldInfo.getBlockIdMap(), false, CoreRegistry.get(BlockFamilyFactoryRegistry.class));
        }
        blockManager.buildAtlas();
        CoreRegistry.put(BlockManager.class, blockManager);

        return true;
    }

    @Override
    public int begin() {
        return 1;
    }

}
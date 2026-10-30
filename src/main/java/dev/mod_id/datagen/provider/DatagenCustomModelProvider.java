package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.datagen.builder.DatagenCustomModelBuilder;
import dev.mod_id.datagen.model.ClusterModelWriter;
import dev.mod_id.datagen.model.LargeBudModelWriter;
import dev.mod_id.datagen.model.MediumBudModelWriter;
import dev.mod_id.datagen.model.SmallBudModelWriter;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

public final class DatagenCustomModelProvider extends DatagenCustomModelBuilder {

    public DatagenCustomModelProvider(FabricPackOutput output) {

        add(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD, new SmallBudModelWriter(output));
        add(RegisterBlocks.MEDIUM_ROSE_QUARTZ_BUD, new MediumBudModelWriter(output));
        add(RegisterBlocks.LARGE_ROSE_QUARTZ_BUD, new LargeBudModelWriter(output));

        add(RegisterBlocks.ROSE_QUARTZ_CLUSTER, new ClusterModelWriter(output));
    }
}

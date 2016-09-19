package org.ow2.proactive.procci.model.occi.infrastructure;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;

import java.util.ArrayList;
import java.util.List;

import static org.ow2.proactive.procci.model.ModelConstant.*;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.*;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.SUMMARY_NAME;

/**
 * Created by mael on 02/06/16.
 */
@EqualsAndHashCode
@ToString
public class ComputeBuilder {
    @Getter(AccessLevel.PACKAGE)
    private String url;
    @Getter
    private String title;
    @Getter
    private String summary;
    @Getter
    private Compute.Architecture architecture;
    @Getter
    private Integer cores;
    @Getter(AccessLevel.PACKAGE)
    private Integer share;
    @Getter
    private Float memory; // in Gigabytes
    @Getter(AccessLevel.PACKAGE)
    private String hostname;
    @Getter(AccessLevel.PACKAGE)
    private ComputeState state;
    private List<Link> links;
    private List<Mixin> mixins;

    public ComputeBuilder() {
        this.url = "";
        title = "Compute";
        summary = "";
        architecture = null;
        cores = null;
        share = null;
        hostname = "";
        memory = null;
        state = ComputeState.ERROR;
        mixins = new ArrayList<>();
        links = new ArrayList<>();
    }

    public ComputeBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ComputeBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ComputeBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }


    public ComputeBuilder addMixin(Mixin mixin) {
        this.mixins.add(mixin);
        return this;
    }

    public ComputeBuilder addLink(Link link) {
        this.links.add(link);
        return this;
    }

    public ComputeBuilder architecture(Compute.Architecture architecture) {
        this.architecture = architecture;
        return this;
    }

    public ComputeBuilder architecture(String architecture) {
        if (Compute.Architecture.X64.toString().equalsIgnoreCase(architecture)) {
            this.architecture = Compute.Architecture.X64;
        } else if (Compute.Architecture.X86.toString().equalsIgnoreCase(architecture)) {
            this.architecture = Compute.Architecture.X86;
        }
        return this;
    }

    public ComputeBuilder cores(Integer cores) {
        this.cores = cores;
        return this;
    }

    public ComputeBuilder cores(String cores) {
        if (cores != null && (!cores.isEmpty())) {
            this.cores = Integer.parseInt(cores);
        }
        return this;
    }

    public ComputeBuilder share(Integer share) {
        this.share = share;
        return this;
    }

    public ComputeBuilder share(String share) {
        if (share != null && (!share.isEmpty())) {
            this.share = Integer.parseInt(share);
        }
        return this;
    }

    public ComputeBuilder hostame(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ComputeBuilder memory(Float memory) {
        this.memory = memory;
        return this;
    }

    public ComputeBuilder memory(String memory) {
        if ((memory != null && !memory.isEmpty())) {
            this.memory = Float.parseFloat(memory);
        }
        return this;
    }

    public ComputeBuilder state(ComputeState state) {
        this.state = state;
        return this;
    }

    public ComputeBuilder state(String state) {
        switch (state) {
            case RUNNING_STATE:
                this.state = ComputeState.ACTIVE;
                break;
            case STOPPED_STATE:
                this.state = ComputeState.SUSPENDED;
                break;
            case PENDING_STATE:
                this.state = ComputeState.INACTIVE;
                break;
            case TERMINATED_STATE:
                this.state = ComputeState.INACTIVE;
                break;
            case ERROR_STATE:
                this.state = ComputeState.ERROR;
                break;
        }
        return this;
    }

    /**
     *  Set the builder according to the cloud automation model information
     * @param cloudAutomation is the instance of the cloud automation model for a compute
     * @return a compute builder with the cloud automation model information mapped into the builder
     */
    public ComputeBuilder update(Model cloudAutomation) {
        this.url(cloudAutomation.getVariables().getOrDefault(INSTANCE_ID, ""))
                .title(cloudAutomation.getVariables().getOrDefault(ENTITY_TITLE_NAME, ""))
                .architecture(cloudAutomation.getVariables().getOrDefault(ARCHITECTURE_NAME,null))
                .state(cloudAutomation.getVariables().getOrDefault(INSTANCE_STATUS, ""))
                .hostame(cloudAutomation.getVariables().getOrDefault(INSTANCE_ENDPOINT, ""))
                .cores(cloudAutomation.getVariables().getOrDefault(CORES_NAME, ""))
                .memory(cloudAutomation.getVariables().getOrDefault(MEMORY_NAME, ""))
                .share(cloudAutomation.getVariables().getOrDefault(SHARE_NAME, ""))
                .summary(cloudAutomation.getVariables().getOrDefault(SUMMARY_NAME, ""));

        return this;
    }

    /**
     *  Set the builder according to the resource rendering information
     * @param rendering is the instance of the cloud automation model for a compute
     * @return a compute builder with the cloud automation model information mapped into the builder
     */
    public ComputeBuilder update(ResourceRendering rendering) {
        this.url(rendering.getId())
                .title((String) rendering.getAttributes().get(ENTITY_TITLE_NAME))
                .architecture((Compute.Architecture) rendering.getAttributes().getOrDefault(architecture,null))
                .state((String) rendering.getAttributes().getOrDefault(COMPUTE_STATE_NAME,""))
                .hostame((String) rendering.getAttributes().getOrDefault(HOSTNAME_NAME,""))
                .cores((String) rendering.getAttributes().getOrDefault(CORES_NAME,""))
                .memory((String) rendering.getAttributes().getOrDefault(MEMORY_NAME,""))
                .share((String) rendering.getAttributes().getOrDefault(SHARE_NAME,""))
                .summary((String) rendering.getAttributes().getOrDefault(SUMMARY_NAME, ""));

        return this;
    }




    public Compute build() {
        return new Compute(url, InfrastructureKinds.COMPUTE, title, new ArrayList<>(), summary, new ArrayList<>(), architecture,
                cores, share, hostname, memory, state);
    }


}

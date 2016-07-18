package org.ow2.proactive.procci.model.occi.infrastructure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mael on 02/06/16.
 */
@EqualsAndHashCode
@ToString
public class ComputeBuilder {

    private String url;
    @Getter
    private String title;
    private String summary;
    @Getter
    private Compute.Architecture architecture;
    @Getter
    private Integer cores;
    private Integer share;
    @Getter
    private Float memory; // in Gigabytes
    private String hostname;
    private ComputeState state;
    private List<Link> links;
    private List<Mixin> mixins;

    public ComputeBuilder() {
        this.url = "";
        title = "";
        summary = "";
        architecture = null;
        cores = null;
        share = null;
        hostname = "";
        memory = null;
        state = null;
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
        if (architecture.equals(Compute.Architecture.X64.toString())) {
            this.architecture = Compute.Architecture.X64;
        } else if (architecture.equals(Compute.Architecture.X86.toString())) {
            this.architecture = Compute.Architecture.X86;
        }
        return this;
    }

    public ComputeBuilder cores(Integer cores) {
        this.cores = cores;
        return this;
    }

    public ComputeBuilder cores(String cores) {
        this.cores = Integer.parseInt(cores);
        return this;
    }

    public ComputeBuilder share(Integer share) {
        this.share = share;
        return this;
    }

    public ComputeBuilder share(String share) {
        this.share = Integer.parseInt(share);
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
        this.memory = Float.parseFloat(memory);
        return this;
    }

    public ComputeBuilder state(ComputeState state) {
        this.state = state;
        return this;
    }

    public ComputeBuilder state(String state) {
        switch (state) {
            case "RUNNING":
                this.state = ComputeState.ACTIVE;
                break;
            case "STOPPED":
                this.state = ComputeState.SUSPENDED;
                break;
            case "PENDING":
                this.state = ComputeState.INACTIVE;
                break;
            case "TERMINATED":
                this.state = ComputeState.INACTIVE;
                break;
            case "ERROR":
                this.state = ComputeState.ERROR;
                break;
        }
        return this;
    }

    public ComputeBuilder update(Model pca) {
        title = pca.getVariables().get("name");
        architecture = Compute.Architecture.getArchitecture(pca.getVariables().get("architecture"));
        String cores = pca.getVariables().get("cores");
        if (cores != null && (!cores.isEmpty())) {
            this.cores = Integer.parseInt(cores);
        }

        String memory = pca.getVariables().get("memory");
        if ((memory != null && !memory.isEmpty())) {
            this.memory = Float.parseFloat(memory);
        }

        return this;
    }

    public Compute build() {
        return new Compute(url, InfrastructureKinds.COMPUTE, title, new ArrayList<>(), summary, new ArrayList<>(), architecture,
                cores, share, hostname, memory, state);
    }


}

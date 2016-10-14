package org.ow2.proactive.procci.model.occi.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static org.ow2.proactive.procci.model.ModelConstant.ERROR_STATE;
import static org.ow2.proactive.procci.model.ModelConstant.INSTANCE_ENDPOINT;
import static org.ow2.proactive.procci.model.ModelConstant.INSTANCE_STATUS;
import static org.ow2.proactive.procci.model.ModelConstant.PENDING_STATE;
import static org.ow2.proactive.procci.model.ModelConstant.RUNNING_STATE;
import static org.ow2.proactive.procci.model.ModelConstant.STOPPED_STATE;
import static org.ow2.proactive.procci.model.ModelConstant.TERMINATED_STATE;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.ARCHITECTURE_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.COMPUTE_STATE_ACTIVE;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.COMPUTE_STATE_ERROR;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.COMPUTE_STATE_INACTIVE;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.COMPUTE_STATE_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.COMPUTE_STATE_SUSPENDED;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.CORES_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.HOSTNAME_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.MEMORY_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.SHARE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ID_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.SUMMARY_NAME;

/**
 * Compute Builder class, enable to easily construct a Compute from RenderingCompute or Cloud Automation Model
 */
@EqualsAndHashCode
@ToString
@Getter
public class ComputeBuilder {
    private Optional<String> url;
    private Optional<String> title;
    private Optional<String> summary;
    private Optional<Compute.Architecture> architecture;
    private Optional<Integer> cores;
    private Optional<Integer> share;
    private Optional<Float> memory; // in Gigabytes
    private Optional<String> hostname;
    private Optional<ComputeState> state;
    private List<Link> links;
    private List<Mixin> mixins;

    /**
     * Default Builder
     */
    public ComputeBuilder() {
        this.url = null;
        this.title = null;
        this.summary = null;
        this.architecture = null;
        this.cores = null;
        this.share = null;
        this.hostname = null;
        this.memory = null;
        this.state = null;
        this.mixins = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    /**
     * Set the builder according to the cloud automation model information
     *
     * @param cloudAutomation is the instance of the cloud automation model for a compute
     */
    public ComputeBuilder(Model cloudAutomation) throws SyntaxException {
        this.url = Optional.ofNullable(cloudAutomation.getVariables().getOrDefault(ID_NAME, null));
        this.title = Optional.ofNullable(
                cloudAutomation.getVariables().getOrDefault(ENTITY_TITLE_NAME, null));
        this.hostname = Optional.ofNullable(
                cloudAutomation.getVariables().getOrDefault(INSTANCE_ENDPOINT, null));
        this.summary = Optional.ofNullable(cloudAutomation.getVariables().getOrDefault(SUMMARY_NAME, null));
        this.cores = ConvertUtils.getIntegerFromString(
                Optional.ofNullable(cloudAutomation.getVariables().getOrDefault(CORES_NAME, null)));
        this.memory = ConvertUtils.getFloatFromString(
                Optional.ofNullable(cloudAutomation.getVariables().getOrDefault(MEMORY_NAME, null)));
        this.share = ConvertUtils.getIntegerFromString(
                Optional.ofNullable(cloudAutomation.getVariables().getOrDefault(SHARE_NAME, null)));
        this.architecture = getArchitectureFromString(
                Optional.ofNullable(cloudAutomation.getVariables().getOrDefault(ARCHITECTURE_NAME, null)));
        this.state = getStateFromCloudAutomation(
                Optional.ofNullable(cloudAutomation.getVariables().getOrDefault(INSTANCE_STATUS, null)));
        this.mixins = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    /**
     * Set the builder according to the resource rendering information
     *
     * @param rendering is the instance of the cloud automation model for a compute
     */
    public ComputeBuilder(ResourceRendering rendering) throws SyntaxException {
        this.url = Optional.ofNullable(rendering.getId());
        this.title = Optional.ofNullable(
                (String) rendering.getAttributes().getOrDefault(ENTITY_TITLE_NAME, null));
        this.architecture = getArchitectureFromString(Optional.ofNullable(
                (String) rendering.getAttributes().getOrDefault(ARCHITECTURE_NAME, null)));
        this.state = getStateFromString(Optional.ofNullable(
                (String) rendering.getAttributes().getOrDefault(COMPUTE_STATE_NAME, null)));
        this.hostname = Optional.ofNullable(
                (String) rendering.getAttributes().getOrDefault(HOSTNAME_NAME, null));
        this.cores = ConvertUtils.getIntegerFromString(
                Optional.ofNullable(rendering.getAttributes().getOrDefault(CORES_NAME, null))
                        .map(coreNumber -> String.valueOf(coreNumber)));
        this.memory = ConvertUtils.getFloatFromString(
                Optional.ofNullable(rendering.getAttributes().getOrDefault(MEMORY_NAME, null))
                        .map(memoryNumber -> String.valueOf(memoryNumber)));
        this.share = ConvertUtils.getIntegerFromString(
                Optional.ofNullable(rendering.getAttributes().getOrDefault(SHARE_NAME, null))
                        .map(shareNumber -> String.valueOf(shareNumber)));
        this.summary = Optional.ofNullable(
                (String) rendering.getAttributes().getOrDefault(SUMMARY_NAME, null));
        this.mixins = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public ComputeBuilder url(String url) {
        this.url = Optional.of(url);
        return this;
    }

    public ComputeBuilder title(String title) {
        this.title = Optional.of(title);
        return this;
    }

    public ComputeBuilder summary(String summary) {
        this.summary = Optional.of(summary);
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
        this.architecture = Optional.of(architecture);
        return this;
    }

    public ComputeBuilder cores(Integer cores) {
        this.cores = Optional.ofNullable(cores);
        return this;
    }

    public ComputeBuilder cores(String cores) {
        this.cores = Optional.ofNullable(cores).map(c -> Integer.parseInt(c));
        return this;
    }

    public ComputeBuilder share(Integer share) {
        this.share = Optional.ofNullable(share);
        return this;
    }

    public ComputeBuilder share(String share) {
        this.share = Optional.ofNullable(share).map(s -> Integer.parseInt(s));
        return this;
    }

    public ComputeBuilder hostame(String hostname) {
        this.hostname = Optional.ofNullable(hostname);
        return this;
    }

    public ComputeBuilder memory(Float memory) {
        this.memory = Optional.ofNullable(memory);
        return this;
    }

    public ComputeBuilder memory(String memory) {
        this.memory = Optional.ofNullable(memory).map(m -> Float.parseFloat(m));
        return this;
    }

    public ComputeBuilder state(ComputeState state) {
        this.state = Optional.ofNullable(state);
        return this;
    }

    /**
     * Parse a string OCCI state into the state object
     *
     * @param state a string representing the state
     * @return an optional state object
     * @throws SyntaxException if the string is not null and doesn't match with any state
     */
    public Optional<ComputeState> getStateFromString(Optional<String> state) throws SyntaxException {
        if (!state.isPresent()) {
            return Optional.empty();
        }

        switch (state.get()) {
            case COMPUTE_STATE_ACTIVE:
                return Optional.of(ComputeState.ACTIVE);
            case COMPUTE_STATE_INACTIVE:
                return Optional.of(ComputeState.INACTIVE);
            case COMPUTE_STATE_SUSPENDED:
                return Optional.of(ComputeState.SUSPENDED);
            case COMPUTE_STATE_ERROR:
                return Optional.of(ComputeState.ERROR);
            default:
                throw new SyntaxException(state.get());
        }

    }

    /**
     * Parse a string architecture into the architecture object
     *
     * @param architecture a string representing the architecture
     * @return an optional architecture object
     * @throws SyntaxException if the string is not null and doesn't match with any architecture
     */
    private Optional<Compute.Architecture> getArchitectureFromString(
            Optional<String> architecture) throws SyntaxException {
        if (!architecture.isPresent()) {
            return Optional.empty();
        } else if (Compute.Architecture.X64.toString().equalsIgnoreCase(architecture.get())) {
            return Optional.of(Compute.Architecture.X64);
        } else if (Compute.Architecture.X86.toString().equalsIgnoreCase(architecture.get())) {
            return Optional.of(Compute.Architecture.X86);
        } else {
            throw new SyntaxException(architecture.get());
        }
    }

    /**
     * Parse a string cloud automation into an OCCI compute state
     *
     * @param state is a string representing the cloud automation model
     * @return an optional compute state
     * @throws SyntaxException
     */
    private Optional<ComputeState> getStateFromCloudAutomation(
            Optional<String> state) throws SyntaxException {
        if (!state.isPresent()) {
            return Optional.empty();
        }
        switch (state.get()) {
            case RUNNING_STATE:
                return Optional.of(ComputeState.ACTIVE);
            case STOPPED_STATE:
                return Optional.of(ComputeState.SUSPENDED);
            case PENDING_STATE:
                return Optional.of(ComputeState.INACTIVE);
            case TERMINATED_STATE:
                return Optional.of(ComputeState.INACTIVE);
            case ERROR_STATE:
                return Optional.of(ComputeState.ERROR);
            default:
                throw new SyntaxException(state.get());
        }
    }


    public Compute build() {
        Compute compute = new Compute(url, InfrastructureKinds.COMPUTE, title, mixins, summary,
                new ArrayList<>(), architecture,
                cores, share, hostname, memory, state);
        mixins.forEach(mixin -> mixin.addEntity(compute));
        return compute;
    }


}

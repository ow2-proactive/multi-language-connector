package org.ow2.proactive.procci.model.occi.platform.bigdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.occi.platform.Component;
import org.ow2.proactive.procci.model.occi.platform.Status;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataKinds;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ID_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.SUMMARY_NAME;
import static org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes.STATUS_NAME;

@AllArgsConstructor
@RequiredArgsConstructor
public class SwarmBuilder extends Component.Builder {

    private static final String AGENT_IP_SEPARATOR = ",";

    private final String hostIp;
    private final String masterIp;
    private final List<String> agentsIp;
    private Optional<String> machineName;
    private Optional<String> networkName;

    public SwarmBuilder(String hostIp, String masterIp){
        this.hostIp = hostIp;
        this.masterIp = masterIp;
        this.agentsIp = new ArrayList<>();
        this.machineName = Optional.empty();
        this.networkName = Optional.empty();
    }

    public SwarmBuilder(Model cloudautomation) throws ClientException{

        Map<String,String> attributes = cloudautomation.getVariables();
        this.url = Optional.ofNullable(attributes.get(ID_NAME));
        this.title = Optional.ofNullable(attributes.get(ENTITY_TITLE_NAME));
        this.summary = Optional.ofNullable(attributes.get(SUMMARY_NAME));
        this.machineName = Optional.ofNullable(attributes.get(BigDataAttributes.MACHINE_NAME_NAME));
        this.networkName = Optional.ofNullable(attributes.get(BigDataAttributes.NETWORK_NAME_NAME));

        this.hostIp = Optional.ofNullable(attributes.get(BigDataAttributes.HOST_IP_NAME))
                .orElseThrow(()-> new MissingAttributesException(BigDataAttributes.HOST_IP_NAME,BigDataKinds.SWARM.getTitle())
        );
        this.masterIp = Optional.ofNullable(attributes.get(BigDataAttributes.MASTER_IP_NAME))
                .orElseThrow(() -> new MissingAttributesException(BigDataAttributes.HOST_IP_NAME,BigDataKinds.SWARM.getTitle()));
        this.agentsIp = new ArrayList<>(Arrays.asList(Optional.ofNullable(attributes.get(BigDataAttributes.AGENTS_IP_NAME))
                .orElseThrow(() -> new MissingAttributesException(BigDataAttributes.HOST_IP_NAME,BigDataKinds.SWARM.getTitle())).
                        split(AGENT_IP_SEPARATOR)));

        Optional<String> status = Optional.ofNullable(attributes.get(STATUS_NAME));
        if(status.isPresent()) {
            this.status = Optional.of(Status.getStatusFromString(status.get()));
        }
        else {
            this.status = Optional.empty();
        }


    }

    public SwarmBuilder machineName(String machineName){
        this.machineName = Optional.ofNullable(machineName);
        return this;
    }

    public SwarmBuilder networkName(String networkName){
        this.networkName = Optional.ofNullable(networkName);
        return this;
    }

    public SwarmBuilder addAgentIp(String agentIp){
        this.agentsIp.add(agentIp);
        return this;
    }

    @Override
    public Swarm build(){
        return new Swarm(this.getUrl(), BigDataKinds.SWARM,this.getTitle(),this.getMixins(),this.getSummary(),this.getLinks(),
                machineName,hostIp,masterIp,agentsIp,networkName);
    }

}

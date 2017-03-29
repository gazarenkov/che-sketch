package org.eclipse.che.api.workspace.server.spi;

import org.eclipse.che.api.core.ApiException;
import org.eclipse.che.api.core.model.workspace.config.MachineConfig;
import org.eclipse.che.api.core.model.workspace.config.ServerConfig;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "pre-processed" OldMachine Config. Form useful for
 * @author gazarenkov
 */
public class InternalMachineConfig {

    // ordered agent scripts to launch on start
    private final EffectiveAgents           agents  = new EffectiveAgents();
    // set of servers
    private final Map<String, ServerConfig> servers = new HashMap<>();

    private final Map<String, String> attributes = new HashMap<>();

    public InternalMachineConfig(MachineConfig originalConfig, URL agentRegistry) throws ApiException, IOException {

        initAgents(originalConfig.getAgents(), agentRegistry);

        this.servers.putAll(originalConfig.getServers());
        this.servers.putAll(agents.getAllServers());

        this.attributes.putAll(originalConfig.getAttributes());

    }

    // TODO
    // Obtain scripts and organize correctly ordered list according to Agents configuration,
    // including dependencies and excluding duplicates
    private void initAgents(List<String> agentIds, URL agentRegistry) throws ApiException, IOException{

//        List<String> ids = new ArrayList<>();
//
//        // get all id with dependencies
//        for(String id : agentIds) {
//            ids.add(id);
//            AgentDto agent = DefaultHttpJsonRequest.create(agentRegistry.toString()).request().asDto(AgentDto.class);
//            agent.getDependencies();
//        }
//
//        // remove duplicates and init servers
//
//
//        // reverse
//        Lists.reverse(ids);


    }

    /**
     *
     * @return
     */
    public Map<String, ServerConfig> getServers() {
        return servers;
    }

    public List <String> getAgentScripts() {
        return this.agents.agentScripts;
    }

    private class EffectiveAgents {
        private List <String> agentScripts = new ArrayList<>();

        private Map <String, ServerConfig> getAllServers() {
            return null;
        }
    }



}

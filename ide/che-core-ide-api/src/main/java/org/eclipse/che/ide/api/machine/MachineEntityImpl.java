/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.api.machine;

import org.eclipse.che.api.core.model.workspace.runtime.MachineRuntime;
import org.eclipse.che.api.core.model.workspace.runtime.ServerRuntime;
import org.eclipse.che.api.core.model.workspace.config.Environment;
import org.eclipse.che.api.core.model.workspace.Workspace;
import org.eclipse.che.api.machine.shared.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Vitalii Parfonov
 */

public class MachineEntityImpl implements MachineEntity {

    //protected final Machine machineDescriptor;
    //protected final MachineConfig machineConfig;

    //protected final Map<String, MachineServer> servers;
    //protected final Map<String, String>        runtimeProperties;
    //protected final Map<String, String>        envVariables;
    //protected final List<Link>                 machineLinks;

    protected final Environment environment;

    protected final MachineRuntime machineRuntime;

//    protected final ExtendedMachineDto extendedMachine;

    protected final String workspaceId;

    protected final String environmentName;

    protected final String machineName;

    protected final boolean dev;

//    public MachineEntityImpl(@NotNull Machine machineDescriptor) {
//
//    }

    public MachineEntityImpl(Workspace workspace, String machineName) {

   //     public MachineEntityImpl(@NotNull Machine machineDescriptor) {



        //this.machineDescriptor = machineDescriptor;
        this.machineName = machineName;

        this.workspaceId = workspace.getId();

        environmentName = workspace.getRuntime().getActiveEnv();

        environment = workspace.getConfig().getEnvironments().get(environmentName);

//        extendedMachine = environment.getMachines().get(machineName);

        machineRuntime = workspace.getRuntime().getMachines().get(machineName);


        dev = machineRuntime.getServers().containsKey(Constants.WSAGENT_REFERENCE);


//        //this.machineConfig = machineDescriptor != null ? machineDescriptor.getConfig() : null;
//        //this.machineLinks = machineDescriptor instanceof Hyperlinks ? ((Hyperlinks)machineDescriptor).getLinks() : null;
//
//        if (machineDescriptor == null || machineDescriptor.getRuntime() == null) {
//            servers = null;
//            runtimeProperties = null;
//            //envVariables = null;
//        } else {
//            MachineRuntime machineRuntime = machineDescriptor.getRuntime();
//            Map<String, ? extends Server> serverDtoMap = machineRuntime.getServers();
//            servers = new HashMap<>(serverDtoMap.size());
//            for (String s : serverDtoMap.keySet()) {
//                servers.put(s, new MachineServer(serverDtoMap.get(s)));
//            }
//            runtimeProperties = machineRuntime.getProperties();
//            //envVariables = machineRuntime.getEnvVariables();
//        }
//


    }


    public String getWorkspace() {
        return workspaceId;
    }

//    @Override
//    public MachineConfig getConfig() {
//        return machineConfig;
//    }

    @Override
    public String getId() {
        return getInternalId();
    }
//
//    @Override
//    public String getWorkspaceId() {
//        return machineDescriptor.getWorkspaceId();
//    }

//    @Override
    public String getEnvName() {
        return environmentName;
    }

//    @Override
//    public String getOwner() {
//        return machineDescriptor.getOwner();
//    }

//    @Override
//    public MachineStatus getStatus() {
//        return machineDescriptor.getStatus();
//    }

//    @Override
//    public MachineRuntime getRuntime() {
//        return machineDescriptor.getRuntime();
//    }

    @Override
    public boolean isDev() {
        return dev;
    }

    @Override
    public String getDisplayName() {
        return machineName;
    }

    @Override
    public String getType() {
        return environment.getRecipe().getType();
    }

//    @Override
//    public String getDisplayName() {
//        return machineConfig.getName();
//    }

    @Override
    public Map<String, String> getProperties() {
        return machineRuntime.getProperties();
    }

    @Override
    public String getTerminalUrl() {
        return machineRuntime.getServers().get(Constants.TERMINAL_REFERENCE).getUrl();
    }

    @Override
    public String getExecAgentUrl() {
        return machineRuntime.getServers().get(Constants.EXEC_AGENT_REFERENCE).getUrl();
    }

//    public String getTerminalUrl() {
//        for (Link link : machineLinks) {
//            if (Constants.TERMINAL_REFERENCE.equals(link.getRel())) {
//                return link.getHref();
//            }
//        }
//        //should not be
//        final String message = "Reference " + Constants.TERMINAL_REFERENCE + " not found in " + machineConfig.getName()  + " description";
//        Log.error(getClass(), message);
//        throw new RuntimeException(message);
//    }

//    public String getExecAgentUrl() {
//        for (Link link :machineLinks) {
//            if (Constants.EXEC_AGENT_REFERENCE.equals(link.getRel())) {
//                return link.getHref();
//            }
//        }
//        //should not be
//        final String message = "Reference " + Constants.EXEC_AGENT_REFERENCE + " not found in " +  machineConfig.getName() + " description";
//        Log.error(getClass(), message);
//        throw new RuntimeException(message);
//    }

    @Override
    public Map<String, MachineServer> getServers() {



        Map<String, MachineServer> servers = new HashMap<>();
        for(Map.Entry<String, ? extends ServerRuntime> dto : machineRuntime.getServers().entrySet()) {
            servers.put(dto.getKey(), new MachineServer(dto.getValue()));
        }

        return servers;
    }

    @Override
    public MachineServer getServer(String reference) {
        return getServers().get(reference);
//        if (!Strings.isNullOrEmpty(reference)) {
//            for (MachineServer server : servers.values()) {
//                if (reference.equals(server.getRef())) {
//                    return server;
//                }
//            }
//        }
//        return null;
    }

//    @Override
//    public List<Link> getMachineLinks() {
//        return machineLinks;
//    }
//
//    @Override
//    public Link getMachineLink(String ref) {
//        if (!Strings.isNullOrEmpty(ref)) {
//            for (Link link : machineLinks) {
//                if (ref.equals(link.getRel())) {
//                    return link;
//                }
//            }
//        }
//        return null;
//    }

//    @Override
//    public Map<String, String> getEnvVariables() {
//        return envVariables;
//    }

//    /** Returns {@link Machine descriptor} of the Workspace Agent. */
//    @Override
//    public Machine getDescriptor() {
//        return machineDescriptor;
//    }

    private String getInternalId() {
        return workspaceId + environmentName + machineName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        MachineEntityImpl otherMachine = (MachineEntityImpl)other;

        return Objects.equals(getInternalId(), otherMachine.getInternalId());

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getInternalId());
    }
}

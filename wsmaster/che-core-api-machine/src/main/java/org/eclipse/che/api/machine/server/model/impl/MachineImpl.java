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
package org.eclipse.che.api.machine.server.model.impl;

import org.eclipse.che.api.core.model.workspace.runtime.Machine;
import org.eclipse.che.api.core.model.workspace.runtime.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Data object for {@link Machine}.
 *
 * @author Alexander Garagatyi
 */
public class MachineImpl implements Machine {

    private Map<String, String>     properties;
    private Map<String, ServerImpl> servers;

    public static MachineRuntimeInfoImplBuilder builder() {
        return new MachineRuntimeInfoImplBuilder();
    }

//    public MachineImpl(Machine machineRuntime) {
//        this(machineRuntime.getEnvVariables(), machineRuntime.getProperties(), machineRuntime.getServers());
//    }

    public MachineImpl(Map<String, String> properties,
                       Map<String, ? extends Server> servers) {
//        this.envVariables = new HashMap<>(envVariables);
        this.properties = new HashMap<>(properties);
        if (servers != null) {
            this.servers = servers.entrySet()
                                  .stream()
                                  .collect(HashMap::new,
                                           (map, entry) -> map.put(entry.getKey(), new ServerImpl(entry.getValue().getUrl())),
                                           HashMap::putAll);
        }
    }


//    public Map<String, String> getEnvVariables() {
//        if (envVariables == null) {
//            envVariables = new HashMap<>();
//        }
//        return envVariables;
//    }

    @Override
    public Map<String, String> getProperties() {
        if (properties == null) {
            properties = new HashMap<>();
        }
        return properties;
    }

    @Override
    public Map<String, ServerImpl> getServers() {
        if (servers == null) {
            servers = new HashMap<>();
        }
        return servers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineImpl)) return false;
        MachineImpl that = (MachineImpl)o;
        return //Objects.equals(getEnvVariables(), that.getEnvVariables()) &&
               Objects.equals(getProperties(), that.getProperties()) &&
               Objects.equals(getServers(), that.getServers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*getEnvVariables(),*/ getProperties(), getServers());
    }

    public static class MachineRuntimeInfoImplBuilder {
        private Map<String, ? extends Server> servers;
        private Map<String, String>           properties;
//        private Map<String, String>           envVariables;

        public MachineImpl build() {
            return new MachineImpl(properties, servers);
        }

        public MachineRuntimeInfoImplBuilder setServers(Map<String, ? extends Server> servers) {
            this.servers = servers;
            return this;
        }

        public MachineRuntimeInfoImplBuilder setProperties(Map<String, String> properties) {
            this.properties = properties;
            return this;
        }

//        public MachineRuntimeInfoImplBuilder setEnvVariables(Map<String, String> envVariables) {
//            this.envVariables = envVariables;
//            return this;
//        }
    }
}

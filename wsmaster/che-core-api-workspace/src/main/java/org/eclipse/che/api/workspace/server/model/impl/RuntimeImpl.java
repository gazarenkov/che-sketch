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
package org.eclipse.che.api.workspace.server.model.impl;

import org.eclipse.che.api.core.model.workspace.runtime.Machine;
import org.eclipse.che.api.core.model.workspace.Runtime;

import java.util.Map;
import java.util.Objects;

/**
 * Data object for {@link Runtime}.
 *
 * @author Yevhenii Voevodin
 */
public class RuntimeImpl implements Runtime {

    private final String                   activeEnv;
    private String                         owner;
    private String                         userToken;
    private Map<String, ? extends Machine> machines;

//    public RuntimeImpl(String activeEnv) {
//        this.activeEnv = activeEnv;
//    }

    public RuntimeImpl(String activeEnv,
                       Map<String, ? extends Machine> machines,
                       String owner) {
        this.activeEnv = activeEnv;
        this.machines = machines;
        this.owner = owner;
//        this.userToken = userToken;
    }

//    public RuntimeImpl(Runtime runtime) {
//        this(runtime.getActiveEnv(),
//             runtime.getMachines());
//    }

    @Override
    public String getActiveEnv() {
        return activeEnv;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public Map<String, ? extends Machine> getMachines() {
        return machines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuntimeImpl)) return false;
        RuntimeImpl that = (RuntimeImpl)o;
        return Objects.equals(activeEnv, that.activeEnv) &&
//               Objects.equals(rootFolder, that.rootFolder) &&
//               Objects.equals(devMachine, that.devMachine) &&
               Objects.equals(machines, that.machines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeEnv,
//                            rootFolder,
//                            devMachine,
                            machines);
    }

    @Override
    public String toString() {
        return "RuntimeImpl{" +
               "activeEnv='" + activeEnv + '\'' +
//               ", rootFolder='" + rootFolder + '\'' +
//               ", devMachine=" + devMachine +
               ", machines=" + machines +
               '}';
    }
}

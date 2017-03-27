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
package org.eclipse.che.api.workspace.server.spi;

import org.eclipse.che.api.core.model.workspace.WorkspaceRuntime;
import org.eclipse.che.api.core.model.workspace.runtime.MachineRuntime;

import java.util.Map;

/**
 * Implementation of concrete WorkspaceRuntime
 * Important to notice - no states in here, it is always RUNNING
 * @author gazarenkov
 */
public abstract class InternalRuntime implements WorkspaceRuntime {

    private final RuntimeContext context;

    public InternalRuntime(RuntimeContext context) {
        this.context = context;
    }

    @Override
    public String getActiveEnv() {
        return context.getIdentity().getEnvName();
    }

    @Override
    public String getOwner() {
        return context.getIdentity().getOwner();
    }

    @Override
    public abstract Map<String, ? extends MachineRuntime> getMachines();

    /**
     * @return some implementation specific properties if any
     */
    public abstract Map <String, String> getProperties();

    /**
     * @return the Context
     */
    public final RuntimeContext getContext() {
        return context;
    }


}

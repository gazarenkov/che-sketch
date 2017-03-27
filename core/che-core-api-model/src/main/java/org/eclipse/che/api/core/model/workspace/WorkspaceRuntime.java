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
package org.eclipse.che.api.core.model.workspace;

import org.eclipse.che.api.core.model.workspace.runtime.MachineRuntime;
import org.eclipse.che.api.core.model.machine.MachineStatus;

import java.util.Map;

/**
 * Defines a contract for workspace runtime.
 *
 * <p>Workspace has runtime when workspace is <b>running</b>
 * (its {@link Workspace#getStatus() status} is one of the
 * {@link WorkspaceStatus#STARTING}, {@link WorkspaceStatus#RUNNING},
 * {@link WorkspaceStatus#STOPPING}).
 *
 * <p>Workspace runtime defines workspace attributes which
 * exist only when workspace is running. All those attributes
 * are strongly related to the runtime environment.
 * Workspace runtime always exists in couple with {@link Workspace} instance.
 *
 * @author Yevhenii Voevodin
 */
public interface WorkspaceRuntime {

    /**
     * Returns an active environment name.
     * The environment with such name must exist
     * in {@link WorkspaceConfig#getEnvironments()}.
     */
    String getActiveEnv();


    /**
     * Returns all the machines which statuses are either {@link MachineStatus#RUNNING running}
     * or {@link MachineStatus#DESTROYING}.
     *
     * <p>Returned list always contains dev-machine.
     */
    Map<String, ? extends MachineRuntime> getMachines();


    String getOwner();

//    String getUserToken();


}

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
package org.eclipse.che.api.workspace.shared.dto;

import org.eclipse.che.api.core.model.workspace.WorkspaceRuntime;
import org.eclipse.che.api.machine.shared.dto.MachineRuntimeDto;
import org.eclipse.che.dto.shared.DTO;

import java.util.Map;

/**
 * @author Alexander Garagatyi
 */
@DTO
public interface WorkspaceRuntimeDto extends WorkspaceRuntime {

    @Override
    String getActiveEnv();

    void setActiveEnv(String activeEnv);

    WorkspaceRuntimeDto withActiveEnv(String activeEnvName);

//    @Override
//    MachineDto getDevMachine();

//    void setDevMachine(MachineDto machine);

//    WorkspaceRuntimeDto withDevMachine(MachineDto machine);

    @Override
    Map<String, MachineRuntimeDto> getMachines();

    void setMachines(Map<String, MachineRuntimeDto> machines);

    WorkspaceRuntimeDto withMachines(Map<String, MachineRuntimeDto> machines);

    @Override
    String getOwner();

    WorkspaceRuntimeDto withOwner(String owner);

    String getUserToken();

    WorkspaceRuntimeDto withUserToken(String userToken);

    void setUserToken(String userToken);


    //    void setRootFolder(String rootFolder);
//
//    WorkspaceRuntimeDto withRootFolder(String rootFolder);

//    WorkspaceRuntimeDto withLinks(List<Link> links);
}

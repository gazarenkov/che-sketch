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
package org.eclipse.che.api.machine.shared.dto;

import org.eclipse.che.api.core.model.workspace.runtime.MachineRuntime;
import org.eclipse.che.dto.shared.DTO;

import java.util.Map;

/**
 * @author Alexander Garagatyi
 */
@DTO
public interface MachineRuntimeDto extends MachineRuntime {

    @Override
    Map<String, String> getProperties();

    MachineRuntimeDto withProperties(Map<String, String> properties);

    @Override
    Map<String, ServerRuntimeDto> getServers();

    MachineRuntimeDto withServers(Map<String, ServerRuntimeDto> servers);


}

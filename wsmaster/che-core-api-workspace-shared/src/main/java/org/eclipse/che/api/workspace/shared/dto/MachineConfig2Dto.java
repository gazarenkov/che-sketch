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

import org.eclipse.che.api.core.factory.FactoryParameter;
import org.eclipse.che.api.core.model.workspace.config.MachineConfig2;
import org.eclipse.che.dto.shared.DTO;

import java.util.List;
import java.util.Map;

import static org.eclipse.che.api.core.factory.FactoryParameter.Obligation.OPTIONAL;

/**
 * @author Alexander Garagatyi
 */
@DTO
public interface MachineConfig2Dto extends MachineConfig2 {
    @Override
    @FactoryParameter(obligation = OPTIONAL)
    List<String> getAgents();

    void setAgents(List<String> agents);

    MachineConfig2Dto withAgents(List<String> agents);

    @Override
    @FactoryParameter(obligation = OPTIONAL)
    Map<String, ServerConf2Dto> getServers();

    void setServers(Map<String, ServerConf2Dto> servers);

    MachineConfig2Dto withServers(Map<String, ServerConf2Dto> servers);

    @Override
    @FactoryParameter(obligation = OPTIONAL)
    Map<String, String> getAttributes();

    void setAttributes(Map<String, String> attributes);

    MachineConfig2Dto withAttributes(Map<String, String> attributes);
}

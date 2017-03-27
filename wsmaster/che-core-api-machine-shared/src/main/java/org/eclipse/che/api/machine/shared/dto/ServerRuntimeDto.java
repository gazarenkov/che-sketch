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

import org.eclipse.che.api.core.model.workspace.runtime.ServerRuntime;
import org.eclipse.che.dto.shared.DTO;

/**
 * Describes how to access to exposed ports for servers inside machine
 *
 * @author Alexander Garagatyi
 */
@DTO
public interface ServerRuntimeDto extends ServerRuntime {

    @Override
    String getUrl();

    void setUrl(String url);

    ServerRuntimeDto withUrl(String url);


}

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
package org.eclipse.che.ide.extension.machine.client.inject.factories;

import org.eclipse.che.api.core.model.workspace.Workspace;
import org.eclipse.che.ide.api.machine.MachineEntityImpl;

import javax.inject.Inject;

/**
 * @author gazarenkov
 */
public class MachineItem extends MachineEntityImpl {
    @Inject
    public MachineItem(Workspace workspace, String machineName) {
        super(workspace, machineName);
    }
}

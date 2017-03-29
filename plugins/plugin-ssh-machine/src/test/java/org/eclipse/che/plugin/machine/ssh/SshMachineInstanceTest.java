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
package org.eclipse.che.plugin.machine.ssh;

import org.eclipse.che.api.core.model.machine.OldMachine;
import org.eclipse.che.api.core.model.machine.OldMachineConfig;
import org.eclipse.che.api.core.model.workspace.runtime.Machine;
import org.eclipse.che.api.core.util.LineConsumer;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for{@link SshOldMachineInstance}
 *
 * @author Igor Vinokur
 */
@Listeners(MockitoTestNGListener.class)
public class SshMachineInstanceTest {
    @Mock
    private OldMachine   machine;
    @Mock
    private SshClient    sshClient;
    @Mock
    private LineConsumer outputConsumer;

    private SshOldMachineInstance sshMachineInstance;

    @BeforeMethod
    public void setUp() {
        when(machine.getConfig()).thenReturn(mock(OldMachineConfig.class));
        when(machine.getEnvName()).thenReturn("EnvName");
        when(machine.getId()).thenReturn("Id");
        when(machine.getOwner()).thenReturn("Owner");
        when(machine.getRuntime()).thenReturn(mock(Machine.class));
        when(machine.getWorkspaceId()).thenReturn("WorkspaceId");

        sshMachineInstance = new SshOldMachineInstance(machine,
                                                       sshClient,
                                                       outputConsumer,
                                                       mock(SshMachineFactory.class),
                                                       new HashSet<>());
    }

    @Test
    public void shouldCloseOutputConsumerAndStopClientOnDestroy() throws Exception {
        sshMachineInstance.destroy();

        verify(outputConsumer).close();
        verify(sshClient).stop();
    }

}

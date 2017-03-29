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

import com.google.gson.Gson;

import org.eclipse.che.api.core.model.machine.OldMachine;
import org.eclipse.che.api.core.model.machine.OldMachineConfig;
import org.eclipse.che.api.core.model.machine.MachineStatus;
import org.eclipse.che.api.core.util.LineConsumer;
import org.eclipse.che.api.machine.server.exception.MachineException;
import org.eclipse.che.api.machine.server.exception.SnapshotException;
import org.eclipse.che.api.machine.server.model.impl.OldMachineConfigImpl;
import org.eclipse.che.api.machine.server.model.impl.OldMachineImpl;
import org.eclipse.che.api.machine.server.model.impl.MachineSourceImpl;
import org.eclipse.che.api.machine.server.model.impl.OldServerConfImpl;
import org.eclipse.che.api.machine.server.recipe.OldRecipeImpl;
import org.eclipse.che.api.machine.server.spi.Instance;
import org.eclipse.che.api.machine.server.util.RecipeDownloader;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashSet;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Alexander Garagatyi
 */
@Listeners(MockitoTestNGListener.class)
public class SshMachineInstanceProviderTest {
    private static final String RECIPE_SCRIPT = new Gson().toJson(new SshMachineRecipe("localhost", 22, "user", "password"));

    @Mock
    private RecipeDownloader recipeDownloader;

    @Mock
    private SshMachineFactory     sshMachineFactory;
    @Mock
    private SshClient             sshClient;
    @Mock
    private SshOldMachineInstance sshMachineInstance;

    @InjectMocks
    private SshMachineInstanceProvider provider;
    private OldRecipeImpl              recipe;
    private OldMachineImpl             machine;

    @BeforeMethod
    public void setUp() throws Exception {
        machine = createMachine();
        SshMachineRecipe sshMachineRecipe = new SshMachineRecipe("localhost",
                                                                 22,
                                                                 "user",
                                                                 "password");
        recipe = new OldRecipeImpl().withType("ssh-config")
                                    .withScript(RECIPE_SCRIPT);
    }

    @Test
    public void shouldReturnCorrectType() throws Exception {
        assertEquals(provider.getType(), "ssh");
    }

    @Test
    public void shouldReturnCorrectRecipeTypes() throws Exception {
        assertEquals(provider.getRecipeTypes(), new HashSet<>(singletonList("ssh-config")));
    }

    @Test(expectedExceptions = SnapshotException.class,
          expectedExceptionsMessageRegExp = "Snapshot feature is unsupported for ssh machine implementation")
    public void shouldThrowSnapshotExceptionOnRemoveSnapshot() throws Exception {
        provider.removeInstanceSnapshot(null);
    }

    @Test(expectedExceptions = MachineException.class,
          expectedExceptionsMessageRegExp = "Dev machine is not supported for Ssh machine implementation")
    public void shouldThrowExceptionOnDevMachineCreationFromRecipe() throws Exception {
        OldMachine machine = createMachine(true);

        provider.createInstance(machine, LineConsumer.DEV_NULL);
    }

    @Test(expectedExceptions = NullPointerException.class,
          expectedExceptionsMessageRegExp = "Location in machine source is required")
    public void shouldThrowExceptionInvalidMachineConfigSource() throws Exception {
        OldMachineImpl machine = createMachine(true);
        machine.getConfig().setSource(new MachineSourceImpl("ssh-config").setContent("hello"));

        provider.createInstance(machine, LineConsumer.DEV_NULL);
    }

    @Test
    public void shouldBeAbleToCreateSshMachineInstanceOnMachineCreationFromRecipe() throws Exception {
        when(sshMachineFactory.createSshClient(any(SshMachineRecipe.class), anyMap())).thenReturn(sshClient);
        when(sshMachineFactory.createInstance(eq(machine), eq(sshClient), any(LineConsumer.class))).thenReturn(sshMachineInstance);
        when(recipeDownloader.getRecipe(eq(machine.getConfig()))).thenReturn(recipe);

        Instance instance = provider.createInstance(machine, LineConsumer.DEV_NULL);

        assertEquals(instance, sshMachineInstance);
    }

    private OldMachineImpl createMachine() {
        return createMachine(false);
    }

    private OldMachineImpl createMachine(boolean isDev) {
        OldMachineConfig machineConfig = OldMachineConfigImpl.builder()
                                                             .setDev(isDev)
                                                             .setEnvVariables(singletonMap("testEnvVar1", "testEnvVarVal1"))
                                                             .setName("name1")
                                                             .setServers(singletonList(new OldServerConfImpl("myref1",
                                                                                                             "10011/tcp",
                                                                                                             "http",
                                                                                                             null)))
                                                             .setSource(new MachineSourceImpl("ssh-config").setLocation("localhost:10012/recipe"))
                                                             .setType("ssh")
                                                             .build();
        return OldMachineImpl.builder()
                             .setConfig(machineConfig)
                             .setEnvName("env1")
                             .setId("id1")
                             .setOwner("owner1")
                             .setRuntime(null)
                             .setStatus(MachineStatus.CREATING)
                             .setWorkspaceId("wsId1")
                             .build();
    }
}

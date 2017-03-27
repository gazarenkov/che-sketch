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
package org.eclipse.che.api.factory.server.spi.tck;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.NotFoundException;
import org.eclipse.che.api.core.model.factory.Button;
import org.eclipse.che.api.factory.server.FactoryImage;
import org.eclipse.che.api.factory.server.model.impl.ActionImpl;
import org.eclipse.che.api.factory.server.model.impl.AuthorImpl;
import org.eclipse.che.api.factory.server.model.impl.ButtonAttributesImpl;
import org.eclipse.che.api.factory.server.model.impl.ButtonImpl;
import org.eclipse.che.api.factory.server.model.impl.FactoryImpl;
import org.eclipse.che.api.factory.server.model.impl.IdeImpl;
import org.eclipse.che.api.factory.server.model.impl.OnAppClosedImpl;
import org.eclipse.che.api.factory.server.model.impl.OnAppLoadedImpl;
import org.eclipse.che.api.factory.server.model.impl.OnProjectsLoadedImpl;
import org.eclipse.che.api.factory.server.model.impl.PoliciesImpl;
import org.eclipse.che.api.factory.server.spi.FactoryDao;
import org.eclipse.che.api.machine.server.model.impl.CommandImpl;
import org.eclipse.che.api.user.server.model.impl.UserImpl;
import org.eclipse.che.api.workspace.server.model.impl.EnvironmentImpl;
import org.eclipse.che.api.workspace.server.model.impl.EnvironmentRecipeImpl;
import org.eclipse.che.api.workspace.server.model.impl.MachineConfig2Impl;
import org.eclipse.che.api.workspace.server.model.impl.ProjectConfigImpl;
import org.eclipse.che.api.workspace.server.model.impl.ServerConf2Impl;
import org.eclipse.che.api.workspace.server.model.impl.SourceStorageImpl;
import org.eclipse.che.api.workspace.server.model.impl.WorkspaceConfigImpl;
import org.eclipse.che.commons.lang.Pair;
import org.eclipse.che.commons.test.tck.TckListener;
import org.eclipse.che.commons.test.tck.repository.TckRepository;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.testng.Assert.assertEquals;

/**
 * Tests {@link FactoryDao} contract.
 *
 * @author Anton Korneta
 */
@Listeners(TckListener.class)
@Test(suiteName = FactoryDaoTest.SUITE_NAME)
public class FactoryDaoTest {

    public static final String SUITE_NAME = "FactoryDaoTck";

    private static final int ENTRY_COUNT = 5;

    private FactoryImpl[] factories;
    private UserImpl[]    users;

    @Inject
    private FactoryDao factoryDao;

    @Inject
    private TckRepository<FactoryImpl> factoryTckRepository;

    @Inject
    private TckRepository<UserImpl> userTckRepository;

    @BeforeMethod
    public void setUp() throws Exception {
        factories = new FactoryImpl[ENTRY_COUNT];
        users = new UserImpl[ENTRY_COUNT];
        for (int i = 0; i < ENTRY_COUNT; i++) {
            users[i] = new UserImpl("userId_" + i, "email_" + i, "name" + i);
        }
        for (int i = 0; i < ENTRY_COUNT; i++) {
            factories[i] = createFactory(i, users[i].getId());
        }
        userTckRepository.createAll(Arrays.asList(users));
        factoryTckRepository.createAll(Stream.of(factories).map(FactoryImpl::new).collect(toList()));
    }

    @AfterMethod
    public void cleanUp() throws Exception {
        factoryTckRepository.removeAll();
        userTckRepository.removeAll();
    }

    @Test(dependsOnMethods = "shouldGetFactoryById")
    public void shouldCreateFactory() throws Exception {
        final FactoryImpl factory = createFactory(10, users[0].getId());
        factory.getCreator().setUserId(factories[0].getCreator().getUserId());
        factoryDao.create(factory);

        assertEquals(factoryDao.getById(factory.getId()), new FactoryImpl(factory));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeWhenCreateNullFactory() throws Exception {
        factoryDao.create(null);
    }

    @Test(expectedExceptions = ConflictException.class)
    public void shouldThrowConflictExceptionWhenCreatingFactoryWithExistingId() throws Exception {
        final FactoryImpl factory = createFactory(10, users[0].getId());
        final FactoryImpl existing = factories[0];
        factory.getCreator().setUserId(existing.getCreator().getUserId());
        factory.setId(existing.getId());
        factoryDao.create(factory);
    }

    // TODO fix after issue: https://github.com/eclipse/che/issues/2110
//    @Test(expectedExceptions = ConflictException.class)
//    public void shouldThrowConflictExceptionWhenCreatingFactoryWithExistingNameAndUserId() throws Exception {
//        final FactoryImpl factory = createFactory(10, users[0].getId());
//        final FactoryImpl existing = factories[0];
//        factory.getCreator().setUserId(existing.getCreator().getUserId());
//        factory.setName(existing.getName());
//        factoryDao.create(factory);
//    }

    @Test
    public void shouldUpdateFactory() throws Exception {
        final FactoryImpl update = factories[0];
        final String userId = update.getCreator().getUserId();
        update.setName("new-name");
        update.setV("5_0");
        final long currentTime = System.currentTimeMillis();
        update.setPolicies(new PoliciesImpl("ref", "match", "per-click", currentTime, currentTime + 1000));
        update.setCreator(new AuthorImpl(userId, currentTime));
        update.setButton(new ButtonImpl(new ButtonAttributesImpl("green", "icon", "opacity 0.9", true),
                                        Button.Type.NOLOGO));
        update.getIde().getOnAppClosed().getActions().add(new ActionImpl("remove file", ImmutableMap.of("file1", "/che/core/pom.xml")));
        update.getIde().getOnAppLoaded().getActions().add(new ActionImpl("edit file", ImmutableMap.of("file2", "/che/core/pom.xml")));
        update.getIde().getOnProjectsLoaded().getActions().add(new ActionImpl("open file", ImmutableMap.of("file2", "/che/pom.xml")));
        factoryDao.update(update);

        assertEquals(factoryDao.getById(update.getId()), update);
    }

// TODO fix after issue: https://github.com/eclipse/che/issues/2110
//    @Test(expectedExceptions = ConflictException.class)
//    public void shouldThrowConflictExceptionWhenUpdateFactoryWithExistingNameAndUserId() throws Exception {
//        final FactoryImpl update = factories[0];
//        update.setName(factories[1].getName());
//        update.getCreator().setUserId(factories[1].getCreator().getUserId());
//        factoryDao.update(update);
//    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeWhenFactoryUpdateIsNull() throws Exception {
        factoryDao.update(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenUpdatingNonExistingFactory() throws Exception {
        factoryDao.update(createFactory(10, users[0].getId()));
    }

    @Test
    public void shouldGetFactoryById() throws Exception {
        final FactoryImpl factory = factories[0];

        assertEquals(factoryDao.getById(factory.getId()), factory);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeWhenGettingFactoryByNullId() throws Exception {
        factoryDao.getById(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenFactoryWithGivenIdDoesNotExist() throws Exception {
        factoryDao.getById("non-existing");
    }

    @Test
    public void shouldGetFactoryByIdAttribute() throws Exception {
        final FactoryImpl factory = factories[0];
        final List<Pair<String, String>> attributes = ImmutableList.of(Pair.of("id", factory.getId()));
        final List<FactoryImpl> result = factoryDao.getByAttribute(1, 0, attributes);

        assertEquals(new HashSet<>(result), ImmutableSet.of(factory));
    }

    @Test(dependsOnMethods = "shouldUpdateFactory")
    public void shouldFindFactoryByEmbeddedAttributes() throws Exception {
        final List<Pair<String, String>> attributes = ImmutableList.of(Pair.of("policies.match", "match"),
                                                                       Pair.of("policies.create", "perClick"),
                                                                       Pair.of("workspace.defaultEnv", "env1"));
        final FactoryImpl factory1 = factories[1];
        final FactoryImpl factory3 = factories[3];
        factory1.getPolicies().setCreate("perAccount");
        factory3.getPolicies().setMatch("update");
        factoryDao.update(factory1);
        factoryDao.update(factory3);
        final List<FactoryImpl> result = factoryDao.getByAttribute(factories.length, 0, attributes);

        assertEquals(new HashSet<>(result), ImmutableSet.of(factories[0], factories[2], factories[4]));
    }

    @Test
    public void shouldFindAllFactoriesWhenAttributesNotSpecified() throws Exception {
        final List<Pair<String, String>> attributes = emptyList();
        final List<FactoryImpl> result = factoryDao.getByAttribute(factories.length, 0, attributes);

        assertEquals(new HashSet<>(result), new HashSet<>(asList(factories)));
    }

    @Test(expectedExceptions = NotFoundException.class, dependsOnMethods = "shouldGetFactoryById")
    public void shouldRemoveFactory() throws Exception {
        final String factoryId = factories[0].getId();
        factoryDao.remove(factoryId);
        factoryDao.getById(factoryId);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeWhenRemovingNullFactory() throws Exception {
        factoryDao.remove(null);
    }

    @Test
    public void shouldDoNothingWhenRemovingNonExistingFactory() throws Exception {
        factoryDao.remove("non-existing");
    }

    private static FactoryImpl createFactory(int index, String userId) {
        final long timeMs = System.currentTimeMillis();
        final ButtonImpl factoryButton = new ButtonImpl(new ButtonAttributesImpl("red", "logo", "style", true),
                                                        Button.Type.LOGO);
        final AuthorImpl creator = new AuthorImpl(userId, timeMs);
        final PoliciesImpl policies = new PoliciesImpl("referrer", "match", "perClick", timeMs, timeMs + 1000);
        final Set<FactoryImage> images = new HashSet<>();
        final List<ActionImpl> a1 = new ArrayList<>(singletonList(new ActionImpl("id" + index, ImmutableMap.of("key1", "value1"))));
        final OnAppLoadedImpl onAppLoaded = new OnAppLoadedImpl(a1);
        final List<ActionImpl> a2 = new ArrayList<>(singletonList(new ActionImpl("id" + index, ImmutableMap.of("key2", "value2"))));
        final OnProjectsLoadedImpl onProjectsLoaded = new OnProjectsLoadedImpl(a2);
        final List<ActionImpl> a3 = new ArrayList<>(singletonList(new ActionImpl("id" + index, ImmutableMap.of("key3", "value3"))));
        final OnAppClosedImpl onAppClosed = new OnAppClosedImpl(a3);
        final IdeImpl ide = new IdeImpl(onAppLoaded, onProjectsLoaded, onAppClosed);
        final FactoryImpl factory = FactoryImpl.builder()
                                               .generateId()
                                               .setVersion("4_0")
                                               .setName("factoryName" + index)
                                               .setButton(factoryButton)
                                               .setCreator(creator)
                                               .setPolicies(policies)
                                               .setImages(images)
                                               .setIde(ide)
                                               .build();
        factory.setWorkspace(createWorkspaceConfig(index));
        return factory;
    }

    public static WorkspaceConfigImpl createWorkspaceConfig(int index) {
        // Project Sources configuration
        final SourceStorageImpl source1 = new SourceStorageImpl();
        source1.setType("type1");
        source1.setLocation("location1");
        source1.setParameters(new HashMap<>(ImmutableMap.of("param1", "value1")));
        final SourceStorageImpl source2 = new SourceStorageImpl();
        source2.setType("type2");
        source2.setLocation("location2");
        source2.setParameters(new HashMap<>(ImmutableMap.of("param4", "value1")));

        // Project Configuration
        final ProjectConfigImpl pCfg1 = new ProjectConfigImpl();
        pCfg1.setPath("/path1");
        pCfg1.setType("type1");
        pCfg1.setName("project1");
        pCfg1.setDescription("description1");
        pCfg1.getMixins().addAll(asList("mixin1", "mixin2"));
        pCfg1.setSource(source1);
        pCfg1.getAttributes().putAll(ImmutableMap.of("key1", asList("v1", "v2"), "key2", asList("v1", "v2")));

        final ProjectConfigImpl pCfg2 = new ProjectConfigImpl();
        pCfg2.setPath("/path2");
        pCfg2.setType("type2");
        pCfg2.setName("project2");
        pCfg2.setDescription("description2");
        pCfg2.getMixins().addAll(asList("mixin3", "mixin4"));
        pCfg2.setSource(source2);
        pCfg2.getAttributes().putAll(ImmutableMap.of("key3", asList("v1", "v2"), "key4", asList("v1", "v2")));

        final List<ProjectConfigImpl> projects = new ArrayList<>(asList(pCfg1, pCfg2));

        // Commands
        final CommandImpl cmd1 = new CommandImpl("name1", "cmd1", "type1");
        cmd1.getAttributes().putAll(ImmutableMap.of("key1", "value1"));
        final CommandImpl cmd2 = new CommandImpl("name2", "cmd2", "type2");
        cmd2.getAttributes().putAll(ImmutableMap.of("key4", "value4"));
        final List<CommandImpl> commands = new ArrayList<>(asList(cmd1, cmd2));

        // Machine configs
        final MachineConfig2Impl exMachine1 = new MachineConfig2Impl();
        final ServerConf2Impl serverConf1 = new ServerConf2Impl("2265", "http", singletonMap("prop1", "val"));
        final ServerConf2Impl serverConf2 = new ServerConf2Impl("2266", "ftp", singletonMap("prop1", "val"));
        exMachine1.setServers(ImmutableMap.of("ref1", serverConf1, "ref2", serverConf2));
        exMachine1.setAgents(ImmutableList.of("agent5", "agent4"));
        exMachine1.setAttributes(singletonMap("att1", "val"));

        final MachineConfig2Impl exMachine2 = new MachineConfig2Impl();
        final ServerConf2Impl serverConf3 = new ServerConf2Impl("2333", "https", singletonMap("prop2", "val"));
        final ServerConf2Impl serverConf4 = new ServerConf2Impl("2334", "wss", singletonMap("prop2", "val"));
        exMachine2.setServers(ImmutableMap.of("ref1", serverConf3, "ref2", serverConf4));
        exMachine2.setAgents(ImmutableList.of("agent2", "agent1"));
        exMachine2.setAttributes(singletonMap("att1", "val"));

        final MachineConfig2Impl exMachine3 = new MachineConfig2Impl();
        final ServerConf2Impl serverConf5 = new ServerConf2Impl("2333", "https", singletonMap("prop2", "val"));
        exMachine3.setServers(singletonMap("ref1", serverConf5));
        exMachine3.setAgents(ImmutableList.of("agent6", "agent2"));
        exMachine3.setAttributes(singletonMap("att1", "val"));


        // Environments
        final EnvironmentRecipeImpl recipe1 = new EnvironmentRecipeImpl();
        recipe1.setLocation("https://eclipse.che/Dockerfile");
        recipe1.setType("dockerfile");
        recipe1.setContentType("text/x-dockerfile");
        recipe1.setContent("content");
        final EnvironmentImpl env1 = new EnvironmentImpl();
        env1.setMachines(new HashMap<>(ImmutableMap.of("machine1", exMachine1,
                                                       "machine2", exMachine2,
                                                       "machine3", exMachine3)));
        env1.setRecipe(recipe1);

        final EnvironmentRecipeImpl recipe2 = new EnvironmentRecipeImpl();
        recipe2.setLocation("https://eclipse.che/Dockerfile");
        recipe2.setType("dockerfile");
        recipe2.setContentType("text/x-dockerfile");
        recipe2.setContent("content");
        final EnvironmentImpl env2 = new EnvironmentImpl();
        env2.setMachines(new HashMap<>(ImmutableMap.of("machine1", exMachine1,
                                                       "machine3", exMachine3)));
        env2.setRecipe(recipe2);

        final Map<String, EnvironmentImpl> environments = ImmutableMap.of("env1", env1, "env2", env2);

        // Workspace configuration
        final WorkspaceConfigImpl wCfg = new WorkspaceConfigImpl();
        wCfg.setDefaultEnv("env1");
        wCfg.setName("cfgName_" + index);
        wCfg.setDescription("description");
        wCfg.setCommands(commands);
        wCfg.setProjects(projects);
        wCfg.setEnvironments(environments);

        return wCfg;
    }
}

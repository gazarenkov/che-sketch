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
package org.eclipse.che.api.workspace.server;

import com.google.inject.Inject;

import org.eclipse.che.account.api.AccountManager;
import org.eclipse.che.account.shared.model.Account;
import org.eclipse.che.api.core.BadRequestException;
import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.NotFoundException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.core.model.workspace.Workspace;
import org.eclipse.che.api.core.model.workspace.WorkspaceConfig;
import org.eclipse.che.api.core.model.workspace.Runtime;
import org.eclipse.che.api.core.model.workspace.WorkspaceStatus;
import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.machine.server.exception.SourceNotFoundException;
import org.eclipse.che.api.workspace.server.event.WorkspaceCreatedEvent;
import org.eclipse.che.api.workspace.server.model.impl.WorkspaceConfigImpl;
import org.eclipse.che.api.workspace.server.model.impl.WorkspaceImpl;
import org.eclipse.che.api.workspace.server.spi.WorkspaceDao;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.commons.env.EnvironmentContext;
import org.eclipse.che.commons.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Throwables.getCausalChain;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.Collections.emptyMap;
import static java.util.Objects.requireNonNull;
import static org.eclipse.che.api.core.model.workspace.WorkspaceStatus.RUNNING;
import static org.eclipse.che.api.workspace.shared.Constants.WORKSPACE_STOPPED_BY;

/**
 * Facade for Workspace related operations.
 *
 * @author gazarenkov
 * @author Alexander Garagatyi
 * @author Yevhenii Voevodin
 * @author Igor Vinokur
 */
@Singleton
public class WorkspaceManager {

    private static final Logger LOG = LoggerFactory.getLogger(WorkspaceManager.class);

    /** This attribute describes time when workspace was created. */
    public static final String CREATED_ATTRIBUTE_NAME = "created";
    /** This attribute describes time when workspace was last updated or started/stopped/recovered. */
    public static final String UPDATED_ATTRIBUTE_NAME = "updated";

    private final WorkspaceDao                           workspaceDao;
//    private final SnapshotDao                            snapshotDao;
    private final WorkspaceRuntimes                      runtimes;
    private final AccountManager                         accountManager;
    private final WorkspaceSharedPool                    sharedPool;
    private final EventService                           eventService;
    private final WorkspaceValidator            validator;

    // cache
    private final ConcurrentMap<String, WorkspaceStatus> states;

    @Inject
    public WorkspaceManager(WorkspaceDao workspaceDao,
                            WorkspaceRuntimes runtimes,
                            EventService eventService,
                            AccountManager accountManager,
//                            WorkspaceValidator validator,
//                            @Named("che.workspace.auto_snapshot") boolean defaultAutoSnapshot,
//                            @Named("che.workspace.auto_restore") boolean defaultAutoRestore,
//                            SnapshotDao snapshotDao,
                            WorkspaceSharedPool sharedPool) {
        this.workspaceDao = workspaceDao;
        this.runtimes = runtimes;
        this.accountManager = accountManager;
        this.eventService = eventService;
//        this.defaultAutoSnapshot = defaultAutoSnapshot;
//        this.defaultAutoRestore = defaultAutoRestore;
        this.sharedPool = sharedPool;
        this.states = new ConcurrentHashMap<>();
        this.validator = new DefaultWorkspaceValidator(runtimes);
    }

    /**
     * Creates a new {@link Workspace} instance based on
     * the given configuration and the instance attributes.
     *
     * @param config
     *         the workspace config to create the new workspace instance
     * @param namespace
     *         workspace name is unique in this namespace
     * @param attributes
     *         workspace instance attributes
     * @return new workspace instance
     * @throws NullPointerException
     *         when either {@code config} or {@code owner} is null
     * @throws NotFoundException
     *         when account with given id was not found
     * @throws ConflictException
     *         when any conflict occurs (e.g Workspace with such name already exists for {@code owner})
     * @throws ServerException
     *         when any other error occurs
     */
    public WorkspaceImpl createWorkspace(WorkspaceConfig config,
                                         String namespace,
                                         Map<String, String> attributes) throws ServerException,
                                                                                NotFoundException,
                                                                                ConflictException,
                                                                                BadRequestException {
        requireNonNull(config, "Required non-null config");
        requireNonNull(namespace, "Required non-null namespace");
        //requireNonNull(attributes, "Required non-null attributes");

        validator.validateConfig(config);
        if(attributes == null)
            attributes = emptyMap();

        return doCreateWorkspace(config, accountManager.getByName(namespace), attributes, false);
    }

    /**
     * Gets workspace by composite key.
     *
     * <p> Key rules:
     * <ul>
     * <li>If it doesn't contain <b>:</b> character then that key is id(e.g. workspace123456)
     * <li>If it contains <b>:</b> character then that key is combination of user name and workspace name
     * <li><b></>:workspace_name</b> is valid abstract key and user will be detected from Environment.
     * <li><b>user_name:</b> is not valid abstract key
     * </ul>
     *
     * @param key
     *         composite key(e.g. workspace 'id' or 'namespace:name')
     * @return the workspace instance
     * @throws NullPointerException
     *         when {@code key} is null
     * @throws NotFoundException
     *         when workspace doesn't exist
     * @throws ServerException
     *         when any server error occurs
     */
    public WorkspaceImpl getWorkspace(String key) throws NotFoundException, ServerException {
        requireNonNull(key, "Required non-null workspace key");
        return normalizeState(getByKey(key), true);
    }

    /**
     * Gets workspace by name and owner.
     *
     * <p>Returned instance status is either {@link WorkspaceStatus#STOPPED}
     * or  defined by its runtime(if exists).
     *
     * @param name
     *         the name of the workspace
     * @param namespace
     *         the owner of the workspace
     * @return the workspace instance
     * @throws NotFoundException
     *         when workspace with such id doesn't exist
     * @throws ServerException
     *         when any server error occurs
     */
    public WorkspaceImpl getWorkspace(String name, String namespace) throws NotFoundException, ServerException {
        requireNonNull(name, "Required non-null workspace name");
        requireNonNull(namespace, "Required non-null workspace owner");
        //return getByKey(namespace + ":" +name);
        return normalizeState(workspaceDao.get(name, namespace), true);
    }

    /**
     * Gets list of workspaces which user can read. Runtimes are included
     *
     * @deprecated use #getWorkspaces(String user, boolean includeRuntimes) instead
     *
     * @param user
     *         the id of the user
     * @return the list of workspaces or empty list if user can't read any workspace
     * @throws NullPointerException
     *         when {@code user} is null
     * @throws ServerException
     *         when any server error occurs while getting workspaces with {@link WorkspaceDao#getWorkspaces(String)}
     */
    @Deprecated
    public List<WorkspaceImpl> getWorkspaces(String user) throws ServerException {
        return getWorkspaces(user, true);
    }

    /**
     * Gets list of workspaces which user can read
     *
     * <p>Returned workspaces have either {@link WorkspaceStatus#STOPPED} status
     * or status defined by their runtime instances(if those exist).
     *
     * @param user
     *         the id of the user
     * @param includeRuntimes
     *         if <code>true</code>, will fetch runtime info for workspaces.
     *         If <code>false</code>, will not fetch runtime info.
     * @return the list of workspaces or empty list if user can't read any workspace
     * @throws NullPointerException
     *         when {@code user} is null
     * @throws ServerException
     *         when any server error occurs while getting workspaces with {@link WorkspaceDao#getWorkspaces(String)}
     */
    public List<WorkspaceImpl> getWorkspaces(String user, boolean includeRuntimes) throws ServerException {
        requireNonNull(user, "Required non-null user id");
        final List<WorkspaceImpl> workspaces = workspaceDao.getWorkspaces(user);
        for (WorkspaceImpl workspace : workspaces) {
            normalizeState(workspace, includeRuntimes);
        }
        return workspaces;
    }

    /**
     * Gets list of workspaces which has given namespace. Runtimes are included
     *
     * @deprecated use #getByNamespace(String user, boolean includeRuntimes) instead
     *
     * @param namespace
     *         the namespace to find workspaces
     * @return the list of workspaces or empty list if no matches
     * @throws NullPointerException
     *         when {@code namespace} is null
     * @throws ServerException
     *         when any server error occurs while getting workspaces with {@link WorkspaceDao#getByNamespace(String)}
     */
    @Deprecated
    public List<WorkspaceImpl> getByNamespace(String namespace) throws ServerException {
        return getByNamespace(namespace, true);
    }

    /**
     * Gets list of workspaces which has given namespace
     *
     * <p>Returned workspaces have either {@link WorkspaceStatus#STOPPED} status
     * or status defined by their runtime instances(if those exist).
     *
     * @param namespace
     *         the namespace to find workspaces
     * @param includeRuntimes
     *         if <code>true</code>, will fetch runtime info for workspaces.
     *         If <code>false</code>, will not fetch runtime info.
     * @return the list of workspaces or empty list if no matches
     * @throws NullPointerException
     *         when {@code namespace} is null
     * @throws ServerException
     *         when any server error occurs while getting workspaces with {@link WorkspaceDao#getByNamespace(String)}
     */
    public List<WorkspaceImpl> getByNamespace(String namespace, boolean includeRuntimes) throws ServerException {
        requireNonNull(namespace, "Required non-null namespace");
        final List<WorkspaceImpl> workspaces = workspaceDao.getByNamespace(namespace);
        for (WorkspaceImpl workspace : workspaces) {
            normalizeState(workspace, includeRuntimes);
        }
        return workspaces;
    }

    /**
     * Updates an existing workspace with a new configuration.
     *
     * <p>Replace strategy is used for workspace update, it means
     * that existing workspace data will be replaced with given {@code update}.
     *
     * @param update
     *         workspace update
     * @return updated instance of the workspace
     * @throws NullPointerException
     *         when either {@code workspaceId} or {@code update} is null
     * @throws NotFoundException
     *         when workspace with given id doesn't exist
     * @throws ConflictException
     *         when any conflict occurs (e.g Workspace with such name already exists in {@code namespace})
     * @throws ServerException
     *         when any other error occurs
     */
    public WorkspaceImpl updateWorkspace(String id, Workspace update) throws ConflictException,
                                                                             ServerException,
                                                                             NotFoundException,
                                                                             BadRequestException {
        requireNonNull(id, "Required non-null workspace id");
        requireNonNull(update, "Required non-null workspace update");
        validator.validateConfig(update.getConfig());
        final WorkspaceImpl workspace = workspaceDao.get(id);
        workspace.setConfig(new WorkspaceConfigImpl(update.getConfig()));
        update.getAttributes().put(UPDATED_ATTRIBUTE_NAME, Long.toString(currentTimeMillis()));
        workspace.setAttributes(update.getAttributes());
        workspace.setTemporary(update.isTemporary());
        return normalizeState(workspaceDao.update(workspace), true);
    }

    /**
     * Removes workspace with specified identifier.
     *
     * <p>Does not remove the workspace if it has the runtime,
     * throws {@link ConflictException} in this case.
     * Won't throw any exception if workspace doesn't exist.
     *
     * @param workspaceId
     *         workspace id to remove workspace
     * @throws ConflictException
     *         when workspace has runtime
     * @throws ServerException
     *         when any server error occurs
     * @throws NullPointerException
     *         when {@code workspaceId} is null
     */
    public void removeWorkspace(String workspaceId) throws ConflictException, ServerException {
        requireNonNull(workspaceId, "Required non-null workspace id");
        if (runtimes.hasRuntime(workspaceId)) {
            throw new ConflictException(format("The workspace '%s' is currently running and cannot be removed.",
                                               workspaceId));
        }

        workspaceDao.remove(workspaceId);
        LOG.info("Workspace '{}' removed by user '{}'", workspaceId, sessionUserNameOr("undefined"));
    }

    /**
     * Asynchronously starts certain workspace with specified environment and account.
     *
     * @param workspaceId
     *         identifier of workspace which should be started
     * @param envName
     *         name of environment or null, when default environment should be used
     * @param options
     *         if <code>true</code> workspace will be restored from snapshot if snapshot exists,
     *         otherwise (if snapshot does not exist) workspace will be started from default source.
     *         If <code>false</code> workspace will be started from default source,
     *         even if auto-restore is enabled and snapshot exists.
     *         If <code>null</code> workspace will be restored from snapshot
     *         only if workspace has `auto-restore` attribute set to <code>true</code>,
     *         or system wide parameter `auto-restore` is enabled and snapshot exists.
     *         <p>
     *         This parameter has the highest priority to define if it is needed to restore from snapshot or not.
     *         If it is not defined workspace `auto-restore` attribute will be checked, then if last is not defined
     *         system wide `auto-restore` parameter will be checked.
     * @return starting workspace
     * @throws NullPointerException
     *         when {@code workspaceId} is null
     * @throws NotFoundException
     *         when workspace with given {@code workspaceId} doesn't exist
     * @throws ServerException
     *         when any other error occurs during workspace start
     */
    public WorkspaceImpl startWorkspace(String workspaceId,
                                        @Nullable String envName,
                                        @Nullable Map<String, String> options) throws NotFoundException,
                                                                          ServerException,
                                                                          ConflictException {
        requireNonNull(workspaceId, "Required non-null workspace id");
        final WorkspaceImpl workspace = workspaceDao.get(workspaceId);
        //final String restoreAttr = workspace.getAttributes().get(AUTO_RESTORE_FROM_SNAPSHOT);
        //final boolean autoRestore = restoreAttr == null ? defaultAutoRestore : parseBoolean(restoreAttr);
        //startAsync(workspace, envName, firstNonNull(restore, autoRestore));
                                       //&& !getSnapshot(workspaceId).isEmpty());
        startAsync(workspace, envName, options);
        return normalizeState(workspace, true);
    }

    /**
     * Asynchronously starts workspace from the given configuration.
     *
     * @param config
     *         workspace configuration from which workspace is created and started
     * @param namespace
     *         workspace name is unique in this namespace
     * @return starting workspace
     * @throws NullPointerException
     *         when {@code workspaceId} is null
     * @throws NotFoundException
     *         when workspace with given {@code workspaceId} doesn't exist
     * @throws ServerException
     *         when any other error occurs during workspace start
     */
    public WorkspaceImpl startWorkspace(WorkspaceConfig config,
                                        String namespace,
                                        boolean isTemporary,
                                        Map<String, String> options) throws ServerException,
                                                                    NotFoundException,
                                                                    ConflictException, BadRequestException {
        requireNonNull(config, "Required non-null configuration");
        requireNonNull(namespace, "Required non-null namespace");
        validator.validateConfig(config);
        final WorkspaceImpl workspace = doCreateWorkspace(config,
                                                          accountManager.getByName(namespace),
                                                          emptyMap(),
                                                          isTemporary);
        startAsync(workspace, workspace.getConfig().getDefaultEnv(), options);
        return normalizeState(workspace, true);
    }



    /**
     * Asynchronously stops the workspace.
     *
     * @param workspaceId
     *         the id of the workspace to stop
     * @throws ServerException
     *         when any server error occurs
     * @throws NullPointerException
     *         when {@code workspaceId} is null
     * @throws NotFoundException
     *         when workspace {@code workspaceId} doesn't have runtime
     */
    public void stopWorkspace(String workspaceId, Map<String, String> options) throws ServerException,
                                                         NotFoundException,
                                                         ConflictException {

        requireNonNull(workspaceId, "Required non-null workspace id");
        final WorkspaceImpl workspace = normalizeState(workspaceDao.get(workspaceId), true);
        checkWorkspaceIsRunning(workspace, "stop");
        stopAsync(workspace, options);
    }


    /** Asynchronously starts given workspace. */
    private void startAsync(WorkspaceImpl workspace,
                            String envName,
                            Map <String, String> options) throws ConflictException,
                                                    NotFoundException,
                                                    ServerException {
        if (envName != null && !workspace.getConfig().getEnvironments().containsKey(envName)) {
            throw new NotFoundException(format("Workspace '%s:%s' doesn't contain environment '%s'",
                                               workspace.getNamespace(),
                                               workspace.getConfig().getName(),
                                               envName));
        }
        workspace.getAttributes().put(UPDATED_ATTRIBUTE_NAME, Long.toString(currentTimeMillis()));
        workspaceDao.update(workspace);
        final String env = firstNonNull(envName, workspace.getConfig().getDefaultEnv());

        states.put(workspace.getId(), WorkspaceStatus.STARTING);
        // barrier, safely doesn't allow to start the workspace twice
        final Future<Runtime> descriptor = runtimes.startAsync(workspace, env, options);

        sharedPool.execute(() -> {
            try {
                descriptor.get();

                states.put(workspace.getId(), WorkspaceStatus.RUNNING);

                LOG.info("Workspace '{}:{}' with id '{}' started by user '{}'",
                         workspace.getNamespace(),
                         workspace.getConfig().getName(),
                         workspace.getId(),
                         sessionUserNameOr("undefined"));
            } catch (Exception ex) {
                if (workspace.isTemporary()) {
                    removeWorkspaceQuietly(workspace);
                }
                for (Throwable cause : getCausalChain(ex)) {
                    if (cause instanceof SourceNotFoundException) {
                        return;
                    }
                }
                LOG.error(ex.getLocalizedMessage(), ex);
            }
        });
    }

    private void stopAsync(WorkspaceImpl workspace, Map<String, String> options) throws ConflictException {
        checkWorkspaceIsRunning(workspace, "stop");
        states.put(workspace.getId(), WorkspaceStatus.STOPPING);
        sharedPool.execute(() -> {
            final String stoppedBy = sessionUserNameOr(workspace.getAttributes().get(WORKSPACE_STOPPED_BY));
            LOG.info("Workspace '{}:{}' with id '{}' is being stopped by user '{}'",
                     workspace.getNamespace(),
                     workspace.getConfig().getName(),
                     workspace.getId(),
                     firstNonNull(stoppedBy, "undefined"));

//            final boolean snapshotBeforeStop;
//            if (workspace.isTemporary()) {
//                snapshotBeforeStop = false;
//            } else if (createSnapshot != null) {
//                snapshotBeforeStop = createSnapshot;
//            } else if (workspace.getAttributes().containsKey(AUTO_CREATE_SNAPSHOT)) {
//                snapshotBeforeStop = parseBoolean(workspace.getAttributes().get(AUTO_CREATE_SNAPSHOT));
//            } else {
//                snapshotBeforeStop = defaultAutoSnapshot;
//            }
//
//            if (snapshotBeforeStop) {
//                try {
//                    runtimes.snapshot(workspace.getId());
//                } catch (ConflictException | NotFoundException | ServerException x) {
//                    LOG.warn("Could not create a snapshot of the workspace '{}:{}' " +
//                             "with workspace id '{}'. The workspace will be stopped",
//                             workspace.getNamespace(),
//                             workspace.getConfig().getName(),
//                             workspace.getId());
//                }
//            }


            try {
                runtimes.stop(workspace.getId(), options);


                if (!workspace.isTemporary()) {
                    workspace.getAttributes().put(UPDATED_ATTRIBUTE_NAME, Long.toString(currentTimeMillis()));
                    workspaceDao.update(workspace);
                }
                LOG.info("Workspace '{}:{}' with id '{}' stopped by user '{}'",
                         workspace.getNamespace(),
                         workspace.getConfig().getName(),
                         workspace.getId(),
                         firstNonNull(stoppedBy, "undefined"));

                states.put(workspace.getId(), WorkspaceStatus.STOPPED);

            } catch (RuntimeException | ConflictException | NotFoundException | ServerException ex) {
                LOG.error(ex.getLocalizedMessage(), ex);
            } finally {
                if (workspace.isTemporary()) {
                    removeWorkspaceQuietly(workspace);
                }
            }
        });
    }

//    private void startAsync(OldMachineConfig machineConfig, String workspaceId) {
//        sharedPool.execute(() -> {
//            try {
//                runtimes.startMachine(workspaceId, machineConfig);
//            } catch (ApiException | EnvironmentException e) {
//                LOG.error(e.getLocalizedMessage(), e);
//            }
//        });
//    }

    private void checkWorkspaceIsRunning(WorkspaceImpl workspace, String operation) throws ConflictException {
        if (workspace.getStatus() != RUNNING) {
            throw new ConflictException(format("Could not %s the workspace '%s:%s' because its status is '%s'.",
                                               operation,
                                               workspace.getNamespace(),
                                               workspace.getConfig().getName(),
                                               workspace.getStatus()));
        }
    }

    private void removeWorkspaceQuietly(Workspace workspace) {
        try {
            workspaceDao.remove(workspace.getId());
        } catch (ServerException x) {
            LOG.error("Unable to remove temporary workspace '{}'", workspace.getId());
        }
    }

    private Subject sessionUser() {
        return EnvironmentContext.getCurrent().getSubject();
    }

    private String sessionUserNameOr(String nameIfNoUser) {
        final Subject subject;
        if (EnvironmentContext.getCurrent() != null && (subject = EnvironmentContext.getCurrent().getSubject()) != null) {
            return subject.getUserName();
        }
        return nameIfNoUser;
    }

    private WorkspaceImpl normalizeState(WorkspaceImpl workspace, boolean includeRuntimes) throws ServerException {
        WorkspaceStatus status = states.get(workspace.getId());
        if (status != null) {
            if(status.equals(WorkspaceStatus.RUNNING) && includeRuntimes) {
                try {
                    workspace.setRuntime(runtimes.get(workspace.getId()));
                } catch (NotFoundException e) {
                    LOG.error("Workspace " + workspace.getId() + " has RUNNING state but no runtime!");
                }
            }
            workspace.setStatus(status);
        } else {
            workspace.setStatus(WorkspaceStatus.STOPPED);
        }

        return workspace;
    }

    private WorkspaceImpl doCreateWorkspace(WorkspaceConfig config,
                                            Account account,
                                            Map<String, String> attributes,
                                            boolean isTemporary) throws NotFoundException,
                                                                        ServerException,
                                                                        ConflictException {
        final WorkspaceImpl workspace = WorkspaceImpl.builder()
                                                     .generateId()
                                                     .setConfig(config)
                                                     .setAccount(account)
                                                     .setAttributes(attributes)
                                                     .setTemporary(isTemporary)
                                                     .build();
        workspace.setStatus(WorkspaceStatus.STOPPED);
        workspace.getAttributes().put(CREATED_ATTRIBUTE_NAME, Long.toString(currentTimeMillis()));

        workspaceDao.create(workspace);
        LOG.info("Workspace '{}:{}' with id '{}' created by user '{}'",
                 account.getName(),
                 workspace.getConfig().getName(),
                 workspace.getId(),
                 sessionUserNameOr("undefined"));
        eventService.publish(new WorkspaceCreatedEvent(workspace));
        return workspace;
    }

    /*
    * Get workspace using composite key.
    *
    */
    private WorkspaceImpl getByKey(String key) throws NotFoundException, ServerException {
        String[] parts = key.split(":", -1); // -1 is to prevent skipping trailing part
        WorkspaceImpl ws;
        if (parts.length == 1) {
            ws = workspaceDao.get(key);
        } else {
            final String nsPart = parts[0];
            final String wsName = parts[1];
            final String namespace = nsPart.isEmpty() ? sessionUser().getUserName() : nsPart;
            ws = workspaceDao.get(wsName, namespace);
        }
        return ws;
    }
}

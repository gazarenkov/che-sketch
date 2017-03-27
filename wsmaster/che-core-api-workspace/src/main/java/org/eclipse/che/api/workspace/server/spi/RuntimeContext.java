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
package org.eclipse.che.api.workspace.server.spi;

import org.eclipse.che.api.core.ApiException;
import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.core.model.workspace.WorkspaceStatus;
import org.eclipse.che.api.core.model.workspace.config.Environment;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * A Context for running Workspace's Runtime
 * @author gazarenkov
 */
public abstract class RuntimeContext {

    protected final Environment               environment;
    protected final InternalEnvironmentConfig internalEnv;
    protected final RuntimeIdentity           identity;
    protected final RuntimeInfrastructure     infrastructure;
    // TODO other than WorkspaceStatus impl
    protected       WorkspaceStatus           state;

    public RuntimeContext(Environment environment, RuntimeIdentity identity,
                          RuntimeInfrastructure infrastructure, URL registryEndpoint) throws ValidationException, ApiException, IOException {
        this.environment = environment;
        this.identity = identity;
        this.infrastructure = infrastructure;
        this.internalEnv = new InternalEnvironmentConfig(environment, registryEndpoint);
        this.state = WorkspaceStatus.STOPPED;
    }


    /**
     * Creates and starts Runtime.
     * In practice this method launching supposed to take unpredictable long time
     * so normally it should be launched in separated thread
     *
     * @param startOptions optional parameters
     * @return running Runtime
     */
    public final InternalRuntime start(Map<String, String> startOptions) throws ApiException {
        if(this.state != WorkspaceStatus.STOPPED)
            throw new ConflictException("Runtime is not STOPPED");
        state = WorkspaceStatus.STARTING;
        InternalRuntime runtime = internalStart(startOptions);
        state = WorkspaceStatus.RUNNING;
        return runtime;
    }

    protected abstract InternalRuntime internalStart(Map<String, String> startOptions) throws ServerException;

    /**
     * Stops Runtime
     * Presumably can take some time so considered to launch in separate thread
     * @param stopOptions
     */
    public final void stop(Map<String, String> stopOptions) throws ServerException, ConflictException {
        internalStop(stopOptions);
        state = WorkspaceStatus.STOPPED;
    }

    protected abstract void internalStop(Map<String, String> stopOptions) throws ServerException;

    /**
     * Infrastructure should assign a channel (usual WebSocket) to push long lived processes messages
     * Examples of such messages include:
     * - Statuses changes
     * - Start/Stop logs output
     * - Agent installer output
     * etc
     * It is expected that ones returning this URL implementation guarantees supporting and not changing
     * it during the whole life time of Runtime.
     * Repeating call of this method should return the same URL
     * @return URL of the channel endpoint
     * @throws NotSupportedException if implementation does not provide channel in general or for this
     * particular channel name
     */
    public abstract URL getRuntimeChannel(String name) throws NotSupportedException;


    /**
     * Runtime Identity contains information allowing uniquelly identify a Runtime
     * It is not neccessary that all of this information is used for identifying
     * Runtime outside of SPI framework (in practice workspace ID looks like enough)
     *
     * @return the RuntimeIdentity
     */
    public RuntimeIdentity getIdentity() {
        return identity;
    }

    /**
     * @return incoming Workspace Environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * @return RuntimeInfrastructure the Context created from
     */
    public RuntimeInfrastructure getInfrastructure() {
        return infrastructure;
    }

    // Returns environment with "suggested" RuntimeMachine list if no machines was declared
    // TODO need that?
//    public Environment getSuggestedEnv(Environment env) {
//        return effectiveEnv;
//    }


}

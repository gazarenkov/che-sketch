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
package org.eclipse.che.ide.workspace;

import com.google.gwt.core.client.Callback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.api.core.model.workspace.WorkspaceStatus;
import org.eclipse.che.api.promises.client.Operation;
import org.eclipse.che.api.promises.client.OperationException;
import org.eclipse.che.api.promises.client.PromiseError;
import org.eclipse.che.api.workspace.shared.dto.WorkspaceDto;
import org.eclipse.che.ide.CoreLocalizationConstant;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.component.Component;
import org.eclipse.che.ide.api.dialogs.DialogFactory;
import org.eclipse.che.ide.api.machine.MachineManager;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.api.notification.StatusNotification;
import org.eclipse.che.ide.api.preferences.PreferencesManager;
import org.eclipse.che.ide.api.workspace.WorkspaceServiceClient;
import org.eclipse.che.ide.context.BrowserQueryFieldRenderer;
import org.eclipse.che.ide.dto.DtoFactory;
import org.eclipse.che.ide.rest.AsyncRequestFactory;
import org.eclipse.che.ide.rest.DtoUnmarshallerFactory;
import org.eclipse.che.ide.ui.loaders.LoaderPresenter;
import org.eclipse.che.ide.util.loging.Log;
import org.eclipse.che.ide.websocket.MessageBusProvider;
import org.eclipse.che.ide.workspace.create.CreateWorkspacePresenter;
import org.eclipse.che.ide.workspace.start.StartWorkspacePresenter;

import static org.eclipse.che.ide.api.notification.StatusNotification.DisplayMode.FLOAT_MODE;
import static org.eclipse.che.ide.ui.loaders.LoaderPresenter.Phase.STARTING_WORKSPACE_RUNTIME;

/**
 * Performs default start of IDE - creates new or starts latest workspace.
 * Used when no {@code factory} specified.
 *
 * @author Max Shaposhnik (mshaposhnik@codenvy.com)
 *
 */
@Singleton
public class DefaultWorkspaceComponent extends WorkspaceComponent  {

    private AsyncRequestFactory asyncRequestFactory;

    @Inject
    public DefaultWorkspaceComponent(WorkspaceServiceClient workspaceServiceClient,
                                     CreateWorkspacePresenter createWorkspacePresenter,
                                     StartWorkspacePresenter startWorkspacePresenter,
                                     CoreLocalizationConstant locale,
                                     DtoUnmarshallerFactory dtoUnmarshallerFactory,
                                     EventBus eventBus,
                                     AppContext appContext,
                                     Provider<MachineManager> machineManagerProvider,
                                     NotificationManager notificationManager,
                                     MessageBusProvider messageBusProvider,
                                     BrowserQueryFieldRenderer browserQueryFieldRenderer,
                                     DialogFactory dialogFactory,
                                     PreferencesManager preferencesManager,
                                     DtoFactory dtoFactory,
                                     WorkspaceEventsHandler workspaceEventsHandler,
                                     AsyncRequestFactory asyncRequestFactory,
                                     LoaderPresenter loader) {
        super(workspaceServiceClient,
              createWorkspacePresenter,
              startWorkspacePresenter,
              locale,
              dtoUnmarshallerFactory,
              eventBus,
              appContext,
              machineManagerProvider,
              notificationManager,
              messageBusProvider,
              browserQueryFieldRenderer,
              dialogFactory,
              preferencesManager,
              dtoFactory,
              workspaceEventsHandler,
              loader);

        this.asyncRequestFactory = asyncRequestFactory;
    }

    /** {@inheritDoc} */
    @Override
    public void start(final Callback<Component, Exception> callback) {
        this.callback = callback;

        String workspaceId = browserQueryFieldRenderer.getParameterFromURLByName("workspace");

        Log.info(DefaultWorkspaceComponent.class, "WORKSPACE_ID = " + workspaceId);

        if(workspaceId == null || workspaceId.isEmpty()) {


            workspaceServiceClient.getWorkspace(browserQueryFieldRenderer.getNamespace(), browserQueryFieldRenderer.getWorkspaceName())
                                  .then(
                                          new Operation<WorkspaceDto>() {
                                              @Override
                                              public void apply(WorkspaceDto workspaceDto) throws OperationException {
                                                  handleWorkspaceEvents(workspaceDto, callback, true, false);
                                              }
                                          }).catchError(new Operation<PromiseError>() {
                @Override
                public void apply(PromiseError error) throws OperationException {
                    needToReloadComponents = true;
                    dialogFactory.createMessageDialog(locale.getWsErrorDialogTitle(),
                                                      locale.getWsErrorDialogContent(error.getMessage()),
                                                      null).show();
                }
            });

        } else {

//            asyncRequestFactory.createGetRequest(url)
//                               .header(ACCEPT, APPLICATION_JSON)
//                               .loader(loaderFactory.newLoader("Getting info about workspace..."))
//                               .send(dtoUnmarshallerFactory.newUnmarshaller(WorkspaceDto.class))
//

            workspaceServiceClient.getWorkspace(workspaceId)
                                  .then(
                                          new Operation<WorkspaceDto>() {
                                              @Override
                                              public void apply(WorkspaceDto workspaceDto) throws OperationException {
                                                  Log.info(DefaultWorkspaceComponent.class, "getWorkspace: " + workspaceDto);

                                                  if(workspaceDto.getStatus() == WorkspaceStatus.STOPPED) {
                                                      loader.show(STARTING_WORKSPACE_RUNTIME);
                                                      Log.info(DefaultWorkspaceComponent.class, "Workspace starting: " );
                                                      workspaceServiceClient
                                                              .startById(workspaceDto.getId(), workspaceDto.getConfig().getDefaultEnv(),
                                                                         false).catchError(new Operation<PromiseError>() {
                                                          @Override
                                                          public void apply(PromiseError error) throws OperationException {
                                                              notificationManager.notify(locale.startWsErrorTitle(), error.getMessage(),
                                                                                         StatusNotification.Status.FAIL, FLOAT_MODE);
                                                              loader.setError(STARTING_WORKSPACE_RUNTIME);
                                                          }
                                                      });
                                                  }


                                                  //handleWorkspaceEvents(workspaceDto, callback, true, false);
                                              }
                                          }).catchError(new Operation<PromiseError>() {
                @Override
                public void apply(PromiseError error) throws OperationException {
                    needToReloadComponents = true;
                    dialogFactory.createMessageDialog(locale.getWsErrorDialogTitle(),
                                                      locale.getWsErrorDialogContent(error.getMessage()),
                                                      null).show();
                }
            });
        }
    }

    @Override
    public void tryStartWorkspace() {
    }

}

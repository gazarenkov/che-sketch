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
package org.eclipse.che.ide.extension.machine.client.machine;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Roman Nikitenko
 */
@RunWith(MockitoJUnitRunner.class)
public class MachineStatusHandlerTest {
//    private static final String MACHINE_NAME = "machineName";
//    private static final String MACHINE_ID   = "machineId";
//    private static final String WORKSPACE_ID = "workspaceId";
//
//    //constructor mocks
//    @Mock
//    private NotificationManager         notificationManager;
//    @Mock
//    private MachineLocalizationConstant locale;
//    @Mock
//    private EntityFactory               entityFactory;
//    @Mock
//    private WorkspaceServiceClient      workspaceServiceClient;
//    @Mock
//    private AppContext                  appContext;
//
//    //additional mocks
//    @Mock
//    private OldMachineDto                              machineDto;
//    @Mock
//    private MachineEntity                           machine;
//    @Mock
//    private MachineStateEvent.Handler               handler;
//    @Mock
//    private MachineStatusChangedEvent               machineStatusChangedEvent;
//    @Mock
//    private Promise<WorkspaceDto>                   workspacePromise;
//    @Mock
//    private WorkspaceDto                            workspace;
//    @Mock
//    private RuntimeDto                     runtime;
//    @Captor
//    private ArgumentCaptor<Operation<WorkspaceDto>> workspaceCaptor;
//
//    private EventBus eventBus = new SimpleEventBus();
//    private MachineStatusHandler statusNotifier;
//
//    @Before
//    public void setUp() {
//        statusNotifier =
//                new MachineStatusHandler(eventBus, appContext, entityFactory, workspaceServiceClient, notificationManager, locale);
//        eventBus.addHandler(MachineStateEvent.TYPE, handler);
//
//        when(machine.getDisplayName()).thenReturn(MACHINE_NAME);
//        when(machineDto.getId()).thenReturn(MACHINE_ID);
//        when(entityFactory.createMachine(machineDto)).thenReturn(machine);
//        when(workspace.getRuntime()).thenReturn(runtime);
//        when(runtime.getMachines()).thenReturn(Collections.singletonList(machineDto));
//        when(machineStatusChangedEvent.getMachineId()).thenReturn(MACHINE_ID);
//        when(machineStatusChangedEvent.getWorkspaceId()).thenReturn(WORKSPACE_ID);
//        when(machineStatusChangedEvent.getMachineName()).thenReturn(MACHINE_NAME);
//        when(workspaceServiceClient.getWorkspace(WORKSPACE_ID)).thenReturn(workspacePromise);
//    }
//
//    @Test
//    public void shouldNotifyWhenDevMachineStateIsCreating() throws Exception {
//        when(machine.isDev()).thenReturn(true);
//
//        when(machineStatusChangedEvent.getEventType()).thenReturn(CREATING);
//        statusNotifier.onMachineStatusChanged(machineStatusChangedEvent);
//
//        verify(workspaceServiceClient.getWorkspace(WORKSPACE_ID)).then(workspaceCaptor.capture());
//        workspaceCaptor.getValue().apply(workspace);
//
//        verify(appContext).setWorkspace(workspace);
//        verify(handler).onMachineCreating(Matchers.<MachineStateEvent>anyObject());
//    }
//
//    @Test
//    public void shouldNotifyWhenNonDevMachineStateIsCreating() throws Exception {
//        when(machine.isDev()).thenReturn(false);
//
//        when(machineStatusChangedEvent.getEventType()).thenReturn(CREATING);
//        statusNotifier.onMachineStatusChanged(machineStatusChangedEvent);
//
//        verify(workspaceServiceClient.getWorkspace(WORKSPACE_ID)).then(workspaceCaptor.capture());
//        workspaceCaptor.getValue().apply(workspace);
//
//        verify(appContext).setWorkspace(workspace);
//        verify(handler).onMachineCreating(Matchers.<MachineStateEvent>anyObject());
//    }
//
//    @Test
//    public void shouldHandleCaseWhenDevMachineStateIsRunning() throws Exception {
//        when(machine.isDev()).thenReturn(true);
//
//        when(machineStatusChangedEvent.getEventType()).thenReturn(RUNNING);
//        statusNotifier.onMachineStatusChanged(machineStatusChangedEvent);
//
//        verify(workspaceServiceClient.getWorkspace(WORKSPACE_ID)).then(workspaceCaptor.capture());
//        workspaceCaptor.getValue().apply(workspace);
//
//        verify(appContext).setWorkspace(workspace);
//        verify(handler).onMachineRunning(Matchers.<MachineStateEvent>anyObject());
//    }
//
//    @Test
//    public void shouldHandleCaseWhenNonDevMachineStateIsRunning() throws Exception {
//        when(machine.isDev()).thenReturn(false);
//
//        when(machineStatusChangedEvent.getEventType()).thenReturn(RUNNING);
//        statusNotifier.onMachineStatusChanged(machineStatusChangedEvent);
//
//        verify(workspaceServiceClient.getWorkspace(WORKSPACE_ID)).then(workspaceCaptor.capture());
//        workspaceCaptor.getValue().apply(workspace);
//
//        verify(appContext).setWorkspace(workspace);
//        verify(handler).onMachineRunning(Matchers.<MachineStateEvent>anyObject());
//    }
//
//    @Test
//    public void shouldNotifyWhenMachineStateIsError() throws Exception {
//        when(machineStatusChangedEvent.getEventType()).thenReturn(ERROR);
//        statusNotifier.onMachineStatusChanged(machineStatusChangedEvent);
//
//        verify(workspaceServiceClient.getWorkspace(WORKSPACE_ID)).then(workspaceCaptor.capture());
//        workspaceCaptor.getValue().apply(workspace);
//
//        verify(appContext).setWorkspace(workspace);
//        verify(machineStatusChangedEvent).getErrorMessage();
//        verify(notificationManager).notify(anyString(), (StatusNotification.Status)anyObject(), anyObject());
//    }
}

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
package org.eclipse.che.ide.extension.machine.client.processes.panel;

import com.google.gwtmockito.GwtMockitoTestRunner;

import org.junit.runner.RunWith;

/**
 * @author Roman Nikitenko
 */
@RunWith(GwtMockitoTestRunner.class)
public class ProcessesPanelPresenterTest {
//    private static final String MACHINE_ID     = "machineID";
//    private static final String MACHINE_NAME   = "machineName";
//    private static final String WORKSPACE_ID   = "workspaceID";
//    private static final String PROCESS_ID     = "processID";
//    private static final String PROCESS_NAME   = "processName";
//    private static final String OUTPUT_CHANNEL = "outputChannel";
//    private static final int    PID            = 101;
//
//    @Mock
//    private CommandConsoleFactory         commandConsoleFactory;
//    @Mock
//    private ConsoleTreeContextMenuFactory consoleTreeContextMenuFactory;
//    @Mock
//    private CommandTypeRegistry           commandTypeRegistry;
//    @Mock
//    private DialogFactory                 dialogFactory;
//    @Mock
//    private WorkspaceAgent                workspaceAgent;
//    @Mock
//    private NotificationManager           notificationManager;
//    @Mock
//    private MachineLocalizationConstant   localizationConstant;
//    @Mock
//    private TerminalFactory               terminalFactory;
//    @Mock
//    private ProcessesPanelView            view;
//    @Mock
//    private MachineResources              resources;
//    @Mock
//    private AppContext                    appContext;
//    @Mock
//    private SshServiceClient              sshService;
//    @Mock
//    private EventBus                      eventBus;
//    @Mock
//    private WorkspaceDto                  workspace;
//    @Mock
//    private OutputConsole                 outputConsole;
//    @Mock
//    private MachineManager                machineManager;
//    @Mock
//    private EntityFactory                 entityFactory;
//    @Mock
//    private RuntimeDto           runtime;
//    @Mock
//    private PartStack                     partStack;
//    @Mock
//    private ExecAgentCommandManager       execAgentCommandManager;
//    @Mock
//    private MacroProcessor                macroProcessor;
//    @Mock
//    private DtoFactory                    dtoFactory;
//
//    @Mock
//    private Promise<List<MachineProcessDto>> processesPromise;
//    @Mock
//    private Promise<List<GetProcessesResponseDto>> promise;
//
//    @Captor
//    private ArgumentCaptor<AcceptsOneWidget>                   acceptsOneWidgetCaptor;
//    @Captor
//    private ArgumentCaptor<Operation<List<MachineProcessDto>>> processesCaptor;
//    @Captor
//    private ArgumentCaptor<MachineStateEvent.Handler>          machineStateHandlerCaptor;
//    @Captor
//    private ArgumentCaptor<Operation<PromiseError>>            errorOperation;
//
//    private ProcessesPanelPresenter presenter;
//
//    @Before
//    public void setUp() {
//        DevMachine devMachine = mock(DevMachine.class);
//        when(devMachine.getId()).thenReturn(WORKSPACE_ID);
//        when(appContext.getDevMachine()).thenReturn(devMachine);
//        when(appContext.getWorkspace()).thenReturn(workspace);
//        when(workspace.getRuntime()).thenReturn(runtime);
//
//        when(processesPromise.then(Matchers.<Operation<List<MachineProcessDto>>>anyObject())).thenReturn(processesPromise);
//        when(commandConsoleFactory.create(anyString())).thenReturn(mock(OutputConsole.class));
//
//        when(appContext.getWorkspaceId()).thenReturn(WORKSPACE_ID);
//        when(workspaceAgent.getPartStack(eq(PartStackType.INFORMATION))).thenReturn(partStack);
//
//        when(execAgentCommandManager.getProcesses(anyString(), anyBoolean())).thenReturn(promise);
//        when(promise.then(any(Operation.class))).thenReturn(promise);
//
//        presenter = new ProcessesPanelPresenter(view,
//                                                localizationConstant,
//                                                resources,
//                                                eventBus,
//                                                workspaceAgent,
//                                                appContext,
//                                                notificationManager,
//                                                entityFactory,
//                                                terminalFactory,
//                                                commandConsoleFactory,
//                                                dialogFactory,
//                                                consoleTreeContextMenuFactory,
//                                                commandTypeRegistry,
//                                                sshService,
//                                                execAgentCommandManager,
//                                                macroProcessor,
//                                                dtoFactory);
//    }
//
//    @Test
//    public void shouldAddMachineWhenMachineCreating() throws Exception {
//        MachineEntity machine = mock(MachineEntity.class);
//        OldMachineConfigDto machineConfigDto = mock(OldMachineConfigDto.class);
//        OutputConsole outputConsole = mock(OutputConsole.class);
//        when(machineConfigDto.getName()).thenReturn(MACHINE_NAME);
//        when(machine.getConfig()).thenReturn(machineConfigDto);
//        when(appContext.getWorkspaceId()).thenReturn(WORKSPACE_ID);
//        when(commandConsoleFactory.create(eq(MACHINE_NAME))).thenReturn(outputConsole);
//
//        MachineStateEvent machineStateEvent = mock(MachineStateEvent.class);
//        when(machineStateEvent.getMachine()).thenReturn(machine);
//        verify(eventBus, times(8)).addHandler(anyObject(), machineStateHandlerCaptor.capture());
//        MachineStateEvent.Handler machineStateHandler = machineStateHandlerCaptor.getAllValues().get(0);
//        machineStateHandler.onMachineCreating(machineStateEvent);
//
//        verify(outputConsole).go(acceptsOneWidgetCaptor.capture());
//        IsWidget widget = mock(IsWidget.class);
//        acceptsOneWidgetCaptor.getValue().setWidget(widget);
//
//        verify(commandConsoleFactory).create(eq(MACHINE_NAME));
//        verify(view).addWidget(anyString(), anyString(), anyObject(), anyObject(), anyBoolean());
//        verify(view).setProcessesData(eq(presenter.rootNode));
//    }
//
//    @Test
//    public void shouldShowErrorWhenMachineNodeIsNull() throws Exception {
//        List<ProcessTreeNode> children = new ArrayList<>();
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//
//        OutputConsole outputConsole = mock(OutputConsole.class);
//
//        presenter.addCommandOutput(MACHINE_ID, outputConsole);
//        verify(notificationManager).notify(anyString(), anyString(), any(StatusNotification.Status.class), any(DisplayMode.class));
//        verify(localizationConstant, times(2)).machineNotFound(eq(MACHINE_ID));
//    }
//
//    @Test
//    public void shouldAddCommand() throws Exception {
//        ProcessTreeNode machineNode = mock(ProcessTreeNode.class);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//        List<ProcessTreeNode> children = new ArrayList<>();
//        children.add(machineNode);
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//
//        presenter.addCommandOutput(MACHINE_ID, outputConsole);
//
//        verify(view, never()).hideProcessOutput(anyString());
//
//        verify(outputConsole).go(acceptsOneWidgetCaptor.capture());
//        IsWidget widget = mock(IsWidget.class);
//        acceptsOneWidgetCaptor.getValue().setWidget(widget);
//
//        verify(view).addProcessNode(anyObject());
//        verify(view).addWidget(anyString(), anyString(), anyObject(), eq(widget), anyBoolean());
//        verify(view, times(2)).selectNode(anyObject());
//        verify(view).setProcessesData(anyObject());
//        verify(view).getNodeById(anyString());
//        verify(view).setStopButtonVisibility(anyString(), anyBoolean());
//    }
//
//    @Test
//    public void shouldDisplayStopProcessButtonAtAddingCommand() throws Exception {
//        ProcessTreeNode machineNode = mock(ProcessTreeNode.class);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//        List<ProcessTreeNode> children = new ArrayList<>();
//        children.add(machineNode);
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//
//        ProcessTreeNode selectedCommandNode = new ProcessTreeNode(COMMAND_NODE, null, PROCESS_NAME, null, children);
//        children.add(selectedCommandNode);
//
//        when(outputConsole.isFinished()).thenReturn(false);
//
//        presenter.consoles.clear();
//
//        presenter.addCommandOutput(MACHINE_ID, outputConsole);
//
//        verify(view, never()).hideProcessOutput(anyString());
//
//        verify(outputConsole).go(acceptsOneWidgetCaptor.capture());
//        IsWidget widget = mock(IsWidget.class);
//        acceptsOneWidgetCaptor.getValue().setWidget(widget);
//
//        verify(view).addProcessNode(anyObject());
//        verify(view).addWidget(anyString(), anyString(), anyObject(), eq(widget), anyBoolean());
//        verify(view, times(2)).selectNode(anyObject());
//        verify(view).setProcessesData(anyObject());
//        verify(view).getNodeById(anyString());
//        verify(view).setStopButtonVisibility(anyString(), eq(true));
//    }
//
//    @Test
//    public void shouldHideStopProcessButtonAtAddingCommand() throws Exception {
//        ProcessTreeNode machineNode = mock(ProcessTreeNode.class);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//        List<ProcessTreeNode> children = new ArrayList<>();
//        children.add(machineNode);
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//
//        ProcessTreeNode selectedCommandNode = new ProcessTreeNode(COMMAND_NODE, null, PROCESS_NAME, null, children);
//        children.add(selectedCommandNode);
//
//        when(outputConsole.isFinished()).thenReturn(true);
//
//        presenter.consoles.clear();
//
//        presenter.addCommandOutput(MACHINE_ID, outputConsole);
//
//        verify(view, never()).hideProcessOutput(anyString());
//
//        verify(outputConsole).go(acceptsOneWidgetCaptor.capture());
//        IsWidget widget = mock(IsWidget.class);
//        acceptsOneWidgetCaptor.getValue().setWidget(widget);
//        verify(view).addProcessNode(anyObject());
//
//        verify(view).addWidget(anyString(), anyString(), anyObject(), eq(widget), anyBoolean());
//        verify(view, times(2)).selectNode(anyObject());
//        verify(view).setProcessesData(anyObject());
//        verify(view).getNodeById(anyString());
//        verify(view).setStopButtonVisibility(anyString(), eq(false));
//    }
//
//    @Test
//    public void shouldHideStopProcessButtonAtAddingTerminal() throws Exception {
//        OldMachineDto machineDto = mock(OldMachineDto.class);
//        MachineEntity machine = mock(MachineEntity.class);
//        when(machine.getId()).thenReturn(MACHINE_ID);
//        OldMachineConfigDto machineConfigDto = mock(OldMachineConfigDto.class);
//        when(machine.getConfig()).thenReturn(machineConfigDto);
//        when(machineConfigDto.isDev()).thenReturn(true);
//        when(machine.getStatus()).thenReturn(MachineStatus.RUNNING);
//        List<OldMachineDto> machines = new ArrayList<>(1);
//        machines.add(machineDto);
//        when(runtime.getMachines()).thenReturn(machines);
//        when(entityFactory.createMachine(machineDto)).thenReturn(machine);
//
//        ProcessTreeNode machineNode = mock(ProcessTreeNode.class);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//        List<ProcessTreeNode> children = new ArrayList<>();
//        children.add(machineNode);
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//
//        TerminalPresenter terminal = mock(TerminalPresenter.class);
//        when(terminalFactory.create(machine, presenter)).thenReturn(terminal);
//        IsWidget terminalWidget = mock(IsWidget.class);
//        when(terminal.getView()).thenReturn(terminalWidget);
//
//        presenter.addCommandOutput(MACHINE_ID, outputConsole);
//        presenter.onAddTerminal(MACHINE_ID, presenter);
//
//        verify(terminalFactory).create(eq(machine), eq(presenter));
//        verify(terminal).getView();
//        verify(view, times(2)).setProcessesData(anyObject());
//        verify(view, times(2)).selectNode(anyObject());
//        verify(view).addWidget(anyString(), anyString(), anyObject(), eq(terminalWidget), anyBoolean());
//        verify(view, times(1)).addProcessNode(anyObject());
//        verify(terminal).setVisible(eq(true));
//        verify(terminal).connect();
//        verify(terminal).setListener(anyObject());
//        verify(view).setStopButtonVisibility(anyString(), eq(false));
//    }
//
//    @Test
//    public void shouldReplaceCommandOutput() throws Exception {
//        MachineEntity machine = mock(MachineEntity.class);
//        when(machine.getId()).thenReturn(MACHINE_ID);
//        OldMachineConfigDto machineConfigDto = mock(OldMachineConfigDto.class);
//        when(machine.getConfig()).thenReturn(machineConfigDto);
//
//        List<ProcessTreeNode> children = new ArrayList<>();
//        ProcessTreeNode commandNode = new ProcessTreeNode(COMMAND_NODE, null, PROCESS_NAME, null, children);
//        children.add(commandNode);
//        ProcessTreeNode machineNode = new ProcessTreeNode(MACHINE_NODE, null, machine, null, children);
//        children.add(machineNode);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//
//        String commandId = commandNode.getId();
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//        presenter.consoles.put(commandId, outputConsole);
//
//        when(outputConsole.isFinished()).thenReturn(true);
//        when(outputConsole.getTitle()).thenReturn(PROCESS_NAME);
//
//        presenter.addCommandOutput(MACHINE_ID, outputConsole);
//
//        verify(outputConsole).go(acceptsOneWidgetCaptor.capture());
//        IsWidget widget = mock(IsWidget.class);
//        acceptsOneWidgetCaptor.getValue().setWidget(widget);
//
//        verify(view).hideProcessOutput(eq(commandId));
//        verify(view).addWidget(anyString(), anyString(), anyObject(), eq(widget), anyBoolean());
//        verify(view, times(2)).selectNode(anyObject());
//        verify(view).getNodeById(anyString());
//    }
//
//    @Test
//    public void shouldAddTerminal() throws Exception {
//        OldMachineDto machineDto = mock(OldMachineDto.class);
//        MachineEntity machine = mock(MachineEntity.class);
//        when(machine.getId()).thenReturn(MACHINE_ID);
//        OldMachineConfigDto machineConfigDto = mock(OldMachineConfigDto.class);
//        when(machine.getConfig()).thenReturn(machineConfigDto);
//        when(machineConfigDto.isDev()).thenReturn(true);
//        when(machine.getStatus()).thenReturn(MachineStatus.RUNNING);
//        List<OldMachineDto> machines = new ArrayList<>(1);
//        machines.add(machineDto);
//        when(runtime.getMachines()).thenReturn(machines);
//        when(entityFactory.createMachine(machineDto)).thenReturn(machine);
//
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, new ArrayList<ProcessTreeNode>());
//
//        TerminalPresenter terminal = mock(TerminalPresenter.class);
//        when(terminalFactory.create(machine, presenter)).thenReturn(terminal);
//        IsWidget terminalWidget = mock(IsWidget.class);
//        when(terminal.getView()).thenReturn(terminalWidget);
//
//        presenter.onAddTerminal(MACHINE_ID, presenter);
//
//        verify(terminalFactory).create(eq(machine), eq(presenter));
//        verify(workspaceAgent).setActivePart(presenter);
//        verify(terminal).getView();
//        verify(view, times(2)).setProcessesData(anyObject());
//        verify(view).selectNode(anyObject());
//        verify(view).addWidget(anyString(), anyString(), anyObject(), eq(terminalWidget), anyBoolean());
//        verify(view).addProcessNode(anyObject());
//        verify(terminal).setVisible(eq(true));
//        verify(terminal).connect();
//        verify(terminal).setListener(anyObject());
//    }
//
//    @Test
//    public void shouldShowCommanOutputWhenCommandSelected() throws Exception {
//        ProcessTreeNode commandNode = mock(ProcessTreeNode.class);
//        when(commandNode.getId()).thenReturn(PROCESS_ID);
//
//        presenter.consoles.put(PROCESS_ID, outputConsole);
//
//        presenter.onTreeNodeSelected(commandNode);
//
//        verify(view).showProcessOutput(eq(PROCESS_ID));
//        verify(view).setStopButtonVisibility(anyString(), eq(true));
//    }
//
//    @Test
//    public void stopButtonShouldBeHiddenWhenConsoleHasFinishedProcess() {
//        ProcessTreeNode commandNode = mock(ProcessTreeNode.class);
//        when(commandNode.getId()).thenReturn(PROCESS_ID);
//
//        when(outputConsole.isFinished()).thenReturn(true);
//        presenter.consoles.put(PROCESS_ID, outputConsole);
//
//        presenter.onTreeNodeSelected(commandNode);
//
//        verify(view).setStopButtonVisibility(PROCESS_ID, false);
//    }
//
//    @Test
//    public void stopButtonStateShouldBeRefreshedWhenConsoleHasRunningProcess() {
//        ProcessTreeNode commandNode = mock(ProcessTreeNode.class);
//        when(commandNode.getId()).thenReturn(PROCESS_ID);
//
//        when(outputConsole.isFinished()).thenReturn(false);
//        presenter.consoles.put(PROCESS_ID, outputConsole);
//
//        presenter.onTreeNodeSelected(commandNode);
//
//        verify(view).setStopButtonVisibility(PROCESS_ID, true);
//    }
//
//    @Test
//    public void stopButtonShouldBeHiddenWhenProcessFinished() {
//        when(outputConsole.isFinished()).thenReturn(true);
//        presenter.consoles.put(PROCESS_ID, outputConsole);
//
//        presenter.onProcessFinished(new ProcessFinishedEvent(PID));
//
//        verify(view).setStopButtonVisibility(PROCESS_ID, false);
//    }
//
//    @Test
//    public void shouldStopProcessWithoutCloseCommandOutput() throws Exception {
//        ProcessTreeNode machineNode = mock(ProcessTreeNode.class);
//        ProcessTreeNode commandNode = mock(ProcessTreeNode.class);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//        List<ProcessTreeNode> children = new ArrayList<>();
//        children.add(machineNode);
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//
//        when(outputConsole.isFinished()).thenReturn(false);
//        presenter.consoles.put(PROCESS_ID, outputConsole);
//        //noinspection ConstantConditions
//        machineNode.getChildren().add(commandNode);
//
//        when(commandNode.getId()).thenReturn(PROCESS_ID);
//        when(view.getNodeIndex(anyString())).thenReturn(0);
//        when(machineNode.getChildren()).thenReturn(children);
//        when(commandNode.getParent()).thenReturn(machineNode);
//
//        presenter.onStopCommandProcess(commandNode);
//
//        verify(outputConsole).stop();
//        verify(view, never()).hideProcessOutput(eq(PROCESS_ID));
//        verify(view, never()).removeProcessNode(eq(commandNode));
//    }
//
//    @Test
//    public void shouldCloseCommandOutputWhenCommandHasFinished() throws Exception {
//        ProcessTreeNode machineNode = mock(ProcessTreeNode.class);
//        ProcessTreeNode commandNode = mock(ProcessTreeNode.class);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//        List<ProcessTreeNode> children = new ArrayList<>();
//        children.add(machineNode);
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//
//        when(outputConsole.isFinished()).thenReturn(true);
//        presenter.consoles.put(PROCESS_ID, outputConsole);
//        machineNode.getChildren().add(commandNode);
//
//        when(commandNode.getId()).thenReturn(PROCESS_ID);
//        when(view.getNodeIndex(anyString())).thenReturn(0);
//        when(machineNode.getChildren()).thenReturn(children);
//        when(commandNode.getParent()).thenReturn(machineNode);
//
//        presenter.onCloseCommandOutputClick(commandNode);
//
//        verify(commandNode, times(3)).getId();
//        verify(commandNode).getParent();
//        verify(view).getNodeIndex(eq(PROCESS_ID));
//        verify(view).hideProcessOutput(eq(PROCESS_ID));
//        verify(view).removeProcessNode(eq(commandNode));
//        verify(view).setProcessesData(anyObject());
//    }
//
//    @Test
//    public void shouldShowConfirmDialogWhenCommandHasNotFinished() throws Exception {
//        ConfirmDialog confirmDialog = mock(ConfirmDialog.class);
//        ProcessTreeNode commandNode = mock(ProcessTreeNode.class);
//
//        when(outputConsole.isFinished()).thenReturn(false);
//        presenter.consoles.put(PROCESS_ID, outputConsole);
//
//        when(commandNode.getId()).thenReturn(PROCESS_ID);
//        when(dialogFactory.createConfirmDialog(anyString(), anyString(), anyObject(), anyObject())).thenReturn(confirmDialog);
//
//        presenter.onCloseCommandOutputClick(commandNode);
//
//        verify(commandNode).getId();
//        verify(view, never()).hideProcessOutput(anyString());
//        verify(view, never()).removeProcessNode(anyObject());
//        verify(dialogFactory).createConfirmDialog(anyString(), anyString(), anyObject(), anyObject());
//        verify(confirmDialog).show();
//    }
//
//    @Test
//    public void shouldShowTerminalWhenTerminalNodeSelected() throws Exception {
//        TerminalPresenter terminal = mock(TerminalPresenter.class);
//        presenter.terminals.put(PROCESS_ID, terminal);
//
//        ProcessTreeNode terminalNode = mock(ProcessTreeNode.class);
//        when(terminalNode.getId()).thenReturn(PROCESS_ID);
//        presenter.onTreeNodeSelected(terminalNode);
//
//        verify(view).showProcessOutput(eq(PROCESS_ID));
//        verify(view, never()).setStopButtonVisibility(PROCESS_ID, true);
//    }
//
//    @Test
//    public void shouldCloseTerminal() throws Exception {
//        ProcessTreeNode machineNode = mock(ProcessTreeNode.class);
//        when(machineNode.getId()).thenReturn(MACHINE_ID);
//
//        TerminalPresenter terminal = mock(TerminalPresenter.class);
//
//        ProcessTreeNode terminalNode = mock(ProcessTreeNode.class);
//        when(terminalNode.getId()).thenReturn(PROCESS_ID);
//
//        List<ProcessTreeNode> children = new ArrayList<>();
//        children.add(machineNode);
//        presenter.rootNode = new ProcessTreeNode(ROOT_NODE, null, null, null, children);
//        presenter.terminals.put(PROCESS_ID, terminal);
//
//        when(view.getNodeIndex(anyString())).thenReturn(0);
//        when(machineNode.getChildren()).thenReturn(children);
//        when(terminalNode.getParent()).thenReturn(machineNode);
//
//        presenter.onCloseTerminal(terminalNode);
//
//        verify(terminal).stopTerminal();
//        verify(terminalNode, times(3)).getId();
//        verify(terminalNode).getParent();
//        verify(view).getNodeIndex(eq(PROCESS_ID));
//        verify(view).hideProcessOutput(eq(PROCESS_ID));
//        verify(view).removeProcessNode(eq(terminalNode));
//        verify(view).setProcessesData(anyObject());
//    }
//
//    @Test
//    public void testGo() throws Exception {
//        AcceptsOneWidget container = mock(AcceptsOneWidget.class);
//
//        presenter.go(container);
//
//        verify(container).setWidget(eq(view));
//    }
//
//    @Test
//    public void commandShouldBeRestoredWhenWsAgentIsStarted() throws Exception {
//        WsAgentStateEvent event = mock(WsAgentStateEvent.class);
//
//        MachineEntity machineEntity = mock(MachineEntity.class);
//        OldMachineDto machine = mock(OldMachineDto.class);
//        when(machineEntity.getId()).thenReturn(MACHINE_ID);
//        when(machineEntity.getWorkspaceId()).thenReturn(WORKSPACE_ID);
//        when(entityFactory.createMachine(machine)).thenReturn(machineEntity);
//        OldMachineConfigDto machineConfigDto = mock(OldMachineConfigDto.class);
//        when(machine.getConfig()).thenReturn(machineConfigDto);
//        when(machineConfigDto.isDev()).thenReturn(true);
//        when(machine.getStatus()).thenReturn(MachineStatus.RUNNING);
//        List<OldMachineDto> machines = new ArrayList<>(2);
//        machines.add(machine);
//        when(runtime.getMachines()).thenReturn(machines);
//
//        MachineProcessDto machineProcessDto = mock(MachineProcessDto.class);
//        when(machineProcessDto.getOutputChannel()).thenReturn(OUTPUT_CHANNEL);
//        when(machineProcessDto.getPid()).thenReturn(PID);
//        List<MachineProcessDto> processes = new ArrayList<>(1);
//        processes.add(machineProcessDto);
//
//        CommandOutputConsole outputConsole = mock(CommandOutputConsole.class);
//
//        CommandType commandType = mock(CommandType.class);
//        when(commandTypeRegistry.getCommandTypeById(anyString())).thenReturn(commandType);
//        when(commandConsoleFactory.create(anyObject(),
//                                          any(org.eclipse.che.api.core.model.machine.OldMachine.class))).thenReturn(outputConsole);
//
//        presenter.onWsAgentStarted(event);
//    }

}

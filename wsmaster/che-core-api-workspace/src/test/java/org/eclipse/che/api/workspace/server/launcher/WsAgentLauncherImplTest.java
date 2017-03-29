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
package org.eclipse.che.api.workspace.server.launcher;

import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;

@Listeners(MockitoTestNGListener.class)
public class WsAgentLauncherImplTest {
//    private static final String               MACHINE_ID                    = "machineId";
//    private static final String               WORKSPACE_ID                  = "testWorkspaceId";
//    private static final String               WS_AGENT_PORT                 = Constants.WS_AGENT_PORT;
//    private static final long                 WS_AGENT_MAX_START_TIME_MS    = 1000;
//    private static final long                 WS_AGENT_PING_DELAY_MS        = 1;
//    private static final String               WS_AGENT_SERVER_LOCATION      = "ws-agent.com:456789/";
//    private static final String               WS_AGENT_SERVER_URL           = "http://" + WS_AGENT_SERVER_LOCATION;
//    private static final String               WS_AGENT_SERVER_LOCATION_EXT  = "ws-agent-ext.com:456789/";
//    private static final String               WS_AGENT_SERVER_URL_EXT       = "http://" + WS_AGENT_SERVER_LOCATION;
//    private static final ServerPropertiesImpl SERVER_PROPERTIES             = new ServerPropertiesImpl(null,
//                                                                                                       WS_AGENT_SERVER_LOCATION,
//                                                                                                       WS_AGENT_SERVER_URL);
//    private static final OldServerImpl           SERVER                        = new OldServerImpl("ref",
//                                                                                             "http",
//                                                                                             WS_AGENT_SERVER_LOCATION_EXT,
//                                                                                             WS_AGENT_SERVER_URL_EXT,
//                                                                                             SERVER_PROPERTIES);
//    private static final String               WS_AGENT_TIMED_OUT_MESSAGE    = "timeout error message";
//
//    @Mock
//    private MachineProcessManager     machineProcessManager;
//    @Mock
//    private HttpJsonRequestFactory    requestFactory;
//    @Mock
//    private Instance                  machine;
//    @Mock
//    private HttpJsonResponse          pingResponse;
//    @Mock
//    private MachineImpl        machineRuntime;
//    @Mock
//    private WsAgentPingRequestFactory wsAgentPingRequestFactory;
//    @Mock
//    private Agent                     agent;
//
//    private HttpJsonRequest     pingRequest;

//    private WsAgentLauncherImpl wsAgentLauncher;

//    @BeforeMethod
//    public void setUp() throws Exception {
//        wsAgentLauncher = new WsAgentLauncherImpl(() -> machineProcessManager,
//                                                  wsAgentPingRequestFactory, null,
//                                                  WS_AGENT_MAX_START_TIME_MS,
//                                                  WS_AGENT_PING_DELAY_MS,
//                                                  WS_AGENT_TIMED_OUT_MESSAGE
//        );
//        pingRequest = Mockito.mock(HttpJsonRequest.class, new SelfReturningAnswer());
//        when(agent.getScript()).thenReturn("script");
//        when(machine.getId()).thenReturn(MACHINE_ID);
//        when(machine.getWorkspaceId()).thenReturn(WORKSPACE_ID);
//        when(machine.getRuntime()).thenReturn(machineRuntime);
//        doReturn(Collections.<String, OldServer>singletonMap(WS_AGENT_PORT, SERVER)).when(machineRuntime).getServers();
//        when(requestFactory.fromUrl(anyString())).thenReturn(pingRequest);
//        when(wsAgentPingRequestFactory.createRequest(machine)).thenReturn(pingRequest);
//        when(pingRequest.request()).thenReturn(pingResponse);
//        when(pingResponse.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
//    }
//
//    @Test
//    public void shouldStartWsAgentUsingMachineExec() throws Exception {
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(machineProcessManager).exec(eq(WORKSPACE_ID),
//                                           eq(MACHINE_ID),
//                                           eq(new CommandImpl("org.eclipse.che.ws-agent",
//                                                              "script\n" + WsAgentLauncherImpl.DEFAULT_WS_AGENT_RUN_COMMAND,
//                                                              WS_AGENT_PROCESS_NAME)),
//                                           eq(WsAgentLauncherImpl.getWsAgentProcessOutputChannel(WORKSPACE_ID)));
//
//    }
//
//    @Test
//    public void shouldPingWsAgentAfterStart() throws Exception {
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(pingRequest).request();
//        verify(pingResponse).getResponseCode();
//    }
//
//    @Test
//    public void shouldPingWsAgentMultipleTimesAfterStartIfPingFailsWithException() throws Exception {
//        when(pingRequest.request()).thenThrow(new ServerException(""),
//                                              new BadRequestException(""),
//                                              new IOException())
//                                   .thenReturn(pingResponse);
//
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(pingRequest, times(4)).request();
//        verify(pingResponse).getResponseCode();
//    }
//
//    @Test
//    public void shouldPingWsAgentMultipleTimesAfterStartIfPingReturnsNotOKResponseCode() throws Exception {
//        when(pingResponse.getResponseCode()).thenReturn(HttpURLConnection.HTTP_CREATED,
//                                                        HttpURLConnection.HTTP_NO_CONTENT,
//                                                        HttpURLConnection.HTTP_OK);
//
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(pingRequest, times(3)).request();
//        verify(pingResponse, times(3)).getResponseCode();
//    }
//
//    @Test
//    public void shouldNotPingWsAgentAfterFirstSuccessfulPing() throws Exception {
//        when(pingRequest.request()).thenThrow(new ServerException(""))
//                                   .thenReturn(pingResponse);
//
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(pingRequest, times(2)).request();
//        verify(pingResponse).getResponseCode();
//    }
//
//    @Test(expectedExceptions = ServerException.class, expectedExceptionsMessageRegExp = "Test exception")
//    public void shouldThrowMachineExceptionIfMachineManagerExecInDevMachineThrowsNotFoundException() throws Exception {
//        when(machineProcessManager.exec(anyString(),
//                                        anyString(),
//                                        any(Command.class),
//                                        anyString()))
//                .thenThrow(new NotFoundException("Test exception"));
//
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(machineProcessManager).exec(anyString(),
//                                           anyString(),
//                                           any(Command.class),
//                                           anyString());
//    }
//
//    @Test(expectedExceptions = ServerException.class, expectedExceptionsMessageRegExp = "Test exception")
//    public void shouldThrowMachineExceptionIfMachineManagerExecInDevMachineThrowsMachineException() throws Exception {
//        when(machineProcessManager.exec(anyString(),
//                                        anyString(),
//                                        any(Command.class),
//                                        anyString()))
//                .thenThrow(new MachineException("Test exception"));
//
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(machineProcessManager).exec(anyString(),
//                                           anyString(),
//                                           any(Command.class),
//                                           anyString());
//    }
//
//    @Test(expectedExceptions = ServerException.class, expectedExceptionsMessageRegExp = "Test exception")
//    public void shouldThrowExceptionIfMachineManagerExecInDevMachineThrowsBadRequestException() throws Exception {
//        when(machineProcessManager.exec(anyString(),
//                                        anyString(),
//                                        any(Command.class),
//                                        anyString()))
//                .thenThrow(new BadRequestException("Test exception"));
//
//        wsAgentLauncher.launch(machine, agent);
//
//        verify(machineProcessManager).exec(anyString(),
//                                           anyString(),
//                                           any(Command.class),
//                                           anyString());
//    }
//
//    @Test(expectedExceptions = ServerException.class,
//            expectedExceptionsMessageRegExp = WS_AGENT_TIMED_OUT_MESSAGE)
//    public void shouldThrowMachineExceptionIfPingsWereUnsuccessfulTooLong() throws Exception {
//        when(pingRequest.request()).thenThrow(new ServerException(""));
//
//        wsAgentLauncher.launch(machine, agent);
//    }

}

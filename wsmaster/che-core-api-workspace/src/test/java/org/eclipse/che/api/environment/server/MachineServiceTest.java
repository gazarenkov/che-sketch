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
package org.eclipse.che.api.environment.server;

import org.everrest.assured.EverrestJetty;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;

/**
 * @author Alexander Garagatyi
 */
@Listeners(value = {EverrestJetty.class, MockitoTestNGListener.class})
public class MachineServiceTest {
//    @SuppressWarnings("unused")
//    static final ApiExceptionMapper                     MAPPER = new ApiExceptionMapper();
//    @SuppressWarnings("unused")
//    static final WorkspaceServiceTest.EnvironmentFilter FILTER = new WorkspaceServiceTest.EnvironmentFilter();
//    @Mock
//    WorkspaceManager      wsManager;
//    @Mock
//    MachineProcessManager machineProcessManager;
//    @Mock
//    CheEnvironmentValidator environmentValidator;
//
//    MachineService service;
//
//    @BeforeMethod
//    public void setup() {
//        service = new MachineService(machineProcessManager,
//                                     new MachineServiceLinksInjector(),
//                                     wsManager,
//                                     environmentValidator);
//    }
//
//    @Test(dataProvider = "illegalMachineConfigProvider")
//    public void shouldReturnErrorOnStartMachineIfBodyIsInvalid(OldMachineConfig machineConfig) throws Exception {
//        // given
//        String workspaceId = "wsId";
//
//        // when
//        final Response response = given().auth()
//                                         .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
//                                         .when()
//                                         .body(machineConfig)
//                                         .contentType(ContentType.JSON)
//                                         .post(SECURE_PATH + "/workspace/" + workspaceId + "/machine");
//
//        // then
//        assertEquals(response.getStatusCode(), 400);
//        verify(wsManager, never()).startMachine(any(OldMachineConfig.class), anyString());
//    }
//
//    @DataProvider(name = "illegalMachineConfigProvider")
//    public static Object[][] illegalMachineConfigProvider() {
//        OldMachineConfigImpl.MachineConfigImplBuilder builder =
//                OldMachineConfigImpl.builder()
//                                 .setDev(false)
//                                 .setName("name")
//                                 .setType("type")
//                                 .setSource(new MachineSourceImpl("type1").setContent("content"));
//        return new Object[][] {
//                {builder.setType(null)
//                         .build()},
//                {builder.setSource(null)
//                         .build()},
//                {builder.setSource(new MachineSourceImpl((String)null).setContent("content"))
//                         .build()},
//                {builder.setSource(new MachineSourceImpl("type").setContent("content")
//                                                                .setLocation("location"))
//                         .build()},
//                };
//    }
//
//    @Test
//    public void shouldStartMachine() throws Exception {
//        // given
//        String workspaceId = "wsId";
//        OldMachineConfig machineConfig = OldMachineConfigImpl.builder()
//                                                       .setDev(false)
//                                                       .setName("name")
//                                                       .setType("type")
//                                                       .setSource(new MachineSourceImpl("type1").setContent("content"))
//                                                       .build();
//
//        // when
//        final Response response = given().auth()
//                                         .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
//                                         .when()
//                                         .body(machineConfig)
//                                         .contentType(ContentType.JSON)
//                                         .post(SECURE_PATH + "/workspace/" + workspaceId + "/machine");
//
//        // then
//        assertEquals(response.getStatusCode(), 204);
//        verify(wsManager).startMachine(any(OldMachineConfig.class), eq(workspaceId));
//    }
//
//    @Test
//    public void shouldStopMachine() throws Exception {
//        // given
//        String workspaceId = "wsId";
//        String machineId = "mcId";
//
//        // when
//        final Response response = given().auth()
//                                         .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
//                                         .when()
//                                         .delete(SECURE_PATH + "/workspace/" + workspaceId + "/machine/" + machineId);
//
//        // then
//        assertEquals(response.getStatusCode(), 204);
//    }
}
